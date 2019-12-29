package de.htw.spaetiapp.models;

public class Spaeti {

    private String name;
    private String openingTime;
    private String closingTime;
    private String description;
    private double longitude;
    private double latitude;
    private String streetName;
    private String city;
    private int zip;
    private String country;
    private String _id;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

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

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
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

    public void setLongitude(double lon) {
        this.longitude = lon;
    }

    public void setLatitude(double lat) {
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


    @Override
    public String toString() {
        String result = "";
        result += getName();
        if(null != openingTime && !openingTime.trim().isEmpty() && !openingTime.equals("n/a"))
            result += "\n" + openingTime;
        if(null != closingTime && !closingTime.trim().isEmpty() && !closingTime.equals("n/a"))
            result += "\n" + closingTime;
        if(null != description && !description.trim().isEmpty())
            result += "\n" + description;
        result += "\n" + getStreetName()
                + "\n" + getZip()
                + "\n" + getCity()
                + "\n" + getCountry();
        return result;
    }

}
