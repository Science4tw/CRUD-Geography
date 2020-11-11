package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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

	// AF
	private static String CountrylistFile = "C:/ProgProjekte/00 Software Engineering Projects/CRUD-Geography/src/model/SE_CountryList.csv";
	private static String StatelistFile = "C:/ProgProjekte/00 Software Engineering Projects/CRUD-Geography/src/model/SE_StateList.csv";
	private static String SEPARATOR = ";";

	// Konstruktor
	public App_Model() {

	}

	// CREATE Country
	// Fügt / speichert der Liste ein neu erzeugtes Country Objekts hinzu (MR)
	public static void createNewCountry(String name, double area, int population, FormOfGovernment formOfGovernment,
			ObservableList<State> myStates) {
		Country country = new Country(name, area, population, formOfGovernment, myStates);
		governedRegions.add(country);
		countries.add(country);

	}

	// CREATE Country (OHNE LISTE MIT STATES)
	// Fügt / speichert der Liste ein neu erzeugtes Country Objekts hinzu (MR)
	public static void createNewCountry(String name, double area, int population, FormOfGovernment formOfGovernment) {
		Country country = new Country(name, area, population, formOfGovernment);
		governedRegions.add(country);
		countries.add(country);

	}

	// CREATE State
	// Fügt / speichert der Liste ein neu erzeugtes State Objekts hinzu (MR)
	public static void createNewState(String name, double area, int population, FormOfGovernment formOfGovernment,
			Country myCountry) {
		State state = new State(name, area, population, formOfGovernment, myCountry);
		governedRegions.add(state);
		myCountry.addState(state);
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

	// Methode um ein Excel-File (CSV-File) einzulesen

	public void readCountries() {

		File countriesFile = new File(CountrylistFile);

		String data = "";
		// Reader wird initilisiert um File zu lesen
		try (BufferedReader fileIn = new BufferedReader(new FileReader(countriesFile))) {

			// Loop wird erstellt um Zeilen zu lesen und in Observable list zu speichern
			String line = fileIn.readLine();
			while (line != null) {
				Country country = readCountry(line);
				governedRegions.add(country);
				countries.add(country);
				line = fileIn.readLine();
			}
		} catch (Exception e) {
			data = e.getClass().toString();
			e.printStackTrace();
		}

	}

	// die eingelesenen Zeilen werden zum Country Objekt gemacht
	private Country readCountry(String line) {
		String[] attributes = line.split(SEPARATOR);
		String name = attributes[0];
		double area = Double.parseDouble(attributes[1]);
		int population = Integer.parseInt(attributes[2]);
		FormOfGovernment formOfGovernment = FormOfGovernment.valueOf(attributes[3]);
		Country myCountry = App_Model.getCountryByName(name);
		if (myCountry == null) {
			myCountry = new Country(name, area, population, formOfGovernment);
		}

		return myCountry;
	}

	public void readStates() {

		File statesFile = new File(StatelistFile);

		String data = "";
		// Reader wird initilisiert um File zu lesen
		try (BufferedReader fileIn = new BufferedReader(new FileReader(statesFile))) {
			// Loop wird erstellt um Zeilen zu lesen und in Observable list zu speichern
			String line = fileIn.readLine();

			while (line != null) {
				State state = readState(line);
				governedRegions.add(state);
				states.add(state);
				line = fileIn.readLine();
			}

		} catch (Exception e) {
			data = e.getClass().toString();
			e.printStackTrace();
		}

	}

	// die eingelesenen Zeilen werden zum Country Objekt gemacht
	private State readState(String line) throws Exception {
		String[] attributes = line.split(SEPARATOR);
		String name = attributes[0];
		String countryName = attributes[4];
		double area = Double.parseDouble(attributes[1]);
		int population = Integer.parseInt(attributes[2]);
		FormOfGovernment formOfGovernment = FormOfGovernment.valueOf(attributes[3]);
		Country myCountry = App_Model.getCountryByName(countryName);

		if (myCountry == null) {
			throw new Exception();
		}

		State state = new State(name, area, population, formOfGovernment, myCountry);

		return state;
	}
	public void saveCountries() {

		File countryFile = new File(CountrylistFile);

		try (Writer out = new FileWriter(countryFile)) {
			for (Country country : countries) {
				String line = writeCountry(country);
				out.write(line);
				
			}
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String writeCountry(GovernedRegion country) {
		String line = country.getName() + SEPARATOR + country.getArea() + SEPARATOR + country.getPopulation()
				+ SEPARATOR + country.getFormOfGovernment() + "\n";
		return line;
	}
	public void saveStates() {

		File stateFile = new File(StatelistFile);

		try (Writer out = new FileWriter(stateFile)) {
			for (State state : states) {
				String line = writeState(state);
				out.write(line);
				
			}
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String writeState(State state) {
		String line = state.getName() + SEPARATOR + state.getArea() + SEPARATOR + state.getPopulation()
				+ SEPARATOR + state.getFormOfGovernment() + SEPARATOR + state.getMyCountry() + "\n";
		return line;
	}
}
