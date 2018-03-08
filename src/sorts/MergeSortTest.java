package sorts;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class MergeSortTest {

    int[] unsorted = {13, 8, 5, 2, 4, 0, 6, 9, 7, 3, 12, 1, 10, 11};

    public int[] merge(int[] left, int[] right) {
        System.out.println("Merging: " + Arrays.toString(left) + " / " + Arrays.toString(right));

        int l = left.length + right.length;

        int[] result = new int[l];

        int lp = 0;
        int rp = 0;

        for (int i = 0; i < l; i++) {
            if (lp == left.length) {
                //Have to take from the right as the left is used up
                result[i] = right[rp++];
            } else if (rp == right.length) {
                // Have to take from the left
                result[i] = left[lp++];
            } else if (left[lp] < right[rp]) {
                result[i] = left[lp++];
            } else {
                result[i] = right[rp++];
            }
        }

        System.out.println("Merged to: " + Arrays.toString(result));
        return result;
    }

    public int[] mergeSort(int[] input) {
        System.out.println(Arrays.toString(input));

        if (input.length <= 1) {
            System.out.println("Returning sorted");
            return input;
        }

        int split = input.length / 2;
        System.out.println("Spliting input of length " + input.length + " at " + split);
        int[] left = Arrays.copyOfRange(input, 0, split);
        int[] right = Arrays.copyOfRange(input, split, input.length);

        int[] sortedLeft = mergeSort(left);
        int[] sortedRight = mergeSort(right);

        return merge(sortedLeft, sortedRight);
    }

    @Test
    public void canSort() {
        int[] result = mergeSort(unsorted);

        System.out.println(Arrays.toString(result));

        assertTrue(Arrays.equals(result, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13}));
    }

    @Test
    public void anArrayOfLengthOneCanBeConsideredToBeSorted() {
        int[] small = {7};

        int[] result = mergeSort(small);

        assertTrue(Arrays.equals(result, new int[]{7}));
    }

}
