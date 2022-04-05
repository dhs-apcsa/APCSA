package dhs.challenges.hard.enigma;

public class Enigma {
    private String message, encrypted;
    private Rotor[] rotors;
    private final Reflector reflector;

    private boolean hasMessage, hasRotors;

    // debugs
    private boolean showPath = false;
    private boolean addSpaces = false;
    private boolean showRotations = false;

    public Enigma() {
        message = "";
        encrypted = "";
        reflector = Reflector.getInstance();
    }

    public Enigma(int[] rot, int[] pos, String message) {
        this.message = message.toUpperCase();
        encrypted = "";
        rotors = new Rotor[rot.length];
        setRotors(rot, pos);
        reflector = Reflector.getInstance();
        hasRotors = true;
        hasMessage = true;
    }

    public void changeConfig(int[] rot, int[] pos) {
        rotors = new Rotor[rot.length];
        setRotors(rot, pos);
    }

    public void setRotors(int[] rot) { setRotors(rot, new int[]{0,0,0}); }
    public void setRotors(int[] rot, int[] pos) {
        if (rotors == null) rotors = new Rotor[rot.length];

        for (int i = 0; i < rot.length; i++) {
            while (rot[i] > 26) rot[i] -= 26;
            while (rot[i] < 0) rot[i] += 26;
            rotors[i] = new Rotor(rot[i], pos[i]);
        }

        hasRotors = true;
    }

    public void setOffsets(int[] off) {
        if (off.length != rotors.length) {
            System.out.println("Offset count does not match rotor count.");
            return;
        }

        for (int i = 0; i < off.length; i++) rotors[i].changeOffset(off[i]);
    }

    public void encrypt() {
        StringBuilder sb = new StringBuilder();
        message = message.replaceAll("[^A-Z]", "");

        for (int i = 0; i < message.length(); i++) {
            if (addSpaces && i != 0 && i % 5 == 0) sb.append(' ');
            if (showRotations) {
                System.out.println("Rotor Rotations: "
                        + rotors[0].getRotorNum() + "-" + rotors[0].getRotations() + "(" + rotors[0].gettRotations() + "), "
                        + rotors[1].getRotorNum() + "-" + rotors[1].getRotations() + "(" + rotors[1].gettRotations() + "), "
                        + rotors[2].getRotorNum() + "-" + rotors[2].getRotations() + "(" + rotors[2].gettRotations() + ")");
            }

            char c = message.charAt(i);
            if (showPath) System.out.print("[PATH] " + c + " →");

//            for (int j = 0; j < 3; j++) { // rotors 1-3
            for (int j = 0; j < rotors.length; j++) {
                c = rotors[j].passThrough(c);
                if (showPath) System.out.print(" (" + rotors[j].getRotorNum() + ")" + c + " →");
            }

            c = reflector.getReflection(c);
            if (showPath) System.out.print(" (R)" + c + " →");

//            for (int j = 2; j > -1; j--) { // rotors 3-1
            for (int j = rotors.length - 1; j > -1; j--) {
                c = rotors[j].passBack(c);
                if (showPath) System.out.print(" (" + rotors[j].getRotorNum() + ")" + c + " →");
            }

            if (showPath) System.out.println(" [FIN] " + c);
            sb.append(c);

            // rotate rotors
            rotors[2].rotate();
            if (rotors[2].getRotations() != 0 && rotors[2].getRotations() % 26 == 0) rotors[1].rotate();
            if (rotors[2].getRotations() % 676 == 0) rotors[0].rotate();

            if (showRotations) System.out.println();
        }

        encrypted = sb.toString();
    }

    public void swapOffsets(int rotNum, char c1, char c2) {
        rotNum--;
        if (rotNum > rotors.length || rotNum < 0) return;
        rotors[rotNum].swapOffsets(Character.toUpperCase(c1), Character.toUpperCase(c2));
    }

    public String getMessage() { return message; }
    public void setMessage(String m) {
        if (m.equals("")) {
            hasMessage = false;
            System.out.println("\n[ERROR] Message cannot be blank.");
        }
        message = m.toUpperCase();
        hasMessage = true;
    }
    public String getEncrypted() { return encrypted; }
    public boolean showPath() { return showPath; }
    public void setShowPath(boolean s) { showPath = s; }
    public boolean addSpaces() { return addSpaces; }
    public void setAddSpaces(boolean s) { addSpaces = s; }
    public boolean showRotations() { return showRotations; }
    public void setShowRotations(boolean s) { showRotations = s; }
    public boolean hasMessage() { return hasMessage; }
    public boolean hasRotors() { return hasRotors; }
    public int getNumRotors() { return rotors.length; }

    public void printRotors() {
        System.out.print("Current Rotors: ");
        for (int i = 0; i < rotors.length; i++) {
            System.out.print(rotors[i].getRotorNum());
            if (i != rotors.length - 1) System.out.print(", ");
        }
    }
    public void printOffsets() {
        System.out.print("Current Offsets: ");
        for (int i = 0; i < rotors.length; i++) {
            System.out.print(rotors[i].getOffset());
            if (i != rotors.length - 1) System.out.print(", ");
        }
    }
    public void printEncrypted() { System.out.println(encrypted); }
    public void printMessage() { System.out.println(message); }

}
