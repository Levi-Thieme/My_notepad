package com.example.signo.my_notepad.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.signo.my_notepad.Models.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by signo on 5/28/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notes_DB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NOTES = "notes";

    //notes Table Column names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "name";
    private static final String KEY_TEXT = "text";


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NOTES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_TEXT + " TEXT" + ")";

        db.execSQL(CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);

        onCreate(db);
    }

    public void addNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.getTitle());
        values.put(KEY_TEXT, note.getText());

        db.insert(TABLE_NOTES, null, values);
        db.close();
    }

    public Note getNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_NOTES, new String[] {KEY_ID,
            KEY_TITLE, KEY_TEXT}, KEY_ID + "=?",
               new String[] {String.valueOf(id)}, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        Note note = new Note(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));

        cursor.close();

        return note;

    }

    public List<Note> getAllNotes(){
        List<Note> noteList = new ArrayList<Note>();

        String selectQuery = "SELECT * FROM " + TABLE_NOTES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do{
                Note note = new Note();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setText(cursor.getString(2));

                noteList.add(note);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return noteList;
    }

    public void deleteAllNotes(){
        List<Note> noteList = this.getAllNotes();

        for(int i = 0; i < noteList.size(); i ++){
            this.deleteNote(noteList.get(i));
        }

    }


    public int getNoteCount() {
        String countQuery = "SELECT * FROM " + TABLE_NOTES;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.getTitle());
        values.put(KEY_TEXT, note.getText());

        return db.update(TABLE_NOTES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(note.getId()) });
    }

    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, KEY_ID + " = ?",
                new String[] { String.valueOf(note.getId()) });
        db.close();
    }




}
