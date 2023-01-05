import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Decompress {

    StringBuilder out = new StringBuilder();

    @Test
    public void test() throws Exception {
        String input = "3abc7d1ef12g";

        StringBuilder number = new StringBuilder();
        StringBuilder text = new StringBuilder();
        for (char c : input.toCharArray()) {
            boolean isNumber = c >= '0' && c <= '9';
            if (isNumber) {
                if (text.length() > 0) {
                    flush(number, text);
                    number = new StringBuilder();
                    text = new StringBuilder();
                }
                number.append(c);

            } else {
                text.append(c);
            }
        }
        flush(number, text);

        System.out.println(out);
        assertEquals("abcabcabcdddddddefgggggggggggg", out.toString());
        System.out.println("Done");
    }

    private void flush(StringBuilder number, StringBuilder text) {
        int n = Integer.parseInt(number.toString());
        for (int i = 0; i < n; i++) {
            out.append(text);
        }
    }

}
