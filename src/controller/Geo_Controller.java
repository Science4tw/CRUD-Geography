package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import model.FormOfGovernment;
import model.Geo_Model;
import model.GovernedRegion;
import view.Geo_View;


// 0
public class Geo_Controller {

	private Geo_Model model;
	private Geo_View view;
	
	//Speichert Wert für gültige Textfelder
	private boolean countryValid = false;
	private boolean areaValid = false;

	// 0 Konstruktor
	public Geo_Controller(Geo_Model model, Geo_View view) {
		this.model = model;
		this.view = view;

		// CREATE COUNTRY - Event handler for the button
		// Wenn der Create Button (btnCreate) gedrückt wird, ruft der Controller die
		// Methode createNewCountry auf
		view.getCountryView().getBtnCreate().setOnAction(this::createNewCountry);

		// DELETE COUNTRY
		view.getCountryView().getBtnDelete().setOnAction(e -> {
			GovernedRegion selectedItem = view.getCountryView().getTableView().getSelectionModel().getSelectedItem();
			view.getCountryView().getTableView().getItems().remove(selectedItem);
		});

		// ADDITIONAL FUNCTIONALITY
		/**
		 * 3 ChangeListener Wenn kein kein Integer in Area oder kein String in
		 * Countryname, dann Farbe rot, sonst Farbe grün
		 */

		view.getCountryView().getTxtCountry().textProperty().addListener((obserable, oldValue, newValue) -> {
			validateCountry(newValue);
		});

		view.getCountryView().getTxtArea().textProperty().addListener((obserable, oldValue, newValue) -> {
			validateArea(newValue);
		});

		// ADDITIONAL FUNCTIONALITY
		/**
		 * 3 Wenn kein Zeile in der TableView angewählt ist "DEAKTIVIERE" den DELETE
		 * Button (DISABLED)
		 */
		view.getCountryView().getBtnDelete().disableProperty()
				.bind(Bindings.isEmpty(view.getTableView().getSelectionModel().getSelectedItems()));

		// Event handler for the model's ObservableList requires a ListChangeListener.
		// To make generics happy, we have to cast our lambda: what kind of data do we
		// have?S
		// Note: May contain multiple changes - hence, the loop!
		model.getAllData().addListener((ListChangeListener<GovernedRegion>) c -> {
			while (c.next()) {
				view.getCountryView().getTableView().scrollTo(c.getFrom());
			}
		});
	}

	// 3 Methoden, um Eingabe zu validieren mit ChangeListener
	/**
	 * Muss ein String sein, zwischen 3 und 50 Zeichen, nur Letters keine Zeichen/Zahlen
	 */

	private void validateCountry(String newValue) {
		boolean valid = false;
		String country = newValue;
		Pattern pattern = Pattern.compile("[a-zA-Z]*");
		Matcher m = pattern.matcher(country);
		boolean alphabetic = m.matches();
		if (country.length() >= 3 && country.length() <= 50 && alphabetic) {
			valid = true;
		}

		// Farben neutralisieren
		view.getCountryView().getTxtCountry().getStyleClass().remove("countryNotOk");
		view.getCountryView().getStyleClass().remove("countryOk");

		// Farben setzen, rot = invalid, grün = valid
		if (valid) {
			view.getCountryView().getTxtCountry().getStyleClass().add("countryOk");
		} else {
			view.getCountryView().getTxtCountry().getStyleClass().add("countryNotOk");
		}
		// Speichert true/false-Wert für Button aktivieren
		countryValid = valid;
		// Aktiviert Button, wenn alles korrekt
		enableCreateButton();
	}

	/**
	 * Eingabe in Textfeld Area uss ein Integer sein: > 0, <= Max_Value
	 */
	private void validateArea(String newValue) {
		boolean valid = false;

		try {
			int area = Integer.parseInt(newValue);
			if (area > 0 && area <= Integer.MAX_VALUE)
				valid = true;
				
		//wenn kein Integer
		} catch (NumberFormatException e) { 
			valid = false;
		}

		// Farben neutralisieren
		view.getCountryView().getTxtArea().getStyleClass().remove("areaNotOk");
		view.getCountryView().getTxtArea().getStyleClass().remove("areaOk");
		// Farben setzen, rot = invalid, grün = valid
		if (valid) {
			view.getCountryView().getTxtArea().getStyleClass().add("areaOk");

		} else {
			view.getCountryView().getTxtArea().getStyleClass().add("areaNotOk");
		}
		
		// Speichert true/false-Wert für Button aktivieren
		areaValid = valid;
		// Aktiviert Button, wenn alles korrekt
		enableCreateButton();
	}

	
	/*
	 * Aktiviert Button, wenn beide Felder korrekt ausgefüllt
	 */
	private void enableCreateButton() {
		boolean valid = countryValid & areaValid;
		view.getCountryView().getBtnCreate().setDisable(!valid);
	}

	
	// CREATE COUNTRY
	// TODO: Exceptions(Text in Area Attribut löst Exception aus)
	// INPUT: Event
	// OUTPUT: Keinen
	// Beschreibung: Siehe Nummerierung (Allgemein:
	// Werte aus View holen,
	// Mit diesen Werten im Model Objekt erzeugen
	private void createNewCountry(ActionEvent event) {
		// 1. Lese den Wert des Textfelds mit den Namen des Landes aus
		String name = view.getCountryView().getTxtCountry().getText();
		// 2. Lese den Wert des Textfelds mit der Fläche des Landes aus
		int area = Integer.parseInt(view.getCountryView().getTxtArea().getText());
		// 3. Hole den ausgewählten Wert der ComboBox
		FormOfGovernment fOfGov = view.getCountryView().getCmbFormOfGov().getValue();
		// 4. Überprüfen das Kontrollelemente nicht leer sind
		if (name != null && area != 0 && fOfGov != null) {
			// 5
			model.createNewCountry(name, fOfGov, area);
			view.setStatus("Country Objekt hinzugefügt"); // Aktualisiert Status
			view.getCountryView().reset(); // Setzt die Eingaben in den Kontrollelementen zurück
		} else {
			// Eigentlich unnötig, Create Button ist disabled wenn Felder leer sind
			view.setStatus("Country Objekt nicht hinzugefügt");
		}

	}

	// DELETE COUNTRY
	// Bebschreibung: X
	// TODO: EXCEPTIONS
	// INPUT: X
	// OUTPUT: X

//	/**
//	 * UPDATE TODO: 
//	 * Die Methode soll das GUI updaten(die TableView) 
//	 * INPUT: 
//	 * OUTPUT:
//	 */
//	public void updateView() {
//		// Finally, attach the tableView to the ObservableList of data
//		view.getCountryView().getTableView().setItems(model.getAllData());
//
//	}

}
