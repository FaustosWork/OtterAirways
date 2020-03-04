package edu.csumb.jofausto.otterairways;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.Cursor;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private SystemDB db;
    private static int counter;
    private Button submitButton;
    private EditText usernameText;
    private EditText passwordText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        counter = 2;
        submitButton = findViewById(R.id.loginSubmitButton);
        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        db  = new SystemDB(this);
        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        Cursor cursor = db.verifyLogin(username, password);
        Intent currentActivity = getIntent();
        String activity = currentActivity.getStringExtra("activity");
        if(activity.equals("manageSystem")) {
            if(username.equals("admin2") && password.equals("admin2")) {
                Intent manageSystem = new Intent((getApplicationContext()), ManageSystemActivity.class);
                startActivity(manageSystem);
            } else {
                counter--;
                if(counter == 0) {
                    Intent intent = new Intent(getApplicationContext(), ErrorLoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Incorrect username or password", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            if (cursor.getCount() == 0) {
                counter--;
                if (counter == 0) {
                    Intent intent = new Intent(getApplicationContext(), ErrorLoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Incorrect username or password", Toast.LENGTH_LONG).show();
                }
            } else {
                if (activity.equals("selectFlight")) {
                    Integer numTickets = Integer.parseInt(currentActivity.getStringExtra("numOfTickets"));
                    String flightNo = currentActivity.getStringExtra("flightNo");
                    Intent confirmFlight = new Intent(getApplicationContext(), ConfirmReservationActivity.class);
                    confirmFlight.putExtra("numOfTickets", numTickets.toString());
                    confirmFlight.putExtra("flightNo", flightNo);
                    confirmFlight.putExtra("account", username);
                    confirmFlight.putExtra("repeated", "No");
                    startActivity(confirmFlight);
                } else if (activity.equals("deleteReservation")) {
                    Intent deleteReservation = new Intent(getApplicationContext(), DeleteReservationActivity.class);
                    deleteReservation.putExtra("username", username);
                    startActivity(deleteReservation);
                }
            }
        }
    }
}
