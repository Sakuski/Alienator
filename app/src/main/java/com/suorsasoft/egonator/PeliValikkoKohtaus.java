package com.suorsasoft.egonator;


import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.ViewAnimationUtils;

public class PeliValikkoKohtaus implements Kohtaus{

    private ValikkoIkoni kotiIkoni;
    private Point kotiIkoniPiste;

    private ValikkoIkoni lentoKonePeliIkoni;
    private Point lentoKonePeliIkoniPiste;

    private ValikkoIkoni avaruusHyppyPeliIkoni;
    private Point avaruusHyppyPeliIkoniPiste;

    private ValikkoIkoni kirjainG;
    private Point kirjainGpiste;

    private ValikkoIkoni kirjainA;
    private Point kirjainApiste;

    private ValikkoIkoni kirjainM;
    private Point kirjainMpiste;

    private ValikkoIkoni kirjainE;
    private Point kirjainEpiste;

    private ValikkoIkoni kirjainS;
    private Point kirjainSpiste;

    private Taustakuva taustakuva;

    public PeliValikkoKohtaus() {

        kotiIkoni = new ValikkoIkoni(new Rect(0, 0, 200, 200), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.home));
        kotiIkoniPiste = new Point(Vakiot.NAYTTO_LEVEYS/2, 9*Vakiot.NAYTTO_KORKEUS/10);
        kotiIkoni.update(this.kotiIkoniPiste);

        lentoKonePeliIkoni = new ValikkoIkoni(new Rect(0, 0, 186, 105),  BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.shipgreen));
        lentoKonePeliIkoniPiste = new Point(1*Vakiot.NAYTTO_LEVEYS/8, 5*Vakiot.NAYTTO_KORKEUS/14);
        lentoKonePeliIkoni.update(lentoKonePeliIkoniPiste);

        avaruusHyppyPeliIkoni = new ValikkoIkoni(new Rect(0, 0, 132, 184), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.aliengreen));
        avaruusHyppyPeliIkoniPiste = new Point(1 * Vakiot.NAYTTO_LEVEYS/8, 7*Vakiot.NAYTTO_KORKEUS/14);
        avaruusHyppyPeliIkoni.update(avaruusHyppyPeliIkoniPiste);

        kirjainG = new ValikkoIkoni(new Rect(0, 0, 200, 200), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.letterg));
        kirjainGpiste = new Point(Vakiot.NAYTTO_LEVEYS/8, Vakiot.NAYTTO_KORKEUS/7);
        kirjainG.update(kirjainGpiste);

        kirjainA = new ValikkoIkoni(new Rect(0, 0, 200, 200), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.lettera));
        kirjainApiste = new Point(Vakiot.NAYTTO_LEVEYS/8 + 200, Vakiot.NAYTTO_KORKEUS/7);
        kirjainA.update(kirjainApiste);

        kirjainM = new ValikkoIkoni(new Rect(0, 0, 200, 200), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.letterm));
        kirjainMpiste = new Point(Vakiot.NAYTTO_LEVEYS/8 + 400, Vakiot.NAYTTO_KORKEUS/7);
        kirjainM.update(kirjainMpiste);

        kirjainE = new ValikkoIkoni(new Rect(0, 0, 200, 200), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.lettere));
        kirjainEpiste = new Point(Vakiot.NAYTTO_LEVEYS/8 + 600, Vakiot.NAYTTO_KORKEUS/7);
        kirjainE.update(kirjainEpiste);

        kirjainS = new ValikkoIkoni(new Rect(0, 0, 200, 200), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.letters));
        kirjainSpiste = new Point(Vakiot.NAYTTO_LEVEYS/8 + 800, Vakiot.NAYTTO_KORKEUS/7);
        kirjainS.update(kirjainSpiste);

        taustakuva = new Taustakuva(new Rect(0, 0, Vakiot.NAYTTO_LEVEYS + 1, Vakiot.NAYTTO_KORKEUS + 1), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.tausta));
    }

    public static void terminate() {
        KohtausManageri.AKTIIVINEN_KOHTAUS = 1;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int)event.getX();
            int y = (int)event.getY();

            Rect lentoKonePeliNelio = new Rect(0, 2*Vakiot.NAYTTO_KORKEUS/7, Vakiot.NAYTTO_LEVEYS + 1, 3*Vakiot.NAYTTO_KORKEUS/7);
            Rect avaruusHyppyPeliNelio = new Rect(0, 3*Vakiot.NAYTTO_KORKEUS/7, Vakiot.NAYTTO_LEVEYS + 1, 4*Vakiot.NAYTTO_KORKEUS/7);
            Rect kotiIkoniNelio = kotiIkoni.getRect();

            if(lentoKonePeliNelio.contains(x, y)) {
                LentoKonePeliKohtaus.terminate();
            }

            if(kotiIkoniNelio.contains(x, y)) {
                AlkuKohtaus.terminate();
            }

            if(avaruusHyppyPeliNelio.contains(x, y)) {
                AvaruusHyppyPeliKohtaus.terminate();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        taustakuva.draw(canvas);

        Paint maali = new Paint();

        //piirretään yhen väriset neliöt
        maali.setColor(Color.rgb(169, 222, 236));
        maali.setAlpha(235);

        Rect nelikulmio = new Rect(0, 2*Vakiot.NAYTTO_KORKEUS/7, Vakiot.NAYTTO_LEVEYS + 1, 3*Vakiot.NAYTTO_KORKEUS/7);
        canvas.drawRect(nelikulmio, maali);

        nelikulmio = new Rect(0, 4*Vakiot.NAYTTO_KORKEUS/7, Vakiot.NAYTTO_LEVEYS + 1, 5*Vakiot.NAYTTO_KORKEUS/7);
        canvas.drawRect(nelikulmio, maali);


        //piirretään toisen väriset neliöt
        maali.setColor(Color.rgb(135,206,235));
        maali.setAlpha(235);

        nelikulmio = new Rect(0, 3 * Vakiot.NAYTTO_KORKEUS / 7, Vakiot.NAYTTO_LEVEYS + 1, 4 * Vakiot.NAYTTO_KORKEUS / 7);
        canvas.drawRect(nelikulmio, maali);

        nelikulmio = new Rect(0, 5 * Vakiot.NAYTTO_KORKEUS / 7, Vakiot.NAYTTO_LEVEYS + 1, 6 * Vakiot.NAYTTO_KORKEUS / 7);
        canvas.drawRect(nelikulmio, maali);

        // piirretään flappy ufo jutut
        lentoKonePeliIkoni.draw(canvas);
        maali.setTextSize(100);
        maali.setColor(Color.rgb(0, 0, 0));
        maali.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Flappy Ufo", Vakiot.NAYTTO_LEVEYS / 2, 5 * Vakiot.NAYTTO_KORKEUS / 14 + 25, maali);

        // piirretään space jump jutut
        avaruusHyppyPeliIkoni.draw(canvas);
        canvas.drawText("Space Jump", Vakiot.NAYTTO_LEVEYS/2, 7*Vakiot.NAYTTO_KORKEUS/14+25, maali);

        // piirretään games-kirjaimet
        kirjainG.draw(canvas);
        kirjainA.draw(canvas);
        kirjainM.draw(canvas);
        kirjainE.draw(canvas);
        kirjainS.draw(canvas);

        // piirretään koti-ikoni
        kotiIkoni.draw(canvas);
    }

    @Override
    public void update() {

    }
}
