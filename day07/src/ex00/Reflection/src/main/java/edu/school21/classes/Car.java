package edu.school21.classes;

import java.util.StringJoiner;

public class Car {
	private int horsepower;
	private String serialNumber;
 
	public Car() {
		serialNumber = "default";
	}
 
	public Car(int horsepower, String serialNumber) {
		this.horsepower = horsepower;
		this.serialNumber = serialNumber;
	}
 
	public void printSerialNumber() {
		System.out.println(serialNumber);
	}

	public void changeAll(int horsepower, String serialNumber) {
		this.horsepower = horsepower;
		this.serialNumber = serialNumber;
		System.out.println(this.toString());
	}
 
	@Override
	public String toString() {
		return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
				.add("horsepower=" + horsepower)
				.add("serialNumber='" + serialNumber + "'")
				.toString();
	}
}
