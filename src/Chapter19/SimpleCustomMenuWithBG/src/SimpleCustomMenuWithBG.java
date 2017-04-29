import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.util.*;

public class SimpleCustomMenuWithBG extends MIDlet implements CommandListener {

  Display display;
  Display pauseDisplay;
  boolean isSplash = true;
  MenuScreen menuScreen;

  public SimpleCustomMenuWithBG() {
    MenuScreen menuScreen = new MenuScreen();
    display = Display.getDisplay(this);
    display.setCurrent(menuScreen);    
  }  

  protected void startApp() throws MIDletStateChangeException   {
  }  
  
  protected void pauseApp() {  }
  protected void destroyApp (boolean flag) throws MIDletStateChangeException {}  
  
  public void commandAction (Command cmd, Displayable dis) {
  	
  }
}