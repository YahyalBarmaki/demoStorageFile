package com.example.demostoragefile.service;

import com.example.demostoragefile.entity.FileData;
import com.example.demostoragefile.repository.FileDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Service
public class StorageService {

    private final FileDataRepository fileDataRepository;
    final String FOLDER_PATH = "/Users/yayaly/Documents/MyFiles/";

    public StorageService(FileDataRepository fileDataRepository) {
        this.fileDataRepository = fileDataRepository;
    }


    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        String filePath=FOLDER_PATH+file.getOriginalFilename();

        FileData fileData=fileDataRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath).build());

        file.transferTo(new File(filePath));

        if (fileData != null) {
            return "file uploaded successfully : " + filePath;
        }
        return null;
    }

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
          Optional<FileData> fileData = fileDataRepository.findByName(fileName);
        String filePath=fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
        //return Files.readAllBytes(new File(filePath).toPath());
    }

    public List<FileData> getFiles() {
        return fileDataRepository.findAll();
    }

}
