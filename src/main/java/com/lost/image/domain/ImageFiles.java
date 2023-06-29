package com.lost.image.domain;

import java.util.Collections;
import java.util.List;

public class ImageFiles {

    private final List<ImageFile> imageFiles;

    public ImageFiles(List<ImageFile> imageFiles) {
        this.imageFiles = imageFiles;
    }

    public List<ImageFile> getImageFiles() {
        return Collections.unmodifiableList(imageFiles);
    }
}
