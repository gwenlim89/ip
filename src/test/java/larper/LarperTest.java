package larper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class LarperTest {
    private static final String TODAY_PROPERTY = "larper.today";

    @TempDir
    private Path tempDir;

    @BeforeEach
    public void setUp() {
        System.setProperty(TODAY_PROPERTY, "2026-08-23");
    }

    @AfterEach
    public void tearDown() {
        System.clearProperty(TODAY_PROPERTY);
    }

    @Test
    public void getWelcomeMessage_newLarper_containsGreetingAndPrompt() {
        Larper larper = new Larper(tempDir.resolve("larperdata.txt"));

        assertEquals(" _\n"
                + "| |       __ _   _ __   _ __     ___   _ __\n"
                + "| |      / _` | | '__| | '_ \\   / _ \\ | '__|\n"
                + "| |___  | (_| | | |    | |_) | |  __/ | |\n"
                + "|_____|  \\__,_| |_|    | .__/   \\___| |_|\n"
                + "                       |_|\n"
                + "Larper has entered the chat.\n\n"
                + " Drop a command. Let's lock in.", larper.getWelcomeMessage());
    }

    @Test
    public void getResponse_addListExit_returnsExpectedMessages() {
        Larper larper = new Larper(tempDir.resolve("larperdata.txt"));

        LarperResponse addResponse = larper.getResponse("todo read book");
        LarperResponse listResponse = larper.getResponse("list");
        LarperResponse exitResponse = larper.getResponse("exit");

        assertFalse(addResponse.isExit());
        assertEquals(" Say less. I've added this quest:\n"
                + " [T][ ] read book\n"
                + " Quest log now has 1 task.", addResponse.getMessage());
        assertFalse(listResponse.isExit());
        assertEquals(" Quest log check:\n"
                + " 1. [T][ ] read book", listResponse.getMessage());
        assertTrue(exitResponse.isExit());
        assertEquals(" Aight, Larper is logging off. Come back with more quests soon.",
                exitResponse.getMessage());
    }

    @Test
    public void getTaskSummaries_afterTaskChanges_returnsCurrentTasks() {
        Larper larper = new Larper(tempDir.resolve("larperdata.txt"));

        larper.getResponse("todo read book");
        larper.getResponse("deadline submit report /by 2026-08-23 no time");
        larper.getResponse("mark 2");
        larper.getResponse("delete 1");

        assertEquals(List.of("[D][X] submit report (by: Aug 23 2026)"), larper.getTaskSummaries());
    }

    @Test
    public void getResponse_invalidCommand_returnsErrorResponse() {
        Larper larper = new Larper(tempDir.resolve("larperdata.txt"));

        LarperResponse response = larper.getResponse("hello");

        assertFalse(response.isExit());
        assertTrue(response.isError());
    }

    @Test
    public void getResponse_help_returnsCommandGuide() {
        Larper larper = new Larper(tempDir.resolve("larperdata.txt"));

        LarperResponse response = larper.getResponse("help");

        assertFalse(response.isExit());
        assertFalse(response.isError());
        assertTrue(response.getMessage().contains("todo DESCRIPTION"));
        assertTrue(response.getMessage().contains("delete NUMBER"));
    }

    @Test
    public void getResponse_misspelledCommand_returnsSuggestionError() {
        Larper larper = new Larper(tempDir.resolve("larperdata.txt"));

        LarperResponse response = larper.getResponse("delet 2");

        assertFalse(response.isExit());
        assertTrue(response.isError());
        assertEquals(" Unknown command: `delet`\n"
                + " Did you mean `delete 2`?\n\n"
                + " Type `help` for commands.", response.getMessage());
    }

    @Test
    public void getResponse_invalidTaskNumber_returnsErrorResponse() {
        Larper larper = new Larper(tempDir.resolve("larperdata.txt"));

        larper.getResponse("todo read book");
        LarperResponse response = larper.getResponse("mark 999");

        assertFalse(response.isExit());
        assertTrue(response.isError());
        assertEquals(" That task number is not in the quest log.", response.getMessage());
    }

    @Test
    public void getResponse_findFlow_returnsPromptAndMatches() {
        Larper larper = new Larper(tempDir.resolve("larperdata.txt"));

        larper.getResponse("todo read book");
        LarperResponse promptResponse = larper.getResponse("find");
        LarperResponse findResponse = larper.getResponse("READ BOOK");

        assertFalse(promptResponse.isExit());
        assertEquals(" What phrase are we hunting for?", promptResponse.getMessage());
        assertFalse(findResponse.isExit());
        assertEquals(" Found it. Receipts below:\n"
                + " [T][ ] read book (task no: 1)", findResponse.getMessage());
    }

    @Test
    public void getResponse_tagUntagAndFindTag_updatesAndSearchesTasks() {
        Larper larper = new Larper(tempDir.resolve("larperdata.txt"));

        larper.getResponse("todo prepare slides");
        LarperResponse tagResponse = larper.getResponse("tag 1 #SCHOOL urgent");
        LarperResponse findResponse = larper.getResponse("find tag school");
        larper.getResponse("mark 1");
        LarperResponse untagResponse = larper.getResponse("untag 1 #school");

        assertEquals(" Tagged task 1:\n"
                + " [T][ ] prepare slides [#school] [#urgent]", tagResponse.getMessage());
        assertEquals(" Found it. Receipts below:\n"
                + " [T][ ] prepare slides [#school] [#urgent] (task no: 1)", findResponse.getMessage());
        assertEquals(" Untagged task 1:\n"
                + " [T][X] prepare slides [#urgent]", untagResponse.getMessage());
    }
}
