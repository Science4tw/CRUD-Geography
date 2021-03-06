package controller;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import abstractClasses.Controller;
import commonClasses.Translator;
import mvc.ServiceLocator;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.WindowEvent;
import model.FormOfGovernment;
import model.App_Model;
import model.Country;
import model.GovernedRegion;
import model.State;
import view.App_View;

// 0
public class App_Controller extends Controller<App_Model, App_View> {

	ServiceLocator serviceLocator;

	// Speichert Wert für gültige Textfelder
	private boolean countryValid = false;
	private boolean areaValid = false;
	private boolean populationValid = false;
	private boolean formOfGovernmentValid = false;

	private boolean stateValid = false;
	private boolean stateAreaValid = false;
	private boolean statePopulationValid = false;
	private boolean stateCountryValid = false;

	// 0 Konstruktor
	public App_Controller(App_Model model, App_View view) {
		super(model, view);

		model.readCountries();
		model.readStates();

		// *** HANDLING COUNTRY ***

		// *** SZENEN WECHSEL ***
		// SZENEN Wechsel von App_View zu CountryView (CREATE COUNTRY)
		// Wenn in der App_View der Create Button gedrückt wird,
		// soll die Sczene gewechselt werden zu der CountryView
		view.getBtnCreateCountry().setOnAction(event -> {
			view.getStage().setScene(getCountryScene());
			view.getCountryView().getCmbFormOfGov().getStyleClass().add("comboboxNotOk");
		});

		// (CANCEL UPDATEVIEWCOUNTRY) SZENEN Wechsel von UpdateView zu App_View
		// Wenn in der UpdateView der Cancel Button gedrückt wird,
		// soll die Sczene gewechselt werden zu der Geo_View
		view.getUpdateView().getBtnUpdateCancel().setOnAction(event -> {
			view.getStage().setScene(getMainScene());
		});

		// (SAVE UPDATEVIEWCOUNTRY)
		view.getUpdateView().getBtnUpdateSave().setOnAction(event -> {
			view.getStage().setScene(getMainScene());
		});

		// *** COUNTRY VIEW ***
		// BUTTON CANCEL und Eingaben leeren
		view.getCountryView().getBtnCancel().setOnAction(event -> {
			view.getCountryView().reset();
			view.getStage().setScene(getMainScene());

		});

		// SZENEN WECHSEL: GOTO -> UPDATEVIEW from App_View
		// BUTTON: UPDATE COUNTRY
		view.getBtnUpdateCountry().setOnAction(event -> {
			view.getStage().setScene(getUpdateScene());
		});

		// *** STATE ***

		// SZENEN WECHSEL: GOTO -> StateView from App_View
		// BUTTON: UPDATE State
		view.getBtnUpdateState().setOnAction(event -> {
			view.getStage().setScene(getUpdateSceneState());
		});

		// SZENEN WECHSEL: GOTO -> App_View from UpdateViewState
		// BUTTON: UPDATE State
		view.getUpdateViewState().getBtnUpdateCancelState().setOnAction(event -> {
//			view.getUpdateViewState().reset();
			view.getStage().setScene(getMainScene());
		});

		// SZENEN WECHSEL: GOTO -> App_View from UpdateViewState
		// BUTTON: UPDATE State
		view.getUpdateViewState().getBtnUpdateSaveState().setOnAction(event -> {
			view.getStage().setScene(getMainScene());
		});

		// *** DELETE ***
		// DELETE COUNTRY in App_View in der Country TableView
		view.getBtnDeleteCountry().setOnAction(e -> { // Holt den Button und setzt in durch das Event unter Aktion
			// Holt das ausgewählt Item in der TableView
			GovernedRegion selectedItem = view.getTableView().getSelectionModel().getSelectedItem();
			Alert alertDeletCountry = new Alert(AlertType.CONFIRMATION);
			Translator t = ServiceLocator.getServiceLocator().getTranslator();
			alertDeletCountry.setTitle(t.getString("alert.alertDeletCountry.title"));
			alertDeletCountry.setHeaderText(t.getString("alert.alertDeletCountry.header"));

			Button okButton = (Button) alertDeletCountry.getDialogPane().lookupButton(ButtonType.OK);
			okButton.setText(t.getString("alert.buttonOk"));
			Button cancelButton = (Button) alertDeletCountry.getDialogPane().lookupButton(ButtonType.CANCEL);
			cancelButton.setText(t.getString("alert.buttonCancel"));

			Optional<ButtonType> result = alertDeletCountry.showAndWait();
			if (result.get() == ButtonType.OK) {
				view.getTableView().getItems().remove(selectedItem);
				view.setStatus(t.getString("statusLabel.deleteCountry"));
			} else {

			}

		});

		// Alert für MenuItem Shortcut
		view.getMenuHelpShortcuts().setOnAction(e -> {
			Alert shortcutinfo = new Alert(AlertType.INFORMATION);

			Translator t = ServiceLocator.getServiceLocator().getTranslator();
			shortcutinfo.setTitle(t.getString("alert.shortcutinfo.title"));
			shortcutinfo.setHeaderText(t.getString("alert.shortcutinfo.header"));
			shortcutinfo.setContentText(t.getString("alert.shortcutinfo.content"));

			shortcutinfo.showAndWait();

		});

		// UPDATE COUNTRY Teil 1
		// Die TableView wird permanent überwacht und die Werte der ausgewählten Zeile
		// werden immer in die UpdateViewCountry übertragen
		view.getTableView().getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
				// Check whether item is selected and set value of selected item to Label
				if (view.getTableView().getSelectionModel().getSelectedItem() != null) {

					GovernedRegion governedRegion = getTableView().getSelectionModel().getSelectedItem();
					String name = governedRegion.getName();

					double area = governedRegion.getArea();
					String areaString = Double.toString(area);

					int population = governedRegion.getPopulation();
					String populationString = Integer.toString(population);

					FormOfGovernment formOfGovernment = governedRegion.getFormOfGovernment();

					view.getUpdateViewCountry().getTxtUpdateCountry().setText(name);
					view.getUpdateViewCountry().getTxtUpdateArea().setText(areaString);
					view.getUpdateViewCountry().getTxtUpdatePopulation().setText(populationString);

					view.getUpdateViewCountry().getCmbFormOfGov().getSelectionModel().select(formOfGovernment);

					view.getMyStatesTableView().setItems(
							model.getCountryByName(view.getTableView().getSelectionModel().getSelectedItem().getName())
									.getMyStates());

				}
			}
		});

		// UPDATE COUNTRY TEIL 2 - Save Button unter Aktion setzen
		view.getUpdateViewCountry().getBtnUpdateSave().setOnAction(this::updateCountry);

		/**
		 * 
		 */

		// UPDATE STATE Teil 1
		// Die TableView wird permanent überwacht und die Werte der ausgewählten Zeile
		// werden immer in die UpdateViewState übertragen
		view.getStateTableView().getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
				// Check whether item is selected and set value of selected item to Label
				if (view.getStateTableView().getSelectionModel().getSelectedItem() != null) {

					State state = getStateTableView().getSelectionModel().getSelectedItem();

					String name = state.getName();

					double area = state.getArea();
					String areaString = Double.toString(area);

					int population = state.getPopulation();
					String populationString = Integer.toString(population);

					FormOfGovernment formOfGovernment = state.getFormOfGovernment();
					Country myCountry = state.getMyCountry();

					view.getUpdateViewState().getTxtUpdateState().setText(name);
					view.getUpdateViewState().getTxtUpdateAreaState().setText(areaString);
					view.getUpdateViewState().getTxtUpdatePopulationState().setText(populationString);

					view.getUpdateViewState().getCmbUpdateFormOfGovState().getSelectionModel().select(formOfGovernment);
					view.getUpdateViewState().getCmbMyCountry().getSelectionModel().select(myCountry);

				}
			}
		});

		// UPDATE STATE TEIL 2 - Save Button unter Aktion setzen
//		view.getUpdateViewState().getBtnUpdateSaveState().setOnAction(this::updateState);
		view.getUpdateViewState().getBtnUpdateSaveState().setOnAction(event -> {
			this.updateState(event);
			view.getMyStatesTableView().refresh();
		});

		// *** COUNTRY VIEW ***
		// SAVE (CREATE) COUNTRY - Event handler for the button in App_View
		// Wenn der Create Button (btnCreate) gedrückt wird, ruft der Controller die
		// Methode createNewCountry auf
		view.getCountryView().getBtnSave().setOnAction(this::createNewCountry);

		// ** HANDLING STATE ***

		// CANCEL STATEVIEW) SZENEN Wechsel von StateView zu App_View
		view.getStateView().getBtnCancelState().setOnAction(event -> {
			view.getStateView().reset();
			view.getStage().setScene(getMainScene());
		});

		// SZENEN Wechsel von App_View zu StateView (CREATE STATE)
		// Wenn in der App_View der Create Button gedrückt wird,
		// soll die Sczene gewechselt werden zu der CountryView
		view.getBtnCreateState().setOnAction(event -> {
			view.getStage().setScene(getStateScene());
			view.getStateView().getCmbMyCountry().getStyleClass().add("comboboxNotOk");

		});

		// DELETE STATE in App_View in der State TableView
		view.getBtnDeleteState().setOnAction(e -> { // Holt den Button und setzt in durch das Event unter Aktion
			GovernedRegion selectedItem = view.getStateTableView().getSelectionModel().getSelectedItem();
			Alert alertDeletState = new Alert(AlertType.CONFIRMATION);

			Translator t = ServiceLocator.getServiceLocator().getTranslator();
			alertDeletState.setTitle(t.getString("alert.alertDeletState.title"));
			alertDeletState.setHeaderText(t.getString("alert.alertDeletState.header"));

			Button okButton = (Button) alertDeletState.getDialogPane().lookupButton(ButtonType.OK);
			okButton.setText(t.getString("alert.buttonOk"));
			Button cancelButton = (Button) alertDeletState.getDialogPane().lookupButton(ButtonType.CANCEL);
			cancelButton.setText(t.getString("alert.buttonCancel"));

			Optional<ButtonType> result = alertDeletState.showAndWait();
			if (result.get() == ButtonType.OK) {
				view.getStateTableView().getItems().remove(selectedItem);
				view.setStatus(t.getString("statusLabel.deleteState"));

			} else {

			}

		});

		// SAVE COUNTRY - Event handler for the button in App_View
		// Wenn der Create Button (btnCreate) gedrückt wird, ruft der Controller die
		// Methode createNewCountry auf
		view.getStateView().getBtnCreateState().setOnAction(this::createNewState);

		/**
		 * 3 ChangeListener für Textfelder Area, Country, Population, State
		 */

		// ChangeListener für countryView
		view.getCountryView().getTxtCountry().textProperty().addListener((obserable, oldValue, newValue) -> {
			validateCountry(newValue);
		});

		view.getCountryView().getTxtArea().textProperty().addListener((obserable, oldValue, newValue) -> {
			validateArea(newValue);
		});

		view.getCountryView().getTxtPopulation().textProperty().addListener((obserable, oldValue, newValue) -> {
			validatePopulation(newValue);
		});

		view.getCountryView().getCmbFormOfGov().getSelectionModel().selectedItemProperty()
				.addListener((obserable, oldValue, newValue) -> {
					validateFormOfGovernment(newValue);
				});

		// ChangeListener für Update countryView
		view.getUpdateViewCountry().getTxtUpdateCountry().textProperty()
				.addListener((obserable, oldValue, newValue) -> {
					validateCountryUpdate(newValue);
				});

		view.getUpdateViewCountry().getTxtUpdateArea().textProperty().addListener((obserable, oldValue, newValue) -> {
			validateAreaUpdate(newValue);
		});

		view.getUpdateViewCountry().getTxtUpdatePopulation().textProperty()
				.addListener((obserable, oldValue, newValue) -> {
					validatePopulationUpdate(newValue);
				});

		// ChangeListener für stateView
		view.getStateView().getTxtState().textProperty().addListener((obserable, oldValue, newValue) -> {
			validateState(newValue);
		});

		view.getStateView().getTxtAreaState().textProperty().addListener((obserable, oldValue, newValue) -> {
			validateStateArea(newValue);
		});

		view.getStateView().getTxtPopulationState().textProperty().addListener((obserable, oldValue, newValue) -> {
			validateStatePopulation(newValue);
		});

		view.getStateView().getCmbmyCountry().getSelectionModel().selectedItemProperty()
				.addListener((obserable, oldValue, newValue) -> {
					validateStateCountry(newValue);
				});

		// ChangeListener für UpdatestateView
		view.getUpdateViewState().getTxtUpdateState().textProperty().addListener((obserable, oldValue, newValue) -> {
			validateStateUpdate(newValue);
		});

		view.getUpdateViewState().getTxtUpdateAreaState().textProperty()
				.addListener((obserable, oldValue, newValue) -> {
					validateStateAreaUpdate(newValue);
				});

		view.getUpdateViewState().getTxtUpdatePopulationState().textProperty()
				.addListener((obserable, oldValue, newValue) -> {
					validateStatePopulationUpdate(newValue);
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

		/**
		 * Buttons beim ersten Aufruf, leeren Feldern oder keiner Änderung deaktivieren
		 */
		view.getCountryView().getBtnSave().setDisable(true);
		view.getStateView().getBtnCreateState().setDisable(true);
		view.getUpdateViewCountry().getBtnUpdateSave().setDisable(true);
		view.getUpdateViewState().getBtnUpdateSaveState().setDisable(true);

		// Scrollt automatisch zum zugefügten Country/State
		model.getGovernedRegions().addListener((ListChangeListener<GovernedRegion>) c -> {
			while (c.next()) {
				view.getTableView().scrollTo(c.getFrom());
			}
		});

		// Scrollt automatisch zum zugefügten State
		model.getGovernedRegions().addListener((ListChangeListener<GovernedRegion>) c -> {
			while (c.next()) {
				view.getStateTableView().scrollTo(c.getFrom());
			}
		});

		// register ourselves to handle window-closing event
		view.getStage().setOnCloseRequest(evt -> {
			Alert closingAlert = new Alert(AlertType.CONFIRMATION);

			Translator t = ServiceLocator.getServiceLocator().getTranslator();
			closingAlert.setTitle(t.getString("alert.closingAlert.title"));
			closingAlert.setHeaderText(t.getString("alert.closingAlert.header"));

			Button okButton = (Button) closingAlert.getDialogPane().lookupButton(ButtonType.OK);
			okButton.setText(t.getString("alert.buttonOk"));
			Button cancelButton = (Button) closingAlert.getDialogPane().lookupButton(ButtonType.CANCEL);
			cancelButton.setText(t.getString("alert.buttonCancel"));

			closingAlert.showAndWait().filter(r -> r != ButtonType.OK).ifPresent(r -> evt.consume());
			model.saveCountries();
			model.saveStates();
		});

		// Doppelclick auf countryrow updateCountry
		view.getTableView().setRowFactory(tv -> {
			TableRow<Country> row = new TableRow<>();
			row.setOnMouseClicked(e -> {
				if (e.getClickCount() == 2 && (!row.isEmpty())) {
					view.getBtnUpdateCountry().fire();
				}
			});

			return row;
		});
		// Doppelclick auf state row updateState
		view.getStateTableView().setRowFactory(tv -> {
			TableRow<State> row = new TableRow<>();
			row.setOnMouseClicked(e -> {
				if (e.getClickCount() == 2 && (!row.isEmpty())) {
					view.getBtnUpdateState().fire();
				}
			});

			return row;
		});

		// set shortcut ctrl'c to create country
		view.getBtnCreateCountry().getScene().getAccelerators()
				.put(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN), new Runnable() {
					public void run() {
						view.getBtnCreateCountry().fire();
					}
				});

		// set shortcut ctrl'c to update country
		view.getBtnUpdateCountry().getScene().getAccelerators()
				.put(new KeyCodeCombination(KeyCode.U, KeyCombination.CONTROL_DOWN), new Runnable() {
					public void run() {
						view.getBtnUpdateCountry().fire();
					}
				});

		// set ctrl d to delete country
		view.getBtnDeleteCountry().getScene().getAccelerators()
				.put(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN), new Runnable() {
					public void run() {
						view.getBtnDeleteCountry().fire();
					}
				});

		// set shortcut ctrl shift c to create state button
		view.getBtnCreateState().getScene().getAccelerators().put(
				new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN),
				new Runnable() {
					public void run() {
						view.getBtnCreateState().fire();
					}
				});

		// set shortcut ctrl shift u to update state button
		view.getBtnUpdateState().getScene().getAccelerators().put(
				new KeyCodeCombination(KeyCode.U, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN),
				new Runnable() {
					public void run() {
						view.getBtnUpdateState().fire();
					}
				});

		// set shortcut ctrl shift d to delete state button
		view.getBtnDeleteState().getScene().getAccelerators().put(
				new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN),
				new Runnable() {
					public void run() {
						view.getBtnDeleteState().fire();
					}
				});

		// set ctrl s to save button on country scene
		view.getCountryScene().getAccelerators().put(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN),
				new Runnable() {
					public void run() {
						view.getCountryView().getBtnSave().fire();
					}
				});

		// set ctrl s to create button on state scene
		view.getStateScene().getAccelerators().put(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN),
				new Runnable() {
					public void run() {
						view.getStateView().getBtnCreate().fire();
					}
				});

		// set ctrl s to save button on country update scene
		view.getUpdateScene().getAccelerators().put(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN),
				new Runnable() {
					public void run() {
						view.getUpdateViewCountry().getBtnUpdateSave().fire();
					}
				});

		// set ctrl s to create button on state update scene
		view.getUpdateSceneState().getAccelerators().put(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN),
				new Runnable() {
					public void run() {
						view.getUpdateViewState().getBtnUpdateSaveState().fire();
					}
				});

		// set ctrl x to cancel on country scene
		view.getCountryScene().getAccelerators().put(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN),
				new Runnable() {
					public void run() {
						view.getCountryView().getBtnCancel().fire();
					}
				});

		// set ctrl x to cancel on state scene
		view.getStateScene().getAccelerators().put(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN),
				new Runnable() {
					public void run() {
						view.getStateView().getBtnCancelState().fire();
					}
				});

		// set ctrl x to cancel on country update scene
		view.getUpdateScene().getAccelerators().put(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN),
				new Runnable() {
					public void run() {
						view.getUpdateViewCountry().getBtnUpdateCancel().fire();
					}
				});

		// set ctrl x to cancel on country update scene
		view.getUpdateScene().getAccelerators().put(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN),
				new Runnable() {
					public void run() {
						view.getUpdateViewCountry().getBtnUpdateCancel().fire();
					}
				});

		// set ctrl x to cancel on state update scene
		view.getUpdateSceneState().getAccelerators().put(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN),
				new Runnable() {
					public void run() {
						view.getUpdateViewState().getBtnUpdateCancelState().fire();
					}
				});

		serviceLocator = ServiceLocator.getServiceLocator();
		serviceLocator.getLogger().info("Application controller initialized");
	}

	protected TableView<State> getStateTableView() {
		return view.getStateTableView();
	}

	private Scene getUpdateSceneState() {
		return view.getUpdateSceneState();
	}

	protected TableView<Country> getTableView() {
		return view.getTableView();
	}

	// 3 METHODEN; UM INPUT ZU VALIDIEREN MIT CHANGELISTENER
	/**
	 * Für Country Input
	 */
	// Muss String zwischen 3 & 50 Zeichen sein, nur Letters keine Zahlen
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
		view.getCountryView().getTxtCountry().getStyleClass().remove("inputNotOk");
		view.getCountryView().getStyleClass().remove("inputOk");

		// Farben setzen, rot = invalid, grün = valid
		if (valid) {
			view.getCountryView().getTxtCountry().getStyleClass().add("inputOk");
		} else {
			view.getCountryView().getTxtCountry().getStyleClass().add("inputNotOk");
		}
		// Speichert true/false-Wert für Button aktivieren
		countryValid = valid;
		// Aktiviert Button, wenn alles korrekt
		enableCreateButton();
	}

	// Eingabe in Textfeld Area muss Integer sein: > 0, <= Max_Value
	private void validateArea(String newValue) {
		boolean valid = false;

		try {
			int area = Integer.parseInt(newValue);
			if (area > 0 && area <= Integer.MAX_VALUE)
				valid = true;

		} catch (NumberFormatException e) {
			valid = false;
		}

		view.getCountryView().getTxtArea().getStyleClass().remove("inputNotOk");
		view.getCountryView().getTxtArea().getStyleClass().remove("inputOk");

		if (valid) {
			view.getCountryView().getTxtArea().getStyleClass().add("inputOk");

		} else {
			view.getCountryView().getTxtArea().getStyleClass().add("inputNotOk");
		}

		areaValid = valid;
		enableCreateButton();
	}

	// Eingabe in Textfeld Population muss Integer sein: > 0, <= Max_Value
	private void validatePopulation(String newValue) {
		boolean valid = false;

		try {
			int population = Integer.parseInt(newValue);
			if (population > 0 && population <= Integer.MAX_VALUE)
				valid = true;

		} catch (NumberFormatException e) {
			valid = false;
		}

		view.getCountryView().getTxtPopulation().getStyleClass().remove("inputNotOk");
		view.getCountryView().getTxtPopulation().getStyleClass().remove("inputOk");

		if (valid) {
			view.getCountryView().getTxtPopulation().getStyleClass().add("inputOk");

		} else {
			view.getCountryView().getTxtPopulation().getStyleClass().add("inputNotOk");
		}

		populationValid = valid;
		enableCreateButton();
	}

	// Combobox Country of State muss ausgefüllt sein
	private void validateFormOfGovernment(FormOfGovernment newValue) {
		boolean valid = false;
		boolean value = view.getCountryView().getCmbFormOfGov().getSelectionModel().isEmpty();

		view.getCountryView().getCmbFormOfGov().getStyleClass().remove("comboboxOk");
		view.getCountryView().getCmbFormOfGov().getStyleClass().remove("comboboxNotOk");

		if (!value) {
			valid = true;
			view.getCountryView().getCmbFormOfGov().getStyleClass().add("comboboxOk");
		}

		formOfGovernmentValid = valid;
		enableCreateButton();
	}

	// Aktiviert Button, wenn alle Felder korrekt ausgefüllt
	private void enableCreateButton() {
		boolean valid = countryValid & areaValid & populationValid & formOfGovernmentValid;
		view.getCountryView().getBtnSave().setDisable(!valid);
	}

	/**
	 * Für Country Update
	 */
	private void validateCountryUpdate(String newValue) {
		boolean valid = false;
		String country = newValue;
		Pattern pattern = Pattern.compile("[a-zA-Z]*");
		Matcher m = pattern.matcher(country);
		boolean alphabetic = m.matches();
		if (country.length() >= 3 && country.length() <= 50 && alphabetic) {
			valid = true;
		}

		view.getUpdateViewCountry().getTxtUpdateCountry().getStyleClass().remove("inputNotOk");
		view.getUpdateViewCountry().getStyleClass().remove("inputOk");

		if (valid) {
			view.getUpdateViewCountry().getTxtUpdateCountry().getStyleClass().add("inputOk");
		} else {
			view.getUpdateViewCountry().getTxtUpdateCountry().getStyleClass().add("inputNotOk");
		}
		countryValid = valid;
		enableCountryUpdateButton();
	}

	// Eingabe in Textfeld Area muss Integer sein: > 0, <= Max_Value
	private void validateAreaUpdate(String newValue) {
		boolean valid = false;

		try {
			double area = Double.parseDouble(newValue);
			if (area > 0 && area <= Double.MAX_VALUE)
				valid = true;

		} catch (NumberFormatException e) {
			valid = false;
		}

		view.getUpdateViewCountry().getTxtUpdateArea().getStyleClass().remove("inputNotOk");
		view.getUpdateViewCountry().getTxtUpdateArea().getStyleClass().remove("inputOk");

		if (valid) {
			view.getUpdateViewCountry().getTxtUpdateArea().getStyleClass().add("inputOk");

		} else {
			view.getUpdateViewCountry().getTxtUpdateArea().getStyleClass().add("inputNotOk");
		}

		areaValid = valid;
		enableCountryUpdateButton();
	}

	// Eingabe in Textfeld Population muss Integer sein: > 0, <= Max_Value
	private void validatePopulationUpdate(String newValue) {
		boolean valid = false;

		try {
			int population = Integer.parseInt(newValue);
			if (population > 0 && population <= Integer.MAX_VALUE)
				valid = true;

		} catch (NumberFormatException e) {
			valid = false;
		}

		view.getUpdateViewCountry().getTxtUpdatePopulation().getStyleClass().remove("inputNotOk");
		view.getUpdateViewCountry().getTxtUpdatePopulation().getStyleClass().remove("inputOk");

		if (valid) {
			view.getUpdateViewCountry().getTxtUpdatePopulation().getStyleClass().add("inputOk");

		} else {
			view.getUpdateViewCountry().getTxtUpdatePopulation().getStyleClass().add("inputNotOk");
		}

		populationValid = valid;
		enableCountryUpdateButton();
	}

	private void enableCountryUpdateButton() {
		boolean valid = countryValid & areaValid & populationValid;
		view.getUpdateViewCountry().getBtnUpdateSave().setDisable(!valid);
	}

	/**
	 * für State Input
	 */
	// Muss String zwischen 3 & 50 Zeichen sein, nur Letters keine Zahlen
	private void validateState(String newValue) {
		boolean valid = false;
		String state = newValue;
		Pattern pattern = Pattern.compile("[a-zA-Z]*");
		Matcher m = pattern.matcher(state);
		boolean alphabetic = m.matches();
		if (state.length() >= 3 && state.length() <= 50 && alphabetic) {
			valid = true;
		}

		view.getStateView().getTxtState().getStyleClass().remove("inputNotOk");
		view.getStateView().getStyleClass().remove("inputOk");

		if (valid) {
			view.getStateView().getTxtState().getStyleClass().add("inputOk");
		} else {
			view.getStateView().getTxtState().getStyleClass().add("inputNotOk");
		}

		stateValid = valid;
		enableCreateStateButton();
	}

	// Eingabe in Textfeld Population muss Integer sein: > 0, <= Max_Value
	private void validateStatePopulation(String newValue) {
		boolean valid = false;

		try {
			int population = Integer.parseInt(newValue);
			if (population > 0 && population <= Integer.MAX_VALUE)
				valid = true;

		} catch (NumberFormatException e) {
			valid = false;
		}

		view.getStateView().getTxtPopulationState().getStyleClass().remove("inputNotOk");
		view.getStateView().getTxtPopulationState().getStyleClass().remove("inputOk");

		if (valid) {
			view.getStateView().getTxtPopulationState().getStyleClass().add("inputOk");

		} else {
			view.getStateView().getTxtPopulationState().getStyleClass().add("inputNotOk");
		}

		statePopulationValid = valid;
		enableCreateStateButton();
	}

	// Eingabe in Textfeld Area muss Integer sein: > 0, <= Max_Value
	private void validateStateArea(String newValue) {
		boolean valid = false;

		try {
			int area = Integer.parseInt(newValue);
			if (area > 0 && area <= Integer.MAX_VALUE)
				valid = true;

		} catch (NumberFormatException e) {
			valid = false;
		}

		view.getStateView().getTxtAreaState().getStyleClass().remove("inputNotOk");
		view.getStateView().getTxtAreaState().getStyleClass().remove("inputOk");

		if (valid) {
			view.getStateView().getTxtAreaState().getStyleClass().add("inputOk");

		} else {
			view.getStateView().getTxtAreaState().getStyleClass().add("inputNotOk");
		}

		stateAreaValid = valid;
		enableCreateStateButton();
	}

	// Combobox Country of State muss ausgefüllt sein
	private void validateStateCountry(Country newValue) {
		boolean valid = false;
		boolean value = view.getStateView().getCmbMyCountry().getSelectionModel().isEmpty();

		view.getStateView().getCmbMyCountry().getStyleClass().remove("comboboxOk");
		view.getStateView().getCmbMyCountry().getStyleClass().remove("comboboxNotOk");

		if (!value) {
			valid = true;
			view.getStateView().getCmbMyCountry().getStyleClass().add("comboboxOk");
		}

		stateCountryValid = valid;
		enableCreateStateButton();
	}

	// Aktiviert Button, wenn alle Felder korrekt ausgefüllt
	private void enableCreateStateButton() {
		boolean valid = stateValid & stateAreaValid & statePopulationValid & stateCountryValid;
		view.getStateView().getBtnCreateState().setDisable(!valid);
	}

	/**
	 * Für Update State
	 */
	// Muss String zwischen 3 & 50 Zeichen sein, nur Letters keine Zahlen
	private void validateStateUpdate(String newValue) {
		boolean valid = false;
		String state = newValue;
		Pattern pattern = Pattern.compile("[a-zA-Z]*");
		Matcher m = pattern.matcher(state);
		boolean alphabetic = m.matches();
		if (state.length() >= 3 && state.length() <= 50 && alphabetic) {
			valid = true;
		}

		view.getUpdateViewState().getTxtUpdateState().getStyleClass().remove("inputNotOk");
		view.getUpdateViewState().getStyleClass().remove("inputOk");

		if (valid) {
			view.getUpdateViewState().getTxtUpdateState().getStyleClass().add("inputOk");
		} else {
			view.getUpdateViewState().getTxtUpdateState().getStyleClass().add("inputNotOk");
		}

		stateValid = valid;
		enableUpdateStateButton();
	}

	// Eingabe in Textfeld Area muss Integer sein: > 0, <= Max_Value
	private void validateStateAreaUpdate(String newValue) {
		boolean valid = false;

		try {
			double area = Double.parseDouble(newValue);
			if (area > 0 && area <= Double.MAX_VALUE)
				valid = true;

		} catch (NumberFormatException e) {
			valid = false;
		}

		view.getUpdateViewState().getTxtUpdateAreaState().getStyleClass().remove("inputNotOk");
		view.getUpdateViewState().getTxtUpdateAreaState().getStyleClass().remove("inputOk");

		if (valid) {
			view.getUpdateViewState().getTxtUpdateAreaState().getStyleClass().add("inputOk");

		} else {
			view.getUpdateViewState().getTxtUpdateAreaState().getStyleClass().add("inputNotOk");
		}

		stateAreaValid = valid;
		enableUpdateStateButton();
	}

	// Eingabe in Textfeld Population muss Integer sein: > 0, <= Max_Value
	private void validateStatePopulationUpdate(String newValue) {
		boolean valid = false;

		try {
			double population = Double.parseDouble(newValue);
			if (population > 0 && population <= Double.MAX_VALUE)
				valid = true;

		} catch (NumberFormatException e) {
			valid = false;
		}

		view.getUpdateViewState().getTxtUpdatePopulationState().getStyleClass().remove("inputNotOk");
		view.getUpdateViewState().getTxtUpdatePopulationState().getStyleClass().remove("inputOk");

		if (valid) {
			view.getUpdateViewState().getTxtUpdatePopulationState().getStyleClass().add("inputOk");

		} else {
			view.getUpdateViewState().getTxtUpdatePopulationState().getStyleClass().add("inputNotOk");
		}

		statePopulationValid = valid;
		enableUpdateStateButton();
	}

	// Aktiviert Button, wenn alle Felder korrekt ausgefüllt
	private void enableUpdateStateButton() {
		boolean valid = stateValid & stateAreaValid & statePopulationValid;
		view.getUpdateViewState().getBtnUpdateSaveState().setDisable(!valid);
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
		double area = Double.parseDouble(view.getCountryView().getTxtArea().getText());
		int population = Integer.parseInt(view.getCountryView().getTxtPopulation().getText());
		// 3. Hole den ausgewählten Wert der ComboBox
		FormOfGovernment formOfGovernment = view.getCountryView().getCmbFormOfGov().getValue();
		ObservableList<State> myStates = FXCollections.observableArrayList();
		// 4. Überprüfen das Kontrollelemente nicht leer sind
		if (name != null && area != 0 && formOfGovernment != null && myStates != null) {

		// 5
		model.createNewCountry(name, area, population, formOfGovernment, myStates);
		Translator t = ServiceLocator.getServiceLocator().getTranslator();
		view.setStatus(t.getString("statusLabel.createCountry"));
		view.getCountryView().reset(); 
		}
	}

	// *** UPDATE COUNTRY
	private void updateCountry(ActionEvent event) {
		// 1. Lese den Wert des Textfelds mit den Namen des Landes aus
		String name = view.getUpdateViewCountry().getTxtUpdateCountry().getText();
		// 2. Lese den Wert des Textfelds mit der Fläche des Landes aus
		double area = Double.parseDouble(view.getUpdateViewCountry().getTxtUpdateArea().getText());
		int population = Integer.parseInt(view.getUpdateViewCountry().getTxtUpdatePopulation().getText());
		// 3. Hole den ausgewählten Wert der ComboBox
		FormOfGovernment formOfGovernment = view.getUpdateViewCountry().getCmbUpdateFormOfGov().getValue();

		// 4. Überprüfen das Kontrollelemente nicht leer sind
		if (name != null && area != 0 && formOfGovernment != null) {
			Country selectedItem = view.getTableView().getSelectionModel().getSelectedItem();
			selectedItem.setName(name);
			selectedItem.setArea(area);
			selectedItem.setPopulation(population);
			selectedItem.setFormOfGovernment(formOfGovernment);

			int position = view.getTableView().getSelectionModel().getSelectedIndex();
			view.getTableView().getItems().set((int) position, selectedItem);

			view.getStateTableView().getItems();
			view.getStateTableView().refresh();
			Translator t = ServiceLocator.getServiceLocator().getTranslator();
			view.setStatus(t.getString("statusLabel.updateCountry"));
			view.getUpdateViewCountry().getBtnUpdateSave().setDisable(true);
		}
	}

	// *** UPDATE STATE
	private void updateState(ActionEvent event) {
		// 1. Lese den Wert des Textfelds mit den Namen des Landes aus
		String name = view.getUpdateViewState().getTxtUpdateState().getText();
		// 2. Lese den Wert des Textfelds mit der Fläche des Landes aus
		double area = Double.parseDouble(view.getUpdateViewState().getTxtUpdateAreaState().getText());
		int population = Integer.parseInt(view.getUpdateViewState().getTxtUpdatePopulationState().getText());
		// 3. Hole den ausgewählten Wert der ComboBox
		FormOfGovernment formOfGovernment = view.getUpdateViewState().getCmbUpdateFormOfGovState().getValue();

		Country myCountry = view.getUpdateViewState().getCmbMyCountry().getValue();

		// 4. Überprüfen das Kontrollelemente nicht leer sind
		if (name != null && area != 0 && population != 0 && formOfGovernment != null && myCountry != null) {
			// Holt das ausgewählte Objekt
			State selectedItem = view.getStateTableView().getSelectionModel().getSelectedItem(); 
			// Speichert den Namen des ausgewählten Objekts
			String selectedName = selectedItem.getName(); 
			// Holt den Index wo das Objekt gespeichert ist
			int index = model.getStates().indexOf(selectedItem);
			
			// Schaut in "states" ob ein State it diesem namen vorhanden ist
			State oldState = model.getStateByName(selectedName); 
																
			// Erstellt neues Objekt mit den neuen Werten
			State newState = new State(name, area, population, formOfGovernment, myCountry); 	

			model.getStates().set(index, newState);

			view.getMyStatesTableView().getItems().remove(selectedItem);
			view.getStateTableView().refresh();
			view.getMyStatesTableView().refresh();
			Translator t = ServiceLocator.getServiceLocator().getTranslator();
			view.setStatus(t.getString("statusLabel.updateState"));
			view.getUpdateViewState().getBtnUpdateSaveState().setDisable(true);
		} else {
		}

	}

	// CREATE STATE
	private void createNewState(ActionEvent event) {
		String name = view.getStateView().getTxtState().getText();
		double area = Integer.parseInt(view.getStateView().getTxtAreaState().getText());
		int population = Integer.parseInt(view.getStateView().getTxtPopulationState().getText());
		FormOfGovernment formOfGovernment = view.getStateView().getCmbFormOfGovState().getValue();

		Country myCountry = view.getStateView().getCmbMyCountry().getValue();

		// 4. Überprüfen das Kontrollelemente nicht leer sind
		if (name != null && area != 0 && formOfGovernment != null && myCountry != null) {
		
		// 5
		model.createNewState(name, area, population, formOfGovernment, myCountry);
		Translator t = ServiceLocator.getServiceLocator().getTranslator();
		view.setStatus(t.getString("statusLabel.createState"));
		view.getStateView().reset(); 
		}
	}

	/**
	 * UPDATE Country und State TableView.
	 */
	public void updateView() {
		// Finally, attach the tableView to the ObservableList of data
		view.getTableView().setItems(model.getCountries());
		view.getStateTableView().setItems(model.getStates());

	}

	// ****** SZENEN WECHSEL ******

	// ****** ENDE SZENEN WECHSEL ******

	// ****** GETTER FUER DIE SZENNEN ******
	// Methode um die Country Szene aus der App_View zu holen
	private Scene getCountryScene() {
		return view.getCountryScene();

	}

	private Scene getUpdateScene() {
		return view.getUpdateScene();
	}

	// Methode um die State Szene aus der App_View zu holen
	private Scene getStateScene() {
		return view.getStateScene();
	}

	// Methode um die Country Szene aus der App_View zu holen
	private Scene getMainScene() {
		return view.getMainScene();
	}

	// ****** ENDE GETTER FUER DIE SZENEN ******

}
