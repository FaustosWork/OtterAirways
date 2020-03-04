package edu.csumb.jofausto.otterairways;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

public class DeletionCancelledActivity extends AppCompatActivity
        implements View.OnClickListener{

    private Button accept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletion_cancelled);

        accept = findViewById(R.id.acceptButton);
        accept.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent returnMain = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(returnMain);
    }
}
