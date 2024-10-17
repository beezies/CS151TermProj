package FinanceUI;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class HomePage extends Application{

	@Override
    public void start(Stage stage) {
		
		var label = new Label("To be implemented: home page \n(Add account button, \naccounts view, \nschedule transaction button, \ntransactions view)");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setTitle("Create New Account");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
