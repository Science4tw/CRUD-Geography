package model;

import java.util.ArrayList;

/*
 * MINIMUM REQUIRMENTS: (MR)
 * Concrete class Country: An INDEPENDENT GovernedRegion. Additional
 * attributes include at least a list of States that belong to the country, etc.
 */

public class Country extends GovernedRegion {

	// Konstruktor
	public Country(String name, double area, int population, FormOfGovernment formOfGovernment) {
		super(name, area, population, formOfGovernment);
	}

	// X (MR)
	private static ArrayList<State> myStates;

	// Getter für states (MR)
	public ArrayList<State> getStates() {
		return this.myStates;
	}

	// Setter für states (MR)
	public void setStates(ArrayList<State> states) {
		this.myStates = states;
	}

	// HasState - Ist ein State in der Liste vorhanden?
	// Input: String name
	// Output: true / false
	public boolean hasState(String name) {
		for (State state : this.myStates) {
			if (state.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	// ADD State
	// Beschreibung: Fügt der Liste mit State Objekten ein State Objekt hinzu
	// INPUT: State Objekt
	// Ouput: Keinen
	public void addState(State state) {
		this.myStates.add(state);
	}
}
