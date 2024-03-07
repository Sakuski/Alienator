package com.suorsasoft.egonator;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

public class UfoVarikkoKohtaus implements Kohtaus {

    private LentoKonePelaaja pelaaja;
    private Point lentoKonePelaajaPiste;

    private ValikkoIkoni seuraavaNuoli;
    private Point seuraavaNuoliPiste;

    private ValikkoIkoni edellinenNuoli;
    private Point edellinenNuoliPiste;

    private ValikkoIkoni kotiIkoni;
    private Point kotiIkoniPiste;

    private ValikkoIkoni asetusValikkoIkoni;
    private Point asetusValikkoIkoniPiste;

    private ValikkoIkoni kolikkoIkoni;
    private Point kolikkoIkoniPiste;

    public UfoVarikkoKohtaus() {
        this.pelaaja = new LentoKonePelaaja();
        this.lentoKonePelaajaPiste = new Point(Vakiot.NAYTTO_LEVEYS / 2, Vakiot.NAYTTO_KORKEUS / 2);
        this.pelaaja.setRect(372, 210);
        this.pelaaja.update(this.lentoKonePelaajaPiste);


        this.seuraavaNuoli = new ValikkoIkoni(new Rect(100, 100, 200, 200), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.forward));
        this.seuraavaNuoliPiste = new Point(7*Vakiot.NAYTTO_LEVEYS/8, Vakiot.NAYTTO_KORKEUS/2);
        this.seuraavaNuoli.update(this.seuraavaNuoliPiste);

        this.edellinenNuoli = new ValikkoIkoni(new Rect(100, 100, 200, 200), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.backward));
        this.edellinenNuoliPiste = new Point(1*Vakiot.NAYTTO_LEVEYS/8, Vakiot.NAYTTO_KORKEUS/2);
        this.edellinenNuoli.update(this.edellinenNuoliPiste);

        this.kotiIkoni = new ValikkoIkoni(new Rect(0, 0, 200, 200), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.home));
        this.kotiIkoniPiste = new Point(Vakiot.NAYTTO_LEVEYS/2, 9*Vakiot.NAYTTO_KORKEUS/10);
        this.kotiIkoni.update(this.kotiIkoniPiste);

        this.kolikkoIkoni = new ValikkoIkoni(new Rect(0, 0, 150, 150), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.kolikko));
        this.kolikkoIkoniPiste = new Point(1*Vakiot.NAYTTO_LEVEYS/8, 1*Vakiot.NAYTTO_KORKEUS/15);
        this.kolikkoIkoni.update(kolikkoIkoniPiste);

        this.asetusValikkoIkoni = new ValikkoIkoni(new Rect(100, 100, 250, 250), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.asetusvalikko));
        this.asetusValikkoIkoniPiste = new Point(7*Vakiot.NAYTTO_LEVEYS/8, 1*Vakiot.NAYTTO_KORKEUS/15);
        this.asetusValikkoIkoni.update(asetusValikkoIkoniPiste);
    }

    public static void terminate() {
        KohtausManageri.AKTIIVINEN_KOHTAUS = 3;
    }

    @Override
    public void receiveTouch(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            if (this.kotiIkoni.getRect().contains(x, y)) {
                AlkuKohtaus.terminate();
            }

            if (this.seuraavaNuoli.getRect().contains(x, y)) {
                this.pelaaja.seuraava();
            }

            if (this.edellinenNuoli.getRect().contains(x, y)) {
                this.pelaaja.edellinen();
            }
        }
    }

     @Override
    public void update() {

     }

     @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.rgb(65,105,225));

         Rect nelikulmio = new Rect(0, 4*Vakiot.NAYTTO_KORKEUS/5, Vakiot.NAYTTO_LEVEYS + 1, Vakiot.NAYTTO_KORKEUS + 1);
         Paint maali = new Paint();
         maali.setColor(Color.rgb(0,0,139));
         canvas.drawRect(nelikulmio, maali);

        pelaaja.draw(canvas);
        edellinenNuoli.draw(canvas);
        seuraavaNuoli.draw(canvas);
        kotiIkoni.draw(canvas);
        kolikkoIkoni.draw(canvas);
        asetusValikkoIkoni.draw(canvas);

        maali.setColor(Color.rgb(255,223,0));
        maali.setTextSize(100);
        canvas.drawText("" + Vakiot.kolikkoMaara, 2*Vakiot.NAYTTO_LEVEYS/9, 2*Vakiot.NAYTTO_KORKEUS/23, maali);
     }
}
