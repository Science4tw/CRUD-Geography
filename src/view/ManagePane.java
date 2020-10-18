package view;

import controller.Geo_Controller;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.GovernedRegion;

// 1
public class ManagePane extends GridPane{

	// 1 SEARCH
	private Label lblSearch = new Label("Suchbegriff eingeben: ");
	private TextField txtSearch;
	private Button btnSearch;
	
	// 1 SEARCH, READ, UPDATE, DELETE
	private ListView<GovernedRegion> resultList;
	
	// 1
	public ManagePane(Geo_Controller controller) {
		// 1 Label um das
		this.lblSearch = new Label("Searchstate");
		this.add(this.lblSearch, 1, 1);
		

		// 1 TextField für den Suchbegriff
		this.txtSearch = new TextField();
		this.add(this.txtSearch, 2, 1);
		
		// 1 Button der die Suche auslöst
		this.btnSearch = new Button("Search");
		this.add(this.btnSearch, 1, 3);
		
		// 1 ListView
		this.resultList = new ListView<GovernedRegion>();
		this.resultList.getItems().addAll();
		this.add(this.resultList, 1, 4);	}

}
