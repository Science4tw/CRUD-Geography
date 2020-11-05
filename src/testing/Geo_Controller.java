package testing;

import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.FormOfGovernment;
import model.App_Model;
import model.GovernedRegion;
import view.App_View;
import view.CountryView;

// 0
public class Geo_Controller {

	private App_Model model;
	private App_View view;

	// 0 Konstruktor
	public Geo_Controller(App_Model model, App_View view) {
		this.model = model;
		this.view = view;

		// CREATE COUNTRY - Event handler for the button
		// Wenn der Create Button (btnCreate) gedrückt wird, ruft der Controller die
		// Methode createNewCountry auf
		view.getCountryView().getBtnCreate().setOnAction(this::createNewCountry);

		// DELETE COUNTRY
		view.getCountryView().getBtnDelete().setOnAction(e -> {
			GovernedRegion selectedItem = view.getTableView().getSelectionModel().getSelectedItem();
			view.getTableView().getItems().remove(selectedItem);
		});

		// ADDITIONAL FUNCTIONALITY
		/**
		 * 3 Wenn kein Name & Area in den Textfeldern eingegeben ist, "DEAKTIVIERE" den
		 * CREATE Button (DISABLED)
		 */
		view.getCountryView().getBtnCreate().disableProperty()
				.bind(view.getCountryView().getTxtCountry().textProperty().isEmpty());
		view.getCountryView().getBtnCreate().disableProperty()
				.bind(view.getCountryView().getTxtArea().textProperty().isEmpty());
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
				view.getTableView().scrollTo(c.getFrom());
			}
		});
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
