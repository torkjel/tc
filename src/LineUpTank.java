import java.util.Random;

import no.conduct.totalconquest.AbstractTank;
import no.conduct.totalconquest.Direction;

public class LineUpTank extends AbstractTank {

    public LineUpTank() {
    }

    Random r = new Random();
    Direction friendLastObserved;

    enum State {
        SEEK_FRIENDS,
        WAITING,
        LEFT,
        RIGHT,
        FIGHT,
        TRAVEL;
    }

    private State state = State.SEEK_FRIENDS;
    private Direction dir;

    private void fight() {
        if (getEnemyPositions().size() > 1) {
            move(getEnemyPosition().getOposite());
        } else {
            hit(getEnemyPosition());
        }
    }

    @Override
    public void go() {
        if (dir == null) {
            dir = Direction.N; //Math.random() < 0.5 ? Direction.N : Direction.W;
            state = State.TRAVEL;
        }

        if (isInFight()) {
            fight();
            return;
        }

        switch (state) {
        case TRAVEL:
            if (isWall(dir)) {
                dir = dir.getOposite();
                move(dir);
            } else if (isFriend(dir)) {
                Direction d = null;
                for (Direction perp : dir.getPerpendicular())
                    if (isFree(perp))
                        d = perp;
                if (d != null) {
                    dir = dir.getOposite();
                    move(d);
                }
            } else {
                move(dir);
            }
            break;
        default:
            break;
        }
    }
}
