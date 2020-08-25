package com.vietdung.roomdatabase.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.vietdung.roomdatabase.database.AppDatabase;
import com.vietdung.roomdatabase.database.WordDao;
import com.vietdung.roomdatabase.database.entity.WordEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

public class WordRepository {

    private static WordRepository wordRepository = null;
    private WordDao wordDao = null;

    private WordRepository(Context context) {
        wordDao = AppDatabase.getInstance(context).wordDao();
    }

    public synchronized static WordRepository getInstance(Context context) {
        if (wordRepository == null) {
            wordRepository = new WordRepository(context);
        }
        return wordRepository;
    }

    public Observable<List<WordEntity>> getAllFoods(int isMemorized) {
        return wordDao.getAllWords(isMemorized);
    }

    public Maybe<Long> insertWord(WordEntity wordEntity) {
        return wordDao.insertWord(wordEntity);
    }

    public Maybe<Integer> updateWord(WordEntity wordEntity) {
        return wordDao.updateWord(wordEntity);
    }

    public Maybe<Integer> deleteWord(WordEntity wordEntity) {
        return wordDao.deleteWord(wordEntity);
    }
 }
