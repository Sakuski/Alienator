package com.suorsasoft.egonator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class LentoKonePeliEsteManageri {

    private ArrayList<LentoKonePeliMaa> maat;
    private int jaamaaNumero = 0;

    private ArrayList<LentoKonePeliMaa> maat2;
    private int jaamaaNumero2 = 0;

    public LentoKonePeliEsteManageri() {

        this.maat = new ArrayList<>();
        this.maat2 = new ArrayList<>();
        generoiMaa();
    }

    public void draw(Canvas canvas) {

        // piirretään pohjamaa
        for (LentoKonePeliMaa maaPala : maat) {
            maaPala.draw(canvas);
        }

        //piirretään ylämaa
        for (LentoKonePeliMaa maaPala2 : maat2) {
            maaPala2.draw(canvas);
        }
    }

    public void generoiMaa() {
        // generoidaan pohjamaa
        int nytX = 0;
        while(maat.size() < 3) {
            Bitmap piirrettavaJaa;
            if (jaamaaNumero ==  0) {
                piirrettavaJaa = BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.jaamaa);
            } else if(jaamaaNumero == 1){
                piirrettavaJaa = BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.jaamaa);
            } else {
                piirrettavaJaa = BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.jaamaa);
            }
            maat.add(new LentoKonePeliMaa(new Rect(nytX, 3*Vakiot.NAYTTO_KORKEUS/4 - 142, nytX+1616, 3*Vakiot.NAYTTO_KORKEUS/4), piirrettavaJaa));
            nytX += 1616;
            jaamaaNumero++;
        }

        //generoidaan ylämaa
        int nytX2 = 0;
        int jaamaaNumero2 = 0;
        while(maat2.size() < 3) {
            Bitmap piirrettavaJaa2;
            if (jaamaaNumero2 == 0) {
                piirrettavaJaa2 = BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.jaamaavaarinpain);
            } else if(jaamaaNumero2 == 1 ) {
                piirrettavaJaa2 = BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.jaamaavaarinpain);
            } else {
                piirrettavaJaa2 = BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.jaamaavaarinpain);
            }
            maat2.add(new LentoKonePeliMaa(new Rect(nytX2, Vakiot.NAYTTO_KORKEUS/4, nytX2+1616, Vakiot.NAYTTO_KORKEUS/4+142), piirrettavaJaa2));
            nytX2 += 1616;
            jaamaaNumero2++;
        }

        jaamaaNumero = 0;
        jaamaaNumero2 = 0;
    }


    public void update() {
        int nopeus = LentoKonePeliKohtaus.esteNopeus;
        // päivitetään alamaa
        for (LentoKonePeliMaa maa : maat) {
            maa.vahennaX(nopeus);
        }
        if (maat.get(0).getRect().right == 0) {
            maat.remove(0);
            if (jaamaaNumero == 3) {
                jaamaaNumero = 0;
            }

            if (jaamaaNumero == 0) {
                maat.add(new LentoKonePeliMaa(new Rect(2*1616, 3*Vakiot.NAYTTO_KORKEUS/4 - 142, 3*1616, 3*Vakiot.NAYTTO_KORKEUS/4), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.jaamaa)));
            } else if (jaamaaNumero == 1){
                maat.add(new LentoKonePeliMaa(new Rect(2*1616, 3*Vakiot.NAYTTO_KORKEUS/4 - 142, 3*1616, 3*Vakiot.NAYTTO_KORKEUS/4), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.jaamaa)));
            } else {
                maat.add(new LentoKonePeliMaa(new Rect(2*1616, 3*Vakiot.NAYTTO_KORKEUS/4 - 142, 3*1616,3* Vakiot.NAYTTO_KORKEUS/4), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.jaamaa)));
            }
            jaamaaNumero++;
        }

        //päivitetään ylämaa
        for (LentoKonePeliMaa maa : maat2) {
            maa.vahennaX(nopeus);
        }

        if(maat2.get(0).getRect().right == 0) {
            maat2.remove(0);
            if(jaamaaNumero2 == 3) {
                jaamaaNumero2 = 0;
            }

            if(jaamaaNumero2 == 0) {
                maat2.add(new LentoKonePeliMaa(new Rect(2*1616, Vakiot.NAYTTO_KORKEUS/4, 3*1616, Vakiot.NAYTTO_KORKEUS/4+142), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.jaamaavaarinpain)));
            } else if(jaamaaNumero2 == 1) {
                maat2.add(new LentoKonePeliMaa(new Rect(2*1616, Vakiot.NAYTTO_KORKEUS/4, 3*1616, Vakiot.NAYTTO_KORKEUS/4+142), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.jaamaavaarinpain)));
            } else {
                maat2.add(new LentoKonePeliMaa(new Rect(2*1616, Vakiot.NAYTTO_KORKEUS/4, 3*1616, Vakiot.NAYTTO_KORKEUS/4+142), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.jaamaavaarinpain)));
            }
            jaamaaNumero2++;
        }
    }

    public boolean tarkistaTormaukset(LentoKonePelaaja pelaaja) {
        boolean palautettava = false;

        for (LentoKonePeliMaa maa : maat) {
            if (pelaaja.tormaa(maa)) {
                palautettava = true;
            }
        }

        for (LentoKonePeliMaa maa : maat2) {
            if (pelaaja.tormaa(maa)) {
                palautettava = true;
            }
        }

        return palautettava;
    }

    public boolean tarkistaTormaukset(NelioPelaaja pelaaja) {
        boolean palautettava = false;

        for (LentoKonePeliMaa maa : maat) {
            if (pelaaja.tormaa(maa)) {
                palautettava = true;
            }
        }

        for (LentoKonePeliMaa maa : maat2) {
            if (pelaaja.tormaa(maa)) {
                palautettava = true;
            }
        }

        return palautettava;
    }
}
