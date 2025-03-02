package dolla.model;

import dolla.storage.StorageWrite;

import java.util.ArrayList;

//@@author tata

public class BillList extends RecordList {

    public BillList(ArrayList<Record> importBillList) {
        super(importBillList);
    }

    @Override
    public void add(Record newRecord) {
        super.add(newRecord);
        StorageWrite.setBill(get()); //save
    }

    @Override
    public void removeFromList(int index) {
        super.removeFromList(index);
        StorageWrite.setBill(get());
    }

    @Override
    public void setRecordList(ArrayList<Record> recordList) {
        this.recordArrayList = recordList;
        StorageWrite.setBill(get());
    }

    @Override
    public void addWithIndex(int modifyIndex, Record newRecord) {
        super.addWithIndex(modifyIndex, newRecord);
        StorageWrite.setBill(get());
    }
}
