package src.Objects;

public class PossibleMove {
    private int numberOfMoves;
    private String moves;
    private int[] startSpike = new int[4];
    private int[] roll = new int[4];
    private moveType[] type = new moveType[4];

    public PossibleMove() {
        this.numberOfMoves = 0;
        this.moves = ">";
    }

    public void add(int start, int roll, moveType t, byte player) {
        this.numberOfMoves++;
        this.roll[numberOfMoves] = roll;
        this.type[numberOfMoves] = t;
        this.startSpike[numberOfMoves] = start;

        int displaySrt;
        int displayEnd;

        if(player == 0) {
            displaySrt = start;
            displayEnd = start + roll;
        } else {
            displaySrt = 25 - start;
            displayEnd = 25 - start + roll;
        }

        if (t == moveType.Normal)
            this.moves += " " + displaySrt + "-" + displayEnd;
        else if (t == moveType.Hit)
            this.moves += " " + displaySrt + "-" + displayEnd + "*";
        else if (t == moveType.BearOff)
            this.moves += " " + (start) + "-" + "B";
    }

    public void clone(PossibleMove original) {
        this.numberOfMoves = original.numberOfMoves;
        this.moves = original.moves;
        this.startSpike = original.startSpike;
        this.roll = original.roll;
        this.type = original.type;
    }

    public String getMoves() { return this.moves; }
    public int getNumberOfMoves() { return this.numberOfMoves; }
}
