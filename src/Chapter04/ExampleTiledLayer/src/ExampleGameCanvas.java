import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class ExampleGameCanvas extends GameCanvas implements Runnable {
  private boolean isPlay;   // Game Loop runs when isPlay is true
  private long delay;       // To give thread consistency  
  private int width;        // To hold screen width
  private int height;       // To hold screen height


  // Layer Manager
  private LayerManager layerManager;

  // TiledLayer
  private TiledLayer tiledBackground;    

  // Constructor and initialization
  public ExampleGameCanvas() throws Exception {
    super(true);
    width = getWidth();
    height = getHeight();
    delay = 20;

    tiledBackground = initBackground();
    layerManager = new LayerManager();
    layerManager.append(tiledBackground);
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
      try {
        Thread.sleep(delay);
      } catch (InterruptedException ie) {
      }
    }
  }

  // Method to Handle User Inputs
  private void input() {
     // no inputs
  }

  // Method to Display Graphics
  private void drawScreen(Graphics g) {
    g.setColor(0xffffff);
    g.fillRect(0, 0, getWidth(), getHeight());
    g.setColor(0x0000ff);   
    
    layerManager.paint(g,0,0);
    flushGraphics();
  }

  private TiledLayer initBackground() throws Exception {
    Image tileImages = Image.createImage("/tiles.png");
    TiledLayer tiledLayer = new TiledLayer(10,10,tileImages,32,32);

    int[] map = {
       5,  1,  1,  4,  1,  1,  1,  1,  1,  6,
       5,  1,  3,  1,  1,  3,  1,  1,  1,  6,
       5,  1,  2,  1,  1,  2,  1,  1,  1,  6,
       5,  1,  2,  3,  1,  2,  1,  1,  1,  6,
       5,  1,  4,  2,  1,  2,  1,  1,  1,  6,
       5,  1,  1,  4,  1,  2,  1,  1,  1,  6,
       5,  1,  1,  1,  1,  4,  1,  1,  1,  6,
       5,  1,  1,  1,  1,  1,  1,  1,  1,  6,
       5,  1,  1,  1,  1,  1,  1,  1,  1,  6,
       5,  1,  1,  1,  1,  1,  1,  1,  1,  6
    };
    for (int i=0; i < map.length; i++) {
      int column = i % 10;
      int row = (i - column) / 10;
      tiledLayer.setCell(column,row,map[i]);
    }      

    return tiledLayer;
  }
}