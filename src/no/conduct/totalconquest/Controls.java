package no.conduct.totalconquest;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Controls extends JPanel {

    JButton start = new JButton("Start");
    JButton stop = new JButton("Stop");
    JLabel speedLabel = new JLabel("Speed:");
    JTextField speed = new JTextField(5);

    private final Battlefield battlefield;

    public Controls(Battlefield bf) {
        this.battlefield = bf;

        setLayout(new FlowLayout());
        add(start);
        add(stop);
        add(speedLabel);
        add(speed);
        speed.setText(TC.CONFIG.getSpeed() + "");

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!battlefield.isRunning())
                    battlefield.init();
                    battlefield.start();
            }
        });

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (battlefield.isRunning())
                    battlefield.stop();
            }
        });

        speed.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        TC.CONFIG.setSpeed(Integer.parseInt(speed.getText()));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

    }
}
