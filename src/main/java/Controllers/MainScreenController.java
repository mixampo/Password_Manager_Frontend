package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

    private IApiCallService apiCallService = new ApiCallService();
    private ObservableList<PasswordSet> PasswordSets;


    public void initialize(URL location, ResourceBundle resources) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        websiteUrl.setCellValueFactory(new PropertyValueFactory<>("websiteUrl"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    public void addPasswordSet(ActionEvent actionEvent) {
        if (txtPassword.getText().isEmpty()){
            lblPasswordAdded.setText("Password field cannot be empty!");
            lblPasswordAdded.setTextFill(Color.web("#f90000"));
        }
        else{
            apiCallService.addPasswordSet(txtPassword.getText(), txtTitle.getText(), txtUrl.getText(), txtDescription.getText());
            lblPasswordAdded.setText("Added password to database");
            lblPasswordAdded.setTextFill(Color.web("#56e200"));
        }
    }

    public void getPasswordSets(ActionEvent actionEvent){
        try {
            PasswordSets = FXCollections.observableList(apiCallService.getPasswordSets());
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

        }
    }

    public void generatePassword(ActionEvent actionEvent) {
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
