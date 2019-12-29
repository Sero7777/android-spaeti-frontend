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
        if (null != spaeti) return spaetiList.add(spaeti);
        return false;
    }

    public boolean updateSpaeti(Spaeti spaeti) {
        if (null!= spaeti){
            for (Spaeti spaetiFromRepo: spaetiList) {
                if (spaetiFromRepo.get_id().equals(spaeti.get_id())){
                    spaetiList.set(spaetiList.indexOf(spaetiFromRepo), spaeti);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean deleteSpaeti(String id) {
        if (null != id){
            for (Spaeti spaeti: spaetiList) {
                if(spaeti.get_id().equals(id)) {
                    return spaetiList.remove(spaeti);
                }
            }
        }
        return false;
    }
}
