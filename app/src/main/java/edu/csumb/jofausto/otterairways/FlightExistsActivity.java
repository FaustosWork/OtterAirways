package edu.csumb.jofausto.otterairways;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;

import org.w3c.dom.Text;

public class FlightExistsActivity extends AppCompatActivity implements View.OnClickListener{

    private Button accept;
    private TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_exists);

        Intent current = getIntent();
        accept = findViewById(R.id.acceptButton);

        accept.setOnClickListener(this);

        String flightNo = current.getStringExtra("flightNo");

        String message = "Error! Flight No: " + flightNo + " already exists!";

        errorMessage = findViewById(R.id.flightExistsText);

        errorMessage.setText(message);


    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
