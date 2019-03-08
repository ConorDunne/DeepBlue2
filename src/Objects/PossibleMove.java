package src.Objects;

public class PossibleMove {
    private int numberOfMoves;
    private String moves;
    private int startSpike;
    private int roll;
    private moveType type;

    public PossibleMove(int start, int roll, moveType t) {
        this.numberOfMoves = 1;
        this.roll = roll;
        this.type = t;
        this.startSpike = start;
        moves = "> " + start + "-" + (start+roll);
    }

    public String getMoves() { return this.moves; }
    public int getNumberOfMoves() { return this.numberOfMoves; }
    public int getStartSpike () { return this.startSpike; }
    public int getRoll () { return this.roll; }
    public moveType getMoveType () { return this.type; }
}
