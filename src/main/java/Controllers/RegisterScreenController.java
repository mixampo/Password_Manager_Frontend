package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.ApiCallService;
import service.IApiCallService;
import service.ILoginCallService;
import service.LoginCallService;

import java.io.IOException;

public class RegisterScreenController {
    public TextField txtUsername;
    public PasswordField txtPassword;
    public PasswordField txtRepeatPassword;
    public Button btnRegister;

    private ILoginCallService loginCallService = new LoginCallService();
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public void register(ActionEvent actionEvent) {
        if (txtPassword.getText().equals(txtRepeatPassword.getText()) && !txtUsername.getText().equals("")){
            if (loginCallService.register(txtUsername.getText(), txtPassword.getText())){
                alert.setTitle("User created");
                alert.setHeaderText(null);
                alert.setContentText("Your account has been created");
                alert.showAndWait();
                update();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getClassLoader().getResource("fxml/LoginScreen.fxml"));
                Parent mainScreenParent = null;

                try {
                    mainScreenParent = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Scene mainScreenScene = new Scene(mainScreenParent);

                //Get stage information
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                window.setScene(mainScreenScene);
                window.show();
            }
            else {
                alert.setTitle("Username in use");
                alert.setHeaderText(null);
                alert.setContentText("An user with this username already exists. Please create a different one");

                alert.showAndWait();
                update();
            }
        }
        else {
            alert.setTitle("Passwords do not match");
            alert.setHeaderText(null);
            alert.setContentText("Please make sure the passwords match");

            alert.showAndWait();
            update();
        }
    }

    private void update(){
        txtUsername.clear();
        txtPassword.clear();
        txtRepeatPassword.clear();
    }
}
