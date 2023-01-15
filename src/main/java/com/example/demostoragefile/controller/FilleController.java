package com.example.demostoragefile.controller;

import com.example.demostoragefile.entity.FileData;
import com.example.demostoragefile.service.StorageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/app/v1")
public class FilleController {

    private  final StorageService storageService;

    public FilleController(StorageService storageService) {
        this.storageService = storageService;
    }


    @PostMapping("/fileSystem")
    public ResponseEntity<?> uploadImageToFIleSystem(@RequestParam("image") MultipartFile file) throws IOException {
        String uploadImage = storageService.uploadImageToFileSystem(file);
        return ResponseEntity.status(OK)
                .body(uploadImage);
    }

    @GetMapping("/fileSystem/{fileName}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageData = storageService.downloadImageFromFileSystem(fileName);
        return ResponseEntity.status(OK)
                .contentType(MediaType.valueOf("image/.*"))
                .body(imageData);

    }
    @GetMapping("/getFiles")
    public ResponseEntity<List<FileData>> fileList(){
        return new ResponseEntity<>(storageService.getFiles(),OK);
    }
}
