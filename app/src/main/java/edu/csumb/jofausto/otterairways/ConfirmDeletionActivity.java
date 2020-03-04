package edu.csumb.jofausto.otterairways;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

public class ConfirmDeletionActivity extends AppCompatActivity implements View.OnClickListener{

    private Button confirm;
    private Button cancel;
    private SystemDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_deletion);
        confirm = findViewById(R.id.confirmDeletionButton);
        cancel = findViewById(R.id.cancelCancellationButton);

        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        db  = new SystemDB(this);
        switch (view.getId()) {
            case R.id.confirmDeletionButton:
                db.logDeletion();
                Toast.makeText(ConfirmDeletionActivity.this, "Reservation Otter102 was Deleted", Toast.LENGTH_LONG).show();
                Intent returnMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(returnMain);
                break;
            case R.id.cancelCancellationButton:
                Intent cancelled = new Intent(getApplicationContext(), DeletionCancelledActivity.class);
                startActivity(cancelled);
                break;
        }
    }
}
