package com.company;
/*
    Doplňte funkcionalitu pre aplikáciu Reakčná doba

    Princíp hry

    Hráč po zadaní svojho mena spustí hru.
    Na otázku "Pripraveny ?" odpovedá stlačením ENTER
    Objaví sa hlásenie "Pozooor ..."
    a po náhodne dlhej dobe v intervale 0.5 - 3 sekundy sa objaví povel "START !!!"
    Cieľom hráča je v najrýchlejšom možnom čase opäť stlačiť ENTER.
    Program vypíše čas v milisekundách, ktorý uplynul od zobrazenia povelu START po stlačenie ENTER
    a zaradí ho do usporiadanej tabuľky výkonov (Meno hráča + výkon)
    Na obrazovku vypíše, kde sa daný výkon v tabuľke nachádza a to tak, že vypíše
    5 bezprostredne predchádzajúcich výkonov
    aktuálny výkon
    5 bezprostredne nasledujúcich výkonov
    To všetko v tvare Poradové číslo v tabuľke výkonov Tab6 Meno hráča Tab25 výkon
    Celú tabuľku s novým záznamom zapíše do textového súboru na disk, každý riadok v tvare MenoHraca:vykon

    Hra po spustení načíta zo súboru aktuálnu tabuľku výkonov a požiada hráča o prihlásenie (zadanie mena)
    Potom zobrazí MENU s položkami
    1 - Spusť hru
    2 - Zmena hráča
    3 - TOP 10
    4 - Koniec
    A reaguje podľa výberu

    Hru naprogramujte ako konzolovú aplikáciu aj ako aplikáciu s GUI. Využite pritom MVC.
    Pre meranie času využite funkciu System.currentTimeMillis();
    Hra musí ošetriť aj predčasné stlačenie pred zobrazením START ako chybu a potrestať ju (spôsob trestu je na vás)
*/

import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;



public class ReactBase {
    final int CM_PLAY = 1;
    final int CM_CHANGE_PLAYER = 2;
    final int CM_TOP10 = 3;
    final int CM_QUIT = 4;
    String Player;
    ArrayList<Record> records = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException, IOException {
        boolean gameOn;
	    ReactBase Game = new ReactBase();
	    do
            gameOn =  Game.Run();
        while (gameOn);
    }

    public ReactBase() throws IOException {
        ImportRecords();
        NewPlayer();
    }

    public boolean Run() throws InterruptedException {
        switch(Menu()){
            case CM_CHANGE_PLAYER:
                NewPlayer();
                return true;
            case CM_PLAY:
                Double LastTime = Play(Player);
                Sort(Player, LastTime);
                ShowRecords(Player, LastTime);
                SaveRecords();
                return true;
            case CM_TOP10:
                ShowRecords("", 0.0);
                return true;
            case CM_QUIT:
                return false;
        }
        return true;
    }

    public void ImportRecords() throws IOException {
        String zaznam = null;
        BufferedReader vstup = null;

             vstup = new BufferedReader(new FileReader("D:\\Programy\\Reakcna_Doba\\records.txt"));
            while ((zaznam = vstup.readLine()) != null){
                String[] line = zaznam.split(";");
                records.add(new Record(Double.parseDouble(line[0]),line[1]));




    }vstup.close();}

    public void NewPlayer(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Zadaj meno hraca:");
        Player = sc.nextLine();

    }

    public int Menu(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Menu :");
        System.out.println("1 - Spust hru");
        System.out.println("2 - Zmen hraca");
        System.out.println("3 - Top 10");
        System.out.println("4 - Koniec");
        int cisielko = sc.nextInt();
        switch (cisielko){
            case 1 : return CM_PLAY;
            case 2 : return CM_CHANGE_PLAYER;
            case 3 : return  CM_TOP10;
            case 4 : return CM_QUIT;
        }


        return CM_QUIT;
    }

    public Double Play(String who) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Pripraveny ? [ENTER]");
        sc.nextLine();
        System.out.println("Pozoooor .....");
        long sleep = (long) ((Math.random()*3000)+500);
        Thread.sleep(sleep);
        System.out.println("START");
        long startTime = System.nanoTime();
        sc.nextLine();
        long endTime = System.nanoTime();
        double duration = Math.round(((double)(endTime - startTime) / 1000000000)*1000.0)/1000.0;
        System.out.println("Trvalo ti to: "+duration+" sekund");



        return duration;
    }

    public void Sort(String who, Double record){
        Record Player = new Record(record,who);
        records.add(Player);
        Collections.sort(records, new Sorter());

    }

    public void ShowRecords(String who, Double record){
        if(who == ""  &&  record == 0.0){
            int rank = 1;
            System.out.println("*** TOP 10 ***");
            System.out.println("PLAYER :      RECORD:");
            for(Record i : records){
                System.out.println(rank+"."+i.player+"        "+i.value);
                if(rank==10)break;
                rank++;
            }
        }
        else {

        }
    }

    public void SaveRecords(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\Programy\\Reakcna_Doba\\records.txt",false));
            for(Record i : records){
                Double value = i.value;
                String player = i.player;
                bw.write(""+value+";"+player);
                bw.newLine();


            }bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
