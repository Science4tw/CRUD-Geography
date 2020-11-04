package view;

import controller.Geo_Controller;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Geo_Model;
import model.GovernedRegion;

// 0
public class Geo_View extends GridPane { // 1 extends BorderPane

	// 0
	private Stage stage;
	private Geo_Model model;
	// 1 (abklären ob das so machbar ist)
	private Geo_Controller controller;

	// VIEWS
	private SplashView splashView;
	private CountryView countryView;
	private StateView stateView;

	// SZENEN
	private Scene splashScene;
	private Scene mainScene; // -> Geo_View (GridPane)
	private Scene countryScene;
	private Scene stateScene;

	// Controls
	private Button btnCreateCountry = new Button("Create");
	private Button btnDeleteCountry = new Button("Delete");
	private Button btnUpdateCountry = new Button("Update");

	// 1 (Data Display) die TableView
	protected TableView<GovernedRegion> tableView;
	protected TableColumn<GovernedRegion, String> colCountry;
	protected TableColumn<GovernedRegion, Double> colArea;
	protected TableColumn<GovernedRegion, Integer> colPopulation;
	protected TableColumn<GovernedRegion, String> colFormOfGov;

	// 1 & 2 Aktueller Status Label
	private Label lblStatus;

	// 0
	public Geo_View(Stage primaryStage, Geo_Model model) {
		this.stage = primaryStage;
		this.model = model;

		// VIEWS
//		this.add(createCountryView(), 0, 0);

		// Control Pane
		this.add(createControlPane(), 0, 2);

		// DATA DISPLAY PANE
		// Initialisieren der TableView
		this.tableView = createTableView();
		this.add(tableView, 0, 3);

		// SZENEN
		mainScene = new Scene(this);
		countryScene = new Scene(createCountryView(), 300,300);
		stateScene = new Scene(createStateView(), 300,300);

		// 1 Aktueller Status
		this.lblStatus = new Label("Everything okay");
		this.add(this.lblStatus, 0, 10);

		// 1 Create the scene using our layout; then display it
//		mainScene = new Scene(this);
		mainScene.getStylesheets().add(getClass().getResource("view.css").toExternalForm());
		stage.setScene(mainScene);
		stage.setTitle("Geography Miniproject");
		stage.setResizable(true);
	}

	public Geo_Model getModel() {
		return model;
	}

	public void setModel(Geo_Model model) {
		this.model = model;
	}

	public Scene getMainScene() {
		return mainScene;
	}

	public void setMainScene(Scene mainScene) {
		this.mainScene = mainScene;
	}

	// 0
	public void start() {
		stage.show();

	}

	// Methode um die Kontrollelemente zu erzeugen
	public Pane createControlPane() {
		GridPane pane = new GridPane();
		btnCreateCountry = new Button("Create");
		btnDeleteCountry = new Button("Delete");
		btnUpdateCountry = new Button("Update");
		pane.add(btnCreateCountry, 0, 0);
		pane.add(btnDeleteCountry, 2, 0);
		pane.add(btnUpdateCountry, 4, 0);
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

		// Country Spalte
		colCountry = new TableColumn<>("Country"); // Erstellen und Beschriftung der Spalte
		colCountry.setMinWidth(200);
		colCountry.setCellValueFactory(new PropertyValueFactory<>("name")); // Insatnzieren ein Property und übergeben
		tableView.getColumns().add(colCountry); // Fügen der TableView die Spalte hinzu

		// Area Spalte
		colArea = new TableColumn<>("Area");
		colArea.setMinWidth(200);
		colArea.setCellValueFactory(new PropertyValueFactory<>("area"));
		tableView.getColumns().add(colArea);

		// Population Spalte
		colPopulation = new TableColumn<>("Population");
		colPopulation.setMinWidth(200);
		colPopulation.setCellValueFactory(new PropertyValueFactory<>("population"));
		tableView.getColumns().add(colPopulation);

		// Government Spalte
		colFormOfGov = new TableColumn<>("Form of Government");
		colFormOfGov.setMinWidth(200);
		colFormOfGov.setCellValueFactory(new PropertyValueFactory<GovernedRegion, String>("formOfGovernment"));
		tableView.getColumns().add(colFormOfGov);

		// Finally, attach the tableView to the ObservableList of data
		tableView.setItems(model.getAllData());

		return tableView;
	}

	// Methode die Country View zu erzeugen
	public Pane createCountryView() {
		Pane pane = new Pane();
		this.setCountryView(new CountryView(stage, model, controller));
		pane.getChildren().add(this.getCountryView());
		return pane;

	}

	// Methode die Country View zu erzeugen
	public Pane createStateView() {
		Pane pane = new Pane();
		this.setStateView(new StateView(stage, model, controller));
		pane.getChildren().add(this.getStateView());
		return pane;

	}

	// Methode um den Status zu aktualiseren
	public void setStatus(String message) {
		this.lblStatus.setText(message); // status = Label

	}

	// Getter für die CountryView
	public CountryView getCountryView() {
		return this.countryView;
	}

	// Setter für die CountryView
	public void setCountryView(CountryView countryView) {
		this.countryView = countryView;
	}

	// Getter für die StateView
	public StateView getStateView() {
		return this.stateView;
	}

	// Setter für die StateView
	public void setStateView(StateView stateView) {
		this.stateView = stateView;
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
	public TableColumn<GovernedRegion, Double> getColArea() {
		return colArea;
	}

	// Setter
	public void setColArea(TableColumn<GovernedRegion, Double> colArea) {
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

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public SplashView getSplashView() {
		return splashView;
	}

	public void setSplashView(SplashView splashView) {
		this.splashView = splashView;
	}

	public Scene getSplashScene() {
		return splashScene;
	}

	public void setSplashScene(Scene splashScene) {
		this.splashScene = splashScene;
	}

	public Scene getCountryScene() {
		return this.countryScene;
	}

	public void setCountryScene(Scene countryScene) {
		this.countryScene = countryScene;
	}

	public Scene getStateScene() {
		return stateScene;
	}

	public void setStateScene(Scene stateScene) {
		this.stateScene = stateScene;
	}

	public Button getBtnCreateCountry() {
		return btnCreateCountry;
	}

	public void setBtnCreateCountry(Button btnCreateCountry) {
		this.btnCreateCountry = btnCreateCountry;
	}

	public Button getBtnDeleteCountry() {
		return btnDeleteCountry;
	}

	public void setBtnDeleteCountry(Button btnDeleteCountry) {
		this.btnDeleteCountry = btnDeleteCountry;
	}

	public Button getBtnUpdateCountry() {
		return btnUpdateCountry;
	}

	public void setBtnUpdateCountry(Button btnUpdateCountry) {
		this.btnUpdateCountry = btnUpdateCountry;
	}

}
