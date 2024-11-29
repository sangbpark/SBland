package com.sbland.common.file;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class FileManagerTest {
	@Autowired
	FileManager fileManager;

	@Test
	void 파일다운로드테스트() {
		MultipartFile file =  fileManager.imageDownload("https://i.ebayimg.com/images/g/waMAAOSwdSxnN4Qx/s-l225.jpg");
		log.info("[테스트] file:{}", file);
		fileManager.uploadFile(file, "테스트", "product");
	}

}
