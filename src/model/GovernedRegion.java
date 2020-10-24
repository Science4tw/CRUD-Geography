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
	private FormOfGovernment formOfGovernment;
	private int area;
//	private Integer population;

	// Konstruktor
	public GovernedRegion(String name, FormOfGovernment formOfGovernment, int area) {
		this.name = name;
		this.formOfGovernment = formOfGovernment;
		this.area = area;

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
	public int getArea() {
		return this.area;
	}

	// Setter für area (MR)
	public void setArea(int area) {
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

}
