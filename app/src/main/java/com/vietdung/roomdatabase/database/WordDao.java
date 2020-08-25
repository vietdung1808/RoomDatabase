package com.vietdung.roomdatabase.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.vietdung.roomdatabase.database.entity.WordEntity;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;

@Dao
public interface WordDao {

    @Query(value = "SELECT * FROM word WHERE ismemorized = CASE :isMemorized WHEN -1 THEN ismemorized ELSE :isMemorized END ")
    Observable<List<WordEntity>> getAllWords(int isMemorized);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Maybe<Long> insertWord(WordEntity wordEntity);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    Maybe<Integer> updateWord(WordEntity wordEntity);

    @Delete()
    Maybe<Integer> deleteWord(WordEntity wordEntity);



}
