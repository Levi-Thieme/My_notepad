package com.example.signo.my_notepad.Models;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.signo.my_notepad.R;

import java.util.ArrayList;

/**
 * Created by signo on 5/29/2017.
 */

public class NoteAdapter extends ArrayAdapter {
    private ArrayList<Note> noteList;

    public NoteAdapter(Context context, int resource, int textViewResourceId, ArrayList<Note> notes){
        super(context, resource, textViewResourceId, notes);

        noteList = notes;
    }

    @Override
    public Note getItem(int position){
       return noteList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        //Get the data item for this position
        Note note = (Note) getItem(position);

        //Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_item_layout, parent, false);
        }

        //Lookup view for data population
        TextView noteTitle = (TextView) convertView.findViewById(R.id.noteTitle);


        //Populate the data into the template view using the data object
        noteTitle.setText(note.getTitle().toString());

        //Return the completed view to render on screen
        return convertView;

    }

    public void refreshItems(ArrayList<Note> notes){
        this.clear();
        this.addAll(notes);
        this.notifyDataSetChanged();
    }
}
