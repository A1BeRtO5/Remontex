package com.example.programm;

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class HelloController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField mainPageClient, mainPageSerialNumber, mainPageSearchOrderTextField, mainPagePhoneNumber, mainPageGroup, mainPageGiveMyMoney, mainPageDate, mainPageCondition, mainPageColor, changeSeriaNumberTextArea, changePhoneNumberTextArea, changeGroupTextArea, changeGiveMyMoneyTextArea, changeDateTextArea, changeClientTextArea, changeConditionTextArea, changeColorTextArea;
    @FXML
    private Label  orderWindows, mainPageUrgentOrders, mainPageOrdersInRemont, mainPageOverdueOrders, mainPageOrdersAreStillProcess, mainPageDoneOrders, mainPageDateLabel, errorTextField, mainPageAllOrders;
    @FXML
    private TextArea dataBaseTextArea, notesTextArea;
    @FXML
    private Button exitAppearedWindow,saveNotesButton, searchUrgentOrdersButton, searchPendingIssuanceOrdersButton, searchDoneOrdersButton1, searchOverdueOrdersButton, searchAllOrdersButton, saveChangesOrdersButton, orderButton, DeleteOrdersButton, mainPageSearchOrders, mainButton, mainPageAddOrdersButton;
    @FXML
    private AnchorPane mainPanel, orderPanel, showOrdersAncorPane;

    static String idFromDB;
    Connect c = new Connect();
    ArrayList<String> arrayList = new ArrayList<>();


    @FXML
    void DeleteOrdersButtonAction(ActionEvent event) {
        c.DeleteOrder();
        changePhoneNumberTextArea.setText("");
        changeDateTextArea.setText("");
        changeGroupTextArea.setText("");
        changeConditionTextArea.setText("");
        changeClientTextArea.setText("");
        changeColorTextArea.setText("");
        changeSeriaNumberTextArea.setText("");
        changeGiveMyMoneyTextArea.setText("");
        showOrdersAncorPane.setVisible(false);
        setMainPageClientText();
    }

    @FXML
    void exitAppearedWindowActon(ActionEvent event) {
        showOrdersAncorPane.setVisible(false);
    }

    @FXML
    void saveNotesButtonAction(ActionEvent event) {
        String textToSave = notesTextArea.getText();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("notes.txt"))) {
            writer.write(textToSave);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    @FXML
    void mainButtonAction(ActionEvent event) {
        mainPanel.setVisible(true);
        orderPanel.setVisible(false);

        searchUrgentOrdersButton.setVisible(false);
        searchPendingIssuanceOrdersButton.setVisible(false);
        searchOverdueOrdersButton.setVisible(false);
        searchAllOrdersButton.setVisible(false);
        searchDoneOrdersButton1.setVisible(false);
        setMainPageClientText();
    }

    @FXML
    void orderButtonAction(ActionEvent event) {
        mainPanel.setVisible(false);
        orderPanel.setVisible(true);

        searchUrgentOrdersButton.setVisible(true);
        searchPendingIssuanceOrdersButton.setVisible(true);
        searchOverdueOrdersButton.setVisible(true);
        searchAllOrdersButton.setVisible(true);
        searchDoneOrdersButton1.setVisible(true);

        dataBaseTextArea.setText(c.selectFromDb());
    }

    @FXML
    void mainPageAddOrdersButtonAction(ActionEvent event) {
        if (isValidDate(mainPageDate.getText())) {
                    c.writeToDb(mainPagePhoneNumber.getText(), mainPageDate.getText(), mainPageGroup.getText(), mainPageCondition.getText(), mainPageClient.getText(), mainPageColor.getText(), mainPageSerialNumber.getText(), mainPageGiveMyMoney.getText());
        } else {
            mainPageDate.setText("Невірний формат");
            return;
        }
        mainPagePhoneNumber.setText("");
        mainPageGroup.setText("");
        mainPageCondition.setText("");
        mainPageClient.setText("");
        mainPageColor.setText("");
        mainPageSerialNumber.setText("");
        mainPageDate.setText("");
        mainPageGiveMyMoney.setText("");
        setMainPageClientText();
    }

    public static boolean isValidDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @FXML
    void mainPageSearchOrdersAction(ActionEvent event) {
        showOrdersAncorPane.setVisible(true);
        arrayList = c.selectPhoneNumberromDb(mainPageSearchOrderTextField.getText());
        System.out.println(arrayList);
        String sTrim = mainPageSearchOrderTextField.getText().trim();
        String sTr = arrayList.get(0);
        System.out.println(arrayList);
        if (sTrim.equals(sTr)) {
            mainPageSearchOrderTextField.setText("");
            errorTextField.setVisible(false);
            changePhoneNumberTextArea.setVisible(true);
            changeDateTextArea.setVisible(true);;
            changeGroupTextArea.setVisible(true);
            changeConditionTextArea.setVisible(true);
            changeClientTextArea.setVisible(true);
            changeColorTextArea.setVisible(true);
            changeSeriaNumberTextArea.setVisible(true);;
            changeGiveMyMoneyTextArea.setVisible(true);
            orderWindows.setVisible(true);
            DeleteOrdersButton.setVisible(true);
            saveChangesOrdersButton.setVisible(true);

            changePhoneNumberTextArea.setText(arrayList.get(0));
            changeDateTextArea.setText(arrayList.get(1));
            changeGroupTextArea.setText(arrayList.get(2));
            changeConditionTextArea.setText(arrayList.get(3));
            changeClientTextArea.setText(arrayList.get(4));
            changeColorTextArea.setText(arrayList.get(5));
            changeSeriaNumberTextArea.setText(arrayList.get(6));
            changeGiveMyMoneyTextArea.setText(arrayList.get(7));
            idFromDB = arrayList.get(8);
            setMainPageClientText();
            return;
        }

        errorTextField.setVisible(true);
        errorTextField.setText("номер не " + "\n" +  "зареєстрований");

        changePhoneNumberTextArea.setVisible(false);
        changeDateTextArea.setVisible(false);;
        changeGroupTextArea.setVisible(false);
        changeConditionTextArea.setVisible(false);
        changeClientTextArea.setVisible(false);
        changeColorTextArea.setVisible(false);
        changeSeriaNumberTextArea.setVisible(false);;
        changeGiveMyMoneyTextArea.setVisible(false);
        orderWindows.setVisible(false);
        DeleteOrdersButton.setVisible(false);
        saveChangesOrdersButton.setVisible(false);
    }

    @FXML
    void saveChangesOrdersButtonActoin(ActionEvent event) {
        int id = Integer.parseInt(idFromDB);
        c.saveChangesOrdersBToDb(id, changePhoneNumberTextArea.getText(), changeDateTextArea.getText(), changeGroupTextArea.getText(), changeConditionTextArea.getText(), changeClientTextArea.getText(), changeColorTextArea.getText(), changeSeriaNumberTextArea.getText(), changeGiveMyMoneyTextArea.getText());

        changePhoneNumberTextArea.setText("");
        changeDateTextArea.setText("");
        changeGroupTextArea.setText("");
        changeConditionTextArea.setText("");
        changeClientTextArea.setText("");
        changeColorTextArea.setText("");
        changeSeriaNumberTextArea.setText("");
        changeGiveMyMoneyTextArea.setText("");
        showOrdersAncorPane.setVisible(false);
        setMainPageClientText();
        }

    @FXML
    void searchAllOrdersButtonAction(ActionEvent event) {//всі
        Connect c = new Connect();
        dataBaseTextArea.setText(c.selectFromDb());
    }

    @FXML
    void searchDoneOrdersButtonAction(ActionEvent event) {// видані
        Connect c = new Connect();
        dataBaseTextArea.setText(c.selectOrdinarydOrders());
    }

    @FXML
    void searchOverdueOrdersButtonAction(ActionEvent event) {// прострок
        Connect c = new Connect();
        dataBaseTextArea.setText(c.selectOverdueOrders());
    }

    @FXML
    void searchPendingIssuanceOrdersButtonAction(ActionEvent event) {// чекають видачі
        Connect c = new Connect();
        dataBaseTextArea.setText(c.selectSearchDoneOrders());
    }

    @FXML
    void searchUrgentOrdersButtonAction(ActionEvent event) {//  термінові
        Connect c = new Connect();
        dataBaseTextArea.setText(c.selectUrgentOrders());
    }

    @FXML
    void initialize() {

        notesTextArea.setStyle("-fx-control-inner-background: rgb(90, 90, 90); -fx-text-fill: rgb(180, 180, 180);");
        dataBaseTextArea.setStyle("-fx-control-inner-background: rgb(43, 42, 42); -fx-text-fill: rgb(180, 180, 180);");
        dataBaseTextArea.setOnKeyPressed(event -> {
            orderButton.requestFocus();
        });
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("d MMMM yyyy", new Locale("uk", "UA"));
        mainPageDateLabel.setText(formatter.format(date));

        setMainPageClientText();
        notesTextArea.setText(writerNotes());

    }

    void setMainPageClientText() {
        Connect c = new Connect();
        mainPageUrgentOrders.setText(c.selectCondition());                // термінові
        mainPageDoneOrders.setText(c.selectDoneOrders());                 // чекають видачі
        mainPageOrdersAreStillProcess.setText(c.selectIssuedOrders());    // видано
        mainPageOverdueOrders.setText(c.selectDataiff());                 // прострок
        mainPageAllOrders.setText(c.selectAllOrdersFromDb());
        int all = Integer.parseInt(c.selectAllOrdersFromDb());
        int issued = Integer.parseInt(c.selectIssuedOrders());
        int inRemont = all - issued;
        mainPageOrdersInRemont.setText(String.valueOf(inRemont));        //в семонті всього
    }

    String writerNotes() {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("notes.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        String s = String.valueOf(stringBuilder);
        return s;
    }

}