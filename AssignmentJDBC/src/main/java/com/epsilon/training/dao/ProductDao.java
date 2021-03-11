package com.epsilon.training.dao;

import java.util.List;

import com.epsilon.training.entity.Product;

public interface ProductDao {

	public void addProduct(Product product) throws DaoException;
	
	public Product getProduct(int id) throws DaoException;
	
	public void updateProduct(Product product) throws DaoException;
	
	public void deleteProduct(int id) throws DaoException;
	
	public List<Product> getAll() throws DaoException;
	
	public List<Product> getByPriceRange(double min, double max) throws DaoException;

	public List<Product> getByBrand(String brand) throws DaoException;

	public List<Product> getByCategory(String category) throws DaoException;
	
}
