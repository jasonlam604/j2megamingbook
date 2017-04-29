import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class LocaleTest extends MIDlet {
    private LocaleLib localeLib;

    private Display display;
    TextBox box = null;

    public void startApp() {
      try {
      	localeLib = LocaleLib.getInstance();
        //localeLib = LocaleLib.getInstance("en-us");
        //localeLib = LocaleLib.getInstance("de");

        String[] localeList = localeLib.getSupportedLocales();
        for (int i=0; i<localeList.length; i++) {
          localeLib.setLocaleKey(localeList[i]);
          localeLib.loadLocale();

          System.out.println("\n\n============ Locale " + localeList[i] + " ========================");
          System.out.println(localeLib.getLocaleString("ID_HELLOWORLD"));
          System.out.println(localeLib.getLocaleString("ID_NEW"));
          System.out.println(localeLib.getLocaleString("ID_SETTINGS"));
          System.out.println(localeLib.getLocaleString("ID_HISCORE"));
          System.out.println(localeLib.getLocaleString("ID_HELP"));
          System.out.println(localeLib.getLocaleString("ID_ABOUT"));
          System.out.println(localeLib.getLocaleString("ID_EXIT"));
        }

        String temp2 = localeLib.getLocaleString("ID_HELLOWORLD");
        box = new TextBox("",temp2, 500, 0);
        display = Display.getDisplay(this);
        display.setCurrent(box);
      } catch (Exception ex) {
      	System.out.println(ex);
      }
    }

    public void destroyApp(boolean b) {}
    public void pauseApp() {}
}