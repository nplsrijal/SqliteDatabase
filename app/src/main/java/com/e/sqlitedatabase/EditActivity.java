package com.e.sqlitedatabase;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    private EditText txtword,txtmeaning,txtwordid;
    private Button btnupdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        txtword=findViewById(R.id.txtword);
        txtmeaning=findViewById(R.id.txtmeaning);
        txtwordid=findViewById(R.id.wordid);
        Bundle bundle = getIntent().getExtras();


        String word = bundle.getString("word");
        String meaning = bundle.getString("meaning");
        String wordid = bundle.getString("wordid");
        txtmeaning.setText(meaning);
        txtwordid.setText(wordid);
        txtword.setText(word);
        final MyHelper myHelper=new MyHelper(this);
        final SQLiteDatabase db=myHelper.getWritableDatabase();

        btnupdate=findViewById(R.id.btnsaveword);
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myHelper.UpdateData(Integer.parseInt(txtwordid.getText().toString()),txtword.getText().toString(),txtmeaning.getText().toString(),db))
                {
                    Toast.makeText(EditActivity.this,"Successfully Updated" , Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(EditActivity.this,"Error", Toast.LENGTH_SHORT).show();

                }
            }
        });



    }
}
