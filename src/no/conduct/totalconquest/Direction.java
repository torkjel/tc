package no.conduct.totalconquest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum Direction {

    N ( 0, -1),
    NE( 1, -1),
    E ( 1,  0),
    SE( 1,  1),
    S ( 0,  1),
    SW(-1,  1),
    W (-1,  0),
    NW(-1, -1);

    private static Random RANDOM = new Random();

    private int dx, dy;
    private List<Direction> adjacent = new ArrayList<Direction>();
    private List<Direction> perpendicular = new ArrayList<Direction>();
    private Direction oposite;

    private Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    int getX() {
        return dx;
    }

    int getY() {
        return dy;
    }

    private void setAdjacent(Direction ... adjacent) {
        for (Direction adj : adjacent) {
            this.adjacent.add(adj);
        }
    }

    public List<Direction> getAdjacentRandomized() {
        return randomize(new ArrayList<Direction>(adjacent));
    }

    public List<Direction> getAdjacent() {
        return adjacent;
    }

    public void setOposite(Direction oposite) {
        this.oposite = oposite;
    }

    public Direction getOposite() {
        return oposite;
    }

    public static void initAdjacent() {
        N.setAdjacent(NW, NE);
        NE.setAdjacent(N, E);
        E.setAdjacent(NE, SE);
        SE.setAdjacent(S, E);
        S.setAdjacent(SE, SW);
        SW.setAdjacent(S, W);
        W.setAdjacent(SW, NW);
        NW.setAdjacent(W, N);

        N.setOposite(S);
        NE.setOposite(SW);
        E.setOposite(W);
        SE.setOposite(NW);
        S.setOposite(N);
        SW.setOposite(NE);
        W.setOposite(E);
        NW.setOposite(SE);

        N.setPerpendicular(E, W);
        NE.setPerpendicular(NW, SE);
        E.setPerpendicular(N, S);
        SE.setPerpendicular(NE, SW);
        S.setPerpendicular(E, W);
        SW.setPerpendicular(SE, NW);
        W.setPerpendicular(N, E);
        NW.setPerpendicular(NE, SW);

    }

    public static List<Direction> valuesList() {
        return new ArrayList<Direction>(Arrays.asList(values()));
    }

    public static Direction any() {
        return anyOf(valuesList());
    }

    public static List<Direction> randomOrder() {
        List<Direction> ordered = new ArrayList<Direction>();
        for (Direction d : values())
            ordered.add(d);
        return randomize(ordered);
    }

    public static Direction anyOf(List<Direction> directions) {
        if (directions != null && directions.size() > 0)
            return directions.get(RANDOM.nextInt(directions.size()));
        else
            return null;
    }

    private void setPerpendicular(Direction ... perpendicular) {
        this.perpendicular = Arrays.asList(perpendicular);
    }

    public List<Direction> getPerpendicular() {
        return perpendicular;
    }

    private static List<Direction> randomize(List<Direction> ordered) {
        List<Direction> randomized = new ArrayList<Direction>();
        while (ordered.size() > 0) {
            int index = RANDOM.nextInt(ordered.size());
            Direction d = ordered.get(index);
            ordered.remove(index);
            randomized.add(d);
        }
        return randomized;
    }

}
