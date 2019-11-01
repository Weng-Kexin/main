package dolla;

import dolla.task.DebtList;
import dolla.task.EntryList;
import dolla.task.LimitList;
import dolla.task.Record;
import dolla.task.RecordList;
import dolla.task.BillList;

import static dolla.storage.Storage.getDebtsFromSave;
import static dolla.storage.Storage.getEntriesFromSave;
import static dolla.storage.Storage.getLimitsFromSave;
import static dolla.storage.Storage.getBillsFromSave;

public class DollaData implements ModeStringList {

    private String mode = MODE_DOLLA;
    private EntryList entryList; // TODO: Find out alternatives to using a public variable
    private DebtList debtList;
    private LimitList limitList;
    private BillList billList;

    private String prevMode;
    private int modifyIndex;

    /**
     * Creates an instance of DollaData to store and manipulate data.
     */
    public DollaData() {
        //this.entryList = new EntryList(new ArrayList<Record>());
        this.entryList = new EntryList(getEntriesFromSave()); //Import from save file
        this.limitList = new LimitList(getLimitsFromSave()); //Import from save file
        this.debtList = new DebtList(getDebtsFromSave()); //Import from save file
        this.billList = new BillList(getBillsFromSave()); //Import from save file

    }

    /**
     * Returns the relevant RecordList (ie. EntryList) according to the specified mode.
     *
     * @param mode The mode pertaining to the RecordList to be retrieved.
     * @return The RecordList according to the specified mode.
     */
    public RecordList getRecordList(String mode) {
        switch (mode) {
        case MODE_ENTRY:
            return entryList;
        case MODE_DEBT:
            return debtList;
        case MODE_LIMIT:
            return limitList;
        default:
            return null; // placeholder so that Dolla can compile
        }
    }

    /**
     * Returns the relevant record (ie. Entry) from the specified index
     * in the relevant RecordList (ie. EntryList) according to the specified mode.
     * @param mode The mode pertaining to the Record to be retrieved.
     * @param index corresponding to the Record to be retried from the RecordList
     * @return The Record according to the specified mode.
     */
    public Record getRecordFromList(String mode, int index) {
        switch (mode) {
        case MODE_ENTRY:
            return entryList.getFromList(index);
        case MODE_DEBT:
            return debtList.getFromList(index);
        case MODE_LIMIT:
            return limitList.getFromList(index);
        default:
            return null; // placeholder so that Dolla can compile
        }
    }

    public RecordList getBillRecordList() {
        return billList;
    }

    /**
     * Adds a new Record (ie. Entry) into the relevant RecordList (ie. EntryList) according to the specified mode.
     *
     * @param mode   The mode pertaining to the RecordList to be retrieved.
     * @param newRecord The new Record to be added into the relevant RecordList.
     */
    public void addToRecordList(String mode, Record newRecord) {
        if (mode.equals(MODE_ENTRY)) {
            entryList.add(newRecord);
        } else if (mode.equals(MODE_DEBT)) {
            debtList.add(newRecord);
        } else if (mode.equals(MODE_LIMIT)) {
            limitList.add(newRecord);
        }
    }

    public void addBillToRecordList(Record newRecord) {
        billList.add(newRecord);
    }

    /**
     * Add to prev position.
     *
     * @param mode         the mode
     * @param newRecord       the new record
     * @param prevPosition the prev position
     */
    public void addToPrevPosition(String mode, Record newRecord, int prevPosition) {
        if (mode.equals(MODE_ENTRY)) {
            entryList.insertPrevPosition(prevPosition, newRecord);
        } else if (mode.equals(MODE_DEBT)) {
            debtList.insertPrevPosition(prevPosition, newRecord);
        } else if (mode.equals(MODE_LIMIT)) {
            limitList.insertPrevPosition(prevPosition, newRecord);
        }
    }

    /**
     * Remove from record list.
     *
     * @param mode  the mode
     * @param index the index
     */
    public void removeFromRecordList(String mode, int index) {
        if (mode.equals(MODE_ENTRY)) {
            entryList.removeFromList(index);
        } else if (mode.equals(MODE_DEBT)) {
            debtList.removeFromList(index);
        } else if (mode.equals(MODE_LIMIT)) {
            limitList.removeFromList(index);
        }
    }

    /**
     * Changes the content of a particular log in list to the specified one.
     *
     * @param newRecord the new record to replace the current item on the list.
     */
    public void modifyRecordList(Record newRecord) {
        switch (prevMode) {
        case MODE_ENTRY:
            entryList.removeFromList(modifyIndex);
            entryList.addWithIndex(modifyIndex, newRecord);
            break;
        case MODE_LIMIT:
            // TODO
            break;
        case MODE_DEBT:
            debtList.removeFromList(modifyIndex);
            debtList.addWithIndex(modifyIndex, newRecord);
            break;
        case MODE_SHORTCUT:
            // TODO
            break;
        default:
            break;
        }
    }

    public String getMode() {
        return mode;
    }

    /**
     * Updates Dolla's mode to the specified new mode.
     * @param newMode the mode to update to.
     */
    public void updateMode(String newMode) {
        mode = newMode;
    }

    /**
     * Prepares Dolla for modify mode by storing the previous mode and the index of the log to be modified.
     * @param prevMode the current mode of Dolla when this method is called.
     * @param index    the index of the log to be modified.
     */
    public void prepForModify(String prevMode, int index) {
        this.prevMode = prevMode;
        modifyIndex = index;
    }

    public void setModifyIndex(int index) {
        modifyIndex = index;
    }

    public String getPrevMode() {
        return prevMode;
    }
}
