import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class PlayerSprite extends Sprite {
  static final int MOVE = 4;

  int scnWidth,scnHeight;


  int frame;

  int lives;

  boolean firing;
  int firingDelay=500;
  long timeLastFired;

  boolean isTilt;
  
  int energy;

  public PlayerSprite(Image image, int frameWidth, int frameHeight, int scnWidth, int scnHeight) throws Exception {
    super(image, frameWidth, frameHeight);
    defineReferencePixel(frameWidth / 2, frameHeight / 2);
    this.scnWidth = scnWidth;
    this.scnHeight = scnHeight;
    this.frame = 1;
    this.lives = 3;
    this.firing = false;
    this.setFrame(1);
    this.setPosition(scnWidth/2,scnHeight/2);
    this.isTilt = false;
    this.setVisible(false);
  }

  public int getMidY() {
    return getY() + (getWidth()/2);
  }

  public int getMidX() {
    return getX() + (getWidth()/2);
  }


  public void startPosition() {
    setPosition(scnWidth/2,scnHeight/2);
  }

  public void moveLeft() {
    if (getX() - MOVE > 0) {
      move(MOVE * -1,0);
      setFrame(0);
      isTilt = true;
    }
  }

  public void moveRight() {
    if (getX() + MOVE + getWidth() < scnWidth) {
      move(MOVE,0);
      setFrame(2);
      isTilt = true;
    }
  }

  public void moveUp() {
    if (getY() - MOVE > 0)
      move(0,MOVE * -1);
  }

  public void moveDown() {
    if (getY() + MOVE + getHeight() < scnHeight)
      move(0,MOVE);
  }

  public Bullet fire(Image bullets) throws Exception {
    Bullet bullet = null;

    long timeSinceLastFire = (System.currentTimeMillis() - timeLastFired);
    if (!firing && timeSinceLastFire > firingDelay) {
      bullet = new Bullet(bullets,2,2,Bullet.TYPE_PLAYER);
      bullet.setPosition(getX()+(getWidth()/2)-1,getY());
      timeLastFired = System.currentTimeMillis();
    } else {
      firing = false;
    }
    return bullet;
  }

  public int getLives() {
    return lives;
  }

  public void setTiltOff() {    
    if (isTilt){
      isTilt = false;
      setFrame(1);
    }
  }

  public void setLives(int lives) {
    this.lives = lives;
  } 
  

}