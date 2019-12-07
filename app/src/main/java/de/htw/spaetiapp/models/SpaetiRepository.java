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

    public boolean addSpaeti(Spaeti spaeti) {
        return spaetiList.add(spaeti);
    }

    public Spaeti updateSpaeti(Spaeti spaeti) {
        //discuss how to implement this
        //maybe with indexOf and set
        //or if update was success, delete old entry and replace with new??


        spaetiList.forEach(spaetiFromRepo -> {
            if (spaetiFromRepo.get_id().equals(spaeti.get_id())) {
                    spaetiList.set(spaetiList.indexOf(spaetiFromRepo), spaeti);
            }
        });
        return null;
    }

    public boolean deleteSpaeti(String id) {
        //maybe problematic since the object in the list is a different one than the one from the server
        // even though the attributes are the same, different reference

        spaetiList.forEach(spaeti -> {
            if (spaeti.get_id().equals(id)) {
                spaetiList.remove(spaeti);
            }
        });
        return false;
    }

}
