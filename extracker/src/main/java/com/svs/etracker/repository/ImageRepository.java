package com.svs.etracker.repository;

import org.springframework.data.repository.CrudRepository;

import com.svs.etracker.model.UploadImage;

public interface ImageRepository extends CrudRepository<UploadImage, Integer> {

}
