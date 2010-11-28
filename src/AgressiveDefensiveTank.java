
import no.conduct.totalconquest.AbstractTank;
import no.conduct.totalconquest.Direction;

public class AgressiveDefensiveTank extends AbstractTank {

    public AgressiveDefensiveTank() {
    }

    Direction lastObserved;

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
        if (getEnemyPositions().size() > 1) {
            Direction safe = getSafePosition();
            if (safe != null)
                move(safe);
            else
                hit(getEnemyPosition());
        } else {
            for (Direction dir : Direction.values()) {
                if (isFoe(dir)) {
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
                move(Direction.any());
        }
    }

}
