package com.suorsasoft.egonator;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

public class KohtausManageri {
    private ArrayList<Kohtaus> kohtaukset = new ArrayList<>();
    public static int AKTIIVINEN_KOHTAUS;

    public KohtausManageri() {
        AKTIIVINEN_KOHTAUS = 0;
        kohtaukset.add(new AlkuKohtaus());
        kohtaukset.add(new PeliValikkoKohtaus());
        kohtaukset.add(new LentoKonePeliKohtaus());
        kohtaukset.add(new UfoVarikkoKohtaus());
        kohtaukset.add(new AvaruusHyppyPeliKohtaus());
        kohtaukset.add(new RakennusKohtaus());
        //lisaa kohtauksia
    }

    public void receiveTouch(MotionEvent event) {
        kohtaukset.get(AKTIIVINEN_KOHTAUS).receiveTouch(event);
    }

    public void update() {
        kohtaukset.get(AKTIIVINEN_KOHTAUS).update();
    }

    public void draw(Canvas canvas) {
        kohtaukset.get(AKTIIVINEN_KOHTAUS).draw(canvas);
    }
}
