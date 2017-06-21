package org.pfccap.education.entities;

/**
 * Created by jggomez on 08-Jun-17.
 */

public class Configuration {

    private int lapseCervix;
    private int lapseBreast;
    private int numOpportunities;

    public int getLapseCervix() {
        return lapseCervix;
    }

    public void setLapseCervix(int lapseCervix) {
        this.lapseCervix = lapseCervix;
    }

    public int getLapseBreast() {
        return lapseBreast;
    }

    public void setLapseBreast(int lapseBreast) {
        this.lapseBreast = lapseBreast;
    }

    public int getNumOpportunities() {
        return numOpportunities;
    }

    public void setNumOpportunities(int numOpportunities) {
        this.numOpportunities = numOpportunities;
    }
}
