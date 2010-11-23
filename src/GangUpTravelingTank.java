import java.util.Random;

import no.conduct.totalconquest.Direction;
import no.conduct.totalconquest.Tank;


public class GangUpTravelingTank extends Tank {

    public GangUpTravelingTank() {
    }

    Random r = new Random();
    Direction friendLastObserved;
    Direction prefDir = Math.random() > 0.2 ? Direction.E : Direction.W;

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

    @Override
    public void go() {
        if (isInFight()) {
            hit(getEnemyPosition());
        } else  if (isWithFriends()) {
            friendLastObserved = getFriendPosition();
            Direction dir = getAlternativePosition();
            if (dir != null)
                move(dir);
        } else if (friendLastObserved != null) {
            move(friendLastObserved);
            friendLastObserved = null;
        } else {
            if (isFoe(prefDir))
                move(prefDir);
            else {
                if (isWall(prefDir))
                    prefDir = prefDir.getOposite();
                move(prefDir);
//                move(Direction.randomOrder().get(0));
            }
        }
    }

}
