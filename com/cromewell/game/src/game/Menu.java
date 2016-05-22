package game;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import leveleditor.Editor;

/**
 * Created by Jo on 21.05.2016.
 */
public class Menu extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("MENU");
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(20, 20, 20, 20));
        primaryStage.setScene(new Scene(layout, 600, 400));

        /*---------+
         *  LABEL  +
         *---------+
         */
        Label welcome = new Label("Hi! First open the editor, then open the game. \nIf you want to change again, close and re-open the Menu.\n" +
                "(Info for the editor: 0=air, 1=ground, 2=orange-block, 3=moos-block and nothing=air)");
        layout.setTop(welcome);
        welcome.setPadding(new Insets(50, 50, 50, 50));

        /*---------+
         * BUTTONS +
         *---------+
         */
        Button editor = new Button("EDITOR");
        editor.setPrefWidth(100);
        editor.setMaxHeight(50);
        editor.setOnAction(e-> {
            Editor.start();
        });

        Button game = new Button("GAME");
        game.setPrefWidth(100);
        game.setMaxHeight(50);
        game.setOnAction(e-> Main.start());

        layout.setLeft(game);
        layout.setRight(editor);

        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
