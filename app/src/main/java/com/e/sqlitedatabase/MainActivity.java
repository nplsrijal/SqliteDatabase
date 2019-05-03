package com.e.sqlitedatabase;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.e.sqlitedatabase.model.Word;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView lstword;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstword=findViewById(R.id.lstwords);
        LoadWord();
    }

    private void LoadWord() {
        final MyHelper myHelper = new MyHelper(this);
        final SQLiteDatabase sqLiteDatabase =myHelper.getWritableDatabase();

        List<Word> wordList=new ArrayList<>();
        wordList =myHelper.GetAllWords(sqLiteDatabase);

        final HashMap<String ,String > hashMap = new HashMap<>();
        final HashMap<String,Integer > idhash = new HashMap<>();

        for (int i =0; i<wordList.size();i++){
            hashMap.put(wordList.get(i).getWord(),wordList.get(i).getMeaning()+"->"+wordList.get(i).getWordId());


        }
        ArrayAdapter<String>stringArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,new ArrayList<String>(hashMap.keySet()));
        lstword.setAdapter(stringArrayAdapter);
        lstword.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String word = parent.getItemAtPosition(position).toString();
                String mean = hashMap.get(word);
                String[] parts=mean.split("->");


                Intent intent = new Intent(MainActivity.this, AnotherActivity.class);
                intent.putExtra("meaning", parts[0]);
                intent.putExtra("wordid", parts[1]);
                intent.putExtra("word", word);

                startActivity(intent);
                //Toast.makeText(getApplicationContext(),mean.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}

