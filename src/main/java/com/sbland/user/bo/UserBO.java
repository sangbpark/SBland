package com.sbland.user.bo;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbland.common.encrypt.EncryptUtils;
import com.sbland.common.reponse.HttpStatusCode;
import com.sbland.common.reponse.Response;
import com.sbland.user.domain.User;
import com.sbland.user.dto.UserSessionDTO;
import com.sbland.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserBO {
	private final UserMapper userMapper;
	private final EncryptUtils encryptUtils;
	private final ObjectMapper objectMapper;
	
	public Response<Boolean> isDuplicateUserByLoginId(String loginId) {
		User user = userMapper.selectUserByLoginId(loginId);
		if (user != null) {
			return  Response
					.<Boolean>builder()
					.code(HttpStatusCode.OK.getCodeValue())
					.message("중복입니다.")
					.data(true)
					.build();
		} else {
			return  Response
					.<Boolean>builder()
					.code(HttpStatusCode.OK.getCodeValue())
					.message("중복이 아닙니다.")
					.data(false)
					.build();
		}
	}
	
	public Response<String> addUser(String loginId, String password
			, String name, String email, LocalDate birthday, String gender) {
		String hashedPassword = encryptUtils.hashedPassword(password);
		int result = userMapper.insertUser(loginId, hashedPassword, name, email, birthday, gender, "USER");
		if (result == 1) {
			return Response
					.<String>builder()
					.code(HttpStatusCode.OK.getCodeValue())
					.message("가입 성공")
					.data(name)
					.build();
		} else {
			log.info("[회원가입] 실패 loginId:{}", loginId);
			return Response
					.<String>builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("가입 실패")
					.data(name)
					.build();
		}
	}
	
	public Response<UserSessionDTO> getUserByUserIdAndPassword(String loginId, String password) {
		UserSessionDTO userSessionDTO = null;
		User user = userMapper.selectUserByLoginId(loginId);
		if (user == null) {
			return Response
					.<UserSessionDTO>builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("아이디를 확인해주세요.")
					.data(userSessionDTO)
					.build();
		}
		if (encryptUtils.isMatchPassword(password, user.getPassword())) {
			userSessionDTO = objectMapper.convertValue(user, UserSessionDTO.class);
		}
		if (userSessionDTO == null) {
			return Response
					.<UserSessionDTO>builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("패스워드를 확인 해주세요.")
					.data(userSessionDTO)
					.build();
		} else {
			return Response
					.<UserSessionDTO>builder()
					.code(HttpStatusCode.OK.getCodeValue())
					.message("로그인 성공")
					.data(userSessionDTO)
					.build();
		}
	}
}
