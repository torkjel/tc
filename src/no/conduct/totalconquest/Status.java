package no.conduct.totalconquest;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class Status extends JPanel {

    private Battlefield battlefield;

    private Map<String, TeamStatus> teamStatus = new HashMap<String, TeamStatus>();

    public Status(Battlefield battlefield) {
        this.battlefield = battlefield;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        for (String team : battlefield.getTeams()) {
            TeamStatus ts = new TeamStatus(team);
            add(ts);
            teamStatus.put(team, ts);
        }
    }

    public void update() {
        setBackground(Color.BLUE);
        int roundMaxScore = Integer.MIN_VALUE;
        for (String team : battlefield.getTeams()) {
            int score = Math.abs(battlefield.getScore(team));
            if (score > roundMaxScore)
                roundMaxScore = score;
        }

        for (String team : battlefield.getTeams()) {
            TeamStatus l = teamStatus.get(team);
            l.setScore(battlefield.getScore(team), roundMaxScore);
        }
        repaint();
    }

}
