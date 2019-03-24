package src.Objects;


public class PossibleMove {
    private int numberOfMoves;
    private String moves;

    public PossibleMove() {
        this.numberOfMoves = 0;
        this.moves = "";
    }

    public void add(int start, int roll, moveType t,byte player) {
        this.numberOfMoves++;

        if (player == 1) {
            start = 25 - start;
            roll = -roll;
        }

        if (t == moveType.Normal)
            this.moves += (start) + "-" + (start + roll) + " ";
        else if (t == moveType.Hit)
            this.moves += (start) + "-" + (start + roll) + "* ";
        else if (t == moveType.BearOff)
            this.moves += (start) + "-" + "B ";
    }

    public void clone(PossibleMove original) {
        this.numberOfMoves = original.numberOfMoves;
        this.moves = original.moves;
    }

    public String getMoves() { return this.moves; }
    public int getNumberOfMoves() { return this.numberOfMoves; }

    public int getStartSpike(int i) {
        String m[] = this.moves.split(" ");
        String n[] = m[i].split("-");

        return Integer.parseInt(n[0]);
    }

    public int getEndSpike(int i) {
        String m[] = this.moves.split(" ");
        String n[] = m[i].split("-");
        String out = n[1];

        String last = n[1].substring(n[1].length() - 1);
        System.out.println(" Last Char: " + last);
        if (last.equals("*")) {
            out = n[1].substring(0, n[1].length() - 1);
        } else if (last.equals("B")) {
            return 25;
        }

        return Integer.parseInt(out);
    }

    public moveType getMoveType(int i) {
        String m[] = this.moves.split(" ");
        String n[] = m[i].split("-");
        String out = n[1];

        String last = n[1].substring(n[1].length() - 1);
        System.out.println(" Last Char: " + last);
        if (last.equals("*")) {
            return moveType.Hit;
        } else if (last.equals("B")) {
            return moveType.BearOff;
        } else {
            return moveType.Normal;
        }
    }
}
