import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class ExamplePauseResumeMIDP1 extends MIDlet {
 private Display display; 
 
 private GameCanvas gameCanvas;  

 public ExamplePauseResumeMIDP1() {
  display = Display.getDisplay(this);   
 }

 public void startApp() {
   try {
     this.gameCanvas = new GameCanvas(this);
     this.gameCanvas.start();
     display.setCurrent(this.gameCanvas);
   } catch (Exception ex) {
     System.out.println("error: " + ex);	
   }
 }

 public void pauseApp() {  }
 public void destroyApp(boolean unconditional) {  
   gameCanvas.autoSave();
 }
 
 public void exitGame() {
   destroyApp(false);
   notifyDestroyed(); 	
 }
}