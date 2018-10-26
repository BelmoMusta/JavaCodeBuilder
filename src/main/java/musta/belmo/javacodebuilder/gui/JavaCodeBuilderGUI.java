package musta.belmo.javacodebuilder.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaCodeBuilderGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException{
        Parent parent  = FXMLLoader.load(JavaCodeBuilderGUI.class.getClassLoader().getResource("window-fx.fxml"));
        Scene scene = new Scene(parent,400,400);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
