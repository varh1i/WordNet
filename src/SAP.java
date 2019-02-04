import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SAP {

    private final Digraph digraph;
    private final int verticesSize;

    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException();
        }
        this.digraph = new Digraph(G);
        this.verticesSize = this.digraph.V();
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return length(Collections.singletonList(v), Collections.singletonList(w));
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return ancestor(Collections.singletonList(v), Collections.singletonList(w));
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validation(v, w);
        Set<Integer> markedV = new HashSet<>();
        HashMap<Integer, Integer> distanceV = new HashMap<>();
        Queue<Integer> queueV = new Queue<>();
        for (int integer : v) {
            markedV.add(integer);
            distanceV.put(integer, 0);
            queueV.enqueue(integer);
        }

        Set<Integer> markedW = new HashSet<>();
        HashMap<Integer, Integer> distanceW = new HashMap<>();
        Queue<Integer> queueW = new Queue<>();
        for (int integer : w) {
            if (markedV.contains(integer)) {
                return 0;
            }
            distanceW.put(integer, 0);
            markedW.add(integer);
            queueW.enqueue(integer);
        }

        int shortestLength = Integer.MAX_VALUE;
        boolean found = false;
        while (!queueV.isEmpty() || !queueW.isEmpty()) {

            Queue<Integer> adj = new Queue<>();
            while (!queueV.isEmpty()) {
                int toProcessV = queueV.dequeue();
                for (int integer : digraph.adj(toProcessV)) {
                    if (markedW.contains(integer)) {
                        int length = distanceW.get(integer) + distanceV.get(toProcessV) + 1;
                        if (length < shortestLength) {
                            shortestLength = length;
                            found = true;
                        }
                    } else if (!markedV.contains(integer)) {
                        adj.enqueue(integer);
                        distanceV.put(integer, distanceV.get(toProcessV) + 1);
                        markedV.add(integer);
                    }
                }
            }
            queueV = adj;

            adj = new Queue<>();
            while (!queueW.isEmpty()) {
                int toProcessW = queueW.dequeue();
                for (int integer : digraph.adj(toProcessW)) {
                    if (markedV.contains(integer)) {
                        int length = distanceV.get(integer) + distanceW.get(toProcessW) + 1;
                        if (length < shortestLength) {
                            shortestLength = length;
                            found = true;
                        }
                    } else if (!markedW.contains(integer)) {
                        adj.enqueue(integer);
                        distanceW.put(integer, distanceW.get(toProcessW) + 1);
                        markedW.add(integer);
                    }
                }
            }
            queueW = adj;
        }
        if (found) {
            return shortestLength;
        }
        return -1;
    }

    private void validation(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }
        for (Integer integer : v) {
            if (integer == null) {
                throw new IllegalArgumentException();
            } else {
                validate(integer);
            }
        }
        for (Integer integer : w) {
            if (integer == null) {
                throw new IllegalArgumentException();
            } else {
                validate(integer);
            }
        }
    }

    private void validate(int v) {
        if (v < 0 || v >= verticesSize) {
            throw new IllegalArgumentException();
        }
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validation(v, w);

        Set<Integer> markedV = new HashSet<>();
        HashMap<Integer, Integer> distanceV = new HashMap<>();
        Queue<Integer> queueV = new Queue<>();
        for (int integer : v) {
            markedV.add(integer);
            distanceV.put(integer, 0);
            queueV.enqueue(integer);
        }

        Set<Integer> markedW = new HashSet<>();
        HashMap<Integer, Integer> distanceW = new HashMap<>();
        Queue<Integer> queueW = new Queue<>();
        for (int integer : w) {
            if (markedV.contains(integer)) {
                return integer;
            }
            distanceW.put(integer, 0);
            markedW.add(integer);
            queueW.enqueue(integer);
        }

        int shortestLength = Integer.MAX_VALUE;
        int shortestAncestor = -1;
        boolean found = false;
        while (!queueV.isEmpty() || !queueW.isEmpty()) {

            Queue<Integer> adj = new Queue<>();
            while (!queueV.isEmpty()) {
                int toProcessV = queueV.dequeue();
                for (int integer : digraph.adj(toProcessV)) {
                    if (markedW.contains(integer)) {
                        int length = distanceW.get(integer) + distanceV.get(toProcessV) + 1;
                        if (length < shortestLength) {
                            shortestLength = length;
                            shortestAncestor = integer;
                            found = true;
                        }
                    } else if (!markedV.contains(integer)) {
                        adj.enqueue(integer);
                        distanceV.put(integer, distanceV.get(toProcessV) + 1);
                        markedV.add(integer);
                    }
                }
            }
            queueV = adj;

            adj = new Queue<>();
            while (!queueW.isEmpty()) {
                int toProcessW = queueW.dequeue();
                for (int integer : digraph.adj(toProcessW)) {
                    if (markedV.contains(integer)) {
                        int length = distanceV.get(integer) + distanceW.get(toProcessW) + 1;
                        if (length < shortestLength) {
                            shortestLength = length;
                            shortestAncestor = integer;
                            found = true;
                        }
                    } else if (!markedW.contains(integer)) {
                        adj.enqueue(integer);
                        distanceW.put(integer, distanceW.get(toProcessW) + 1);
                        markedW.add(integer);
                    }
                }
            }
            queueW = adj;

            if (found) {
                return shortestAncestor;
            }
        }
        return -1;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        /*
        Digraph digraph = new Digraph(12);
        digraph.addEdge(6,3);
        digraph.addEdge(7,3);
        digraph.addEdge(3,1);
        digraph.addEdge(4,1);
        digraph.addEdge(5,1);
        digraph.addEdge(8,5);
        digraph.addEdge(9,5);
        digraph.addEdge(10,9);
        digraph.addEdge(11,9);
        digraph.addEdge(1,0);
        digraph.addEdge(2,0);
        int v = 3;
        int w = 10;

        Digraph digraph = new Digraph(6);
        digraph.addEdge(1, 0);
        digraph.addEdge(1, 2);
        digraph.addEdge(2, 3);
        digraph.addEdge(3, 4);
        digraph.addEdge(4, 5);
        digraph.addEdge(5, 0);
        int v = 1;
        int w = 5;

        Digraph digraph = new Digraph(25);
        digraph.addEdge(13, 7);
        digraph.addEdge(14, 7);
        digraph.addEdge(7, 3);
        digraph.addEdge(8, 3);
        digraph.addEdge(9, 3);
        digraph.addEdge(15, 9);
        digraph.addEdge(16, 9);
        digraph.addEdge(21, 16);
        digraph.addEdge(22, 16);
        digraph.addEdge(3, 1);
        digraph.addEdge(4, 1);
        digraph.addEdge(1, 0);
        digraph.addEdge(2, 0);
        digraph.addEdge(6, 2);
        digraph.addEdge(5, 2);
        digraph.addEdge(10, 5);
        digraph.addEdge(17, 10);
        digraph.addEdge(18, 10);
        digraph.addEdge(11, 5);
        digraph.addEdge(12, 5);
        digraph.addEdge(19, 12);
        digraph.addEdge(20, 12);
        digraph.addEdge(23, 20);
        digraph.addEdge(24, 20);

        List<Integer> v = Arrays.asList(13,23,24);
        List<Integer> w = Arrays.asList(6,16,17);

        SAP sap = new SAP(digraph);
        System.out.println(sap.length(v,w));
        System.out.println(sap.ancestor(v,w));


        // digraph1.txt
        Digraph digraph = new Digraph(13);
        digraph.addEdge(7, 3);
        digraph.addEdge(8, 3);
        digraph.addEdge(3, 1);
        digraph.addEdge(4, 1);
        digraph.addEdge(5, 1);
        digraph.addEdge(9, 5);
        digraph.addEdge(10, 5);
        digraph.addEdge(11, 10);
        digraph.addEdge(12, 10);
        digraph.addEdge(1, 0);
        digraph.addEdge(2, 0);

        //digraph4.txt
        Digraph digraph = new Digraph(10);
        digraph.addEdge(1, 2);
        digraph.addEdge(1, 7);
        digraph.addEdge(2, 3);
        digraph.addEdge(3, 4);
        digraph.addEdge(4, 5);
        digraph.addEdge(5, 6);
        digraph.addEdge(7, 8);
        digraph.addEdge(9, 0);
        digraph.addEdge(8, 6);
        digraph.addEdge(0, 8);

        SAP sap = new SAP(digraph);
        System.out.println(sap.length(4,1));
        System.out.println(sap.ancestor(4,1));
        */
    }
}
