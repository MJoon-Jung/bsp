package com.lost.image.service;

import com.lost.common.domain.StorageProperties.ImageConfig;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Component;

@Component
public class FileGeneratorUtil {

    private static final String URL_FORMAT = "%s/%s";
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
        return rootPath.resolve(Paths.get(fileName))
                .normalize()
                .toAbsolutePath();
    }
}
