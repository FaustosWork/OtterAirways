package edu.csumb.jofausto.otterairways;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.database.Cursor;
import android.widget.TextView;
import android.widget.Toast;


public class ConfirmReservationActivity extends AppCompatActivity
        implements View.OnClickListener {

    private SystemDB db;
    private TextView flightInformation;
    private Button acceptButton;
    private Button cancelButton;
    private String reservationNumber;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new SystemDB(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_reservation);
        text = "";

        flightInformation = findViewById(R.id.flightInformationText);

        Intent intent = getIntent();
        String repeat = intent.getStringExtra("repeated");
        if(repeat.equals("Yes")) {
            text += intent.getStringExtra("ReservationInfo");
        } else {
            Integer numOfTickets = Integer.parseInt(intent.getStringExtra("numOfTickets"));
            String flightNo = intent.getStringExtra("flightNo");
            String account = intent.getStringExtra("account");
            Cursor cursor = db.getFlight(flightNo);

            reservationNumber = db.bookFlight(flightNo, numOfTickets, account);

            text += "Customer: " + account + "\n";

            while (cursor.moveToNext()) {
                text += "Flight No: " + cursor.getString(0) + "\n";
                text += "Departure: " + cursor.getString(1) + "\n";
                text += "Arrival: " + cursor.getString(2) + "\n";
                text += "Time: " + cursor.getString(3) + "\n";
                text += "Reservation Number: " + reservationNumber + "\n";
                text += "Number of tickets: " + numOfTickets + "\n";
                text += "Amount Owed: " + db.getAmountOwed(numOfTickets, flightNo) + "\n";
                text += "\n";
            }
        }
        flightInformation.setText(text);

        acceptButton = findViewById(R.id.confirmFlightButton);
        cancelButton = findViewById(R.id.cancelFlightButton);

        acceptButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.confirmFlightButton:
                Intent confirm = new Intent(getApplicationContext(), MainActivity.class);
                db.logNewReservation(reservationNumber);
                Toast.makeText(ConfirmReservationActivity.this, "Reservation Created", Toast.LENGTH_LONG).show();
                startActivity(confirm);
                break;
            case R.id.cancelFlightButton:
                Intent confirmCancel = new Intent(getApplicationContext(), CancelReservationActivity.class);
                confirmCancel.putExtra("ReservationInfo", text);
                confirmCancel.putExtra("reservationNumber", reservationNumber);
                startActivity(confirmCancel);
                break;
        }
    }
}
