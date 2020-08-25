package com.vietdung.roomdatabase.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity(indices = {@Index(value = {"en"}, unique = true)}, tableName = "word")
public class WordEntity {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(defaultValue = "")
    @NonNull
    private String en;

    @ColumnInfo(defaultValue = "")
    private String vn;

    @ColumnInfo(defaultValue = "0")
    private Integer ismemorized;

    @Ignore
    public WordEntity(String en, String vn, Integer ismemorized) {
        this.en = en;
        this.vn = vn;
        this.ismemorized = ismemorized;
    }

    @Ignore
    public WordEntity(Integer id, String en, String vn, Integer ismemorized) {
        this.id = id;
        this.en = en;
        this.vn = vn;
        this.ismemorized = ismemorized;
    }

    public WordEntity() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getVn() {
        return vn;
    }

    public void setVn(String vn) {
        this.vn = vn;
    }

    public Integer getIsmemorized() {
        return ismemorized;
    }

    public void setIsmemorized(Integer ismemorized) {
        this.ismemorized = ismemorized;
    }

    @Override
    public String toString() {
        return "WordEntity{" +
                "id=" + id +
                ", en='" + en + '\'' +
                ", vn='" + vn + '\'' +
                ", ismemorized=" + ismemorized +
                '}';
    }
}
