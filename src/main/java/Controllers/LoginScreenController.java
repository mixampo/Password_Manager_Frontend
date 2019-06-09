package Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.Session;
import service.ILoginCallService;
import service.LoginCallService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.LongFunction;

public class LoginScreenController implements Initializable {
    public TextField txtUsername;
    public PasswordField txtPassword;
    public Button btnLogin;

    private ILoginCallService loginCallService = new LoginCallService();

    public void initialize(URL location, ResourceBundle resources) {
    }


    public void login(ActionEvent actionEvent) throws IOException {
        if (!txtUsername.getText().isEmpty() && !txtPassword.getText().isEmpty()) {
            Session session = loginCallService.login(txtUsername.getText(), txtPassword.getText());
            if (session != null){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getClassLoader().getResource("fxml/MainScreen.fxml"));
                Parent mainScreenParent = loader.load();
                Scene mainScreenScene = new Scene(mainScreenParent);

                //Access controller
                MainScreenController controller = loader.getController();
                controller.initSession(session);

                //Get stage information
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                window.setScene(mainScreenScene);
                window.show();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Wrong credentials");
            alert.setHeaderText(null);
            alert.setContentText("The username and or password you gave is/are incorrect!");

            alert.showAndWait();
        }
    }
}
