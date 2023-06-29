package com.lost.image.service;

import com.lost.common.domain.exception.StorageException;
import com.lost.image.domain.FileType;
import com.lost.image.domain.Image;
import com.lost.image.domain.StorageProperties.ImageConfig;
import com.lost.image.service.repository.ImagePostRepository;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
public class ImageService {

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSS");
    private static final String URL_FORMAT = "%s/%s";
    private final ImageConfig imageConfig;
    private final ImagePostRepository imagePostRepository;
    private final Path rootLocation;

    public ImageService(ImageConfig imageConfig, ImagePostRepository imagePostRepository) {
        this.imageConfig = imageConfig;
        this.imagePostRepository = imagePostRepository;
        this.rootLocation = Paths.get(imageConfig.getRootLocation());
    }

    public List<Image> store(List<MultipartFile> images) {
        return images.stream().map(this::store).toList();
    }

    public Image store(MultipartFile imageFile) {
        try {
            if (imageFile.isEmpty()) {
                throw new StorageException("빈 파일을 저장할 수 없습니다.");
            }

            String prefix = LocalDateTime.now().format(DATE_TIME_FORMAT);
            String imageName = prefix + imageFile.getOriginalFilename();

            String filenameExtension = StringUtils.getFilenameExtension(imageFile.getOriginalFilename());
            FileType fileType = FileType.valueOf(filenameExtension);

            Image image = Image.builder()
                    .url(getImageUrl(imageName))
                    .fileName(imageFile.getOriginalFilename())
                    .fileSize(imageFile.getSize())
                    .fileType(fileType)
                    .build();
            image = imagePostRepository.save(image);

            Path destinationFile = getPath(imageName);
            try (InputStream inputStream = imageFile.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            return image;
        } catch (IOException e) {
            throw new StorageException("이미지 저장에 실패했습니다.", e);
        }
    }

    private Path getPath(String imageName) {
        Path destinationFile = rootLocation.resolve(Paths.get(imageName))
                .normalize()
                .toAbsolutePath();
        if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
            throw new StorageException("현재 디렉터리 외부에 이미지를 저장할 수 없습니다.");
        }
        return destinationFile;
    }

    private String getImageUrl(String imageName) {
        return String.format(URL_FORMAT, imageConfig.getBaseUrl(), imageName);
    }
}
