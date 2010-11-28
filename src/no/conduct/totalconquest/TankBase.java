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

    public abstract void go();

    public final int getEnergy() {
        return energy;
    }

    public final String getTeamName() {
        return teamName;
    }

    public final Sample sense(Direction direction) {
        return sense(direction.getX(), direction.getY());
    }

    public final Sample sense(int leftRight, int upDown) {
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

    public final void hit(Direction dir) {
        hit(dir.getX(), dir.getY());
    }

    public final void hit(int leftRight, int upDown) {
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

    public final boolean move(Direction direction) {
        return move(direction.getX(), direction.getY());
    }

    public final boolean move(int leftRight, int upDown) {
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

    private void validateMove() {
        if (hasMoved) {
            throw new RuntimeException("This tank '" + this + "' has laready moved this round");
        }
        hasMoved = true;
    }

    final void newRound() {
        hasMoved = false;
    }

    private int clamp(int value) {
        if (value > 1)
            value = 1;
        else if (value < -1)
            value = -1;
        return value;
    }

}
