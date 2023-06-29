package com.lost.image.domain;

import com.lost.common.domain.exception.StorageException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ImageFile {

    private final MultipartFile file;
    private final Path path;
    private final String defaultUrl;

    public ImageFile(MultipartFile file, String prefix, String defaultUrl) {
        validate(file);
        this.file = file;
        path = Paths.get(prefix + Objects.requireNonNull(file.getOriginalFilename()));
        this.defaultUrl = defaultUrl;
    }

    private void validate(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new StorageException("빈 파일을 저장할 수 없습니다.");
        }
    }
}
