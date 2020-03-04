package edu.csumb.jofausto.otterairways;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.database.Cursor;

public class ManageSystemActivity extends AppCompatActivity implements OnClickListener{

    private Button accept;
    private SystemDB db;
    private TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_system);

        accept = findViewById(R.id.acceptManageSystemButton);
        accept.setOnClickListener(this);

        text = findViewById(R.id.logInformation);

        db  = new SystemDB(this);
        Cursor cursor = db.getLog();
        String log = "";
        if(cursor.getCount() == 0) {
            log += "No Activity to display!";
        } else {
            while (cursor.moveToNext()) {
                log += cursor.getString(0);
                log += "\n";
                log += "\n";
            }
        }
        text.setText(log);
        text.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent((getApplicationContext()), ManageSystem2Activity.class);
        startActivity(intent);
    }
}
