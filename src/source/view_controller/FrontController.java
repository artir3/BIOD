package source.view_controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;

public class FrontController {
    private File fileInput;
    private File fileOutput;

    @FXML
    public TextField fileInputField;
    @FXML
    public TextField fileOutputField;

    @FXML
    public BorderPane mainPane = new BorderPane();

    FrontController frontController;

    public FrontController() {

    }

    public void initialize() {
        if (fileInputField == null)
            fileInputField = new TextField();
        if (fileOutputField == null)
            fileOutputField = new TextField();
    }

    @FXML
    public void decryptFile(ActionEvent actionEvent) {
        if (validateFilePath()) {
            System.out.println("Deszyfrowanie");


        }
    }


    @FXML
    public void encryptFile(ActionEvent actionEvent) {
        if (validateFilePath()) {
            System.out.println("Szyfrowanie");


        }

    }

    private boolean validateFilePath() {
        if (!fileInputField.equals(null) || !fileInputField.getText().equals("")) {
            fileInput = new File(fileInputField.getText());
        }
        if (fileInput.equals(null)) {
            return false;
        }
        if (!fileOutputField.equals(null) || !fileOutputField.getText().equals("")) {
            fileOutput = new File(fileOutputField.getText());
        }
        if (fileInput.equals(null)) {
            return false;
        }
        return true;
    }

    @FXML
    private void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik do szyfrowania");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileInput = fileChooser.showOpenDialog(mainPane.getScene().getWindow());
        fileInputField.setText(fileInput.getAbsolutePath());
    }

    @FXML
    private void saveFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz folder do zapisu");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileOutput = fileChooser.showSaveDialog(mainPane.getScene().getWindow());
        fileOutputField.setText(fileOutput.getAbsolutePath());
    }


    //    @FXML
    public void exitMenu(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }


    @FXML
    public void info(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(mainPane.getScene().getWindow());
        alert.setTitle("Informacje");
        alert.setHeaderText("Informacje");
        String tekst = "Aplikacja na BIOD!!\n" +
                "Autor: Artur Markowski\n" +
                "Index: 187104\n";
        alert.setContentText(tekst);
        alert.showAndWait();

    }
}
