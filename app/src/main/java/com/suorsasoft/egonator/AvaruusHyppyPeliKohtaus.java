package com.suorsasoft.egonator;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

public class AvaruusHyppyPeliKohtaus implements Kohtaus {

    private Taustakuva avaruusHyppyPeliTaustakuva;

    private AvaruusHyppyPeliObjektiManageri manageri;
    private OrientationData orientationData;

    private NelioPelaaja pelaaja;
    private Point pelaajaPiste;

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

    private boolean peliOhi;
    private boolean paussattu;
    private boolean piirraTama;
    private boolean saaHavita;
    private boolean hyppaa;

    public static int esteNopeus = 10;
    public static int pelaajaNopeus = 35;
    public static int pelaajaXnopeus =  10;

    private long frameTime;

    public AvaruusHyppyPeliKohtaus() {

        avaruusHyppyPeliTaustakuva = new Taustakuva(new Rect(0, 0, Vakiot.NAYTTO_LEVEYS, Vakiot.NAYTTO_KORKEUS), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.pelitausta));

        manageri = new AvaruusHyppyPeliObjektiManageri();
        orientationData = new OrientationData();
        orientationData.register();
        orientationData.newGame();

        pelaaja = new NelioPelaaja(new Rect(0, 0, 112, 166));
        pelaajaPiste = new Point(Vakiot.NAYTTO_LEVEYS/2, Vakiot.NAYTTO_KORKEUS/2);
        pelaaja.update(pelaajaPiste);

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

        peliOhi = false;
        paussattu = false;
        piirraTama = false;
        saaHavita = false;

        pelaajaNopeus = 35;
        hyppaa = false;

        frameTime = System.currentTimeMillis();
        Vakiot.avaruusHyppyPeliScore = 0;
    }

    public void reset() {

        avaruusHyppyPeliTaustakuva = new Taustakuva(new Rect(0, 0, Vakiot.NAYTTO_LEVEYS, Vakiot.NAYTTO_KORKEUS), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.pelitausta));

        manageri = new AvaruusHyppyPeliObjektiManageri();
        orientationData = new OrientationData();
        orientationData.register();
        orientationData.newGame();

        pelaaja = new NelioPelaaja(new Rect(0, 0, 112, 166));
        pelaajaPiste = new Point(Vakiot.NAYTTO_LEVEYS/2, Vakiot.NAYTTO_KORKEUS/2);
        pelaaja.update(pelaajaPiste);

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

        peliOhi = false;
        paussattu = false;
        piirraTama = false;
        saaHavita = false;

        pelaajaNopeus = 35;
        hyppaa = false;

        frameTime = System.currentTimeMillis();
        Vakiot.avaruusHyppyPeliScore = 0;

    }

    public static void terminate() {
        KohtausManageri.AKTIIVINEN_KOHTAUS = 4;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && peliOhi == false) {

            int x = (int) event.getX();
            int y = (int) event.getY();

            if (paussi.getRect().contains(x, y) && paussattu == false) {
                paussattu = true;
            } else if (paussattu == true && paussi.getRect().contains(x, y)) {
                paussattu = false;
            } else if(paussattu == true && uiPlay.getRect().contains(x, y)) {
                paussattu = false;
            } else if (paussattu == true && uiSuicide.getRect().contains(x, y)) {
                peliOhi = true;
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
    public void update() {

        if(!paussattu) {

            int elapsedTime = (int)(System.currentTimeMillis() - frameTime);
            if(orientationData.getOrientation() != null && orientationData.getStartOrientation() != null) {
                float xKierto = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];

                if(xKierto > 0.3) {
                    pelaaja.lisaaX(pelaajaXnopeus);
                } else if (xKierto < -0.3) {
                    pelaaja.vahennaX(pelaajaXnopeus);
                }

            }

            if(!hyppaa) {
               pelaaja.lisaaY(pelaajaNopeus);
               pelaajaNopeus++;

               if(pelaaja.getRect().bottom > Vakiot.NAYTTO_KORKEUS && !saaHavita) {
                   pelaajaNopeus = 35;
                   hyppaa = true;
                   saaHavita = true;
               }

               if(pelaaja.getRect().top > Vakiot.NAYTTO_KORKEUS && saaHavita) {
                   peliOhi = true;
               }

               if(manageri.tarkistaTormaukset(pelaaja)) {
                   pelaajaNopeus = 35;
                   hyppaa = true;
                   saaHavita = true;
               }
           }

           if(hyppaa) {

               if(pelaaja.getRect().centerY() - pelaajaNopeus >= Vakiot.NAYTTO_KORKEUS/2) {
                   pelaaja.vahennaY(pelaajaNopeus);
               } else {
                   int vahennettava = -(pelaaja.getRect().centerY() - pelaajaNopeus - Vakiot.NAYTTO_KORKEUS/2);
                   pelaaja.update(new Point(pelaaja.getRect().centerX(), Vakiot.NAYTTO_KORKEUS/2));
                   manageri.alennaEsteita(vahennettava);
               }

               if(pelaajaNopeus > 0) {
                   pelaajaNopeus--;
               } else {
                   hyppaa = false;
               }

           }

        }
    }

    @Override
    public void draw(Canvas canvas) {

        Paint maali = new Paint();

        avaruusHyppyPeliTaustakuva.draw(canvas);

        if(!peliOhi) {

            if(!paussattu) {

                pelaaja.draw(canvas);
                paussi.draw(canvas);
                kolikko.draw(canvas);
                manageri.draw(canvas);

                maali.setColor(Color.rgb(255,223,0));
                maali.setTextSize(100);
                canvas.drawText("" + Vakiot.kolikkoMaara, 2*Vakiot.NAYTTO_LEVEYS/9, 2*Vakiot.NAYTTO_KORKEUS/23, maali);

                maali.setColor(Color.rgb(255, 255, 255));
                canvas.drawText("Score: " + Vakiot.avaruusHyppyPeliScore, 1*Vakiot.NAYTTO_LEVEYS/16, 4*Vakiot.NAYTTO_KORKEUS/23, maali);

            } else if(paussattu) {

                pelaaja.draw(canvas);
                paussi.draw(canvas);
                kolikko.draw(canvas);
                manageri.draw(canvas);

                maali.setColor(Color.rgb(255,223,0));
                maali.setTextSize(100);
                canvas.drawText("" + Vakiot.kolikkoMaara, 2*Vakiot.NAYTTO_LEVEYS/9, 2*Vakiot.NAYTTO_KORKEUS/23, maali);

                maali.setColor(Color.rgb(255, 255, 255));
                canvas.drawText("Score: " + Vakiot.avaruusHyppyPeliScore, 1*Vakiot.NAYTTO_LEVEYS/16, 4*Vakiot.NAYTTO_KORKEUS/23, maali);

                Rect paussiNelio = new Rect(Vakiot.NAYTTO_LEVEYS/8, 4*Vakiot.NAYTTO_KORKEUS/9, 7*Vakiot.NAYTTO_LEVEYS/8, 5*Vakiot.NAYTTO_KORKEUS/9);
                maali.setColor(Color.WHITE);
                maali.setAlpha(235);
                canvas.drawRect(paussiNelio, maali);

                uiPlay.draw(canvas);
                uiSuicide.draw(canvas);
            }
        } else if(peliOhi) {

            uiPalikka.draw(canvas);
            uiRestart.draw(canvas);
            uiHome.draw(canvas);
            peliOhiIkoni.draw(canvas);

            maali.setColor(Color.rgb(178,34,34));
            maali.setTextSize(100);
            maali.setTextAlign(Paint.Align.CENTER);

            int x = Vakiot.NAYTTO_LEVEYS/2;
            int y = 5*Vakiot.NAYTTO_KORKEUS/13;
            canvas.drawText("Score: " + Vakiot.avaruusHyppyPeliScore, x, y, maali);

            if(Vakiot.avaruusHyppyPeliScore > Vakiot.avaruusHyppyPeliHighScore || piirraTama) {
                Vakiot.avaruusHyppyPeliHighScore = Vakiot.avaruusHyppyPeliScore;

                y = 6*Vakiot.NAYTTO_KORKEUS/13;
                canvas.drawText("New High Score!", x, y, maali);
                piirraTama = true;
            } else {
                y = 6*Vakiot.NAYTTO_KORKEUS/13;
                canvas.drawText("High Score: " + Vakiot.avaruusHyppyPeliHighScore, x, y, maali);
            }
        }
    }
}
