import java.awt.Color;
import java.util.Random;

import no.conduct.totalconquest.Direction;
import no.conduct.totalconquest.Tank;


public class AgressiveTank extends Tank {

    public AgressiveTank() {
    }

    @Override
    public Color getColor() {
        return Color.YELLOW;
    }

    Random r = new Random();
    Direction lastObserved;

    @Override
    public void go() {
        for (Direction dir : Direction.values()) {
            String sample = sense(dir);
            if (sample != null && !"EDGE".equals(sample) && !getTeamName().equals(sample)) {
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
            move(r.nextInt(3) - 1, r.nextInt(3) - 1);
    }

}
