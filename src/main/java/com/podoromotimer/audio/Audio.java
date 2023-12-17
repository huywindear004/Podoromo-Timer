package com.podoromotimer.audio;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

public class Audio {
    static{TinySound.init();}
    private static final Sound click = loadSound("click");
    private static final Sound work = loadSound("work");
    private static final Sound shortBreak = loadSound("shortBreak");
    private static final Sound longBreak = loadSound("longBreak");

    public static void init() {
        TinySound.init();
    }

    public static void shutdown() {
        TinySound.shutdown();
    }

    public static Sound loadSound(String soundName) {
        return TinySound.loadSound("sounds/" + soundName+".wav");
    }

    public static void playSound(String sound){
        try{
            switch (sound){
                case "click" -> click.play();
                case "work" -> work.play();
                case "shortBreak" -> shortBreak.play();
                case "longBreak" -> longBreak.play();
                default -> System.out.println("Can't find "+sound);
            }
        }catch(NullPointerException ignored){}
    }
}
