package sorts;

import org.junit.Test;

public class BubbleSortTest {

    @Test
    public void casSortList() {
        int[] input = {5, 4, 3, 2, 1};

        boolean finished = false;
        while (finished != true) {
            printArray(input);

            boolean swapped = false;
            for (int i = 0; i < input.length - 1; i++) {
                int current = input[i];
                int next = input[i + 1];
                if (next < current) {
                    System.out.println("!!!! " + current + " / " + next);
                    input[i + 1] = current;
                    input[i] = next;
                    swapped = true;
                }
            }
            finished = !swapped;
        }

        printArray(input);
    }

    public void printArray(int[] input) {
        System.out.println("--------");
        for (int j = 0; j < input.length; j++) {
            System.out.println(input[j]);
        }
        System.out.println("--------");
    }

}
