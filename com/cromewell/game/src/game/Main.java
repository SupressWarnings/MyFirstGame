package game;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import level.LevelData;
import player.Player;
import java.util.ArrayList;

public class Main {

    private static GraphicsContext gcf;
    private static GraphicsContext gcb;
    private static Player ply;
    private static ArrayList<String> input = new ArrayList<>();
    private static ArrayList<Shape> collisionObjects = new ArrayList<>();


    //main loop
    public static void start() {

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Game");
        Group root = new Group();
        Scene scene = new Scene(root, 800, 800*9/12);
        primaryStage.setScene(scene);
        Canvas foreground = new Canvas(800, 800*9/12);
        Canvas background = new Canvas(800, 800*9/12);
        root.getChildren().addAll(background, foreground);

        ply = new Player(50, 520, 0, 0, 50, 50);

        scene.setOnKeyPressed(e->{
            String code = e.getCode().toString();
            if(!input.contains(code)) {
                input.add(code);
            }
        });

        scene.setOnKeyReleased(e->{
            String code = e.getCode().toString();
            input.remove(code);
        });

        gcb = background.getGraphicsContext2D();
        gcf = foreground.getGraphicsContext2D();

        initLevel();

        new AnimationTimer(){

            @Override
            public void handle(long now) {
                render();
                update();
                checkCollision();
                inputHandler();

            }
        }.start();


        primaryStage.show();
    }

    private static void inputHandler() {

        if(input.contains("LEFT")&&input.contains("UP") || input.contains("RIGHT")&&input.contains("UP")){
            if(input.contains("LEFT")){
                ply.setVelX(-2);
                ply.setX(ply.getX()+ply.getVelX());
                ply.setVelX(0);
            }else if(input.contains("RIGHT")) {
                ply.setVelX(2);
                ply.setX(ply.getX() + ply.getVelX());
                ply.setVelX(0);
            }
            ply.jump();
        }else if(input.contains("LEFT")){
            ply.setVelX(-2);
            ply.setX(ply.getX()+ply.getVelX());
            ply.setVelX(0);
        }else if(input.contains("RIGHT")) {
            ply.setVelX(2);
            ply.setX(ply.getX() + ply.getVelX());
            ply.setVelX(0);
        }else if(input.contains("UP")){
            ply.jump();
        }

    }

    //draws the level
    private static void initLevel() {
        //Draw sky
        gcb.setFill(Color.CYAN);
        gcb.fillRect(0, 0, 800, 600);
        //Draw level

        for(int i = 0; i < LevelData.LEVEL1.length; i++){
            String id = LevelData.CUSTOMLEVEL[i];
            int y = calcY(i+1);
            int x = calcX(i+1)-1;

            switch (id){
                case "0":break;
                case "1":renderImage(new Image(Main.class.getClassLoader().getResource("resources/imgs/ground.png").toString()), x, y, 50, 25);break;
                case "2":renderImage(new Image(Main.class.getClassLoader().getResource("resources/imgs/block_1.png").toString()), x, y, 50, 50);break;
                case "3":renderImage(new Image(Main.class.getClassLoader().getResource("resources/imgs/block_2.png").toString()), x, y, 50, 50);break;
            }
        }

    }

    //get the x value of the array item
    private static int calcY(int i) {
        int times = 0;
        if(i%16==0){
            times = i/16;
            return times;
        }else{
            while(i > 0){
                i -=16;
                times++;
            }
            return times;
        }
    }

    //get the y value of the array item
    private static int calcX(int i) {
        if(i%16==0){
            return 16;
        }else{
            while(i > 16){
                i -=16;
            }
            return i;
        }

    }

    //draws the image
    private static void renderImage(Image i, int x, int y, int w, int h) {
        gcb.drawImage(i, x * 50, y * 50 -h, w, h);
        collisionObjects.add(new Rectangle(x*50, y*50-h, w, h));
    }

    //checks for collisions
    private static void checkCollision() {

        for (Shape s: collisionObjects) {

            boolean collision = false;
            Shape intersect = Shape.intersect(s, ply.getPlayer());
            if(intersect.getBoundsInLocal().getWidth() >= 0 || intersect.getBoundsInLocal().getHeight() >= 0){
                collision = true;
            }
            if(collision){
                ply.setVelY(0);
                ply.setVelX(0);
                if(intersect.getBoundsInLocal().getHeight() > intersect.getBoundsInLocal().getWidth()){
                    //left/right
                    if(ply.getPlayer().getBoundsInLocal().getMinX() < s.getBoundsInLocal().getMaxX() && ply.getPlayer().getBoundsInLocal().getMaxX() > s.getBoundsInLocal().getMaxX()){
                        //player hits the left side of a block
                        ply.setX(ply.getPlayer().getX()+intersect.getBoundsInLocal().getWidth());
                    }else{
                        //player hits the right side of a block
                        ply.setX(ply.getPlayer().getX()-intersect.getBoundsInLocal().getWidth());
                    }
                }else if(intersect.getBoundsInLocal().getWidth() > intersect.getBoundsInLocal().getHeight()){
                    //top/bottom
                    if(ply.getPlayer().getBoundsInLocal().getMinY() < s.getBoundsInLocal().getMinY() && ply.getPlayer().getBoundsInLocal().getMaxY() > s.getBoundsInLocal().getMinY()){
                        //player is on the top
                        ply.setY(ply.getPlayer().getY()-intersect.getBoundsInLocal().getHeight());
                        ply.setJumping(false);
                    }else{
                        //player hits the bottom of a block
                        ply.setY(ply.getPlayer().getY()+intersect.getBoundsInLocal().getHeight());
                    }
                }
            }
        }
    }

    //Updates the screen
    private static void update() {

        ply.update();

    }

    //renders ply + screen clear
    private static void render(){
        gcf.clearRect(0, 0, 800, 800*9/12);
        ply.render(gcf);

    }
}
