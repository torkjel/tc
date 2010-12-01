import static no.conduct.totalconquest.Direction.E;
import static no.conduct.totalconquest.Direction.N;
import static no.conduct.totalconquest.Direction.S;

import java.util.List;

import no.conduct.totalconquest.AbstractTank;
import no.conduct.totalconquest.Direction;

public class TomtensSpellemannsTank extends AbstractTank {

    boolean foundFriend = false;

    public TomtensSpellemannsTank() {

    }

    @Override
    public void go() {

        Direction p = getFriendPosition();

        List<Direction> ens = getEnemyPositions();

        if (ens.size() > 0) {
            attackSimple();
        } else if (isWallOrFriendClose()) {
            attackSimple();
        } else if (p != null) {
            move(p.getPerpendicular().get((int) Math.random() * 2));
        } else {
            if (isFoe(E))
                hit(E);
            else
                move(E);
        }

    }

    private void attackSimple() {
        List<Direction> ens = getEnemyPositions();

        if (ens.size() > 0) {
            int ind = (int) (Math.random() * ens.size());
            hit(ens.get(ind));
        } else {
            if (isWall(N))
                move(E);
            else if (isWall(E))
                move(N);

        }

    }

    private boolean isWallOrFriendClose() {
        if (isWall(E))
            return true;

        if (isFriend(N))
            return true;

        if (isFriend(S))
            return true;

        return false;

    }

}
