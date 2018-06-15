package kojo.mything;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<ItemObject> myItemList = new ArrayList<ItemObject>();
    public static ArrayList<ZoneObject> myZoneList = new ArrayList<ZoneObject>();
    public static ArrayList<ItemObject> myZoneItems = new ArrayList<ItemObject>();
    public static itemadapter itemsadapter;
    public static itemadapter zoneitemadapter;
    public static zoneitemadapter zoneadapter;
    public static MqttHelper mqttHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            SharedPreferences appsharedprefs = getSharedPreferences("KoJo", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String item_json = appsharedprefs.getString("MyObject", "");
            String zone_json = appsharedprefs.getString("MyZone", "");
            Type item_type = new TypeToken<ArrayList<ItemObject>>() {}.getType();
            Type zone_type = new TypeToken<ArrayList<ZoneObject>>() {}.getType();

            if (item_json !=null && item_type != null) {
                ArrayList<ItemObject> itemlist = gson.fromJson(item_json, item_type);
                ArrayList<ZoneObject> zonelist = gson.fromJson(zone_json, zone_type);
                //userList = (ArrayList<ItemObject>) ObjectSerializer.deserialize(prefs.getString("ObjectList", ObjectSerializer.serialize(new ArrayList<ItemObject>())));
                if (itemlist != null) {
                    myItemList = itemlist;
                }
                if (zonelist != null) {
                    myZoneList = zonelist;
                }
            }
        }
        catch (ClassCastException e){

        }
        itemsadapter = new itemadapter(this, android.R.layout.simple_list_item_1, myItemList);
        zoneadapter = new zoneitemadapter(this, android.R.layout.simple_list_item_1, myZoneList);
        zoneitemadapter = new itemadapter(this, android.R.layout.simple_list_item_1, myZoneItems);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Overview"));
        tabLayout.addTab(tabLayout.newTab().setText("Zones"));
        tabLayout.addTab(tabLayout.newTab().setText("Functions"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        final PageAdapter adapter = new PageAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        startMqtt();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Context context = getApplicationContext();
        int text = R.string.toast_addnew;
        int duration = Toast.LENGTH_SHORT;

        String content = new String();
        String dataValue = new String();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(context, Settings.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_addnew) {

            Intent intent = new Intent(context, ItemConfiguration.class);
            startActivity(intent);
            //Toast toast = Toast.makeText(context, text, duration);
            //toast.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startMqtt(){
        mqttHelper = new MqttHelper(getApplicationContext());
        mqttHelper.setCallback(new MqttCallbackExtended() {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            @Override
            public void connectComplete(boolean b, String s) {
                toolbar.setSubtitle("...connected");
                Toast toast = Toast.makeText(getApplicationContext(), "Conencted to MQTT!", Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void connectionLost(Throwable throwable) {
                toolbar.setSubtitle("...disconnected");
                Toast toast = Toast.makeText(getApplicationContext(), "MQTT connection lost", Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug",mqttMessage.toString());
                try {
                    int itemlistsize = myItemList.size();

                    for (int i = 0; i < itemlistsize; i++) {
                        if (myItemList.get(i).getTopic().toString().equals(topic)) {
                            myItemList.get(i).setDataValue(mqttMessage.toString());
                            itemsadapter.notifyDataSetChanged();
                        }
                    }
                }
                catch (Exception e){
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

//    private void loadFragment(Fragment fragment){
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        // Replace the contents of the container with the new fragment
//        ft.replace(R.id.gridfragment_placeholder, new ItemGridview_fragment());
//        // or ft.add(R.id.your_placeholder, new FooFragment());
//        // Complete the changes added above
//        ft.commit();
//    }

}
