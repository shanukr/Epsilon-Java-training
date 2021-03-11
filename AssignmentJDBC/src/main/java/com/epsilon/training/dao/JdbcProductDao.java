package com.epsilon.training.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epsilon.training.entity.Product;
import com.epsilon.training.utils.DbUtil;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class JdbcProductDao implements ProductDao {

	@Override
	public void addProduct(Product p) throws DaoException {
		String sql = "insert into products values(null, ?, ?, ?, ?, ?, ?, ?, ?)";
		try(Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setString(1, p.getName());
			stmt.setString(2, p.getBrand());
			stmt.setString(3, p.getCategory());
			stmt.setString(4, p.getQuantityPerUnit());
			stmt.setString(5, p.getDescription());
			stmt.setString(6, p.getPicture());
			stmt.setDouble(7, p.getUnitPrice());
			stmt.setDouble(8, p.getDiscount());
			
			stmt.execute(); 
			log.debug("1 ({})  record inserted successfully!", p.getName());
		} catch (Exception ex) {
			log.warn("Error - {}", ex.getMessage());
		}
	}

	@Override
	public Product getProduct(int id) throws DaoException {
		String sql = "select * from products where id = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					return createProduct(rs);
				}
			}
		} catch (Exception ex) {
			throw new DaoException(ex);
		}
		return null;
	}

	private Product createProduct(ResultSet rs) throws SQLException {
		Product p = new Product();
		p.setId(rs.getInt("id"));
		p.setName(rs.getString("name"));
		p.setBrand(rs.getString("brand"));
		p.setCategory(rs.getString("category"));
		p.setDescription(rs.getString("description"));
		p.setQuantityPerUnit(rs.getString("quantity_per_unit"));
		p.setPicture(rs.getString("picture"));
		p.setDiscount(rs.getDouble("discount"));
		p.setUnitPrice(rs.getDouble("unit_price"));
		return p;
	}

	@Override
	public void updateProduct(Product p) throws DaoException {
		
		String sql = "update products set name=?, brand=?, category=?, description=?, quantity_per_unit=?,"
				+ " picture=?, unit_price=?, discount=? where id=?";
		
		try(Connection conn = DbUtil.createConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);){
			
			stmt.setString(1, p.getName());
			stmt.setString(2, p.getBrand());
			stmt.setString(3, p.getCategory());
			stmt.setString(4, p.getDescription());
			stmt.setString(5, p.getQuantityPerUnit());
			stmt.setString(6, p.getPicture());
			stmt.setDouble(7, p.getUnitPrice());
			stmt.setDouble(8, p.getDiscount());
			stmt.setInt(9, p.getId());
			
		}
		catch(Exception ex) {
			throw new DaoException(ex);
		}
		
	}

	@Override
	public void deleteProduct(int id) throws DaoException {
		String sql = "delete from products where id= ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);){
			stmt.setInt(1, id);
			stmt.executeUpdate(sql);
			log.debug("Item deleted sucessfully");
		}catch (Exception ex) {
			throw new DaoException(ex);
		}
	}
	
	public List<Product> populateList() throws DaoException {
		String sql = "select * from products";
		List<Product> prodList = new ArrayList<>();
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);){
			
			try (ResultSet rs = stmt.executeQuery();) {
				while (rs.next()) {
					 Product p = new Product(); 
					 p.setId(rs.getInt("id"));
						p.setName(rs.getString("name"));
						p.setBrand(rs.getString("brand"));
						p.setCategory(rs.getString("category"));
						p.setDescription(rs.getString("description"));
						p.setQuantityPerUnit(rs.getString("quantity_per_unit"));
						p.setPicture(rs.getString("picture"));
						p.setDiscount(rs.getDouble("discount"));
						p.setUnitPrice(rs.getDouble("unit_price"));
						prodList.add(p);
				}
			}
		} catch (Exception ex) {
			throw new DaoException(ex);
		}
		return prodList;      
		}

	@Override
	public List<Product> getAll() throws DaoException {
		return populateList();
	}

	@Override
	public List<Product> getByPriceRange(double min, double max) throws DaoException {
		List<Product> p = populateList();
		List<Product> l = populateList();
		for(int i=0;i<p.size();i++) {
			if(min<p.get(i).getUnitPrice() && max>p.get(i).getUnitPrice()) {
				l.add(p.get(i));
			}
		}
		return l;
		
	}

	@Override
	public List<Product> getByBrand(String brand) throws DaoException {
		List<Product> p = populateList();
		List<Product> l = populateList();
		for(int i=0;i<p.size();i++) {
			if(brand.equals(p.get(i).getBrand()) ) {
				l.add(p.get(i));
			}
		} return l;
	}

	@Override
	public List<Product> getByCategory(String category) throws DaoException {
		List<Product> p = populateList();
		List<Product> l = populateList();
		for(int i=0;i<p.size();i++) {
			if(category.equals(p.get(i).getBrand()) ) {
				l.add(p.get(i));
			}
		} return l;
	}

}