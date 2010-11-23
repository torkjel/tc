package no.conduct.totalconquest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Battlefield {

    private Tank[][] grid;
    private int width, height;
    private Set<String> teams = new HashSet<String>();
    private Map<String, Integer> score = new HashMap<String, Integer>();

    private Callback callback;

    private Random random = new Random(314);

    private Set<Tank> tanks = new HashSet<Tank>();

    public Battlefield(int width, int height) {
        this.grid = new Tank[width][height];
        this.width = width;
        this.height = height;
    }

    public void setUpdateCallback(Callback callback) {
        this.callback = callback;
    }

    public void addTeam(Class<? extends Tank> tankType, int count) {
        while (count --> 0) {
            int x, y;
            do {
                x = random.nextInt(width);
                y = random.nextInt(height);
            } while (grid[x][y] != null);
            Tank tank;
            try {
                tank = tankType.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            teams.add(tank.getTeamName());
            score.put(tank.getTeamName(), 0);
            grid[x][y] = tank;
            tanks.add(tank);
            tank.setId(count);
            tank.setPosition(x, y);
            tank.setBattlefield(this);
        }
    }

    public Tank get(int x, int y) {
        return grid[x][y];
    }

    public Tank set(Tank tank, int x, int y) {
        return grid[x][y] = tank;
    }

    public void remove(Tank t) {
        tanks.remove(t);
        grid[t.getXPosition()][t.getYPosition()] = null;
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
                    try { Thread.sleep(300); } catch (InterruptedException e) { }
                    if (paused)
                        continue;
                    for (Tank t : new HashSet<Tank>(tanks)) {
                        try {
                            t.go();
                        } catch (Exception e) {
                            System.err.println(t + " threw exception and must die!");
                            e.printStackTrace();
                        }
                    }
                    callback.update();
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
