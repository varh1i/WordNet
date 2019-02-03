public class Outcast {

    private WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int maxDistance = Integer.MIN_VALUE;
        String outcast = null;
        for (String i : nouns) {
            int distance = 0;
            for (String j : nouns) {
                int distance1 = wordNet.distance(i, j);
                distance += distance1;
            }
            if (distance > maxDistance) {
                maxDistance = distance;
                outcast = i;
            }
        }
        return outcast;
    }


    public static void main(String[] args) {
        WordNet wordNet = new WordNet("resources/synsets.txt", "resources/hypernyms.txt");
        // int distance = wordNet.distance("bear", "table");
        // int distance = wordNet.distance("bear", "entity");
        // int distance = wordNet.distance("table", "entity");
        // System.out.println("Distance: " + distance);

        // System.out.println("isNoun: " + wordNet.isNoun("bear"));
        Outcast outcasT = new Outcast(wordNet);
        // String outcast = outcasT.outcast(new String[] {"horse", "zebra", "cat", "bear", "table"});
        // String outcast = outcasT.outcast(new String[] {"water", "soda", "bed", "orange_juice", "milk", "apple_juice", "tea", "coffee"});
        String outcast = outcasT.outcast(new String[] {"apple", "pear", "peach", "banana", "lime", "lemon", "blueberry", "strawberry", "mango", "watermelon", "potato"});

        System.out.println("OUTCAST: " + outcast);

        //
    }
}
