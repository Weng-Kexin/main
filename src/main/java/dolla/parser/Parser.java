package dolla.parser;

import dolla.ModeStringList;
import dolla.Time;
import dolla.ui.EntryUi;
import dolla.ui.LimitUi;
import dolla.ui.Ui;
import dolla.ui.ModifyUi;

import dolla.command.Command;
import dolla.command.ErrorCommand;
import dolla.ui.SortUi;
import dolla.ui.RemoveUi;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

//@@author omupenguin
/**
 * Parser is an abstract class that loads the appropriate command according to the user's input.
 * It also ensures that the user's input for the command is valid, such as by checking the format
 * of the input, and the utilisation of correct terms.
 */
public abstract class Parser implements ParserStringList, ModeStringList {

    protected String mode;
    protected LocalDate date;
    protected String description;
    protected String inputLine;
    protected String type;
    protected double amount;
    protected String[] inputArray;
    protected String commandToRun;
    protected static final String SPACE = " ";
    protected static int undoFlag = 0;
    protected static int redoFlag = 0;
    protected static int prevPosition;

    protected int modifyRecordNum;


    /**
     * Creates an instance of a parser.
     * @param inputLine The entire string containing the user's input.
     */
    public Parser(String inputLine) {
        this.inputLine = inputLine;
        this.inputArray = inputLine.split(SPACE);
        this.commandToRun = inputArray[0];
    }

    public abstract Command parseInput();

    /**
     * Splits the input from the user and assigns the relevant data into description and date variables.
     * If the incorrect format is given in the input, the corresponding alert will be printed.
     */
    public void extractDescTime() throws Exception {
        // dataArray[0] is command, amount and description, dataArray[1] is time and tag
        String[] dataArray = inputLine.split(" /on ");
        String dateString = (dataArray[1].split(" /tag "))[0];
        description = dataArray[0].split(inputArray[2] + " ")[1];
        try {
            date = Time.readDate(dateString);
        } catch (ArrayIndexOutOfBoundsException e) {
            // TODO: Shouldn't happen anymore, need to test if this will happen still
            Ui.printMsg("Please add '/at <date>' after your task to specify the entry date.");
            throw new Exception("missing date");
        }  catch (DateTimeParseException e) {
            Ui.printDateFormatError();
            throw new Exception("invalid date");
        }
    }

    /**
     * Returns a double variable from the specified string.
     * <p>
     *     Returns 0 if the specified string is not of a number.
     * </p>
     * <p>
     *     Mainly used for using the specified string for calculations in the command.
     *     IE. Accessing arrays.
     * </p>
     * @param str String (of number) to be converted into integer type.
     * @return Integer type of the specified string.
     */
    public static double stringToDouble(String str) {
        double newDouble = 0.0;
        try {
            newDouble = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            Ui.printInvalidNumberError(str);
        }
        return newDouble;
    }

    /**
     * Alerts the user that the input is invalid, and returns an ErrorCommand.
     * @return an ErrorCommand
     */
    public Command invalidCommand() {
        Ui.printInvalidCommandError();
        return new ErrorCommand();
    }

    /**
     * Checks if the first word after 'add' is either 'income' or 'expense'.
     * @param s String to be analysed.
     * @return Either 'expense' or 'income' if either are passed in.
     * @throws Exception ???
     */
    public static String verifyAddType(String s) throws Exception {
        if (s.equals("income") || s.equals("expense")) {
            return s;
        } else {
            EntryUi.printInvalidEntryType();
            throw new Exception("invalid type");
        }
    }

    /**
     * Returns true if no error occurs while creating the required variables for 'addEntryCommand'.
     * Also splits description and time components in the process.
     * @return true if no error occurs.
     */
    public boolean verifyAddCommand() {
        try {
            verifyAddType(inputArray[1]);
            stringToDouble(inputArray[2]);
            extractDescTime();
        } catch (IndexOutOfBoundsException e) {
            EntryUi.printInvalidEntryFormatError();
            return false;
        } catch (Exception e) {
            return false; // If error occurs, stop the method!
        }
        return true;
    }

    /**
     * Returns true if the only element in the input that follows 'modify' is a number.
     * @return true if the only element in the input that follows 'modify' is a number.
     */
    public boolean verifyFullModifyCommand() {
        if (inputArray.length != 2) {
            return false;
        }
        try {
            Integer.parseInt(inputArray[1]);
        } catch (Exception e) {
            ModifyUi.printInvalidFullModifyFormatError();
            return false;
        }
        return true;
    }

    //@@author yetong1895
    /**
     * This method will check if the input contain an type to sort.
     * @return true is inputArray[1] contain something, false if inputArray[1] is invalid.
     */
    protected boolean verifySort() {
        if (inputArray.length < 2) {
            SortUi.printInvalidSort(mode);
            return false;
        } else {
            switch (mode) {
            case MODE_ENTRY:
                if (inputArray[1].equals(SORT_TYPE_AMOUNT)
                    || inputArray[1].equals(SORT_TYPE_DATE)
                    || inputArray[1].equals(SORT_TYPE_DESC)) {
                    return true;
                } else {
                    SortUi.printInvalidSort(mode);
                    return false;
                }
            case MODE_DEBT:
                if (inputArray[1].equals(SORT_TYPE_AMOUNT)
                    || inputArray[1].equals(SORT_TYPE_DATE)
                    || inputArray[1].equals(SORT_TYPE_DESC)
                    || inputArray[1].equals(SORT_TYPE_NAME)) {
                    return true;
                } else {
                    SortUi.printInvalidSort(mode);
                    return false;
                }
            case MODE_LIMIT:
                if (inputArray[1].equals(SORT_TYPE_DATE)) {
                    return true;
                } else {
                    SortUi.printInvalidSort(mode);
                    return false;
                }
            default:
                SortUi.printInvalidSort(mode);
                return false;
            }
        }
    }

    /**
     * The method will check if the user have entered a valid number to be removed.
     * @return true if there is a valid number or false otherwise.
     */
    protected boolean verifyRemove() {
        if (inputArray.length < 2) {
            RemoveUi.printInvalidRemoveMessage();
            return false;
        }
        try {
            Integer.parseInt(inputArray[1]);
        } catch (NumberFormatException e) {
            RemoveUi.printInvalidRemoveMessage();
            return false;
        }
        return true;
    }

    /**
     * This method will set the prevPosition int in this class.
     * @param prevPosition the prevPosition of a deleted input.
     */
    public static void setPrevPosition(int prevPosition) {
        Parser.prevPosition = prevPosition;
        undoFlag = 1;
    }

    /**
     * THis method will set prevPosition to -1 and undoFlag to 0.
     */
    public static void resetUndoFlag() {
        Parser.prevPosition = -1;
        undoFlag = 0;
    }

    /**
     * This method will set redoFlag to 1.
     */
    public static void setRedoFlag() {
        redoFlag = 1;
    }

    /**
     * This method will set redoFlag to 0.
     */
    public static void resetRedoFlag() {
        redoFlag = 0;
    }

    //@@author omupenguin
    /**
     * Returns true if the input has no formatting issues.
     * Also designates the correct information to the relevant variables.
     * @return true if the input has no formatting issues.
     */
    public boolean verifyPartialModifyCommand() {

        boolean hasComponents = false;
        //ArrayList<String> errorList = new ArrayList<String>();
        type = null;
        amount = -1;
        description = null;
        date = null;

        try {
            modifyRecordNum = Integer.parseInt(inputArray[1]);
        } catch (Exception e) {
            ModifyUi.printInvalidFullModifyFormatError();
            return false;
        }

        switch (mode) {
        case MODE_ENTRY:
            hasComponents = findEntryComponents();
            break;
        case MODE_LIMIT:
            // TODO
            Ui.printUpcomingFeature();
            return false;
            //break;
        case MODE_DEBT:
            // TODO
            Ui.printUpcomingFeature();
            return false;
            //break;
        case MODE_SHORTCUT:
            // TODO
            break;
        default:
            break;
        }


        if (!hasComponents) {
            ModifyUi.printInvalidPartialModifyFormatError();
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns true if the input contains a component to be edited in the current mode,
     * demarcated with strings like "/type".
     * Also designates the correct information to the relevant variables.
     * @return true if the input contains a component to be edited in the current mode.
     */
    private boolean findEntryComponents() {
        boolean hasComponents = false;
        for (int i = 0; i < inputArray.length; i += 1) {
            String currStr = inputArray[i];

            if (isComponent(currStr)) {
                String nextStr = inputArray[i + 1];
                try {
                    switch (currStr) {
                    case COMPONENT_TYPE:
                        type = verifyAddType(nextStr);
                        break;
                    case COMPONENT_AMOUNT:
                        amount = stringToDouble(nextStr);
                        break;
                    case COMPONENT_DESC:
                        description = parseDesc(i + 1);
                        break;
                    case COMPONENT_DATE:
                        date = Time.readDate(nextStr);
                        break;
                    case COMPONENT_TAG:
                        //TODO
                        break;
                    default:
                        break;
                    }
                } catch (Exception e) {
                    ModifyUi.printInvalidPartialModifyFormatError();
                    return false;
                }
                hasComponents = true;
            }
        }
        return hasComponents;
    }

    /**
     * Returns true if the specified string is a editable component for the current mode.
     * @param s the string to be checked.
     * @return true if the specified string is a editable component for the current mode.
     */
    private boolean isComponent(String s) {
        switch (mode) {
        case MODE_ENTRY:
            switch (s) {
            case COMPONENT_TYPE:
            case COMPONENT_AMOUNT:
            case COMPONENT_DESC:
            case COMPONENT_DATE:
            case COMPONENT_TAG:
                return true;
            default:
                break;
            }
            break;
        /*
        case MODE_LIMIT:
            switch (s) {
                // TODO
            }
            break;
        case MODE_DEBT:
            switch (s) {
                // TODO
            }
            break;
        case MODE_SHORTCUT:
            switch (s) {
                // TODO
            }
            break;
        */
        default:
            break;
        }
        return false;
    }

    /**
     * Extracts and returns a string containing the new description of the record to be modified.
     * @param index The index of element in inputArray to start extracting from.
     * @return string containing the new description of the record to be modified.
     */
    private String parseDesc(int index) {
        String tempStr = "";
        for (int i = index; i < inputArray.length; i += 1) {
            if (isComponent(inputArray[i])) {
                break;
            }
            tempStr = tempStr.concat(inputArray[i] + " ");
        }
        tempStr = tempStr.substring(0, tempStr.length() - 1);
        return tempStr;
    }

    //@@author Weng-Kexin
    protected double findLimitAmount() {
        double amount = 0;
        try {
            amount = stringToDouble(inputArray[2]);
        } catch (NumberFormatException e) {
            LimitUi.invalidAmountPrinter();
        }
        return amount;
    }

    private Boolean verifyLimitType(String limitType) {
        return limitType.equals(LIMIT_TYPE_S)
                || limitType.equals(LIMIT_TYPE_B);
    }

    private Boolean verifyLimitDuration(String limitDuration) {
        return limitDuration.equals(LIMIT_DURATION_D)
                || limitDuration.equals(LIMIT_DURATION_W)
                || limitDuration.equals(LIMIT_DURATION_M);
    }

    private Boolean verifyLimitAmount(double limitAmount) {
        return (limitAmount != 0);
    }

    protected Boolean verifySetLimitCommand() {
        boolean isValid;
        try {
            String typeStr = inputArray[1];
            double amountInt = findLimitAmount();
            String durationStr = inputArray[3];
            isValid = verifyLimitType(typeStr) && verifyLimitAmount(amountInt) && verifyLimitDuration(durationStr);
        } catch (IndexOutOfBoundsException e) {
            LimitUi.invalidSetCommandPrinter();
            isValid = false;
        } catch (Exception e) {
            isValid = false;
        }
        return isValid;
    }
}
