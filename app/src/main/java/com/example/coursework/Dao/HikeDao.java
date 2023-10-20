package com.example.coursework.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursework.Models.Hike;

import java.util.List;

@Dao
public interface HikeDao {
    @Insert
    Long add(Hike hike);
    @Query("SELECT * FROM hikes")
    List<Hike> getAll();
    @Query("SELECT * FROM hikes where id = :id")
    Hike getById(Long id);
    @Delete
    void delete(Hike hike);
    @Query("DELETE FROM hikes")
    void deleteAll();
    @Update
    void update(Hike hike);
    @Query("SELECT * FROM hikes where name like '%' || :name || '%'")
    List<Hike> findByName(String name);
}
