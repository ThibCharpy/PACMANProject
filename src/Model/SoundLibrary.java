/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.scene.media.AudioClip;

/**
 *
 * @author Antoine
 */
public class SoundLibrary {

    public final AudioClip audio_background5 = new AudioClip(getClass().getResource("/Sprites/pacman_background5.mp3").toExternalForm());
    public final AudioClip audio_alt_powermode = new AudioClip(getClass().getResource("/Sprites/pacman_alt_power.wav").toExternalForm());
    public final AudioClip audio_eat_gomme = new AudioClip(getClass().getResource("/Sprites/pacman_coinin.wav").toExternalForm());
    public final AudioClip audio_death = new AudioClip(getClass().getResource("/Sprites/pacman_death.wav").toExternalForm());
    public final AudioClip audio_extralife = new AudioClip(getClass().getResource("/Sprites/pacman_extralife.wav").toExternalForm());
    public final AudioClip audio_eat_ghost = new AudioClip(getClass().getResource("/Sprites/pacman_getghost.wav").toExternalForm());
    public final AudioClip audio_introsong = new AudioClip(getClass().getResource("/Sprites/pacman_song1.wav").toExternalForm());
    public boolean bool_background5 = true;
    public boolean bool_alt_powermode = true;
    public boolean bool_eat_gomme = true;
    public boolean bool_death = true;
    public boolean bool_extralife = true;
    public boolean bool_eat_ghost = true;
    public boolean bool_introsong = true;

    /**
     * Méthode jouant un son si le booleen associé l'autorise, même si le son
     * est déjà en train d'être joué.
     *
     * @param file_bool booleen associé au son
     * @param file AudioClip utilisé pour généré le son.
     * @param volume volume du son lors de cette instance de lecture.
     */
    public void playOverride(boolean file_bool, AudioClip file, double volume) {
        if (file_bool) {
            file.play(volume);
        }
    }

    /**
     * Méthode jouant un son si le booleen associé l'autorise, et si le son
     * n'est pad déjà en train d'être joué.
     *
     * @param file_bool booleen associé au son
     * @param file AudioClip utilisé pour généré le son.
     * @param volume volume du son lors de cette instance de lecture.
     */
    public void play(boolean file_bool, AudioClip file, double volume) {
        if (file_bool) {
            if (!file.isPlaying()) {
                file.play(volume);
            }
        }
    }

    /**
     * Stop la lecture actuelle du son et si le booleen associé l'autorise le
     * relance.
     *
     * @param file_bool booleen associé au son
     * @param file AudioClip utilisé pour généré le son.
     * @param volume volume du son lors de cette instance de lecture.
     */
    public void stopAndPlay(boolean file_bool, AudioClip file, double volume) {
        file.stop();
        if (file_bool) {
            file.play(volume);
        }

    }
}
