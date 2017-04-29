import javax.microedition.rms.*;
import java.util.*;
import java.io.*;

public class Score {
  private String rmsName;
  private RecordStore recordStore;
  	
  private String[] names = new String[10];
  private int[] values = new int[10];

  public Score(String rmsName) {
    this.rmsName = rmsName;    
    initValues();
  }

  private void initValues() {
    names[0] = "NEW";
    names[1] = "NEW";
    names[2] = "NEW";
    names[3] = "NEW";
    names[4] = "NEW";
    names[5] = "NEW";
    names[6] = "NEW";
    names[7] = "NEW";
    names[8] = "NEW";
    names[9] = "NEW";
    values[0] = 0;
    values[1] = 0;
    values[2] = 0;
    values[3] = 0;
    values[4] = 0;
    values[5] = 0;
    values[6] = 0;
    values[7] = 0;
    values[8] = 0;
    values[9] = 0;
  }
  
  public void open() throws Exception {
    try {
      recordStore = RecordStore.openRecordStore(this.rmsName,true);
      if (recordStore.getNumRecords() > 0) {
        loadData();
      } else {
        createDefaultData();
      }
    } catch (Exception e) {
      throw new Exception(this.rmsName+"::open::"+e);
    }
  }

  public void close() throws Exception {
    if (recordStore != null) {
      try {
        recordStore.closeRecordStore();
      } catch(Exception e) {
      	throw new Exception(this.rmsName+"::close::"+e);
      }
    }
  }

  public RecordStore getRecordStore() {
    return this.recordStore;
  }  

  public void loadScores() throws Exception {
    try {
      // Will call either loadData() or createDefaultData()
      this.open();

      // Add any other neccessary processing, in this case there is none

      // Close
      if (this.getRecordStore() != null)
        this.close();
    } catch (Exception e) {
      throw new Exception("Error loading Scores" + e);
    }
  }
  
  public String getRMSName() {
    return this.rmsName;
  }  

  public int[] getValues() {
    return this.values;
  }

  public String[] getNames() {
    return this.names;
  }

  public void reset() throws Exception {
    this.open();
    initValues();
    updateData();
    if (this.getRecordStore() != null)
      this.close();
  }

  public void updateScores(int score, String name) throws Exception {
    try {
      for (int i = 0; i < names.length; i++) {
        if (score >= values[i]) {
          // load current scores
          this.open();

          // Shift the score table.
          for (int j = names.length - 1; j > i; j--) {
             values[j] = values[j - 1];
             names[j] = names[j - 1];
          }

          // Insert the new score.
          this.values[i] = score;
          this.names[i] = name;

          // Update High Scores
          updateData();

          // close
          if (this.getRecordStore() != null)
           this.close();
          break;
        }
      }
    } catch (Exception e) {
      throw new Exception(this.getRMSName() + "::updateScores::" + e);
    }
  }

  public boolean isHighScore(int score) throws Exception {
    boolean isHighScore = false;
    try {
     for (int i = 0; i < names.length; i++) {
       if (score >= values[i])
         isHighScore = true;
     }
    } catch (Exception e) {
      throw new Exception(this.getRMSName() + "::isHighScore::" + e);
    }
    return isHighScore;
  }

  protected void loadData() throws Exception {
    try {
      for (int i = 0; i < names.length; i++) {
        byte[] record = this.getRecordStore().getRecord(i + 1);
        DataInputStream istream = new DataInputStream(new ByteArrayInputStream(record,0,record.length));
        values[i] = istream.readInt();
        names[i] = istream.readUTF();
      }
    } catch (Exception e) {
      throw new Exception (this.getRMSName() + "::loadData::" + e);
    }
  }

  protected void createDefaultData() throws Exception {
    try {
      for (int i = 0; i < names.length; i++) {
        ByteArrayOutputStream bstream = new ByteArrayOutputStream(12);
        DataOutputStream ostream = new DataOutputStream(bstream);
        ostream.writeInt(values[i]);
        ostream.writeUTF(names[i]);
        ostream.flush();
        ostream.close();
        byte[] record = bstream.toByteArray();
        this.getRecordStore().addRecord(record, 0, record.length);
      }
    } catch (Exception e) {
      throw new Exception(this.getRMSName() + "::createDefaultData::" + e);
    }
  }

  protected void updateData() throws Exception {
    try {
      for (int i = 0; i < names.length; i++) {
        ByteArrayOutputStream bstream = new ByteArrayOutputStream(12);
        DataOutputStream ostream = new DataOutputStream(bstream);
        ostream.writeInt(values[i]);
        ostream.writeUTF(names[i]);
        ostream.flush();
        ostream.close();
        byte[] record = bstream.toByteArray();
        this.getRecordStore().setRecord(i + 1, record, 0, record.length);
      }
    } catch(Exception e) {
      throw new Exception(this.getRMSName() + "::updateData::" + e);
    }
  }
}