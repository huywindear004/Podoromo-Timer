package com.podoromotimer.gui;

import com.podoromotimer.PodoromoTimer;
import com.podoromotimer.Settings;
import com.podoromotimer.audio.Audio;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class PodoromoFrame extends JFrame implements PodoromoPanelListener {
    /**
     * MainPanel (CardLayout): contains timerPanel and settingsPanel, which aren't instantiated in this class.
     * It is switched when click the tomato/ confirm/ cancel
     */
    private JPanel mainPanel;
    CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
    private final PodoromoTimer timer;
    //==============================================TIMER PANEL==============================================
    private JLabel timeLabel;
    private JPanel statePanel;
    private JLabel podoromoCountLabel;
    private JPanel podoromoCountPanel;
    private JLabel workLabel;
    private JLabel shortBreakLabel;
    private JLabel longBreakLabel;
    private JButton restartButton;
    private JButton startButton;
    private JButton resetButton;
    private JButton nextButton;
    /**
     * settingButton: show settingsPanel when click
     */
    private JButton settingButton;
    //==============================================SETTINGS PANEL==============================================
    private JTextField workField;
    private JTextField shortBreakField;
    private JTextField longBreakField;
    /**
     * confirmButton and cancelButton: show timerPanel when click
     */
    private JButton confirmButton;
    private JButton cancelButton;

    public PodoromoFrame() {
        //initialize Audio first to prevent the first click is muted
        Audio.init();
        this.timer = new PodoromoTimer();
        this.timer.setListener(this);
        this.timer.resetTimer();

        //set by default
        this.podoromoCountLabel.setText(String.valueOf(timer.getPodoromoCount()));
        this.workField.setText(String.valueOf(Settings.State.WORK.getDuration()));
        this.shortBreakField.setText(String.valueOf(Settings.State.SHORT_BREAK.getDuration()));
        this.longBreakField.setText(String.valueOf(Settings.State.LONG_BREAK.getDuration()));

        //set common properties for JTextFields
        Settings.setNumberFormatValidate(this.workField);
        Settings.setNumberFormatValidate(this.shortBreakField);
        Settings.setNumberFormatValidate(this.longBreakField);

        //==============================START BUTTON==============================
        this.startButton.addActionListener(e -> {
            Audio.playSound("click");
            if (!timer.isRunning()) {
                this.startButton.setText("PAUSE");
                timer.startTimer();
            } else {
                this.startButton.setText("START");
                timer.pauseTimer();
            }
        });
        //==============================RESTART BUTTON==============================
        this.restartButton.addActionListener(e -> {
            Audio.playSound("click");
            timer.pauseTimer();
            this.startButton.setText("RESUME");
            timer.restartThisSession();
        });
        //==============================RESET BUTTON==============================
        this.resetButton.addActionListener(e -> {
            Audio.playSound("click");
            timer.pauseTimer();
            this.startButton.setText("START");
            timer.resetTimer();
        });
        //==============================NEXT BUTTON==============================
        this.nextButton.addActionListener(e -> {
            Audio.playSound("click");
            timer.pauseTimer();
            this.startButton.setText("RESUME");
            timer.switchSession();
        });
        //==============================SETTINGS BUTTON==============================
        this.settingButton.addActionListener(e -> {
            Audio.playSound("click");
            this.changePanel("settingsPanel");
        });
        //==============================CONFIRM BUTTON==============================
        this.confirmButton.addActionListener(e -> {
            Audio.playSound("click");
            this.changePanel("timerPanel");
            //take input
            Settings.State.setWorkMinutes(Short.parseShort(this.workField.getText()));
            Settings.State.setShortBreakMinutes(Short.parseShort(this.shortBreakField.getText()));
            Settings.State.setLongBreakMinutes(Short.parseShort(this.longBreakField.getText()));
        });
        //==============================CANCEL BUTTON==============================
        this.cancelButton.addActionListener(e -> {
            Audio.playSound("click");
            this.changePanel("timerPanel");
            //reset textfields value
            this.workField.setText(String.valueOf(Settings.State.WORK.getDuration()));
            this.shortBreakField.setText(String.valueOf(Settings.State.SHORT_BREAK.getDuration()));
            this.longBreakField.setText(String.valueOf(Settings.State.LONG_BREAK.getDuration()));
        });

        //==============================JFRAME==============================
        this.setTitle("Podoromo Timer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/images/icon.png"))).getImage());

        this.add(mainPanel);

        this.pack();
        this.setLayout(null);
        this.setLocationRelativeTo(null);   //this should be called after pack() for center the window
        this.setResizable(false);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Shut down TinySound after close this window
                timer.pauseTimer();
                Audio.shutdown();
            }
        });
    }

    @Override
    public void repaintStatePanel() {
        this.workLabel.setOpaque(false);
        this.shortBreakLabel.setOpaque(false);
        this.longBreakLabel.setOpaque(false);
        this.statePanel.repaint();
        switch (timer.getCurrentState()) {
            case WORK -> this.workLabel.setOpaque(true);
            case SHORT_BREAK -> this.shortBreakLabel.setOpaque(true);
            case LONG_BREAK -> this.longBreakLabel.setOpaque(true);
        }
        this.podoromoCountLabel.setText(String.valueOf(timer.getPodoromoCount()));
    }

    @Override
    public void updateTimerLabel(int seconds) {
        this.timeLabel.setText(String.format("%02d:%02d", seconds / 60, seconds % 60));
    }

    @Override
    public void updateTimerLabel(String something) {
        this.timeLabel.setText(something);
    }

    @Override
    public void changeBackGroundColor() {
        switch(timer.getCurrentState()){
            case WORK -> {
                Color bgColor = new Color(186,73,73);
                this.mainPanel.setBackground(bgColor);
                this.startButton.setForeground(bgColor);
                this.restartButton.setForeground(bgColor);
                this.resetButton.setForeground(bgColor);
                this.nextButton.setForeground(bgColor);
                this.podoromoCountPanel.setBackground(new Color(193,92,92));
            }
            case SHORT_BREAK -> {
                Color bgColor = new Color(76,145,150);
                this.mainPanel.setBackground(bgColor);
                this.startButton.setForeground(bgColor);
                this.restartButton.setForeground(bgColor);
                this.resetButton.setForeground(bgColor);
                this.nextButton.setForeground(bgColor);
                this.podoromoCountPanel.setBackground(new Color(110,166,171));
            }
            case LONG_BREAK -> {
                Color bgColor = new Color(77,127,162);
                this.mainPanel.setBackground(bgColor);
                this.startButton.setForeground(bgColor);
                this.restartButton.setForeground(bgColor);
                this.resetButton.setForeground(bgColor);
                this.nextButton.setForeground(bgColor);
                this.podoromoCountPanel.setBackground(new Color(95,140,171));
            }
        }
    }

    private void changePanel(String cardName) {
        cardLayout.show(this.mainPanel, cardName);
    }
}
