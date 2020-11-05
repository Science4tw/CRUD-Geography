package testing;

import controller.App_Controller;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.App_Model;
import model.GovernedRegion;

// 0
public class Geo_View extends BorderPane { // 1 extends BorderPane 

	// 0
	private Stage stage;
	private App_Model model;
	// 1 (abklären ob das so machbar ist)
	private App_Controller controller;
	
	// 1 TABPane um die Tabs zu organisieren
	private TabPane centerPane;
	
	// 1
	// Behälter um Country Objekte zu managen
	private CountryView countryView;
	private Tab tabCountry;
	
	// 1 & 2 Aktueller Status Label
	private Label lblStatus;
	
	// 0
	public Geo_View(Stage primaryStage, App_Model model) {
		this.stage = primaryStage;
		this.model = model;
		
		/**
		 * Hinweis: Um den Konstruktor der View mit möglichst wenig Zeilen Code zu implementieren,
		 * sind die CRUD Bereiche in 2 Tabs aufgeteilt, beide werden mit einer separaten Methode
		 * erzeugt 
		 * 1. Instanzieren neuen Tab Bereich, welcher Tab Objekte aufnehmen kann
		 * 2. Fügen dem Tab Bereich alle Tab Objekte hinzu
		 * 3. Setzen in DIESEM Objekt (also der Geopgraphie_View, welche eine BorderPane ist
		 * 		im CENTER den Tab Bereich
		 */				
		this.centerPane = new TabPane();
		this.centerPane.getTabs().add(createCountryView());
		this.setCenter(this.centerPane);
		
		// 1 Aktueller Status
		this.lblStatus = new Label("Everything okay");
		this.setBottom(this.lblStatus);
		
		// 1 Create the scene using our layout; then display it
		Scene scene = new Scene(this);
		scene.getStylesheets().add(getClass().getResource("view.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("Geography Miniproject");
		stage.setResizable(true);		
	}

	/**
	 * 1 Manage Bereich (beinhaltet Funktionalitäten: Read, Update, Delete, Search)
	 * ManagePane
	 * INPUT:	Keinen
	 * OUTPUT:	Tab
	 * 1. Instanzieren neuen Bereich um Objekte zu mangen und übergeben diesen Bereich dem controller
	 * 2. Instanzieren neuen Tab
	 * 3. Tab nicht schliessbar
	 * 4. Beschriftung der Tab
	 * 5. Setzen den Bereich als Inhalt des Tabs
	 * @return
	 */
	public Tab createCountryView() {
		this.setCountryView(new CountryView(stage, model, controller));
		this.tabCountry = new Tab();
		this.tabCountry.setClosable(false);
		this.tabCountry.setText("Create Country");
		this.tabCountry.setContent(this.getCountryView());
		return this.tabCountry;
	}
	
	public Pane createCountryView1() {
		Pane pane = new Pane();
		this.setCountryView(new CountryView(stage, model, controller));
		pane.getChildren().add(this.getCountryView());
		return pane;
		
	}
	// 0
	public void start() {
		stage.show();
		
	}

	// Getter für die CountryView
	public CountryView getCountryView() {
		return this.countryView;
	}

	// Setter für die CountryView
	public void setCountryView(CountryView countryView) {
		this.countryView = countryView;
	}

	// Methode um den Status zu aktualiseren
	public void setStatus(String message) {
		this.lblStatus.setText(message); // status = Label

	}

	// Getter für die TableView
	public TableView<GovernedRegion> getTableView() {
		// TODO Auto-generated method stub
		return this.getCountryView().getTableView();
	}


}
