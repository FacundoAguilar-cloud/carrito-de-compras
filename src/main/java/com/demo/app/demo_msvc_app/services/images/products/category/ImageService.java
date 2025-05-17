package com.demo.app.demo_msvc_app.services.images.products.category;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.app.demo_msvc_app.dto.ImageDto;
import com.demo.app.demo_msvc_app.entities.Image;
import com.demo.app.demo_msvc_app.entities.Product;
import com.demo.app.demo_msvc_app.repositories.ImageRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ImageService implements ImageServiceIMPL {
    
    private final ImageRepository imageRepository;
    private final ProductService productService;
   
    @Override
    public Image getImageById(Long Id) {
       return imageRepository.findById(Id).orElseThrow(() -> new RuntimeException("Image was not found with id " + Id));
    }
    
    @Override
    public List<Image> getAllImages() {
       return (List <Image>) imageRepository.findAll();
    }

    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> imageDtos = new ArrayList<>();
        
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(file.getBytes());
                image.setProduct(product);
                
                // Guardamos la imagen para obtener el ID generado
                Image savedImage = imageRepository.save(image);
                
                String downloadUrl = "/api/image-download/" + savedImage.getId(); 
                savedImage.setDownloadUrl(downloadUrl);
                
                imageRepository.save(savedImage); 
                
                ImageDto imageDto = new ImageDto();
                imageDto.setId(savedImage.getId());
                imageDto.setFileName(savedImage.getFileName());
                imageDto.setDownloadUrL(savedImage.getDownloadUrl());
                imageDtos.add(imageDto);
                
            } catch (IOException e) {
                throw new RuntimeException("Error processing file: " + e.getMessage());
            }
        }
        return imageDtos;
    }

    @Override
    public void updateImage( MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImage(file.getBytes());
            imageRepository.save(image);
        } catch (Exception e) {
            throw new RuntimeException("null");
        }
        
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.deleteById(id);
        
    }

}
