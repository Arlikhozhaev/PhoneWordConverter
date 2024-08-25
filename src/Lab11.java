/**
 * File:        Lab11.java
 * Author:      Abdu Alim Arlikhozhaev
 * Date:        July 30, 2024
 * Compiler:    JDK 1.8
 */

import java.io.*;
import java.util.*;

public class Lab11 {
    public static void main(String[] args) throws IOException {
        match();
    }

    /** Read telephone numbers from a file and match with words */
    public static void match() throws IOException {
        String[] telephoneNumbers = readTelephoneNumbers("telephone.txt");
        int telCount = telephoneNumbers.length;

        String[] words = readWords("word_list.txt");
        int wordCount = words.length;

        PrintWriter writer = new PrintWriter(new FileWriter("result.txt"));

        for (int i = 0; i < telCount; i++) {
            String telNumber = telephoneNumbers[i];
            String firstPart = telNumber.substring(0, 3);
            String secondPart = telNumber.substring(3, 7);

            writer.println("TEL: " + telNumber);

            for (int j = 0; j < wordCount; j++) {
                String word = words[j];
                String wordDigitSequence = wordToDigit(word);

                if (word.length() == 7 && wordDigitSequence.equals(telNumber)){
                    writer.println(telNumber + ": " + word);
                }
            }
            // matches for the first 3-digit part
            for (int k = 0; k < wordCount; k++) {
                String word = words[k];
                String wordDigitSequence = wordToDigit(word);

                if (word.length() == 3 && wordDigitSequence.equals(firstPart)) {
                    writer.println(firstPart + " : " + word);
                }
            }

            // matches for the last 4-digit part
            for (int l = 0; l < wordCount; l++) {
                String word = words[l];
                String wordDigitSequence = wordToDigit(word);

                if (word.length() == 4 && wordDigitSequence.equals(secondPart)) {
                    writer.println(secondPart + " : " + word);
                }
            }

            generateCombinations(writer, telNumber, words, "");
            writer.println("--------");
        }
        writer.close();
    }

    /** Generate and print all possible combinations
     *
     * @param writer: object to print the result to a file
     * @param telNumber: part of a telephone number yet to be matched
     * @param words: words to match
     * @param prefix: current combination of words already matched
     */
    public static void generateCombinations(PrintWriter writer, String telNumber, String[] words, String prefix) {
        //check whether telNumber String is empty or not
        if (telNumber.isEmpty()) {
            //trim to remove any spaces
            writer.println(prefix.trim());

            return;
        }

        for (String word : words) {
            String digitSequence = wordToDigit(word);
            if (telNumber.startsWith(digitSequence)) {
                generateCombinations(writer, telNumber.substring(digitSequence.length()), words, prefix + " " + word);
            }
        }
    }

    /** Read telephone numbers from file
     *
     * @param filename: name of a file with telephone numbers
     * @return array of strings with last 7 digits of telephone number
     */
    public static String[] readTelephoneNumbers(String filename) throws FileNotFoundException {
        String[] telephoneNumbers = new String[10];
        int index = 0;
        Scanner scanner = new Scanner(new File(filename));

        while (scanner.hasNextLine()) {
            //trim to remove any spaces
            String line = scanner.nextLine().trim();
            if (line.length() >= 11) { // Expect area code and number
                String lastSevenDigits = line.substring(4).replaceAll(" ", ""); // Skip area code and spaces
                if (index >= telephoneNumbers.length) {
                    telephoneNumbers = resizeArray(telephoneNumbers);
                }
                telephoneNumbers[index++] = lastSevenDigits;
            }
        }

        return cutArray(telephoneNumbers, index);
    }

    /** Read words, convert to uppercase, store in an array
     *
     * @param filename: name of a file with words
     * @return array of strings with words of length 7
     */
    public static String[] readWords(String filename) throws FileNotFoundException {
        String[] words = new String[10];
        int index = 0;
        Scanner scanner = new Scanner(new File(filename));

        while (scanner.hasNextLine()) {
            String word = scanner.nextLine().toUpperCase();
            if (word.length() == 7 || word.length() == 3 || word.length() == 4) {
                if (index >= words.length) {
                    words = resizeArray(words);
                }
                words[index++] = word;
            }
        }

        return cutArray(words, index);
    }

    /** Convert word to digit
     *
     * @param word: word to convert
     * @return string
     */
    public static String wordToDigit(String word) {
        char[] digitSequence = new char[word.length()];
        for (int i = 0; i < word.length(); i++) {
            digitSequence[i] = charToDigit(word.charAt(i));
        }

        return new String(digitSequence);
    }

    /** Convert character to a digit
     *
     * @param ch: character to convert
     * @return digit
     */
    public static char charToDigit(char ch) {
        if (ch >= 'A' && ch <= 'C') {
            return '2';
        }
        else if (ch >= 'D' && ch <= 'F') {
            return '3';
        }
        else if (ch >= 'G' && ch <= 'I') {
            return '4';
        }
        else if (ch >= 'J' && ch <= 'L') {
            return '5';
        }
        else if (ch >= 'M' && ch <= 'O') {
            return '6';
        }
        else if (ch >= 'P' && ch <= 'S') {
            return '7';
        }
        else if (ch >= 'T' && ch <= 'V') {
            return '8';
        }
        else if (ch >= 'W' && ch <= 'Z') {
            return '9';
        }
        else {
           return '*'; //when input is invalid
        }
    }

    /** Resize an array (twice)
     *
     * @param array: original array
     * @return new array
     */
    public static String[] resizeArray(String[] array) {
        String[] newArray = new String[array.length * 2];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }

        return newArray;
    }

    /** Cut array to specified length, remove unused elements
     *
     * @param array: original array
     * @param length: new length
     * @return new array
     */
    public static String[] cutArray(String[] array, int length) {
        String[] cutArray = new String[length];
        for (int i = 0; i < length; i++) {
            cutArray[i] = array[i];
        }

        return cutArray;
    }
}