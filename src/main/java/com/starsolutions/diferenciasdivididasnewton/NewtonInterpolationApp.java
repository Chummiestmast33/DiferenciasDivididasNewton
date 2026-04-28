package com.starsolutions.diferenciasdivididasnewton;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NewtonInterpolationApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(NewtonInterpolationApp.class.getResource("interpolation-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        scene.getStylesheets().add(NewtonInterpolationApp.class.getResource("styles.css").toExternalForm());
        stage.setTitle("Interpolación por Diferencias Divididas de Newton");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
