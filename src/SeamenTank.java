import java.util.List;

import no.conduct.totalconquest.AbstractTank;
import no.conduct.totalconquest.Direction;
import no.conduct.totalconquest.Sample;

/**
 * Startpunkt.
 * TODO: Kall om klassen til noe unikt for ditt lag.
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

    public void go2() {
    	nw = sense(Direction.NW);
    	ne = sense(Direction.NE);
    	n = sense(Direction.N);
    	w = sense(Direction.W);
    	se = sense(Direction.SE);
    	sw = sense(Direction.SW);
    	e = sense(Direction.E);
    	s = sense(Direction.S);
    	
    	if (n == Sample.FOE) {
    		hit(Direction.N);
    		return;
    	}
    	if (nw == Sample.FOE) {
    		hit(Direction.N);
    		return;
    	}
    	if (w == Sample.FOE) {
    		hit(Direction.W);
    		return;
    	}
    		
    	List<Direction> enemies = getEnemyPositions();
    	List<Direction> friends = getFriendPositions();
//		System.err.println("E"+enemies.size());
//		System.err.println("F"+friends.size());

		if (enemies.size() > 0) {
			System.err.println("HIT" + enemies.size());
			hit(enemies.get(enemies.size() - 1));
			return;
		}

    	boolean moved = defaultMove();
    	
//    	if (!moved) {
//    		if (enemies.size() > 0) {
//    			System.err.println("HIT" + enemies.size());
//    			hit(enemies.get(enemies.size() - 1));
//    		}
//    	}
    	

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
//        if (lastObserved != null) {
//            move(lastObserved);
//            lastObserved = null;
//        }
//        else
        	defaultMove();
//            move(Direction.any());
    }
    
    
    private boolean defaultMove() {
    	
//    	if (w == Sample.FRIEND && nw == Sample.FRIEND) {
//    		return false;
//    	}
//    	if (nw == Sample.FRIEND && n == Sample.FRIEND) {
//    		return false;
//    	}
    	
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
//    	System.err.println("NOMOVE");
    	return false;
    }
}
