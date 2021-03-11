package com.epsilon.training.programs;


import java.util.InputMismatchException;
import java.util.List;

import com.epsilon.training.dao.DaoException;
import com.epsilon.training.dao.DaoFactory;
import com.epsilon.training.dao.ProductDao;
import com.epsilon.training.entity.Product;
import com.epsilon.training.utils.KeyboardUtil;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ProductManagerApplication {

	ProductDao dao;

	void start() throws DaoException {
		dao = DaoFactory.getProductDao();

		while (true) {
			menu();
			try {
				int choice = KeyboardUtil.getInt("Enter your choice: ");
				if (choice == 0) {
					System.out.println("Thank you and have a nice day.");
					break;
				}

				switch (choice) {
				case 1:
					acceptAndAddProductDetails();
					break;
				case 2:
					getProductWithId();
					break;
				case 3:
					acceptAndUpdateProduct();
					break;
				case 4:
					deleteProduct();
					break;
				case 5: 
					getAllProduct();
					break;
				case 6: 
					getProductbyPriceRange();
					break;
				case 7:
					getProductbyBrand();
					break;
				case 8:
					getProductbyCateogory();
					break;
				
				default:
					
					System.out.println("Invalid choice! Please try again.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid choice! Please try again.");
			}
		}
	}
	
	private void getProductbyCateogory() {
		try {
			String cateogory = KeyboardUtil.getString("Enter product category");
			dao.getByCategory(cateogory);
		} catch (DaoException e) {
			
			log.warn("there was an error retriving the products: ");
			log.warn(e.getMessage());
		}
	}

	private void getProductbyBrand() {
		try {
			String brand = KeyboardUtil.getString("Enter the brand: ");
			List<Product> list =	dao.getByBrand(brand);
			for(Product p: list) {
				log.debug("{}", p);
			}
		} catch (DaoException e) {
			log.warn("there was an error retriving the products :");
			log.warn(e.getMessage());
		}
	}

	private void getProductbyPriceRange() {
		try {
			double min = KeyboardUtil.getDouble("Enter min price: ");
			double max = KeyboardUtil.getDouble("Enter max price: ");
			List<Product> list =	dao.getByPriceRange(min, max);
			for(Product p: list) {
				log.debug("{}", p);
			}
		} catch (DaoException e) {
			log.warn("there was an error retriving the product :");
			log.warn(e.getMessage());
		}
		
	}

	private void getAllProduct() {
		try {
			List<Product> list = dao.getAll();
			for(Product p: list) {
				log.debug("{}", p);
			}
		} catch (DaoException e) {
			log.warn("there was an error retriving the product :");
			log.warn(e.getMessage());

		}
		
		
	}

	private void deleteProduct() {
		try {
			int id = KeyboardUtil.getInt("Enter the product id to be deleted: ");
			dao.deleteProduct(id);
		} catch (DaoException e) {
			log.warn("there was an error retriving the product :");
			log.warn(e.getMessage());
		}
	}

	private void acceptAndUpdateProduct() {
		 int id = KeyboardUtil.getInt("Enter the product id: ");
		
		try {
			Product p = dao.getProduct(id);
			if (p == null) {
				System.out.println("No product data found for id " + id);
			} else {
				// display product fields and ask for modification
				String input;
				input = getUserInput("Name", p.getName());
				p.setName(input);
				
				input = getUserInput("Brand", p.getBrand());
				p.setBrand(input);
				
				input = getUserInput("Category", p.getCategory());
				p.setCategory(input);
				
				input = getUserInput("Description", p.getDescription());
				p.setDescription(input);
				
				input = getUserInput("Quantity per unit", p.getQuantityPerUnit());
				p.setQuantityPerUnit(input);
				
				input = getUserInput("Picture", p.getPicture());
				p.setPicture(input);
				
				input = getUserInput("Unit price", p.getUnitPrice());
				p.setUnitPrice(Double.parseDouble(input));
				
				input = getUserInput("Discount", p.getDiscount());
				p.setDiscount(Double.parseDouble(input));
				
				log.debug("Product is {}", p);
				
			}
		} catch (DaoException e) {
			log.warn("There was an error - {}", e.getMessage());
		}

	}

	private String getUserInput(String fieldTitle, Object currVal) {
		String input;
		input = KeyboardUtil.getString(String.format("Enter %s: (%s) ", fieldTitle, currVal)).trim();
		if (input.equals("")) {
			return String.valueOf(currVal);
		}
		return input;
	}

	Product getProductWithId(){
		
		try {
			int id = KeyboardUtil.getInt("Enter Product id to retrieve: ");
			return dao.getProduct(id);
		} catch (DaoException e) {
			log.warn("there was an error retriving the product :");
			log.warn(e.getMessage());
		}
		return null;
		
	}

	private void acceptAndAddProductDetails() {

		try {
			// create variable for all product fields
			// accept value for each variable from the user
			
			int id = KeyboardUtil.getInt("Enter product name: ");
			String name = KeyboardUtil.getString("Enter product name: ");
			String description = KeyboardUtil.getString("Enter product description: ");
			String brand = KeyboardUtil.getString("Enter product brand: ");
			String category = KeyboardUtil.getString("Enter product category: ");
			String qunatityPerUnit = KeyboardUtil.getString("Enter quantity per unit: ");
			Double unitPrice = (double) KeyboardUtil.getInt("Enter per unit price: ");
			Double discount = (double) KeyboardUtil.getInt("Enter discount: ");
			
			Product p = new Product();
			p.setId(id);
			p.setName(name);
			p.setDescription(description);
			p.setBrand(brand);
			p.setCategory(category);
			p.setQuantityPerUnit(qunatityPerUnit);
			p.setUnitPrice(unitPrice);
			p.setDiscount(discount);
			
			dao.addProduct(p);
			System.out.println("New product added sucessfully");
		} catch (Exception ex) {
			log.warn("there was an error while trying to add a product");
			log.warn(ex.getMessage());
		}
	}

	void menu() {
		System.out.println("*** Main Menu ***");
		System.out.println("0. Exit");
		System.out.println("1. Add a new product");
		System.out.println("2. Retrieve a product by id");
		System.out.println("3. Modify details of a product");
		System.out.println("4. Remove product details");
		System.out.println("5. Get all products");
		System.out.println("6. Get Product by price range");
		System.out.println("7. Get Product by Brand");
		System.out.println("8. Get Product by Cateogory");
	}

	public static void main(String[] args) throws DaoException {
		new ProductManagerApplication().start();
	}

}

