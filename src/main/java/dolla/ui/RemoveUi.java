package dolla.ui;

public class RemoveUi extends Ui {
    //@@author yetong1895
    /**
     * The method will print the message of the removal of a user defined record.
     * @param record the record to be removed
     * @param mode the mode dolla is on
     */
    public static void echoRemove(String record, String mode) {
        System.out.println(line);
        System.out.println("\tNoted. I've removed this " + mode + ": ");
        System.out.println("\t" + record);
        System.out.println(line);
    }

    /**
     * This method will print the error message if user have enter an invalid number to be removed.
     */
    public static void printInvalidRemoveMessage() {
        System.out.println(line);
        System.out.println("\tPlease enter a valid number to be removed.");
        System.out.println(line);
    }

    //@@author tatayu
    /**
     * Print the error message if the remove command is invalid.
     */
    public static void printInvalidRemoveFormatInDebtMode() {
        System.out.println(line);
        System.out.println("\tIf you want to remove a debt, please follow the format: remove [LIST NUM]");
        System.out.println(line);
    }
}
