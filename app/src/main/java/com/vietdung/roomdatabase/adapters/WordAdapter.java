package com.vietdung.roomdatabase.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vietdung.roomdatabase.R;
import com.vietdung.roomdatabase.database.entity.WordEntity;
import com.vietdung.roomdatabase.interfaces.OnAdapterListener;


import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordHolder> {

    private Context context;
    private OnAdapterListener adapterListener;
    private List<WordEntity> mWordList = new ArrayList<>();

    public WordAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public WordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_word, parent, false);
        return new WordHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordHolder holder, int position) {
        WordEntity word = mWordList.get(position);
        holder.tvEN.setText(word.getEn());
        if (word.getIsmemorized() == 1) {
            holder.tvVI.setText("***");
            holder.btnMemorized.setText(R.string.btn_forgot);
            holder.btnMemorized.setBackground(context.getDrawable(R.drawable.custom_button_green));
        } else {
            holder.tvVI.setText(word.getVn());
            holder.btnMemorized.setText(R.string.btn_memorized);
            holder.btnMemorized.setBackground(context.getDrawable(R.drawable.custom_button_red));
        }
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    class WordHolder extends RecyclerView.ViewHolder {
        TextView tvEN, tvVI;
        Button btnMemorized, btnRemove;

        public WordHolder(@NonNull View itemView) {
            super(itemView);

            tvEN = itemView.findViewById(R.id.textViewItemEN);
            tvVI = itemView.findViewById(R.id.textViewItemVI);
            btnMemorized = itemView.findViewById(R.id.buttonItemMemorized);
            btnRemove = itemView.findViewById(R.id.buttonItemRemove);

            btnMemorized.setOnClickListener(v -> {
                adapterListener.onMemorizedClick(mWordList.get(getAdapterPosition()));
            });

            btnRemove.setOnClickListener(v -> {
                adapterListener.onRemoveClick(mWordList.get(getAdapterPosition()));
            });
        }
    }

    public void setOnItemClickListener(OnAdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    public void setAllWords(List<WordEntity> wordList) {
        if (mWordList.size() > 0) {
            mWordList.clear();
        }
        mWordList.addAll(wordList);
        notifyDataSetChanged();
    }

}
