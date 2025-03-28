package com.demo.app.demo_msvc_app.services.images.products.category;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.demo.app.demo_msvc_app.dto.ImageDto;
import com.demo.app.demo_msvc_app.entities.Image;

public interface ImageServiceIMPL {
Image getImageById(Long Id);

List <Image> getAllImages();

List <ImageDto> createImage(List <MultipartFile> files, Long productId);

void updateImage(MultipartFile file, Long imageId);

void deleteImageById(Long id);
}
