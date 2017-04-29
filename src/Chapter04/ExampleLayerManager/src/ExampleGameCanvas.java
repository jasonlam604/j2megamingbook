import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class ExampleGameCanvas extends GameCanvas implements Runnable {
  private boolean isPlay;   // Game Loop runs when isPlay is true
  private long delay;       // To give thread consistency
  private int currentX, currentY;  // To hold current position of the 'X'
  private int width;        // To hold screen width
  private int height;       // To hold screen height
  
  // Sprites to be used
  private Sprite playerSprite;  
  private Sprite backgroundSprite;
  
  // Layer Manager
  private LayerManager layerManager;
  
  // Constructor and initialization
  public ExampleGameCanvas() throws Exception {
    super(true);
    width = getWidth();    
    height = getHeight();    
    
    currentX = width / 2;
    currentY = height / 2;
    delay = 20;
    
    // Load Images to Sprites
    Image playerImage = Image.createImage("/transparent.png");      
    playerSprite = new Sprite (playerImage,32,32); 
    
    Image backgroundImage = Image.createImage("/background.png");
    backgroundSprite = new Sprite(backgroundImage);
    
    layerManager = new LayerManager();
    layerManager.append(playerSprite);
    layerManager.append(backgroundSprite);
    
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
    
    playerSprite.setFrame(0);
    
    // Left
    if ((keyStates & LEFT_PRESSED) != 0) {
      currentX = Math.max(0, currentX - 1);
      playerSprite.setFrame(1);
    }  

    // Right
    if ((keyStates & RIGHT_PRESSED) !=0 )
      if ( currentX + 5 < width) {
        currentX = Math.min(width, currentX + 1);
        playerSprite.setFrame(3);
      }  

    // Up
    if ((keyStates & UP_PRESSED) != 0) {
      currentY = Math.max(0, currentY - 1);
      playerSprite.setFrame(2);
    }

    // Down
    if ((keyStates & DOWN_PRESSED) !=0)
      if ( currentY + 10 < height) {
        currentY = Math.min(height, currentY + 1);
        playerSprite.setFrame(4);
      }  
  }

  // Method to Display Graphics
  private void drawScreen(Graphics g) {
 
    //g.setColor(0x00C000);
    g.setColor(0xffffff);
    g.fillRect(0, 0, getWidth(), getHeight());
    g.setColor(0x0000ff);   
    
    // updating player sprite position
    playerSprite.setPosition(currentX,currentY);  
    
    // display all layers 
    //layerManager.paint(g,0,0);        
    layerManager.setViewWindow(55,20,140,140);   
    layerManager.paint(g,20,20);
    
    flushGraphics();
  }  
  
}