package com.green.cho.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TestEntity {
	
	@Id
	private long no;
	
	private String name;

}
