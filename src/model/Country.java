package model;

import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/*
 * MINIMUM REQUIRMENTS: (MR)
 * Concrete class Country: An INDEPENDENT GovernedRegion. Additional
 * attributes include at least a list of States that belong to the country, etc.
 */

public class Country extends GovernedRegion {

	// Konstruktor
	public Country(String name, FormOfGovernment formOfGovernment, int area) {
		super(name, formOfGovernment, area);
	}

	// X (MR)
	private ArrayList<State> states;

	// Getter für states (MR)
	public ArrayList<State> getStates() {
		return states;
	}

	// Setter für states (MR)
	public void setStates(ArrayList<State> states) {
		this.states = states;
	}

	// HasState - Ist ein State in der Liste vorhanden?
	// Input:	String name
	// Output:	true / false
	public boolean hasState(String name) {
		for (State state : this.states) {
			if (state.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	// ADD State
	// Beschreibung: Fügt der Liste mit State Objekten ein State Objekt hinzu
	// INPUT:	State Objekt
	// Ouput:	Keinen
	public void addState(State state) {
		this.states.add(state);
	}
}
