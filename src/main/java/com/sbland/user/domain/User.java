package com.sbland.user.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Builder(toBuilder=true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private Long id;
	private String loginId;
	private String email;
	private String password;
	private String name;
	private LocalDate birthday;
	private String gender;
	private String role;
	private LocalDateTime updatedAt;
	private LocalDateTime createdAt;
}
