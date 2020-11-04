package mvc;

import controller.Geo_Controller;

import javafx.application.Application;
import javafx.stage.Stage;
import model.FormOfGovernment;
import model.Geo_Model;
import view.Geo_View;

//Stufe 0 = Basis MVC Struktur
//Stufe 1 = Die View

// 0
public class Geo_MVC extends Application {

	private static final FormOfGovernment DEMOCRACY = null;
	private static final String Schweiz = "Schweiz";
	
	// 0
	private Geo_Model model;
	private Geo_View view;
	private Geo_Controller controller;

	// 0
	public static void main(String[] args) {
		launch(args); // Ruft Fenster des OS auf
	}

	// 0
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Initialize the three MVC components
		this.model = new Geo_Model();
		this.view = new Geo_View(primaryStage, model);
		this.controller = new Geo_Controller(model, view);

		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);
		model.createNewCountry(Schweiz, 10, 10, DEMOCRACY);

		// Zeigt GUI an sobald alles initialisiert ist
		view.start();
	}

}
