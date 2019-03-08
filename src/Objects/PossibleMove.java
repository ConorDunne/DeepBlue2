package src.Objects;

public class PossibleMove {
    private int numberOfMoves;
    private String moves;
    private int[] startSpike = new int[4];
    private int[] roll = new int[4];
    private moveType[] type = new moveType[4];

    public PossibleMove() {
        numberOfMoves = 0;
        moves = ">";
    }

    public void addMove(int start, int roll, moveType t) {
        this.startSpike[numberOfMoves] = start;
        this.roll[numberOfMoves] = roll;
        this.type[numberOfMoves] = t;
        this.numberOfMoves++;

        moves += " " + start + "-" + (start + roll);
    }

    public String getMoves() { return this.moves; }
    public int getNumberOfMoves() { return this.numberOfMoves; }
    public int getStartSpike (int i) { return this.startSpike[i]; }
    public int getRoll (int i) { return this.roll[i]; }
    public moveType getMoveType (int i) { return this.type[i]; }
}
