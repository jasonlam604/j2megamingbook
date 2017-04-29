import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class Eliminator extends MIDlet {
  protected Display display;
  private Image splashLogo;
  private boolean isSplash = true;

  private MainMenuScreen mainMenuScreen;
  private SettingsScreen settingsScreen;
  private HighScoreScreen highScoreScreen;
  private HelpScreen helpScreen;
  private AboutScreen aboutScreen;
  
  private Alert alert;
  
  private static final boolean CONST_DEBUG = true;

  public Eliminator() {
  }

  public void startApp() {
    display = Display.getDisplay(this);
    if(isSplash) {
      isSplash = false;
      try {
        mainMenuScreen = new MainMenuScreen(this);        
        settingsScreen = new SettingsScreen(this);
        highScoreScreen = new HighScoreScreen(this);
        helpScreen = new HelpScreen(this);
        aboutScreen = new AboutScreen(this);      	
        splashLogo = Image.createImage("/splash.png");
        new SplashScreen(display, mainMenuScreen, splashLogo,3000);
      } catch(Exception ex) {
        showErrorMsg(null);
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

  protected void mainMenuScreenShow(Alert alert) {   
    if (alert==null)        
      display.setCurrent(mainMenuScreen);
    else
      display.setCurrent(alert,mainMenuScreen);   
  }  

  protected void settingsScreenShow() {
    display.setCurrent(settingsScreen);
  }

  protected void highScoreScreenShow() {
    display.setCurrent(highScoreScreen);
  }

  protected void helpScreenShow() {
    display.setCurrent(helpScreen);
  }

  protected void aboutScreenShow() {
    display.setCurrent(aboutScreen);
  }

  protected void mainMenuScreenQuit() {
    destroyApp(true);
  }
  
  protected void showErrorMsg(String alertMsg) {
    if (alertMsg == null || CONST_DEBUG == false) {
      alertMsg = "Error Starting Elminator, may or may not function correctly.  Please contact support.";
    }	
    alert = new Alert("Eliminator ERROR",alertMsg,null,null);
    alert.setTimeout(Alert.FOREVER);
    alert.setType(AlertType.ERROR);                       
    this.mainMenuScreenShow(alert);    	
  }
}