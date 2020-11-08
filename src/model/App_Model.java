package model;

import java.util.ArrayList;
import java.util.List;

import abstractClasses.Model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FormOfGovernment;
import model.State;
import model.Country;

/*
 *MINIMUM REQUIRMENTS: (MR)
 *-	Your user interface allows the user to Create, Read, Update and Delete
 *	(CRUD) objects in the class hierarchy
 *	- Your user interface prevents basic data entry errors, like empty names
 *- Objects that the user has created are maintained in an ArrayList
 */

// 0
public class App_Model extends Model {

	// 2 (MR)
	private static ObservableList<GovernedRegion> governedRegions = FXCollections.observableArrayList();
	private static ObservableList<Country> countries = FXCollections.observableArrayList();
	private static ObservableList<State> states = FXCollections.observableArrayList();
	
	
	// Konstruktor
	public App_Model () {
		
	}

	// CREATE Country
	// Fügt / speichert der Liste ein neu erzeugtes Country Objekts hinzu (MR)
	public static void createNewCountry(String name, double area, int population, FormOfGovernment formOfGovernment, ObservableList<State> myStates) {
		Country country = new Country(name, area, population, formOfGovernment, myStates);
		governedRegions.add(country);
		countries.add(country);

	}

	// CREATE State
	// Fügt / speichert der Liste ein neu erzeugtes State Objekts hinzu (MR)
	public static void createNewState(String name, double area, int population, FormOfGovernment formOfGovernment,
			Country myCountry) {
		State state = new State(name, area, population, formOfGovernment, myCountry);
		governedRegions.add(state);
		states.add(state);
//		getMyStates().add(state);

	}
	
	// UPDATE COUNTRY
	

	// Getter für ObservableList<GovernedRegion> governedRegions (MR)
	public ObservableList<GovernedRegion> getGovernedRegions() {
		return governedRegions;
	}

	public static ObservableList<State> getStates() {
		return states;
	}

//	public ObservableList<GovernedRegion> getStates(){
//		return getStates();
//	}
//	
	public static void setCountries(ObservableList<Country> countries) {
		App_Model.countries = countries;
	}
	
	public static void setstates(ObservableList<State> states) {
		App_Model.states = states;
	}

	public static void setgovernedRegions(ObservableList<GovernedRegion> governedRegions) {
		App_Model.governedRegions = governedRegions;
	}

	// Um nach dem Namen eines Countrys zu holen
	public static Country getCountryByName(String name) {
		for (Country country : countries) {
			if (country instanceof Country && country.getName() != null && country.getName().equals(name)) {
				return country;
			}
		}
		return null;
	}

	// Um nach dem Namen eines States zu holen
	public static State getStateByName(StringProperty name) {
		for (State state : states) {
			if (state instanceof State && state.getName() != null && state.getName().equals(name)) {
				return (State) state;
			}
		}
		return null;
	}


	public ObservableList<Country> getCountries() {
		
		return countries;
	}



	
	

}
