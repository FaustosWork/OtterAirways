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
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.app.Activity;
import android.widget.Toast;
import android.content.Intent;

public class CancelReservationActivity extends AppCompatActivity implements View.OnClickListener{

    private Button confirm;
    private Button cancel;
    private SystemDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_reservation);
        db = new SystemDB(this);

        confirm = findViewById(R.id.confirmCancelButton);
        cancel = findViewById(R.id.cancelCancellationButton);

        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancelCancellationButton:
                Intent intent = getIntent();
                String reservationInfo = intent.getStringExtra("ReservationInfo");
                String reservationNum = intent.getStringExtra("reservationNumber");
                Intent tryAgain = new Intent(getApplicationContext(), ConfirmReservationActivity.class);
                tryAgain.putExtra("repeated", "Yes");
                tryAgain.putExtra("ReservationInfo", reservationInfo);
                tryAgain.putExtra("reservationNumber", reservationNum);
                startActivity(tryAgain);
                break;
            case R.id.confirmCancelButton:
                Intent activity = getIntent();
                String resNum = activity.getStringExtra("reservationNumber");
                db.deleteReservation(resNum);
                Intent reservationCancelled = new Intent(getApplicationContext(), ReservationCancelledActivity.class);
                startActivity(reservationCancelled);
                break;
        }
    }
}
