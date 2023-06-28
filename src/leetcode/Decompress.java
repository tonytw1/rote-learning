package leetcode;

import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Decompress {

    @Test
    public void test() throws Exception {
        assertEquals("abc", decode("abc"));
        assertEquals("abcdd", decode("abc2[d]"));
        assertEquals("aaabcbc", decode("3[a]2[bc]"));
        assertEquals("accaccacc", decode("3[a2[c]]"));
        assertEquals("abcabccdcdcdef", decode("2[abc]3[cd]ef"));
    }

    private String decode(String input) {
        // This is a nested problem; use stacks to keep track of this.
        Stack<Integer> ns = new Stack<Integer>();
        Stack<StringBuilder> outs = new Stack<StringBuilder>();

        // Setup the top level writer
        StringBuilder out = new StringBuilder();

        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            // If a digit then we must have started a new group; read the entire number and open a new writer; push both onto the stacks
            // [ must be immediately after a number; it is not interesting but needs to be skipped over
            // If ] then we have completed a group; we should render it n many times to the previous writer
            if (isNumber(c)) {
                // Consume the entire multi digit number and start a new group
                StringBuilder num = new StringBuilder();
                while (isNumber(c)) {
                    num.append(c);
                    i++;    //  Also handles skipping of the ]
                    c = chars[i];
                }
                // Open a new level
                ns.push(Integer.parseInt(num.toString()));  // Push know mnay copies we will need onto the stack
                outs.push(out); // Push the parent that this group will render into onto the stack
                out = new StringBuilder();

            } else if (c == ']') {
                // End this group and render it to the outer writer n times
                String group = out.toString();
                out = outs.pop();
                int n = ns.pop();
                out.append(group.repeat(n));

            } else {
                out.append(c);
            }
        }

        return out.toString();
    }

    private boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

}
