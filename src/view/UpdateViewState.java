package view;

import controller.App_Controller;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.App_Model;
import model.Country;
import model.FormOfGovernment;

public class UpdateViewState extends GridPane {

	private App_Model model;
	private App_Controller controller;
	private Stage stage;

	// 1 Controls used for data processing (Data Entry)
	private Label lblUpdateState;
	private Label lblUpdateAreaState;
	private Label lblUpdatePopulationState;
	private Label lblUpdateFormOfGovState;

	// 1
	// Textfeld um den Namen des States einzugeben
	private TextField txtUpdateState = new TextField();
	// Textfeld um die Fläche(area) des States einzugeben
	private TextField txtUpdateAreaState = new TextField();
	// TextFeld für die Population des States einzugeben
	private TextField txtUpdatePopulationState = new TextField();
	// ComboBox um die Regierungsform auszuwählen
	protected ComboBox<FormOfGovernment> cmbUpdateFormOfGovState = new ComboBox<FormOfGovernment>();
	
	
	// ComboBox um zu wählen zu welchem Land ein neu erzeugtes State Objekt gehört
	private ComboBox<Country> cmbMyCountry = new ComboBox<Country>();

	// 1 Buttons (Data Control)
	protected Button btnUpdateSaveState = new Button("Save State");
	protected Button btnUpdateCancelState = new Button("Cancel");

	// Konstruktor
	public UpdateViewState(Stage stage, App_Model model, App_Controller controller) {
		this.stage = stage;
		this.model = model;
		this.add(createUpdateDataEntryPaneState(), 0, 0);
		this.add(createUpdateControlPaneState(), 0, 1);

	}
	
	// 1 Data Entry Pane
	private Pane createUpdateDataEntryPaneState() {
		GridPane pane = new GridPane();
		pane.setId("dataEntry");
		// Declare the individual controls in the GUI
		lblUpdateState = new Label("Name des States");
		lblUpdateAreaState = new Label("Fläche des States");
		lblUpdatePopulationState = new Label("Population des States");
		lblUpdateFormOfGovState = new Label("Regierungsform des States");
		// Fill combos (hol mir die Items,alle hinzufügen von den values der Enums)
		cmbUpdateFormOfGovState.getItems().addAll(FormOfGovernment.values());
		cmbUpdateFormOfGovState.setValue(FormOfGovernment.DICTATORSHIP);
		// Organize the layout, add in the controls (col, row)
		// pane.add(child, columnIndex, rowIndex);
		pane.add(lblUpdateState, 0, 0);
		pane.add(getTxtUpdateState(), 1, 0);
		pane.add(lblUpdateAreaState, 0, 1);
		pane.add(getTxtUpdateAreaState(), 1, 1);
		pane.add(lblUpdatePopulationState, 0, 2);
		pane.add(getTxtUpdatePopulationState(), 1, 2);
		pane.add(lblUpdateFormOfGovState, 0, 3);
		pane.add(getCmbUpdateFormOfGovState(), 1, 3);

		return pane;
		
	}
	// 1 Data Control Pane
	private Pane createUpdateControlPaneState() {
		GridPane pane = new GridPane();
		pane.setId("controlArea");
		pane.add(btnUpdateSaveState, 0, 0);
		pane.add(btnUpdateCancelState, 1, 0);
		return pane;
	}
	
	/*
	 * Methode um die TextFelder und Choicebox zu leeren
	 */
	public void reset() {
		this.txtUpdateState.setText("");
		this.txtUpdateAreaState.setText("");
		this.txtUpdatePopulationState.setText("");
		this.cmbUpdateFormOfGovState.getSelectionModel().clearSelection(); // API
	}
	public App_Model getModel() {
		return model;
	}

	public void setModel(App_Model model) {
		this.model = model;
	}

	public App_Controller getController() {
		return controller;
	}

	public void setController(App_Controller controller) {
		this.controller = controller;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Label getLblUpdateState() {
		return lblUpdateState;
	}

	public void setLblUpdateState(Label lblUpdateState) {
		this.lblUpdateState = lblUpdateState;
	}

	public Label getLblUpdateAreaState() {
		return lblUpdateAreaState;
	}

	public void setLblUpdateAreaState(Label lblUpdateAreaState) {
		this.lblUpdateAreaState = lblUpdateAreaState;
	}

	public Label getLblUpdatePopulationState() {
		return lblUpdatePopulationState;
	}

	public void setLblUpdatePopulationState(Label lblUpdatePopulationState) {
		this.lblUpdatePopulationState = lblUpdatePopulationState;
	}

	public Label getLblUpdateFormOfGovState() {
		return lblUpdateFormOfGovState;
	}

	public void setLblUpdateFormOfGovState(Label lblUpdateFormOfGovState) {
		this.lblUpdateFormOfGovState = lblUpdateFormOfGovState;
	}

	public TextField getTxtUpdateState() {
		return txtUpdateState;
	}

	public void setTxtUpdateState(TextField txtUpdateState) {
		this.txtUpdateState = txtUpdateState;
	}

	public TextField getTxtUpdateAreaState() {
		return txtUpdateAreaState;
	}

	public void setTxtUpdateAreaState(TextField txtUpdateAreaState) {
		this.txtUpdateAreaState = txtUpdateAreaState;
	}

	public TextField getTxtUpdatePopulationState() {
		return txtUpdatePopulationState;
	}

	public void setTxtUpdatePopulationState(TextField txtUpdatePopulationState) {
		this.txtUpdatePopulationState = txtUpdatePopulationState;
	}

	public ComboBox<FormOfGovernment> getCmbUpdateFormOfGovState() {
		return cmbUpdateFormOfGovState;
	}

	public void setCmbUpdateFormOfGovState(ComboBox<FormOfGovernment> cmbUpdateFormOfGovState) {
		this.cmbUpdateFormOfGovState = cmbUpdateFormOfGovState;
	}

	public Button getBtnUpdateSaveState() {
		return btnUpdateSaveState;
	}

	public void setBtnUpdateSaveState(Button btnUpdateSaveState) {
		this.btnUpdateSaveState = btnUpdateSaveState;
	}

	public Button getBtnUpdateCancelState() {
		return btnUpdateCancelState;
	}

	public void setBtnUpdateCancelState(Button btnUpdateCancelState) {
		this.btnUpdateCancelState = btnUpdateCancelState;
	}


	public ComboBox<Country> getCmbMyCountry() {
		return cmbMyCountry;
	}

	public void setCmbMyCountry(ComboBox<Country> cmbMyCountry) {
		this.cmbMyCountry = cmbMyCountry;
	}
	
	
}
