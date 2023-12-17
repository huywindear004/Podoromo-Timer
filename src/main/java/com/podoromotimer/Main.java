package com.podoromotimer;

import com.podoromotimer.gui.PodoromoFrame;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PodoromoFrame::new);
    }
}