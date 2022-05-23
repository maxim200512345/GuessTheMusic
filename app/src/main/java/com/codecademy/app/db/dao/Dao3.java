package com.codecademy.app.db.dao;


import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.codecademy.app.db.models.StatsEntity1;
import com.codecademy.app.db.models.StatsEntity3;

import java.util.List;

@androidx.room.Dao
public interface Dao3 {
    @Insert
    void insertResult(StatsEntity3 statsEntity3);

    @Update
    void updatePerson(StatsEntity3 statsEntity3);

    @Query("SELECT * FROM stats3")
    List<StatsEntity3> getStats3();

}
