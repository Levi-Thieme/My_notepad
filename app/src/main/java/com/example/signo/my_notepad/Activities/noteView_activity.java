package com.example.signo.my_notepad.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.signo.my_notepad.Data.DatabaseHelper;
import com.example.signo.my_notepad.Models.Note;
import com.example.signo.my_notepad.R;

import static android.util.Log.VERBOSE;

public class noteView_activity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    Note note;
    TextView noteText;
    boolean VERBOSE = true;
    String TAG = "TESTING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.noteView_toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DatabaseHelper(this);

        noteText = (TextView) findViewById(R.id.editText2);

        Bundle bundle = this.getIntent().getExtras();

        if(bundle != null){
            note = (Note) bundle.getSerializable("note");

            toolbar.setTitle(note.getTitle());
            noteText.setText(note.getText());

            this.getSupportActionBar().setTitle(note.getTitle());



            if(note.getText().equals(""))
                noteText.setHint("Add some text to your note");
        }
    }


    @Override
    public void onPause(){
        super.onPause();

        if (VERBOSE) Log.v(TAG, "++ ON Pause ++ \t"+ noteText.getText().toString());

        String note_contents = noteText.getText().toString();
        note.setText(note_contents);

        dbHelper.updateNote(note);
        dbHelper.close();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (VERBOSE) Log.v(TAG, "++ ON START ++");



    }

    @Override
    public void onResume() {
        super.onResume();
        if (VERBOSE) Log.v(TAG, "+ ON RESUME +");

    }

    @Override
    public void onStop() {
        super.onStop();
        if (VERBOSE) Log.v(TAG, "-- ON STOP --");


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (VERBOSE) Log.v(TAG, "- ON DESTROY -");



    }

}
