package edu.csumb.jofausto.otterairways;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;

public class ManageSystem2Activity extends AppCompatActivity implements View.OnClickListener{

    private Button yes;
    private Button no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_system2);

        yes = findViewById(R.id.yesButton);
        no = findViewById(R.id.noButton);

        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.yesButton:
                Intent addFlight = new Intent(getApplicationContext(), AddFlightActivity.class);
                startActivity(addFlight);
                break;
            case R.id.noButton:
                Intent returnMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(returnMain);
                break;

        }
    }
}
