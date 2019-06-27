package com.lambdaschool.tipsease.models;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "restaurant")
public class Restaurant extends Auditable{

    // this is used to set fields to a true/false state determining if its hidden from the swagger docs or not
    private final boolean isHidden = false;

    @Id
    @ApiModelProperty(notes = "The database generated restaurant ID", hidden = isHidden)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int restrauntid;

    private String name, location, imageUrl;

    public Restaurant() {
    }

    public Restaurant(String name, String location, String imageUrl) {
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
    }

    // auto generated getters & setters

    public int getRestrauntid() {
        return restrauntid;
    }

    public void setRestrauntid(int restrauntid) {
        this.restrauntid = restrauntid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
