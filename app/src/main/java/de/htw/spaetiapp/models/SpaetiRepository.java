package de.htw.spaetiapp.models;

import java.util.ArrayList;

public class SpaetiRepository {

    private static SpaetiRepository spaetiRepo = null;
    private ArrayList<Spaeti> spaetiList;

    private SpaetiRepository() {
        spaetiList = new ArrayList<>();
    }

    public ArrayList<Spaeti> getSpaetiList() {
        return spaetiList;
    }

    public static SpaetiRepository getSpaetiRepo() {
        if (spaetiRepo == null) {
            spaetiRepo = new SpaetiRepository();
        }
        return spaetiRepo;
    }

}
