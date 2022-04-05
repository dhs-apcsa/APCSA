package dhs.challenges.hard.enigma;

import java.util.Arrays;

public class Offset {
    private static final boolean DEBUG = false;
    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private int[] offsetsIn;
    private int[] offsetsOut;

    private int rotorNum, rotations, tRotations, offset; // rotorNumber, rotations after initial, total rotations including initial, initial rotation

    public Offset(int offsetNum, int rotation) {
        offsetsIn = defaultOffsetsIn(offsetNum);
        offsetsOut = defaultOffsetsOut(offsetNum);
        rotations = 0;
        tRotations = 0;
        rotorNum = offsetNum;
        offset = rotation;
        if (rotation > 0) rotate(rotation, true);
    }

    // TODO: Allow swapping of offset letters

    private int[] defaultOffsetsIn(int n) {
        return switch (n) {
            //                  A   B   C   D   E   F   G   H   I   J   K   L   M   N   O   P   Q   R   S   T   U   V   W   X   Y   Z
            case 1 -> new int[]{7, -1,  1,  8,  4, -3, -1, -6,  4,  8,  3, -7, -3, -3, -8,  6,  9,  5,  2, -1,  3, -7, -7, -4,  0, -9};
            case 2 -> new int[]{8,  4, -2,  0, -3,  2, -4, -3,  5, -3,  8,  4, -1,  1, -5, -5,  8,  4, -2,  1, -1,  4,  1, -6, -2,-13};
            case 3 -> new int[]{8,  0, -2, -1,  1, -2,  6,  2, -1,  2,  5,  2, -6,  3,  6,-11, -6,  4,  5,  6, -1,  3, -5, -1,-10, -7};
            case 4 -> new int[]{7,  9, -2,  3, -3,  4, -2, -2,  3, -6, -8, -3,  7,  7, -1,  3, -4, -2,  3, -2, -4, -7,  2,  0, -2,  0};
            default -> null;
        };
    }

    private int[] defaultOffsetsOut(int n) {
        return switch (n) {
            //                  A   B   C   D   E   F   G   H   I   J   K   L   M   N   O   P   Q   R   S   T   U   V   W   X   Y   Z
            case 1 -> new int[]{1,  6,  3, -1,  7,  1,  8, -7, -4,  3,  3, -8, -4, -3,  7,  7,  9, -8,  1,  4, -2, -6, -5, -3,  0, -9};
            case 2 -> new int[]{2,  3,  4,  0,  3, -4,  3, -2, -8,  5,  5,  1, 13, -5, -1, -4,  2,  6, -8,  1, -1, -4,  2, -1, -8, -4};
            case 3 -> new int[]{2,  0,  1,  2, 11, -1,  6,  1, -8, -2,  6, -2, -6, -2, 10, -5, -3,  5,  7,  1, -6, -4,  1, -5, -3, -6};
            case 4 -> new int[]{2,  3,  8,  6,  2,  2, -3, -7,  3, -4, -9, -3,  4,  1,  7,  2,  4,  2, -3, -7, -7, -3,  2,  0, -2,  0};
            default -> null;
        };
    }

    public void changeOffset(int o) {
        while (o < 0) o += ALPHABET.length();
        while (o > ALPHABET.length()) o -= ALPHABET.length();

        if (o > 0) {
            rotate(o, true);
            offset = o;
        } else if (o == 0) {
            offsetsIn = defaultOffsetsIn(rotorNum);
            offsetsOut = defaultOffsetsOut(rotorNum);
        }
    }

    public void swapOffsets(char c1, char c2) {
        int i = ALPHABET.indexOf(c1);
        int j = ALPHABET.indexOf(c2);

        int in = offsetsIn[i];
        int out = offsetsOut[i];

        offsetsIn[i] = offsetsIn[j];
        offsetsOut[i] = offsetsOut[j];

        offsetsIn[j] = in;
        offsetsOut[j] = out;
    }

    public void rotate() { rotate(1, false); }
    public void rotate(int times, boolean initRotation) {
        if (DEBUG) {
            debug("Rotor: " + rotorNum + " | Current Rotation: " + rotations);
            debug(" IN: " + Arrays.toString(offsetsIn));
            debug("OUT: " + Arrays.toString(offsetsOut));
        }

        // ensure 'times' is in bounds of the array
        while (times > offsetsIn.length) times -= offsetsIn.length;
        while (times < 0) times += offsetsIn.length;

        int[] t = new int[offsetsIn.length - times];
        int[] u = new int[offsetsOut.length - times];
        System.arraycopy(offsetsIn, 0, t, 0, offsetsIn.length - times);
        System.arraycopy(offsetsOut, 0, u, 0, offsetsOut.length - times);

        for (int i = offsetsIn.length - times; i < offsetsIn.length; i++)
            offsetsIn[i - offsetsIn.length + times] = offsetsIn[i];
        for (int i = offsetsOut.length - times; i < offsetsOut.length; i++)
            offsetsOut[i - offsetsOut.length + times] = offsetsOut[i];

        System.arraycopy(t, 0, offsetsIn, times, offsetsIn.length - times);
        System.arraycopy(u, 0, offsetsOut, times, offsetsOut.length - times);

        if (DEBUG) {
            debug(" IN: " + Arrays.toString(offsetsIn));
            debug("OUT: " + Arrays.toString(offsetsOut));
        }

        if (!initRotation) {
            // inc rotations
            rotations += times;
            tRotations += times;
        }
    }

    public char getOffset(char c, boolean back) {
        int i = ALPHABET.indexOf(c);
        int j;

        if (back) j = offsetsOut[i];
        else j = offsetsIn[i];

        int k = i + j;
        if (k > 25) k -= 26;
        else if (k < 0) k += 26;

        return ALPHABET.charAt(k);
    }


    public int getRotations() { return rotations; }
    public int gettRotations() { return tRotations; }
    public int getOffset() { return offset; }


    private void debug(String msg) {
        System.out.println("\u001B[34m" + "[DEBUG] " + msg + "\u001B[0m");
    }
}
