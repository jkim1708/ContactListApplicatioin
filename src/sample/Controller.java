package sample;

import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.Comparator;
import java.util.Optional;

public class Controller {

    @FXML
    private TableView<Contact> contactList;

    @FXML
    private  MenuItem deleteButton;

    private ObservableList<Contact> list;


    private ContactData contactData = new ContactData();

    public void initialize() {
        list = contactData.getContacts();
        contactData.loadContacts();
        contactList.setItems(list.sorted(new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return  o1.getFirstName().compareTo(o2.getFirstName());
            }
        }));

        TableColumn<Contact,String> firstCol = new TableColumn<Contact,String>("First Name");
        firstCol.setPrefWidth(225);
        firstCol.setCellValueFactory(new PropertyValueFactory("firstName"));
        TableColumn<Contact,String> secondCol = new TableColumn<Contact,String>("Last Name");
        secondCol.setPrefWidth(225);
        secondCol.setCellValueFactory(new PropertyValueFactory("lastName"));
        TableColumn<Contact,String> thirdCol = new TableColumn<Contact,String>("Phone Number");
        thirdCol.setPrefWidth(225);
        thirdCol.setCellValueFactory(new PropertyValueFactory("phoneNumber"));
        TableColumn<Contact,String> fourthCol = new TableColumn<Contact,String>("Notes");
        fourthCol.setPrefWidth(225);
        fourthCol.setCellValueFactory(new PropertyValueFactory("notes"));


        contactList.getColumns().setAll(firstCol, secondCol,thirdCol,fourthCol);

    }

    public void showMenuButton(){
        System.out.println("test");
        deleteButton.disableProperty();
    }



    public void showNewContactDialog(ActionEvent actionEvent) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Create new contact");
        dialog.getDialogPane().getScene().getWindow().sizeToScene();

        ButtonType loginButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setGridLinesVisible(false);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField newFirstName = new TextField();
        TextField newLastName = new TextField();
        TextField newPhoneNumber = new TextField();
        TextField newNotes = new TextField();

        grid.add(new Label("New first name:"), 0, 0);
        grid.add(newFirstName, 1, 0);
        grid.add(new Label("New last name:"), 0, 1);
        grid.add(newLastName, 1, 1);
        grid.add(new Label("New phone number:"), 0, 2);
        grid.add(newPhoneNumber, 1, 2);
        grid.add(new Label("New new notes:"), 0, 3);
        grid.add(newNotes, 1, 3);
        dialog.getDialogPane().setContent(grid);


        Optional<Pair<String, String>> result = dialog.showAndWait();
        if (result.isPresent()) {
            Contact newContact = new Contact(newFirstName.getCharacters().toString(),newLastName.getCharacters().toString(),newPhoneNumber.getCharacters().toString(),newNotes.getCharacters().toString());

        contactData.addContacts(newContact);
        contactData.saveContacts();


        }

    }
    public void showDeleteContactDialog(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure deleting the contact?");
        alert.setTitle("Delete Contact");
        alert.setHeaderText("");
        if(contactList.getSelectionModel().getSelectedItem() != null) {

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                contactData.deleteContacts(contactList.getSelectionModel().getSelectedItem());
                contactData.saveContacts();
            }
        }
    }

    public void showEditContactDialog(ActionEvent actionEvent) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Edit existing contact");
        dialog.getDialogPane().getScene().getWindow().sizeToScene();

        ButtonType loginButtonType = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setGridLinesVisible(false);
        grid.setPadding(new Insets(20, 150, 10, 10));

        if(contactList.getSelectionModel().getSelectedItem() != null) {

        TextField newFirstName = new TextField(contactList.getSelectionModel().getSelectedItem().getFirstName());
        TextField newLastName = new TextField(contactList.getSelectionModel().getSelectedItem().getLastName());
        TextField newPhoneNumber = new TextField(contactList.getSelectionModel().getSelectedItem().getPhoneNumber());
        TextField newNotes = new TextField(contactList.getSelectionModel().getSelectedItem().getNotes());

        grid.add(new Label("First name:"), 0, 0);
        grid.add(newFirstName, 1, 0);
        newFirstName.isEditable();
        grid.add(new Label("Last name:"), 0, 1);
        grid.add(newLastName, 1, 1);
        newLastName.isEditable();
        grid.add(new Label("Phone number:"), 0, 2);
        grid.add(newPhoneNumber, 1, 2);
        newPhoneNumber.isEditable();
        grid.add(new Label("New notes:"), 0, 3);
        grid.add(newNotes, 1, 3);
        newNotes.isEditable();
        dialog.getDialogPane().setContent(grid);




            Optional<Pair<String, String>> result = dialog.showAndWait();
            if (result.isPresent()) {
                Contact newContact = new Contact(newFirstName.getCharacters().toString(), newLastName.getCharacters().toString(), newPhoneNumber.getCharacters().toString(), newNotes.getCharacters().toString());
                contactData.deleteContacts(contactList.getSelectionModel().getSelectedItem());
                contactData.addContacts(newContact);
                contactData.saveContacts();

            }
        }

    }



    public void handleKeyPressed(KeyEvent keyEvent) {
    }
}
