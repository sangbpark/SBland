package com.sbland.common.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Primary
@Component("fileCurrent")
public class FileCurrentTIme implements FileManagerIf{

	@Override
	public String uploadFile(MultipartFile file, String name, String fileUploadPath, String folder) {
		String directoryName = name + "_" + System.currentTimeMillis();
		String filePath = fileUploadPath + folder + "/" + directoryName + "/";
		
		File directory = new File(filePath);
		if (directory.mkdir() == false) {
			log.info("[파일매니저 파일생성 실패] directory:{}", filePath);
			return null; 
		}
		
		
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(filePath + file.getOriginalFilename());
			Files.write(path, bytes);
			return "/images/" + folder + "/" + directoryName + "/" + file.getOriginalFilename();
		} catch (IOException e) {
			log.info("[파일매니저 파일생성 이미지 실패] imageName:{}", file.getOriginalFilename());
			return null; 
		}
	}

	@Override
	public void deleteFile(String imagePath, String fileUploadPath) {
		Path path= null;
		try {
		path = Paths.get(fileUploadPath + imagePath.replace("/images/", ""));
		} catch (InvalidPathException e) {
			log.info("[파일매니저 파일경로 문제] imagePath:{}", imagePath);
			return;
		}
		if (Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				log.info("[파일매니저 파일삭제] imagePath:{}", imagePath);
				return;
			}
		}
		
		path = path.getParent();
		if (Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				log.info("[파일매니저 폴더삭제] imagePath:{}", imagePath);
			}
		}
	}

}
