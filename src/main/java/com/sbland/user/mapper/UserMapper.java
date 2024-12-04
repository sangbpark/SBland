package com.sbland.user.mapper;

import java.time.LocalDate;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbland.common.reponse.Response;
import com.sbland.user.domain.User;

@Mapper
public interface UserMapper {
	public User selectUserByLoginId(String loginId);
	
	public User selectUserByUserIdAndPassword(
			@Param("loginId") String loginId, 
			@Param("password") String password);
	
	public int insertUser(
			@Param("loginId") String loginId, 
			@Param("password") String password, 
			@Param("name") String name, 
			@Param("email") String email, 
			@Param("birthday") LocalDate birthday, 
			@Param("gender") String gender,
			@Param("role") String role);
}
