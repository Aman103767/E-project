package com.hb.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.hb.exceptions.AdminException;
import com.hb.models.Product;
import com.hb.models.ProductDTO;

public interface ProductService {

	public Product createProduct(ProductDTO product) throws AdminException;
    
	public String removeProduct(Integer productId) throws AdminException;
	
	public Product updateProduct(ProductDTO product) throws AdminException;
	
	public Product productById(Integer productId) throws AdminException;

	public List<Product> getAllProduct() throws AdminException;
	
	public List<Product> sortProductAsc(String field) throws AdminException;
	
	public Page<Product> findProductWithPagination(int offset, int pageSize);
	


}
