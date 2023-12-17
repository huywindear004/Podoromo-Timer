package com.podoromotimer;

import com.podoromotimer.audio.Audio;
import com.podoromotimer.gui.PodoromoPanelListener;

import javax.swing.Timer;

public class PodoromoTimer {
    private final Timer timer;
    private PodoromoPanelListener listener;
    private boolean isRunning = false;
    private Settings.State currentState = Settings.State.WORK;
    private byte currentSession = 0;
    private int podoromoCount = 0;
    private int seconds = currentState.getDuration() * 60;

    public PodoromoTimer() {
        timer = new Timer(Settings.getDELAY(),e -> updateTimer());
    }

    public Settings.State getCurrentState(){return this.currentState;}

    public int getPodoromoCount(){return this.podoromoCount;}

    public void setListener(PodoromoPanelListener listener){
        this.listener = listener;
    }

    public boolean isRunning() {
        return isRunning;
    }

    private void updateTimer() {
        this.seconds--;
        if (seconds < 0)
            this.switchSession();
        this.listener.updateTimerLabel(this.seconds);
    }

    public void switchSession() {
        //switch to break
        if (this.currentState == Settings.State.WORK) {
            //long break
            if (this.currentSession == Settings.getWorkSessions() - 1) {
                this.currentState = Settings.State.LONG_BREAK;
                this.currentSession = 0;
                Audio.playSound("longBreak");
            }
            //short break
            else {
                this.currentState = Settings.State.SHORT_BREAK;
                this.currentSession++;
                Audio.playSound("shortBreak");
            }
            this.podoromoCount++;
        }
        //switch to work
        else {
            this.currentState = Settings.State.WORK;
            Audio.playSound("work");
        }
        this.seconds = currentState.getDuration()* 60;
        //repaint the statePanel to see the current state
        this.listener.repaintStatePanel();
        this.listener.updateTimerLabel(this.seconds);
        this.listener.changeBackGroundColor();
    }

    public void restartThisSession(){
        this.seconds = this.currentState.getDuration() * 60;
        this.listener.updateTimerLabel(this.seconds);
    }

    public void resetTimer(){
        this.seconds = Settings.State.WORK.getDuration() * 60;
        this.currentSession = 0;
        this.currentState = Settings.State.WORK;
        this.podoromoCount = 0;
        this.isRunning = false;
        this.listener.updateTimerLabel("PRESS START");
        this.listener.repaintStatePanel();
        this.listener.changeBackGroundColor();
    }

    public void startTimer(){
        isRunning = true;
        timer.start();
    }

    public void pauseTimer(){
        isRunning = false;
        timer.stop();
    }
}
