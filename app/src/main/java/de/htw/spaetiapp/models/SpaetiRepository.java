package de.htw.spaetiapp.models;

import java.util.ArrayList;

public class SpaetiRepository {

    private static SpaetiRepository instance = null;
    private ArrayList<Spaeti> spaetiList;

    private SpaetiRepository() {
        spaetiList = new ArrayList<>();
    }

    public ArrayList<Spaeti> getSpaetiList() {
        return spaetiList;
    }

    public static SpaetiRepository getInstance() {
        if (instance == null) {
            instance = new SpaetiRepository();
        }
        return instance;
    }

    public void addSpaeti(Spaeti spaeti) {
        spaetiList.add(spaeti);
    }

    public void updateSpaeti(Spaeti spaeti){
        //discuss how to implement this
        //maybe with indexOf and set
        //or if update was success, delete old entry and replace with new??
    }

    public void deleteSpaeti(Spaeti spaeti){
        //maybe problematic since the object in the list is a different one than the one from the server
        // even though the attributes are the same, different reference
        spaetiList.remove(spaeti);
    }

}
