package dhs.challenges.hard.enigma;

public class Rotor {
    private Offset offset;
    private int rotorNum, rotation;

    public Rotor(int n, int rotation) {
        rotorNum = n;
        this.rotation = rotation;
        offset = new Offset(n, rotation);
    }

    public void rotate() { offset.rotate(); }
    public char passThrough(char c) { return offset.getOffset(c, false); }
    public char passBack(char c) { return offset.getOffset(c, true); }
    public void changeOffset(int o) { offset.changeOffset(o); }
    public void swapOffsets(char c1, char c2) { offset.swapOffsets(c1, c2); }

    public int getRotorNum() { return rotorNum; }
    public int getRotations() { return offset.getRotations(); }
    public int gettRotations() { return offset.gettRotations(); }
    public int getRotation() { return rotation; }
    public int getOffset() { return offset.getOffset(); }
}
