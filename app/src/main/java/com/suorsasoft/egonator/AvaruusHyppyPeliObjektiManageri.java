package com.suorsasoft.egonator;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AvaruusHyppyPeliObjektiManageri {

    private List<AvaruusHyppyPeliMaa> maat;

    public AvaruusHyppyPeliObjektiManageri() {
        this.maat = new LinkedList<>();
        generoiEsteet();
    }

    private void generoiEsteet() {
       int x;
       int y;
       int yIndeksi = 7;

        while(yIndeksi > -8) {

            y = (yIndeksi * Vakiot.NAYTTO_KORKEUS / 8);
            x = (int)(Math.random()*(Vakiot.NAYTTO_LEVEYS - 380));
            x += 180;

            AvaruusHyppyPeliMaaIso maa = new AvaruusHyppyPeliMaaIso();
            Point piste = new Point(x, y);
            maa.update(piste);

            maat.add(maa);

            yIndeksi--;
        }

    }

    public void update(){

    }

    public boolean tarkistaTormaukset(NelioPelaaja pelaaja) {
        boolean palautettava = false;
        for(AvaruusHyppyPeliMaa maa : this.maat) {
            if(pelaaja.jalatOsuu(maa) && !maa.getTuhoutunut()) {
                palautettava = true;
                maa.hajota();
                break;
            }
        }
        return palautettava;
    }

    public void alennaEsteita(int alennettava) {
        int poistettavat = 0;
        for(AvaruusHyppyPeliMaa maa: this.maat) {
            maa.lisaaY(alennettava);
            if(maa.getRect().top > Vakiot.NAYTTO_KORKEUS) {
                poistettavat++;
            }
        }

        int indeksi = 1;
        while(indeksi <= poistettavat) {

            AvaruusHyppyPeliMaa lisattava = new AvaruusHyppyPeliMaaIso();

            int x = (int)(Math.random()*(Vakiot.NAYTTO_LEVEYS - 380));
            x += 180;
            int y = maat.get(maat.size()-1).getRect().centerY()-1*Vakiot.NAYTTO_KORKEUS/8;
            maat.remove(maat.get(0));
            Point piste = new Point(x, y);
            lisattava.update(piste);
            maat.add(lisattava);
            Vakiot.avaruusHyppyPeliScore++;
            indeksi++;
        }
    }

    public void draw(Canvas canvas) {
        for(AvaruusHyppyPeliMaa maaPala : maat) {
            maaPala.draw(canvas);
        }
    }
}
