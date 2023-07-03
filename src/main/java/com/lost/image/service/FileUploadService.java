package com.lost.image.service;

import com.lost.image.infra.FileSaver;
import com.lost.image.service.dto.FileSaveResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final FileSaver fileSaver;

    public List<FileSaveResult> store(List<MultipartFile> images) {
        return images.stream().map(this::store).toList();
    }

    public FileSaveResult store(MultipartFile file) {
        return fileSaver.save(file);
    }
}
