// Rizon Takabe
// CS 141
// 5/15/23
// Assignment 1 Word Search Generator
// Purpose: Make a program that takes in words then generates a word search with them

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class WordSearchGenerator {
    public static Scanner scan = new Scanner(System.in);
    public static char[][] solution;
    public static char[][] wordSearch;
    public static int size;

    public static void main(String[] args) {
        final double wordSearchScale = 1.5; // Sets the size of word search

        String input;
        ArrayList<String> list;
        boolean loop = true;
        boolean generated = false;

        Scanner scan = new Scanner(System.in);

        printIntro();
        // Main menu
        while(loop) {
            printOptions();
            input = scan.nextLine();
            switch (input.toLowerCase()) {
                // Generates the word search after accepting list of words
                case "g":
                    list = userWords();
                    size = (int) (list.get(0).length() * wordSearchScale);
                    solution = new char[size][size];
                    wordSearch = new char[size][size];
                    generateNew(list);
                    System.out.println("New word search generated!\n");
                    generated = true;
                    break;
                // print method
                case "p":
                    if (generated) {
                        print();
                    } else {
                        System.out.println("You need to generate a word search first!\n");
                    }
                    break;
                // showSolution method
                case "s":
                    if (generated) {
                        showSolution();
                    } else {
                        System.out.println("You need to generate a word search first!\n");
                    }
                    break;
                case "q":
                    loop = false;
                    continue;
                default:
                    System.out.println("Input not recognized\n");
            }
        }
        scan.close();
    }

    // Intro
    public static void printIntro() {
        System.out.println("Welcome to my word search generator!");
        System.out.println("This program will allow you to generate your own word search puzzle!");
    }

    // Menu options
    public static void printOptions() {
        System.out.println("Please select an option:");
        System.out.println("Generate a new word search (g)");
        System.out.println("Print your word search (p)");
        System.out.println("Show the solution to your word search (s)");
        System.out.println("Quit the program (q)");
    }

    // Creates solution array then makes a copy and fills in blanks with random letters
    public static void generateNew(ArrayList<String> wordList) {
        Random random = new Random();

        for (String word : wordList) {
            addWord(word);
        }

        for (int i = 0; i < size; i++) { // Rows
            for (int j = 0; j < size; j++) { // Columns
                // Add random character
                if (wordSearch[j][i] > 'Z' || wordSearch[j][i] < 'A') {
                    wordSearch[j][i] = (char) (random.nextInt(26) + 'A');
                }
            }
        }
    }

    // Asks user for number of words
    public static ArrayList<String> userWords() {
        System.out.print("How many words? ");
        int maxWords = scan.nextInt();
        ArrayList<String> words = new ArrayList<String>(maxWords);
        String input = scan.nextLine();
        for (int i = 0; i < maxWords; i++) {
            System.out.print("Input a word: ");
            input = scan.nextLine();
            words = sortedWordsArray(words, input);
        }
        return words;
    }

    // Puts word into a sorted array by length (long to short)
    public static ArrayList<String> sortedWordsArray(ArrayList<String> list, String word) {
        if (list.isEmpty()) {
            list.add(word);
        } else {
            int listSize = list.size();
            for (int i = 0; i < listSize; i++) {
                if (list.get(i).length() < word.length()) {
                    list.add(i, word);
                    break;
                } else if (i < list.size()) {
                    list.add(word); // adds word, shift smaller words down an index
                    break;
                }
            }
        }
        return list;
    }

    // checks if there is space to add a word
    public static boolean hasSpace(int direction, int rows, int columns, String word) {
        switch (direction) {
            // Vertical
            case 0:
                for (char c : word.toCharArray()) {
                    char currentChar = solution[columns][rows++];
                    // Checks if current letter is the one we want
                    if (currentChar == Character.toUpperCase(c)) {
                        continue;
                    // Checks if current letter is an empty space
                    } else if (currentChar < 'A' || currentChar > 'Z') {
                        continue;
                    } else {
                        return false;
                    }
                }
                return true;
            // Horizontal
            case 1:
                for (char c : word.toCharArray()) {
                    char currentChar = solution[columns++][rows];
                    if (currentChar == Character.toUpperCase(c)) {
                        continue;
                    } else if (currentChar < 'A' || currentChar > 'Z') {
                        continue;
                    } else {
                        return false;
                    }
                }
                return true;
            // Diagonal
            case 2:
                for (char c : word.toCharArray()) {
                    char currentChar = solution[columns++][rows++];
                    if (currentChar == Character.toUpperCase(c)) {
                        continue;
                    } else if (currentChar < 'A' || currentChar > 'Z') {
                        continue;
                    } else {
                        return false;
                    }
                }
                return true;
            default:
                return false;
        }
    }

    // Tries 100 times to add a word
    public static void addWord(String word) {
        Random random = new Random();
        int direction;
        int rows;
        int columns;
        for (int i = 0; i < 100; i++) {
            // Selects a direction for inserting word
            direction = random.nextInt(3);
            switch (direction) {
                // Vertical word
                case 0:
                    rows = random.nextInt(size - word.length());
                    columns = random.nextInt(size);
                    if (hasSpace(direction, rows, columns, word)) {
                        for (char c : word.toCharArray()) {
                            wordSearch[columns][rows] = Character.toUpperCase(c);
                            solution[columns][rows++] = Character.toUpperCase(c);
                        }
                        // Stops once word has been added
                        return;
                    }
                    break;
                // Horizontal word
                case 1:
                    rows = random.nextInt(size);
                    columns = random.nextInt(size - word.length());
                    if (hasSpace(direction, rows, columns, word)) {
                        for (char c : word.toCharArray()) {
                            wordSearch[columns][rows] = Character.toUpperCase(c);
                            solution[columns++][rows] = Character.toUpperCase(c);
                        }
                        return;
                    }
                    break;
                // Diagonal word
                case 2:
                    rows = random.nextInt(size - word.length() + 1);
                    columns = random.nextInt(size - word.length() + 1);
                    if (hasSpace(direction, rows, columns, word)) {
                        for (char c : word.toCharArray()) {
                            wordSearch[columns][rows] = Character.toUpperCase(c);
                            solution[columns++][rows++] = Character.toUpperCase(c);
                        }
                        return;
                    }
                    break;
                default:
                    continue;
            }
        }
    }

    // Prints the word
    public static void print() {
        for (int i = 0; i < size; i++) { // Rows
            for (int j = 0; j < size; j++) { // Column
                System.out.print(" " + wordSearch[j][i] + " ");
            }
            System.out.println("");
        }
    }

    // Prints the solution
    public static void showSolution() {
        for (int i = 0; i < size; i++) { // Rows
            for (int j = 0; j < size; j++) { // Columns
                // Prints characters in solution or spaces if not
                if (solution[j][i] <= 'Z' && solution[j][i] >= 'A') {
                    System.out.print(" " + solution[j][i] + " ");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
    }
}