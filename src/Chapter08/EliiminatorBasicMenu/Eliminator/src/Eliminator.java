import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class Eliminator extends MIDlet {
  protected Display display;
  
  private Image splashLogo;
  private boolean isSplash = true; 
  
  MainMenuScreen mainMenuScreen;
  
  public Eliminator() {  	
  }
  
  public void startApp() {
    display = Display.getDisplay(this);
    mainMenuScreen = new MainMenuScreen(this);
    if(isSplash) {
      isSplash = false;      
      try {
        splashLogo = Image.createImage("/splash.png");                
        new SplashScreen(display, mainMenuScreen, splashLogo,3000);
      } catch(Exception ex) {
        mainMenuScreenShow(null);          
      }
    } else {                            
      mainMenuScreenShow(null);
    }        

  }
  
  public Display getDisplay() {
    return display;  
  }
  
  public void pauseApp() {
  }

  public void destroyApp(boolean unconditional) {
    System.gc();    
    notifyDestroyed();
  }   
  
  private Image createImage(String filename) {
    Image image = null;
    try {
      image = Image.createImage(filename);
    } catch (Exception e) {
    }
    return image;
  }      
  
  public void mainMenuScreenShow(Alert alert) {      
    if (alert==null)        
      display.setCurrent(mainMenuScreen);  
    else
      display.setCurrent(alert,mainMenuScreen);
  }  
  
  public void mainMenuScreenQuit() {  	
    destroyApp(true);
  } 
}