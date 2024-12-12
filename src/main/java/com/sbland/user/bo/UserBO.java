package com.sbland.user.bo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbland.common.encrypt.EncryptUtils;
import com.sbland.common.keys.KeysGenerator;
import com.sbland.common.objectmapper.ObjectMapperFactory;
import com.sbland.common.reponse.HttpStatusCode;
import com.sbland.common.reponse.Response;
import com.sbland.email.bo.EmailBO;
import com.sbland.user.domain.User;
import com.sbland.user.dto.UserDTO;
import com.sbland.user.dto.UserSessionDTO;
import com.sbland.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor  
@Service
public class UserBO {
	private final UserMapper userMapper;
	private final EmailBO emailBO;
	private final EncryptUtils encryptUtils;
	
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
	
	public UserDTO getUserDTObyUserId(Long id) {
		ObjectMapper camelObjectMapper = new ObjectMapperFactory().getCamelObjectMapper();
		return camelObjectMapper.convertValue(userMapper.selectUserById(id), UserDTO.class);
	};
	
	public Response<Boolean> getUserByUseIdAndPassword(Long id, String password) {
		User user = userMapper.selectUserById(id);
		if (user == null) {
			return Response
					.<Boolean>builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("존재하지 않는 유저입니다.")
					.data(false)
					.build();
		} 
		if (encryptUtils.isMatchPassword(password, user.getPassword())) {
			return Response
					.<Boolean>builder()
					.code(HttpStatusCode.OK.getCodeValue())
					.message("성공")
					.data(true)
					.build();
		} else {
			return Response
					.<Boolean>builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("패스워드를 확인해주세요.")
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
	
	public Response<Boolean> updateUser (Long id, String loginId, String password
			, String name, String email, LocalDate birthday, String gender) {
		String hashedPassword = "";
		if (password.length() > 8) {
			hashedPassword = encryptUtils.hashedPassword(password);
		}
		User user = userMapper.selectUserById(id);
		if (user == null) {
			return Response
					.<Boolean>builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("존재하지 않는 유저 입니다.")
					.data(false)
					.build();
		}
		user = setUserUpdate(loginId, hashedPassword, name, email, birthday, gender, user);
		int result = userMapper.updateUser(user);
		if (result == 1) {
			return Response
					.<Boolean>builder()
					.code(HttpStatusCode.OK.getCodeValue())
					.message("유저정보 변경 성공")
					.data(true)
					.build();
		} else {
			log.info("[회원가입] 실패 loginId:{}", loginId);
			return Response
					.<Boolean>builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("유저 정보 변경 실패")
					.data(false)
					.build();
		}
	}
	
	public Response<UserSessionDTO> getUserByUserLoginIdAndPassword(String loginId, String password) {
		ObjectMapper camelObjectMapper = new ObjectMapperFactory().getCamelObjectMapper();
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
			userSessionDTO = camelObjectMapper.convertValue(user, UserSessionDTO.class);
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
	
	public Response<List<String>> getFindUserByNameAndEmail(String name, String email) {

			List<User> userList = userMapper.selectFindUserByNameAndEmail(name, email);
			if (userList.isEmpty()) {
				return Response
						.<List<String>>builder()
						.code(HttpStatusCode.FAIL.getCodeValue())
						.message("유저 아이디를 가져오는데 실패했습니다.")
						.data(null)
						.build();				
			}
			List<String> userLoginIdList = userList
										.stream()
										.map(user -> {
											int lastIndex = user.getLoginId().length();
											String loginId = user.getLoginId().substring(0, lastIndex - 2) + "**";
											return loginId;
										})
										.collect(Collectors.toList());
			return Response
					.<List<String>>builder()
					.code(HttpStatusCode.OK.getCodeValue())
					.message("성공")
					.data(userLoginIdList)
					.build();

	};
	
	public Response<Boolean> changePsswordByUserNameAndUserLoginId(String name, String loginId) {
		User user = userMapper.selectUserByLoginIdAndName(loginId, name);
		if (user == null) {
			return Response
					.<Boolean>builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("존재하지 않는 유저 입니다.")
					.data(false)
					.build();
		}
		String newPassword = new KeysGenerator().getSalt(16) + "@1";
		String body = "안녕하세요 SBLAND입니다.\n" + "새로운 비밀번호는: " + newPassword + "입니다.";
		emailBO.sendEmail(user.getEmail(), "[SBLAND] 비밀번호 찾기", body);
		String hashNewPassword = encryptUtils.hashedPassword(newPassword);
		int result = userMapper.updateUserPasswordByUserNameAndLoginId(name, loginId, hashNewPassword);
		if (result > 0) {
			return Response
					.<Boolean>builder()
					.code(HttpStatusCode.OK.getCodeValue())
					.message("비밀번호 변경에 성공했습니다.")
					.data(true)
					.build();
		} else {
			return Response
					.<Boolean>builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("비밀번호 변경에 실패했습니다.")
					.data(false)
					.build();
		}
	};
	
	private User setUserUpdate(String loginId, String password
			, String name, String email, LocalDate birthday, String gender ,User user) {
		String newLoginId = user.getLoginId();
		String newPassword = user.getPassword();
		String newName = user.getName();
		String newEmail = user.getEmail();
		LocalDate newBirthday = birthday;
		String newGender = gender;
		if (loginId.length() >= 4) {
			newLoginId = loginId;
		}
		if (password.length() >= 8) {
			newPassword = password;
		}
		if (name.length() >= 2) {
			newName = name;
		}
		if (email.length() >= 5) {
			newEmail = email;
		}
		return user
				.toBuilder()
				.loginId(newLoginId)
				.password(newPassword)
				.name(newName)
				.birthday(newBirthday)
				.email(newEmail)
				.gender(newGender)
				.build();
	};
}
