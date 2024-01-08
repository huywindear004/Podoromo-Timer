package com.podoromotimer.settings;

import javax.swing.JTextField;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class Settings {
    private static short workSessions = 4;
    private static final short DELAY = 1000;

    public static short getWorkSessions(){
        return workSessions;
    }

    public static void setWorkSessions(short val){
        workSessions = val;
    }

    public static short getDELAY() {
        return DELAY;
    }

    /**
     * Store the latestValue (valid).
     * If the new input is invalid -> reset the textfield to the latestValue(valid),
     * else (valid) -> assign the inputValue to latestValue
     */
    public static void setNumberFormatValidate(JTextField field) {
        field.addFocusListener(new FocusListener() {
            private static String latestString;

            @Override
            public void focusGained(FocusEvent e) {
                latestString = field.getText();
                field.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {
                try {
                    if (Short.parseShort(field.getText()) <= 0)
                        throw new NumberFormatException();
                } catch (NumberFormatException err) {
                    field.setText(latestString);
                }
            }
        });
    }
}
