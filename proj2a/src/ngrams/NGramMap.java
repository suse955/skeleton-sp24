package ngrams;

import edu.princeton.cs.algs4.In;


import java.util.*;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.


    private RedBlackTree _redBlackTree;
    private TimeSeries _count;
    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        In wordIn = new In(wordsFilename);
        In countIn = new In(countsFilename);
        _count = new TimeSeries();
        _redBlackTree = new RedBlackTree();
        while (!countIn.isEmpty()) {
            String nextLine = countIn.readLine();
            String[] splitLine = nextLine.split(",");
            try {
                Integer key = Integer.parseInt(splitLine[0]);
                Double value = Double.parseDouble(splitLine[1]);
                _count.put(key, value);
            } catch (NumberFormatException ignored) {

            }

        }
        while (!wordIn.isEmpty()) {
            String nextLine = wordIn.readLine();
            String[] splitLine = nextLine.split("\t");
            try {
                TimeSeries ts = new TimeSeries();
                ts.put(Integer.parseInt(splitLine[1]), Double.parseDouble(splitLine[2]));
                _redBlackTree.put(splitLine[0], ts);
            } catch (NumberFormatException ignored) {

            }
        }
    }



    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries result = (TimeSeries) _redBlackTree.get(word).clone();
        if (result.isEmpty()) {
            return new TimeSeries();
        }
//        ArrayList<Integer> keys = new ArrayList<>(result.keySet());
//        Collections.sort(keys);
//        TimeSeries ts = new TimeSeries();
//        for (Integer key : keys) {
//            if (key.compareTo(startYear) >= 0 && key.compareTo(endYear) <= 0) {
//                ts.put(key,result.get())
//            }
//        }
        return new TimeSeries(result,startYear,endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        return _redBlackTree.get(word).isEmpty() ? new TimeSeries() : (TimeSeries) _redBlackTree.get(word).clone();
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        return (TimeSeries) _count.clone();
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries wordS2E = new TimeSeries(_redBlackTree.get(word), startYear, endYear);
        if (wordS2E.isEmpty()) {
            return new TimeSeries();
        }
//        TimeSeries result = new TimeSeries();
        /**
         * 应该根据wordS2E遍历
         * for (int i = startYear; i <= endYear; i++) {
         * result.put(i, wordS2E.get(i) / _count.get(i));
         * }
         * */
//        List<Integer> keys = wordS2E.years();
//        for (Integer key : keys) {
//            result.put(key, wordS2E.get(key) / _count.get(key));
//        }

        return wordS2E.dividedBy(_count);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        TimeSeries ts = _redBlackTree.get(word);
        if (ts.isEmpty()) {
            return new TimeSeries();
        }
//        TimeSeries result = new TimeSeries();
//        List<Integer> keys = ts.years();
//        for (Integer key : keys) {
//            result.put(key, ts.get(key) / _count.get(key));
//        }
        return ts.dividedBy(_count);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries ts = new TimeSeries();
        for (String word : words) {
            TimeSeries tmp = weightHistory(word, startYear, endYear);
            ts = ts.plus(tmp);
        }
        return ts;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.
        TimeSeries ts = new TimeSeries();
        for (String word : words) {
            TimeSeries tmp = weightHistory(word);
            ts.plus(tmp);
        }
        return ts;
    }

    @Override
    public String toString() {

        return  "size = "+ _redBlackTree.size();
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
