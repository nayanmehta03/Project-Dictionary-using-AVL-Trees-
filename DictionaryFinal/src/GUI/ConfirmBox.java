package GUI;

import javafx.scene.image.Image;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class ConfirmBox {

    //Create variable
    static boolean answer;

    public static boolean display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(200);
        Label label = new Label();
        label.setText(message);

        //Create two buttons
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        //Clicking will set answer and close window
        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        GridPane layout = new GridPane();
        GridPane.setConstraints(label,0,0);
        GridPane.setConstraints(yesButton,1,1);
        GridPane.setConstraints(noButton,2,1);
        layout.setHgap(5);
        layout.setVgap(10);

        //Add buttons
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("Viper.css");
        window.getIcons().add(new Image("file:icon.jpg"));
        window.setScene(scene);
        window.showAndWait();

        //Make sure to return answer
        return answer;
    }

}