package dolla.command;

import dolla.DollaData;
import dolla.ui.ListUi;

import dolla.task.RecordList;


import java.util.ArrayList;

/**
 * Display all the tasks stored in the relevant RecordList depending on mode.
 */
public class ShowListCommand extends Command {

    private String mode;

    public ShowListCommand(String mode) {
        this.mode = mode;
    }

    /**
     * Prints out the logs from the specified RecordList in dollaData.
     * @param dollaData Data to be manipulated.
     */
    @Override
    public void execute(DollaData dollaData) {

        RecordList recordList = dollaData.getRecordListObj(mode);
        boolean listIsEmpty = (recordList.size() == 0);

        if (listIsEmpty) { // TODO: Place this in proper place
            ListUi.printEmptyListError(mode);
        } else if (mode.equals(MODE_ENTRY)) {
            ListUi.printList(mode, recordList);
        } else if (mode.equals(MODE_DEBT)) {
            ListUi.printList(mode, recordList);
        } else if (mode.equals(MODE_LIMIT)) {
            ListUi.printList(mode, recordList);
        } else if (mode.equals(MODE_SHORTCUT)) {
            ListUi.printList(mode, recordList);
        }
    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}
