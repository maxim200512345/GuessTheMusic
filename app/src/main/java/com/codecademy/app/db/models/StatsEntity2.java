package com.codecademy.app.db.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "stats2")
public class StatsEntity2 {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "statisticsForGame1")
    public int stats2;
    public StatsEntity2(int id, int stats2){
        this.id = id;
        this.stats2 = stats2;
    }
    @Ignore
    public StatsEntity2(int stats2){
        this.stats2 = stats2;
    }


    public int getStats2() {
        return stats2;
    }

    public void setStats2(int stats2) {
        this.stats2 = stats2;
    }

    public int getId() {
        return id;
    }
}
