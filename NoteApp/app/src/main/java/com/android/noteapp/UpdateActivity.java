package com.android.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateActivity extends AppCompatActivity {
    EditText inputHeader, inputText;
    Button btnInsert;
    NoteModel model;
    DatabaseHelper dbhelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        inputHeader = (EditText)findViewById(R.id.inputHeader);
        inputText = (EditText)findViewById(R.id.inputText);
        btnInsert = (Button)findViewById(R.id.btnInsert);
        dbhelp = new DatabaseHelper(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            model = dbhelp.getData(bundle.getInt("ID"));
            inputHeader.setText(model.getHeader());
            inputText.setText(model.getText());
        }
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbhelp.updateData(model.getId(), inputHeader.getText().toString(), inputText.getText().toString());
                finish();
            }
        });
    }
}
