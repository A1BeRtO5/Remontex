package com.example.programm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("background.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1245, 640);
        stage.setTitle("Remontex");

        stage.setScene(scene);
//        stage.setMaximized(true);//
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}