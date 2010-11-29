package no.conduct.totalconquest;

abstract class TankBase {

    private int energy;
    private Battlefield battlefield;
    private int x, y;
    private String teamName;
    private boolean hasMoved = false;

    protected TankBase() {
        energy = TC.CONFIG.getTankEnergy();
        this.teamName = getClass().getSimpleName();
    }

    final void setBattlefield(Battlefield battlefield) {
        this.battlefield = battlefield;
    }

    final void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    final int getXPosition() {
        return x;
    }

    final int getYPosition() {
        return y;
    }

    /**
     * Denne skal du implementere :)
     */
    public abstract void go();

    /**
     * Hent gjennstående energi for denne tanken.
     * En tank starter med 10 og dør når den har 0 i energi. Hver gang en tank blir slått mister
     * den et energi-poeng.
     * @return
     */
    final int getEnergy() {
        return energy;
    }

    /**
     * Hent navnet til denne tankens lag. Matcher klassenavnet til styringsprogrammet.
     * @return
     */
    final String getTeamName() {
        return teamName;
    }

    /**
     * Se hva som befinner seg i den gitte retningen
     */
    final Sample sense(int leftRight, int upDown) {
        leftRight = clamp(leftRight);
        upDown = clamp(upDown);
        int sx = x + leftRight;
        int sy = y + upDown;
        if (sx < 0 || sx >= battlefield.getWidth() || sy < 0 || sy >= battlefield.getHeight())
            return Sample.WALL;
        else {
            TankBase tank = battlefield.get(sx, sy);
            if (tank == null)
                return Sample.FREE;
            else
                return tank.getTeamName().equals(getTeamName())
                    ? Sample.FRIEND
                    : Sample.FOE;
        }
    }

    /**
     * Slå i den gitte retningen
     */
    final void hit(int leftRight, int upDown) {
        validateMove();
        leftRight = clamp(leftRight);
        upDown = clamp(upDown);
        int sx = x + leftRight;
        int sy = y + upDown;
        if (sx >= 0 && sx < battlefield.getWidth() && sy >= 0 && sy < battlefield.getHeight()) {
            TankBase enemy = battlefield.get(sx, sy);
            if (enemy != null) {
                enemy.receiveHit();
                battlefield.incScore(getTeamName());
            }
        }
    }

    private void receiveHit() {
        energy -= TC.CONFIG.getHitDamage();
        battlefield.decScore(getTeamName());
        if (energy <= 0) {
            battlefield.addTank(this.getClass());
            battlefield.remove(this);
        }
    }

    /**
     * Flytt i den gitte retningen
     */
    final boolean move(int leftRight, int upDown) {
        validateMove();
        leftRight = clamp(leftRight);
        upDown = clamp(upDown);
        int sx = x + leftRight;
        int sy = y + upDown;
        if (sx < 0 || sx >= battlefield.getWidth() || sy < 0 || sy >= battlefield.getHeight())
            return false;
        else if (battlefield.get(sx, sy) != null)
            return false;
        else {
            battlefield.set(null, x, y);
            battlefield.set(this, sx, sy);
            setPosition(sx, sy);
            return true;
        }
    }

    public final int hashCode() {
        return super.hashCode();
    }

    public final boolean equals(Object o) {
        return super.equals(o);
    }

    public final String toString() {
        return "Tank:" + getTeamName() + ":" + super.toString();
    }

    final void die() {
        while (energy > 0)
            receiveHit();
    }

    final void newRound() {
        hasMoved = false;
    }

    private void validateMove() {
        if (hasMoved) {
            throw new RuntimeException("This tank '" + this + "' has laready moved this round");
        }
        hasMoved = true;
    }

    private int clamp(int value) {
        if (value > 1)
            value = 1;
        else if (value < -1)
            value = -1;
        return value;
    }

}
