package com.token.serviceimpl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.token.entity.User;
import com.token.entity.UserProfile;
import com.token.repository.UserProfileRepository;
import com.token.repository.UserRepository;
import com.token.service.BasicService;
import com.token.service.UserProfileService;

@Service
public class UserProfileServiceImpl extends BasicService<UserProfile, UserProfileRepository> implements UserProfileService{

	@Autowired
	public UserRepository userRepository;

	@Override
	public UserProfile save(MultipartFile multipartFile,Long user_id) throws IOException {
		
		User u = userRepository.findById(user_id).orElse(null);
		UserProfile userProfile = repository.loadUserProfileByUserId(user_id).orElse(null);
		if(u != null && userProfile != null) {
			repository.delete(userProfile);
		}
		
		UserProfile doc = new UserProfile();
		doc.setFileName(multipartFile.getOriginalFilename());
		String extention = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
		BufferedImage src = ImageIO.read(new ByteArrayInputStream(multipartFile.getBytes()));
		File destination = new File("C:\\Users\\Admin\\Documents\\images\\"+multipartFile.getOriginalFilename());
		ImageIO.write(src, extention , destination);
		doc.setFilePath(destination.getPath());
		User user = userRepository.findById(user_id).orElse(null);
		doc.setUser(user);
		return repository.save(doc);
	}

	@Override
	public Optional<UserProfile> loadUserProfileByUserId(Long user_id) {
		return repository.loadUserProfileByUserId(user_id);
	}

}
