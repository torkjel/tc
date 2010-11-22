package no.conduct.totalconquest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TC extends JFrame implements Callback {

    public static Config CONFIG;

    public static void main(String[] args) {
        Direction.initAdjacent();
        CONFIG = new Config(args);
        Battlefield battlefield = new Battlefield(CONFIG.getGridWidth(), CONFIG.getGridHeight());
        new TC(battlefield);
    }

    private final BattlefieldWidget battlefieldWidget;
    private final Status status;

    public TC(Battlefield battlefield) {

        for (int n = 0; n < CONFIG.getTeamCount(); n++) {
            battlefield.addTeam(CONFIG.getTankType(n), CONFIG.getTeamSize());
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                TC.this.dispose();
            }
        });

        Container root = getContentPane();
        root.setLayout(new BorderLayout());
        root.add(new Controls(battlefield), BorderLayout.NORTH);
        battlefieldWidget = new BattlefieldWidget(battlefield);
        battlefield.setUpdateCallback(this);
        root.add(battlefieldWidget, BorderLayout.CENTER);

        status = new Status(battlefield);
        root.add(status, BorderLayout.EAST);

        setVisible(true);
        pack();
        setSize(CONFIG.getWindowWidth(), CONFIG.getWindowHeight());
    }

    @Override
    public void update() {
        battlefieldWidget.repaint();
        status.update();
    }
}

class BattlefieldWidget extends JPanel {

    private Battlefield battlefield;

    private int cellSize = TC.CONFIG.getCellSize();

    public BattlefieldWidget(Battlefield battlefield) {
        this.battlefield = battlefield;
        Dimension dim = new Dimension(battlefield.getWidth() * cellSize, battlefield.getHeight() * cellSize);
        setSize(dim);
        setMaximumSize(dim);
        setMinimumSize(dim);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int y = 0; y < battlefield.getHeight(); y++) {
            for (int x = 0; x < battlefield.getWidth(); x++) {
                g.setColor(Color.DARK_GRAY);
                g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);
                Tank t = battlefield.get(x, y);
                if (t != null) {
                    g.setColor(t.getColor());
                    g.fillRect(x * cellSize + 1, y * cellSize + 1, cellSize - 2,cellSize - 2);
                }
            }
        }
    }
}
