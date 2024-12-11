package com.sbland.user.mapper;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbland.user.domain.User;

@Mapper
public interface UserMapper {
	public User selectUserByLoginId(String loginId);
	
	public User selectUserById(Long id);
	
	public int updateUser(User user);
	
	public User selectUserByLoginIdAndName(
			@Param("loginId") String loginId, 
			@Param("name") String name);
	
	public User selectUserByUserIdAndPassword(
			@Param("id") Long id, 
			@Param("password") String password);
	
	public User selectUserByUserLoginIdAndPassword(
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
	
	public List<User> selectFindUserByNameAndEmail(
			@Param("name") String name, 
			@Param("email") String email);
	
	public int updateUserPasswordByUserNameAndLoginId(
			@Param("name") String name, 
			@Param("loginId") String loginId,
			@Param("password") String password);
}
