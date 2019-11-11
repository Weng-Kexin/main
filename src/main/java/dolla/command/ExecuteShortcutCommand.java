package dolla.command;

import dolla.Time;
import dolla.model.DollaData;
import dolla.model.Record;
import dolla.exception.DollaException;
import dolla.ui.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

//@@author yetong1895

/**
 * Execute a shortcut command.
 */
public class ExecuteShortcutCommand extends Command {

    private int index;
    private String mode;

    public ExecuteShortcutCommand(String index, String mode) {
        this.index = Integer.parseInt(index);
        this.mode = mode;
    }

    @Override
    public void execute(DollaData dollaData) throws DollaException {
        ArrayList<Record> entryList = dollaData.getRecordList(MODE_ENTRY);
        ArrayList<Record> shortcutList = dollaData.getRecordList(MODE_SHORTCUT);
        try {
            Record shortcut = entryList.get(index);
            Ui.printDateRequest();
            Scanner input = new Scanner(System.in);
            while (true) {
                try {
                    String inputDate = input.nextLine();
                    LocalDate newDate = Time.readDate(inputDate);
                    Command c = new AddEntryCommand(shortcut.getType(), shortcut.getAmount(),
                                                    shortcut.getDescription(), newDate);
                    c.execute(dollaData);
                    break;
                } catch (DateTimeParseException e) {
                    Ui.printDateFormatError();
                }
            }
        } catch (IndexOutOfBoundsException e) {
            Ui.printNumberOfRecords(shortcutList.size());
        }
    }

    @Override
    public String getCommandInfo() {
        String command = "execute shortcut ";
        return command + index + " in mode " + mode;
    }
}
