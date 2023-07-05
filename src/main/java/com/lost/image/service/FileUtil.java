package com.lost.image.service;

import com.lost.common.domain.StorageProperties.ImageConfig;
import com.lost.common.domain.exception.ResourceNotFoundException;
import java.io.File;
import org.springframework.stereotype.Component;

@Component
public class FileUtil {

    private final String rootLocation;

    public FileUtil(ImageConfig imageConfig) {
        rootLocation = imageConfig.getRootLocation();
    }

    public File find(String fileName) {
        File file = new File(rootLocation, fileName);
        if (!file.exists()) {
            throw new ResourceNotFoundException("IMAGE", "fileName", fileName);
        }
        return file;
    }
}
