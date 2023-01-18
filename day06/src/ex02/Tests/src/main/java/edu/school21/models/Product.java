package edu.school21.models;

public class Product {
	private Long id;
	private String name;
	private Long price;

	public Product(Long id, String name, Long price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	} 
}
