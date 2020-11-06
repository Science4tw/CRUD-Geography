package model;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 * MINIMUM REQUIRMENTS: (MR)
 * Concrete class Country: An INDEPENDENT GovernedRegion. Additional
 * attributes include at least a list of States that belong to the country, etc.
 */

public class Country extends GovernedRegion {

	// X (MR)
	private static ObservableList<State> myStates = FXCollections.observableArrayList();

	// Konstruktor
	public Country(String name, double area, int population, FormOfGovernment formOfGovernment) {
		super(name, area, population, formOfGovernment);
		this.myStates = myStates;
	}

	// Getter für states (MR)
	public static ObservableList<State> getStates() {
		return myStates;
	}

	// Setter für states (MR)
	public void setStates(ObservableList<State> states) {
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

	public ObservableList<State> getMyStates() {
		return myStates;
	}

	public void setMyStates(ObservableList<State> myStates) {
		this.myStates = myStates;
	}

	// ADD State
	// Beschreibung: Fügt der Liste mit State Objekten ein State Objekt hinzu
	// INPUT: State Objekt
	// Ouput: Keinen
	public void addState(State state) {
		this.myStates.add(state);
	}
}
