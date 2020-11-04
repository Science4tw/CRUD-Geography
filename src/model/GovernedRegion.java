   package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/*
 *MINIMUM REQUIRMENTS: (MR)
 *Abstract class GovernedRegion: Represents any physical area that has a
 *government. Attributes include area, population, form-of-government, and
 *possibly other information
 */

public abstract class GovernedRegion {

	// 1 (MR)
	private String name;
	private double area;
	private int population;
	private FormOfGovernment formOfGovernment;

	// Konstruktor
	public GovernedRegion(String name, double area, int population, FormOfGovernment formOfGovernment) {
		this.name = name;
		this.population = population;
		this.area = area;
		this.formOfGovernment = formOfGovernment;

	}

	// Getter für formOfGovernment (MR)
	public FormOfGovernment getFormOfGovernment() {
		return this.formOfGovernment;
	}

	// Setter für formOfGovernment (MR)
	public void setFormOfGovernment(FormOfGovernment formOfGovernment) {
		this.formOfGovernment = formOfGovernment;
	}

	// Getter für area (MR)
	public double getArea() {
		return this.area;
	}

	// Setter für area (MR)
	public void setArea(double area) {
		this.area = area;
	}

	// Getter für name (MR)
	public String getName() {
		return this.name;
	}

	// Setter für name (MR)
	public void setName(String name) {
		this.name = name;
	}

	public int getPopulation() {
		return this.population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

}
