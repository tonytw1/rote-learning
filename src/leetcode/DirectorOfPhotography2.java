package leetcode;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectorOfPhotography2 {

    @Test
    public void test() {
        assertEquals(1, getValid("PAB", 1, 2));
        assertEquals(1, getValid("P..A..BB", 2, 3));
        assertEquals(0, getValid("P", 1, 2));
        assertEquals(2, getValid("PABPAB", 1, 2));
        assertEquals(3, getValid("....PAAAB....", 1, 3));
        assertEquals(0, getValid("P.A.B", 1, 1));
        assertEquals(1, getValid("P.A.B", 1, 2));
        assertEquals(1, getValid("APABA", 1, 2));
        assertEquals(0, getValid("APABA", 2, 3));
        assertEquals(3, getValid(".PBAAP.B", 1, 3));
    }

    private long getValid(String C, int X, int Y) {
        ArrayDeque<Integer> photographerPositions = new ArrayDeque<>();
        ArrayDeque<Integer> actorPositions = new ArrayDeque<>();
        ArrayDeque<Integer> backdropPositions = new ArrayDeque<>();

        long valid = 0;

        // Walk the input looking for photographers or backdrops which would complete a shot
        for (int i = 0; i < C.length(); i++) {
            char c = C.charAt(i);
            if (c == 'P') {
                photographerPositions.add(i);

                // Can we find a valid artist shot behind us?
                final int left = i - Y;
                final int right = i - X;
                Set<Integer> inScopeActors = actorPositions.stream().filter(a -> a >= left && a <= right).collect(Collectors.toSet());

                for (int a : inScopeActors) {
                    final int actorLeft = a - Y;
                    final int actorRight = a - X;
                    Set<Integer> validEnds = backdropPositions.stream().filter(b -> b >= actorLeft && b <= actorRight).collect(Collectors.toSet());
                    if (!validEnds.isEmpty()) {
                        valid += validEnds.size();
                    }
                }

            } else if (c == 'A') {
                // record the location of this actor
                actorPositions.add(i);

            } else if (c == 'B') {
                backdropPositions.add(i);
                // record the location of this backdrop

                // Can we behind us?
                // actorPositions.stream().filter(a -> a >= left && a <= right).collect(Collectors.toSet());

                final int left = i - Y;
                final int right = i - X;
                Set<Integer> inScopeActors = actorPositions.stream().filter(a -> a >= left && a <= right).collect(Collectors.toSet());


                for (int a : inScopeActors) {
                    final int actorLeft = a - Y;
                    final int actorRight = a - X;
                    Set<Integer> validEnds = photographerPositions.stream().filter(b -> b >= actorLeft && b <= actorRight).collect(Collectors.toSet());
                    if (!validEnds.isEmpty()) {
                        valid += validEnds.size();
                    }

                }
            }

            // Try to keep the in scope remembered positions tight for fast scanning
            // Actors more than Y away are never going to be involved in a photo
            // Use the right of the queue to feed items coming into range into scope
            while (!actorPositions.isEmpty() && actorPositions.peekFirst() <= (i - Y)) {
                actorPositions.pollFirst();
            }

            // Backdrops and photographers more than 2 Y away are never going to be involved in a photo
            while (!photographerPositions.isEmpty() && photographerPositions.peekFirst() <=(i - (Y * 2))) {
                photographerPositions.pollFirst();
            }
            while (!backdropPositions.isEmpty() && backdropPositions.peekFirst() <= (i - (Y * 2))) {
                backdropPositions.pollFirst();
            }
        }

        return valid;
    }

}
