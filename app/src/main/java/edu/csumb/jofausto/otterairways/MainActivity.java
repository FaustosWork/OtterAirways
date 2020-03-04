package edu.csumb.jofausto.otterairways;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button createAccountButton,
            reserveSeatButton,
            cancelReservationButton,
            manageSystemButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createAccountButton = (Button) findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createAccount =
                        new Intent(getApplicationContext(), CreateAccountActivity.class);
                createAccount.putExtra("Tries", 2);
                startActivity(createAccount);
            }
        });

        reserveSeatButton = (Button) findViewById(R.id.reserveSeatButton);
        reserveSeatButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reserveSeat =
                        new Intent(getApplicationContext(), ReserveSeatActivity.class);
                startActivity(reserveSeat);
            }
        });

        cancelReservationButton = (Button) findViewById(R.id.cancelReservationButton);
        cancelReservationButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancelReservation =
                        new Intent(getApplicationContext(), LoginActivity.class);
                cancelReservation.putExtra("activity", "deleteReservation");
                startActivity(cancelReservation);
            }
        });

        manageSystemButton = (Button) findViewById(R.id.manageSystemButton);
        manageSystemButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent manageSystem =
                        new Intent(getApplicationContext(), LoginActivity.class);
                manageSystem.putExtra("activity", "manageSystem");
                startActivity(manageSystem);
            }
        });


    }

}