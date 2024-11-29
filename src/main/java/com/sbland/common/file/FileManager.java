package com.sbland.common.file;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class FileManager  {
	private final FileManagerIf fileManagerIf;
	private final WebClient webClient;
	
	public static final String FILE_UPLOAD_PATH = "C:\\Users\\qkrtk\\Desktop\\SBLAND\\sbland-workspace\\images\\";
	
	public String uploadFile(MultipartFile file, String name, String folder) {
		return fileManagerIf.uploadFile(file, name, FILE_UPLOAD_PATH, folder);
	}
	
	public void deleteFile(String imagePath) {
		fileManagerIf.deleteFile(imagePath, FILE_UPLOAD_PATH);
	}
	
	public MultipartFile imageDownload(String imageUrl) {
		try {
		  byte[] imageBytes = webClient.get()
                  .uri(imageUrl)
                  .retrieve()
                  .bodyToMono(byte[].class)
                  .block();
		  String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
		  String ContentType = "image/" + fileName.substring(fileName.lastIndexOf(".") + 1);
          InputStream inputStream = new ByteArrayInputStream(imageBytes);
     
          return new MockMultipartFile(fileName, fileName, ContentType, inputStream);
		} catch (IOException e) {
			log.info("[파일 다운받기 실패] imageUrl:{}", imageUrl);
			e.printStackTrace();
			return null;
		}
	}
}
