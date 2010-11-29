package no.conduct.totalconquest;

import static no.conduct.totalconquest.Sample.*;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTank extends TankBase {

    /**
     * Se hva som befinner seg i den gitte retningen
     */
    public final Sample sense(Direction direction) {
        return sense(direction.getX(), direction.getY());
    }

    /**
     * Slå i den gitte retningen
     */
    public final void hit(Direction dir) {
        hit(dir.getX(), dir.getY());
    }

    /**
     * Flytt i den gitte retningen
     */
    public final boolean move(Direction direction) {
        return move(direction.getX(), direction.getY());
    }

    /**
     * Hent posisjonen til alle synlige fiender
     * @return
     */
    public final List<Direction> getEnemyPositions() {
        return getAll(FOE);
    }

    /**
     * Hent posisjonen til alle synlige venner
     * @return
     */
    public final List<Direction> getFriendPositions() {
        return getAll(FRIEND);
    }

    /**
     * Hent posisjonen til en tilfeldig synlig venn, eller <code>null</code> dersom du er alene.
     * @return
     */
    public final Direction getFriendPosition() {
        return Direction.anyOf(getFriendPositions());
    }

    /**
     * Hent posisjonen til en tilfeldig synlig fiende, eller <code>null</code>.
     * @return
     */
    public final Direction getEnemyPosition() {
        return Direction.anyOf(getEnemyPositions());
    }

    /**
     * Er noen venner synlige?
     */
    public final boolean isWithFriends() {
        return getFriendPosition() != null;
    }

    /**
     * Er noen fiender synlige?
     */
    public final boolean isInFight() {
        return getEnemyPosition() != null;
    }

    /**
     * Kan du se en venn i denne retningen?
     */
    public final boolean isFriend(Direction dir) {
        return sense(dir) == FRIEND;
    }

    /**
     * Kan du se en fiende i denne retningen?
     */
    public final boolean isFoe(Direction dir) {
        return sense(dir) == FOE;
    }

    /**
     * Er ruten i denne retningen ledig?
     */
    public final boolean isFree(Direction dir) {
        return sense(dir) == FREE;
    }

    /**
     * Inneholder ruten i denne retningen en vegg?
     */
    public final boolean isWall(Direction dir) {
        return sense(dir) == WALL;
    }

    private List<Direction> getAll(Sample sample) {
        List<Direction> list = new ArrayList<Direction>();
        for (Direction dir : Direction.values())
            if (sense(dir) == sample)
                list.add(dir);
        return list;
    }

    public abstract void go();
}
