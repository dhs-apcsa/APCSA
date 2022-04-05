package dhs.challenges.hard.enigma;

import java.util.Scanner;

public abstract class Offsets {
    public static final char[] ALPHABET = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static int getOffset(char from, char to) {
        int f = getIndex(from);
        int t = getIndex(to);
        return t - f;
    }

    public static int getIndex(char c) {
        for (int i = 0; i < ALPHABET.length; i++)
            if (c == ALPHABET[i]) return i;
        return Integer.MIN_VALUE;
    }

    public static void doOffsets() {
        Scanner sc = new Scanner(System.in);

        do {
            System.out.print("> ");
            char c1 = sc.next().toUpperCase().charAt(0);
            char c2 = sc.next().toUpperCase().charAt(0);
            System.out.println(getOffset(c1, c2));
        } while (true);
    }

    public static String stripString(String s) {
        s = s.toUpperCase();
        return s.replaceAll("[^A-Z]", "");
    }
}
