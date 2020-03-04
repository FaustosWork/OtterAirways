package edu.csumb.jofausto.otterairways;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.database.Cursor;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class DeleteReservationActivity extends AppCompatActivity
        implements View.OnClickListener {

    private SystemDB db;
    private TextView reservations;
    String reservationInfo;
    private EditText reservationNumbertoDelete;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_reservation);
        db  = new SystemDB(this);

        reservationInfo = "";

        submit = findViewById(R.id.submitReservationButton);
        submit.setOnClickListener(this);

        Intent intent = getIntent();

        String username = intent.getStringExtra("username");

        Cursor cursor = db.getReservationsByOwner(username);


        if(cursor.getCount() == 0) {
            Intent noReservations = new Intent(getApplicationContext(), NoReservationsAvailableActivity.class);
            startActivity(noReservations);
        } else {
            reservations = findViewById(R.id.reservationInformation);
            while(cursor.moveToNext()) {
                reservationInfo += "Reservation Number: " + cursor.getString(0) + "\n";
                reservationInfo += "Username: " + cursor.getString(1) + "\n";
                reservationInfo += "Tickets Reserved: " + cursor.getString(2) + "\n";
                reservationInfo += "Flight Number: " + cursor.getString(3) + "\n";
                reservationInfo += "Amount Owed: " + cursor.getString(4) + "\n";
                reservationInfo += "\n";
                reservationInfo += "\n";
            }

            reservations.setText(reservationInfo);
            reservations.setMovementMethod(new ScrollingMovementMethod());
        }

    }
    @Override
    public void onClick(View view) {
        db  = new SystemDB(this);
        reservationNumbertoDelete = findViewById(R.id.reservationNoText);
        String resNum = reservationNumbertoDelete.getText().toString();

        Intent confirmDeletion = new Intent(getApplicationContext(), ConfirmDeletionActivity.class);
        confirmDeletion.putExtra("reservationNumber", resNum);
        startActivity(confirmDeletion);
    }
}
