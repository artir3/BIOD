package source.view_controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import source.algorytm.Szyfrowanie;
import source.algorytm.SzyfrowanieByte;
import source.algorytm.rwFIle;

import java.io.File;
import java.io.InputStream;

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

    private rwFIle rw = new rwFIle();

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
            System.out.println("Deszyfrowanie");


            info("Plik został deszyfrowany");
        }
    }

    @FXML
    public void encryptFile(ActionEvent actionEvent) {
        if (validateFilePath()) {
            System.out.println("Szyfrowanie");
            byte[] is = rw.readFiles(fileInput);
//            StringBuilder is = rw.readFile(fileInput);
//            Szyfrowanie as = new Szyfrowanie();
            SzyfrowanieByte as = new SzyfrowanieByte();
            byte[] sb = as.szyfrowanie(is, passField.getText());
//            StringBuilder sb = as.szyfrowanie(is, passField.getText());
            rw.writeFile(sb, fileOutput, fileInput);
            info("Plik został zaszyfrowany");
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
            info("Hasło musi posiadać więcej niz 8 znaków, proszę poprawić.");
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
