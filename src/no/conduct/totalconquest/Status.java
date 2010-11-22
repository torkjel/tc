package no.conduct.totalconquest;

import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Status extends JPanel {

    private Battlefield battlefield;

    private Map<String, JLabel> scoreLabels = new HashMap<String, JLabel>();

    public Status(Battlefield battlefield) {
        this.battlefield = battlefield;
        setLayout(new FlowLayout());
        for (String team : battlefield.getTeams()) {
            JLabel l = new JLabel(team + ": " + battlefield.getScore(team));
            add(l);
            scoreLabels.put(team, l);
        }
    }

    public void update() {
        for (String team : battlefield.getTeams()) {
            JLabel l = scoreLabels.get(team);
            l.setText(team + ": " + battlefield.getScore(team));
        }
        repaint();
    }

}
