package dolla.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author tatayu
public class RemoveBillCommandTest {

    @Test
    public void removeBillCommandTest1() {
        Command commandTest = new RemoveBillCommand(2);
        String expected = "bill 2";
        assertEquals(expected, commandTest.getCommandInfo());
    }

    @Test
    public void removeBillCommandTest2() {
        Command commandTest = new RemoveBillCommand(100);
        String expected = "bill 100";
        assertEquals(expected, commandTest.getCommandInfo());
    }

    @Test
    public void removeBillCommandTest3() {
        Command commandTest = new RemoveBillCommand(3);
        String expected = "bill 3";
        assertEquals(expected, commandTest.getCommandInfo());
    }
}
