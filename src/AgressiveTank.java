import no.conduct.totalconquest.AbstractTank;
import no.conduct.totalconquest.Direction;

/**
 * Tank som angriper alle fiender den ser, og prøver å følge etter fiender som stikker av.
 */
public class AgressiveTank extends AbstractTank {

    public AgressiveTank() {
    }

    Direction lastObserved;

    @Override
    public void go() {
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
