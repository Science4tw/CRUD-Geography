package view;

import java.util.Locale;
import java.util.logging.Logger;

import abstractClasses.View;
import commonClasses.Translator;
import mvc.ServiceLocator;
import controller.App_Controller;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.App_Model;
import model.GovernedRegion;

// 0
public class App_View extends GridPane { // 1 extends BorderPane

	// 0
	private Stage stage;
	private App_Model model;
	// 1 (abklären ob das so machbar ist)
	private App_Controller controller;

	// VIEWS
	private SplashView splashView;
	private CountryView countryView;
	private StateView stateView;
	private UpdateView updateView;
	
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
	
	// Controls State
	private Button btnCreateState;
	private Button btnDeleteState;
	private Button btnUpdateState;

	// 1 (Data Display) die TableView für die Countries
	protected TableView<GovernedRegion> tableView;
	protected TableColumn<GovernedRegion, String> colCountry;
	protected TableColumn<GovernedRegion, Double> colArea;
	protected TableColumn<GovernedRegion, Integer> colPopulation;
	protected TableColumn<GovernedRegion, String> colFormOfGov;
	
	// 1 (Data Display) die TableView für die States
	protected TableView<GovernedRegion> stateTableView;
	protected TableColumn<GovernedRegion, String> colState;
	protected TableColumn<GovernedRegion, Double> colStateArea;
	protected TableColumn<GovernedRegion, Integer> colStatePopulation;
	protected TableColumn<GovernedRegion, String> colStateFormOfGov;

	// *** MENUS ***
	protected Menu menuFile;
	protected Menu menuFileLanguage;
	protected Menu menuHelp;
	
	// 1 & 2 Aktueller Status Label
	private Label lblStatus;

	// 0
	public App_View(Stage primaryStage, App_Model model) {
		this.stage = primaryStage;
		this.model = model;
		ServiceLocator.getServiceLocator().getLogger().info("Application view initialized");
		
		// VIEWS
//		this.add(createCountryView(), 0, 0);

		// MENU Bereich initialisieren
		this.add(createMenuPane(), 0, 1);
		
		
		// Control Pane mit den Buttons Create, Delete und Update in der App_View für die Country View
		this.add(createControlPane(), 0, 2);
		
		// Control Pane mit den Buttons Create, Delete und Update in der App_View für die State View
		this.add(createControlPaneState(), 6, 2);

		// DATA DISPLAY PANE Country View
		// Initialisieren der TableView
		this.tableView = createTableView();
		this.add(tableView, 0, 3);
		
		this.stateTableView = createStateTableView();
		this.add(stateTableView, 6, 3);

		// SZENEN
		mainScene = new Scene(this);
		countryScene = new Scene(createCountryView(), 300,300);
		stateScene = new Scene(createStateView(), 300,300);
		updateScene = new Scene(createUpdateView(), 300, 300);

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

	// Methode um die Kontrollelemente zu erzeugen
	public Pane createControlPane() {
		GridPane pane = new GridPane();
		btnCreateCountry = new Button("Create Country");
		btnDeleteCountry = new Button("Delete Country");
		btnUpdateCountry = new Button("Update Country");
		pane.add(btnCreateCountry, 0, 0);
		pane.add(btnDeleteCountry, 2, 0);
		pane.add(btnUpdateCountry, 4, 0);
		return pane;
	}

	public Pane createControlPaneState() {
		GridPane pane = new GridPane();
		btnCreateState = new Button("Create State");
		btnDeleteState= new Button("Delete State");
		btnUpdateState= new Button("Update State");
		pane.add(btnCreateState, 0, 0);
		pane.add(btnDeleteState, 2, 0);
		pane.add(btnUpdateState, 4, 0);
		return pane;
	}
	/*
	 * 1, 2 & 3 Data Display Pane TableView für die COUNTRY Liste
	 */
	private TableView<GovernedRegion> createTableView() {
		this.tableView = new TableView<GovernedRegion>();
		this.tableView.setEditable(false);

		// Each column needs a title, and a source of data.
		// For editable columns, each column needs to contain a TextField.

		// Country Spalte
		colCountry = new TableColumn<>("Country"); // Erstellen und Beschriftung der Spalte
		colCountry.setMinWidth(50);
		colCountry.setCellValueFactory(new PropertyValueFactory<>("name")); // Insatnzieren ein Property und übergeben
		tableView.getColumns().add(colCountry); // Fügen der TableView die Spalte hinzu

		// Area Spalte
		colArea = new TableColumn<>("Area");
		colArea.setMinWidth(50);
		colArea.setCellValueFactory(new PropertyValueFactory<>("area"));
		tableView.getColumns().add(colArea);

		// Population Spalte
		colPopulation = new TableColumn<>("Population");
		colPopulation.setMinWidth(50);
		colPopulation.setCellValueFactory(new PropertyValueFactory<>("population"));
		tableView.getColumns().add(colPopulation);

		// Government Spalte
		colFormOfGov = new TableColumn<>("Form of Government");
		colFormOfGov.setMinWidth(50);
		colFormOfGov.setCellValueFactory(new PropertyValueFactory<GovernedRegion, String>("formOfGovernment"));
		tableView.getColumns().add(colFormOfGov);

		// Finally, attach the tableView to the ObservableList of data
		tableView.setItems(model.getAllData());

		return tableView;
	}

	/*
	 * 1, 2 & 3 Data Display Pane TableView für die Country Liste
	 */
	private TableView<GovernedRegion> createStateTableView() {
		this.stateTableView = new TableView<GovernedRegion>();
		this.stateTableView.setEditable(false);

		// Each column needs a title, and a source of data.
		// For editable columns, each column needs to contain a TextField.

		// Country Spalte
		colState = new TableColumn<>("State"); // Erstellen und Beschriftung der Spalte
		colState.setMinWidth(50);
		colState.setCellValueFactory(new PropertyValueFactory<>("name")); // Insatnzieren ein Property und übergeben
		stateTableView.getColumns().add(colState); // Fügen der TableView die Spalte hinzu

		// Area Spalte
		colStateArea = new TableColumn<>("Area State");
		colStateArea.setMinWidth(50);
		colStateArea.setCellValueFactory(new PropertyValueFactory<>("area"));
		stateTableView.getColumns().add(colStateArea);

		// Population Spalte
		colStatePopulation = new TableColumn<>("Population State");
		colStatePopulation.setMinWidth(50);
		colStatePopulation.setCellValueFactory(new PropertyValueFactory<>("population"));
		stateTableView.getColumns().add(colStatePopulation);

		// Government Spalte
		colStateFormOfGov = new TableColumn<>("Form of Government State");
		colStateFormOfGov.setMinWidth(50);
		colStateFormOfGov.setCellValueFactory(new PropertyValueFactory<GovernedRegion, String>("formOfGovernment"));
		stateTableView.getColumns().add(colStateFormOfGov);

		// Finally, attach the tableView to the ObservableList of data
		stateTableView.setItems(model.getAllDataState());

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
	
	// Methode um die MainView zu erzeugen
	public Pane createUpdateView() {
		Pane pane = new Pane();
		this.setUpdateView(new UpdateView(stage, model, controller));
		pane.getChildren().add(this.getUpdateView());
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
	// Getter für die UpdateView
	public UpdateView getUpdateView() {
		return this.updateView;
	}
	
	// Setter für die UpdateView
	public void setUpdateView(UpdateView updateView) {
		this.updateView = updateView;
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
	public App_Controller getController() {
		return controller;
	}

	public void setController(App_Controller controller) {
		this.controller = controller;
	}

	// *****   *****
	
	// *****   *****
	
	// *****   *****
	
	// **** SZENEN *****
	// (UPDATE)
	public Scene getUpdateScene() {
		return updateScene;
	}

	public void setUpdateScene(Scene updateScene) {
		this.updateScene = updateScene;
	}

	public void stop() {
		stage.hide();
		// TODO Auto-generated method stub
		
	}

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

	public TableColumn<GovernedRegion, Integer> getColPopulation() {
		return colPopulation;
	}

	public void setColPopulation(TableColumn<GovernedRegion, Integer> colPopulation) {
		this.colPopulation = colPopulation;
	}

	public TableView<GovernedRegion> getStateTableView() {
		return stateTableView;
	}

	public void setStateTableView(TableView<GovernedRegion> stateTableView) {
		this.stateTableView = stateTableView;
	}

	public TableColumn<GovernedRegion, String> getColState() {
		return colState;
	}

	public void setColState(TableColumn<GovernedRegion, String> colState) {
		this.colState = colState;
	}

	public TableColumn<GovernedRegion, Double> getColStateArea() {
		return colStateArea;
	}

	public void setColStateArea(TableColumn<GovernedRegion, Double> colStateArea) {
		this.colStateArea = colStateArea;
	}

	public TableColumn<GovernedRegion, Integer> getColStatePopulation() {
		return colStatePopulation;
	}

	public void setColStatePopulation(TableColumn<GovernedRegion, Integer> colStatePopulation) {
		this.colStatePopulation = colStatePopulation;
	}

	public TableColumn<GovernedRegion, String> getColStateFormOfGov() {
		return colStateFormOfGov;
	}

	public void setColStateFormOfGov(TableColumn<GovernedRegion, String> colStateFormOfGov) {
		this.colStateFormOfGov = colStateFormOfGov;
	}

	public UpdateViewState getUpdateViewState() {
		return this.updateViewState;
	}

	public void setUpdateViewState(UpdateViewState updateViewState) {
		this.updateViewState = updateViewState;
	}

	public Scene getUpdateSceneState() {
		return updateSceneState;
	}

	public void setUpdateSceneState(Scene updateSceneState) {
		this.updateSceneState = updateSceneState;
	}

//	@Override
//	protected Scene create_GUI() {
//		// TODO Auto-generated method stub
//		return null;
//	}	
	
	// **** VIEWS *****
	
//	@Override
	protected Pane createMenuPane() {
	    ServiceLocator sl = ServiceLocator.getServiceLocator();  
	    Logger logger = sl.getLogger();
	    
	    MenuBar menuBar = new MenuBar();
	    menuFile = new Menu();
	    menuFileLanguage = new Menu();
	    menuFile.getItems().add(menuFileLanguage);
	    
       for (Locale locale : sl.getLocales()) {
           MenuItem language = new MenuItem(locale.getLanguage());
           menuFileLanguage.getItems().add(language);
           language.setOnAction( event -> {
				sl.getConfiguration().setLocalOption("Language", locale.getLanguage());
                sl.setTranslator(new Translator(locale.getLanguage()));
                updateTexts();
            });
        }
	    
        menuHelp = new Menu();
	    menuBar.getMenus().addAll(menuFile, menuHelp);
		
		GridPane root = new GridPane();
		root.add(menuBar, 0, 0);
		
        
        updateTexts();
		
//        Scene scene = new Scene(root);
//        scene.getStylesheets().add(
//                getClass().getResource("app.css").toExternalForm());
        return root;
	}
	
	   protected void updateTexts() {
	       Translator t = ServiceLocator.getServiceLocator().getTranslator();
	        
	        // The menu entries
	       menuFile.setText(t.getString("program.menu.file"));
	       menuFileLanguage.setText(t.getString("program.menu.file.language"));
           menuHelp.setText(t.getString("program.menu.help"));
	        
	        // Other controls
           
           
           stage.setTitle(t.getString("program.name"));
	    }	
}
