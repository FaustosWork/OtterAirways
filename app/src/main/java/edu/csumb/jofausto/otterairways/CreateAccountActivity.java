package edu.csumb.jofausto.otterairways;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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

public class CreateAccountActivity extends AppCompatActivity
        implements OnClickListener{

    //private AirlineTicketReservationSystem airline = AirlineTicketReservationSystem.getInstance();
    private Button submitButton;
    private EditText usernameText;
    private EditText passwordText;
    private SystemDB db;
    private static int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        submitButton = (Button) findViewById(R.id.createAccountSubmitButton);
        usernameText = (EditText) findViewById(R.id.usernameText);
        passwordText = (EditText) findViewById(R.id.passwordText);

        submitButton.setOnClickListener(this);
        db  = new SystemDB(this);
        counter = 2;

    }

    private boolean isValid(String info) {

        if(info.length() < 4 ) {
            return false;
        }
        int numberCounter = 1;
        int letterCounter = 3;
        for(int i = 0; i < info.length(); ++i) {
            if(Character.isDigit(info.charAt(i))) {
                numberCounter--;
            } else if(Character.isLetter(info.charAt(i))) {
                letterCounter--;
            }
        }

        if(letterCounter > 0 || numberCounter > 0) {
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        Intent error = new Intent(getApplicationContext(), ErrorCreateAccountActivity.class);
        if(! (isValid(username) && isValid(password)) ) {
            counter --;
            if(counter == 0) {
                startActivity(error);
            }
            Toast.makeText(CreateAccountActivity.this, "Incorrect username or password format", Toast.LENGTH_LONG).show();
            usernameText.setText("");
            passwordText.setText("");
        }
        else if(!db.addAccount(username, password)) {
            counter--;
            if(counter == 0) {
                startActivity(error);
            }
            Toast.makeText(CreateAccountActivity.this, "Account username already exists", Toast.LENGTH_LONG).show();
            usernameText.setText("");
            passwordText.setText("");
        } else {
            db.logNewAccount(username);
            Toast.makeText(CreateAccountActivity.this, "Account " + username + " has been created", Toast.LENGTH_LONG).show();
            Intent returnMain = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(returnMain);
        }
    }
}
