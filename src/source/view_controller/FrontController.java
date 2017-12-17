package source.view_controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import source.algorytm.Deszyfrowanie;
import source.algorytm.RWfiles;
import source.algorytm.Szyfrowanie;

import java.io.File;

public class FrontController {
    private File fileInput;
    private File fileOutput;

    @FXML
    public TextField fileInputField;
    @FXML
    public TextField fileOutputField;
    @FXML
    public PasswordField passField;

    @FXML
    public BorderPane mainPane = new BorderPane();

    FrontController frontController;

    private RWfiles rw = new RWfiles();

    public FrontController() {
    }

    public void initialize() {
        if (fileInputField == null)
            fileInputField = new TextField();
        if (fileOutputField == null)
            fileOutputField = new TextField();
        if (passField == null)
            passField = new PasswordField();
    }

    @FXML
    public void decryptFile(ActionEvent actionEvent) {
        if (validateFilePath()) {
            String text  = rw.readFile(fileInput);
            Deszyfrowanie as = new Deszyfrowanie();
            StringBuilder sb = as.deszyfrowanie(text, passField.getText());
            rw.writeFile(sb, fileOutput, fileInput);
            info("Plik zostal deszyfrowany");
        }
    }

    @FXML
    public void encryptFile(ActionEvent actionEvent) {
        if (validateFilePath()) {
            String text  = rw.readFile(fileInput);
            Szyfrowanie as = new Szyfrowanie();
            StringBuilder sb = as.szyfrowanie(text, passField.getText());
            rw.writeFile(sb, fileOutput, fileInput);
            info("Plik zostal zaszyfrowany");
        }
    }

    @FXML
    public void info(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(mainPane.getScene().getWindow());
        alert.setTitle("Informacje");
        alert.setContentText(text);
        alert.showAndWait();
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
        if (passField.equals(null) || passField.getText().length()<8) {
            info("Has?o musi posiada? wi?cej niz 8 znaków, prosz? poprawi?.");
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
        if (fileInput!=null && fileInput.exists())
            fileInputField.setText(fileInput.getAbsolutePath());
    }

    @FXML
    private void saveFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz folder do zapisu");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileOutput = fileChooser.showSaveDialog(mainPane.getScene().getWindow());
        if (fileOutput!=null)
            fileOutputField.setText(fileOutput.getAbsolutePath());
    }

    @FXML
    public void info(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(mainPane.getScene().getWindow());
        alert.setTitle("Informacje");
        alert.setHeaderText("Informacje");
        String tekst = "Aplikacja s?u??ca do szyfrowania i deszyfrowania plików. \n\n " +
                "Algorym szyfrowania jest blokowy, symetryczny z szyfrowaniem przestawieniowym i polialfabetycznym. \n\n " +
                "Wymagania co do klucza s? nast?puj?ce:\n" +
                "Minimum 8 znaków.\n\n" +
                "Autor: Artur Markowski\n" +
                "Index: 187104\n";
        alert.setContentText(tekst);
        alert.showAndWait();
    }

}
