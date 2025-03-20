package com.demo.app.demo_msvc_app.repositories;

import org.springframework.data.repository.CrudRepository;

import com.demo.app.demo_msvc_app.entities.Image;

public interface ImageRepository extends CrudRepository <Image, Long> {

    Image findByFileName(String fileName);

    Image findByFileType(String fileType);

}
