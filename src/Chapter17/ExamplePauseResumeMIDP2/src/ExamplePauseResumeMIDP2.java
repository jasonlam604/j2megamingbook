import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class ExamplePauseResumeMIDP2 extends MIDlet {
 private Display display;

 private GameScreen gameScreen;


 public ExamplePauseResumeMIDP2() {
  display = Display.getDisplay(this);
 }

 public void startApp() {
   try {
     this.gameScreen = new GameScreen(this);
     this.gameScreen.start();
     display.setCurrent(this.gameScreen);
   } catch (Exception ex) {
     System.out.println("error: " + ex);
   }
 }

 public void pauseApp() {  }
 public void destroyApp(boolean unconditional) {
   gameScreen.autoSave();
 }

 public void exitGame() {
   destroyApp(false);
   notifyDestroyed();
 }
}