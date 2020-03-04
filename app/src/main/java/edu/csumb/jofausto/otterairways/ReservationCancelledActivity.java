package edu.csumb.jofausto.otterairways;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class ReservationCancelledActivity extends AppCompatActivity implements View.OnClickListener {

    private Button acceptButton;
    private SystemDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_cancelled);

        acceptButton = findViewById(R.id.acceptButton);
        acceptButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = getIntent();

        Intent mainMenu = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainMenu);
    }
}