package dolla.command.modify;

import dolla.DollaDataStubs.DollaDataStub2;
import dolla.ModeStringList;
import dolla.model.DollaData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RevertFromModifyCommandTest implements ModeStringList {

    @Test
    public void execute_EntryMode_Entry() {
        DollaData dollaData = new DollaDataStub2();

        int indexToModify = 1;

        InitialModifyCommand firstCommand = new InitialModifyCommand(indexToModify);
        firstCommand.execute(dollaData);

        RevertFromModifyCommand secondCommand = new RevertFromModifyCommand();
        secondCommand.execute(dollaData);

        String expectedPrevMode = MODE_ENTRY;
        assertEquals(expectedPrevMode, dollaData.getPrevMode());
    }

}
