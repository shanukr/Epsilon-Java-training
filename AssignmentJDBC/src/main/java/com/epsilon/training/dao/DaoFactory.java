package com.epsilon.training.dao;

import java.util.ResourceBundle;

public final class DaoFactory {

	private static final String discriminator;

	private DaoFactory() {
	}

	static {
		String envVal = System.getenv("DAO_IMPL");
		if (envVal == null) {
			ResourceBundle rb = ResourceBundle.getBundle("dao");
			discriminator = rb.getString("dao.impl");
		} else {
			discriminator = envVal;
		}
	}

	public static ProductDao getProductDao() {
		switch (discriminator.toUpperCase()) {
		case "DUMMY":
			//return new DummyProductDao();
		case "ARRAY":
			//return new ArrayProductDao();
		case "JDBC":
			return new JdbcProductDao();
		case "CSV":
			//return new CsvProductDao();
		case "SERIALIZED":
			//return new SerializedProductDao();
		case "MONGODB":
		default:
			throw new RuntimeException("Invalid discriminator");
		}
	}
}