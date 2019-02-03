package com.company;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

import java.util.HashMap;
import java.util.HashSet;
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
        HashMap<Integer, Integer> edgeFrom = new HashMap<>();
        Set<Integer> marked = new HashSet<>();
        marked.add(v);
        marked.add(w);
        HashMap<Integer, Integer> distance = new HashMap<>();
        distance.put(v, 0);
        distance.put(w, 0);
        Queue<Integer> queue = new Queue();
        queue.enqueue(v);
        queue.enqueue(w);

        while (!queue.isEmpty()) {
            Integer toProcess = queue.dequeue();
            for (Integer integer : digraph.adj(toProcess)) {
                if (!marked.contains(integer)) {
                    queue.enqueue(integer);
                    edgeFrom.put(integer, toProcess);
                    distance.put(integer, distance.get(toProcess) + 1);
                    marked.add(integer);
                } else {
                    return distance.get(integer) + distance.get(toProcess) + 1;
                }
            }
        }
        return -1;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v == w) {
            return v;
        }
        HashMap<Integer, Integer> edgeFrom = new HashMap<>();
        Set<Integer> marked = new HashSet<>();
        marked.add(v);
        marked.add(w);
        HashMap<Integer, Integer> distance = new HashMap<>();
        distance.put(v, 0);
        distance.put(w, 0);
        Queue<Integer> queue = new Queue();
        queue.enqueue(v);
        queue.enqueue(w);

        while (!queue.isEmpty()) {
            Integer toProcess = queue.dequeue();
            for (Integer integer : digraph.adj(toProcess)) {
                if (!marked.contains(integer)) {
                    queue.enqueue(integer);
                    edgeFrom.put(integer, toProcess);
                    distance.put(integer, distance.get(toProcess) + 1);
                    marked.add(integer);
                } else {
                    return integer;
                }
            }
        }
        return -1;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return 0;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return 0;
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

        Digraph digraph = new Digraph(6);
        digraph.addEdge(1, 0);
        digraph.addEdge(1, 2);
        digraph.addEdge(2, 3);
        digraph.addEdge(3, 4);
        digraph.addEdge(4, 5);
        digraph.addEdge(5, 0);
        int v = 1;
        int w = 5;

        SAP sap = new SAP(digraph);
        System.out.println(sap.length(v,w));
        System.out.println(sap.ancestor(v,w));
    }
}
