package com.ameerhamza.sqlite;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SqlHelper myDb;
    EditText name, surname, marks;
    Button add, view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new SqlHelper(this);

        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        marks = findViewById(R.id.marks);
        add = findViewById(R.id.add);
        view = findViewById(R.id.view);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.isInserted(name.getText().toString(), surname.getText().toString(), marks.getText().toString());
                if (isInserted) {
                    Toast.makeText(MainActivity.this, "Data Inserted Sucessfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data not Inserted Sucessfully", Toast.LENGTH_LONG).show();
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAll();
                if (res.getCount() == 0) {
                    showMessage("ERROR", "NOTHING FOUND");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("ID : " + res.getString(0) + "\n");
                    buffer.append("NAME : " + res.getString(1) + "\n");
                    buffer.append("SURNAME : " + res.getString(2) + "\n");
                    buffer.append("MARKS : " + res.getString(3) + "\n\n");
                }
                showMessage("Data ", buffer.toString());
            }
        });


    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
