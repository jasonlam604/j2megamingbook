import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

/**
 * Main Sprite / Player
 * Inherits the MIDP 2.0 Sprite class
 */
public class PlayerSprite extends Sprite {
  private static final int MOVE = 3;  
  private int x,y;
  private int scnWidth,scnHeight;
  private int frameWidth, frameHeight;
  private int frame;
  private int lives;

  public PlayerSprite(Image image, int frameWidth, int frameHeight, int scnWidth, int scnHeight) throws Exception {
    super(image, frameWidth, frameHeight);
    x = frameWidth/2;
    y = frameHeight/2;
    this.scnWidth = scnWidth;
    this.scnHeight = scnHeight;
    this.frameWidth = frameWidth;
    this.frameHeight = frameHeight;
    this.frame = 1;
    this.lives = 3;    
  }

  public void startPosition() {
    setPosition(scnWidth/2,scnHeight/2);
  }

  public void moveLeft() {
    getXY();
    if (x - MOVE > 0)   
      move(MOVE * -1,0);
  }

  public void moveRight() {
    getXY();
    if (x + MOVE + frameWidth < scnWidth)      
      move(MOVE,0);
  }

  public void moveUp() {
    getXY();
    if (y - MOVE > 0)      
      move(0,MOVE * -1);
  }

  public void moveDown() {
    getXY();
    if (y + MOVE + frameHeight < scnHeight)      
      move(0,MOVE);
  }

  public Sprite fire(Image bullets) {
    Sprite bullet = new Sprite(bullets,2,2);
    bullet.setFrame(0);
    getXY();
    bullet.setPosition(x - bullet.getWidth() / 2 + this.getWidth() / 2, y);
    return bullet;
  }
  
  public void display(Graphics g) {
    this.setFrame(frame);
    this.paint(g);
  }
  
  public int getLives() {
    return lives;
  }

  public void setLives(int lives) {
    this.lives = lives;
  }

  private void getXY() {
    x = getX();
    y = getY();
  }
}