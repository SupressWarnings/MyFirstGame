package player;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by Jo on 16.05.2016.
 */
public class Player {

    double x, y;
    double velX, velY;
    double width, height;
    double gravity = 0.56;
    Color color = Color.BLACK;
    Rectangle ply;
    boolean jumping = false;

    public Player(double x, double y, double velX, double velY, double width, double height){

        this.x = x;
        this.y = y;
        this.velX = velX;
        this.velY = velY;
        this.width = width;
        this.height = height;

        ply = new Rectangle(x, y, width, height);

    }

    public void render(GraphicsContext gc){

        gc.setFill(color);
        gc.fillRect(x, y, width, height);

    }

    public void update(){

        velY += gravity;
        y += velY;
        x +=velX;
        ply = new Rectangle(x, y, width, height);

    }

    public Rectangle getPlayer(){

        return ply;

    }

    public double getVelX() {

        return velX;

    }

    public void setX(double x) {

        this.x = x;

    }

    public double getX() {

        return x;

    }

    public void setVelX(double velX) {

        this.velX = velX;

    }

    public double getY() {

        return y;

    }

    public void setY(double y) {

        this.y = y;

    }

    public void jump() {

        if(!jumping){
            setVelY(-10);
            jumping = true;
        }

    }

    public void setVelY(double velY) {

        this.velY = velY;

    }

    public boolean isJumping() {

        return jumping;

    }

    public void setJumping(boolean jumping) {

        this.jumping = jumping;

    }

}
