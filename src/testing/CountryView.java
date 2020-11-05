	package testing;

import controller.App_Controller;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

// 0
public class CountryView extends GridPane {

	private App_Model model;
	private App_Controller controller;
	private Stage stage;

	// 1 Controls used for data processing (Data Entry)
	private Label lblCountry;
	private Label lblArea;
	private Label lblFormOfGov;

	// 1
	// Textfeld um den Namen des Landes einzugeben
	private TextField txtCountry = new TextField();
	// Textfeld um die Fläche(area) des landes einzugeben
	private TextField txtArea = new TextField();
	// ComboBox um die Regierungsform auszuwählen
	protected ComboBox<FormOfGovernment> cmbFormOfGov = new ComboBox<FormOfGovernment>();

	// 1 (Data Display) die TableView
	protected TableView<GovernedRegion> tableView;
	protected TableColumn<GovernedRegion, String> colCountry;
	protected TableColumn<GovernedRegion, String> colArea;
	protected TableColumn<GovernedRegion, String> colFormOfGov;

	// 1 Buttons (Data Control)
	protected Button btnCreate = new Button("Create");
	protected Button btnDelete = new Button("Delete");

	// Konstruktor
	public CountryView(Stage stage, App_Model model, App_Controller controller) {
		this.stage = stage;
		this.model = model;
//		this.add(createDataEntryPane(), 0, 0);
		this.add(createControlPane(), 0, 1);

		// Initialisieren der TableView
		this.tableView = createTableView();
		this.add(tableView, 0, 3);

	}

	// 1 Data Entry Pane
	private Pane createDataEntryPane() {
		GridPane pane = new GridPane();
		pane.setId("dataEntry");
		// Declare the individual controls in the GUI
		Label lblCountry = new Label("Name des Landes");
		Label lblArea = new Label("Fläche des Landes");
		Label lblFormOfGov = new Label("Regierungsform des Landes");
		// Fill combos (hol mir die Items,alle hinzufügen von den values der Enums)
		cmbFormOfGov.getItems().addAll(FormOfGovernment.values());
		cmbFormOfGov.setValue(FormOfGovernment.DICTATORSHIP);
		// Organize the layout, add in the controls (col, row)
		// pane.add(child, columnIndex, rowIndex);
		pane.add(lblCountry, 0, 0);
		pane.add(getTxtCountry(), 1, 0);
		pane.add(lblArea, 0, 1);
		pane.add(getTxtArea(), 1, 1);
		pane.add(lblFormOfGov, 0, 2);
		pane.add(cmbFormOfGov, 1, 2);

		return pane;
	}

	// 1 Data Control Pane
	private Pane createControlPane() {
		GridPane pane = new GridPane();
		pane.setId("controlArea");
		pane.add(btnCreate, 0, 0);
		pane.add(btnDelete, 1, 0);
		return pane;
	}

	/*
	 * 1, 2 & 3 Data Display Pane TableView
	 */
	private TableView<GovernedRegion> createTableView() {
		this.tableView = new TableView<GovernedRegion>();
		this.tableView.setEditable(true);

		// Each column needs a title, and a source of data.
		// For editable columns, each column needs to contain a TextField.

		colCountry = new TableColumn<>("Country"); // Erstellen und Beschriftung der Spalte
		colCountry.setCellFactory(TextFieldTableCell.forTableColumn()); // Ermöglicht Editierung in der TableView
		colCountry.setCellValueFactory(new PropertyValueFactory<>("name")); // Insatnzieren ein Property und übergeben
		tableView.getColumns().add(colCountry); // Fügen der TableView die Spalte hinzu

		colFormOfGov = new TableColumn<>("Form of Government");
//		colFormOfGov.setCellFactory(TextFieldTableCell.forTableColumn()); // Ermöglicht Editierung in der TableView
		colFormOfGov.setCellValueFactory(new PropertyValueFactory<GovernedRegion, String>("formOfGovernment"));
		tableView.getColumns().add(colFormOfGov);

		colArea = new TableColumn<>("Area");
//		colArea.setCellFactory(TextFieldTableCell.forTableColumn()); // Ermöglicht Editierung in der TableView
		colArea.setCellValueFactory(new PropertyValueFactory<>("area"));
		tableView.getColumns().add(colArea);

		// Finally, attach the tableView to the ObservableList of data
		tableView.setItems(model.getAllData());

		return tableView;
	}

	/*
	 * Methode um die TextFelder und Choicebox zu leeren
	 */
	public void reset() {
		this.txtCountry.setText("");
		this.txtArea.setText("");
		this.cmbFormOfGov.getSelectionModel().clearSelection(); // API
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

	// Getter
	public TableView<GovernedRegion> getTableView() {
		return this.tableView;
	}

	// Setter
	public void setTableView(TableView<GovernedRegion> tableView) {
		this.tableView = tableView;
	}

	// Getter
	public TableColumn<GovernedRegion, String> getColCountry() {
		return colCountry;
	}

	// Setter
	public void setColCountry(TableColumn<GovernedRegion, String> colCountry) {
		this.colCountry = colCountry;
	}

	// Getter
	public TableColumn<GovernedRegion, String> getColArea() {
		return colArea;
	}

	// Setter
	public void setColArea(TableColumn<GovernedRegion, String> colArea) {
		this.colArea = colArea;
	}

	// Getter
	public TableColumn<GovernedRegion, String> getColFormOfGov() {
		return colFormOfGov;
	}

	// Setter
	public void setColFormOfGov(TableColumn<GovernedRegion, String> colFormOfGov) {
		this.colFormOfGov = colFormOfGov;
	}

	// Getter
	public Button getBtnCreate() {
		return this.btnCreate;
	}

	// Setter
	public void setBtnCreate(Button btnCreate) {
		this.btnCreate = btnCreate;
	}

	// Getter
	public Button getBtnDelete() {
		return this.btnDelete;
	}

	// Setter
	public void setBtnDelete(Button btnDelete) {
		this.btnDelete = btnDelete;
	}
}
