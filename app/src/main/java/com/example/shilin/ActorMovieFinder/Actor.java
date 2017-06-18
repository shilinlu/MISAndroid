package com.example.shilin.ActorMovieFinder;

/**
 * Created by shilinlu on 8/21/2016.
 */
public class Actor {
    private int id;
    private String name;
    private String picturePath;
    private String knownForFilm;
    private String bio;
    private String dob;
    private String placeOfBirth;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getKnownForFilm() {
        return knownForFilm;
    }

    public void setKnownForFilm(String knownForFilm) {
        this.knownForFilm = knownForFilm;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
    public void setDOB(String dob) {
        this.dob = dob;
    }

    public String getBio() {
        return bio;
    }
    public String getDOB(){
        return dob;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }
    public String getPlaceOfBirth(){return placeOfBirth;}

}
