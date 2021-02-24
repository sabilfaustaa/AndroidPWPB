package com.android.noteapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DBNAME = "db_note";
    private static final String TABLENAME = "note";

    private static String colID = "id";
    private static String colHeader = "header";
    private static String colText = "text";

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLENAME + " (" + colID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + colHeader + " TEXT," + colText + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLENAME);
        onCreate(db);
    }

    public void insertData(String nama, String alamat){
        String insertData = "INSERT INTO "+ TABLENAME + " ("+ colHeader +","+ colText +") VALUES ('"+nama +"', '"+alamat+"')";
        this.getWritableDatabase().execSQL(insertData);
    }

    public void updateData(int id, String nama, String alamat){
        String updateData = "UPDATE "+TABLENAME+ " SET "+ colHeader + "= '"+nama +"', "+ colText + "= '"+alamat + "' WHERE "+colID +" ="+id;
        this.getWritableDatabase().execSQL(updateData);
    }

    public void deleteData(int id){
        String deleteData = "DELETE FROM "+TABLENAME +" WHERE id="+id;
        this.getWritableDatabase().execSQL(deleteData);
    }

    public NoteModel getData(int id){
        NoteModel model = null;
        String selectData = "SELECT * FROM "+TABLENAME + " WHERE id="+String.valueOf(id);
        Cursor data = this.getWritableDatabase().rawQuery(selectData, null);
        if(data.moveToFirst()){
            model = new NoteModel(Integer.parseInt(data.getString(data.getColumnIndex(colID))), data.getString(data.getColumnIndex(colHeader)), data.getString(data.getColumnIndex(colText)));
        }
        return model;
    }

    public List<NoteModel> getAll(){
        List<NoteModel> model = new ArrayList<>();
        String selectData = "SELECT * FROM "+TABLENAME;
        Cursor data = this.getWritableDatabase().rawQuery(selectData, null);
        if(data.moveToFirst()){
            do{
                model.add(new NoteModel(Integer.parseInt(data.getString(data.getColumnIndex(colID))), data.getString(data.getColumnIndex(colHeader)), data.getString(data.getColumnIndex(colText))));
            }while (data.moveToNext());
        }
        return model;
    }
}
