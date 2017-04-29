import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class ExampleGameCanvas extends GameCanvas implements Runnable {
  private boolean isPlay;   // Game Loop runs when isPlay is true
  private long delay;       // To give thread consistency  
  private int width;        // To hold screen width
  private int height;       // To hold screen height
  private int scnX, scnY;   // To hold screen starting viewpoint
  
  
  // Sprites to be used
  Image backgroundImage;
  private Sprite backgroundSprite;
  
  // Layer Manager
  private LayerManager layerManager;
  
  // Constructor and initialization
  public ExampleGameCanvas() throws Exception {
    super(true);
    width = getWidth();    
    height = getHeight();    
    
    scnX = 55;
    scnY = 20;
    delay = 20;
    
    // Load Images to Sprites    
    backgroundImage = Image.createImage("/background.png");
    backgroundSprite = new Sprite(backgroundImage);
    
    layerManager = new LayerManager();    
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
    
   
    if ((keyStates & LEFT_PRESSED) != 0) {
      if (scnX - 1 > 0)	
        scnX--;	
    }
    if ((keyStates & RIGHT_PRESSED) != 0) {
      if (scnX + 1 + 140 < backgroundImage.getWidth())
        scnX++;	
    }
  }

  // Method to Display Graphics
  private void drawScreen(Graphics g) {
 
    //g.setColor(0x00C000);
    g.setColor(0xffffff);
    g.fillRect(0, 0, getWidth(), getHeight());
    g.setColor(0x0000ff);     
    
    // display all layers 
    layerManager.setViewWindow(scnX,scnY,140,140);   
    layerManager.paint(g,20,20);
    
    flushGraphics();
  }  
  
}