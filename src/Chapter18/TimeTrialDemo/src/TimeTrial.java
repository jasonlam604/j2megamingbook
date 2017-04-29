import javax.microedition.rms.*;
import java.util.*;
import java.io.*;

public class TimeTrial extends BaseRMS {
  public static final int TT_DATE = 0;
  public static final int TT_DAYS = 1;
  public static final int TT_PLAYS = 2;

  public static final int MAX_NUM_DAYS = 30;
  public static final int MAX_NUM_PLAYS = 10;
  
  Calendar currentCalendar;
  Date currentDate;
  int  currentDateAsInt; // YYYYMMDD
  
  private int timeTrialType;
  private String value;
  private String decryptedValue;

  private int numDays;
  private int numPlays;

  /**
   * Constructor
   */
  public TimeTrial(String rmsName,int timeTrialType) throws Exception {
    super(rmsName);
    setTimeTrialType(timeTrialType);
    
    currentDate = new Date();
    currentCalendar = Calendar.getInstance();	
    currentCalendar.setTime(currentDate);      
    currentDateAsInt = getCurrentDateAsInt();
    
  }

  /**
   * Change TimeTrial Method
   */
  public void setTimeTrialType(int timeTrialType) throws Exception {
    if(timeTrialType == TT_DATE || timeTrialType == TT_DAYS || timeTrialType == TT_PLAYS) {
      this.timeTrialType = timeTrialType;
    } else {
     throw new Exception("Invalid Time Trial Option");
    }
  }

  /**
   * Check if App is Exired
   */
  public boolean isValid(String value) throws Exception {
    boolean result = false;
    this.value = value;
    decryptedValue = Base64.decodeToString(value);
    switch (this.timeTrialType) {
      case TT_DATE: result = isValidDate(); break;
      case TT_DAYS: result = isValidDays(); break;
      case TT_PLAYS: result = isValidPlays(); break;
    };
    return result;
  }

  /**
   * Time Trial by Date
   */
  private boolean isValidDate() throws Exception {
    boolean result = false;
        
    Date expireDate = new Date();
    Calendar expireCalendar = Calendar.getInstance();

    expireCalendar.set(Calendar.YEAR,Integer.parseInt(decryptedValue.substring(0,4)));
    expireCalendar.set(Calendar.MONTH,Integer.parseInt(decryptedValue.substring(4,6)));
    expireCalendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(decryptedValue.substring(6,8)));
    expireDate = expireCalendar.getTime();

    if (currentDate.getTime() < expireDate.getTime())
      result = true;

    return result;
  }

  /**
   * Time Trial By Number of Days
   */
  private boolean isValidDays() throws Exception {
    boolean result = false;
    
    loadTimeTrialData();   
    if (currentDateAsInt - numDays < Integer.parseInt(decryptedValue)) {
      result = true;      
    }
    return result;
  }

  /**
   * Time Trial By Number of Plays
   */

  private boolean isValidPlays() throws Exception {
    boolean result = false;
    loadTimeTrialData();
    System.out.println("Play: " + decryptedValue + " Plays RMS: " + numPlays);
    if (numPlays <= Integer.parseInt(decryptedValue)) {     
      updatePlays(++numPlays);	
      result = true;	
    }
    
    return result;
  }

  public void loadTimeTrialData() throws Exception {
    try {
      // Will call either loadData() or createDefaultData()
      this.open();

      if (this.getRecordStore() != null)
        this.close();
    } catch (Exception e) {
      throw new Exception("Error loading Settings" + e);
    }
  }

  private void updatePlays(int numPlays) throws Exception {
    try {
      // load current scores
      this.open();

      // Update
      this.numPlays = numPlays;      
      updateData();

      // close
      if (this.getRecordStore() != null)
        this.close();
    } catch (Exception e) {
      throw new Exception(this.getRMSName() + "::updateTimeTrial::" + e);
    }
  }
  
  private int getCurrentDateAsInt() {
    StringBuffer sb = new StringBuffer();
    sb.append(String.valueOf(currentCalendar.get(Calendar.YEAR)));
    if (currentCalendar.get(Calendar.MONTH) < 10)      
      sb.append("0" + String.valueOf(currentCalendar.get(Calendar.MONTH)));
    else
      sb.append(String.valueOf(currentCalendar.get(Calendar.MONTH)));
    sb.append(String.valueOf(currentCalendar.get(Calendar.DAY_OF_MONTH)));      
      
    return Integer.parseInt(sb.toString());        	
  }

  ////////////////////////////////////////////////////////////
  // RMS Related methods (loadData,createDefaultData, updateData) inherited from BaseRMS

  protected void loadData() throws Exception {
    try {
        byte[] record = this.getRecordStore().getRecord(1);
        DataInputStream istream = new DataInputStream(new ByteArrayInputStream(record,0,record.length));
        numDays = istream.readInt();
        numPlays = istream.readInt();
    } catch (Exception e) {
      throw new Exception (this.getRMSName() + "::loadData::" + e);
    }
  }

  /**
   * Default values for numDays will be currentDay (first day
   * the user plays the game
   * 
   * numPlays is defaulted to zero number times played
   */
  protected void createDefaultData() throws Exception {
    try {                      
      numDays = currentDateAsInt;      
      numPlays = 0;
      
      ByteArrayOutputStream bstream = new ByteArrayOutputStream(12);
      DataOutputStream ostream = new DataOutputStream(bstream);
      ostream.writeInt(numDays);
      ostream.writeInt(numPlays);
      ostream.flush();
      ostream.close();
      byte[] record = bstream.toByteArray();
      this.getRecordStore().addRecord(record,0,record.length);
    } catch (Exception e) {
      throw new Exception(this.getRMSName() + "::createDefaultData::" + e);
    }
  }

  protected void updateData() throws Exception {
    try { 
      ByteArrayOutputStream bstream = new ByteArrayOutputStream(12);
      DataOutputStream ostream = new DataOutputStream(bstream);
      ostream.writeInt(numDays);
      ostream.writeInt(numPlays);
      ostream.flush();
      ostream.close();
      byte[] record = bstream.toByteArray();
      this.getRecordStore().setRecord(1, record, 0, record.length);
    } catch(Exception e) {
      throw new Exception(this.getRMSName() + "::updateData::" + e);
    }
  }

}