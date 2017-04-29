import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class Elminator extends MIDlet implements CommandListener {
  Display display;
  Image splashLogo;
  boolean isSplash = true;

  GameScreen gameScreen;

  Alert alert;

  static Score score;
  static final String scoreRMSName = "ElminiatorScore";
  static Settings settings;
  static final String settingsRMSName = "ElminatorSettings";

  static final boolean CONST_DEBUG = true;

  // Main Menu
  Command selectCommand = new Command(Utils.getStr(Utils.ID_SELECT), Command.ITEM,1);
  Command exitCommand = new Command(Utils.getStr(Utils.ID_EXIT), Command.EXIT,1);
  List mainMenu;

  public Elminator() {
  }

  public void startApp() {
    display = Display.getDisplay(this);
    if(isSplash) {
      isSplash = false;
      try {
        settings = new Settings(settingsRMSName);
        score = new Score(scoreRMSName);

        mainMenu = new List(Utils.getStr(Utils.ID_TITLE),Choice.IMPLICIT);
        if (settings.level > 0)
          mainMenu.append(Utils.getStr(Utils.ID_CONTINUE),null);
        mainMenu.append(Utils.getStr(Utils.ID_NEW),null);
        mainMenu.append(Utils.getStr(Utils.ID_SETTINGS),null);
        mainMenu.append(Utils.getStr(Utils.ID_HIGH_SCORE), null);
        mainMenu.append(Utils.getStr(Utils.ID_HELP),null);
        mainMenu.append(Utils.getStr(Utils.ID_ABOUT),null);
        mainMenu.addCommand(exitCommand);
        mainMenu.addCommand(selectCommand);
        mainMenu.setCommandListener(this);

        splashLogo = Utils.createImage("/splash.png");
        new SplashScreen(display, mainMenu, splashLogo,3000);
      } catch(Exception ex) {
        showErrorMsg(null);
      }
    } else {
      mainMenuScreenShow(null);
    }
  }

  public void pauseApp() {
  }

  public void destroyApp(boolean unconditional) {
    System.gc();
    notifyDestroyed();
  }

  protected void mainMenuScreenShow(Alert alert) {
    if (alert==null)
      display.setCurrent(mainMenu);
    else
      display.setCurrent(mainMenu);
  }

  protected void mainMenuScreenQuit() {
    destroyApp(true);
    notifyDestroyed();
  }

  protected void showErrorMsg(String alertMsg) {
    if (alertMsg == null || CONST_DEBUG == false) {
      alertMsg = Utils.getStr(Utils.ID_ERROR_SUPPORT);
    }
    alert = new Alert(Utils.getStr(Utils.ID_ERROR),alertMsg,null,null);
    alert.setTimeout(Alert.FOREVER);
    alert.setType(AlertType.ERROR);
    this.mainMenuScreenShow(alert);
  }

  public void commandAction(Command c, Displayable d) {
    if (c == exitCommand) {
      mainMenuScreenQuit();
    } else {
       try {
         switch (mainMenu.getSelectedIndex()) {
           case 0:
             gameScreen = null;
             gameScreen = new GameScreen(this,settings,score);
             gameScreen.start();
             display.setCurrent(gameScreen);
             break;
           case 1: display.setCurrent(new Gui(this).settingsForm(settings)); break;
           case 2: display.setCurrent(new Gui(this).highScoreForm(score)); break;
           case 3: display.setCurrent(new Gui(this).textForm(Utils.getStr(Utils.ID_HELP),Utils.getStr(Utils.ID_HELP_CONTROLS))); break;
           case 4: display.setCurrent(new Gui(this).textForm(Utils.getStr(Utils.ID_ABOUT),Utils.getStr(Utils.ID_VERSION))); break;
         };
       } catch (Exception ex) {
         showErrorMsg("null");
       }
    }
  }
}