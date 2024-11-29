package com.sbland.common.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileManagerIf {
	public String uploadFile(MultipartFile file, String name, String fileUploadPath, String folder);
	public void deleteFile(String imagePath, String fileUploadPath);
}
