package dolla.parser;

import dolla.Time;
import dolla.command.AddDebtsCommand;
import dolla.command.AddEntryCommand;
import dolla.command.AddLimitCommand;
import dolla.command.Command;
import dolla.command.ErrorCommand;
import dolla.command.view.ViewDateCommand;
import dolla.command.view.ViewTodayCommand;

import dolla.ui.DebtUi;
import dolla.ui.Ui;
import dolla.ui.LimitUi;
import dolla.ui.ViewUi;

import java.time.DateTimeException;
import java.time.LocalDate;

//@@author omupenguin
public class DollaParser extends Parser {

    protected static final String VIEW_TODAY = "today";

    public DollaParser(String inputLine) {
        super(inputLine);
        this.mode = MODE_DOLLA;
    }

    @Override
    public Command parseInput() {

        if (commandToRun.equals(DOLLA_VIEW)) {
            if (verifyViewTodayCommand()) {
                return new ViewTodayCommand();
            } else if (verifyViewDateCommand()) {
                return new ViewDateCommand(date);
            } else {
                return new ErrorCommand();
            }

        } else if (commandToRun.equals(ENTRY_COMMAND_ADD)) {
            if (verifyAddCommand()) {
                return new AddEntryCommand(type, amount, description, date);
            } else {
                return new ErrorCommand();
            }

        } else if (commandToRun.equals(DEBT_COMMAND_OWE) || commandToRun.equals(DEBT_COMMAND_BORROW)) {
            if (verifyDebtCommand()) {
                return new AddDebtsCommand(commandToRun, inputArray[1], amount, description, date);
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(ParserStringList.LIMIT_COMMAND_SET)) {
            if (verifySetCommand()) {
                return new AddLimitCommand(type, amount, duration);
            } else {
                LimitUi.invalidSetCommandPrinter();
                return new ErrorCommand();
            }
        }
        return invalidCommand();
    }

    private boolean verifyViewTodayCommand() {
        try {
            return inputArray[1].equals(VIEW_TODAY);
        } catch (IndexOutOfBoundsException e) {
            ViewUi.printInvalidViewFormatError();
            return false;
        }
    }

    private boolean verifyViewDateCommand() {
        try {
            date = Time.readDate(inputArray[1]);
        } catch (IndexOutOfBoundsException e) {
            //ViewUi.printInvalidViewFormatError();
            return false;
        } catch (DateTimeException e) {
            Ui.printDateFormatError();
            ViewUi.printInvalidViewFormatError();
            return false;
        }
        return true;
    }
}