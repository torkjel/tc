package no.conduct.totalconquest;

import java.awt.Color;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class Config {

    public Iterator<Color> avalableColors =
        Arrays.asList(Color.RED, Color.BLUE, Color.GREEN, Color.CYAN, Color.MAGENTA).iterator();

    private Map<String, Color> colors = new HashMap<String, Color>();

    private Properties properties = new Properties();
    private int teamCount;

    public Config() {
        FileReader fr = null;
        try {
            fr = new FileReader("tc.properties");
            properties.load(fr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try { fr.close(); } catch (IOException e) { e.printStackTrace(); }
        }
    }

    @SuppressWarnings("unchecked")
    public Config(String ... classNames) {
        this();
        for (String team : classNames) {
            Class<? extends Tank> teamType;
            try {
                teamType = (Class<Tank>)getClass().getClassLoader().loadClass(team);
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
    public Class<Tank> getTankType(int teamNumber) {
        return (Class<Tank>)properties.get("team." + teamNumber + ",tanktype");
    }

    public int getWindowWidth() {
        return getGridWidth() * getCellSize() + 200;
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

}
