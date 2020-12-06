package com.example.notes.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.notes.R;
import com.example.notes.data.DatabaseHandler;
import com.example.notes.model.Item;

import java.util.List;

public class RecyclerViewAdapter extends Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Item> itemList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    public RecyclerViewAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.noteTitleEl.setText("Note: " + item.getNoteTitle());
        holder.noteTextEl.setText("Description: " +item.getNoteText());
        holder.noteDateEl.setText("Created at: " +item.getDateNoteAdded());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView noteTitleEl;
        public TextView noteTextEl;
        public TextView noteDateEl;
        public ImageButton editBtnEl;
        public ImageButton deleteBtnEl;
        public  int id;
        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            noteTitleEl = itemView.findViewById(R.id.note_title_el);
            noteTextEl = itemView.findViewById(R.id.note_text_el);
            noteDateEl = itemView.findViewById(R.id.note_date_el);
            editBtnEl = itemView.findViewById(R.id.editBtn);
            deleteBtnEl = itemView.findViewById(R.id.deleteBtn);

            editBtnEl.setOnClickListener(this);
            deleteBtnEl.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position;
            switch (v.getId()){
                case R.id.editBtn:
                    break;
                case  R.id.deleteBtn:
                    position = getAdapterPosition();
                    Item item = itemList.get(position);
                    deleteItem(item.getId());
                    break;
            }
        }

        private void deleteItem (int id) {

            builder = new AlertDialog.Builder(context);

            inflater = LayoutInflater.from(context);
            View view= inflater.inflate(R.layout.confirm, null);

            Button noBtn = view.findViewById(R.id.alert_no_btn);
            Button yesBtn = view.findViewById(R.id.alert_yes_btn);

            builder.setView(view);
            dialog=builder.create();
            dialog.show();

            noBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            yesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db = new DatabaseHandler(context);
                    db.deleteNote(id);

                    itemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    dialog.dismiss();
                }
            });



        }

    }
}
