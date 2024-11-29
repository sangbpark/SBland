package com.sbland.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder= true)
public abstract class BaseEntity {
	@Column(name = "created_at")
	@CreationTimestamp
    private LocalDateTime createdAt;
	
	@Column(name = "updated_at")
	@UpdateTimestamp
    private LocalDateTime updatedAt;
}