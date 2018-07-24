package kojo.mything;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

public class Settings extends AppCompatActivity {

    private EditText mqtt_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
        mqtt_address = findViewById(R.id.mqtt_address);
        mqtt_address.setText(MainActivity.server_uri);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int menuitemid = item.getItemId();
        mqtt_address = findViewById(R.id.mqtt_address);

        if (menuitemid == R.id.action_settings_save) {
            SharedPreferences prefs = getSharedPreferences("KoJo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            Gson gson = new Gson();
            String serveruri = mqtt_address.getText().toString();
            String json = gson.toJson(serveruri);
            editor.putString("Settings", json);
            editor.commit();

            MainActivity.server_uri = serveruri;
        }

        return super.onOptionsItemSelected(item);
    }
}
