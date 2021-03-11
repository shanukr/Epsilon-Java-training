package com.epsilon.training.entity;

import lombok.Data;

@Data
public class Product {
	private int id;
	private String name;
	private String brand;
	private String description;
	private String category;
	private String quantityPerUnit;
	private double discount;
	private double unitPrice;
	private String picture;
	
}
