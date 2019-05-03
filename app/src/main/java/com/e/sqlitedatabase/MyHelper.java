package com.e.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.e.sqlitedatabase.model.Word;

import java.util.ArrayList;
import java.util.List;

public class MyHelper extends SQLiteOpenHelper {

    private static final String databaseName ="DictionaryDB";
    private static final int dbVersion=1;

    private static final String tblWord="tblWord";
    private static final String WordID="WordId";
    private static  final String Word="Word";
    private static final String Meaning="Meaning";

    public MyHelper(Context context) {
        super(context, databaseName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE " + tblWord +
                "("+WordID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Word + " TEXT ," + Meaning + " TEXT" + ")";
        db.execSQL(query);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long InsertData(String word, String meaning , SQLiteDatabase db){
        long id;
        ContentValues contentValues =new ContentValues();
        contentValues.put(Word,word);
        contentValues.put(Meaning,meaning);
        id=db.insert(tblWord,null,contentValues);
        return id;
    }

    public List<com.e.sqlitedatabase.model.Word> GetAllWords(SQLiteDatabase db){
        List<Word> dictionarylist= new ArrayList<>();
        String[] columns={WordID, Word, Meaning};
        Cursor cursor =db.query(tblWord,columns,null,null,null,null,null);
        if(cursor.getCount() > 0) {
            while (cursor.moveToNext()){
                dictionarylist.add(new Word(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            }
        }
        return  dictionarylist;

        }

        public List<Word>GetWordByName(String word,SQLiteDatabase db){
        List<Word>dictionaryList=new ArrayList<>();
        Cursor cursor=db.rawQuery("Select * from tblWord where Word ='"+ word + "'",null);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                dictionaryList.add(new Word(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            }
        }
        return dictionaryList;
        }
    public boolean UpdateData(int wordid,String word, String meaning , SQLiteDatabase db){

        ContentValues contentValues =new ContentValues();
        contentValues.put(Word,word);
        contentValues.put(Meaning,meaning);
        db.update(tblWord, contentValues, "WordID="+wordid, null);

        return true;
    }
    }

