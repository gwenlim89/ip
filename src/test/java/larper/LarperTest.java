package larper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

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
}
