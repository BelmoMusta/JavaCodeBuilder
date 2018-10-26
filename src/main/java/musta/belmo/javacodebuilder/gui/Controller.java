package musta.belmo.javacodebuilder.gui;

import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

public class Controller {


    public void chooseSourceDirectory(ActionEvent actionEvent) {



        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Choose a java file");

        java.io.File f = fileChooser.showOpenDialog(null);
        String path = f.getAbsolutePath();

    }

    public void generateDoc(ActionEvent actionEvent) {

    }

    public void chooseDestinationDirectory(ActionEvent actionEvent) {

    }

    public void loadProperties(ActionEvent actionEvent) {

    }
}
