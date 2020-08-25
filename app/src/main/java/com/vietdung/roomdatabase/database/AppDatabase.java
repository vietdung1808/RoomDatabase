package com.vietdung.roomdatabase.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.vietdung.roomdatabase.database.entity.WordEntity;

@Database(entities = {WordEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WordDao wordDao();

    private static AppDatabase appDatabase = null;

    public synchronized static AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(
                    context,
                    AppDatabase.class,
                    "DatabaseWord.db"
            ).build();
        }
        return appDatabase;
    }
}
