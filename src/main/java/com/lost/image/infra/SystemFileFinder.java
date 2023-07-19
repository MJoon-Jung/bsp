package com.lost.image.infra;

import com.lost.common.domain.StorageProperties.ImageConfig;
import com.lost.common.domain.exception.ResourceNotFoundException;
import com.lost.image.service.FileFinder;
import java.io.File;
import org.springframework.stereotype.Component;

@Component
public class SystemFileFinder implements FileFinder {

    private final String rootLocation;

    public SystemFileFinder(ImageConfig imageConfig) {
        rootLocation = imageConfig.getRootLocation();
    }

    @Override
    public File find(String fileName) {
        File file = new File(rootLocation, fileName);
        if (!file.exists()) {
            throw new ResourceNotFoundException("IMAGE", "fileName", fileName);
        }
        return file;
    }
}
