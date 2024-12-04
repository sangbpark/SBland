package com.sbland.user.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
	private Long id;
	private String login_id;
	private String email;
	private String name;
	private LocalDate birthday;
	private String gender;
	private String role;
	private LocalDateTime updated_at;
	private LocalDateTime created_at;
}
