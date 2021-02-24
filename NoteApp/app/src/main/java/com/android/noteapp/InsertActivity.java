package com.android.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity {
    EditText inputHeader, inputText;
    Button btnInsert;
    DatabaseHelper dbhelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        inputHeader = (EditText)findViewById(R.id.inputHeader);
        inputText = (EditText)findViewById(R.id.inputText);
        btnInsert = (Button)findViewById(R.id.btnInsert);
        dbhelp = new DatabaseHelper(this);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbhelp.insertData(inputHeader.getText().toString(), inputText.getText().toString());
                Toast.makeText(getApplicationContext(), "Data Tersimpan", Toast.LENGTH_SHORT).show();
                inputHeader.getText().clear();
                inputText.getText().clear();
                startActivity(new Intent(InsertActivity.this, MainActivity.class));
            }
        });
    }
}
