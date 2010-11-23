package no.conduct.totalconquest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class TeamStatus extends JPanel {

    public static final int WIDTH = 60;

    private String name;
    private Dimension dim;
    private int score, scale;
    private Color color;

    public TeamStatus(String team) {
        dim = new Dimension(WIDTH, TC.CONFIG.getWindowHeight() - 100);
        setSize(dim);
        setMinimumSize(dim);
        setMaximumSize(dim);
        this.color = TC.CONFIG.getColor(team);
        this.name = team;
    }

    public void setScore(int score, int scale) {
        this.scale = scale;
        this.score = score;
        if (score > scale)
            this.scale = score;
        else if (-score > scale)
            this.scale = -score;
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        setSize(dim);
        g.clearRect(0, 0, (int)dim.getWidth(), (int)dim.getHeight());
        g.setColor(Color.BLACK);
        g.fillRect(2, 0, (int)dim.getWidth() - 4, (int)dim.getHeight());
        double h2 = dim.getHeight() / 2.0;
        int h = (int)(score * h2 / scale);
        g.setColor(color);
        if (h > 0)
            g.fillRect(2, (int)(h2 - h), (int)dim.getWidth() - 4, h);
        else {
            g.fillRect(2, (int)(h2), (int)dim.getWidth() - 4, -h);
        }
        g.setColor(Color.WHITE);
        g.setXORMode(Color.BLACK);
        g.setFont(getFont().deriveFont(Font.BOLD));
        g.drawString(score + "", 10, 20);
        g2.translate(WIDTH / 2 - 10, 30);
        g2.rotate(Math.PI / 2);
        g.drawString(name, 0, 0);
    }
}
