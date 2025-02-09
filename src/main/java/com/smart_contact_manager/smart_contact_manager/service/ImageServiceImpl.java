package com.smart_contact_manager.smart_contact_manager.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.smart_contact_manager.smart_contact_manager.helpers.AppConstants;
@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
	private Cloudinary cloudinary;
	
	@Override
	public String uploadImage(MultipartFile image,String filesname) {
		try {
			
			String filename = UUID.randomUUID().toString();
			
			byte[] data = new byte[image.getInputStream().available()];
			image.getInputStream().read(data);
			cloudinary.uploader().upload(data, ObjectUtils.asMap(
                "public_id",filename
					));
			return this.getUrlFromPublicId(filename);
		}
		catch(IOException e) {
			e.printStackTrace();
			return null;
		}
		
		
		
	}

	@Override
	public String getUrlFromPublicId(String publicId) {
		
		return cloudinary.url().transformation(
				new Transformation<>()
				.width(AppConstants.CONTACT_IMAGE_HEIGHT)
				.height(AppConstants.CONTACT_IMAGE_WIDTH)
				.crop(AppConstants.CONTACT_IMAGE_CROP)
				).generate(publicId);
	}

	
	

}
