package com.vietdung.roomdatabase.interfaces;

import com.vietdung.roomdatabase.database.entity.WordEntity;

public interface OnAdapterListener {
    public void onMemorizedClick(WordEntity wordEntity);
    public void onRemoveClick(WordEntity wordEntity);
}
