package dolla.parser;


import dolla.command.AddShortcutCommand;
import dolla.command.Command;
import dolla.command.ErrorCommand;
import dolla.command.ShowListCommand;
import dolla.command.ExecuteShortcutCommand;
import dolla.command.RemoveCommand;

public class ShortcutParser extends Parser {

    /**
     * Creates an instance of a parser.
     *
     * @param inputLine The entire string containing the user's input.
     */
    public ShortcutParser(String inputLine) {
        super(inputLine);
        this.mode = MODE_SHORTCUT;
    }

    @Override
    public Command parseInput() {
        switch (commandToRun) {
        case SHORTCUT_COMMAND_LIST:
            return new ShowListCommand(mode);
        case ENTRY_COMMAND_LIST:
            return new ShowListCommand(MODE_ENTRY);
        case COMMAND_REMOVE:
            if (verifyRemove()) {
                return new RemoveCommand(mode, inputArray[1]);
            } else {
                return new ErrorCommand();
            }
        case SHORTCUT_COMMAND_CREATE:
            if (verifyShortcut()) {
                return new AddShortcutCommand(inputArray[1]);
            } else {
                return new ErrorCommand();
            }
        case SHORTCUT_COMMAND_EXECUTE:
            if (verifyShortcut()) {
                return new ExecuteShortcutCommand(inputArray[1]);
            } else {
                return new ErrorCommand();
            }
        default:
            return invalidCommand();
        }
    }
}
