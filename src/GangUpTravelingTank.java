import no.conduct.totalconquest.AbstractTank;
import no.conduct.totalconquest.Direction;

public class GangUpTravelingTank extends AbstractTank {

    public GangUpTravelingTank() {
    }

    Direction friendLastObserved;
    Direction prefDir = Math.random() > 0.2 ? Direction.E : Direction.W;

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
            }
        }
    }
}
