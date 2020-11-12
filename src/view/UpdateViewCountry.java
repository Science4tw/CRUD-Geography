package view;

import controller.App_Controller;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.FormOfGovernment;
import model.App_Model;

public class UpdateViewCountry extends GridPane {

	private App_Model model;
	private App_Controller controller;
	private Stage stage;

	// 1 Controls used for data processing (Data Entry)
	private Label lblUpdateCountry;
	private Label lblUpdateArea;
	private Label lblUpdatePopulation;
	private Label lblUpdateFormOfGov;

	// 1
	// Textfelder
	private TextField txtUpdateCountry = new TextField();
	private TextField txtUpdateArea = new TextField();
	private TextField txtUpdatePopulation = new TextField();
	// ComboBox um die Regierungsform auszuwählen
	protected ComboBox<FormOfGovernment> cmbUpdateFormOfGov = new ComboBox<FormOfGovernment>();

	// 1 Buttons (Data Control)
	protected Button btnUpdateSave = new Button("Save");
	protected Button btnUpdateCancel = new Button("Cancel");
	
	// Konstruktor
	public UpdateViewCountry(Stage stage, App_Model model, App_Controller controller) {
		this.stage = stage;
		this.model = model;
		this.add(createUpdateDataEntryPane(), 0, 0);
		this.add(createUpdateControlPane(), 0, 1);
		
		getStylesheets().add(getClass().getResource("view.css").toExternalForm());

	}
	
	// 1 Data Entry Pane
	private Pane createUpdateDataEntryPane() {
		GridPane pane = new GridPane();
		pane.setId("dataEntry");
		// Declare the individual controls in the GUI
		lblUpdateCountry = new Label("Name des Landes");
		lblUpdateArea = new Label("Fläche des Landes");
		lblUpdatePopulation = new Label("Population des Landes");
		lblUpdateFormOfGov = new Label("Regierungsform des Landes");
		// Fill combos (hol mir die Items,alle hinzufügen von den values der Enums)
		cmbUpdateFormOfGov.getItems().addAll(FormOfGovernment.values());
		cmbUpdateFormOfGov.setValue(FormOfGovernment.DICTATORSHIP);
		// Organize the layout, add in the controls (col, row)
		// pane.add(child, columnIndex, rowIndex);
		pane.add(lblUpdateCountry, 0, 0);
		pane.add(getUpdateTxtCountry(), 1, 0);
		pane.add(lblUpdateArea, 0, 1);
		pane.add(getUpdateTxtArea(), 1, 1);
		pane.add(lblUpdatePopulation, 0, 2);
		pane.add(getUpdateTxtPopulation(), 1, 2);
		pane.add(lblUpdateFormOfGov, 0, 3);
		pane.add(cmbUpdateFormOfGov, 1, 3);

		return pane;
	}
	// 1 Data Control Pane
	private Pane createUpdateControlPane() {
		GridPane pane = new GridPane();
		pane.setId("controlArea");
		pane.add(btnUpdateSave, 0, 0);
		pane.add(btnUpdateCancel, 1, 0);
		pane.setHgap(10);
		pane.setPadding(new Insets(10, 10, 10, 35));
		return pane;
	}

	/*
	 * Methode um die TextFelder und Choicebox zu leeren
	 */
	public void reset() {
		this.txtUpdateCountry.setText("");
		this.txtUpdateArea.setText("");
		this.txtUpdatePopulation.setText("");
		this.cmbUpdateFormOfGov.getSelectionModel().clearSelection(); // API
	}
	
	public TextField getUpdateTxtPopulation() {
		return txtUpdatePopulation;
	}

	public void setUpdateTxtPopulation(TextField txtPopulation) {
		this.txtUpdatePopulation = txtPopulation;
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
	public TextField getUpdateTxtCountry() {
		return this.txtUpdateCountry;
	}

	// Setter
	public void setUpdateTxtCountry(TextField txtCountry) {
		this.txtUpdateCountry = txtCountry;
	}

	// Getter
	public TextField getUpdateTxtArea() {
		return txtUpdateArea;
	}

	// Setter 
	public void setTxtArea(TextField txtArea) {
		this.txtUpdateArea = txtArea;
	}

	// Getter
	public ComboBox<FormOfGovernment> getCmbFormOfGov() {
		return cmbUpdateFormOfGov;
	}

	// Setter
	public void setCmbFormOfGov(ComboBox<FormOfGovernment> cmbFormOfGov) {
		this.cmbUpdateFormOfGov = cmbFormOfGov;
	}


	public Button getBtnSave() {
		return btnUpdateSave;
	}

	public void setBtnSave(Button btnSave) {
		this.btnUpdateSave = btnSave;
	}

	public Button getBtnUpdateCancel() {
		return btnUpdateCancel;
	}

	public void setBtnUpdateCancel(Button btnCancel) {
		this.btnUpdateCancel = btnCancel;
	}

	public TextField getTxtUpdateCountry() {
		return txtUpdateCountry;
	}

	public void setTxtUpdateCountry(TextField txtUpdateCountry) {
		this.txtUpdateCountry = txtUpdateCountry;
	}

	public TextField getTxtUpdateArea() {
		return txtUpdateArea;
	}

	public void setTxtUpdateArea(TextField txtUpdateArea) {
		this.txtUpdateArea = txtUpdateArea;
	}

	public TextField getTxtUpdatePopulation() {
		return txtUpdatePopulation;
	}

	public void setTxtUpdatePopulation(TextField txtUpdatePopulation) {
		this.txtUpdatePopulation = txtUpdatePopulation;
	}

	public ComboBox<FormOfGovernment> getCmbUpdateFormOfGov() {
		return cmbUpdateFormOfGov;
	}

	public void setCmbUpdateFormOfGov(ComboBox<FormOfGovernment> cmbUpdateFormOfGov) {
		this.cmbUpdateFormOfGov = cmbUpdateFormOfGov;
	}

	public Button getBtnUpdateSave() {
		return btnUpdateSave;
	}

	public void setBtnUpdateSave(Button btnUpdateSave) {
		this.btnUpdateSave = btnUpdateSave;
	}


}
