package com.example.m_hike;

public class Hike {
    private int id;
    private String name;
    private String location;
    private String date;
    private boolean parking;
    private double length;
    private String difficulty;
    private String description;
    private boolean favorite;
    private boolean completed;

    public Hike(String name, String location, String date, boolean parking, double length,
                String difficulty, String description, boolean favorite, boolean completed){
        this.name = name;
        this.location = location;
        this.date = date;
        this.parking = parking;
        this.length = length;
        this.difficulty = difficulty;
        this.description = description;
        this.favorite = favorite;
        this.completed = completed;
    }

    public Hike(int id, String name, String location, String date, boolean parking, double length,
                String difficulty, String description, boolean favorite, boolean completed){
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.parking = parking;
        this.length = length;
        this.difficulty = difficulty;
        this.description = description;
        this.favorite = favorite;
        this.completed = completed;
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setLocation(String location){
        this.location = location;
    }
    public String getLocation(){
        return location;
    }

    public void setDate(String date){
        this.date =date;
    }
    public String getDate(){
        return date;
    }

    public void setParking(boolean parking){
        this.parking = parking;
    }
    public boolean isParking(){
        return parking;
    }

    public void setLength(double length){
        this.length = length;
    }
    public double getLength(){
        return length;
    }

    public void setDifficulty(String difficulty){
        this.difficulty = difficulty;
    }
    public String getDifficulty(){
        return  difficulty;
    }

    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return description;
    }

    public void setFavorite(boolean favorite){
        this.favorite = favorite;
    }
    public boolean isFavorite(){
        return favorite;
    }

    public void setCompleted(boolean completed){
        this.completed = completed;
    }
    public boolean isCompleted(){
        return completed;
    }
}
