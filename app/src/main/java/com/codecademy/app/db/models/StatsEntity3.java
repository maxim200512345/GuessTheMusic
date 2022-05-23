package com.codecademy.app.db.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "stats3")
public class StatsEntity3 {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "statisticsForGame1")
    private int stats3;
    public StatsEntity3(int id, int stats3){
        this.id = id;
        this.stats3 = stats3;
    }
    @Ignore
    public StatsEntity3(int stats3){
        this.stats3 = stats3;
    }


    public int getStats3() {
        return stats3;
    }

    public void setStats3(int stats3) {
        this.stats3 = stats3;
    }

    public int getId() {
        return id;
    }
}
