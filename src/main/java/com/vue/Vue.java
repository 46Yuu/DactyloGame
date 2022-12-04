package com.vue;

//import org.fxmisc.richtext.StyleClassedTextArea;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.*;
import com.modele.*;


public class Vue extends Application{
    public void launching(String [] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            BorderPane root = new BorderPane();
            //chercher comment ajouter richtextFX

            //StyleClassedTextArea textArea = new StyleClassedTextArea();
            //textArea.replaceText("Lorem Ipsum");
            //root.getChildren().add(textArea);
            Label lbl = new Label("Hello world!");
            root.setCenter(lbl);
            Scene scene = new Scene(root,500,500);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Test Scene");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
