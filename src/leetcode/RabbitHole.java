package leetcode;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RabbitHole {

    @Test
    public void test() {
        assertEquals(3, getMaxVisitableWebpages(new int[]{4, 3, 5, 1, 2}));
        assertEquals(4, getMaxVisitableWebpages(new int[]{4, 1, 2, 1}));
        assertEquals(4, getMaxVisitableWebpages(new int[]{2, 4, 2, 2, 3}));
    }


    public int getMaxVisitableWebpages(int[] input) {
        // The single direction, not nullable links and only form groups which are rings or rings with spokes.

        int nextGroup = 1;
        Map<Integer, Integer> nodeGroups = new HashMap<>();
        Map<Integer, Integer> nodeInLinks = new HashMap<>();
        Map<Integer, Integer> nodeFwdLinks = new HashMap<>();

        // Visit every node; assigning them to a group.
        // If the node links to a node which is already in a group, then this node is assigned the same group.
        // Record which nodes have been linked to; the longest threads will be the leaf nodes or the entire ring.

        for (int n = 1; n <= input.length; n++) {
            int linkTo = input[n - 1];
            //System.out.println("Node " + n + " links to " + linkTo);
            nodeInLinks.put(linkTo, n);
            nodeFwdLinks.put(n, linkTo);

            Integer groupOfNode = nodeGroups.get(n);
            Integer groupOfLinkedNode = nodeGroups.get(linkTo);
            if (groupOfNode != null && groupOfLinkedNode == null) {
                // Linked node joins this group
                nodeGroups.put(linkTo, groupOfLinkedNode);
            } else if (groupOfNode == null && groupOfLinkedNode != null) {
                nodeGroups.put(n, groupOfLinkedNode);
            } else if (groupOfNode == null && groupOfLinkedNode == null) {
                // Assign a new group
                int group = nextGroup;
                nextGroup++;
                nodeGroups.put(n, group);
                nodeGroups.put(linkTo, group);
            } else {
                // Two existing groups are being linked; this is a ring with spokes.
                // This node and everyone else it's group are reassigned to the linked group.
                for (int gn : nodeGroups.keySet()) {
                    if (nodeGroups.get(gn) == groupOfNode) {
                        nodeGroups.put(gn, groupOfLinkedNode);
                    }
                }
            }

        }

        System.out.println(nodeGroups);
        int numberOfGroups = nextGroup - 1;
        System.out.println("Finished with " + numberOfGroups + " groups.");

        // Foreach group find the leaf node for that group; if there are none this this group is a ring.
        Map<Integer, Integer> groupScores = new HashMap<>();
        for (int g = 1; g <= numberOfGroups; g++) {
            List<Integer> leafNodes = new ArrayList<>();
            int nodesCount = 0;
            for (int n = 1; n <= input.length; n++) {
                if (nodeGroups.get(n) == g) {
                    nodesCount++;
                    if (nodeInLinks.get(n) == null) {
                        //System.out.println("Node " + n + " is a leaf node of group " + g);
                        leafNodes.add(n);
                    }
                }
            }

            if (leafNodes.size() == 0) {
                //System.out.println("Group " + g + " is a ring with " + nodesCount + " nodes.");
                groupScores.put(g, nodesCount);
            } else {
                //System.out.println("Group " + g + " has " + leafNodes.size() + " leaf nodes.");
                // For each leaf node transverse the ring until loop detection.
                int best = 0;
                for (int n : leafNodes) {
                   //System.out.println("Starting at leaf node " + n);
                    Set<Integer> visited = new HashSet<>();
                    visited.add(n);
                    //System.out.println("Node " + n + " links to " + nodeFwdLinks.get(n));

                    Integer next = nodeFwdLinks.get(n);
                    int length = 1;
                    while (next != null && !visited.contains(next)) {
                        //System.out.println("Node " + next + " links to " + nodeFwdLinks.get(next));
                        visited.add(next);
                        next = nodeFwdLinks.get(next);
                        //System.out.println(next);
                        //System.out.println(visited);
                        length++;
                    }
                    //System.out.println("Leaf node " + n + " is the start of a chain " + length + " long.");
                    best = Math.max(best, length);
                }

                groupScores.put(g, best);
            }
        }

        return groupScores.values().stream().max(Integer::compareTo).orElse(0);
    }


}
