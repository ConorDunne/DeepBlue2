package src.Objects;

public class PossibleMove {
    private int numberOfMoves;
    private String moves;
    private int[] startSpike = new int[4];
    private int[] endSpike = new int[4];
    private moveType[] type = new moveType[4];

    public PossibleMove() {
        this.numberOfMoves = 0;
        this.moves = ">";
    }

    public void add(int start, int roll, moveType t,byte player) {
        this.numberOfMoves++;
        this.endSpike[numberOfMoves] = start + roll;
        this.type[numberOfMoves] = t;
        this.startSpike[numberOfMoves] = start;

        if (player == 1) {
            start = 25 - start;
            roll = -roll;
        }

        if (t == moveType.Normal)
            this.moves += " " + (start) + "-" + (start + roll);
        else if (t == moveType.Hit)
            this.moves += " " + (start) + "-" + (start + roll) + "*";
        else if (t == moveType.BearOff)
            this.moves += " " + (start) + "-" + "B";
    }

    public void clone(PossibleMove original) {
        this.numberOfMoves = original.numberOfMoves;
        this.moves = original.moves;
        this.startSpike = original.startSpike;
        this.endSpike = original.endSpike;
        this.type = original.type;
    }

    public String getMoves() { return this.moves; }
    public int getNumberOfMoves() { return this.numberOfMoves; }

    public int getStartSpike(int i) {
        return this.startSpike[i];
    }

    public int getEndSpike(int i) {
        return endSpike[i];
    }
}
