package com.sbland.common.file;

import java.io.ByteArrayOutputStream;

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
	
//	public static final String FILE_UPLOAD_PATH = "C:\\Users\\qkrtk\\Desktop\\SBLAND\\sbland-workspace\\images\\";
	public static final String FILE_UPLOAD_PATH = "/home/ec2-user/images/";
	
	public String uploadFile(MultipartFile file, String name, String folder) {
		return fileManagerIf.uploadFile(file, name, FILE_UPLOAD_PATH, folder);
	}
	
	public void deleteFile(String imagePath) {
		fileManagerIf.deleteFile(imagePath, FILE_UPLOAD_PATH);
	}
	
	public MultipartFile imageDownload(String imageUrl) {
		
	    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
	
	        webClient.get()
	            .uri(imageUrl)
	            .retrieve()
	            .bodyToFlux(DataBuffer.class)
	            .map(DataBuffer::asInputStream)
	            .doOnNext(inputStream -> {
	                try (inputStream) {
	                    inputStream.transferTo(outputStream); 
	                } catch (Exception e) {
	                	log.info("[파일 다운로드 실패] imageUrl:{}", imageUrl);
	                }
	            })
	            .blockLast(); 

	       
	        byte[] imageBytes = outputStream.toByteArray();

	     
	        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
	        String contentType = "image/" + fileName.substring(fileName.lastIndexOf(".") + 1);

	      
	        return new MockMultipartFile(fileName, fileName, contentType, imageBytes);

	    } catch (Exception e) {
	    	log.info("[파일 다운로드 실패] imageUrl:{}", imageUrl);
	    	return null;
	    }
	}
}
