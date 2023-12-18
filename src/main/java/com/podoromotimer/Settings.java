package com.podoromotimer;

import javax.swing.JTextField;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class Settings {
    private static short workSessions = 4;
    private static final short DELAY = 1000;

    public enum State{
        WORK((short) 25),SHORT_BREAK((short) 5),LONG_BREAK((short) 15);
        private short duration;

        private State(short duration){
            this.duration = duration;
        }

        public int getDuration() {
            return duration;
        }

        public static void setWorkMinutes(short duration){
            WORK.duration = duration;
        }

        public static void setShortBreakMinutes(short duration){
            SHORT_BREAK.duration = duration;
        }

        public static void setLongBreakMinutes(short duration){
            LONG_BREAK.duration = duration;
        }
    }

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
