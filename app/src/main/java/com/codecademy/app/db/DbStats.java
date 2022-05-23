package com.codecademy.app.db;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.codecademy.app.db.dao.Dao1;
import com.codecademy.app.db.dao.Dao2;
import com.codecademy.app.db.dao.Dao3;
import com.codecademy.app.db.models.StatsEntity1;
import com.codecademy.app.db.models.StatsEntity2;
import com.codecademy.app.db.models.StatsEntity3;

import java.util.List;


@Database(entities = {StatsEntity1.class, StatsEntity2.class, StatsEntity3.class},
version = 1)
public abstract class DbStats extends RoomDatabase {
    private static final String DB_NAME = "person.db";
    private static DbStats instance;

    public static synchronized DbStats getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), DbStats.class,
                    DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract Dao1 dao1();

    public abstract Dao2 dao2();

    public abstract Dao3 dao3();
}