package dhs.challenges.hard.enigma;

import java.util.HashMap;

public class Reflector {
    private final HashMap<Character, Character> reflector;
    private static Reflector instance = null;

    private Reflector() {
        reflector = new HashMap<>();
        reflector.put('A', 'I');
        reflector.put('B', 'D');
        reflector.put('C', 'P');
        reflector.put('D', 'B');
        reflector.put('E', 'H');
        reflector.put('F', 'Q');
        reflector.put('G', 'M');
        reflector.put('H', 'E');
        reflector.put('I', 'A');
        reflector.put('J', 'N');
        reflector.put('K', 'O');
        reflector.put('L', 'W');
        reflector.put('M', 'G');
        reflector.put('N', 'J');
        reflector.put('O', 'K');
        reflector.put('P', 'C');
        reflector.put('Q', 'F');
        reflector.put('R', 'Z');
        reflector.put('S', 'X');
        reflector.put('T', 'V');
        reflector.put('U', 'Y');
        reflector.put('V', 'T');
        reflector.put('W', 'L');
        reflector.put('X', 'S');
        reflector.put('Y', 'U');
        reflector.put('Z', 'R');
    }

    public static Reflector getInstance() {
        if (instance == null) instance = new Reflector();
        return instance;
    }

    public char getReflection(char c) {
        if (instance == null) getInstance();
        return reflector.get(c);
    }
}
