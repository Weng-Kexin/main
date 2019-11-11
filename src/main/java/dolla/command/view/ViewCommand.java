package dolla.command.view;

import dolla.Time;
import dolla.command.Command;
import dolla.model.DollaData;
import dolla.model.Record;
import dolla.model.RecordList;
import dolla.ui.ListUi;
import dolla.ui.ViewUi;

import java.time.LocalDate;

public class ViewCommand extends Command {

    protected LocalDate cmpDate;
    protected String dateStr;
    protected static final String TODAY = "today";
    protected static final String EXPENSE = "expense";
    private double sum = 0;


    @Override
    public void execute(DollaData dollaData) {

        RecordList entryList = dollaData.getRecordListObj(MODE_ENTRY);
        boolean hasRelevantEntries = false;

        if (entryList.isEmpty()) {
            ListUi.printEmptyListError(MODE_ENTRY);
            return;
        }

        ViewUi.printStartLine();
        for (int i = 0; i < entryList.size(); i += 1) {
            Record currEntry = entryList.getFromList(i);
            LocalDate currDate = currEntry.getDate();

            if (!isSameDate(currDate, cmpDate)) {
                continue;
            }

            hasRelevantEntries = true;

            double currAmount = getSignedAmount(currEntry);
            String currDesc = currEntry.getDescription();

            ViewUi.printViewSingleExpense(currAmount, currDesc);
            sum += currAmount;

        }

        if (hasRelevantEntries) {
            ViewUi.printOverallExpense(sum, dateStr);
        } else {
            ViewUi.printNoRelevantExpense(dateStr);
        }

    }

    @Override
    public String getCommandInfo() {
        return Time.dateToString(cmpDate) + " " + dateStr;
    }

    private boolean isSameDate(LocalDate d1, LocalDate d2) {
        return d1.compareTo(d2) == 0;
    }

    private double getSignedAmount(Record currEntry) {
        String type = currEntry.getType();
        double amount = currEntry.getAmount();
        if (type.equals(EXPENSE)) {
            amount *= -1;
        }
        return amount;
    }

    public double getSum() {
        return sum;
    }

}