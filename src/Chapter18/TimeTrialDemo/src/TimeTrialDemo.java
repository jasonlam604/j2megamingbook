import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class TimeTrialDemo extends MIDlet implements CommandListener {
 private Display display;
 private Command cmdExit = new Command("Exit", Command.EXIT,1);;
 private Form formMain;
 private StringItem stringItem;
  
 private TimeTrial timeTrial;
 private static final String rmsName = "TimeTrialDemoRMS";
 private boolean result;

 public TimeTrialDemo() {
   try {	
   	
     // Manual Encrypt - You may need this to change the values in the JAD file    
     //System.out.println("Manual Encryption: " + Base64.encodeToString("20031015"));   	
   	
     // Time Trial - By Date	
     //timeTrial = new TimeTrial(rmsName,TimeTrial.TT_DATE);   
     //result = TimeTrial.isValid(getAppProperty("Expire-Date"));
     
     // Time Trial - Number of Days
     //timeTrial = new TimeTrial(rmsName,TimeTrial.TT_DAYS);
     //result = TimeTrial.isValid(getAppProperty("Expire-Days"));
     
     // Time Trial - Number of Plays
     timeTrial = new TimeTrial(rmsName,TimeTrial.TT_PLAYS);
     result = timeTrial.isValid(getAppProperty("Expire-Plays"));
     
     if (result)
       System.out.println("Game NOT Expired");
     else
       System.out.println("Game Expired");
   } catch (Exception ex) {
     System.out.println(ex);
   }
   display = Display.getDisplay(this);
   stringItem = new StringItem("","See Console");
   formMain = new Form("Time Trial Demo");
   formMain.addCommand(cmdExit);
   formMain.append(stringItem);
   formMain.setCommandListener(this);
 }

 public void startApp() {
   display.setCurrent(formMain);
 }

 public void pauseApp() {  }
 public void destroyApp(boolean unconditional) {}

 public void commandAction(Command c, Displayable s) {
   if (c == cmdExit) {
     destroyApp(false);
     notifyDestroyed();
   }
 }
}