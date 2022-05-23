package com.codecademy.app.db.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "stats1")
public class StatsEntity1 {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "statisticsForGame1")
    private int stats1;
    public StatsEntity1(int id, int stats1){
        this.id = id;
        this.stats1 = stats1;
    }
    @Ignore
    public StatsEntity1(int stats1){
        this.stats1 = stats1;
    }


    public int getStats1() {
        return stats1;
    }

    public void setStats1(int stats1) {
        this.stats1 = stats1;
    }

    public int getId() {
        return id;
    }
}
