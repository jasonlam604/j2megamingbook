import javax.microedition.rms.*;
import java.util.*;
import java.io.*;

abstract public class BaseRMS {
  private String rmsName;
  private RecordStore recordStore;

  BaseRMS(String rmsName) {
    this.rmsName = rmsName;
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

  public String getRMSName() {
    return this.rmsName;
  }

  abstract void loadData() throws Exception;
  abstract void createDefaultData() throws Exception;
  abstract void updateData() throws Exception;
}