package com.e.sqlitedatabase;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AnotherActivity extends AppCompatActivity {
    private TextView tvout;
    private Button btnback,btnedit,btndel;
    private EditText txtwordid,txtword,txtmeaning;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);
        btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnotherActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String meaning = bundle.getString("meaning");
            String word = bundle.getString("word");
            String wordid=bundle.getString("wordid");
            tvout = findViewById(R.id.tvout);
            tvout.setText(meaning);
            txtwordid=findViewById(R.id.wordid);
            txtwordid.setText(wordid);
            txtword=findViewById(R.id.txtword);
            txtword.setText(word);
            txtmeaning=findViewById(R.id.txtmeaning);
            txtmeaning.setText(meaning);


        }
        btnedit=findViewById(R.id.btnedit);
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wordid=txtwordid.getText().toString();
                String word=txtword.getText().toString();
                String meaning=txtmeaning.getText().toString();
               // Toast.makeText(AnotherActivity.this,"Value"+wordid,Toast.LENGTH_SHORT).show();

                Intent i = new Intent(AnotherActivity.this, EditActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("word", word);
                bundle.putString("meaning", meaning);
                bundle.putString("wordid", wordid);

                i.putExtras(bundle);

                startActivity(i);


            }
        });
        final MyHelper myHelper=new MyHelper(this);
        final SQLiteDatabase db=myHelper.getWritableDatabase();
        btndel=findViewById(R.id.btndel);
        btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AnotherActivity.this);
                builder.setTitle("Confirm dialog demo !");
                builder.setMessage("You are about to delete all records of database. Do you really want to proceed ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(myHelper.DeleteData(Integer.parseInt(txtwordid.getText().toString()),db))
                        {
                            Intent intent=new Intent(AnotherActivity.this,MainActivity.class);
                            startActivity(intent);

                            Toast.makeText(AnotherActivity.this,"Successfully Deleted" , Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AnotherActivity.this,"Error", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "You've changed your mind to delete this record", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });
    }
}
