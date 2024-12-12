package com.sbland.email.bo;

import org.springframework.stereotype.Service;

import com.sbland.common.reponse.HttpStatusCode;
import com.sbland.common.reponse.Response;
import com.sbland.email.dto.EmailVerifyDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailServiceBO {
	private final EmaiCacheBO emailCacheBO;
	
	public Response<Boolean> sendVerifyEmail(String email) { 
		EmailVerifyDTO emailVerify = emailCacheBO.createVerifyEmail(email);
		if (emailVerify != null) {
			return Response
					.<Boolean>builder()
					.code(HttpStatusCode.OK.getCodeValue())
					.message("이메일 전송 성공")
					.data(true)
					.build();
		}
		
		return Response
				.<Boolean>builder()
				.code(HttpStatusCode.FAIL.getCodeValue())
				.message("이메일 전송 실패")
				.data(false)
				.build();
	}

	public Response<Boolean> verifyEmail(String salt, String email) {
		EmailVerifyDTO emailVerify = emailCacheBO.getVerifyEmail(email);
		if (emailVerify == null) {
			return Response
					.<Boolean>builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("이메일 인증 유효기간이 지났습니다.")
					.data(false)
					.build();
		} else {
			if (emailVerify.getSalt().equals(salt)) {
				return Response
						.<Boolean>builder()
						.code(HttpStatusCode.OK.getCodeValue())
						.message("이메일 인증에 성공했습니다.")
						.data(true)
						.build();
			} else {
				return Response
						.<Boolean>builder()
						.code(HttpStatusCode.FAIL.getCodeValue())
						.message("이메일 인증에 실패했습니다.")
						.data(false)
						.build();
			}
		}
	}
	
}
