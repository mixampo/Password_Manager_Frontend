package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import models.PasswordSet;
import org.springframework.beans.PropertyValue;
import service.ApiCallService;
import service.IApiCallService;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    public TableView<PasswordSet> tvPasswordSet;
    public TableColumn<PasswordSet, Integer> id;
    public TableColumn<PasswordSet, String> password;
    public TableColumn<PasswordSet, String> title;
    public TableColumn<PasswordSet, String> websiteUrl;
    public TableColumn<PasswordSet, String> description;
    public Button btnAddPasswordToDb;
    public Button btnRetrievePasswordsFromDb;
    public TextArea txtDescription;
    public TextField txtUrl;
    public TextField txtTitle;
    public TextField txtPassword;
    public Button btnDeletePasswordSetFromDb;
    public Label lblPasswordAdded;
    public Button btnEditPassword;
    public Button btnUpdateEditedPassword;
    public Button generatePassword;
    public RadioButton rbGetPasswordByUserSpecification;
    public RadioButton rbGetRandomHexKey;
    public ListView lvGeneratedPassword;
    public Button btnSetGeneratedPasswordToAddTab;
    public CheckBox cbUpperCase;
    public CheckBox cbLowerCase;
    public CheckBox cbDigits;
    public CheckBox cbSpecialChar;
    public ChoiceBox cbBitSize;

    private IApiCallService apiCallService = new ApiCallService();
    private ObservableList<PasswordSet> PasswordSets;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);



    public void initialize(URL location, ResourceBundle resources) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        websiteUrl.setCellValueFactory(new PropertyValueFactory<>("websiteUrl"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        cbBitSize.getItems().addAll("128", "192", "256");
    }

    public void addPasswordSet(ActionEvent actionEvent) {
        if (txtPassword.getText().isEmpty()){
            alert.setTitle("No password");
            alert.setHeaderText(null);
            alert.setContentText("Password field cannot be empty!");

            alert.showAndWait();
        }
        else{
            apiCallService.addPasswordSet(txtPassword.getText(), txtTitle.getText(), txtUrl.getText(), txtDescription.getText());
            update();
        }
    }

    public void getPasswordSets(ActionEvent actionEvent){
        try {
            PasswordSets = FXCollections.observableList(apiCallService.getPasswordSets(1));
            tvPasswordSet.setItems(PasswordSets);
            update();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deletePasswordSet(ActionEvent actionEvent) {
        apiCallService.deletePasswordSet(tvPasswordSet.getSelectionModel().getSelectedItem());
        update();
    }

    public void updatePassword(ActionEvent actionEvent) {
        tvPasswordSet.getSelectionModel().getSelectedItem().setPassword(txtPassword.getText());
        tvPasswordSet.getSelectionModel().getSelectedItem().setTitle(txtTitle.getText());
        tvPasswordSet.getSelectionModel().getSelectedItem().setWebsiteUrl(txtUrl.getText());
        tvPasswordSet.getSelectionModel().getSelectedItem().setDescription(txtDescription.getText());
        apiCallService.updatePasswordSet(tvPasswordSet.getSelectionModel().getSelectedItem());
        update();
    }

    public void setPasswordToEditFields(ActionEvent actionEvent) {

        if (tvPasswordSet.getSelectionModel().getSelectedItem() != null) {
            txtPassword.setText(tvPasswordSet.getSelectionModel().getSelectedItem().getPassword());
            txtTitle.setText(tvPasswordSet.getSelectionModel().getSelectedItem().getTitle());
            txtUrl.setText(tvPasswordSet.getSelectionModel().getSelectedItem().getWebsiteUrl());
            txtDescription.setText(tvPasswordSet.getSelectionModel().getSelectedItem().getDescription());
            btnUpdateEditedPassword.setDisable(false);
            btnAddPasswordToDb.setDisable(true);
        }
        else {
            alert.setTitle("No password selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a password");

            alert.showAndWait();
        }
    }

    public void generatePassword(ActionEvent actionEvent) {
        if (rbGetRandomHexKey.isSelected() && !cbBitSize.getSelectionModel().isSelected(-1)){
            lvGeneratedPassword.getItems().add(apiCallService.getGeneratedHexKey(Integer.parseInt(cbBitSize.getValue().toString())));
        }
        else if (rbGetPasswordByUserSpecification.isSelected()){

        }
        else {
            alert.setTitle("Selection chosen");
            alert.setHeaderText(null);
            alert.setContentText("Please select a way for the generator to generate a password");

            alert.showAndWait();
        }
    }

    public void changeSelection(ActionEvent actionEvent) {
        if (rbGetRandomHexKey.isSelected()){
            rbGetPasswordByUserSpecification.setSelected(false);
        }
        else if (rbGetPasswordByUserSpecification.isSelected()){
            rbGetRandomHexKey.setSelected(false);
        }
    }

    public void addGeneratedPasswordToAddTab(ActionEvent actionEvent) {
        if (lvGeneratedPassword.getSelectionModel().getSelectedItem() != null){
            txtPassword.setText(lvGeneratedPassword.getSelectionModel().getSelectedItem().toString());
        }
        else {
            alert.setTitle("No password selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a password that you want to use");

            alert.showAndWait();
        }
    }

    public void cbUpperCaseHandler(ActionEvent actionEvent) {
        rbGetRandomHexKey.setSelected(false);
        rbGetPasswordByUserSpecification.setSelected(true);
    }

    public void cbLowerCaseHandler(ActionEvent actionEvent) {
        rbGetRandomHexKey.setSelected(false);
        rbGetPasswordByUserSpecification.setSelected(true);
    }

    public void cbDigitsHandler(ActionEvent actionEvent) {
        rbGetRandomHexKey.setSelected(false);
        rbGetPasswordByUserSpecification.setSelected(true);
    }

    public void cbSpecialCharHandler(ActionEvent actionEvent) {
        rbGetRandomHexKey.setSelected(false);
        rbGetPasswordByUserSpecification.setSelected(true);
    }


    public void changeToHex(MouseEvent mouseEvent) {
        rbGetRandomHexKey.setSelected(true);
        rbGetPasswordByUserSpecification.setSelected(false);
    }

    public void update(){
        txtPassword.clear();
        txtTitle.clear();
        txtUrl.clear();
        txtDescription.clear();
        btnUpdateEditedPassword.setDisable(true);
        btnAddPasswordToDb.setDisable(false);
        //lblPasswordAdded.setText("");
    }
}
