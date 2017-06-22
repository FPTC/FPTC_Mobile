package org.pfccap.education.entities;

/**
 * Created by jggomez on 03-May-17.
 */

public class UserAuth {

    private String name;
    private String lastName;
    private String dateBirthday;
    private String email;
    private String phoneNumber;
    private String address;
    private String neighborhood;
    private double latitude;
    private double longitude;
    private double height;
    private double weight;
    private int hasChilds;
    private int pointsBreast;
    private int pointsCervix;
    private int pointsTotal;
    private int state;
    private int repetitionsAnswersBreast;
    private int repetitionsAnswersCervix;
    private String dateCompletedBreast;
    private String dateCompletedCervix;
    private int currentPointsBreast;
    private int currentPointsCervix;
    private int profileCompleted;

    public UserAuth() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getProfileCompleted() {
        return profileCompleted;
    }

    public void setProfileCompleted(int profileCompleted) {
        this.profileCompleted = profileCompleted;
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

    public int getPointsTotal() {
        return pointsTotal;
    }

    public void setPointsTotal(int pointsTotal) {
        this.pointsTotal = pointsTotal;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getRepetitionsAnswersBreast() {
        return repetitionsAnswersBreast;
    }

    public void setRepetitionsAnswersBreast(int repetitionsAnswers) {
        this.repetitionsAnswersBreast = repetitionsAnswers;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getHasChilds() {
        return hasChilds;
    }

    public void setHasChilds(int hasChilds) {
        this.hasChilds = hasChilds;
    }

    public int getPointsBreast() {
        return pointsBreast;
    }

    public void setPointsBreast(int pointsBreast) {
        this.pointsBreast = pointsBreast;
    }

    public int getPointsCervix() {
        return pointsCervix;
    }

    public void setPointsCervix(int pointsCervix) {
        this.pointsCervix = pointsCervix;
    }

    public int getRepetitionsAnswersCervix() {
        return repetitionsAnswersCervix;
    }

    public void setRepetitionsAnswersCervix(int repetitionsAnswersCervix) {
        this.repetitionsAnswersCervix = repetitionsAnswersCervix;
    }

    public String getDateCompletedBreast() {
        return dateCompletedBreast;
    }

    public void setDateCompletedBreast(String dateCompletedBreast) {
        this.dateCompletedBreast = dateCompletedBreast;
    }

    public String getDateCompletedCervix() {
        return dateCompletedCervix;
    }

    public void setDateCompletedCervix(String dateCompletedCervix) {
        this.dateCompletedCervix = dateCompletedCervix;
    }

    public int getCurrentPointsBreast() {
        return currentPointsBreast;
    }

    public void setCurrentPointsBreast(int currentPointsBreast) {
        this.currentPointsBreast = currentPointsBreast;
    }

    public int getCurrentPointsCervix() {
        return currentPointsCervix;
    }

    public void setCurrentPointsCervix(int currentPointsCervix) {
        this.currentPointsCervix = currentPointsCervix;
    }
}
