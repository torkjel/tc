package no.conduct.totalconquest;

import static no.conduct.totalconquest.Sample.*;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTank extends TankBase {

    public List<Direction> getEnemyPositions() {
        return getAll(FOE);
    }

    public List<Direction> getFriendPositions() {
        return getAll(FRIEND);
    }

    public Direction getFriendPosition() {
        return Direction.anyOf(getFriendPositions());
    }

    public Direction getEnemyPosition() {
        return Direction.anyOf(getEnemyPositions());
    }

    public boolean isWithFriends() {
        return getFriendPosition() != null;
    }

    public boolean isInFight() {
        return getEnemyPosition() != null;
    }

    public boolean isFriend(Direction dir) {
        return sense(dir) == FRIEND;
    }

    public boolean isFoe(Direction dir) {
        return sense(dir) == FOE;
    }

    public boolean isFree(Direction dir) {
        return sense(dir) == FREE;
    }

    public boolean isWall(Direction dir) {
        return sense(dir) == WALL;
    }

    private List<Direction> getAll(Sample sample) {
        List<Direction> list = new ArrayList<Direction>();
        for (Direction dir : Direction.values())
            if (sense(dir) == sample)
                list.add(dir);
        return list;
    }

}
