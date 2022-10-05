import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.Random;

public class Shift {

    /**
     * Reference Table: _ref.charAt(i) returns the desired character
     */
    private final static String _ref = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789()*+,-./";
    private final static int _refSize = 44;

    /**
     * Given a string, random generate an offset to shift the characters, and encode the string
     * with the new shifted characters.
     * @param plainText Original string to be encoded
     * @return Encoded string
     */
    public String encode(String plainText) {

        // use millisecond of locale to generate a random offset
        Random rng = new Random();
        rng.setSeed(ZonedDateTime.now().getLong(ChronoField.MILLI_OF_DAY));
        int offset = rng.nextInt(_refSize);

        // append the shifted string to the offset character
        char offsetChar = _ref.charAt(offset);
        return offsetChar + shiftStr(plainText, offset);
    }

    /**
     * Given an encoded string, we decode it by identifying the offset from the first char and
     * decode it.
     * @param encodedText
     * @return
     * @throws InvalidOffsetException
     */
    public String decode(String encodedText) throws InvalidOffsetException {
        int offset = indexOf(encodedText.charAt(0));

        if (-1 == offset) {
            throw new InvalidOffsetException("offset character is not found in reference table");
        }

        return shiftStr(encodedText.substring(1), -offset);
    };

    /**
     * Shifts a single character to the new character by offset. If the character is not in the ref table
     * return it as is.
     * @param c Character to shift
     * @param offset Offset
     * @return Shifted character
     */
    private static char shiftChar(char c, int offset) {
        int idx = indexOf(c);

        // if the char is not in the ref table, return it as is
        if (-1 == idx) {
            return c;
        }

        // return the shifted char
        // to shift the characters by offset down
        // we can view the shifting as shifting table back by offset
        // however to account for negative indexes, we shift the table forwards
        // instead by the full table size of 44 minus the offset to achieve the same effect
        return _ref.charAt((idx + _refSize - offset) % _refSize);
    }

    /**
     * Returns the index of the char in the reference table
     * @param c Character to index in ref table
     * @return Index in ref table
     */
    private static int indexOf(char c) {
        return _ref.indexOf(c);
    }

    /**
     * We shift the characters in the string by getting the character index and adding the offset and taking the mod 44
     * of the result.
     * @param s
     * @param offset
     * @return
     */
    private static String shiftStr(String s, int offset) {
        StringBuilder sb = new StringBuilder();

        // shift each of the char
        for (int i = 0; i < s.length(); i++) {
            sb.append(shiftChar(s.charAt(i), offset));
        }
        return sb.toString();
    }
}
