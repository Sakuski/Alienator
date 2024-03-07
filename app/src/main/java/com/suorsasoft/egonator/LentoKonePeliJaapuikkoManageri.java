package com.suorsasoft.egonator;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

public class LentoKonePeliJaapuikkoManageri {
    private int jaaPuikkoAlasNumero;
    private int jaaPuikkoYlosNumero;

    private ArrayList<LentoKonePeliJaapuikko> jaapuikotAlas;
    private ArrayList<LentoKonePeliJaapuikko> jaapuikotYlos;

    private int luodaanko;

    private ValikkoIkoni kolikko;
    private long luomisAika;
    private boolean eiLuotu;

    private ValikkoIkoni bentseeni;
    private long bentseeniLuomisAika;
    private boolean bentseeniEiLuotu;

    public LentoKonePeliJaapuikkoManageri() {
        this.jaaPuikkoAlasNumero = 0;
        this.jaaPuikkoYlosNumero = 0;

        this.jaapuikotAlas = new ArrayList<>();
        this.jaapuikotYlos = new ArrayList<>();

        this.luodaanko = 100;

        this.kolikko = new ValikkoIkoni(new Rect(0, 0, 75, 75), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.kolikko));
        this.luomisAika = 0;
        this.eiLuotu = true;


    }

    public void draw(Canvas canvas) {
        //piirretään yläjääpuikot
        for(LentoKonePeliJaapuikko jaapuikko : jaapuikotAlas) {
            jaapuikko.draw(canvas);
        }

        //piirretään alasjääpuikot
        for(LentoKonePeliJaapuikko jaapuikko : jaapuikotYlos) {
            jaapuikko.draw(canvas);
        }

        if(eiLuotu == false) {
            this.kolikko.draw(canvas);
        }


    }

    public void update() {

        //tehdään kolikkojutut
        this.luoKolikko();
        this.kolikko.vahennaX(LentoKonePeliKohtaus.esteNopeus);

        if(kolikko.getRect().right < 0) {
            eiLuotu = true;
        }


        //päivitetään jääpuikot
        for(LentoKonePeliJaapuikko jaapuikko : jaapuikotAlas) {

            boolean saaLisata = false;

            if(jaapuikko.getRect().right > Vakiot.NAYTTO_LEVEYS/5 - 88) {
                saaLisata = true;
            }

            jaapuikko.vahennaX(LentoKonePeliKohtaus.esteNopeus);

            if(jaapuikko.getRect().right < Vakiot.NAYTTO_LEVEYS/5 - 88 && saaLisata) {
                Vakiot.lentoKonePeliScore++;
                saaLisata = false;
            }
        }
        for(LentoKonePeliJaapuikko jaapuikko : jaapuikotYlos) {

            boolean saaLisata = false;

            if(jaapuikko.getRect().right > Vakiot.NAYTTO_LEVEYS/5 - 88) {
                saaLisata = true;
            }

            jaapuikko.vahennaX(LentoKonePeliKohtaus.esteNopeus);

            if(jaapuikko.getRect().right < Vakiot.NAYTTO_LEVEYS/5 - 88 && saaLisata) {
                Vakiot.lentoKonePeliScore++;
                saaLisata = false;
            }
        }


        // arvotaan jaapuikot
        double randomi = Math.random();
        boolean luodaan = luoJaapuikko((int)(11*randomi));
        int jaapuikkoX = Vakiot.NAYTTO_LEVEYS + (Vakiot.NAYTTO_LEVEYS/4);
        int jaapuikkoY;

        if(luodaan && luodaanko == 100) {

            luodaanko = 0;

            boolean luodaanAlas = alas((int)(randomi*2));

            if (luodaanAlas) {
                jaapuikkoY = ((int)(100*randomi)) + 3*Vakiot.NAYTTO_KORKEUS/4 - 239;

                if(jaapuikotYlos.size() == 3 && jaapuikotYlos.get(0).getRect().centerX()+jaapuikotYlos.get(0).getRect().width()/2 < 0) {
                    jaapuikotYlos.remove(0);
                }

                if(jaapuikotYlos.size() < 3) {
                    if(jaaPuikkoYlosNumero == 3) {
                        jaaPuikkoYlosNumero = 0;
                    }
                    luoAlaJaaPuikko(jaaPuikkoYlosNumero, jaapuikkoX, jaapuikkoY);
                    jaaPuikkoYlosNumero++;
                }

            } else {
                jaapuikkoY = ((int)(100*randomi)) + Vakiot.NAYTTO_KORKEUS/4;

                if(jaapuikotAlas.size() == 3 && jaapuikotAlas.get(0).getRect().centerX()+jaapuikotAlas.get(0).getRect().width()/2 < 0) {
                    jaapuikotAlas.remove(0);
                }

                if(jaapuikotAlas.size() < 3) {
                    if(jaaPuikkoAlasNumero == 3) {
                        jaaPuikkoAlasNumero = 0;
                    }
                    luoYlaJaaPuikko(jaaPuikkoAlasNumero, jaapuikkoX, jaapuikkoY);
                    jaaPuikkoAlasNumero++;
                }
            }
        }
        if(luodaanko != 100) {
            luodaanko++;
        }
    }

    private boolean luoJaapuikko(int arvottu) {
        if (arvottu == 5) {
            return true;
        }
        return false;
    }

    private boolean alas(int arvottu) {
        if(arvottu == 1) {
            return true;
        }
        return false;
    }


    private void luoAlaJaaPuikko(int jaaPuikkoNumero, int x, int y) {
        if(jaaPuikkoNumero == 0) {
            LentoKonePeliJaapuikko jaapuikko = new LentoKonePeliJaapuikko(new Rect(0, 0, 216, 478), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.jaapuikkoylos), false);
            Point piste = new Point(x, y);
            jaapuikko.update(piste);
            this.jaapuikotYlos.add(jaapuikko);
        } else if(jaaPuikkoNumero == 1) {
            LentoKonePeliJaapuikko jaapuikko = new LentoKonePeliJaapuikko(new Rect(0, 0, 216, 478), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.jaapuikkoylos), false);
            Point piste = new Point(x, y);
            jaapuikko.update(piste);
            this.jaapuikotYlos.add(jaapuikko);
        } else if (jaaPuikkoNumero == 2){
            LentoKonePeliJaapuikko jaapuikko = new LentoKonePeliJaapuikko(new Rect(0, 0, 216, 478), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.jaapuikkoylos), false);
            Point piste = new Point(x, y);
            jaapuikko.update(piste);
            this.jaapuikotYlos.add(jaapuikko);
        }
    }

    private void luoYlaJaaPuikko(int jaaPuikkoNumero, int x, int y) {
        if(jaaPuikkoNumero == 0) {
        LentoKonePeliJaapuikko jaapuikko = new LentoKonePeliJaapuikko(new Rect(0, 0, 216, 478), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.jaapuikkoalas), true);
            Point piste = new Point(x, y);
            jaapuikko.update(piste);
            this.jaapuikotAlas.add(jaapuikko);
        } else if(jaaPuikkoNumero == 1) {
            LentoKonePeliJaapuikko jaapuikko = new LentoKonePeliJaapuikko(new Rect(0, 0, 216, 478), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.jaapuikkoalas), true);
            Point piste = new Point(x, y);
            jaapuikko.update(piste);
            this.jaapuikotAlas.add(jaapuikko);
        } else if (jaaPuikkoNumero == 2) {
            LentoKonePeliJaapuikko jaapuikko = new LentoKonePeliJaapuikko(new Rect(0, 0, 216, 478), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.jaapuikkoalas), true);
            Point piste = new Point(x, y);
            jaapuikko.update(piste);
            this.jaapuikotAlas.add(jaapuikko);
        }
    }

    private void luoKolikko() {

        if(System.currentTimeMillis() > luomisAika + 2000 && eiLuotu) {
            int arvottuX = (int)(Math.random() * Vakiot.NAYTTO_LEVEYS) + Vakiot.NAYTTO_LEVEYS;
            int arvottuY = (int)(Math.random() * Vakiot.NAYTTO_KORKEUS);

            if(arvottuY > 3*Vakiot.NAYTTO_KORKEUS/4 - 142 || arvottuY < Vakiot.NAYTTO_KORKEUS/4+142) {
                return;
            }

            for(LentoKonePeliJaapuikko jaapuikko : jaapuikotYlos) {
                if(jaapuikko.getRect().contains(arvottuX, arvottuY)) {
                    return;
                }
            }

            for(LentoKonePeliJaapuikko jaapuikko : jaapuikotAlas) {
                if(jaapuikko.getRect().contains(arvottuX, arvottuY)) {
                    return;
                }
            }

            Point kolikkoPiste = new Point(arvottuX, arvottuY);
            this.kolikko.update(kolikkoPiste);
            eiLuotu = false;
            luomisAika = System.currentTimeMillis();
        }
    }



    public boolean tarkistaTormaukset(LentoKonePelaaja pelaaja) {
        boolean palautettava = false;

        for (LentoKonePeliJaapuikko jaapuikko : jaapuikotAlas) {
            if(pelaaja.tormaa(jaapuikko)) {
                palautettava = true;
            }
        }

        for(LentoKonePeliJaapuikko jaapuikko : jaapuikotYlos) {
            if(pelaaja.tormaa(jaapuikko)) {
                palautettava = true;
            }
        }

        return palautettava;
    }

    public boolean tarkistaTormaukset(NelioPelaaja pelaaja) {
        boolean palautettava = false;

        for (LentoKonePeliJaapuikko jaapuikko : jaapuikotAlas) {
            if(pelaaja.tormaa(jaapuikko)) {
                palautettava = true;
            }
        }

        for(LentoKonePeliJaapuikko jaapuikko : jaapuikotYlos) {
            if(pelaaja.tormaa(jaapuikko)) {
                palautettava = true;
            }
        }

        return palautettava;
    }

    public void tarkistaKolikkoTormaus(LentoKonePelaaja pelaaja) {
        if (pelaaja.tormaa(this.kolikko) && eiLuotu == false) {
            eiLuotu = true;
            luomisAika = System.currentTimeMillis();
            Vakiot.kolikkoMaara++;
        }
    }

    public void tarkistaKolikkoTormaus(NelioPelaaja pelaaja) {
        if (pelaaja.tormaa(this.kolikko) && eiLuotu == false) {
            eiLuotu = true;
            luomisAika = System.currentTimeMillis();
            Vakiot.kolikkoMaara++;
        }
    }




}
