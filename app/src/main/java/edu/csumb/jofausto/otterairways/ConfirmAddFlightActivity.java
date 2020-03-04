package edu.csumb.jofausto.otterairways;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class ConfirmAddFlightActivity extends AppCompatActivity implements OnClickListener{

    private Button confirm;
    private TextView text;
    private SystemDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_add_flight);

        confirm = findViewById(R.id.randomButtonName);
        confirm.setOnClickListener(this);

        text = findViewById(R.id.confirmFlight);
        Intent intent = getIntent();
        String flightNo = intent.getStringExtra("flightNo");

        db  = new SystemDB(this);

        Cursor flight = db.getFlight(flightNo);
        flight.moveToFirst();

        String message = "Confirm the information is correct.\n";

        message += "Flight No: " + flight.getString(0) + "\n";
        message += "Departure: " + flight.getString(1) + "\n";
        message += "Arrival: " + flight.getString(2) + "\n";
        message += "Time: " + flight.getString(3) + "\n";
        message += "Tickets Available: " + flight.getString(4) + "\n";
        message += "Price: " + flight.getString(5) + "\n";

        text.setText(message);


    }

    @Override
    public void onClick(View view) {
        Intent done = new Intent(getApplicationContext(), MainActivity.class);
        Toast.makeText(ConfirmAddFlightActivity.this, "Flight Added", Toast.LENGTH_LONG).show();
        startActivity(done);
    }
}
