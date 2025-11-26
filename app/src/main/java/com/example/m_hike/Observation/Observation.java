package com.example.m_hike.Observation;

public class Observation {
    private int id;
    private String observation;
    private String date;
    private String type;
    private String description;
    private int hike_id;

    public Observation(String observation, String  date, String type, String description, int hike_id){
        this.observation = observation;
        this.date = date;
        this.type = type;
        this.description = description;
        this.hike_id = hike_id;
    }

    public Observation(int id,String observation, String date, String type, String description, int hike_id){
        this.id = id;
        this.observation = observation;
        this.date = date;
        this.type = type;
        this.description = description;
        this.hike_id = hike_id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
    public String getObservation() {
        return observation;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setHike_id(int hike_id) {
        this.hike_id = hike_id;
    }

    public int getHike_id() {
        return hike_id;
    }
}
