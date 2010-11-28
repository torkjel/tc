package no.conduct.totalconquest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Battlefield {

    private TankBase[][] grid;
    private int width, height;
    private Set<String> teams = new HashSet<String>();
    private Map<String, Integer> score = new HashMap<String, Integer>();

    private Callback callback;

    private Random random = new Random(314);

    private Set<TankBase> tanks = new HashSet<TankBase>();

    public Battlefield(int width, int height) {
        this.grid = new TankBase[width][height];
        this.width = width;
        this.height = height;
    }

    private void clear() {
        this.grid = new TankBase[width][height];
        teams.clear();
        score.clear();
        tanks.clear();
    }

    public void init() {
        clear();
        for (int n = 0; n < TC.CONFIG.getTeamCount(); n++) {
            addTeam(TC.CONFIG.getTankType(n), TC.CONFIG.getTeamSize());
        }
    }

    public void setUpdateCallback(Callback callback) {
        this.callback = callback;
    }

    public void addTank(Class<? extends TankBase> tankType) {
        int x, y;
        do {
            x = random.nextInt(width);
            y = random.nextInt(height);
        } while (grid[x][y] != null);
        TankBase tank;
        try {
            tank = tankType.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        grid[x][y] = tank;
        tanks.add(tank);
        tank.setPosition(x, y);
        tank.setBattlefield(this);
    }

    public void addTeam(Class<? extends TankBase> tankType, int count) {
        while (count --> 0) {
            int x, y;
            do {
                x = random.nextInt(width);
                y = random.nextInt(height);
            } while (grid[x][y] != null);
            TankBase tank;
            try {
                tank = tankType.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            teams.add(tank.getTeamName());
            score.put(tank.getTeamName(), 0);
            grid[x][y] = tank;
            tanks.add(tank);
            tank.setPosition(x, y);
            tank.setBattlefield(this);
        }
    }

    public TankBase get(int x, int y) {
        return grid[x][y];
    }

    public TankBase set(TankBase tank, int x, int y) {
        return grid[x][y] = tank;
    }

    public void remove(TankBase t) {
        tanks.remove(t);
        grid[t.getXPosition()][t.getYPosition()] = null;
        t.setBattlefield(null);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Set<String> getTeams() {
        return teams;
    }

    public void decScore(String team) {
        score.put(team, score.get(team) - 1);
    }

    public void incScore(String team) {
        score.put(team, score.get(team) + 1);
    }

    public int getScore(String team) {
        return score.get(team);
    }

    private Thread t;
    private boolean running, paused;

    public void start() {
        running = true;
        paused = false;
        t = new Thread(new Runnable() {

            @Override
            public void run() {
                while (running) {
                    long time = System.currentTimeMillis();
                    if (paused)
                        continue;
                    for (TankBase t : new HashSet<TankBase>(tanks)) {
                        if (t.getEnergy() <= 0)
                            continue;
                        try {
                            t.newRound();
                            t.go();
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.err.println(t + " threw exception and must die!");
                            t.die();
                        }
                    }
                    callback.update();
                    time = System.currentTimeMillis() - time;
                    if (time < TC.CONFIG.getSpeed()) {
                        try { Thread.sleep(TC.CONFIG.getSpeed()); } catch (InterruptedException e) { }
                    }
                }
            }

        });
        t.start();
    }

    public void pause() {
        paused = !paused;
        System.out.println(paused);
    }

    public void stop() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isPaused() {
        return running && paused;
    }

}
