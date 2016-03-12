/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.AudioClip;
import javax.sound.sampled.*;

/**
 *
 * @author Antoine
 */
public class SoundLibrary {

    public final AudioClip audio_background1 = new AudioClip(getClass().getResource("/Sprites/pacman_background1.wav").toExternalForm());
    public final AudioClip audio_background2 = new AudioClip(getClass().getResource("/Sprites/pacman_background2.wav").toExternalForm());
    public final AudioClip audio_background3 = new AudioClip(getClass().getResource("/Sprites/pacman_background4.wav").toExternalForm());  
    public final AudioClip audio_background5 = new AudioClip(getClass().getResource("/Sprites/pacman_background5.mp3").toExternalForm());  
    public final AudioClip audio_alt_background = new AudioClip(getClass().getResource("/Sprites/pacman_alt_background.wav").toExternalForm());
    public final AudioClip audio_alt_powermode = new AudioClip(getClass().getResource("/Sprites/pacman_alt_power.wav").toExternalForm());
    public final AudioClip audio_alarm1 = new AudioClip(getClass().getResource("/Sprites/pacman_alarm1.wav").toExternalForm());
    public final AudioClip audio_eat_gomme = new AudioClip(getClass().getResource("/Sprites/pacman_coinin.wav").toExternalForm());
    public final AudioClip audio_death = new AudioClip(getClass().getResource("/Sprites/pacman_death.wav").toExternalForm());
    public final AudioClip audio_extralife = new AudioClip(getClass().getResource("/Sprites/pacman_extralife.wav").toExternalForm());
    public final AudioClip audio_eat_ghost = new AudioClip(getClass().getResource("/Sprites/pacman_getghost.wav").toExternalForm());
    public final AudioClip audio_powermode = new AudioClip(getClass().getResource("/Sprites/pacman_power1.wav").toExternalForm());
    public final AudioClip audio_introsong = new AudioClip(getClass().getResource("/Sprites/pacman_song1.wav").toExternalForm());
    public final AudioClip audio_intermission = new AudioClip(getClass().getResource("/Sprites/pacman_song2.wav").toExternalForm());
    public boolean bool_background1 = true;
    public boolean bool_background2 = true;
    public boolean bool_background3 = true;
    public boolean bool_background5 = true;
    public boolean bool_alt_background = true;
    public boolean bool_alt_powermode = true;
    public boolean bool_alarm1 = true;
    public boolean bool_eat_gomme = true;
    public boolean bool_death = true;
    public boolean bool_extralife = true;
    public boolean bool_eat_ghost = true;
    public boolean bool_powermode = true;
    public boolean bool_introsong = true;
    public boolean bool_intermission = true;
    
   
    public SoundLibrary() {
       
    }

    private File createFileFromString(String path) throws URISyntaxException {
        URL url = this.getClass().getResource(path);
        return new File(url.toURI());
    }

    
    public void playOverride(boolean file_bool, AudioClip file, double volume) {
        if (file_bool) {
                file.play(volume);
        }
    }
    
    public void play(boolean file_bool, AudioClip file, double volume) {
        if (file_bool) {
            if (!file.isPlaying()) {
                file.play(volume);
            }
        }
    }

    public void stopAndPlay(boolean file_bool, AudioClip file, double volume) {
        file.stop();
        if (file_bool) {

            file.play(volume);
        }

    }
}
