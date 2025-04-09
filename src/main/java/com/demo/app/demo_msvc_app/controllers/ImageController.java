package com.demo.app.demo_msvc_app.controllers;


import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.app.demo_msvc_app.dto.ImageDto;
import com.demo.app.demo_msvc_app.entities.Image;
import com.demo.app.demo_msvc_app.response.ApiResponse;
import com.demo.app.demo_msvc_app.services.images.products.category.ImageServiceIMPL;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ImageController {
private final ImageServiceIMPL imageService;

@PostMapping("/upload-image/{productId}")
public ResponseEntity<ApiResponse> saveImage(@RequestParam ("files") List<MultipartFile> files, @PathVariable Long productId){
    try {
        List <ImageDto> imagesDtos = imageService.saveImage(files, productId);
        return ResponseEntity.ok(new ApiResponse("Upload Succesfully!", imagesDtos));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed, please try again.", e.getMessage()));
    }
   
}

@GetMapping("/image-download/{imageId}")
public ResponseEntity<ByteArrayResource> download(@PathVariable Long imageId){
    Image image = imageService.getImageById(imageId);
    ByteArrayResource resource = new ByteArrayResource(image.getImage());
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(image.getFileType()))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
        .body(resource);
}
@PutMapping("/image/update{imageId}")
public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file){
try {
    Image image = imageService.getImageById(imageId);
    if (image != null) {
        imageService.updateImage(file, imageId);
        return ResponseEntity.ok(new ApiResponse("Update succesfully!", null));
    }
} catch (NoSuchElementException e) {
  return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse( e.getMessage(), null));
}
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed", HttpStatus.INTERNAL_SERVER_ERROR));
}
@DeleteMapping("/image/delete{imageId}")
public ResponseEntity<ApiResponse> deleteImageById(@PathVariable Long imageId){
    try {
        Image image = imageService.getImageById(imageId);
        if (image != null) {
            imageService.deleteImageById(imageId);
            return ResponseEntity.ok(new ApiResponse("Image deleted successfully!", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Image not found", null));
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed", HttpStatus.INTERNAL_SERVER_ERROR));
    }
}

}
