import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FishAndChips {

    @Test
    public void fishAndChips() {
        Map<String, Integer> menu = new HashMap<>();
        menu.put("Fish", 5);
        menu.put("Sausage", 2);
        menu.put("Chips", 1);

        // Sort by cost descending is the intuition which prevents duplicate combinations!
        List<String> allItems = menu.entrySet().stream().sorted((a, b) -> Integer.compare(b.getValue(), a.getValue())).map(Map.Entry::getKey).collect(Collectors.toList());

        Set<List<String>> zeroPounds = possibleChoices(menu, 0, allItems, new HashSet<>(), new ArrayList<>());
        assertTrue(zeroPounds.isEmpty());

        Set<List<String>> twoPounds = possibleChoices(menu, 2, allItems, new HashSet<>(), new ArrayList<>());
        assertTrue(twoPounds.contains(List.of("Sausage")));

        Set<List<String>> fivePounds = possibleChoices(menu, 5, allItems, new HashSet<>(), new ArrayList<>());
        // TODO [Sausage, Sausage] is questionable; out exit condition is slightly wrong
        List<String> justFish = List.of("Fish");
        List<String> fiveChips = List.of("Chips", "Chips", "Chips", "Chips", "Chips");
        List<String> sausageAndThreeChips = List.of("Sausage", "Chips", "Chips", "Chips");
        List<String> twoSausageAndOneChips = List.of("Sausage", "Sausage", "Chips");
        assertTrue(fivePounds.contains(justFish));
        assertTrue(fivePounds.contains(fiveChips));
        assertTrue(fivePounds.contains(sausageAndThreeChips));
        assertTrue(fivePounds.contains(twoSausageAndOneChips));
    }

    private Set<List<String>> possibleChoices(Map<String, Integer> menu, int balance, List<String> items, Set<List<String>> validChoices, List<String> choice) {
        if (items.isEmpty()) {
            return validChoices;
        }

        for (int i = 0; i < items.size(); i++) {
            List<String> availableItems = items.subList(i, items.size());
            String item = items.get(i);
            int itemCost = menu.get(item);
            if (itemCost <= balance) {
                // Adding this time would blow the budget; add it
                List<String> next = new ArrayList<>(choice);
                next.add(item);
                possibleChoices(menu, balance - itemCost, availableItems, validChoices, next);

            } else {
                // We are the end of a streak; record what did fit on this path
                if (!choice.isEmpty()) {
                    validChoices.add(choice);
                }
            }
        }

        return validChoices;
    }

}


