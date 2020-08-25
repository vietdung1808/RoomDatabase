package com.vietdung.roomdatabase.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vietdung.roomdatabase.R;
import com.vietdung.roomdatabase.adapters.WordAdapter;
import com.vietdung.roomdatabase.database.entity.WordEntity;
import com.vietdung.roomdatabase.interfaces.OnAdapterListener;
import com.vietdung.roomdatabase.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;

    private RecyclerView rcvWords;
    private Button btnAdd, btnSave, btnCancel;
    private CardView cvGroupAdding;
    private TextInputEditText etEn, etVi;
    private Spinner spnFilter;
    private TextInputLayout textInputLayoutEn;

    private WordAdapter wordAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapViews();
        initViews();
        setOnListener();
        setObserver();
    }

    private void setObserver() {
        //Observer
        mainViewModel.getAllWords().observe(this, new Observer<List<WordEntity>>() {
            @Override
            public void onChanged(List<WordEntity> wordEntities) {
                wordAdapter.setAllWords(wordEntities);
            }
        });

        mainViewModel.getRowId().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                if (aLong > 0) {
                    cvGroupAdding.setVisibility(View.GONE);
                    btnAdd.setVisibility(View.VISIBLE);
                }
            }
        });

        mainViewModel.getErrorMsg().observe(this, errorMsg -> {
            Toast.makeText(this, "Error: " + errorMsg, Toast.LENGTH_LONG).show();
        });

    }

    private void initViews() {
        cvGroupAdding.setVisibility(View.GONE);

        wordAdapter = new WordAdapter( this);
        rcvWords.setAdapter(wordAdapter);
        rcvWords.setItemAnimator(new DefaultItemAnimator());

        mainViewModel = new MainViewModel(this);
        getLifecycle().addObserver(mainViewModel);


        //spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.word_type_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnFilter.setAdapter(adapter);
    }

    //event
    private void setOnListener() {
        btnAdd.setOnClickListener(v -> {
            cvGroupAdding.setVisibility(View.VISIBLE);
            btnAdd.setVisibility(View.GONE);
            clearTextControl();
        });

        btnCancel.setOnClickListener(v -> {
            cvGroupAdding.setVisibility(View.GONE);
            btnAdd.setVisibility(View.VISIBLE);
        });

        btnSave.setOnClickListener(v -> {
            String en = etEn.getText().toString();

            if (en.matches(".*\\d.*")) {
                textInputLayoutEn.setErrorEnabled(true);
                textInputLayoutEn.setError("Loi chua ky tu so");
                return;
            }

            mainViewModel.insertWord(new WordEntity(
                    etEn.getText().toString(),
                    etVi.getText().toString(),
                    0
            ));
        });

        spnFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mainViewModel.callDataWords(position - 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("BBB", "onNothingSelected: ");
            }
        });

        wordAdapter.setOnItemClickListener(new OnAdapterListener() {
            @Override
            public void onMemorizedClick(WordEntity wordEntity) {
                wordEntity.setIsmemorized((wordEntity.getIsmemorized() + 1) % 2); //switch 0 - 1
                mainViewModel.updateWord(wordEntity);
            }

            @Override
            public void onRemoveClick(WordEntity wordEntity) {
                mainViewModel.deleteWord(wordEntity);
            }
        });
    }

    private void mapViews() {
        rcvWords = findViewById(R.id.recycleViewWords);
        btnAdd = findViewById(R.id.buttonAdd);
        btnSave = findViewById(R.id.buttonSave);
        btnCancel = findViewById(R.id.buttonCancel);
        cvGroupAdding = findViewById(R.id.cardViewGroupAdding);
        etEn = findViewById(R.id.textinputEN);
        etVi = findViewById(R.id.textinputVI);
        spnFilter = findViewById(R.id.spnFilter);
        textInputLayoutEn = findViewById(R.id.textInputLayoutEn);
    }

    private void clearTextControl() {
        etVi.setText(null);
        etEn.setText(null);
    }
}