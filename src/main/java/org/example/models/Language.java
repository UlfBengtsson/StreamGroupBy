package org.example.models;

public class Language {
    private String countryCode;
    private String name;//Language
    private boolean official;
    private float percentage;


    public Language(String countryCode, String name, boolean official, float percentage) {
        this.countryCode = countryCode;
        this.name = name;
        this.official = official;
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return countryCode + " \' " + (official ? "Official  " : "Unofficial") + " \' " + (percentage < 10 ? " " : "") + percentage + "% \' " + name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOfficial() {
        return official;
    }

    public void setOfficial(boolean official) {
        this.official = official;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }
}
