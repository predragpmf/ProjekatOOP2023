package pmf.projekatoop.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Controller {

    protected void prozorObavjestenja(String naslov, String tekst) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(naslov);
        alert.setHeaderText(null);
        alert.setContentText(tekst);
        alert.showAndWait();
    }

    protected String hesirajLozinku(String lozinka) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(lozinka.getBytes());
            BigInteger num = new BigInteger(1, messageDigest);
            String hashText = num.toString(16);
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
            return hashText;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void promijeniScenuLogin(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent loginParent = FXMLLoader.load(getClass().getResource("loginView.fxml"));
            Scene loginScene = new Scene(loginParent);
            stage.setScene(loginScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
