package com.example.signo.my_notepad.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.signo.my_notepad.Data.DatabaseHelper;
import com.example.signo.my_notepad.Models.Note;
import com.example.signo.my_notepad.Models.NoteAdapter;
import com.example.signo.my_notepad.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    private ArrayList<Note> noteList;
    private ListView listView;
    private NoteAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        noteList = dbHelper.getAllNotes();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        this.getSupportActionBar().setTitle("Notes");


        adapter = new NoteAdapter(this, R.layout.note_item_layout, R.id.noteTitle, noteList);


        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = adapter.getItem(position);

                Intent intent = new Intent(MainActivity.this, noteView_activity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("note", note);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        registerForContextMenu(listView);


        FloatingActionButton addFab = (FloatingActionButton) findViewById(R.id.add_fab);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                addNote();
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();

        noteList = dbHelper.getAllNotes();
        adapter.refreshItems(noteList);

    }

    public void addNote(){

        EditText title_editText = (EditText) findViewById(R.id.title_editText);
        String title = title_editText.getText().toString();

        if(title.isEmpty()){
            Toast.makeText(this,
                    "You must enter a title before adding a note",
                    Toast.LENGTH_SHORT).show();

            return;
        }

        title_editText.setText("");

        Note newNote = new Note(dbHelper.getNoteCount() + 1, title, "");

        dbHelper.addNote(newNote);

        noteList = dbHelper.getAllNotes();
        adapter.refreshItems(noteList);
    }

    public void deleteNote(Note note){

        dbHelper.deleteNote(note);
        noteList = dbHelper.getAllNotes();

        adapter.refreshItems(noteList);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.listView) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) info;

        Note note = (Note) adapter.getItem(menuInfo.position);

        switch(item.getItemId()) {
            case R.id.delete:

                deleteNote(note);

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.dark_theme:
                getApplicationContext().setTheme(R.style.DarkGreenTheme);
                if (true) Log.v("Theme", "Theme Change to Dark");
                break;

            case R.id.light_theme:
                getApplicationContext().setTheme(R.style.LightTheme);
                if (true) Log.v("Theme", "Theme Change to Light");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
