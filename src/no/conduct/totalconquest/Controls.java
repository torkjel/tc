package no.conduct.totalconquest;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Controls extends JPanel {

    JButton startPause = new JButton("Start");
    JButton stop = new JButton("Stop");

    private final Battlefield battlefield;

    public Controls(Battlefield bf) {
        this.battlefield = bf;

        setLayout(new FlowLayout());
        add(startPause);
        add(stop);

        startPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!battlefield.isRunning()) {
                    battlefield.start();
                    startPause.setText("Pause");
                } else if (battlefield.isRunning()) {
                    System.out.println("pause...");
                    battlefield.pause();
                    startPause.setText(battlefield.isPaused() ? "Start" : "Pause");
                }
            }
        });

        stop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                battlefield.stop();
            }
        });

    }
}
