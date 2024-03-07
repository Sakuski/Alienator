package com.suorsasoft.egonator;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.view.MotionEvent;

import java.util.ArrayList;

public class LentoKonePeliKohtaus implements Kohtaus {

    private Taustakuva lentoKonePeliTausta;

    private LentoKonePelaaja lentoKonePelaaja;
    private Point lentoKonePelaajaPiste;

    private NelioPelaaja pelaaja;
    private Point pelaajaPiste;

    private LentoKonePeliEsteManageri manageri;
    private LentoKonePeliJaapuikkoManageri jaapuikkoManageri;

    private ValikkoIkoni paussi;
    private Point paussiPiste;

    private ValikkoIkoni peliOhiIkoni;
    private Point peliOhiPiste;

    private ValikkoIkoni uiPalikka;
    private Point uiPalikkaPiste;

    private ValikkoIkoni uiRestart;
    private Point uiRestartPiste;

    private ValikkoIkoni uiHome;
    private Point uiHomePiste;

    private ValikkoIkoni uiPlay;
    private Point uiPlayPiste;

    private ValikkoIkoni uiSuicide;
    private Point uiSuicidePiste;

    private ValikkoIkoni kolikko;
    private Point kolikkoPiste;

    public static int esteNopeus = 16;

    private int hyppyNopeus;

    private int aloitusAika;
    private boolean eiAlkanut;

    private boolean paussattu;
    private boolean peliOhi;

    private boolean piirraTama;



    public LentoKonePeliKohtaus() {
        lentoKonePeliTausta = new Taustakuva(new Rect(0, 0, Vakiot.NAYTTO_LEVEYS, Vakiot.NAYTTO_KORKEUS), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.pelitausta));

        lentoKonePelaaja = new LentoKonePelaaja();
        lentoKonePelaajaPiste = new Point(1*Vakiot.NAYTTO_LEVEYS/4, Vakiot.NAYTTO_KORKEUS/2);
        lentoKonePelaaja.update(lentoKonePelaajaPiste);

        pelaaja = new NelioPelaaja(new Rect(0, 0, 56, 83));
        pelaajaPiste = new Point(1*Vakiot.NAYTTO_LEVEYS/4, Vakiot.NAYTTO_KORKEUS/2 - 55);
        pelaaja.update(pelaajaPiste);

        jaapuikkoManageri = new LentoKonePeliJaapuikkoManageri();
        manageri = new LentoKonePeliEsteManageri();

        paussi = new ValikkoIkoni(new Rect(0, 0, 100, 100), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.pause));
        paussiPiste = new Point(19*Vakiot.NAYTTO_LEVEYS/21, Vakiot.NAYTTO_KORKEUS/20);
        paussi.update(paussiPiste);

        peliOhiIkoni = new ValikkoIkoni(new Rect(0, 0, 824, 156), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.gameover));
        peliOhiPiste = new Point(Vakiot.NAYTTO_LEVEYS/2, Vakiot.NAYTTO_KORKEUS/8);
        peliOhiIkoni.update(peliOhiPiste);

        uiPalikka = new ValikkoIkoni(new Rect(0, 0, 800, 800), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.uipalikka));
        uiPalikkaPiste = new Point(Vakiot.NAYTTO_LEVEYS/2, Vakiot.NAYTTO_KORKEUS/2);
        uiPalikka.update(uiPalikkaPiste);

        uiRestart = new ValikkoIkoni(new Rect(0, 0, 200, 200), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.restart));
        uiRestartPiste = new Point(4*Vakiot.NAYTTO_LEVEYS/11, 5*Vakiot.NAYTTO_KORKEUS/9);
        uiRestart.update(uiRestartPiste);

        uiHome = new ValikkoIkoni(new Rect(0, 0, 200, 200), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.home));
        uiHomePiste = new Point(7*Vakiot.NAYTTO_LEVEYS/11, 5*Vakiot.NAYTTO_KORKEUS/9);
        uiHome.update(uiHomePiste);

        uiPlay = new ValikkoIkoni(new Rect(0, 0, 200, 200), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.forward));
        uiPlayPiste = new Point(8*Vakiot.NAYTTO_LEVEYS/13, Vakiot.NAYTTO_KORKEUS/2);
        uiPlay.update(uiPlayPiste);

        uiSuicide = new ValikkoIkoni(new Rect(0, 0, 200, 200), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.stop));
        uiSuicidePiste = new Point(5*Vakiot.NAYTTO_LEVEYS/13, Vakiot.NAYTTO_KORKEUS/2);
        uiSuicide.update(uiSuicidePiste);

        kolikko = new ValikkoIkoni(new Rect(0, 0, 150, 150), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.kolikko));
        kolikkoPiste = new Point(1*Vakiot.NAYTTO_LEVEYS/8, 1*Vakiot.NAYTTO_KORKEUS/15);
        kolikko.update(kolikkoPiste);

        this.eiAlkanut = true;
        this.paussattu = false;
        this.peliOhi = false;

        Vakiot.lentoKonePeliScore = 0;
        piirraTama = false;

        hyppyNopeus = 3;
    }

    public static void terminate() {
        KohtausManageri.AKTIIVINEN_KOHTAUS = 2;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && peliOhi == false) {

            int x = (int) event.getX();
            int y = (int) event.getY();

            if (eiAlkanut) {
                eiAlkanut = false;
            }

            if (eiAlkanut == false && paussi.getRect().contains(x, y) && paussattu == false) {
                paussattu = true;
            } else if (paussattu == true && eiAlkanut == false && paussi.getRect().contains(x, y)) {
                paussattu = false;
            } else if(paussattu == true && eiAlkanut == false && uiPlay.getRect().contains(x, y)) {
                paussattu = false;
            } else if (paussattu == true && eiAlkanut == false && uiSuicide.getRect().contains(x, y)) {
                peliOhi = true;
            }
            else {
                lentoKonePelaaja.hyppaa();
                aloitusAika = (int) System.currentTimeMillis();
                hyppyNopeus = 15;
            }

        } else if(event.getAction() == MotionEvent.ACTION_DOWN && peliOhi == true) {

            int x = (int) event.getX();
            int y = (int) event.getY();

            if(uiRestart.getRect().contains(x, y)) {
                reset();
            }

            if(uiHome.getRect().contains(x, y)) {
                reset();
                AlkuKohtaus.terminate();
            }


        }

    }

    @Override
    public void draw(Canvas canvas) {
        if(peliOhi == false) {
            lentoKonePeliTausta.draw(canvas);
            if(eiAlkanut == false) {
                jaapuikkoManageri.draw(canvas);
            }


            Paint maali = new Paint();
            maali.setColor(Color.rgb(206, 234, 245));
            Rect alanelio = new Rect(0, 3*Vakiot.NAYTTO_KORKEUS/4, Vakiot.NAYTTO_LEVEYS - 1, Vakiot.NAYTTO_KORKEUS - 1);
            Rect ylanelio = new Rect(0, 0, Vakiot.NAYTTO_LEVEYS - 1, Vakiot.NAYTTO_KORKEUS/4);
            canvas.drawRect(alanelio, maali);
            canvas.drawRect(ylanelio, maali);

            manageri.draw(canvas);
            kolikko.draw(canvas);
            lentoKonePelaaja.draw(canvas);
            pelaaja.draw(canvas);

            maali.setColor(Color.rgb(255,223,0));
            maali.setTextSize(100);
            canvas.drawText("" + Vakiot.kolikkoMaara, 2*Vakiot.NAYTTO_LEVEYS/9, 2*Vakiot.NAYTTO_KORKEUS/23, maali);

            maali.setColor(Color.rgb(0, 0, 0));
            canvas.drawText("Score: " + Vakiot.lentoKonePeliScore, 1*Vakiot.NAYTTO_LEVEYS/16, 4*Vakiot.NAYTTO_KORKEUS/23, maali);

            if(eiAlkanut == false) {
                paussi.draw(canvas);
            }

            if (paussattu) {
                Rect paussiNelio = new Rect(Vakiot.NAYTTO_LEVEYS/8, 4*Vakiot.NAYTTO_KORKEUS/9, 7*Vakiot.NAYTTO_LEVEYS/8, 5*Vakiot.NAYTTO_KORKEUS/9);
                maali.setColor(Color.WHITE);
                maali.setAlpha(235);
                canvas.drawRect(paussiNelio, maali);

                uiPlay.draw(canvas);
                uiSuicide.draw(canvas);
            }
        } else {
            lentoKonePeliTausta.draw(canvas);
            peliOhiIkoni.draw(canvas);
            uiPalikka.draw(canvas);

            uiRestart.draw(canvas);
            uiHome.draw(canvas);

            Paint maali = new Paint();
            maali.setColor(Color.rgb(178,34,34));
            maali.setTextSize(100);
            maali.setTextAlign(Paint.Align.CENTER);

            int x = Vakiot.NAYTTO_LEVEYS/2;
            int y = 5*Vakiot.NAYTTO_KORKEUS/13;
            canvas.drawText("Score: " + Vakiot.lentoKonePeliScore, x, y, maali);

            if(Vakiot.lentoKonePeliScore > Vakiot.lentoKonePeliHighScore || piirraTama) {
                Vakiot.lentoKonePeliHighScore = Vakiot.lentoKonePeliScore;

                y = 6*Vakiot.NAYTTO_KORKEUS/13;
                canvas.drawText("New High Score!", x, y, maali);
                piirraTama = true;
            } else {
                y = 6*Vakiot.NAYTTO_KORKEUS/13;
                canvas.drawText("High Score: " + Vakiot.lentoKonePeliHighScore, x, y, maali);
            }


        }


    }

    @Override
    public void update() {

        if(paussattu == false && peliOhi == false) {
            manageri.update();

            if (eiAlkanut == false) {
                jaapuikkoManageri.update();
            }


            if(lentoKonePelaaja.getHyppaa() == false && eiAlkanut == false) {
                lentoKonePelaaja.lisaaY(hyppyNopeus);
                pelaaja.lisaaY(hyppyNopeus);
                hyppyNopeus++;
            }

            if(lentoKonePelaaja.getHyppaa() == true) {
                lentoKonePelaaja.vahennaY(hyppyNopeus);
                pelaaja.vahennaY(hyppyNopeus);
                hyppyNopeus--;
                if ((int)System.currentTimeMillis() > aloitusAika + 5000) {
                    lentoKonePelaaja.lopetaHyppy();
                }
            }

            if(manageri.tarkistaTormaukset(lentoKonePelaaja)) {
                peliOhi = true;
            }

            if(manageri.tarkistaTormaukset(pelaaja)) {
                peliOhi = true;
            }

            if(jaapuikkoManageri.tarkistaTormaukset(lentoKonePelaaja)) {
                peliOhi = true;
            }

            if(jaapuikkoManageri.tarkistaTormaukset(pelaaja)) {
                peliOhi = true;
            }

            jaapuikkoManageri.tarkistaKolikkoTormaus(lentoKonePelaaja);
            jaapuikkoManageri.tarkistaKolikkoTormaus(pelaaja);


        }

    }

    public void reset() {
        lentoKonePeliTausta = new Taustakuva(new Rect(0, 0, Vakiot.NAYTTO_LEVEYS, Vakiot.NAYTTO_KORKEUS), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.pelitausta));

        lentoKonePelaaja = new LentoKonePelaaja();
        lentoKonePelaajaPiste = new Point(1*Vakiot.NAYTTO_LEVEYS/4, Vakiot.NAYTTO_KORKEUS/2);
        lentoKonePelaaja.update(lentoKonePelaajaPiste);

        pelaaja = new NelioPelaaja(new Rect(0, 0, 56, 83));
        pelaajaPiste = new Point(1*Vakiot.NAYTTO_LEVEYS/4, Vakiot.NAYTTO_KORKEUS/2 - 55);
        pelaaja.update(pelaajaPiste);

        manageri = new LentoKonePeliEsteManageri();
        jaapuikkoManageri = new LentoKonePeliJaapuikkoManageri();

        paussi = new ValikkoIkoni(new Rect(0, 0, 100, 100), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.pause));
        paussiPiste = new Point(19*Vakiot.NAYTTO_LEVEYS/21, Vakiot.NAYTTO_KORKEUS/20);
        paussi.update(paussiPiste);

        eiAlkanut = true;
        paussattu = false;
        peliOhi = false;

        Vakiot.lentoKonePeliScore = 0;
        piirraTama = false;

        hyppyNopeus = 3;
    }
}
