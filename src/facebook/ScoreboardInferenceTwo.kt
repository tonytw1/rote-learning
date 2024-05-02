package facebook

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ScoreboardInferenceTwo {

    @Test
    fun test() {
        //assertEquals(4, meh(listOf(10, 9, 2, 1)))

        assertEquals(2, findSmallest(listOf(1, 2)))

        assertEquals(4, findSmallest(listOf(2, 9)))
        assertEquals(1, findSmallest(listOf(1)))
        assertEquals(1, findSmallest(listOf(2)))
        assertEquals(1, findSmallest(listOf(3)))
        assertEquals(1, findSmallest(listOf(3, 3)))
        assertEquals(2, findSmallest(listOf(3, 3, 4)))
        assertEquals(2, findSmallest(listOf(4, 3, 3, 4)))

        assertEquals(3, findSmallest(listOf(1, 2, 3, 4, 5)))
        assertEquals(4, findSmallest(listOf(2, 4, 6, 8)))
        assertEquals(3, findSmallest(listOf(8)))
    }

    fun findSmallest(S: List<Int>): Int {
        // 30 / 35 test cases.
        fun makeChange(v: Int): Pair<MutableMap<Int, Int>, MutableSet<Int>> {
            // This will only work for a canonical coin set; we think 3, 2, 1 is canonical though
            val dominations = listOf(3, 2, 1)
            var r = v
            var coins = mutableMapOf<Int, Int>()
            var uniques = mutableSetOf<Int>()
            dominations.forEach { d ->
                val i = r / d
                coins.put(d, i)
                if (i != 0) {
                    uniques.add(d)
                }
                r %= d
            }
            return Pair(coins, uniques)
        }

        // A naive starting guess is that the largest value in the list controls how small the best outcome could be at best.
        // Make change from the largest score; this is the shortest possible representation of the best score.
        var sorted = S.sorted()
        var largest = sorted.last();
        val changeForLargest = makeChange(largest)
        println("Change for largest value (" + largest + "): " + changeForLargest.first)

        // But this naive best guess clearly isn't complete.
        // ie. 9, 1 -> [3, 3, 3] which has not way of making 1.

        // We might be able to house trade our way down from the largest values change to a best fit?
        // If we keep track of the unique smaller denominations which appear in the other scores we might be able to make informed decisions
        val tail = sorted.subList(0, sorted.size - 1)
        val usedInTail = mutableSetOf<Int>()
        tail.forEach { i ->
            usedInTail.addAll(makeChange(i).second)
        }
        println("Denominations used in the tail: $usedInTail")


        val usedInBest = changeForLargest.second

        var counts = changeForLargest.first
        // Jenga fixes; looking for a pattern

        val b =
            (usedInTail.contains(1) && !usedInBest.contains(1)) || (usedInTail.contains(2) && !usedInBest.contains(2))
        val b1 = counts.getOrDefault(3, 0) > 0
        if (b && b1) {
            counts.set(3, counts[3]!! - 1)
            counts.set(1, counts.getOrDefault(1, 0) + 1)
            counts.set(2, counts.getOrDefault(2, 0) + 1)
        }

        // Split a 2 to make a 1
        val b3 = (usedInTail.contains(1) && counts.getOrDefault(1, 0) == 0) && (counts.getOrDefault(2, 0) > 0)
        if (b3) {
            counts.set(2, counts[2]!! - 1)
            counts.set(1, counts.getOrDefault(1, 0) + 2)
        }

        // If we need a 1 but it does not appear in best has a 2, then we can replace 2 with 2 1s.
        if (!S.contains(1) && counts.getOrDefault(1, 0) > 0 && counts.getOrDefault(3, 0) > 0) {
            counts.set(1, counts.getOrDefault(1, 0) - 1)
            counts.set(2, counts.getOrDefault(2, 0) + 2)
            counts.set(3, counts.getOrDefault(3, 0) - 1)
        }

        // The number of coins in our corrected change should now be the smallest possible which covers all our values
        return counts.map { it.value }.sum()

        // Expect it isn't!
        // We don't know what the failing inputs are.
        // We can try to fizz these function but are limited in what checks we can to.
        // To try:
        // - Check that every value really can be built from our output? A failing on the too short side could be detected this way
    }

}