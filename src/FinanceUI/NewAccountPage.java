package FinanceUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class NewAccountPage extends Application{
	
	@Override
    public void start(Stage stage) {

        var label = new Label("To be implemented: new account page \n(Account name, \ncreation date, \nstarting balance)");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setTitle("Create New Account");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
