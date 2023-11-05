package lab7.set_student;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

public class MyWordCount {
    // public static final String fileName = "data/hamlet.txt";
    public static final String fileName = "data/fit.txt";

    private List<String> words = new ArrayList<>();

    public MyWordCount() {
        try {
            this.words.addAll(Utils.loadWords(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Prints out the number of times each unique token appears in the file
    // data/hamlet.txt (or fit.txt)
    // In this method, we do not consider the order of tokens.
    public List<WordCount> getWordCounts() {
        List<WordCount> resList = new ArrayList<>();
        for (String w : words) {
            WordCount wc = new WordCount(w, countWord(w));
            if (!resList.contains(wc)) {
                resList.add(wc);
            }
        }
        return resList;
    }

    // Returns the words that their appearance are 1, do not consider duplidated
    // words
    public Set<String> getUniqueWords() {
        Set<String> res = new HashSet<>();
        List<WordCount> list = getWordCounts();
        for (WordCount wc : list) {
            if (wc.getCount() == 1) {
                res.add(wc.getWord());
            }
        }
        return res;
    }

    // Returns the words in the text file, duplicated words appear once in the
    // result
    public Set<String> getDistinctWords() {
        Set<String> res = new HashSet<>();
        List<WordCount> list = getWordCounts();
        for (WordCount wc : list) {
            if (wc.getCount() != 1) {
                res.add(wc.getWord());
            }
        }
        return res;
    }

    // Prints out the number of times each unique token appears in the file
    // data/hamlet.txt (or fit.txt) according ascending order of tokens
    // Example: An - 3, Bug - 10, ...
    public Set<WordCount> printWordCounts() {
        Set<WordCount> res = new TreeSet<>(new Comparator<WordCount>() {
            @Override
            public int compare(WordCount o1, WordCount o2) {
                return o1.getWord().compareTo(o2.getWord());
            }
        });
        res.addAll(getWordCounts());
        return res;
    }


    // Prints out the number of times each unique token appears in the file
    // data/hamlet.txt (or fit.txt) according descending order of occurences
    // Example: Bug - 10, An - 3, Nam - 2.
    public Set<WordCount> exportWordCountsByOccurence() {
        Set<WordCount> res = new TreeSet<>(new Comparator<WordCount>() {
            @Override
            public int compare(WordCount o1, WordCount o2) {
                return -(o2.getCount() - o1.getCount());
            }
        });
        res.addAll(getWordCounts());
        return res;
    }

    // delete words begining with the given pattern (i.e., delete words begin with
    // 'A' letter
    public Set<String> filterWords(String pattern) {
        Set<String> res = new HashSet<>();
        List<WordCount> list = getWordCounts();
        for (WordCount wc : list) {
            if (!getFirstLetter(wc.getWord()).equals(pattern)) {
                res.add(wc.getWord());
            }
        }
        return res;
    }

    public int countWord(String w) {
        int count = 0;
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).equals(w)) {
                count++;
            }
        }
        return count;
    }

    public String getFirstLetter(String s) {
        return s.charAt(0) + "";
    }

    public static void main(String[] args) {
        MyWordCount myWordCount = new MyWordCount();
        List<WordCount> list = myWordCount.getWordCounts();
        Set<String> s1 = myWordCount.getUniqueWords();
        Set<String> s2 = myWordCount.getDistinctWords();
        Set<String> s3 = myWordCount.filterWords("B");
        Set<WordCount> s4 = myWordCount.printWordCounts();
        Set<WordCount> s5 = myWordCount.exportWordCountsByOccurence();

        for(WordCount w : s5){
            System.out.println(w.toString());
        }
    }

}
