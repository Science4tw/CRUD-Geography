package mvc;

import controller.App_Controller;
import model.App_Model;
import model.Country;
import model.FormOfGovernment;
import view.App_View;
import splashScreen.Splash_Controller;
import splashScreen.Splash_Model;
import splashScreen.Splash_View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class JavaFX_App_Template extends Application {
    private static JavaFX_App_Template mainProgram; // singleton
    private Splash_View splashView;
    private App_View view;

    private ServiceLocator serviceLocator; // resources, after initialization

    
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
 	
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Note: This method is called on the main thread, not the JavaFX
     * Application Thread. This means that we cannot display anything to the
     * user at this point. Since we want to show a splash screen, this means
     * that we cannot do any real initialization here.
     * 
     * This implementation ensures that the application is a singleton; only one
     * per JVM-instance. On client installations this is not necessary (each
     * application runs in its own JVM). However, it can be important on server
     * installations.
     * 
     * Why is it important that only one instance run in the JVM? Because our
     * initialized resources are a singleton - if two programs instances were
     * running, they would use (and overwrite) each other's resources!
     */
    @Override
    public void init() {
        if (mainProgram == null) {
            mainProgram = this;
        } else {
            Platform.exit();
        }
    }

    /**
     * This method is called after init(), and is called on the JavaFX
     * Application Thread, so we can display a GUI. We have two GUIs: a splash
     * screen and the application. Both of these follow the MVC model.
     * 
     * We first display the splash screen. The model is where all initialization
     * for the application takes place. The controller updates a progress-bar in
     * the view, and (after initialization is finished) calls the startApp()
     * method in this class.
     */
    @Override
    public void start(Stage primaryStage) {
        // Create and display the splash screen and model
        Splash_Model splashModel = new Splash_Model();
        splashView = new Splash_View(primaryStage, splashModel);
        new Splash_Controller(this, splashModel, splashView);
        splashView.start();
       
   

        // Display the splash screen and begin the initialization
        splashModel.initialize();
    }

    /**
     * This method is called when the splash screen has finished initializing
     * the application. The initialized resources are in a ServiceLocator
     * singleton. Our task is to now create the application MVC components, to
     * hide the splash screen, and to display the application GUI.
     * 
     * Multitasking note: This method is called from an event-handler in the
     * Splash_Controller, which means that it is on the JavaFX Application
     * Thread, which means that it is allowed to work with GUI components.
     * http://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm
     */
    public void startApp() {
        Stage appStage = new Stage();

        // Initialize the application MVC components. Note that these components
        // can only be initialized now, because they may depend on the
        // resources initialized by the splash screen
        App_Model model = new App_Model();
        view = new App_View(appStage, model);
        new App_Controller(model, view);

        // Resources are now initialized
        serviceLocator = ServiceLocator.getServiceLocator();

        // Close the splash screen, and set the reference to null, so that all
        // Splash_XXX objects can be garbage collected
        splashView.stop();
        splashView = null;
        // Country TEST
        
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
 		
        view.start();
    }

    /**
     * The stop method is the opposite of the start method. It provides an
     * opportunity to close down the program, including GUI components. If the
     * start method has never been called, the stop method may or may not be
     * called.
     * 
     * Make the GUI invisible first. This prevents the user from taking any
     * actions while the program is ending.
     */
    @Override
    public void stop() {
        serviceLocator.getConfiguration().save();
        if (view != null) {
            // Make the view invisible
            view.stop();
        }

        // More cleanup code as needed

        serviceLocator.getLogger().info("Application terminated");
    }

    // Static getter for a reference to the main program object
    protected static JavaFX_App_Template getMainProgram() {
        return mainProgram;
    }
}
