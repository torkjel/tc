package no.conduct.totalconquest;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class Config {

    private Iterator<Color> avalableColors =
        Arrays.asList(Color.RED, Color.BLUE, Color.GREEN, Color.CYAN, Color.MAGENTA, Color.ORANGE).iterator();

    private Map<String, Color> colors = new HashMap<String, Color>();

    private int speed = 100;

    private Properties properties = new Properties();
    private int teamCount;

    public Config() {
        Reader fr = null;
        try {
            fr = new InputStreamReader(getClass().getResource("/tc.properties").openStream());
            properties.load(fr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try { if (fr != null) fr.close(); } catch (IOException e) { e.printStackTrace(); }
        }
    }

    @SuppressWarnings("unchecked")
    public Config(String ... classNames) {
        this();
        for (String team : classNames) {
            Class<? extends TankBase> teamType;
            try {
                teamType = (Class<TankBase>)getClass().getClassLoader().loadClass(team);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            properties.put("team." + teamCount + ",tanktype", teamType);
            teamCount++;
        }
    }

    public int getTeamCount() {
        return teamCount;
    }

    @SuppressWarnings("unchecked")
    public Class<TankBase> getTankType(int teamNumber) {
        return (Class<TankBase>)properties.get("team." + teamNumber + ",tanktype");
    }

    public int getWindowWidth() {
        return getGridWidth() * getCellSize() + 500;
    }

    public int getWindowHeight() {
        return getGridHeight() * getCellSize() + 100;
    }


    public int getGridWidth() {
        return getIntProperty("grid.width");
    }

    public int getGridHeight() {
        return getIntProperty("grid.height");
    }

    public int getCellSize() {
        return getIntProperty("cell.size");
    }

    public int getTeamSize() {
        return getIntProperty("team.size");
    }

    public int getTankEnergy() {
        return getIntProperty("tank.energy");
    }

    public int getHitDamage() {
        return getIntProperty("tank.hitdamage");
    }

    private int getIntProperty(String name) {
        return Integer.parseInt(properties.getProperty(name));
    }

    public Color getColor(String name) {
        Color c = colors.get(name);
        if (c == null)
            colors.put(name, c = avalableColors.next());
        return c;

    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

}
