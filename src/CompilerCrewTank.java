
import no.conduct.totalconquest.AbstractTank;
import no.conduct.totalconquest.Direction;

/**
 * Tank med følgende stratgi:
 * <ul>
 * <li> Dersom den ser en enkelt fiende, angrip den.
 * <li> Dersom den ser mer enn en fiende, flykt.
 * <li> Dersom ingen fiende er synlig, gå dit hvor vi sist så en fiende.
 * </ul>
 */
public class CompilerCrewTank extends AbstractTank {

    public CompilerCrewTank() {
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
        if (getEnemyPositions().size() - getFriendPositions().size() > 1) {
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
