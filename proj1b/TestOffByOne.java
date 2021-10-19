import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testOffByOne() {
        assertTrue(offByOne.equalChars('r', 'q'));
        assertFalse(offByOne.equalChars('r', 'Q'));
        assertTrue(offByOne.equalChars('&', '%'));
        assertFalse(offByOne.equalChars('z','z'));
        assertTrue(offByOne.equalChars('a', 'b'));
    }
}
