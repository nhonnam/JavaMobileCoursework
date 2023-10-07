package com.example.coursework.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "hikes")
public class Hike implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    public String name;
    public String location;
    public String date;
    public Boolean has_parking;
    public Float length;
    public Integer level;
    public String description;
}
