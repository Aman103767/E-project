package com.hb.models;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer productId;
	private String imagePath;
	private String productName;
	private Double price;
	private String dimension;
	private String specification;
	private String manufacturer;
	private Integer quantity;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Category category = new Category();
}
