package edu.csumb.jofausto.otterairways;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.database.Cursor;
import android.widget.Button;
import android.content.Intent;


public class AddFlightActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText flightNo;
    private EditText departure;
    private EditText arrival;
    private EditText time;
    private EditText capacity;
    private EditText price;

    private String flightNoText;
    private String departureText;
    private String arrivalText;
    private String timeText;
    private Integer capacityText;
    private String priceText;

    public Button submit;

    private SystemDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight);


        submit = findViewById(R.id.submitButton);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        flightNo = findViewById(R.id.flightNoText);
        departure = findViewById(R.id.departureText);
        arrival = findViewById(R.id.arrivalText);
        time = findViewById(R.id.timeText);
        capacity = findViewById(R.id.capacitytext);
        price = findViewById(R.id.priceText);

        flightNoText = flightNo.getText().toString();
        departureText = departure.getText().toString();
        arrivalText = arrival.getText().toString();
        timeText = time.getText().toString();
        capacityText = Integer.parseInt(capacity.getText().toString());
        priceText = price.getText().toString();

        db  = new SystemDB(this);

        Cursor flight = db.getFlight(flightNoText);
        if(flight.getCount() == 0) {
            db.addFlight(flightNoText, departureText, arrivalText, timeText, capacityText, priceText);
            Intent confirm = new Intent(getApplicationContext(), ConfirmAddFlightActivity.class);
            confirm.putExtra("flightNo", flightNoText);
            startActivity(confirm);
        } else {
            Intent alreadyExists = new Intent(getApplicationContext(), FlightExistsActivity.class);
            alreadyExists.putExtra("flightNo", flightNoText);
            startActivity(alreadyExists);
        }
    }
}
