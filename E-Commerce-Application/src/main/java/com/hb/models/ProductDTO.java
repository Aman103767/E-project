package com.hb.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
	private Integer productId;
	private String imagePath;
	private String productName;
	private Double price;
	private String dimension;
	private String specification;
	private String manufacturer;
	private Integer quantity;
	private String categoryName;

}
