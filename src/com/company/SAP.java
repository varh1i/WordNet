package com.company;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SAP {

    private Digraph digraph;

    public SAP(Digraph G) {
        this.digraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v == w) {
            return 0;
        }
        HashMap<Integer, Integer> edgeFromV = new HashMap<>();
        HashMap<Integer, Integer> edgeFromW = new HashMap<>();
        Set<Integer> markedV = new HashSet<>();
        Set<Integer> markedW = new HashSet<>();
        markedV.add(v);
        markedW.add(w);

        HashMap<Integer, Integer> distanceV = new HashMap<>();
        distanceV.put(v, 0);
        HashMap<Integer, Integer> distanceW = new HashMap<>();
        distanceW.put(w, 0);

        Queue<Integer> queueV = new Queue<>();
        Queue<Integer> queueW = new Queue<>();
        queueV.enqueue(v);
        queueW.enqueue(w);

        while (!queueV.isEmpty() && !queueW.isEmpty()) {

            Integer toProcessV = queueV.dequeue();
            for (Integer integer : digraph.adj(toProcessV)) {
                if (markedW.contains(integer)) {
                  return distanceW.get(integer) + distanceV.get(toProcessV) + 1;
                } else if (!markedV.contains(integer)) {
                    queueV.enqueue(integer);
                    edgeFromV.put(integer, toProcessV);
                    distanceV.put(integer, distanceV.get(toProcessV) + 1);
                    markedV.add(integer);
                }
            }

            Integer toProcessW = queueW.dequeue();
            for (Integer integer : digraph.adj(toProcessW)) {
                if (markedV.contains(integer)) {
                    return distanceV.get(integer) + distanceW.get(toProcessW) + 1;
                } else if (!markedW.contains(integer)) {
                    queueW.enqueue(integer);
                    edgeFromW.put(integer, toProcessW);
                    distanceW.put(integer, distanceW.get(toProcessW) + 1);
                    markedW.add(integer);
                }
            }
        }
        return -1;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v == w) {
            return w;
        }
        HashMap<Integer, Integer> edgeFromV = new HashMap<>();
        HashMap<Integer, Integer> edgeFromW = new HashMap<>();
        Set<Integer> markedV = new HashSet<>();
        Set<Integer> markedW = new HashSet<>();
        markedV.add(v);
        markedW.add(w);

        HashMap<Integer, Integer> distanceV = new HashMap<>();
        distanceV.put(v, 0);
        HashMap<Integer, Integer> distanceW = new HashMap<>();
        distanceW.put(w, 0);

        Queue<Integer> queueV = new Queue<>();
        Queue<Integer> queueW = new Queue<>();
        queueV.enqueue(v);
        queueW.enqueue(w);

        while (!queueV.isEmpty() && !queueW.isEmpty()) {

            Integer toProcessV = queueV.dequeue();
            for (Integer integer : digraph.adj(toProcessV)) {
                if (markedW.contains(integer)) {
                    return integer;
                } else if (!markedV.contains(integer)) {
                    queueV.enqueue(integer);
                    edgeFromV.put(integer, toProcessV);
                    distanceV.put(integer, distanceV.get(toProcessV) + 1);
                    markedV.add(integer);
                }
            }

            Integer toProcessW = queueW.dequeue();
            for (Integer integer : digraph.adj(toProcessW)) {
                if (markedV.contains(integer)) {
                    return integer;
                } else if (!markedW.contains(integer)) {
                    queueW.enqueue(integer);
                    edgeFromW.put(integer, toProcessW);
                    distanceW.put(integer, distanceW.get(toProcessW) + 1);
                    markedW.add(integer);
                }
            }
        }
        return -1;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        HashMap<Integer, Integer> edgeFromV = new HashMap<>();
        HashMap<Integer, Integer> edgeFromW = new HashMap<>();

        Set<Integer> markedV = new HashSet<>();
        HashMap<Integer, Integer> distanceV = new HashMap<>();
        Queue<Integer> queueV = new Queue<>();
        for (Integer integer : v) {
            markedV.add(integer);
            distanceV.put(integer, 0);
            queueV.enqueue(integer);
        }

        Set<Integer> markedW = new HashSet<>();
        HashMap<Integer, Integer> distanceW = new HashMap<>();
        Queue<Integer> queueW = new Queue<>();
        for (Integer integer : w) {
            if (markedV.contains(w)) {
                return 0;
            }
            distanceW.put(integer, 0);
            markedW.add(integer);
            queueW.enqueue(integer);
        }

        while (!queueV.isEmpty() && !queueW.isEmpty()) {
            Integer toProcessV = queueV.dequeue();
            for (Integer integer : digraph.adj(toProcessV)) {
                if (markedW.contains(integer)) {
                    return distanceW.get(integer) + distanceV.get(toProcessV) + 1;
                } else if (!markedV.contains(integer)) {
                    queueV.enqueue(integer);
                    edgeFromV.put(integer, toProcessV);
                    distanceV.put(integer, distanceV.get(toProcessV) + 1);
                    markedV.add(integer);
                }
            }

            Integer toProcessW = queueW.dequeue();
            for (Integer integer : digraph.adj(toProcessW)) {
                if (markedV.contains(integer)) {
                    return distanceV.get(integer) + distanceW.get(toProcessW) + 1;
                } else if (!markedW.contains(integer)) {
                    queueW.enqueue(integer);
                    edgeFromW.put(integer, toProcessW);
                    distanceW.put(integer, distanceW.get(toProcessW) + 1);
                    markedW.add(integer);
                }
            }
        }
        return -1;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        HashMap<Integer, Integer> edgeFromV = new HashMap<>();
        HashMap<Integer, Integer> edgeFromW = new HashMap<>();

        Set<Integer> markedV = new HashSet<>();
        HashMap<Integer, Integer> distanceV = new HashMap<>();
        Queue<Integer> queueV = new Queue<>();
        for (Integer integer : v) {
            markedV.add(integer);
            distanceV.put(integer, 0);
            queueV.enqueue(integer);
        }

        Set<Integer> markedW = new HashSet<>();
        HashMap<Integer, Integer> distanceW = new HashMap<>();
        Queue<Integer> queueW = new Queue<>();
        for (Integer integer : w) {
            if (markedV.contains(w)) {
                return integer;
            }
            distanceW.put(integer, 0);
            markedW.add(integer);
            queueW.enqueue(integer);
        }

        while (!queueV.isEmpty() && !queueW.isEmpty()) {

            Integer toProcessV = queueV.dequeue();
            for (Integer integer : digraph.adj(toProcessV)) {
                if (markedW.contains(integer)) {
                    return integer;
                } else if (!markedV.contains(integer)) {
                    queueV.enqueue(integer);
                    edgeFromV.put(integer, toProcessV);
                    distanceV.put(integer, distanceV.get(toProcessV) + 1);
                    markedV.add(integer);
                }
            }

            Integer toProcessW = queueW.dequeue();
            for (Integer integer : digraph.adj(toProcessW)) {
                if (markedV.contains(integer)) {
                    return integer;
                } else if (!markedW.contains(integer)) {
                    queueW.enqueue(integer);
                    edgeFromW.put(integer, toProcessW);
                    distanceW.put(integer, distanceW.get(toProcessW) + 1);
                    markedW.add(integer);
                }
            }
        }
        return -1;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        /*Digraph digraph = new Digraph(12);
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
        int w = 10;*/

        /*Digraph digraph = new Digraph(6);
        digraph.addEdge(1, 0);
        digraph.addEdge(1, 2);
        digraph.addEdge(2, 3);
        digraph.addEdge(3, 4);
        digraph.addEdge(4, 5);
        digraph.addEdge(5, 0);
        int v = 1;
        int w = 5;*/

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
    }
}
