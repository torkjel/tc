import java.awt.Color;
import java.util.Random;

import no.conduct.totalconquest.Tank;


public class StupidTank extends Tank {

    public StupidTank() {
    }

    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    Random r = new Random();

    @Override
    public void go() {
        move(r.nextInt(3) - 1, r.nextInt(3) - 1);
    }

}
