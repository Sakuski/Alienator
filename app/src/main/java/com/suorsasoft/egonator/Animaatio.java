package com.suorsasoft.egonator;

import android.graphics.Bitmap;

public class Animaatio {

    private Bitmap[]  kuvat;
    private int kuva;

    private boolean onKaynnissa = false;
    public boolean getOnKaynnissa() {
        return onKaynnissa;
    }

    public void play() {
        onKaynnissa = true;
    }

    public void stop() {
        onKaynnissa = false;
    }

    private float kuvaAika;

    private long vikaKuva;

    public Animaatio(Bitmap[] bitmap, float animaatioAika) {
        this.kuvat = bitmap;
        kuva = 0;

        kuvaAika = animaatioAika/kuvat.length;

        vikaKuva = System.currentTimeMillis();
    }

    public void update() {
        if(!onKaynnissa) {
            return;
        }

        if(System.currentTimeMillis()- vikaKuva > kuvaAika * 1000) {
            kuva++;
            if (kuva > kuvat.length || kuva == kuvat.length) {
                kuva = 0;
            }
            vikaKuva = System.currentTimeMillis();
        }
    }

}
