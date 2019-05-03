package com.e.sqlitedatabase;



import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintStream;



public class AddWordActivity extends AppCompatActivity {
    EditText txtword,txtmeaning;
    Button btnsave,btnreturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        txtword=findViewById(R.id.txtword);
        txtmeaning=findViewById(R.id.txtmeaning);
        btnsave=findViewById(R.id.btnsaveword);
        btnreturn=findViewById(R.id.btnreturn);

        final MyHelper myHelper=new MyHelper(this);
        final SQLiteDatabase db=myHelper.getWritableDatabase();

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save();

                //now using sqlite db to insert
                long id=myHelper.InsertData(txtword.getText().toString(),txtmeaning.getText().toString(),db);
                if(id > 0)
                {
                    Toast.makeText(AddWordActivity.this,"Successful" + id, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AddWordActivity.this,"Error", Toast.LENGTH_SHORT).show();

                }
            }
        });
        btnreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AddWordActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void  save(){
        try{
            PrintStream printStream= new PrintStream(openFileOutput("Words.txt",MODE_PRIVATE | MODE_APPEND));
            printStream.println(txtword.getText().toString()+"->"+txtmeaning.getText().toString());
            printStream.flush();
            printStream.close();
            Toast.makeText(this,"saved to "+getFilesDir(),Toast.LENGTH_LONG).show();
        }catch (IOException e){
            Log.d("Dictionary Add Word ","Error"+e.toString());
            e.printStackTrace();

        }
    }
}

