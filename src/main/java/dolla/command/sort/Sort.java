package dolla.command.sort;

import dolla.model.Record;

import java.util.ArrayList;

class Sort {
    protected ArrayList<Record> sortedList;

    //@@author yetong1895
    /**
     * This method will set the ArrayList in this class to the ArrayList
     * being passed in.
     * @param unsortedList the ArrayList to be set to.
     */
    Sort(ArrayList<Record> unsortedList) {
        this.sortedList = unsortedList;
    }
}
