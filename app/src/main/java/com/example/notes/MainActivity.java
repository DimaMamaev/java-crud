package com.example.notes;

import android.content.Intent;
import android.os.Bundle;

import com.example.notes.data.DatabaseHandler;
import com.example.notes.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button saveButton;
    private EditText noteTitle;
    private EditText noteText;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);

        databaseHandler = new DatabaseHandler(this);

        List<Item> items = databaseHandler.getAllNotes();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    createPopUpDialog();
            }
        });

        passToActivity();
    }



    private void passToActivity () {
        if (databaseHandler.getNotesCount() > 0) {
            startActivity(new Intent(MainActivity.this, NotesListActivity.class));
            finish();
        }
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

                startActivity(new Intent(MainActivity.this, NotesListActivity.class));
            }
        }, 1500);
    }

    private void createPopUpDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);

        noteTitle = view.findViewById(R.id.note_title_el);
        noteText = view.findViewById(R.id.note_text_el);
        saveButton = view.findViewById(R.id.save_btn);


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


        builder.setView(view);
        dialog = builder.create();
        dialog.show();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, NotesListActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}