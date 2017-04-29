import java.util.Timer;
import javax.microedition.lcdui.*;

public final class SplashScreen extends Canvas {
  private Display display;
  private Displayable next;
  private Timer timer;
  private Image image;    
  private int dismissTime;

  public SplashScreen(Display display, Displayable next, Image image,int dismissTime) throws Exception {
    timer = new Timer();
    this.display = display;
    this.next = next;
    this.image = image;        
    this.dismissTime = dismissTime;
    display.setCurrent(this);
  }

  static void access(SplashScreen splashScreen) {
    splashScreen.dismiss();
  }

  private void dismiss() {
    timer.cancel();
    display.setCurrent(next);
  }

  protected void keyPressed(int keyCode) {
    dismiss();
  }

  protected void paint(Graphics g) {
    g.setColor(0x00FFFFFF);    
    g.fillRect(0, 0, getWidth(), getHeight());
    g.setColor(0x00000000);        
    g.drawImage(image, getWidth() / 2, getHeight() / 2 - 5, 3);        
  }

  protected void pointerPressed(int x, int y) {
    dismiss();
  }

  protected void showNotify() {
    if(dismissTime > 0)
      timer.schedule(new CountDown(this), dismissTime);
  }
}