package com.lost.image.service;

import com.lost.image.infra.ImagePrinter;
import com.lost.image.service.dto.ImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageLoadService {

    private final ImagePrinter imagePrinter;

    public ImageResponse load(String imageName) {
        return imagePrinter.print(imageName);
    }
}
