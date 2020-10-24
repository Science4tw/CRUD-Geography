package model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 *MINIMUM REQUIRMENTS: (MR)
 *-	Your user interface allows the user to Create, Read, Update and Delete
 *	(CRUD) objects in the class hierarchy
 *	- Your user interface prevents basic data entry errors, like empty names
 *- Objects that the user has created are maintained in an ArrayList
 */

// 0
public class Geo_Model {

	// 2 (MR)
	private static ObservableList<GovernedRegion> allData = FXCollections.observableArrayList();

	// CREATE Country
	// Fügt / speichert der Liste ein neu erzeugtes Country Objekts hinzu (MR)
	public static void createNewCountry(String name, FormOfGovernment fOfGov, int area) {
		allData.add(new Country(name, fOfGov, area));

	}

	// CREATE State
	// Fügt / speichert der Liste ein neu erzeugtes State Objekts hinzu (MR)
	public static void createNewState(String name, FormOfGovernment formOfGovernment, int area, Country myCountry) {
		allData.add(new State(name, formOfGovernment, area, myCountry));
	}

	// Getter für ObservableList<GovernedRegion> allData (MR)
	public ObservableList<GovernedRegion> getAllData() {
		return this.allData;
	}

	// Um nach dem Namen eines Countrys zu holen
	public static Country getCountryByName(String name) {
		for (GovernedRegion govReg : allData) {
			if (govReg instanceof Country && govReg.getName() != null && govReg.getName().equals(name)) {
				return (Country) govReg;
			}
		}
		return null;
	}

	// Um nach dem Namen eines States zu holen
	public static State getStateByName(StringProperty name) {
		for (GovernedRegion govReg : allData) {
			if (govReg instanceof State && govReg.getName() != null && govReg.getName().equals(name)) {
				return (State) govReg;
			}
		}
		return null;
	}
}
