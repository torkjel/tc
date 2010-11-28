import no.conduct.totalconquest.AbstractTank;
import no.conduct.totalconquest.Direction;

public class GangUpTank extends AbstractTank {

    public GangUpTank() {
    }

    Direction friendLastObserved;

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
            move(Direction.any());
        }
    }

}
