package com.codecademy.app.db.dao;


import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.codecademy.app.db.models.StatsEntity1;

import java.util.List;

@androidx.room.Dao
public interface Dao1 {
    @Insert
    void insertResult(StatsEntity1 statsEntity1);

    @Update
    void updatePerson(StatsEntity1 statsEntity1);

    @Query("SELECT DISTINCT * FROM stats1")
    List<StatsEntity1> getStats1();


}
