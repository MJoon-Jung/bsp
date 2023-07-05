package com.lost.image.service;

import com.lost.common.domain.StorageProperties.ImageConfig;
import com.lost.common.domain.exception.StorageException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Component;

@Component
public class FileGeneratorUtil {

    private static final String URL_FORMAT = "%s/%s";
    private static final String STORE_FILE_OUTSIDE_CURRENT_DIRECTORY_MESSAGE = "유효하지 않은 저장 경로입니다.";
    private final FileNameGenerator fileNameGenerator;
    private final Path rootPath;

    public FileGeneratorUtil(FileNameGenerator fileNameGenerator, ImageConfig imageConfig) {
        this.fileNameGenerator = fileNameGenerator;
        rootPath = Paths.get(imageConfig.getRootLocation());
    }

    public String generateFileName(String fileName) {
        return fileNameGenerator.generate(fileName);
    }

    public String generateFileUrl(String baseUrl, String fileName) {
        return String.format(URL_FORMAT, baseUrl, fileName);
    }

    public Path generateAbsolutePath(String fileName) {
        Path absolutePath = rootPath.resolve(Paths.get(fileName))
                .normalize()
                .toAbsolutePath();
        if (!absolutePath.getParent().equals(rootPath.toAbsolutePath())) {
            throw new StorageException(STORE_FILE_OUTSIDE_CURRENT_DIRECTORY_MESSAGE);
        }
        return absolutePath;
    }
}
