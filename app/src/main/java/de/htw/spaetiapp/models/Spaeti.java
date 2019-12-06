package de.htw.spaetiapp.models;

public class Spaeti {

    private String name;
    private String openingTime;
    private String closingTime;
    private String description;
    private float longitude;
    private float latitude;
    private String streetName;
    private String city;
    private int zip;
    private String country;

    private String id;

    public String getName() {
        return name;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public String getDescription() {
        return description;
    }

    public float getLon() {
        return longitude;
    }

    public float getLat() {
        return latitude;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getCity() {
        return city;
    }

    public int getZip() {
        return zip;
    }

    public String getCountry() {
        return country;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLon(float lon) {
        this.longitude = lon;
    }

    public void setLat(float lat) {
        this.latitude = lat;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    @Override
    public String toString() {
        return getName() + "\n" +
                getLat() + "\n" +
                getLon() + "\n" +
                getOpeningTime() + "\n" +
                getClosingTime() + "\n" +
                getStreetName() + "\n" +
                getDescription() + "\n" +
                getCity() + "\n" +
                getZip() + "\n" +
                getCountry() + "\n" +
                getId();
        //TODO prints biug weird lol rofl lmao
    }

}
