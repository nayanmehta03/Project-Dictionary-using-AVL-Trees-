package GUI;

import Data.Dictionary;
import Data.DictionaryEntry;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import static javafx.scene.paint.Color.POWDERBLUE;


public class StartWindow extends Application {
    Dictionary dictionary;
    HashSet<String> suggestions;
    Stage window;
    String themeSeleced;
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.getIcons().add(new Image("file:icon.jpg"));
        window = primaryStage;
        String filename1 = "dictionary.txt";
        File f1 =new File(filename1);
        String filename2 = "suggestions.txt";
        File f2 =new File(filename2);
        primaryStage.setTitle("Dictionary");

        if (f1.exists()&&f2.exists()) {
            dictionary = Dictionary.deSerialise();
            suggestions = Dictionary.deSerialiseSug();
        }
        else {
            Dictionary.downloadSuggestions();
            Dictionary.downloadDictionary();
            dictionary = Dictionary.deSerialise();
            suggestions = Dictionary.deSerialiseSug();
        }

        //Fonts
        Font verdana30 = Font.font("Verdana", FontWeight.EXTRA_BOLD,30);

        //Start Pane children
        Label welcome = new Label("Welcome");
        welcome.setFont(verdana30);
        Label catchLine = new Label("Gotta search'em all");
        Button start = new Button("Start");
       /* Label themeLabel = new Label("Theme");
        ComboBox<String> theme = new ComboBox<String>();
        theme.getItems().add("Viper");
        theme.getItems().add("Dark");
        theme.getItems().add("AMOLED Dark");
        GridPane.setConstraints(themeLabel,0,3);
        GridPane.setConstraints(theme,1,3);*/


        //Search Pane Children
        TextField word = new TextField();
        word.setPrefWidth(100);
        word.setPromptText("Enter word");
        word.setPrefColumnCount(10);
        word.getText();
        TextFields.bindAutoCompletion(word,suggestions);


        Button submit = new Button("Submit");
        GridPane.setConstraints(submit, 1, 0);

        TextArea definition = new TextArea();
        definition.setEditable(false);
        GridPane.setConstraints(definition,0,1);

        Button recreate = new Button("Recreate Dictionary");
        GridPane.setConstraints(recreate,0,2);


        //Start Scene Pane
        VBox pane1 = new VBox();
        pane1.setSpacing(10);
        pane1.setPadding(new Insets(10,10,10,10));
        pane1.setAlignment(Pos.CENTER);
        pane1.getChildren().addAll(welcome,catchLine,start);

        //Search Scene Pane
        GridPane pane2 = new GridPane();
        pane2.setPadding(new Insets(10,10,10,10));
        pane2.setVgap(10);
        pane2.setHgap(10);
        GridPane.setConstraints(word,0,0);
        pane2.getChildren().addAll(word,submit,definition,recreate);

        //Start Scene
        Scene startScene = new Scene(pane1,300,300);
        startScene.getStylesheets().add("Viper.css");

        //Search Scene
        Scene searchScene = new Scene(pane2,600,500);
        searchScene.getStylesheets().add("Viper.css");

        primaryStage.setScene(startScene);
        primaryStage.show();

        //Event Handling
        start.setOnAction(event -> {
            primaryStage.setScene(searchScene);
        });




        submit.setOnAction(event -> {
            DictionaryEntry found = dictionary.search(word.getText());
            if(found!=null){
                definition.setText(found.toString());
            }
            else {
                AlertBox.display("Error","Word not found.");
            }

        });

        recreate.setOnAction(event -> {
            Dictionary.downloadDictionary();
            dictionary = Dictionary.deSerialise();
        });

        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            closeProgram();
        });

    }

    public void closeProgram(){
        Boolean answer = ConfirmBox.display("Exit","Sure you want to exist?");
        if (answer)
            window.close();
    }
}
