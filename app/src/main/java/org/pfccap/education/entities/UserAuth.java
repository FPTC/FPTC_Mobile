package org.pfccap.education.entities;

/**
 * Created by jggomez on 03-May-17.
 */

public class UserAuth {

    private String firstLastName;
    private String dateBirthday;
    private String email;
    private String age;
    private String phoneNumber;
    private String address;
    private String neighborhood;
    private double latitude;
    private double longitude;
    private double height;
    private double weight;
    private String hasChilds;
    private String pointsTotal;
    private String state;
    private String repetitionsAnswers;

    public UserAuth() {

    }

    public String getFirstLastName() {
        return firstLastName;
    }

    public void setFirstLastName(String firstLastName) {
        this.firstLastName = firstLastName;
    }

    public String getDateBirthday() {
        return dateBirthday;
    }

    public void setDateBirthday(String dateBirthday) {
        this.dateBirthday = dateBirthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getPointsTotal() {
        return pointsTotal;
    }

    public void setPointsTotal(String pointsTotal) {
        this.pointsTotal = pointsTotal;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRepetitionsAnswers() {
        return repetitionsAnswers;
    }

    public void setRepetitionsAnswers(String repetitionsAnswers) {
        this.repetitionsAnswers = repetitionsAnswers;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getHasChilds() {
        return hasChilds;
    }

    public void setHasChilds(String hasChilds) {
        this.hasChilds = hasChilds;
    }
}
