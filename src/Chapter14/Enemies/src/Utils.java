import javax.microedition.lcdui.*;

public class Utils {
  public static final Font cFont = Font.getFont (Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_SMALL);	
	
  private static String locale;
  private static int localeIndex;  
  public static final String[] supportedLocales = {"en","de"};  
  
  private static int ix=0;	
  public static final int ID_CONTINUE = ix++;
  public static final int ID_NEW = ix++;
  public static final int ID_SETTINGS = ix++;
  public static final int ID_HIGH_SCORE = ix++;
  public static final int ID_HELP = ix++;
  public static final int ID_ABOUT = ix++;
  public static final int ID_EXIT = ix++;
  public static final int ID_SELECT = ix++;
  public static final int ID_BACK = ix++;
  public static final int ID_RESET = ix++;
  public static final int ID_OK = ix++;
  public static final int ID_TITLE= ix++;  
  public static final int ID_ERROR= ix++;  
  public static final int ID_ERROR_SUPPORT = ix++;
  public static final int ID_HELP_CONTROLS = ix++;
  public static final int ID_VERSION = ix++;
  public static final int ID_MUSIC = ix++;
  public static final int ID_SOUND = ix++;
  public static final int ID_VIBRATE = ix++;  
  public static final int ID_AUTOFIRE = ix++;
  
  private static final String[][] translationText = {
    {"Continue","New Game","Settings","High Score","Help","About","Exit","Select","Back","Reset","OK","Elminator","ERROR",
     "Game not functioning correctly.  Please contact support","Press ........",
     "Elminator\nVersion 1.0.0\nDeveloped by Jason Lam\nGraphics by Leeman Cheng and Carlo Casimiro","Music","Sound","Vibrate","Auto Fire"
    }, 
    
    // Should be German -- update later
    {"Continue","New Game","Settings","High Score","Help","About","Exit","Select","Back","Reset","OK","Elminator","ERROR",
     "Game not functioning correctly.  Please contact support","Press ........",
     "Elminator\nVersion 1.0.0\nDeveloped by Jason Lam\nGraphics by Leeman Cheng and Carlo Casimiro","Music","Sound","Vibrate","Auto Fire"
    }
    
  };
  
  private Utils() {
  }
  
  static {
    locale = System.getProperty("microedition.locale");
    
    localeIndex = -1;
    for (int i=0; i< supportedLocales.length; i++) {
      if (locale.equals(supportedLocales[i])) {
      	localeIndex = i;
      	break;
      }
    }
    if (localeIndex == -1) {
      localeIndex = 0;  // default for english
    }      
  }
  
  public static final String getStr(int key) {
    return translationText[localeIndex][key];
  }
  
  public static final Image createImage(String filename) {
    Image image = null;
    try {
      image = Image.createImage(filename);
    } catch (Exception e) {
    }
    return image;
  }  
}