package com.sbland.common.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.buffer.DataBuffer;
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
			InputStream inputStream = webClient.get()
			        .uri(imageUrl)
			        .retrieve()
			        .bodyToFlux(DataBuffer.class)        
			        .map(DataBuffer::asInputStream)     
			        .reduce((is1, is2) -> {            
			            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			            try {
							IOUtils.copy(is1, outputStream);
						} catch (IOException e) {
							log.info("[파일 병합 실패] imageUrl:{}", imageUrl);
							e.printStackTrace();
						}
			            try {
							IOUtils.copy(is2, outputStream);
						} catch (IOException e) {
							log.info("[파일 병합 실패] imageUrl:{}", imageUrl);
							e.printStackTrace();
						}
			            return new ByteArrayInputStream(outputStream.toByteArray());
			        })
			        .block();
		  String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
		  String ContentType = "image/" + fileName.substring(fileName.lastIndexOf(".") + 1);
   
     
          return new MockMultipartFile(fileName, fileName, ContentType, inputStream);
		} catch (IOException e) {
			log.info("[파일 다운받기 실패] imageUrl:{}", imageUrl);
			e.printStackTrace();
			return null;
		}
	}
}
