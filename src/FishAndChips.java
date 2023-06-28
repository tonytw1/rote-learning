import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class FishAndChips {

    @Test
    public void fishAndChips() {
        Map<String, Integer> menu = new HashMap<>();
        menu.put("Fish", 5);
        menu.put("Sausage", 2);
        menu.put("Chips", 1);

        Set<List<String>> zeroPounds = possibleChoices(menu, 0);
        assertTrue(zeroPounds.isEmpty());

        Set<List<String>> twoPounds = possibleChoices(menu, 2);
        assertTrue(twoPounds.contains(List.of("Sausage")));

        Set<List<String>> fivePounds = possibleChoices(menu, 5);
        List<String> justFish = List.of("Fish");
        List<String> fiveChips = List.of("Chips", "Chips", "Chips", "Chips", "Chips");
        List<String> sausageAndThreeChips = List.of("Sausage", "Chips", "Chips", "Chips");
        List<String> twoSausage = List.of("Sausage", "Sausage");
        List<String> twoSausageAndOneChips = List.of("Sausage", "Sausage", "Chips");
        assertTrue(fivePounds.contains(justFish));
        assertTrue(fivePounds.contains(fiveChips));
        assertTrue(fivePounds.contains(sausageAndThreeChips));
        assertTrue(fivePounds.contains(twoSausageAndOneChips));
        assertFalse(fivePounds.contains(twoSausage));   // Not a leaf node
        assertEquals(4, fivePounds.size());
    }

    private Set<List<String>> possibleChoices(Map<String, Integer> menu, int balance) {
        // Sorting the items by cost descending is an intuition which prevents duplicate combinations!
        List<String> allItems = menu.entrySet().stream().
                sorted((a, b) -> Integer.compare(b.getValue(), a.getValue())).
                map(Map.Entry::getKey).
                collect(Collectors.toList());

        Set<List<String>> validChoices = new HashSet<>();
        visit(menu, balance, allItems, validChoices, new ArrayList<>());
        return validChoices;
    }

    private boolean visit(Map<String, Integer> menu, int balance, List<String> items, Set<List<String>> validChoices, List<String> choice) {
        if (balance < 0) {
            // Not a valid path; return false to signal to the caller that they might be a leaf node
            return false;
        }

        // If all of the steps below are invalid, then we are a leaf node
        boolean isLeafNode = true;
        for (int i = 0; i < items.size(); i++) {
            List<String> availableItems = items.subList(i, items.size());
            String item = items.get(i);
            int itemCost = menu.get(item);

            List<String> next = new ArrayList<>(choice);
            next.add(item);
            boolean isValid = visit(menu, balance - itemCost, availableItems, validChoices, next);
            if (isValid) {
                isLeafNode = false;
            }
        }
        if (!choice.isEmpty() && isLeafNode) {
            // A leaf node means there is nothing more which could be added as all of our down streams choices have returned invalid.
            // Add this choice to the valid choices
            validChoices.add(choice);
        }
        return true;
    }

}


