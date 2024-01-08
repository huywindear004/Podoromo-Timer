package com.podoromotimer.settings;

import java.awt.Color;

public enum Colors {
    RED(new Color(186,73,73)),
    LIGHTER_RED(new Color(193,92,92)),
    DARKER_RED(new Color(120,48,48)),
    BLUE(new Color(77,127,162)),
    LIGHTER_BLUE(new Color(95,140,171)),
    DARKER_BLUE(new Color(66,108,138)),
    OCEAN(new Color(76,145,150)),
    LIGHTER_OCEAN(new Color(110,166,171)),
    DARKER_OCEAN(new Color(65,123,128));

    private final Color color;

    Colors(Color color) {
        this.color = color;
    }

    public Color getColor(){
        return this.color;
    }
}
