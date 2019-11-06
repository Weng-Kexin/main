package dolla.command;

import dolla.DollaData;
import dolla.Time;
import dolla.command.action.state.EntryState;
import dolla.command.action.Redo;
import dolla.command.action.state.UndoStateList;
import dolla.task.EntryList;
import dolla.task.Record;
import dolla.ui.Ui;
import dolla.task.Entry;

import java.time.LocalDate;

/**
 * AddEntryCommand is used to create a new Entry entity.
 */
public class AddEntryCommand extends Command {

    private String type;
    private double amount;
    private String description;
    private LocalDate date;
    private String tagName;
    private static final String mode = MODE_ENTRY;

    /**
     * Creates an instance of AddEntryCommand.
     * @param type Income or Expense.
     * @param amount Amount of money that is earned/spent.
     * @param description Details pertaining to the entry.
     * @param date Date of income/expense.
     */
    public AddEntryCommand(String type, double amount, String description, LocalDate date, String tagName) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.tagName = tagName;
    }

    @Override
    public void execute(DollaData dollaData) {
        Entry newEntry = new Entry(type, amount, description, date, tagName);
        EntryList entryList = (EntryList) dollaData.getRecordListObj(mode);
        UndoStateList.addState(new EntryState(entryList.get()), mode);///////////////////////////////////////
        Redo.clearRedoState(mode);
        int duplicateEntryIndex = entryList.findExistingRecordIndex(dollaData, newEntry, mode);
        if (recordDoesNotExist(duplicateEntryIndex)) {
            dollaData.addToRecordList(mode, newEntry);
            Ui.echoAddRecord(newEntry);
        } else {
            Record existingEntry = entryList.getFromList(duplicateEntryIndex);
            Ui.existingRecordPrinter(existingEntry, mode);
        }
    }

    @Override
    public String getCommandInfo() { //todo: add tag part
        String command = "AddEntryCommand";
        //return (command + "{ type: " + type + ", amount: " + amount + ", description: "
        //        + description + ", date: " + Time.dateToString(date) + ", prevPosition: "
        //        + prevPosition) + " }";
        return (command + "{ " + type + ", " + amount + ", "
                + description + ", " + Time.dateToString(date)) + "}";
    }
}
