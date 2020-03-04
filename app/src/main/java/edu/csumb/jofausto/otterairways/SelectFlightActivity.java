package edu.csumb.jofausto.otterairways;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

public class SelectFlightActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView flightInfo;
    private Button submitButton;
    private EditText flightNoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_flight);
        flightInfo = (TextView) findViewById(R.id.flightInformation);
        Intent intent = getIntent();
        String flight = intent.getStringExtra("flightInformation");
        if(flight.equals("empty")) {
            Intent noFlights = new Intent(getApplicationContext(), NoFlightsActivity.class);
            startActivity(noFlights);
        }
        flightInfo.setText(flight);
        flightInfo.setMovementMethod(new ScrollingMovementMethod());
        submitButton = (Button) findViewById(R.id.submitFlightNoButton);
        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        flightNoText = (EditText) findViewById(R.id.flightNoText);
        String flightNo = flightNoText.getText().toString();

        Intent intent = getIntent();
        Integer numOfTickets = Integer.parseInt(intent.getStringExtra("numOfTickets"));
        Intent login = new Intent(getApplicationContext(), LoginActivity.class);
        login.putExtra("flightNo", flightNo);
        login.putExtra("numOfTickets", numOfTickets.toString());
        login.putExtra("activity", "selectFlight");
        startActivity(login);

    }
}
