package edu.csumb.jofausto.otterairways;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.app.Activity;
import android.widget.Toast;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ReserveSeatActivity extends AppCompatActivity implements OnClickListener{

    private EditText numOfTickets;
    private Button submitButton;
    private RadioButton button;
    private RadioGroup departureLocation;
    private RadioGroup arrivalLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_seat);
        submitButton = (Button) findViewById(R.id.submit);
        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        departureLocation = findViewById(R.id.departGroup);
        arrivalLocation = findViewById(R.id.arrivalGroup);

        int departureButtonId = departureLocation.getCheckedRadioButtonId();
        int arrivalButtonId = arrivalLocation.getCheckedRadioButtonId();

        button = departureLocation.findViewById(departureButtonId);
        String departure = (String) button.getText();

        button = arrivalLocation.findViewById(arrivalButtonId);

        String arrival = (String) button.getText();

        numOfTickets = (EditText) findViewById(R.id.numOFTickets);

        Integer ticketsWanted = Integer.parseInt(numOfTickets.getText().toString());

        if(ticketsWanted > 7 || ticketsWanted <=0 ) {
            Intent noTickets = new Intent(getApplicationContext(), NoTicketsActivity.class);
            startActivity(noTickets);
        } else {
            SystemDB db = new SystemDB(this);
            Cursor cursor = db.getFlights(departure,  arrival, ticketsWanted);
            String text = "";

            if(cursor.getCount() == 0) {
                text += "empty";
            } else {
                while (cursor.moveToNext()) {
                    text += "Flight No: " + cursor.getString(0) + "\n";
                    text += "Departure: " + cursor.getString(1) + "\n";
                    text += "Arrival: " + cursor.getString(2) + "\n";
                    text += "Time: " + cursor.getString(3) + "\n";
                    text += "Tickets Available: " + cursor.getString(4) + "\n";
                    text += "Price: " + cursor.getString(5) + "\n";
                    text += "\n";
                    text += "\n";
                }
            }
            Intent reservation = new Intent(getApplicationContext(), SelectFlightActivity.class);
            reservation.putExtra("flightInformation", text);
            reservation.putExtra("numOfTickets", ticketsWanted.toString());
            startActivity(reservation);
        }

    }
}
