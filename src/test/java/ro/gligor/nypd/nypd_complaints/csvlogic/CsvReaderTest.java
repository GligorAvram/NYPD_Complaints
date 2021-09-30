package ro.gligor.nypd.nypd_complaints.csvlogic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CsvReaderTest {

    CsvReader reader = CsvReader.getInstance("src\\test\\java\\test.csv");

    @Test
    void csvParseTest() {
        //check if the reader can parse CSV files
        assertEquals(14, reader.getEventsNumber());

        assertTrue(reader.getOffenses().containsKey(104));
        assertTrue(reader.getOffenses().containsKey(105));
        assertTrue(reader.getOffenses().containsKey(109));
        assertTrue(reader.getOffenses().containsKey(112));
        assertTrue(reader.getOffenses().containsKey(233));
        assertTrue(reader.getOffenses().containsKey(341));
        assertTrue(reader.getOffenses().containsKey(344));
        assertTrue(reader.getOffenses().containsKey(351));
        assertTrue(reader.getOffenses().containsKey(359));
        assertTrue(reader.getOffenses().containsKey(578));
    }

    @Test
    void addRemoveTest(){
        //the file should start with 14 rows
        assertEquals(14, reader.getEventsNumber());

        //addition test
        assertFalse(reader.getOffenses().containsKey(312));
        reader.addRow("12345", "312");
        assertTrue(reader.getOffenses().containsKey(312));
        assertEquals(1, reader.getOffenses().get(312));
        assertEquals(15, reader.getEventsNumber());

        //removal test
        assertTrue(reader.deleteOffense(12345L));
        assertFalse(reader.getOffenses().containsKey(312));
        assertEquals(14, reader.getEventsNumber());
    }

    @Test
    void badAdditionCodes(){
        reader.addRow("Not a number", "1");
        assertEquals(14, reader.getEventsNumber());

        reader.addRow("12345", "not a number");
        assertEquals(14, reader.getEventsNumber());

        reader.addRow("not a number", "not a number");
        assertEquals(14, reader.getEventsNumber());
    }
}