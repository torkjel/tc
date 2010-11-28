package no.conduct.totalconquest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Enum som beskriver retninger det er mulig å se/slå/flytte til.
 */
public enum Direction {

    /**
     * North - Opp
     */
    N ( 0, -1),
    /**
     * North-east - Opp til høyre
     */
    NE( 1, -1),
    /**
     * East - Høyre
     */
    E ( 1,  0),
    /**
     * South-east - Ned til hjøyre
     */
    SE( 1,  1),
    /**
     * South - Ned
     */
    S ( 0,  1),
    /**
     * South-west - Ned til venstre
     */
    SW(-1,  1),
    /**
     * West - Venstre
     */
    W (-1,  0),
    /**
     * North-west - Opp til venstre
     */
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

    /**
     * Finn andre retninger som vil ta deg til en rute som grenser til denne ruten.
     * Resultatet er i tilfelgdig rekkefølge.
     * @return
     */
    public List<Direction> getAdjacentRandomized() {
        return randomize(new ArrayList<Direction>(adjacent));
    }

    /**
     * Finn andre retninger som vil ta deg til en rute som grenser til denne ruten.
     * @return
     */
    public List<Direction> getAdjacent() {
        return adjacent;
    }

    private void setOposite(Direction oposite) {
        this.oposite = oposite;
    }

    /**
     * Finn den motsatte retningen.
     * {@link #N} returnerer {@link #S}, {@link #NE} returnerer {@link #SW}, etc.
     * @return
     */
    public Direction getOposite() {
        return oposite;
    }

    private void setPerpendicular(Direction ... perpendicular) {
        this.perpendicular = Arrays.asList(perpendicular);
    }

    /**
     * Hent retnigne som er vinkelrett på denne.
     * {@link #N} returnerer {@link #E} og {@link #W}, {@link #NE} returnerer {@link #SE} og {@link #NW}, etc.
     * @return
     */
    public List<Direction> getPerpendicular() {
        return perpendicular;
    }

    static void initDirections() {
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

    /**
     * Hent alle retninger, som en liste.
     */
    public static List<Direction> valuesList() {
        return new ArrayList<Direction>(Arrays.asList(values()));
    }

    /**
     * Hent en tilfeldig retning
     */
    public static Direction any() {
        return anyOf(valuesList());
    }

    /**
     * Hent alle retninger, i tilfeldig rekkefølge.
     */
    public static List<Direction> randomOrder() {
        List<Direction> ordered = new ArrayList<Direction>();
        for (Direction d : values())
            ordered.add(d);
        return randomize(ordered);
    }

    static Direction anyOf(List<Direction> directions) {
        if (directions != null && directions.size() > 0)
            return directions.get(RANDOM.nextInt(directions.size()));
        else
            return null;
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
