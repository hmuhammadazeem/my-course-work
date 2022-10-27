package controllers;

import db_adapters.Couplet;
import db_adapters.Poem;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class PoemsProvider {

    private static Poem poemsRepo = new Poem();
    private static Couplet coupletRepo = new Couplet();

    public ObservableList<Poem> getPoemsList(){
        return poemsRepo.getPoemsList();
    }

    public Boolean insertPoem(Poem poem){
        return poemsRepo.insertPoem(poem);
    }

    public void insertCoupletForPoem(Couplet couplet){
        coupletRepo.insertCouplet(couplet);
    }

    public ObservableList<Couplet> getPoemCouplets(int poemId){
        return coupletRepo.getPoemCouplets(poemId);
    }

    public void deletePoem(int poemId){
        poemsRepo.delPoem(poemId);
    }

    public void updatePoem(Poem poem){
        poemsRepo.updatePoem(poem);
    }

}
