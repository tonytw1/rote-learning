package leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectorOfPhotography2 {

    @Test
    public void test() {
        assertEquals(3, getValid(".PBAAP.B", 1, 3));
        assertEquals(0, getValid("", 1, 3));
        assertEquals(0, getValid("BA...P", 2, 4));
        assertEquals(0, getValid("PA...B", 2, 4));
        assertEquals(1, getValid("PAB", 1, 2));
        assertEquals(2, getValid("P.PAAB", 1, 2));
        assertEquals(0, getValid("P.PAAB", 2, 2));
        assertEquals(1, getValid("PPAB", 1, 1));
        assertEquals(1, getValid("..BBAP.", 1, 1));
        assertEquals(1, getValid(".....P.A.B......", 1, 2));
        assertEquals(1, getValid(".....B.A.P......", 1, 2));
        assertEquals(2, getValid(".....B.A.P......BAP...", 1, 2));
        assertEquals(1, getValid(".....B.A.P......BAP...", 1, 1));
        assertEquals(1, getValid("P.A.B......", 1, 2));
        assertEquals(1, getValid("....P.A.B", 1, 2));
        assertEquals(0, getValid("....P.A.B", 1, 1));
        assertEquals(1, getValid("PAB", 1, 1));
        assertEquals(1, getValid("P...A...B", 4, 4));
        assertEquals(2, getValid("PPAB", 1, 2));
        assertEquals(1, getValid("P.A.B", 2, 5));
        assertEquals(0, getValid("P", 1, 2));
        assertEquals(0, getValid("APABA", 2, 3));
        assertEquals(1, getValid("P..A..BB", 2, 3));
        assertEquals(0, getValid("APABA", 2, 3));
        assertEquals(1, getValid("APABA", 1, 2));
        assertEquals(2, getValid("PABPAB", 1, 2));
        assertEquals(3, getValid("....PAAAB....", 1, 3));
        assertEquals(0, getValid("P.A.B", 1, 1));
        assertEquals(1, getValid("P.A.B", 1, 2));
        assertEquals(1, getValid("APABA", 1, 2));
    }

    private long getValid(String C, int X, int Y) {
        long totalValidShots = 0L;
        // Intuition (which I had to look up ) is to look at the problem from the pov of the subject not the photographer!
        // The subject only needs to know how many photographers and backdrops it can see to know how many valid photos there of it.
        // Visiting each subject only once deals with duplicates.

        // Walk the length of the input as the subject
        // But we need to maintain counts of the in range photographers and backdrops; both in front and behind the subject
        long numberOfPhotographersBehind = 0;
        long numberOfBackdropsInFront = 0;
        long numberOfBackdropsBehind = 0;
        long numberOfPhotographersInFront = 0;

        // Set up 2 out riders which follow the valid range windows
        int range = (Y - X) + 1;    // Inclusive!

        int start = -(X + range) * 2;    // This intuition ensures that we read ahead far enough to prefill the in front counts; prevents in front counts going negative!
        int r = start + X;
        int l = start - (X + range) + 1;

        for (int i = start; i < C.length(); i++) {
            // Allow the out riders to move; counting items which move into range.
            while (r < (i + X + range) - 1) {
                r++;
                if (r >= 0 && r < C.length()) {
                    char cr = C.charAt(r);
                    numberOfPhotographersInFront = cr == 'P' ? numberOfPhotographersInFront + 1 : numberOfPhotographersInFront;
                    numberOfBackdropsInFront = cr == 'B' ? numberOfBackdropsInFront + 1 : numberOfBackdropsInFront;
                }
            }

            // Accounting for object are sliding out of range before incrementing
            int outingGoingR = i + (X - 1);
            if (outingGoingR >= 0 && outingGoingR < C.length()) {
                char cr = C.charAt(outingGoingR);
                numberOfPhotographersInFront = cr == 'P' ? numberOfPhotographersInFront - 1 : numberOfPhotographersInFront;
                numberOfBackdropsInFront = cr == 'B' ? numberOfBackdropsInFront - 1 : numberOfBackdropsInFront;
            }

            while (l < (i - X)) {
                l++;
                if (l >= 0 && l < C.length()) {
                    char cr = C.charAt(l);
                    numberOfPhotographersBehind = cr == 'P' ? numberOfPhotographersBehind + 1 : numberOfPhotographersBehind;
                    numberOfBackdropsBehind = cr == 'B' ? numberOfBackdropsBehind + 1 : numberOfBackdropsBehind;
                }
            }

            // Accounting for object are sliding out of range before incrementing
            int outingGoingL = i - (X + range);
            if (outingGoingL >= 0 && outingGoingL < C.length()) {
                char cr = C.charAt(outingGoingL);
                numberOfPhotographersBehind = cr == 'P' ? numberOfPhotographersBehind - 1 : numberOfPhotographersBehind;
                numberOfBackdropsBehind = cr == 'B' ? numberOfBackdropsBehind - 1 : numberOfBackdropsBehind;
            }

            if (i >= 0) {   // Account for prereading the prefill stage
                // If we are at a subject then evaluate the artfulness of this subject
                final char c = C.charAt(i);
                if (c == 'A') {
                    // Count the number of photographers and backdrops we can see
                    long validShots = (numberOfPhotographersBehind * numberOfBackdropsInFront) + (numberOfBackdropsBehind * numberOfPhotographersInFront);
                    totalValidShots += validShots;
                }
            }

        }
        return totalValidShots;
    }

}
