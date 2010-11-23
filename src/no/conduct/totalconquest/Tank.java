package no.conduct.totalconquest;

public abstract class Tank {

    private int energy;
    private Battlefield battlefield;
    private int x, y;
    private String teamName;

    protected Tank() {
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

    final void hit() {
        energy -= TC.CONFIG.getHitDamage();
        battlefield.decScore(getTeamName());
        if (energy <= 0) {
            battlefield.addTank(this.getClass());
            battlefield.remove(this);
        }
    }

    public abstract void go();

    public final int getEnergy() {
        return energy;
    }

    public final String getTeamName() {
        return teamName;
    }

    public final String sense(Direction direction) {
        return sense(direction.getX(), direction.getY());
    }

    public final String sense(int leftRight, int upDown) {
        leftRight = clamp(leftRight);
        upDown = clamp(upDown);
        int sx = x + leftRight;
        int sy = y + upDown;
        if (sx < 0 || sx >= battlefield.getWidth() || sy < 0 || sy >= battlefield.getHeight())
            return "EDGE";
        else {
            Tank tank = battlefield.get(sx, sy);
            if (tank == null)
                return null;
            else
                return tank.getTeamName();
        }
    }

    public final void hit(Direction dir) {
        hit(dir.getX(), dir.getY());
    }


    public final void hit(int leftRight, int upDown) {
        leftRight = clamp(leftRight);
        upDown = clamp(upDown);
        int sx = x + leftRight;
        int sy = y + upDown;
        if (sx >= 0 && sx < battlefield.getWidth() && sy >= 0 && sy < battlefield.getHeight()) {
            Tank enemy = battlefield.get(sx, sy);
            if (enemy != null) {
                enemy.hit();
                battlefield.incScore(getTeamName());
            }
        }
    }

    public final boolean move(Direction direction) {
        return move(direction.getX(), direction.getY());
    }

    public final boolean move(int leftRight, int upDown) {
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
        return "Tank:" + getTeamName();
    }

    private int clamp(int value) {
        if (value > 1)
            value = 1;
        else if (value < -1)
            value = -1;
        return value;
    }

}
