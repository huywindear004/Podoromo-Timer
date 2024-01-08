package com.podoromotimer.settings;

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
