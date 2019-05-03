package com.e.sqlitedatabase;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.e.sqlitedatabase.model.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private EditText txtword;
    private Button btnsearch;
    private ListView lstword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final MyHelper myHelper=new MyHelper(this);
        final SQLiteDatabase db=myHelper.getWritableDatabase();
        txtword=findViewById(R.id.txtword);
        btnsearch=findViewById(R.id.btnsearch);
        lstword=findViewById(R.id.lstwords);

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Word> wordList=new ArrayList<>();
                wordList =myHelper.GetWordByName(txtword.getText().toString().toLowerCase(),db);

                final HashMap<String ,String> hashMap = new HashMap<>();

                for (int i =0; i<wordList.size();i++){
                    hashMap.put(wordList.get(i).getWord(),wordList.get(i).getMeaning()+"->"+wordList.get(i).getWordId());

                }
                ArrayAdapter<String>stringArrayAdapter = new ArrayAdapter<>(SearchActivity.this,
                        android.R.layout.simple_list_item_1,new ArrayList<String>(hashMap.keySet()));

                lstword.setAdapter(stringArrayAdapter);
                lstword.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String word = parent.getItemAtPosition(position).toString();
                        String mean = hashMap.get(word);
                        String[] parts=mean.split("->");
                        Intent intent = new Intent(SearchActivity.this, AnotherActivity.class);
                        intent.putExtra("meaning", parts[0]);
                        intent.putExtra("wordid", parts[1]);
                        intent.putExtra("word", word);
                        startActivity(intent);
                        //Toast.makeText(getApplicationContext(),mean.toString(),Toast.LENGTH_LONG).show();
                    }
                });
            }

        });
    }
}
