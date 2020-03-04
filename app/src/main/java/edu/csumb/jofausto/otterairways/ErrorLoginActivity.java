package edu.csumb.jofausto.otterairways;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class ErrorLoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button acceptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_login);

        acceptButton = findViewById(R.id.acceptButton);
        acceptButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent mainMenu = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainMenu);
    }
}
