package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Scene;
import model.FormOfGovernment;
import model.App_Model;
import model.GovernedRegion;
import view.App_View;

// 0
public class App_Controller {

	private App_Model model;
	private App_View view;

	// Speichert Wert für gültige Textfelder
	private boolean countryValid = false;
	private boolean areaValid = false;

	// 0 Konstruktor
	public App_Controller(App_Model model, App_View view) {
		this.model = model;
		this.view = view;

		// *** HANDLING COUNTRY ***
		
		// SZENEN Wechsel von App_View zu CountryView (CREATE COUNTRY)
		// Wenn in der App_View der Create Button gedrückt wird,
		// soll die Sczene gewechselt werden zu der CountryView
		view.getBtnCreateCountry().setOnAction(event -> {
			view.getStage().setScene(getCountryScene());
		});

		// DELETE COUNTRY in App_View in der Country TableView
		view.getBtnDeleteCountry().setOnAction(e -> { // Holt den Button und setzt in durch das Event unter Aktion
			// Holt das ausgewählt Item in der TableView
			GovernedRegion selectedItem = view.getTableView().getSelectionModel().getSelectedItem();
			view.getTableView().getItems().remove(selectedItem);
		});

		// Szenen Wechsel von App_View zu UpdateView (UPDATE COUNTRY)
		// und das in der TableView ausgewählte Objekt sollt mitgenommen werden
		// und in die TextFelder der UpdateView eingefügt werden
		view.getBtnUpdateCountry().setOnAction(event -> {
//			GovernedRegion selectedItem = view.getTableView().getSelectionModel().getSelectedItem();
			String country = view.getTableView().getColumns().get(0).getCellObservableValue(0).getValue().toString();
			String area = view.getTableView().getColumns().get(0).getCellObservableValue(1).getValue().toString();
			String population = view.getTableView().getColumns().get(0).getCellObservableValue(2).getValue().toString();
			String formOfGovernment = view.getTableView().getColumns().get(0).getCellObservableValue(3).getValue()
					.toString();
			// Die aus der TableView geholten Werte in die TextFelder einfügen
			view.getUpdateView().getUpdateTxtCountry().setText(country);
			view.getUpdateView().getUpdateTxtCountry().setText(area);
			view.getUpdateView().getUpdateTxtCountry().setText(population);
			view.getUpdateView().getUpdateTxtCountry().setText(formOfGovernment);

			view.getStage().setScene(getUpdateScene());
		});

		// *** COUNTRY VIEW ***
		// BUTTON CANCEL
		view.getCountryView().getBtnCancel().setOnAction(event -> {
			view.getStage().setScene(getMainScene());
		});
		
		// *** UPDATE VIEW ***
		// SAVE COUNTRY - Event handler for the button in App_View
		// Wenn der Create Button (btnCreate) gedrückt wird, ruft der Controller die
		// Methode createNewCountry auf
		view.getCountryView().getBtnSave().setOnAction(this::createNewCountry);

		// (CANCEL UPDATEVIEW) SZENEN Wechsel von UpdateView zu App_View 
		// Wenn in der UpdateView der Cancel Button gedrückt wird,
		// soll die Sczene gewechselt werden zu der Geo_View
		view.getUpdateView().getBtnUpdateCancel().setOnAction(event -> {
			view.getStage().setScene(getMainScene());
		});
		

		// ** HANDLING STATE ***
		
		// CANCEL STATEVIEW) SZENEN Wechsel von StateView zu App_View
		view.getStateView().getBtnCancelState().setOnAction(event -> {
			view.getStage().setScene(getMainScene());
		});

		// SZENEN Wechsel von App_View zu StateView (CREATE STATE)
		// Wenn in der App_View der Create Button gedrückt wird,
		// soll die Sczene gewechselt werden zu der CountryView
		view.getBtnCreateState().setOnAction(event -> {
			view.getStage().setScene(getStateScene());
		});
		
		// DELETE STATE in App_View in der State TableView
		view.getBtnDeleteState().setOnAction(e -> { // Holt den Button und setzt in durch das Event unter Aktion
			GovernedRegion selectedItem = view.getStateTableView().getSelectionModel().getSelectedItem();
			view.getStateTableView().getItems().remove(selectedItem);
		});

//		// Szenen Wechsel von der UpdateViewState zur App_View (STATE)
//		view.getUpdateViewState().getBtnUpdateCancelState().setOnAction(event -> {
//			view.getStage().setScene(getMainScene());
//		});

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
		view.getBtnDeleteCountry().disableProperty()
				.bind(Bindings.isEmpty(view.getTableView().getSelectionModel().getSelectedItems()));
		/**
		 * 3 Wenn kein Zeile in der TableView angewählt ist "DEAKTIVIERE" den UPDATE
		 * Button (DISABLED)
		 */
		view.getBtnUpdateCountry().disableProperty()
				.bind(Bindings.isEmpty(view.getTableView().getSelectionModel().getSelectedItems()));

		// ADDITIONAL FUNCTIONALITY
		/**
		 * 3 Wenn kein Zeile in der TableView der States angewählt ist "DEAKTIVIERE" den
		 * DELETE Button der States (DISABLED)
		 */
		view.getBtnDeleteState().disableProperty()
				.bind(Bindings.isEmpty(view.getStateTableView().getSelectionModel().getSelectedItems()));
		/**
		 * 3 Wenn kein Zeile in der TableView der States angewählt ist "DEAKTIVIERE" den
		 * UPDATE Button der States (DISABLED)
		 */
		view.getBtnUpdateState().disableProperty()
				.bind(Bindings.isEmpty(view.getStateTableView().getSelectionModel().getSelectedItems()));

		// Event handler for the model's ObservableList requires a ListChangeListener.
		// To make generics happy, we have to cast our lambda: what kind of data do we
		// have?S
		// Note: May contain multiple changes - hence, the loop!
		model.getAllData().addListener((ListChangeListener<GovernedRegion>) c -> {
			while (c.next()) {
				view.getTableView().scrollTo(c.getFrom());
			}
		});
	}

	// 3 Methoden, um Eingabe zu validieren mit ChangeListener
	/**
	 * Muss ein String sein, zwischen 3 und 50 Zeichen, nur Letters keine
	 * Zeichen/Zahlen
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

			// wenn kein Integer
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
		view.getCountryView().getBtnSave().setDisable(!valid);
	}

	// Methode um die Country Szene aus der App_View zu holen
	private Scene getCountryScene() {
		return view.getCountryScene();

	}

	// Methode um die State Szene aus der App_View zu holen
	private Scene getStateScene() {
		return view.getStateScene();
	}

	// Methode um die Country Szene aus der App_View zu holen
	private Scene getMainScene() {
		return view.getMainScene();
	}

	private Scene getUpdateScene() {
		return view.getUpdateScene();
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
		double area = Integer.parseInt(view.getCountryView().getTxtArea().getText());
		int population = Integer.parseInt(view.getCountryView().getTxtPopulation().getText());
		// 3. Hole den ausgewählten Wert der ComboBox
		FormOfGovernment formOfGovernment = view.getCountryView().getCmbFormOfGov().getValue();
		// 4. Überprüfen das Kontrollelemente nicht leer sind
		if (name != null && area != 0 && formOfGovernment != null) {
			// 5
			model.createNewCountry(name, area, population, formOfGovernment);
			view.setStatus("Country Objekt hinzugefügt"); // Aktualisiert Status
			view.getCountryView().reset(); // Setzt die Eingaben in den Kontrollelementen zurück
		} else {
			// Eigentlich unnötig, Create Button ist disabled wenn Felder leer sind
			view.setStatus("Country Objekt nicht hinzugefügt");
		}

	}

	// CREATE STATE
	private void createNewState(ActionEvent event) {
		String name = view.getStateView().getTxtState().getText();
		double area = Integer.parseInt(view.getStateView().getTxtAreaState().getText());
		int popluation = Integer.parseInt(view.getStateView().getTxtPopulationState().getText());
	}

//	/**
//	 * UPDATE TODO: 
//	 * Die Methode soll das GUI updaten(die TableView) 
//	 * INPUT: 
//	 * OUTPUT:
//	 */
	public void updateView() {
		// Finally, attach the tableView to the ObservableList of data
		view.getTableView().setItems(model.getAllData());

	}

}
