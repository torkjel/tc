import java.util.Random;

import no.conduct.totalconquest.AbstractTank;
import no.conduct.totalconquest.Direction;

/**
 * Tank som angriper alle fiender den ser, og ellers vandrer målløst rundt.
 */
public class StupidTank extends AbstractTank {

    public StupidTank() {
    }

    Random r = new Random();

    @Override
    public void go() {

        Direction dir = Direction.any();
        if (isFoe(dir))
            hit(dir);
        else
            move(dir);
    }

}
