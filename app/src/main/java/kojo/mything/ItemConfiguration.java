package kojo.mything;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.MqttException;

public class ItemConfiguration extends AppCompatActivity {

    private Integer id;
    private EditText topic;
    private EditText name;
    private Spinner staticTypeSpinner;
    private Spinner staticZoneSpinner;
    private Switch actor;
    private Boolean itemedited = false;
    private Context mContext;
    private EditText publishinput;
    private TextView publish;

    private ArrayAdapter<CharSequence> staticTypeAdapter;
    private ArrayAdapter<CharSequence> staticZoneAdapter;

    MqttHelper mqttHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_configuration);

        Toolbar toolbar = (Toolbar) findViewById(R.id.Itemconfig_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });

        /*-------------------------------------------------------*/
        /*Configuring the input fields */

        staticTypeSpinner = findViewById(R.id.mything_typeinput);
        staticZoneSpinner = findViewById(R.id.mything_zoneinput);

        topic = findViewById(R.id.mything_topicnameinput);
        name = findViewById(R.id.mything_idinput);
        actor = findViewById(R.id.mything_actor);
        publish = findViewById(R.id.mything_publish);
        publishinput = findViewById(R.id.mything_publishinput);

        // Create an ArrayAdapter using the string array and a default spinner
        staticTypeAdapter = ArrayAdapter.createFromResource(this, R.array.Typelist, android.R.layout.simple_spinner_item);
        staticZoneAdapter = ArrayAdapter.createFromResource(this, R.array.Zonelist, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staticZoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticTypeSpinner.setAdapter(staticTypeAdapter);
        staticZoneSpinner.setAdapter(staticZoneAdapter);

        /*Getting data from where it was strated through bundle */
        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            id = bundle.getInt("id");
            name.setText(bundle.getString("name"));
            topic.setText(bundle.getString("topic"));

            int zonespinpos = staticZoneAdapter.getPosition(bundle.getString("zone"));
            staticZoneSpinner.setSelection(zonespinpos);

            int typespinpos = staticTypeAdapter.getPosition(bundle.getString("type"));
            staticTypeSpinner.setSelection(typespinpos);

            itemedited = true;

            /*Configuring the view, if the item is a button */
            Boolean setactor = bundle.getBoolean("actor");

            if (setactor) {
                actor.setChecked(true);
                publish.setVisibility(View.VISIBLE);
                publishinput.setVisibility(View.VISIBLE);
                publishinput.setText(bundle.getString("publish"));
            }
            else {
                publish.setVisibility(View.INVISIBLE);
                publishinput.setVisibility(View.INVISIBLE);
            }
        }
        else {
            publish.setVisibility(View.INVISIBLE);
            publishinput.setVisibility(View.INVISIBLE);
        }


        actor.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actor.isChecked()){
                    publish.setVisibility(View.VISIBLE);
                    publishinput.setVisibility(View.VISIBLE);
                }
                else{
                    publish.setVisibility(View.INVISIBLE);
                    publishinput.setVisibility(View.INVISIBLE);
                }
            }
        });

        staticTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        staticZoneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_itemconfig, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int menuitemid = item.getItemId();

        Context context = getApplicationContext();
        int text = R.string.toast_cfgerr_01;
        int duration = Toast.LENGTH_SHORT;

        String myType = staticTypeSpinner.getSelectedItem().toString();
        String myZone = staticZoneSpinner.getSelectedItem().toString();

        /** On save button click */
        if (menuitemid == R.id.action_itemconfig_save) {

            String typeimgsrc = "";
            String zoneimgsrc = "";
            Boolean zonefound = false;

            switch (myType) {
                case "Lamp":
                    typeimgsrc = "lamp03";
                    break;
                case "Temperature":
                    typeimgsrc = "temperature_inside_48";
                    break;
                case "Blinds":
                    typeimgsrc = "blinds02";
                    break;
                case "Socket":
                    typeimgsrc = "socket03";
                    break;
            }

            switch (myZone) {
                case "Livingroom":
                    zoneimgsrc = "sofa02";
                    break;
                case "Bedroom":
                    zoneimgsrc = "bedroom2";
                    break;
                case "Kitchen":
                    zoneimgsrc = "kitchen01";
                    break;
                case "Garage":
                    zoneimgsrc = "garage03";
                    break;
                case "Garden":
                    zoneimgsrc = "garden02";
                    break;
            }

//            imgsrc = "baseline_home_black_48dp";

            if (topic.getText().toString().equals("")){
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            else{
                if (itemedited) {
                    MainActivity.myItemList.get(id).setName(name.getText().toString());
                    MainActivity.myItemList.get(id).setTopic(topic.getText().toString());
                    MainActivity.myItemList.get(id).setZone(myZone);
                    MainActivity.myItemList.get(id).setType(myType);
                    MainActivity.myItemList.get(id).setActor(actor.isChecked());
                    MainActivity.myItemList.get(id).setImageResource(typeimgsrc);
                    MainActivity.myItemList.get(id).setPublishTopic(publishinput.getText().toString());

                    try {
                        //MainActivity.mqttHelper.mqttAndroidClient.subscribe("/sensors/hum01", 0);
                        MainActivity.mqttHelper.mqttAndroidClient.subscribe(topic.getText().toString(), 0);
                    }
                    catch (MqttException ex){

                    }
                    //MainActivity.adapter.notifyDataSetChanged();
                }
                /**creating new**/
                else {

                    int itemcount = MainActivity.myItemList.size();
                    Boolean found = false;
                    id = 0;

                    for (int i = 0; i< itemcount; i++){
                        for (ItemObject j : MainActivity.myItemList){
                            if (j.getId() == i){
                                found = true;
                            }
                        }
                        if (!found){
                            id = i;
                            break;
                        }
                        else {
                            id = i+1;
                        }
                        found = false;
                    }


                    MainActivity.myItemList.add(new ItemObject(id, name.getText().toString(), "",
                            typeimgsrc, topic.getText().toString(), myZone, myType, actor.isChecked(), publishinput.getText().toString(), ""));

                    try {
                        MainActivity.mqttHelper.mqttAndroidClient.subscribe(topic.getText().toString(), 0);
                    }
                    catch (MqttException ex){

                    }
                }


                MainActivity.itemsadapter.notifyDataSetChanged();

                int zonelistsize = MainActivity.myZoneList.size();

                if (zonelistsize == 0){
                    MainActivity.myZoneList.add(new ZoneObject(String.valueOf(zonelistsize), myZone, zoneimgsrc));
                    MainActivity.zoneadapter.notifyDataSetChanged();
                }
                else {
                    for (int i = 0; i < zonelistsize; i++) {
                        if (MainActivity.myZoneList.get(i).getZone().contains(myZone)) {
                            zonefound = true;
//                            MainActivity.myZoneList.add(new ZoneObject(Integer.toString(i), myZone, "outline_security_24"));
//                            MainActivity.zoneadapter.notifyDataSetChanged();
                        }
                    }

                    if (!zonefound){
                            MainActivity.myZoneList.add(new ZoneObject(String.valueOf(zonelistsize), myZone, zoneimgsrc));
                            MainActivity.zoneadapter.notifyDataSetChanged();

                    }
                }

                MainActivity.zoneitemadapter.notifyDataSetChanged();
                MainActivity.zoneadapter.notifyDataSetChanged();

                SharedPreferences prefs = getSharedPreferences("KoJo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                Gson gson = new Gson();
                String item_json = gson.toJson(MainActivity.myItemList);
                String zone_json = gson.toJson(MainActivity.myZoneList);
                editor.putString("MyObject", item_json);
                editor.putString("MyZone", zone_json);
                editor.apply();

                finish();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
