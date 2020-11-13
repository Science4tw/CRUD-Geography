package view;

import controller.App_Controller;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Country;
import model.FormOfGovernment;
import model.GovernedRegion;
import model.App_Model;
import model.State;

public class StateView extends GridPane {

	private App_Model model;
	private App_Controller controller;
	private Stage stage;

	// 1 Controls used for data processing (Data Entry)
	private Label lblState;
	private Label lblAreaState;
	private Label lblPopulationState;
	private Label lblFormOfGovState;
	private Label lblMyCountry;
	private Label lblStateTitle;

	// 1
	// Textfelder
	private TextField txtState = new TextField();
	private TextField txtAreaState = new TextField();
	private TextField txtPopulationState = new TextField();
	// ComboBox um die Regierungsform auszuwählen
	protected ComboBox<FormOfGovernment> cmbFormOfGovState = new ComboBox<FormOfGovernment>();
	
	// ComboBox um das Land auszuwählen, wo der State hinzugehört
	protected ComboBox<Country> cmbMyCountry = new ComboBox<Country>();
	
	// ComboBox um das zugehörige Land auszuwählen
	// 1 Buttons (Data Control)
	protected Button btnCreate = new Button("Create");
	protected Button btnDelete = new Button("Delete");


	// 1 Buttons (Data Control)
	protected Button btnCreateState = new Button("Save State");
	protected Button btnCancelState = new Button("Cancel");

	public StateView(Stage stage, App_Model model, App_Controller controller) {
		this.stage = stage;
		this.model = model;
		this.add(createDataEntryPane(), 0, 0);
		this.add(createControlPane(), 0, 1);
		
		getStylesheets().add(getClass().getResource("view.css").toExternalForm());
	}

	/*
	 * Methode um die TextFelder und Combobox zu leeren
	 */
	public void reset() {
		this.txtState.setText("");
		this.txtAreaState.setText("");
		this.txtPopulationState.setText("");
		this.cmbMyCountry.getSelectionModel().clearSelection();	
		this.getCmbFormOfGovState().getSelectionModel().clearSelection();
	}

	// 1 Data Entry Pane
	private Pane createDataEntryPane() {
		GridPane pane = new GridPane();
		pane.setId("dataEntry");
		// Declare the individual controls in the GUI
		lblStateTitle = new Label("State erstellen");
		lblStateTitle.setStyle("-fx-font-size: 16;");
		lblState = new Label("Name State");
		lblAreaState = new Label("Fläche State");
		lblPopulationState = new Label("Population State");
		lblFormOfGovState = new Label("Regierungsform des Landes");
		lblMyCountry = new Label("Land des States");
	
		
		
//		// Aus der TablewView soll die erste Spalte als Auswahlmöglichkeit angeboten werden		
//		
//		for (GovernedRegion governedRegion: model.getGovernedRegions()) {
//			cmbMyCountry.getItems().add(governedRegion.getName());
//		}

		cmbMyCountry.setItems(model.getCountries());
//		cmbMyCountry.setModel(new DefaultComboBoxModel<GovernedRegion>(model.getGovernedRegions().toArray(new Country[0])));		
//		cmbMyCountry.setValue(null);
		

		
		

		// Fill combos (hol mir die Items,alle hinzufügen von den values der Enums)
		cmbFormOfGovState.getItems().addAll(FormOfGovernment.values());
		//cmbFormOfGovState.setValue(FormOfGovernment.DICTATORSHIP);		
		
		// Organize the layout, add in the controls (col, row)
		pane.add(lblStateTitle, 0, 0);
		pane.add(lblState, 0, 1);
		pane.add(getTxtState(), 1, 1);

		pane.add(lblAreaState, 0, 2);
		pane.add(getTxtAreaState(), 1, 2);

		pane.add(lblPopulationState, 0, 3);
		pane.add(getTxtPopulationState(), 1, 3);

		pane.add(lblFormOfGovState, 0, 4);
		pane.add(cmbFormOfGovState, 1, 4);
		
		pane.add(lblMyCountry, 0, 5);
		pane.add(cmbMyCountry, 1, 5);
		
		pane.setVgap(5);

		return pane;
	}


	// 1 Data Control Pane
	private Pane createControlPane() {
		GridPane pane = new GridPane();
		pane.setId("controlArea");
		pane.add(btnCreateState, 0, 0);
		pane.add(btnCancelState, 1, 0);
		pane.setHgap(10);
		pane.setPadding(new Insets(10, 10, 10, 35));
		return pane;
	}

	// *** GETTERS AND SETTERS BELOW ***
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

	public Label getLblState() {
		return lblState;
	}

	public void setLblState(Label lblState) {
		this.lblState = lblState;
	}

	public Label getLblAreaState() {
		return lblAreaState;
	}

	public void setLblAreaState(Label lblAreaState) {
		this.lblAreaState = lblAreaState;
	}

	public Label getLblFormOfGovState() {
		return lblFormOfGovState;
	}

	public void setLblFormOfGovState(Label lblFormOfGovState) {
		this.lblFormOfGovState = lblFormOfGovState;
	}

	public Label getLblMyCountry() {
		return lblMyCountry;
	}

	public void setLblMyCountry(Label lblMyCountry) {
		this.lblMyCountry = lblMyCountry;
	}

	public TextField getTxtState() {
		return txtState;
	}

	public void setTxtState(TextField txtState) {
		this.txtState = txtState;
	}

	public TextField getTxtAreaState() {
		return txtAreaState;
	}

	public void setTxtAreaState(TextField txtAreaState) {
		this.txtAreaState = txtAreaState;
	}

	public Button getBtnCreate() {
		return btnCreate;
	}

	public void setBtnCreate(Button btnCreate) {
		this.btnCreate = btnCreate;
	}

	public Button getBtnDelete() {
		return btnDelete;
	}

	public void setBtnDelete(Button btnDelete) {
		this.btnDelete = btnDelete;
	}

	public ComboBox<Country> getCmbmyCountry() {
		return cmbMyCountry;
	}

	public void setCmbmyCountry(ComboBox<Country> cmbmyCountry) {
		this.cmbMyCountry = cmbmyCountry;
	}

	public Button getBtnCreateState() {
		return btnCreateState;
	}

	public void setBtnCreateState(Button btnCreateState) {
		this.btnCreateState = btnCreateState;
	}

	public Button getBtnCancelState() {
		return btnCancelState;
	}

	public void setBtnCancelState(Button btnDeleteState) {
		this.btnCancelState = btnDeleteState;
	}

	public TextField getTxtPopulationState() {
		return txtPopulationState;
	}

	public void setTxtPopulationState(TextField txtPopulationState) {
		this.txtPopulationState = txtPopulationState;
	}

	public ComboBox<FormOfGovernment> getCmbFormOfGovState() {
		return cmbFormOfGovState;
	}

	public void setCmbFormOfGovState(ComboBox<FormOfGovernment> cmbFormOfGovState) {
		this.cmbFormOfGovState = cmbFormOfGovState;
	}

	public ComboBox<Country> getCmbMyCountry() {
		return cmbMyCountry;
	}

	public void setCmbMyCountry(ComboBox<Country> cmbMyCountry) {
		this.cmbMyCountry = cmbMyCountry;
	}

}
