import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import no.conduct.totalconquest.Direction;
import no.conduct.totalconquest.Tank;


public class AgressiveDefensiveTank extends Tank {

    public AgressiveDefensiveTank() {
    }

    @Override
    public Color getColor() {
        return Color.MAGENTA;
    }

    Random r = new Random();
    Direction lastObserved;

    private boolean isWithFriends() {
        return getFriendPosition() != null;
    }

    private boolean isInFight() {
        return getEnemyPosition() != null;
    }

    private Direction getEnemyPosition() {
        for (Direction dir : Direction.values())
            if (isFoe(dir))
                return dir;
        return null;
    }

    private List<Direction> getEnemyPositions() {
        List<Direction> list = new ArrayList<Direction>();
        for (Direction dir : Direction.values())
            if (isFoe(dir))
                list.add(dir);
        return list;
    }

    private Direction getFriendPosition() {
        for (Direction dir : Direction.randomOrder())
            if (isFriend(dir))
                return dir;
        return null;
    }

    private boolean isFriend(Direction dir) {
        String sample = sense(dir);
        return sample != null && getTeamName().equals(sample);
    }

    private boolean isFoe(Direction dir) {
        String sample = sense(dir);
        return sample != null && !"EDGE".equals(sample) && !getTeamName().equals(sample);
    }

    private boolean isFree(Direction dir) {
        return sense(dir) == null;
    }

    private boolean isWall(Direction dir) {
        return "EDGE".equals(sense(dir));
    }

    private Direction getAlternativePosition() {
        for (Direction dir : Direction.randomOrder())
            if (isFriend(dir))
                for (Direction adj : dir.getAdjacentRandomized())
                    if (isFree(adj))
                        return adj;
        return null;
    }

    private Direction getSafePosition() {
        next: for (Direction safe : Direction.randomOrder()) {
            if (isFree(safe)) {
                for (Direction adj : safe.getAdjacent()) {
                    if (isFoe(adj))
                        break next;
                }
                return safe;
            }
        }
        return null;
    }

    @Override
    public void go() {
        if (getEnemyPositions().size() > 0) {
            Direction safe = getSafePosition();
            if (safe != null)
                move(safe);
            else
                hit(getEnemyPosition());
        } else {

            for (Direction dir : Direction.values()) {
                String sample = sense(dir);
                if (sample != null && !"EDGE".equals(sample) && !getTeamName().equals(sample)) {
                    lastObserved = dir;
                    hit(dir);
                    return;
                }
            }
            if (lastObserved != null) {
                move(lastObserved);
                lastObserved = null;
            }
            else
                move(r.nextInt(3) - 1, r.nextInt(3) - 1);
        }
    }

}
