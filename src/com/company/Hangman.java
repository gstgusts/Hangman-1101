package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Hangman {

    private static final int MAX_RETRY_COUNT = 6;
    private static final String HIDDEN_LETTER = "_";

    private List<String> words = new ArrayList<>();

    private int tryCounter = 0;
    private Set<Character> usedLetters = new HashSet<>();
    private String currentWord;
    private String hiddenWord;

    public Hangman() {
        loadWords();
        restart();
    }

    public int getTryCounter() {
        return tryCounter;
    }

    public int getTryCount() {
        return MAX_RETRY_COUNT - tryCounter;
    }

    public String getHiddenWord() {
        return hiddenWord;
    }

    public void restart() {
        tryCounter = 0;
        usedLetters.clear();
        pickWord();
    }

    public void guess(Character ch) {

        var chUpper = ch.toString().toUpperCase();

        if(isGameOver() != GameResult.CONTINUE) {
            return;
        }

        if(usedLetters.contains(chUpper)) {
            return;
        }

        if(currentWord.toUpperCase().contains(chUpper)) {

            var chArray = hiddenWord.toCharArray();

            for (int i = 0; i < currentWord.length(); i++) {
                if(currentWord.toUpperCase().charAt(i) == chUpper.charAt(0)) {
                    chArray[i] = chUpper.charAt(0);
                }
            }

            hiddenWord = new String(chArray);
        } else {
            usedLetters.add(chUpper.charAt(0));
            ++tryCounter;
        }
    }

    public Iterable<Character> getUsedLetters() {
        return usedLetters;
    }

    public GameResult isGameOver() {
        if (tryCounter == MAX_RETRY_COUNT) {
            return GameResult.HANG_THE_MAN;
        } else if (!hiddenWord.contains(HIDDEN_LETTER)) {
            return GameResult.SUCCESS;
        }
        return GameResult.CONTINUE;
    }

    private void pickWord() {
        var wordCount = words.size();
        var index = getRandomNumber(0, wordCount);
        currentWord = words.get(index);

        //System.out.println(currentWord);

        hiddenWord = "";

        for (int i = 0; i < currentWord.length(); i++) {
            hiddenWord += HIDDEN_LETTER;
        }
    }

    private int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    private void loadWords() {
        words.clear();

        try {
            File myObj = new File("C:\\Temp\\hangman.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                //System.out.println(data);
                words.add(data);
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
