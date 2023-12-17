package com.podoromotimer.gui;

public interface PodoromoPanelListener {
    void repaintStatePanel();
    void updateTimerLabel(int seconds);
    void updateTimerLabel(String something);
    void changeBackGroundColor();
}
