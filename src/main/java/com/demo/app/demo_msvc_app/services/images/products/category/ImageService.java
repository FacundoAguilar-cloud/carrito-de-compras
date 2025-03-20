package com.demo.app.demo_msvc_app.services.images.products.category;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.app.demo_msvc_app.entities.Image;
import com.demo.app.demo_msvc_app.repositories.ImageRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ImageService implements ImageServiceIMPL {
    @Autowired
    private final ImageRepository imageRepository;
    @Override
    public Optional<Image> getImageById(Long Id) {
       return imageRepository.findById(Id);
    }

    @Override
    public Image getImageByFileName(String fileName) {
        return imageRepository.findByFileName(fileName);
    }

    @Override
    public Image getImageByFileType(String fileType) {
        return imageRepository.findByFileType(fileType);
    }

    @Override
    public List<Image> getAllImages() {
       return (List <Image>) imageRepository.findAll();
    }

    @Override
    public Image createImage(Image image) {
        return null;
    }

    @Override
    public Image updateImage(Image image, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateImage'");
    }

}
