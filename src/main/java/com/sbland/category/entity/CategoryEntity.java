package com.sbland.category.entity;

import com.sbland.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "category")
public class CategoryEntity extends BaseEntity {
	@Id
	private int id;
	private String name;
	private int right_value;
	private int depth;

}
