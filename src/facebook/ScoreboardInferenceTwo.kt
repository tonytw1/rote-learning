package facebook

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ScoreboardInferenceTwo {

    /*
    1≤N≤500,000
    1≤S≤1,000,000,000
    */

    @Test
    fun testCoinSetIsReallyCanonical() {
        // Check the assumption that the coin set is canonical; seems to be
        val coins = listOf(3, 2, 1)
        val us = listOf(50, 25, 20, 10, 5, 1)
        val denominations = us + listOf(12)

        fun fizz(v: Int) {
            val greedy = makeChangeGreedy(v, denominations).first.map { it.value }.sum()
            val exhaustive = makeChangeExhaustive(v, denominations)
            if (greedy != exhaustive) {
                println("$v: $greedy != $exhaustive")
            }
            assertEquals(exhaustive, greedy)
        }

        for (i in 0..500) {
            fizz(i)
        }
    }

    private fun makeChangeExhaustive(v: Int, denominations: List<Int>): Int {
        val cache = mutableMapOf<Pair<Int, Int>, Int>()
        fun visit(v: Int, d: Int): Int {
            val key = Pair(v, d)
            if (cache.contains(key)) {
                return cache[key]!!
            }
            val x = if (denominations.contains((v))) {
                // Base case; we have an exact coin for this value; we have bottomed out
                d
            } else if (v < denominations.min()) {
                // Base case; overreach
                return Integer.MAX_VALUE
            } else {
                // Else step down and select the base path
                denominations.map { c ->
                    visit(v - c, d + 1)
                }.min()
            }
            cache[key] = x
            return x
        }

        val visit = visit(v, 1)
        if (visit == Int.MAX_VALUE) {
            return 0
        }
        return visit
    }

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

        val denominations = listOf(3, 2, 1)

        // A naive starting guess is that the largest value in the list controls how small the best outcome could be at best.
        // Make change from the largest score; this is the shortest possible representation of the best score.
        var sorted = S.sorted()
        var largest = sorted.last();
        val changeForLargest = makeChangeGreedy(largest, denominations)
        println("Change for largest value (" + largest + "): " + changeForLargest.first)

        // But this naive best guess clearly isn't complete.
        // ie. 9, 1 -> [3, 3, 3] which has not way of making 1.

        // We might be able to house trade our way down from the largest values change to a best fit?
        // If we keep track of the unique smaller denominations which appear in the other scores we might be able to make informed decisions
        val tail = sorted.subList(0, sorted.size - 1)
        val usedInTail = mutableSetOf<Int>()
        tail.forEach { i ->
            usedInTail.addAll(makeChangeGreedy(i, denominations).second)
        }
        println("Denominations used in the tail: $usedInTail")


        val usedInBest = changeForLargest.second

        var counts = changeForLargest.first
        // Jenga fixes; looking for a pattern

        val b =
            (S.contains(1) && !usedInBest.contains(1)) || (S.contains(2) && !usedInBest.contains(2))
        val b1 = counts.getOrDefault(3, 0) > 0
        if (b && b1) {
            counts.set(3, counts[3]!! - 1)
            counts.set(1, counts.getOrDefault(1, 0) + 1)
            counts.set(2, counts.getOrDefault(2, 0) + 1)
        }

        // Split a 2 to make a 1
        val b3 = (S.contains(1) && counts.getOrDefault(1, 0) == 0) && (counts.getOrDefault(2, 0) > 0)
        if (b3) {
            counts.set(2, counts[2]!! - 1)
            counts.set(1, counts.getOrDefault(1, 0) + 2)
        }

        // The number of coins in our corrected change should now be the smallest possible which covers all our values
        return counts.map { it.value }.sum()

        // Expect it isn't!
        // We don't know what the failing inputs are.
        // We can try to fizz these function but are limited in what checks we can to.
        // To try:
        // - Check that every value really can be built from our output? A failing on the too short side could be detected this way
    }

    fun makeChangeGreedy(v: Int, denominations: List<Int>): Pair<MutableMap<Int, Int>, MutableSet<Int>> {
        // This will only work for a canonical coin set; we think 3, 2, 1 is canonical though
        var r = v
        var coins = mutableMapOf<Int, Int>()
        var uniques = mutableSetOf<Int>()
        denominations.forEach { d ->
            val i = r / d
            coins[d] = i
            if (i != 0) {
                uniques.add(d)
            }
            r %= d
        }
        return Pair(coins, uniques)
    }

}