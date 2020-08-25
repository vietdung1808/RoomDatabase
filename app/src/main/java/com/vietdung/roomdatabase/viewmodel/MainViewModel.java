package com.vietdung.roomdatabase.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.vietdung.roomdatabase.database.entity.WordEntity;
import com.vietdung.roomdatabase.repository.WordRepository;

import java.util.List;

import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel implements LifecycleObserver {
    private MutableLiveData<List<WordEntity>> mArrayWords;
    private MutableLiveData<Long> mRowId;
    private MutableLiveData<String> mErrorMsg;
    private WordRepository wordRepository;

    public MainViewModel(Context context) {
        mArrayWords = new MediatorLiveData<>();
        mRowId = new MediatorLiveData<>();
        mErrorMsg = new MediatorLiveData<>();
        wordRepository = WordRepository.getInstance(context);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void init() {
        if (mArrayWords == null) {
//            mArrayWords = new MediatorLiveData<>();
        }
    }


    @SuppressLint("CheckResult")
    public synchronized void callDataWords(int isMemorized) {
//        mArrayWords = wordRepository.getAllFoods(isMemorized);
        wordRepository
                .getAllFoods(isMemorized)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<WordEntity>>() {
                    @Override
                    public void accept(List<WordEntity> wordEntities) throws Exception {
                        mArrayWords.setValue(wordEntities);
                    }
                });
    }

    public LiveData<List<WordEntity>> getAllWords() {
        return mArrayWords;
    }

    public LiveData<Long> getRowId() {
        return mRowId;
    }

    public LiveData<String> getErrorMsg() {
        return mErrorMsg;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void clear() {
        if (mArrayWords != null) {
            mArrayWords = null;
        }
    }

    public void insertWord(WordEntity wordEntity) {
        wordRepository
                .insertWord(wordEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Long aLong) {
                        mRowId.setValue(aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mErrorMsg.setValue(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void updateWord(WordEntity wordEntity) {
        wordRepository
                .updateWord(wordEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        mRowId.setValue(Long.parseLong(integer.toString()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        mErrorMsg.setValue(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void deleteWord(WordEntity wordEntity) {
        wordRepository
                .deleteWord(wordEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        mRowId.setValue(Long.parseLong(integer.toString()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        mErrorMsg.setValue(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
