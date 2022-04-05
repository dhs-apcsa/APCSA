package dhs.challenges.hard.enigma;

import java.util.Scanner;

public class Console {
    private static Console console = null;
    private Enigma enigma;

    private Scanner sc;
    private String in;

    private Console() {
        sc = new Scanner(System.in);
        enigma = new Enigma();
        in = "";
        start();
    }

    public static Console getConsole() {
        if (console == null) console = new Console();
        return console;
    }

    private void printMenu() {
        System.out.println("1. Set Message");
        System.out.println("2. Set Rotors");
        System.out.println("3. Set Offsets");
        System.out.println("4. Print Encrypted");
        System.out.println("5. Print Message");
        System.out.println("6. Print Config");

        System.out.println("0. Exit");

        System.out.print("\n> ");
    }

    private int execute(char c) {
        int choice;
        try {
            choice = Integer.parseInt(String.valueOf(c));
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice. Please select what you would like to do by the number in front of the option.");
            return 0;
        }

        switch (choice) {
            case 0: return -1;
            case 1: setMessage(); break;
            case 2: setRotors(); break;
            case 3: setOffsets(); break;
            case 4: doEncrypt(); break;
            case 5: enigma.printMessage(); break;
            case 6: printConfig(); break;
            default: System.out.println("Invalid choice. Please select what you would like to do by the number in front of the option."); return 0;
        }

        System.out.println();
        return choice;
    }

    private void setRotors() {
        sc.nextLine();
        System.out.println("\nWhat rotors would you like to use? Please enter only numbers separated by a space.");
//        int a, b, c;
        String[] rots = sc.nextLine().trim().split(" ");
        int[] rotsI = new int[rots.length];

        try {
//            a = Integer.parseInt(sc.next());
//            b = Integer.parseInt(sc.next());
//            c = Integer.parseInt(sc.next());
            for (int i = 0; i < rots.length; i++) rotsI[i] = Integer.parseInt(rots[i]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid entries. Please only enter numbers.");
            return;
        }

//        enigma.setRotors(new int[]{a, b, c});
        enigma.setRotors(rotsI);
        System.out.println("Rotors set.");
        enigma.printRotors();
        System.out.println("\n");
    }

    private void setOffsets() {
        if (!enigma.hasRotors()) {
            System.out.println("\nRotors have not been set. Cannot change starting offsets.\n");
            return;
        }
        sc.nextLine();
        System.out.println("\nWhat starting offsets would you like to use? Please enter only numbers separated by a space.\n");
//        int a, b, c;
        String[] rots = sc.nextLine().trim().split(" ");
        int[] rotsI = new int[rots.length];

        try {
//            a = Integer.parseInt(sc.next());
//            b = Integer.parseInt(sc.next());
//            c = Integer.parseInt(sc.next());
            for (int i = 0; i < rots.length; i++) rotsI[i] = Integer.parseInt(rots[i]);
        } catch (NumberFormatException e) {
            System.out.println("\nInvalid entries. Please only enter numbers.\n");
            return;
        }

        // ensure the offsets and rotors have the same number
        if (rotsI.length != enigma.getNumRotors()) {
            System.out.println("\nInvalid number of entries. You have " + enigma.getNumRotors() + " rotors and " + rotsI.length + " offsets.");
            return;
        }

//        enigma.setOffsets(new int[]{a, b, c});
        enigma.setRotors(rotsI);
        System.out.println("Offsets set.");
        enigma.printOffsets();
        System.out.println("\n");
    }

    private void printConfig() {
        if (!enigma.hasRotors()) {
            System.out.println("Enigma currently does not have any configurations.");
            return;
        }

        enigma.printRotors();
        System.out.println();
        enigma.printOffsets();
        System.out.println();
    }

    private void setMessage() {
        sc.nextLine();
        System.out.print("\nWhat is the message you would like to use?\n> ");
        in = sc.nextLine();
        enigma.setMessage(in);
        System.out.println();
    }

    private void doEncrypt() {
        if (!enigma.hasMessage()) {
            System.out.println("Cannot Encrypt! There's no message to encrypt!\n");
            return;
        }

        if (!enigma.hasRotors()) {
            System.out.println("The rotors have not been set! Defaulting to Rotors 1, 2, 3 with no offset...\n");
            enigma.setRotors(new int[]{1,2,3});
        }

        enigma.encrypt();
        enigma.printEncrypted();
        System.out.println();
    }

    public void start() {
        System.out.println("+-------------------+");
        System.out.println("| Welcome to Enigma |");
        System.out.println("+-------------------+");

        do {
            printMenu();
        } while (execute(sc.next().charAt(0)) != -1);
    }
}
