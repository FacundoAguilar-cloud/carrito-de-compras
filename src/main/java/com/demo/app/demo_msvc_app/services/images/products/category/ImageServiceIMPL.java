package com.demo.app.demo_msvc_app.services.images.products.category;

import java.util.List;
import java.util.Optional;

import com.demo.app.demo_msvc_app.entities.Image;

public interface ImageServiceIMPL {
Optional <Image>  getImageById(Long Id);

Image getImageByFileName(String fileName);

Image getImageByFileType(String fileType);

List <Image> getAllImages();

Image createImage(Image image);

Image updateImage(Image image, Long id);
}
