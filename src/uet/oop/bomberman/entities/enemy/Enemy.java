package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public abstract class Enemy extends Entity {

    protected final int moveLength = Sprite.SCALED_SIZE /32;
    protected int direction = -1;
    protected Sprite[] spritesSet1;
    protected Sprite[] spritesSet2;

    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public Enemy(int scaleX, int scaleY) {
        super(scaleX, scaleY);
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int generateRandom(){
        Random generator = new Random();
        int value = generator.nextInt((3 - 0) + 1);
        return  value;
    }

    public boolean checkValidMove() {
        boolean collide = true;
        if (checkCollideWall() || checkCollideBrick() || checkCollideBomb() ) collide = false;
        return collide;
    }

    public void randomMove() {
        if (direction != -1) {
            x += changeX[direction] * moveLength;
            y += changeY[direction] * moveLength;
            if (checkValidMove()) {
                return;
            }
            x -= changeX[direction] * moveLength;
            y -= changeY[direction] * moveLength;
        }

        this.setDirection(generateRandom());
        x += changeX[direction] * moveLength;
        y += changeY[direction] * moveLength;
        if (!checkValidMove()) {
            x -= changeX[direction] * moveLength;
            y -= changeY[direction] * moveLength;
        } else {
            switch (direction) {
                //        changeX = {0, 0, 1, -1};     D-U-R-L
                //        changeY = {1, -1, 0, 0};     0-1-2-3
                case 0:
                case 2: {
                    setImg(Sprite.movingSprite(spritesSet1[0],spritesSet1[1],spritesSet1[2], this.getX(), 60).getFxImage());
                    break;
                }
                case 1:
                case 3: {
                    setImg(Sprite.movingSprite(spritesSet2[0],spritesSet2[1],spritesSet2[2], this.getX(), 60).getFxImage());
                    break;
                }
            }
        }
    }

    public void checkCollideBomber(Bomber b) {
        if (Math.abs(b.getX()-x) + Math.abs(b.getY()-y) <= b.getMoveLength()) {
            b.setExisting(false);
        }
    }

    @Override
    public abstract void update() ;
}
