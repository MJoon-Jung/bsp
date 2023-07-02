package com.lost.image.service;

import com.lost.common.domain.StorageProperties.ImageConfig;
import com.lost.common.service.UuidGenerator;
import com.lost.image.controller.response.ImageResponse;
import com.lost.image.infra.ImageFile;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ImageUploadService {

    private static final String DELIMITER = "_";
    private final ImageConfig imageConfig;
    private final UuidGenerator uuidGenerator;

    public List<ImageResponse> store(List<MultipartFile> images) {
        return images.stream().map(this::store).toList();
    }

    public ImageResponse store(MultipartFile file) {
        ImageFile imageFile = ImageFile.from(file, uuidGenerator, imageConfig.getBaseUrl());
        imageFile.save(imageConfig.getRootLocation());
        return ImageResponse.from(imageFile);
    }
}
