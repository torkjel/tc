import java.util.Random;

import no.conduct.totalconquest.Tank;


public class StupidTank extends Tank {

    public StupidTank() {
    }

    Random r = new Random();

    @Override
    public void go() {
        move(r.nextInt(3) - 1, r.nextInt(3) - 1);
    }

}
