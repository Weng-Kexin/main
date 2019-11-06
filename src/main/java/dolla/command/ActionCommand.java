package dolla.command;

import dolla.DollaData;
import dolla.command.action.Redo;
import dolla.command.action.Undo;
import dolla.task.Record;

import java.util.ArrayList;

//@@author yetong1895
public class ActionCommand extends Command {
    private String mode;
    private String command;
    private static final String UNDO = "undo";
    private static final String REDO = "redo";

    /**
     * This method will set the mode and command in this class.
     * @param mode the mode to be set in this class.
     * @param command the command to be set in this class.
     */
    public ActionCommand(String mode, String command) {
        this.mode = mode;
        this.command = command;
    }

    @Override
    public void execute(DollaData dollaData) throws Exception {
        switch (command) {
        case UNDO:
            ArrayList<Record> recordList = Undo.processUndoState(mode);
            if (recordList != null) {
                Redo.addToStateList(mode, dollaData.getRecordList(mode));
                dollaData.setRecordList(recordList);
            }
            break;
        case REDO:
            recordList = Redo.processRedoState(mode);
            if (recordList != null) {
                Undo.addToStateList(mode, dollaData.getRecordList(mode));
                dollaData.setRecordList(recordList);
            }
            break;
        default:
            break;
        }

    }

    @Override
    public String getCommandInfo() {
        return null;
    }

}
