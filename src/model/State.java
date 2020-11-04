package model;

/*
 *MINIMUM REQUIRMENTS: (MR)
 *Concrete class State: A dependent GovernedRegion that is part of a Country.
 *Additional attributes include at least the Country that the State belongs to.
 */

public class State extends GovernedRegion {

	// X (MR)
	protected Country myCountry;

	// Konstruktor (MR)String
	public State(String name, double area, int population, FormOfGovernment formOfGovernment, Country myCountry) {
		super(name, area, population, formOfGovernment);
		this.myCountry = myCountry;
	}

	// Getter für myCountry (MR)
	public Country getMyCountry() {
		return myCountry;
	}

	// Setter für myCountry (MR)
	public void setMyCountry(Country myCountry) {
		this.myCountry = myCountry;
	}

}
