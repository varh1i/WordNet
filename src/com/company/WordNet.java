package com.company;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordNet {

    private Map<String, Set<Integer>> words;
    private Map<Integer, String> verticesString;
    private SAP sap;
    private Integer root;
    private static Digraph digraph;

    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }
        // nouns = new HashSet<>();
        addVertices(synsets);
        this.digraph = addEdges(hypernyms);
        this.sap = new SAP(digraph);
    }

    private void addVertices(String synsets) {
        this.words = new HashMap<>();
        this.verticesString = new HashMap<>();
        In in = new In(synsets);
        String line;
        while ((line = in.readLine()) != null) {
            String[] fields = line.split(",");
            Set<String> synset = new HashSet<>(Arrays.asList(fields[1].split(" ")));
            for (String s : synset) {
                Set<Integer> vertices = this.words.getOrDefault(s, new HashSet<>());
                vertices.add(Integer.valueOf(fields[0]));
                this.words.put(s, vertices);
            }
            this.verticesString.put(Integer.valueOf(fields[0]), fields[1]);
        }
    }

    private Digraph addEdges(String hypernyms) {
        Digraph digraph = new Digraph(verticesString.size());

        In in = new In(hypernyms);
        String line;
        while ((line = in.readLine()) != null) {
            int indexOf = line.indexOf(",");
            if (indexOf != -1) {
                int edgeFrom = Integer.valueOf(line.substring(0, indexOf));
                String[] edgesTo = line.substring(indexOf + 1).split(",");
                for (String to : edgesTo) {
                    digraph.addEdge(edgeFrom, Integer.valueOf(to));
                }

            } else {
                if (this.root == null) {
                    this.root = Integer.valueOf(line);
                    System.out.println("ROOT: " + this.root);
                } else {
                    throw new IllegalArgumentException("More than one root");
                }
            }
        }
        return digraph;
    }

    public Iterable<String> nouns() {
        return new HashSet(words.keySet());
    }

    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return words.keySet().contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        Set<Integer> v = words.get(nounA);
        Set<Integer> w = words.get(nounB);
        return sap.length(v,w);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        Set<Integer> v = words.get(nounA);
        Set<Integer> w = words.get(nounB);
        int ancestor = sap.ancestor(v, w);
        return this.verticesString.get(ancestor);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        System.out.println("Started: " + System.currentTimeMillis());
        WordNet wordNet = new WordNet("resources/synsets.txt", "resources/hypernyms.txt");

        BreadthFirstDirectedPaths breadthFirstDirectedPaths
                = new BreadthFirstDirectedPaths(digraph, 23559);
        breadthFirstDirectedPaths.hasPathTo(38003);
        // int distance = wordNet.distance("white_marlin", "mileage");
        // System.out.println("DISTANCE:" + distance);

        // int distance = wordNet.distance("worm", "bird");
        // String sap = wordNet.sap("worm", "bird");

        int distance = wordNet.distance("individual", "edible_fruit");
        String sap = wordNet.sap("individual", "edible_fruit");

        System.out.println("SAP: " + sap + ", distance:" + distance);

        System.out.println("Finished: " + System.currentTimeMillis());
    }
}
