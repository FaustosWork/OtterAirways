package edu.csumb.jofausto.otterairways;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class NoReservationsAvailableActivity extends AppCompatActivity implements View.OnClickListener{

    private Button accept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_reservations_available);

        accept = findViewById(R.id.acceptButton);

        accept.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent returnMain = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(returnMain);
    }
}
