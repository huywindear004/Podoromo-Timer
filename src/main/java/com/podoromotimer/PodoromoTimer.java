package com.podoromotimer;

import com.podoromotimer.audio.Audio;
import com.podoromotimer.gui.PodoromoPanelListener;
import com.podoromotimer.settings.Settings;
import com.podoromotimer.settings.State;

import javax.swing.Timer;

public class PodoromoTimer {
    private final Timer timer;
    private PodoromoPanelListener listener;
    private boolean isRunning = false;
    private State currentState = State.WORK;
    private byte currentSession = 0;
    private int podoromoCount = 0;
    private int seconds = currentState.getDuration() * 60;

    public PodoromoTimer() {
        timer = new Timer(Settings.getDELAY(), e -> updateTimer());
    }

    public State getCurrentState(){return this.currentState;}

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
        if (this.currentState == State.WORK) {
            //long break
            if (this.currentSession == Settings.getWorkSessions() - 1) {
                this.currentState = State.LONG_BREAK;
                this.currentSession = 0;
                Audio.playSound("longBreak");
            }
            //short break
            else {
                this.currentState = State.SHORT_BREAK;
                this.currentSession++;
                Audio.playSound("shortBreak");
            }
            this.podoromoCount++;
        }
        //switch to work
        else {
            this.currentState = State.WORK;
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
        this.seconds = State.WORK.getDuration() * 60;
        this.currentSession = 0;
        this.currentState = State.WORK;
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
