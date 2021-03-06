package view;

import java.util.Locale;
import java.util.logging.Logger;

import abstractClasses.View;
import commonClasses.Translator;
import mvc.ServiceLocator;
import controller.App_Controller;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.App_Model;
import model.Country;
import model.State;

// 0
public class App_View extends View<App_Model> { // 1 extends BorderPane

	// 0

	private App_Controller controller;

	// VIEWS
	private CountryView countryView;
	private UpdateViewCountry updateViewCountry;

	private StateView stateView;
	private UpdateViewState updateViewState;
	
	// SZENEN
	private Scene splashScene;
	private Scene mainScene; // -> App_View (GridPane)
	private Scene countryScene;
	private Scene updateScene;

	private Scene stateScene;
	private Scene updateSceneState;

	// Controls Country
	private Button btnCreateCountry;
	private Button btnDeleteCountry;
	private Button btnUpdateCountry;
	private Label lblCountryTitle;
	
	// Controls State
	private Button btnCreateState;
	private Button btnDeleteState;
	private Button btnUpdateState;
	private Label lblStateTitle;

	// 1 (Data Display) die TableView für die Countries
	protected TableView<Country> tableView;
	protected TableColumn<Country, String> colCountry;
	protected TableColumn<Country, Double> colArea;
	protected TableColumn<Country, Integer> colPopulation;
	protected TableColumn<Country, String> colFormOfGov;

	// 1 (Data Display) die TableView für die States
	protected TableView<State> stateTableView;
	protected TableColumn<State, String> colState;
	protected TableColumn<State, Double> colStateArea;
	protected TableColumn<State, Integer> colStatePopulation;
	protected TableColumn<State, String> colStateFormOfGov;
	protected TableColumn<State, String> colMyCountry;

	//Label MyStates
	private Label lblMyStatesTitle;
	
	// MyStates VIEW
	protected TableView<State> myStatesTableView;
	protected TableColumn<State, String> colMyStates;
	protected TableColumn<State, Double> colMyStateArea;
	protected TableColumn<State, Integer> colMyStatePopulation;
	protected TableColumn<State, String> colMyStateFormOfGov;


	// *** MENUS ***
	protected Menu menuFile;
	protected Menu menuFileLanguage;
	protected Menu menuHelp;
	protected MenuItem menuHelpShortcuts;

	// 1 & 2 Aktueller Status Label
	private Label lblStatus;

	// 0 Konstruktor
	public App_View(Stage primaryStage, App_Model model) {
		super(primaryStage, model);

		ServiceLocator.getServiceLocator().getLogger().info("Application view initialized");
		mainScene = create_GUI();

		stage.setScene(mainScene);
		stage.setResizable(true);
		mainScene.getStylesheets().add(getClass().getResource("view.css").toExternalForm());

	}

	public App_Model getModel() {
		return model;
	}

	public void setModel(App_Model model) {
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

	// 0
	public void stop() {
		stage.hide();

	}

	// Methode um die Kontrollelemente zu erzeugen (Country)
	public Pane createControlPane() {
		GridPane pane = new GridPane();
		lblCountryTitle = new Label("Countries");
		lblCountryTitle.getStyleClass().add("titleLabel");
		btnCreateCountry = new Button("Create Country");
		btnDeleteCountry = new Button("Delete Country");
		btnUpdateCountry = new Button("Update Country");
		pane.add(lblCountryTitle, 0, 0);
		pane.add(btnCreateCountry, 2, 0);
		pane.add(btnDeleteCountry, 4, 0);
		pane.add(btnUpdateCountry, 6, 0);
		pane.setHgap(5);
		pane.setPadding(new Insets(10, 10, 10, 10));
		return pane;
	}

	// Methode um die Kontrollelemente zu erzeugen (State)
	public Pane createControlPaneState() {
		GridPane pane = new GridPane();
		lblStateTitle = new Label("States");
		lblStateTitle.getStyleClass().add("titleLabel");
		btnCreateState = new Button("Create State");
		btnDeleteState = new Button("Delete State");
		btnUpdateState = new Button("Update State");
		pane.add(lblStateTitle, 0, 0);
		pane.add(btnCreateState, 2, 0);
		pane.add(btnDeleteState, 4, 0);
		pane.add(btnUpdateState, 6, 0);
		pane.setHgap(5);
		pane.setPadding(new Insets(10, 10, 10, 10));
		return pane;
	}

	/*
	 * 1, 2 & 3 Data Display Pane TableView für die "countries" Liste
	 */
	private TableView<Country> createTableView() {
		this.tableView = new TableView<Country>();
		this.tableView.setEditable(false);
		this.tableView.setPlaceholder(new Label("-"));

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
		colFormOfGov.setCellValueFactory(new PropertyValueFactory<Country, String>("formOfGovernment"));
		tableView.getColumns().add(colFormOfGov);

		// Finally, attach the tableView to the ObservableList of data
		tableView.setItems(model.getCountries());

		return tableView;
	}

	/*
	 * 1, 2 & 3 Data Display Pane TableView für die "states" Liste
	 */
	private TableView<State> createStateTableView() {
		this.stateTableView = new TableView<State>();
		this.stateTableView.setEditable(false);
		this.stateTableView.setPlaceholder(new Label("-"));		

		// Each column needs a title, and a source of data.
		// For editable columns, each column needs to contain a TextField.

		// Country Spalte
		colState = new TableColumn<>("State"); // Erstellen und Beschriftung der Spalte
		colState.setMinWidth(200);
		colState.setCellValueFactory(new PropertyValueFactory<>("name")); // Insatnzieren ein Property und übergeben
		stateTableView.getColumns().add(colState); // Fügen der TableView die Spalte hinzu

		// Area Spalte
		colStateArea = new TableColumn<>("Area State");
		colStateArea.setMinWidth(200);
		colStateArea.setCellValueFactory(new PropertyValueFactory<>("area"));
		stateTableView.getColumns().add(colStateArea);

		// Population Spalte
		colStatePopulation = new TableColumn<>("Population State");
		colStatePopulation.setMinWidth(200);
		colStatePopulation.setCellValueFactory(new PropertyValueFactory<>("population"));
		stateTableView.getColumns().add(colStatePopulation);

		// Government Spalte
		colStateFormOfGov = new TableColumn<>("Form of Government State");
		colStateFormOfGov.setMinWidth(200);
		colStateFormOfGov.setCellValueFactory(new PropertyValueFactory<State, String>("formOfGovernment"));
		stateTableView.getColumns().add(colStateFormOfGov);

		colMyCountry = new TableColumn<>("My Country");
		colMyCountry.setMinWidth(200);
		colMyCountry.setCellValueFactory(new PropertyValueFactory<State, String>("myCountryName"));
		stateTableView.getColumns().add(colMyCountry);

		// Finally, attach the tableView to the ObservableList of data
		stateTableView.setItems(model.getStates());

		return stateTableView;
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

	// Methode um die UpdateView zu erzeugen
	public Pane createUpdateView() {
		Pane pane = new Pane();
		this.setUpdateView(new UpdateViewCountry(stage, model, controller));
		pane.getChildren().add(this.getUpdateViewCountry());
		return pane;
	}

	// Methode um die UpdateViewState zu erzeugen
	public Pane createUpdateViewState() {
		Pane pane = new Pane();
		this.setUpdateViewState(new UpdateViewState(stage, model, controller));
		pane.getChildren().add(this.getUpdateViewState());
		return pane;
	}

	// Methode um den Status zu aktualiseren
	public void setStatus(String message) {
		this.lblStatus.setText(message); // status = Label
	}

	// Getter
	public TableView<Country> getTableView() {
		return this.tableView;
	}

	// Setter
	public void setTableView(TableView<Country> tableView) {
		this.tableView = tableView;
	}

	// Getter
	public TableColumn<Country, String> getColCountry() {
		return colCountry;
	}

	// Setter
	public void setColCountry(TableColumn<Country, String> colCountry) {
		this.colCountry = colCountry;
	}

	// Getter
	public TableColumn<Country, Double> getColArea() {
		return colArea;
	}

	// Setter
	public void setColArea(TableColumn<Country, Double> colArea) {
		this.colArea = colArea;
	}

	// Getter
	public TableColumn<Country, String> getColFormOfGov() {
		return colFormOfGov;
	}

	// Setter
	public void setColFormOfGov(TableColumn<Country, String> colFormOfGov) {
		this.colFormOfGov = colFormOfGov;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public MenuItem getMenuHelpShortcuts() {
		return menuHelpShortcuts;
	}

	public void setMenuHelpShortcuts(MenuItem menuHelpShortcuts) {
		this.menuHelpShortcuts = menuHelpShortcuts;
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

	public App_Controller getController() {
		return controller;
	}

	public void setController(App_Controller controller) {
		this.controller = controller;
	}

	// ***** VIEWS ***** (GETTER & SETTER)
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

	// Getter für die UpdateView
	public UpdateViewCountry getUpdateView() {
		return this.updateViewCountry;
	}

	// Setter für die UpdateView
	public void setUpdateView(UpdateViewCountry updateViewCountry) {
		this.updateViewCountry = updateViewCountry;
	}

	public UpdateViewCountry getUpdateViewCountry() {
		return updateViewCountry;
	}

	// Setter für die UpdateViewCountry
	public void setUpdateViewCountry(UpdateViewCountry updateViewCountry) {
		this.updateViewCountry = updateViewCountry;
	}

	// ***** SZENEN ***** (GETTER & SETTER)
	// (UPDATE)
	public Scene getUpdateScene() {
		return updateScene;
	}

	public void setUpdateScene(Scene updateScene) {
		this.updateScene = updateScene;
	}

	// (UPDATE STATE)
	public Scene getUpdateSceneState() {
		return updateSceneState;
	}

	public void setUpdateSceneState(Scene updateSceneState) {
		this.updateSceneState = updateSceneState;
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

	// ***** ENDE SZENEN *****

	// ***** CONTROLS ***** (GETTER & SETTER)
	// CONTROLS STATE
	public Button getBtnCreateState() {
		return btnCreateState;
	}

	public void setBtnCreateState(Button btnCreateState) {
		this.btnCreateState = btnCreateState;
	}

	public Button getBtnDeleteState() {
		return btnDeleteState;
	}

	public void setBtnDeleteState(Button btnDeleteState) {
		this.btnDeleteState = btnDeleteState;
	}

	public Button getBtnUpdateState() {
		return btnUpdateState;
	}

	public void setBtnUpdateState(Button btnUpdateState) {
		this.btnUpdateState = btnUpdateState;
	}

	// **** TABLEVIEWS ***
	// Countries
	public void setColPopulation(TableColumn<Country, Integer> colPopulation) {
		this.colPopulation = colPopulation;
	}

	// States
	public TableView<State> getStateTableView() {
		return stateTableView;
	}

	public void setStateTableView(TableView<State> stateTableView) {
		this.stateTableView = stateTableView;
	}

	public TableColumn<State, String> getColState() {
		return colState;
	}

	public void setColState(TableColumn<State, String> colState) {
		this.colState = colState;
	}

	public TableColumn<State, Double> getColStateArea() {
		return colStateArea;
	}

	public void setColStateArea(TableColumn<State, Double> colStateArea) {
		this.colStateArea = colStateArea;
	}

	public TableColumn<State, Integer> getColStatePopulation() {
		return colStatePopulation;
	}

	public void setColStatePopulation(TableColumn<State, Integer> colStatePopulation) {
		this.colStatePopulation = colStatePopulation;
	}

	public UpdateViewState getUpdateViewState() {
		return this.updateViewState;
	}

	public void setUpdateViewState(UpdateViewState updateViewState) {
		this.updateViewState = updateViewState;
	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Logger logger = sl.getLogger();

		MenuBar menuBar = new MenuBar();
		menuFile = new Menu();
		menuFileLanguage = new Menu();
		menuFile.getItems().add(menuFileLanguage);

		for (Locale locale : sl.getLocales()) {
			MenuItem language = new MenuItem(locale.getLanguage());
			menuFileLanguage.getItems().add(language);
			language.setOnAction(event -> {
				sl.getConfiguration().setLocalOption("Language", locale.getLanguage());
				sl.setTranslator(new Translator(locale.getLanguage()));
				updateTexts();
			});
		}

		menuHelp = new Menu();
		menuHelpShortcuts = new MenuItem("Shortcuts");
		menuHelp.getItems().add(menuHelpShortcuts);
		menuBar.getMenus().addAll(menuFile, menuHelp);

		GridPane root = new GridPane();
		root.add(menuBar, 0, 0);

		// Control Pane mit den Buttons Create, Delete und Update in der App_View für
		// die Country View
		root.add(createControlPane(), 0, 3);

		// DATA DISPLAY PANE Country TableView
		// Initialisieren der TableView
		this.tableView = createTableView();
		root.add(tableView, 0, 4);
		this.tableView.prefWidthProperty().bind(root.widthProperty());

		// Control Pane mit den Buttons Create, Delete und Update in der App_View für
		// die State View
		root.add(createControlPaneState(), 0, 6);

		// DATA DISPLAY PANE STATE TableView
		// Initialisieren der TableView
		this.stateTableView = createStateTableView();
		root.add(stateTableView, 0, 7);

		this.lblMyStatesTitle = new Label("My States");
		this.lblMyStatesTitle.getStyleClass().add("titleLabel");
		root.add(this.lblMyStatesTitle, 0, 8);
		this.myStatesTableView = createMyStatesTablewView();
		root.add(myStatesTableView, 0, 9);

		// SZENEN
		countryScene = new Scene(createCountryView(), 450, 450);
		stateScene = new Scene(createStateView(), 450, 450);
		updateScene = new Scene(createUpdateView(), 450, 450);
		updateSceneState = new Scene(createUpdateViewState(), 450, 450);

		// 1 Aktueller Status
		this.lblStatus = new Label("Everything okay");
		this.lblStatus.getStyleClass().add("statusLabel");
		root.add(this.lblStatus, 0, 10);

		updateTexts();

		Scene scene = new Scene(root);

		return scene;
	}

	protected void updateTexts() {
		Translator t = ServiceLocator.getServiceLocator().getTranslator();

		// Common controls
		stage.setTitle(t.getString("program.name"));
		lblStatus.setText(t.getString("program.lblStatus"));
		
		// The menu entries
		menuFile.setText(t.getString("program.menu.file"));
		menuFileLanguage.setText(t.getString("program.menu.file.language"));
		menuHelp.setText(t.getString("program.menu.help"));
		menuHelpShortcuts.setText(t.getString("program.menu.help.shortcuts"));
		
		// TabelView Country
		getTableView().getColumns().get(0).setText(t.getString("row.tableview.country"));
		getTableView().getColumns().get(1).setText(t.getString("row.tableview.area"));
		getTableView().getColumns().get(2).setText(t.getString("row.tableview.population"));
		getTableView().getColumns().get(3).setText(t.getString("row.tableview.formOfGov"));
		
		// TabelView State
		getStateTableView().getColumns().get(0).setText(t.getString("row.stateTableview.state"));
		getStateTableView().getColumns().get(1).setText(t.getString("row.stateTableview.area"));
		getStateTableView().getColumns().get(2).setText(t.getString("row.stateTableview.population"));
		getStateTableView().getColumns().get(3).setText(t.getString("row.stateTableview.formOfGov"));
		getStateTableView().getColumns().get(4).setText(t.getString("row.stateTableview.country"));
		
		// TabelView dependency
		getMyStatesTableView().getColumns().get(0).setText(t.getString("row.stateTableview.state"));
		getMyStatesTableView().getColumns().get(1).setText(t.getString("row.stateTableview.area"));
		getMyStatesTableView().getColumns().get(2).setText(t.getString("row.stateTableview.population"));
		getMyStatesTableView().getColumns().get(3).setText(t.getString("row.stateTableview.formOfGov"));
		
		lblMyStatesTitle.setText(t.getString("label.lblMyStatesTitle"));
		
		// Controls Country
		lblCountryTitle.setText(t.getString("label.lblCountryTitle"));
		btnCreateCountry.setText(t.getString("button.btnCreateCountry"));
		btnDeleteCountry.setText(t.getString("button.btnDeleteCountry"));
		btnUpdateCountry.setText(t.getString("button.btnUpdateCountry"));

		// Controls State
		lblStateTitle.setText(t.getString("label.lblStateTitle"));
		btnCreateState.setText(t.getString("button.btnCreateState"));
		btnDeleteState.setText(t.getString("button.btnDeleteState"));
		btnUpdateState.setText(t.getString("button.btnUpdateState"));
		
		// Labels CountryView
		countryView.getLblCountryTitle().setText(t.getString("countryview.label.title"));
		countryView.getLblCountry().setText(t.getString("countryview.label.name"));
		countryView.getLblArea().setText(t.getString("countryview.label.area"));
		countryView.getLblPopulation().setText(t.getString("countryview.label.population"));
		countryView.getLblFormOfGov().setText(t.getString("countryview.label.formOfGov"));
				
		// Buttons CountryView
		countryView.getBtnSave().setText(t.getString("countryview.button.save"));
		countryView.getBtnCancel().setText(t.getString("countryview.button.cancel"));
			
		// Labels StateView
		stateView.getLblStateTitle().setText(t.getString("stateview.label.title"));
		stateView.getLblState().setText(t.getString("stateview.label.name"));
		stateView.getLblAreaState().setText(t.getString("stateview.label.area"));
		stateView.getLblPopulationState().setText(t.getString("stateview.label.population"));
		stateView.getLblFormOfGovState().setText(t.getString("stateview.label.formOfGov"));
		stateView.getLblMyCountry().setText(t.getString("stateview.label.countryOfState"));
				
		// Buttons StateView
		stateView.getBtnCreateState().setText(t.getString("stateview.button.save"));
		stateView.getBtnCancelState().setText(t.getString("stateview.button.cancel"));

		// Labels UpdateView Country
		updateViewCountry.getLblUpdateCountryTitle().setText(t.getString("updateviewcountry.label.title"));
		updateViewCountry.getLblUpdateCountry().setText(t.getString("updateviewcountry.label.name"));
		updateViewCountry.getLblUpdateArea().setText(t.getString("updateviewcountry.label.area"));
		updateViewCountry.getLblUpdatePopulation().setText(t.getString("updateviewcountry.label.population"));
		updateViewCountry.getLblUpdateFormOfGov().setText(t.getString("updateviewcountry.label.formOfGov"));
		
		// Buttons UpdateView Country
		updateViewCountry.getBtnUpdateSave().setText(t.getString("updateviewcountry.button.save"));
		updateViewCountry.getBtnUpdateCancel().setText(t.getString("updateviewcountry.button.cancel"));

		// Labels UpdateView State
		updateViewState.getLblUpdateStateTitle().setText(t.getString("updateviewstate.label.title"));
		updateViewState.getLblUpdateState().setText(t.getString("updateviewstate.label.name"));
		updateViewState.getLblUpdateAreaState().setText(t.getString("updateviewstate.label.area"));
		updateViewState.getLblUpdatePopulationState().setText(t.getString("updateviewstate.label.population"));
		updateViewState.getLblUpdateFormOfGovState().setText(t.getString("updateviewstate.label.formOfGov"));
		updateViewState.getLblMyCountry().setText(t.getString("updateviewstate.label.country"));
		
		// Buttons UpdateView State
		updateViewState.getBtnUpdateSaveState().setText(t.getString("updateviewstate.button.save"));
		updateViewState.getBtnUpdateCancelState().setText(t.getString("updateviewstate.button.cancel"));
				
	}

	public TableColumn<State, String> getColMyStates() {
		return colMyStates;
	}

	public void setColMyStates(TableColumn<State, String> colMyStates) {
		this.colMyStates = colMyStates;
	}
	
	public TableView<State> getMyStatesTableView()
	{
		return this.myStatesTableView;
	}

	private TableView<State> createMyStatesTablewView() {
		this.myStatesTableView = new TableView<State>();
		this.myStatesTableView.setEditable(false);
		this.myStatesTableView.setPlaceholder(new Label("-"));

		colMyStates = new TableColumn<>("State");
		colMyStates.setMinWidth(200);
		colMyStates.setCellValueFactory(new PropertyValueFactory<>("name"));
		myStatesTableView.getColumns().add(colMyStates);
		
		colMyStateArea = new TableColumn<>("Area");
		colMyStateArea.setMinWidth(200);
		colMyStateArea.setCellValueFactory(new PropertyValueFactory<>("area"));
		myStatesTableView.getColumns().add(colMyStateArea);

		colMyStatePopulation = new TableColumn<>("Population");
		colMyStatePopulation.setMinWidth(200);
		colMyStatePopulation.setCellValueFactory(new PropertyValueFactory<>("population"));
		myStatesTableView.getColumns().add(colMyStatePopulation);

		colMyStateFormOfGov = new TableColumn<>("Form of Government State");
		colMyStateFormOfGov.setMinWidth(200);
		colMyStateFormOfGov.setCellValueFactory(new PropertyValueFactory<>("formOfGovernment"));
		myStatesTableView.getColumns().add(colMyStateFormOfGov);

		return myStatesTableView;
	}

	// **** GETTER & SETTER FUER DIE VIEWS ****
	// Country

	// State

	// **** ENDE ****

	// **** GETTER & SETTER FUER DIE SZENEN ****
	// Country

	// State

	// **** ENDE ****
}
