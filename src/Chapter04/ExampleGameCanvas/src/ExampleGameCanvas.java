import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class ExampleGameCanvas extends GameCanvas implements Runnable {
  private boolean isPlay;   // Game Loop runs when isPlay is true
  private long delay;       // To give thread consistency
  private int currentX, currentY;  // To hold current position of the 'X'
  private int width;        // To hold screen width
  private int height;       // To hold screen height

  // Constructor and initialization
  public ExampleGameCanvas() {
    super(true);
    width = getWidth();
    height = getHeight();
    currentX = width / 2;
    currentY = height / 2;
    delay = 20;
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

    // Left
    if ((keyStates & LEFT_PRESSED) != 0)
      currentX = Math.max(0, currentX - 1);

    // Right
    if ((keyStates & RIGHT_PRESSED) !=0 )
      if ( currentX + 5 < width)
        currentX = Math.min(width, currentX + 1);

    // Up
    if ((keyStates & UP_PRESSED) != 0)
      currentY = Math.max(0, currentY - 1);

    // Down
    if ((keyStates & DOWN_PRESSED) !=0)
      if ( currentY + 10 < height)
        currentY = Math.min(height, currentY + 1);
  }

  // Method to Display Graphics
  private void drawScreen(Graphics g) {
    g.setColor(0xffffff);
    g.fillRect(0, 0, getWidth(), getHeight());
    g.setColor(0x0000ff);
    g.drawString("X",currentX,currentY,Graphics.TOP|Graphics.LEFT);
    flushGraphics();
  }
}