package src.Objects;

public class PossibleMove {
    private int numberOfMoves;
    private String moves;
    private int[] startSpike = new int[4];
    private int[] roll = new int[4];
    private moveType[] type = new moveType[4];
    private int[] endStateOne = new int[26];
    private int[] endStateTwo = new int[26];

    public PossibleMove() {
        this.numberOfMoves = 0;
        this.moves = ">";
    }

    public void add(int start, int roll, moveType t, int[] counter1state, int[] counter2state, byte player) {
        this.numberOfMoves++;
        this.roll[numberOfMoves] = roll;
        this.type[numberOfMoves] = t;
        this.startSpike[numberOfMoves] = start;
        this.endStateOne = counter1state;
        this.endStateTwo = counter2state;


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
        this.roll = original.roll;
        this.type = original.type;
        this.endStateOne = original.endStateOne;
        this.endStateTwo = original.endStateTwo;
    }

    public String getMoves() { return this.moves; }
    public int getNumberOfMoves() { return this.numberOfMoves; }
}
