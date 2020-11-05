package mvc;

import controller.App_Controller;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Country;
import model.FormOfGovernment;
import model.App_Model;
import view.App_View;

//Stufe 0 = Basis MVC Struktur
//Stufe 1 = Die View

// 0
public class Geo_MVC extends Application {

	// Country TEST
	public FormOfGovernment DEMOCRACY = null;
	public String Schweiz = "Schweiz";
	
	// State TEST
	public String bern = "Bern";
	public String genf = "Genf";
	public String basel = "Basel";
	public String zuerich = "Zuerich";
	
	public Country schweiz = new Country("Schweiz", 0, 0, DEMOCRACY);
	public Country deutschland = new Country("deutschland", 0, 0, DEMOCRACY);
	public Country frankreich = new Country("frankreich", 0, 0, DEMOCRACY);
	
	// 0
	private App_Model model;
	private App_View view;
	private App_Controller controller;

	// 0
	public static void main(String[] args) {
		launch(args); // Ruft Fenster des OS auf
	}

	// 0
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Initialize the three MVC components
		this.model = new App_Model();
		this.view = new App_View(primaryStage, model);
		this.controller = new App_Controller(model, view);

		// Country TEST
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		
		// State TEST
		model.createNewState(bern, 1, 1, DEMOCRACY, schweiz);
		model.createNewState(basel, 1, 1, DEMOCRACY, schweiz);
		model.createNewState(zuerich, 1, 1, DEMOCRACY, schweiz);
		
		model.createNewState("Bayern", 1, 1, DEMOCRACY, deutschland);
		model.createNewState("Hessen", 1, 1, DEMOCRACY, deutschland);
		model.createNewState("Brandenburg", 1, 1, DEMOCRACY, deutschland);
		
		model.createNewState("Paris", 1, 1, DEMOCRACY, frankreich);
		model.createNewState("Lyon", 1, 1, DEMOCRACY, frankreich);
		model.createNewState("Marseille", 1, 1, DEMOCRACY, frankreich);

		// Zeigt GUI an sobald alles initialisiert ist
		view.start();
	}

}
