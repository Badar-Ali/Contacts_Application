package com.example.contactsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int id = 1;
    DatabaseHelper mDatabaseHelper;
    List<EditText> allEds = new ArrayList<EditText>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseHelper = new DatabaseHelper(this);

        final LinearLayout layout = findViewById(R.id.addNumberLayout);

        Button fab = findViewById(R.id.addNumber);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = new EditText(getApplicationContext());
                editText.setHint("Add another number");
                editText.setId(id);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(500,150);
                editText.setLayoutParams(layoutParams);
                layout.addView(editText);
                allEds.add(editText);
                id++;
            }
        });

        final Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText firstName = findViewById(R.id.text1);
                EditText lastName = findViewById(R.id.text2);
                EditText number = findViewById(R.id.text3);
                EditText email = findViewById(R.id.text4);

                String fName = firstName.getText().toString();
                String lName = lastName.getText().toString();
                String num = number.getText().toString();
                String mail = email.getText().toString();

                int size = allEds.size();

                String[] strings = new String[size];

                for(int i=0; i < allEds.size(); i++){
                    strings[i] = allEds.get(i).getText().toString();
                    num += "\n" + strings[i].toString();
                }
                if (firstName.length() != 0 && lastName.length() != 0 && number.length() != 0 && email.length() != 0) {
                    AddData(fName,lName,num,mail);
                    firstName.setText("");
                    lastName.setText("");
                    number.setText("");
                    email.setText("");

                    Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),"You must put something in the text field!",Toast.LENGTH_LONG).show();
                }

            }
        });

        Button viewList = findViewById(R.id.viewList);

        viewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
                startActivity(intent);
            }
        });
    }
    public void AddData(String firstName, String lastName, String number, String email)
    {
        boolean insertData = mDatabaseHelper.addData(firstName, lastName, number, email);

        if (insertData) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Contact Added")
                    .setContentText("Name: " + firstName + " " + lastName + "\n" + number)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            Toast.makeText(getApplicationContext(),"Data Successfully Inserted!",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
        }
    }

}
