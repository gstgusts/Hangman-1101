package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        var hangman = new Hangman();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(hangman.getHiddenWord());
            printUsedLetters(hangman.getUsedLetters());
            System.out.println("You have tries left: " + hangman.getTryCount());

            System.out.print("Enter letter (type [exit] to finish): ");
            var userInput = scanner.nextLine();

            if(userInput.equalsIgnoreCase("exit")) {
                break;
            }

            if(!userInput.isBlank()) {
                hangman.guess(userInput.charAt(0));

                switch (hangman.isGameOver()) {
                    case HANG_THE_MAN -> {
                        System.out.print("Game is over, you lost !!!, type [new] to start again: ");

                        var userInput2 = scanner.nextLine();

                        if(userInput2.equalsIgnoreCase("new")) {
                            hangman.restart();
                            continue;
                        }

                        break;
                    }
                    case SUCCESS -> {
                        System.out.println("You won!!!");
                        break;
                    }
                }
            }
        }
    }

    public static void printUsedLetters(Iterable<Character> chars) {
        for (var usedLetter : chars) {
            System.out.println(usedLetter);
        }
    }
}
