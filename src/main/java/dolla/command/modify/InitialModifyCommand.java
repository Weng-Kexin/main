package dolla.command.modify;

import dolla.command.Command;
import dolla.model.DollaData;
import dolla.ui.ModifyUi;

//@@author omupenguin
/**
 * InitialModifyCommand is a command that runs first when the
 * user wants to execute a modify command.
 */
public class InitialModifyCommand extends Command {

    public InitialModifyCommand(String recordNumStr) {
        index = Integer.parseInt(recordNumStr) - 1;
    }

    @Override
    public void execute(DollaData dollaData) {
        String currMode = dollaData.getMode();
        if (isIndexInList(dollaData.getRecordListObj(currMode), currMode)) {
            ModifyUi.printInitialModifyMsg(currMode);
            dollaData.updateMode("modify " + currMode);
            dollaData.prepForModify(currMode, index);
        } else {
            //istUi.printNoRecordAssocError(index, currMode);
            return;
        }
    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}
