package view;

import controller.Geo_Controller;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Geo_Model;

// 0
public class Geo_View extends BorderPane { // 1 extends BorderPane 
	
	// 0
	private Stage stage;
	private Geo_Model model;

	// 1 TABPane um die Tabs zu organisieren
	private TabPane centerPane;

	// 1
	// Die Behälter um die CRUD Funktionalitäten zu implementieren + SEARCH
	private CreatePane createPane;
	private ManagePane managePane;
	
	// 1 CREATE, DELETE, READ, UPDATE, Search
	protected Tab createTab; // Create
	protected Tab manageTab; // DELETE, READ, UPDATE, Search	
	
	// 1 Aktueller Status Label
	private Label lblStatus;

	// 1 (abklären ob das so machbar ist)
	private Geo_Controller controller;
	
	// 0
	public Geo_View(Stage primaryStage, Geo_Model model) {
		this.stage = primaryStage;
		this.model = model;
		
		// 1 TabPane for CREATE, READ, UPDATE, DELETE, SEARCH
		/**
		 * Hinweis: Um den Konstruktor der View mit möglichst wenig Zeilen Code zu implementieren,
		 * sind die CRUD Bereiche in 2 Tabs aufgeteilt und beide werden mit einer separaten Methode
		 * erzeugt (createXXXXXXTab)
		 * 1. Instanzieren neuen Tab Bereich, welcher Tab Objekte aufnehmen kann
		 * 2. Fügen dem Tab Bereich alle Tab Objekte hinzu
		 * 3. Setzen in DIESEM Objekt (also der Geopgraphie_View, welche eine BorderPane ist
		 * 		im CENTER den Tab Bereich
		 */
		this.centerPane = new TabPane();
		this.centerPane.getTabs().addAll(createCreateTab(), createManageTab());
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

	// 1
	/**
	 * 1 Manage Bereich (beinhaltet Funktionalitäten: Read, Update, Delete, Search)
	 * ManagePane
	 * INPUT:	Keinen
	 * OUTPUT:	Tab
	 * 1. Instanzieren neuen Bereich um Objekte zu mangen und übergeben diesen Bereich dem controller
	 * 2. Instanzieren neuen Tab
	 * 3. Tab nicht schliessbar
	 * 4. Beschriftung der Tab
	 * 5. Setzen den Bereich um Objekte zu managen als Inhalt des Tabs
	 * @return
	 */
	private Tab createManageTab() {
		this.managePane = new ManagePane(controller); // diese Lösung wäre auch möglich: this.setManagePane(new ManagePane(controller));
		this.manageTab = new Tab();
		this.manageTab.setClosable(false);
		this.manageTab.setText("Manage Objects");
		this.manageTab.setContent(this.managePane); // diese Lösung wäre auch möglich: this.manageTab.setContent(this.getManagePane());
		return this.manageTab;
	}


	// 1 CREATE
	// CreatePane
	public Tab createCreateTab() {
		this.createPane = new CreatePane(controller);
		this.createTab = new Tab();
		this.createTab.setClosable(false);
		this.createTab.setText("Create Country");
		this.createTab.setContent(this.createPane);
		return this.createTab;

	}
	// 0
	public void start() {
		stage.show();
		
	}

}
