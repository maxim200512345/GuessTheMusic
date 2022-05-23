package com.codecademy.app.db.dao;


import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.codecademy.app.db.models.StatsEntity1;
import com.codecademy.app.db.models.StatsEntity2;

import java.util.List;

@androidx.room.Dao
public interface Dao2 {
    @Insert
    void insertResult(StatsEntity2 statsEntity2);

    @Update
    void updatePerson(StatsEntity2 statsEntity2);

    @Query("SELECT * FROM stats2")
    List<StatsEntity2> getStats2();

}
