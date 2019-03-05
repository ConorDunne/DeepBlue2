package src.Objects;

public class possibleMove {
    private int numberOfMoves;
    private String moves;

    public possibleMove() {
        numberOfMoves = 0;
        moves = ">";
    }

    public void addMove(int start, int end, moveType t) {
        numberOfMoves++;

        moves += " " + start + "-" + end;
    }

    public String getMoves() { return moves; }
    public int getNumber() { return numberOfMoves; }
}
