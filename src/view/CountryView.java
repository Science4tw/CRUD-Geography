package view;

import java.util.Locale;
import java.util.logging.Logger;

import com.sun.java.accessibility.util.Translator;

import controller.App_Controller;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.GovernedRegion;
import model.Country;
import model.FormOfGovernment;
import model.App_Model;
import model.State;
import mvc.ServiceLocator;

// 0
public class CountryView extends GridPane {

	private App_Model model;
	private App_Controller controller;
	private Stage stage;

	// 1 Controls used for data processing (Data Entry)
	private Label lblCountry;
	private Label lblArea;
	private Label lblPopulation;
	private Label lblFormOfGov;
	private Label lblCountryTitle;

	// 1
	// Textfeld um den Namen des Landes einzugeben
	private TextField txtCountry = new TextField();
	// Textfeld um die Fläche(area) des landes einzugeben
	private TextField txtArea = new TextField();
	// TextFeld für die Population des Landes einzugeben
	private TextField txtPopulation = new TextField();
	// ComboBox um die Regierungsform auszuwählen
	protected ComboBox<FormOfGovernment> cmbFormOfGov = new ComboBox<FormOfGovernment>();

	// 1 Buttons (Data Control)
	protected Button btnSave = new Button("Save");
	protected Button btnCancel = new Button("Cancel");

	// Konstruktor
	public CountryView(Stage stage, App_Model model, App_Controller controller) {
		this.stage = stage;
		this.model = model;
		this.add(createDataEntryPane(), 0, 0);
		this.add(createControlPane(), 0, 1);

		getStylesheets().add(getClass().getResource("view.css").toExternalForm());
	}

	// 1 Data Entry Pane
	private Pane createDataEntryPane() {
		
		GridPane pane = new GridPane();
		pane.setId("dataEntry");
		// Declare the individual controls in the GUI
		lblCountryTitle = new Label("Land erstellen");
		lblCountryTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
		
		lblCountry = new Label("Name des Landes");
		lblArea = new Label("Fläche des Landes");
		lblPopulation = new Label("Population des Landes");
		lblFormOfGov = new Label("Regierungsform des Landes");
		// Fill combos (hol mir die Items,alle hinzufügen von den values der Enums)
		cmbFormOfGov.getItems().addAll(FormOfGovernment.values());
		
		// Organize the layout, add in the controls (col, row)
		pane.add(lblCountryTitle, 0, 0);
		
		pane.add(lblCountry, 0, 1);
		pane.add(getTxtCountry(), 1, 1);
		
		pane.add(lblArea, 0, 2);
		pane.add(getTxtArea(), 1, 2);
		
		pane.add(lblPopulation, 0, 3);
		pane.add(getTxtPopulation(), 1, 3);
		
		pane.add(lblFormOfGov, 0, 4);
		pane.add(cmbFormOfGov, 1, 4);
		
		pane.setVgap(5);
		pane.setHgap(10);
		pane.setPadding(new Insets(10, 10, 10, 10));

		return pane;
	}

	// 1 Data Control Pane
	private Pane createControlPane() {
		GridPane pane = new GridPane();
		pane.setId("controlArea");
		pane.add(btnSave, 0, 0);
		pane.add(btnCancel, 1, 0);
		pane.setHgap(10);
		pane.setPadding(new Insets(10, 10, 10, 35));
		return pane;
	}

	/*
	 * Methode um die TextFelder und Combobox zu leeren
	 */
	public void reset() {
		this.txtCountry.setText("");
		this.txtArea.setText("");
		this.txtPopulation.setText("");
		this.cmbFormOfGov.getSelectionModel().clearSelection();
	}
	
	public TextField getTxtPopulation() {
		return txtPopulation;
	}

	public void setTxtPopulation(TextField txtPopulation) {
		this.txtPopulation = txtPopulation;
	}

	// Getter
	public App_Model getModel() {
		return model;
	}

	// Setter
	public void setModel(App_Model model) {
		this.model = model;
	}

	// Getter
	public App_Controller getController() {
		return controller;
	}

	// Setter
	public void setController(App_Controller controller) {
		this.controller = controller;
	}

	// Getter
	public Stage getStage() {
		return stage;
	}

	// Setter
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	// Getter
	public TextField getTxtCountry() {
		return this.txtCountry;
	}

	// Setter
	public void setTxtCountry(TextField txtCountry) {
		this.txtCountry = txtCountry;
	}

	// Getter
	public TextField getTxtArea() {
		return txtArea;
	}

	// Setter 
	public void setTxtArea(TextField txtArea) {
		this.txtArea = txtArea;
	}

	// Getter
	public ComboBox<FormOfGovernment> getCmbFormOfGov() {
		return cmbFormOfGov;
	}

	// Setter
	public void setCmbFormOfGov(ComboBox<FormOfGovernment> cmbFormOfGov) {
		this.cmbFormOfGov = cmbFormOfGov;
	}


	public Button getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(Button btnSave) {
		this.btnSave = btnSave;
	}

	public Button getBtnCancel() {
		return btnCancel;
	}

	public void setBtnCancel(Button btnCancel) {
		this.btnCancel = btnCancel;
	}

	public Label getLblCountryTitle() {
		return lblCountryTitle;
	}

	public Label getLblCountry() {
		return lblCountry;
	}

	public Label getLblArea() {
		return lblArea;
	}

	public Label getLblPopulation() {
		return lblPopulation;
	}

	public Label getLblFormOfGov() {
		return lblFormOfGov;
	}


}
