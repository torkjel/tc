import java.util.List;

import no.conduct.totalconquest.AbstractTank;
import no.conduct.totalconquest.Direction;
import no.conduct.totalconquest.Sample;

/**
 * Go north-west!
 */
public class SeamenTank extends AbstractTank {

    Sample nw;
    Sample n;
    Sample w;
    Sample e;
    Sample ne;
    Sample s;
    Sample se;
    Sample sw;
	
    public SeamenTank() {
    }

    public void go() {
        nw = sense(Direction.NW);
        ne = sense(Direction.NE);
        n = sense(Direction.N);
        w = sense(Direction.W);
        se = sense(Direction.SE);
        sw = sense(Direction.SW);
        e = sense(Direction.E);
        s = sense(Direction.S);
    
        List<Direction> enemies = getEnemyPositions();

        if (enemies.size() > 0) {
            hit(enemies.get(enemies.size() - 1));
            return;
        }
        defaultMove();
    }
    
    private boolean defaultMove() {
        if (w == Sample.FRIEND && sw == Sample.FREE) {
            move(Direction.SW);
            return true;
        }
        if (n == Sample.FRIEND && ne == Sample.FREE) {
            move(Direction.NE);
            return true;
        }
        if (nw == Sample.FREE) {
            move(Direction.NW);
            return true;
        }
        if (n == Sample.FREE) {
            move(Direction.N);
            return true;
        }
        if (w == Sample.FREE) {
            move(Direction.W);
            return true;
        }
        return false;
    }
}
