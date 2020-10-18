package view;

import controller.Geo_Controller;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

// 1
public class CreatePane extends GridPane {

	// 1 Kontrollelemente zum hinzufuegen der Objekte

	// 1 States
	private Label lblState;

	// 1 Nation
	private ComboBox<?> nationComboBox;
	private Label lblNation;

	// 1 FormOfGovernment
	private Label lblFormGovernment;
	private RadioButton democracyButton, dictatorshipButton, monarchyButton;
	private HBox hBoxGov;

	// 1 Area
	private Label lblArea;
	private TextField txfArea;

	// 1 Population
	private Label lblPopulation;
	private TextField txfPopulation;

	// 1 Button zum Hinzufuegen
	private Button createButton;

	// 1
	/**
	 * Konstruktor um alle Kontrollelemente zu instanzieren und Behälter
	 * hinzuzufügen
	 */
	public CreatePane(Geo_Controller controller) {

		// 1 ChoiceBox zur Auswahl von State
		this.lblState = new Label("State:");
		this.add(this.lblState, 1, 2);

		// 1 Form of Government Label + RadioButtons hinzufügen
		this.lblFormGovernment = new Label("Form of Government");
		this.add(this.lblFormGovernment, 1, 3);

		// 1 Radiobuttons für FormofGovernment
		this.democracyButton = new RadioButton("Demogracy");
		this.dictatorshipButton = new RadioButton("Dictatorship");
		this.monarchyButton = new RadioButton("Monarchy");
		this.democracyButton.setSelected(true);
		ToggleGroup tgGov = new ToggleGroup();// ToggleGroup für RadioButtons
		this.democracyButton.setToggleGroup(tgGov);
		this.dictatorshipButton.setToggleGroup(tgGov);
		this.monarchyButton.setToggleGroup(tgGov);
		this.hBoxGov = new HBox(); // RadioButtons in HBox
		this.hBoxGov.getChildren().addAll(this.democracyButton, this.dictatorshipButton, this.monarchyButton);
		this.add(this.hBoxGov, 2, 3);
		
		// 1 Population
		this.lblPopulation = new Label("Population");
		this.add(this.lblPopulation, 1, 5);
		this.txfPopulation = new TextField();
		this.add(this.txfPopulation, 2, 5);

		// 1 Area
		this.lblArea = new Label("Area");
		this.add(this.lblArea, 1, 6);
		this.txfArea = new TextField();
		this.add(this.txfArea, 2, 6);

		// 1 Button zum hinzufuegen der Objekte
		this.createButton = new Button("Create Country");
		this.add(this.createButton, 2, 7);
	}

}
