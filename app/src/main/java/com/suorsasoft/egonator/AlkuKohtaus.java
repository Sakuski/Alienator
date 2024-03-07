package com.suorsasoft.egonator;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

public class AlkuKohtaus implements Kohtaus {

    private Taustakuva tausta;

    private NelioPelaaja pelaaja;
    private Point pelaajaPiste;

    private ValikkoIkoni peliValikkoIkoni;
    private Point peliValikkoIkoniPiste;

    private ValikkoIkoni ufoVarikkoValikkoIkoni;
    private Point ufoVarikkoValikkoIkoniPiste;

    private ValikkoIkoni hakkuIkoni;
    private Point hakkuIkoniPiste;

    private ValikkoIkoni asetusValikkoIkoni;
    private Point asetusValikkoIkoniPiste;

    private ValikkoIkoni seuraavaIkoni;
    private Point seuraavaIkoniPiste;

    private ValikkoIkoni edellinenIkoni;
    private Point edellinenIkoniPiste;

    private ValikkoIkoni kolikkoIkoni;
    private Point kolikkoIkoniPiste;

    private ValikkoIkoni hinta250;
    private Point hinta250Piste;

    private long kuvaAika;

    public AlkuKohtaus() {

        tausta = new Taustakuva(new Rect(0, 0, Vakiot.NAYTTO_LEVEYS + 1, Vakiot.NAYTTO_KORKEUS + 1), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.alkukohtaustausta));

        pelaaja = new NelioPelaaja(new Rect(100, 100, 435, 600));
        pelaajaPiste = new Point(Vakiot.NAYTTO_LEVEYS/2, Vakiot.NAYTTO_KORKEUS/2);
        pelaaja.update(pelaajaPiste);

        peliValikkoIkoni = new ValikkoIkoni(new Rect(100, 100, 300, 300), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.gamepad));
        peliValikkoIkoniPiste = new Point (Vakiot.NAYTTO_LEVEYS/2, 9*Vakiot.NAYTTO_KORKEUS/10);
        peliValikkoIkoni.update(peliValikkoIkoniPiste);

        ufoVarikkoValikkoIkoni = new ValikkoIkoni(new Rect(100, 100, 300, 200), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.ufoikonimusta));
        ufoVarikkoValikkoIkoniPiste = new Point(Vakiot.NAYTTO_LEVEYS/5, 9*Vakiot.NAYTTO_KORKEUS/10);
        ufoVarikkoValikkoIkoni.update(ufoVarikkoValikkoIkoniPiste);

        hakkuIkoni = new ValikkoIkoni(new Rect(0, 0, 128, 128), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.hakkuikonimusta));
        hakkuIkoniPiste = new Point(4*Vakiot.NAYTTO_LEVEYS/5, 9*Vakiot.NAYTTO_KORKEUS/10);
        hakkuIkoni.update(hakkuIkoniPiste);

        asetusValikkoIkoni = new ValikkoIkoni(new Rect(100, 100, 250, 250), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.asetusvalikko));
        asetusValikkoIkoniPiste = new Point(7*Vakiot.NAYTTO_LEVEYS/8, 1*Vakiot.NAYTTO_KORKEUS/15);
        asetusValikkoIkoni.update(asetusValikkoIkoniPiste);

        seuraavaIkoni = new ValikkoIkoni(new Rect(100, 100, 200, 200), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.forward));
        seuraavaIkoniPiste = new Point(7*Vakiot.NAYTTO_LEVEYS/8, Vakiot.NAYTTO_KORKEUS/2);
        seuraavaIkoni.update(seuraavaIkoniPiste);

        edellinenIkoni = new ValikkoIkoni(new Rect(100, 100, 200, 200), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.backward));
        edellinenIkoniPiste = new Point(1*Vakiot.NAYTTO_LEVEYS/8, Vakiot.NAYTTO_KORKEUS/2);
        edellinenIkoni.update(edellinenIkoniPiste);

        kolikkoIkoni = new ValikkoIkoni(new Rect(0, 0, 150, 150), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.kolikko));
        kolikkoIkoniPiste = new Point(1*Vakiot.NAYTTO_LEVEYS/8, 1*Vakiot.NAYTTO_KORKEUS/15);
        kolikkoIkoni.update(kolikkoIkoniPiste);

        hinta250 = new ValikkoIkoni(new Rect(0, 0, 380, 90), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.hinta250));
        hinta250Piste = new Point(Vakiot.NAYTTO_LEVEYS / 2, 19 * Vakiot.NAYTTO_KORKEUS / 28);
        hinta250.update(hinta250Piste);

        kuvaAika = System.currentTimeMillis();
    }


    public static void terminate() {
        KohtausManageri.AKTIIVINEN_KOHTAUS = 0;
    }

    public void reset() {
        tausta = new Taustakuva(new Rect(0, 0, Vakiot.NAYTTO_LEVEYS + 1, Vakiot.NAYTTO_KORKEUS + 1), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.alkukohtaustausta));

        pelaaja = new NelioPelaaja(new Rect(100, 100, 435, 600));
        pelaajaPiste = new Point(Vakiot.NAYTTO_LEVEYS/2, Vakiot.NAYTTO_KORKEUS/2);
        pelaaja.update(pelaajaPiste);

        peliValikkoIkoni = new ValikkoIkoni(new Rect(100, 100, 300, 300), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.gamepad));
        peliValikkoIkoniPiste = new Point (Vakiot.NAYTTO_LEVEYS/2, 9*Vakiot.NAYTTO_KORKEUS/10);
        peliValikkoIkoni.update(peliValikkoIkoniPiste);

        ufoVarikkoValikkoIkoni = new ValikkoIkoni(new Rect(100, 100, 300, 200), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.ufoikonimusta));
        ufoVarikkoValikkoIkoniPiste = new Point(Vakiot.NAYTTO_LEVEYS/5, 9*Vakiot.NAYTTO_KORKEUS/10);
        ufoVarikkoValikkoIkoni.update(ufoVarikkoValikkoIkoniPiste);

        hakkuIkoni = new ValikkoIkoni(new Rect(0, 0, 128, 128), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.hakkuikonimusta));
        hakkuIkoniPiste = new Point(4*Vakiot.NAYTTO_LEVEYS/5, 9*Vakiot.NAYTTO_KORKEUS/10);
        hakkuIkoni.update(hakkuIkoniPiste);

        asetusValikkoIkoni = new ValikkoIkoni(new Rect(100, 100, 250, 250), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.asetusvalikko));
        asetusValikkoIkoniPiste = new Point(7*Vakiot.NAYTTO_LEVEYS/8, 1*Vakiot.NAYTTO_KORKEUS/15);
        asetusValikkoIkoni.update(asetusValikkoIkoniPiste);

        seuraavaIkoni = new ValikkoIkoni(new Rect(100, 100, 200, 200), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.forward));
        seuraavaIkoniPiste = new Point(7*Vakiot.NAYTTO_LEVEYS/8, Vakiot.NAYTTO_KORKEUS/2);
        seuraavaIkoni.update(seuraavaIkoniPiste);

        edellinenIkoni = new ValikkoIkoni(new Rect(100, 100, 200, 200), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.backward));
        edellinenIkoniPiste = new Point(1*Vakiot.NAYTTO_LEVEYS/8, Vakiot.NAYTTO_KORKEUS/2);
        edellinenIkoni.update(edellinenIkoniPiste);

        kolikkoIkoni = new ValikkoIkoni(new Rect(0, 0, 150, 150), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.kolikko));
        kolikkoIkoniPiste = new Point(1*Vakiot.NAYTTO_LEVEYS/8, 1*Vakiot.NAYTTO_KORKEUS/15);
        kolikkoIkoni.update(kolikkoIkoniPiste);

        hinta250 = new ValikkoIkoni(new Rect(0, 0, 380, 90), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.hinta250));
        hinta250Piste = new Point(Vakiot.NAYTTO_LEVEYS / 2, 19 * Vakiot.NAYTTO_KORKEUS / 28);
        hinta250.update(hinta250Piste);

        kuvaAika = System.currentTimeMillis();
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int)event.getX();
            int y = (int)event.getY();

            Rect peliValikkoNelio = peliValikkoIkoni.getRect();
            Rect asetusValikkoNelio = asetusValikkoIkoni.getRect();
            Rect ufoVarikkoValikkoNelio = ufoVarikkoValikkoIkoni.getRect();
            Rect hakkuIkoniNelio = hakkuIkoni.getRect();
            Rect seuraavaIkoniNelio = seuraavaIkoni.getRect();
            Rect edellinenIkoniNelio = edellinenIkoni.getRect();
            Rect hinta250Nelio = hinta250.getRect();

            if(peliValikkoNelio.contains(x, y)) {
                PeliValikkoKohtaus.terminate();
            }

            if(ufoVarikkoValikkoNelio.contains(x, y)) {
                UfoVarikkoKohtaus.terminate();
            }

            if(hakkuIkoniNelio.contains(x, y)) {
                RakennusKohtaus.terminate();
            }

            if (x > asetusValikkoNelio.left && x < asetusValikkoNelio.right && y > asetusValikkoNelio.bottom && y < asetusValikkoNelio.top) {

            }

            if(seuraavaIkoniNelio.contains(x, y)) {
                pelaaja.seuraavaHahmo();
            }

            if(edellinenIkoniNelio.contains(x, y)) {
                pelaaja.edellinenHahmo();
            }

            if(NelioPelaaja.hahmoIndeksi == 1 && hinta250Nelio.contains(x, y) && !Vakiot.pinkkiAlienOsto && Vakiot.kolikkoMaara >= 250) {
                Vakiot.pinkkiAlienOsto = true;
                Vakiot.kolikkoMaara -= 250;
                this.reset();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        tausta.draw(canvas);

        Rect nelikulmio = new Rect(0, 4*Vakiot.NAYTTO_KORKEUS/5, Vakiot.NAYTTO_LEVEYS + 1, Vakiot.NAYTTO_KORKEUS + 1);
        Paint maali = new Paint();
        maali.setColor(Color.rgb(0,0,0));
        maali.setAlpha(150);
        canvas.drawRect(nelikulmio, maali);

        pelaaja.draw(canvas);
        peliValikkoIkoni.draw(canvas);
        ufoVarikkoValikkoIkoni.draw(canvas);
        hakkuIkoni.draw(canvas);
        asetusValikkoIkoni.draw(canvas);
        kolikkoIkoni.draw(canvas);
        edellinenIkoni.draw(canvas);
        seuraavaIkoni.draw(canvas);

        maali.setColor(Color.rgb(255,223,0));
        maali.setTextSize(100);
        canvas.drawText("" + Vakiot.kolikkoMaara, 2*Vakiot.NAYTTO_LEVEYS/9, 2*Vakiot.NAYTTO_KORKEUS/23, maali);

        if(NelioPelaaja.hahmoIndeksi == 1 && !Vakiot.pinkkiAlienOsto) {
            hinta250.draw(canvas);
        }

    }
    @Override public void update() {

    }
}
