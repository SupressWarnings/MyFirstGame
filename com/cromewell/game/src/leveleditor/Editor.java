package leveleditor;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Jo on 17.05.2016.
 */
public class Editor{


    private static ArrayList<TextField> textFields = new ArrayList<>();

    public static void start()  {

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Level-Editor");
        primaryStage.setResizable(false);
        Group root = new Group();
        FlowPane fp = new FlowPane(0, 0);
        fp.setMinSize(480, 360);    //One inputfield 30*30
        fp.setMaxSize(480, 360);
        Scene scene = new Scene(root, 480, 410); //Button place
        primaryStage.setScene(scene);
        root.getChildren().add(fp);

        for (int i = 0; i < 192; i++) {

            TextField tf = new TextField();
            tf.setPrefSize(30, 30);
            textFields.add(tf);

        }
        for(TextField t: textFields){

            fp.getChildren().add(t);

        }

        Button saveToFile = new Button("Save to File");
        root.getChildren().add(saveToFile);
        saveToFile.setLayoutY(380);
        saveToFile.setLayoutX(480/2-50);

        saveToFile.setOnAction(e->{

            String[] lvl = new String[192];
            for (int i = 0; i < 192; i++) {
                lvl[i] = textFields.get(i).getText();
                if(lvl[i].length() > 1 || lvl[i].length() < 1){ //REGEX if abc...
                    lvl[i] = "0";
                }
            }

            OutputStream os = null;
            try {
                os = new FileOutputStream(LoadLevel.getJarDir(Editor.class).getPath()+"/custom_level.txt");
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));

            for(int i = 0; i < 192; i++){
                //write into file
                try {
                    bufferedWriter.write(lvl[i]+"\n");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            try {
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            try {
                LoadLevel.loadLevel();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        });

        primaryStage.show();

    }
}
