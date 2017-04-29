import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class ExampleGameCanvas extends GameCanvas implements Runnable {
  private boolean isPlay;   // Game Loop runs when isPlay is true
  private long delay;       // To give thread consistency
  private int currentX, currentY;  // To hold current position of the 'X'
  private int width;        // To hold screen width
  private int height;       // To hold screen height
  
  // Sprites to be used
  private Sprite sprite;  
  private Sprite nonTransparentSprite;

  // Constructor and initialization
  public ExampleGameCanvas() throws Exception {
    super(true);
    width = getWidth();
    height = getHeight();
    currentX = width / 2;
    currentY = height / 2;
    delay = 20;
    
    // Load Images to Sprites
    Image image = Image.createImage("/transparent.png");      
    sprite = new Sprite (image,32,32);
    
    Image imageTemp = Image.createImage("/nontransparent.png");      
    nonTransparentSprite = new Sprite (imageTemp,32,32);
    
  }

  // Automatically start thread for game loop
  public void start() {
    isPlay = true;
    Thread t = new Thread(this);
    t.start();
  }

  public void stop() { isPlay = false; }

  // Main Game Loop
  public void run() {
    Graphics g = getGraphics();
    while (isPlay == true) {

      input();
      drawScreen(g);
      try { Thread.sleep(delay); }
      catch (InterruptedException ie) {}
    }
  }

  // Method to Handle User Inputs
  private void input() {
    int keyStates = getKeyStates();
    
    sprite.setFrame(0);
    
    // Left
    if ((keyStates & LEFT_PRESSED) != 0) {
      currentX = Math.max(0, currentX - 1);
      sprite.setFrame(1);
    }  

    // Right
    if ((keyStates & RIGHT_PRESSED) !=0 )
      if ( currentX + 5 < width) {
        currentX = Math.min(width, currentX + 1);
        sprite.setFrame(3);
      }  

    // Up
    if ((keyStates & UP_PRESSED) != 0) {
      currentY = Math.max(0, currentY - 1);
      sprite.setFrame(2);
    }

    // Down
    if ((keyStates & DOWN_PRESSED) !=0)
      if ( currentY + 10 < height) {
        currentY = Math.min(height, currentY + 1);
        sprite.setFrame(4);
      }  
  }

  // Method to Display Graphics
  private void drawScreen(Graphics g) {
    //g.setColor(0xffffff);
    g.setColor(0xFF0000);
    g.fillRect(0, 0, getWidth(), getHeight());
    g.setColor(0x0000ff);   
    
    // display sprites     
    sprite.setPosition(currentX,currentY);
    sprite.paint(g);
    nonTransparentSprite.paint(g);
    
    flushGraphics();
  }  
  
}