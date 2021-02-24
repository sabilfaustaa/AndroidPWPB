package com.android.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<NoteModel> list = new ArrayList<>();
    DatabaseHelper dbhelp;
    Button insert;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbhelp = new DatabaseHelper(this);
        insert = (Button)findViewById(R.id.insert);
        recyclerView = (RecyclerView)findViewById(R.id.recylerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InsertActivity.class));
            }
        }) ;
    }

    @Override
    protected void onResume() {
        super.onResume();
        list = dbhelp.getAll();
        adapter = new ListAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder>{
        Context context;
        List<NoteModel> list;

        public ListAdapter(Context context, List<NoteModel> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data, parent, false);
            return new ListHolder(v);
        }

        @Override
        public void onBindViewHolder(ListHolder holder, int position) {
            final NoteModel model = list.get(position);
            holder.header.setText(model.getHeader());
            holder.text.setText(model.getText());
            holder.update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                    intent.putExtra("ID", model.getId());
                    startActivity(intent);
                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Yakin Menghapus Data?");
                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dbhelp.deleteData(model.getId());
                            dialogInterface.dismiss();
                            notifyDataSetChanged();
                            startActivity(getIntent());
                        }
                    });
                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ListHolder extends RecyclerView.ViewHolder{
            TextView header, text;
            Button update, delete;
            public ListHolder(View itemView) {
                super(itemView);
                header = (TextView)itemView.findViewById(R.id.headerCard);
                text = (TextView)itemView.findViewById(R.id.textCard);
                update = (Button)itemView.findViewById(R.id.btnUpdate);
                delete = (Button)itemView.findViewById(R.id.btnDelete);
            }
        }
    }
}

