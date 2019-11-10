package dolla.command;

import dolla.model.DollaData;
import dolla.model.Bill;
import dolla.model.RecordList;
import dolla.ui.DebtUi;

import java.util.ArrayList;

//@@author tatayu
public class RemoveNameCommand extends Command {

    protected int billNum;
    protected String name;

    public RemoveNameCommand(int billNum, String name) {
        this.billNum = billNum;
        this.name = name;
    }

    @Override
    public void execute(DollaData dollaData) {
        int check = 0;
        RecordList recordList;
        recordList = dollaData.getBillRecordList();
        int people = recordList.get().get(billNum - 1).getPeople();
        double amount = recordList.get().get(billNum - 1).getBillAmount();
        ArrayList<String> nameList = recordList.get().get(billNum - 1).getNameList();
        for (int i = 0; i < nameList.size(); i++) {
            if (nameList.get(i).equals(name)) {
                nameList.remove(i);
                check += 1;
            }
        }
        if (nameList.size() == 0) {
            dollaData.removeFromRecordList("bill", billNum - 1);
            DebtUi.printFinishMessage();
        } else if (check == 0) {
            DebtUi.printNameNotFound();
        } else {
            DebtUi.printRemoveNameMessage(name, nameList);
            recordList.removeFromList(billNum - 1);
            Bill newBill = new Bill("bill", people, amount, nameList);
            recordList.addWithIndex(billNum - 1, newBill);
        }
    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}
