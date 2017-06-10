package com.svs.etracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svs.etracker.model.UploadImage;
import com.svs.etracker.repository.ImageRepository;

@Service
public class ImageService {

	@Autowired
	private ImageRepository imageRepository;

	public void saveImage(UploadImage image){
		imageRepository.save(image);
	}

	public UploadImage getUploadImage(int expenseId){
		return imageRepository.findOne(expenseId);
	}

	public void deleteUploadImage(int[] expenseID){
		for (int i : expenseID) {
			imageRepository.delete(i);
		}
	}
}
