package facebook

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ScoreboardInferenceTwo {

    @Test
    fun test() {
        //assertEquals(4, meh(listOf(10, 9, 2, 1)))

        assertEquals(2, meh(listOf(1, 2)))

        assertEquals(4, meh(listOf(2, 9)))
        assertEquals(1, meh(listOf(1)))
        assertEquals(1, meh(listOf(2)))
        assertEquals(1, meh(listOf(3)))
        assertEquals(1, meh(listOf(3, 3)))
        assertEquals(2, meh(listOf(3, 3, 4)))
        assertEquals(2, meh(listOf(4, 3, 3, 4)))

        assertEquals(3, meh(listOf(1, 2, 3, 4, 5)))
        assertEquals(4, meh(listOf(2, 4, 6, 8)))
        assertEquals(3, meh(listOf(8)))
    }

    fun meh(S: List<Int>): Int {
        // 30 / 35 test cases.
        fun makeChange(v: Int): Pair<MutableMap<Int, Int>, MutableSet<Int>> {
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

        // A naive starter that the largest scope controls the output completely.
        // Make change from the largest score; this is the shortest possible representation of the best score.
        var sorted = S.sorted()
        var largest = sorted.last();
        val changeForLargest = makeChange(largest)
        println(changeForLargest.first)

        // If we keep track of the unique smaller dominanations which appear in the other scores then
        // we might be able to make informed decisions
        val tail = sorted.subList(0, sorted.size - 1)
        val usedInTail = mutableSetOf<Int>()
        tail.forEach { i ->
            val makeChange = makeChange(i)
            println(makeChange)
            usedInTail.addAll(makeChange.second)
        }
        val usedInBest = changeForLargest.second

        var counts = changeForLargest.first
        // Jenga fixes; looking for a pattern

        val b = (usedInTail.contains(1) && !usedInBest.contains(1)) || (usedInTail.contains(2) && !usedInBest.contains(2))
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

        return counts.map { it.value }.sum()
    }

}