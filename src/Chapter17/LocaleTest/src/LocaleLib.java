import java.io.*;
import java.util.*;

public class LocaleLib {
  private static LocaleLib instance = null;
  private static String localeKey = null;
  private static String[] idKeys = null;
  private static String[] supportedLocales = null;
  private static String[] translationText = null;
  private static Hashtable translation = new Hashtable();

  private LocaleLib() {
  }

  private LocaleLib(String localeKey) throws Exception {
    try {
      supportedLocales = loadTextFile("/locale.support");
      idKeys = loadTextFile("/locale.keys");
      setLocaleKey(localeKey);
      loadLocale();
    } catch (Exception e) {
      throw new Exception ("Error Loading Locale Resources::" + e);
    }
  }

  public static LocaleLib getInstance() throws Exception {
    return getInstance(System.getProperty("microedition.locale"));
  }

  public static LocaleLib getInstance(String localKey) throws Exception {
    if (instance == null)
      instance = new LocaleLib(localKey);
    return instance;
  }

  public String getLocaleKey() {
    return this.localeKey;
  }

  public String getLocaleString(String stringKey) {
    return this.parseUnicode((String)translation.get(stringKey));
  }

  public void setLocaleKey(String localeKey) throws Exception {
    this.localeKey = localeKey.toLowerCase();
    if (!validateLocaleKey())
      throw new Exception("Invalid Locale Key");
  }

  public String[] getSupportedLocales() {
    return this.supportedLocales;
  }

  public void loadLocale() throws Exception {
    translationText = loadTextFile("/locale." + this.localeKey.toLowerCase());
    if (translationText.length == idKeys.length) {
      for (int i=0; i<idKeys.length; i++) {
        translation.put(idKeys[i],translationText[i]);
      }
    } else {
      throw new Exception("Invalid Locale Files, data not matching keys");
    }
  }

  /**
   * Ensure the current Locale key exists in the supported local list
   * as listed in locale.support
   *
   * @param none
   * @return false is invalid and true is valid
   */
  private boolean validateLocaleKey() {
    boolean result = false;
    for (int i=0; i<supportedLocales.length; i++) {
      if (localeKey.equals(supportedLocales[i].toLowerCase())) {
        result = true;
        break;
      }
    }
    return result;
  }

  /**
   * Read Text file deliminted by comma
   *
   * @param filename
   * @return Array with delimimted values
   */
  private String[] loadTextFile(String filename) throws Exception {
    String [] result = null;
    int resultIdx = 0;
    StringBuffer sb = null;
    Vector strings = null;
    Enumeration enum = null;

    try {
      Class c = this.getClass();
      InputStream is = c.getResourceAsStream(filename);
      if (is == null)
        throw new Exception("File Does Not Exist");

      sb = new StringBuffer();
      strings = new Vector();
      byte b[] = new byte[1];

      while ( is.read(b) != -1 ) {
        if (b[0] != 44 && b[0] > 31 && b[0] < 255) {
          sb.append(new String(b));
        } else {
          if (sb.toString().trim().length() > 0) {
            strings.addElement(sb.toString().trim());
          }
          sb.delete(0,sb.length());
        }
      }
      is.close();
      if (sb.toString().trim().length() > 0) {
         strings.addElement(sb.toString().trim());
      } else {
      	sb = null;
      }

      result = new String [strings.size()];
      enum = strings.elements();
      while (enum.hasMoreElements()) {
        result[resultIdx] = (String)enum.nextElement();
        resultIdx++;
      }
    } catch (Exception e) {
      throw new Exception ("ERROR: " + e);
    } finally {
      sb = null;
      strings = null;
      enum = null;
    }
    return result;
  }

  /**
   * Parse and translate unicode codes to the appropriate unicode symbol
   *
   * @param String value contain possibly both ASCII characters and unicode codes
   *        Unicode codes are signified by back slash then the character u
   * @return String value with unicode codes translated to thier appropriate unicode symbols
   */
  private String parseUnicode(String value) {
    String result = "";

    // Ensures value has at least one character
    if (value == null || value.length() <= 0)
      return result;

    int idx = 0;
    int idxFound = -1;
    int findLength = 2;
    StringBuffer sb = new StringBuffer();
    while ((idxFound = value.indexOf("\\u",idx)) != -1) {
      sb.append(value.substring(idx,idxFound));
      sb.append(convertStrToUnicode(value.substring(idxFound+2,idxFound+6)));
      idx = idxFound + 6;
    }
    if(idx < value.length()) {
      sb.append(value.substring(idx));
    }

    return sb.toString();
  }

  /**
   * Converts a unicode string code to the unicode symbol
   *
   * @param String value representating a unicode code ie: 0669
   * @return unicode symbol as a String
   */
  private String convertStrToUnicode(String value) {
    short valueAsShort = Short.parseShort(value.trim(),16);
    return String.valueOf((char)valueAsShort);
  }
}