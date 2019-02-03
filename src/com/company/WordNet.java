package com.company;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordNet {

    private Set<String> nouns;
    private Map<Integer, Set<String>> vertices;
    private Digraph digraph;
    private Integer root;

    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }
        nouns = new HashSet<>();
        addVertices(synsets);
        addEdges(hypernyms);
    }

    private void addEdges(String hypernyms) {
        this.digraph = new Digraph(vertices.size());

        In in = new In(hypernyms);
        String line;
        while ((line = in.readLine()) != null) {
            int indexOf = line.indexOf(",");
            if (indexOf != -1) {
                int edgeFrom = Integer.valueOf(line.substring(0, indexOf));
                String[] edgesTo = line.substring(indexOf + 1).split(",");
                for (String to : edgesTo) {
                    this.digraph.addEdge(edgeFrom, Integer.valueOf(to));
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
    }

    private void addVertices(String synsets) {
        this.vertices = new HashMap<>();
        In in = new In(synsets);
        String line;
        while ((line = in.readLine()) != null) {
            String[] fields = line.split(",");
            Set<String> synset = new HashSet<>(Arrays.asList(fields[1].split(" ")));
            nouns.addAll(synset);
            this.vertices.put(Integer.valueOf(fields[0]), synset);


        }
    }

    public Iterable<String> nouns() {
        return new HashSet(nouns);
    }

    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return nouns.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }
        return 0;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }
        return null;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        System.out.println("Started: " + System.currentTimeMillis());
        WordNet wordNet = new WordNet("resources/synsets.txt", "resources/hypernyms.txt");
        System.out.println("Finished: " + System.currentTimeMillis());
    }
}
