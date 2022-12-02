
package fr.uparis.informatique.cpoo5.richtextdemo;

import org.fxmisc.richtext.StyleClassedTextArea;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RichTextDemo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

         

        VBox root = new VBox();
        
        StyleClassedTextArea textArea = new StyleClassedTextArea();
        textArea.replaceText("Lorem Ipsum");
        textArea.setStyleClass( 5, 9, "red");
        root.getChildren().add(textArea);

        Scene scene = new Scene(root, 320, 150);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setTitle("Rich Text Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}

