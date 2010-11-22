import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import no.conduct.totalconquest.Direction;
import no.conduct.totalconquest.Tank;


public class LineUpTank extends Tank {

    public LineUpTank() {
    }

    @Override
    public Color getColor() {
        return Color.CYAN;
    }

    Random r = new Random();
    Direction friendLastObserved;

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


/*        if (state == State.SEEK_FRIENDS) {
            if (isFree(Direction.W)) {
                move(Direction.W);
            } else if (isFoe(Direction.W)) {
                hit(Direction.W);
            } else if (isWall(Direction.W) && isFoe(Direction.N)) {
                hit(Direction.N);
            } else if (isWall(Direction.W) && isFree(Direction.N)) {
                move(Direction.N);
            } else if (isWall(Direction.W) && isFriend(Direction.N)) {
                state = State.WAITING;
            }
        } else if (state == State.WAITING) {
            if (isFree(Direction.S) || isFoe(Direction.S) && !isFriend(Direction.SE)) {
                if (r.nextInt(5) == 1) {
                    move(Direction.E);
                    state = State.RIGHT;
                }
            } else if (isFriend(Direction.SE)) {
                move(Direction.E);
                state = State.RIGHT;
            }
        } else if (state == State.RIGHT) {
            if (isWall(Direction.E) || isFriend(Direction.E)) {
                if (isFoe(Direction.W))
                    move(Direction.W);
                else
                    hit(Direction.W);
                state = State.LEFT;
            } else
                move(Direction.E);
        } else if (state == State.LEFT) {
            if (isWall(Direction.W)) {
                move(Direction.S);
                state = State.SEEK_FRIENDS;
            } else
                move(Direction.W);
        }

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
            move(Direction.randomOrder().get(0));
        }
        */
    }
}
