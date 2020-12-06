package com.example.notes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.notes.data.DatabaseHandler;
import com.example.notes.model.Item;
import com.example.notes.ui.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class NotesListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Item> itemList;
    private DatabaseHandler databaseHandler;
    private FloatingActionButton floatingActionButton;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button saveButton;
    private EditText noteTitle;
    private EditText noteText;
    private TextView emptyFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        recyclerView = findViewById(R.id.notesContainer);
        floatingActionButton = findViewById(R.id.noteListBtn);
        emptyFolder = findViewById(R.id.empty_list_title);

        databaseHandler = new DatabaseHandler(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
        itemList = databaseHandler.getAllNotes();

        recyclerViewAdapter = new RecyclerViewAdapter(this, itemList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                callCreateNoteDialog(v);
            }
        });

        if (databaseHandler.getNotesCount() <= 0) {
            emptyFolder.setText(R.string.empty_folder);
        }

    }

    private void callCreateNoteDialog(View v) {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);

        noteTitle = view.findViewById(R.id.note_title_el);
        noteText = view.findViewById(R.id.note_text_el);
        saveButton = view.findViewById(R.id.save_btn);

        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!noteTitle.getText().toString().isEmpty()
                        && !noteText.getText().toString().isEmpty()) {
                    onSave(v);
                } else {
                    Snackbar.make(v, "Empty fields not allowed", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onSave(View v) {
        Item item = new Item();

        String title = noteTitle.getText().toString().trim();
        String text = noteText.getText().toString().trim();

        item.setNoteText(text);
        item.setNoteTitle(title);

        databaseHandler.addNote(item);

        Snackbar.make(v, "Note saved", Snackbar.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                startActivity(new Intent(NotesListActivity.this, NotesListActivity.class));
                finish();
            }
        }, 1000);
    }
}