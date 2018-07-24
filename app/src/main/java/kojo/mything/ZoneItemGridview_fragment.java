package kojo.mything;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ZoneItemGridview_fragment extends Fragment {

    GridView gridView;
    View rootView;

    @Override
    public void onResume() {
        gridView.setAdapter(MainActivity.itemsadapter);
        super.onResume();
    }

    @Override
    public void onPause() {
        gridView.setAdapter(null);
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_item_gridview, container, false);

        gridView = rootView.findViewById(R.id.fragment_gridview);
        //gridView.getAdapter();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            gridView.setAdapter(MainActivity.zoneitemadapter);
            Boolean Zoneitem = bundle.getBoolean("ZoneitemView");
            if (Zoneitem){
                gridView.setAdapter(MainActivity.zoneitemadapter);
//                gridView.setAdapter( new itemadapter(getContext(), android.R.layout.simple_list_item_1, MainActivity.myZoneItems));
            } else {
                gridView.setAdapter(MainActivity.itemsadapter);
//                gridView.setAdapter( new itemadapter(getContext(), android.R.layout.simple_list_item_1, MainActivity.myItemList));
            }
        }
//        gridView.getAdapter();

        gridView.setOnItemClickListener(OnGridItemClick);
        gridView.setOnItemLongClickListener(OnGridItemLongclick);

        //return inflater.inflate(R.layout.fragment_item_gridview, container, false);

        return rootView;
    }

    AdapterView.OnItemClickListener OnGridItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            ItemObject selectedItem = (ItemObject) adapterView.getAdapter().getItem(i);
//
//            Integer id = selectedItem.getId();
//            String name = selectedItem.getName();
//
//            Toast toast = Toast.makeText(getContext(), "Item " + i + ": " + id.toString() +" / "+ name, Toast.LENGTH_SHORT);
//            toast.show();
            if (selectedItem.getActor()) {

                String messageON = "1";
                String messageOFF= "0";
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                String timestamp =  df.format(c);

                ImageView myicon = adapterView.findViewById(R.id.imageView);

                if (selectedItem.getDataValue().equals("1")){
                    try {
                        MainActivity.mqttHelper.mqttAndroidClient.publish(selectedItem.getPublishTopic(), new MqttMessage(messageOFF.getBytes()));
                        selectedItem.setStatus("Published on: " + timestamp);
                        Toast toast = Toast.makeText(getContext(), "published 1", Toast.LENGTH_SHORT);
                        toast.show();

                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        MainActivity.mqttHelper.mqttAndroidClient.publish(selectedItem.getPublishTopic(), new MqttMessage(messageON.getBytes()));
                        selectedItem.setStatus("Published on: " + timestamp);
                        Toast toast = Toast.makeText(getContext(), "published 0", Toast.LENGTH_SHORT);
                        toast.show();

                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                MainActivity.itemsadapter.notifyDataSetChanged();

            }

        }
    };

    AdapterView.OnItemLongClickListener OnGridItemLongclick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//            final int selectedItem = ((int) adapterView.getItemIdAtPosition(i));

            final ItemObject selectedItem = (ItemObject) adapterView.getAdapter().getItem(i);

            PopupMenu myPopup = new PopupMenu(getContext(), view);

            myPopup.getMenuInflater().inflate(R.menu.mything_popup, myPopup.getMenu());

            myPopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {

                    String itemTitle = item.getTitle().toString();
                    Boolean foundzone = false;

                    //if (itemTitle.equals("@strings/myPopup01")){
                    if (itemTitle.equals("Delete...")){
                        try {
                            MainActivity.mqttHelper.mqttAndroidClient.unsubscribe(selectedItem.getTopic());
                        }
                        catch (MqttException ex){

                        }

                        MainActivity.myItemList.remove(selectedItem);

                        String zone = selectedItem.getZone();

                        for (int i = 0; i < MainActivity.myItemList.size(); i++){
                            if (MainActivity.myItemList.get(i).getZone().equals(zone)){
                                foundzone = true;
                            }
                        }

                        if (!foundzone){
                            for (int i = 0; i< MainActivity.myZoneList.size(); i++) {
                                if (MainActivity.myZoneList.get(i).getZone().equals(zone)){
                                    MainActivity.myZoneList.remove(MainActivity.myZoneList.get(i));
                                    break;
                                }
                            }
                        }
                    }

                    //else if (itemTitle.equals("@strings/myPopup02")){
                    if (itemTitle.equals("Edit...")){
                        Intent intent = new Intent(getContext(), ItemConfiguration.class);

                        Bundle mBundle = new Bundle();

                        Integer id = selectedItem.getId();
                        String name = selectedItem.getName();
                        String zone = selectedItem.getZone();
                        String type = selectedItem.getType();
                        String topic = selectedItem.getTopic();
                        Boolean actor = selectedItem.getActor();
                        String publish = selectedItem.getPublishTopic();

                        mBundle.putInt("id", id);
                        mBundle.putString("name", name);
                        mBundle.putString("topic", topic);
                        mBundle.putString("zone", zone);
                        mBundle.putString("type", type);
                        mBundle.putBoolean("actor", actor);
                        mBundle.putString("publish", publish);

                        intent.putExtras(mBundle);
                        startActivity(intent);
                    }


                    MainActivity.itemsadapter.notifyDataSetChanged();
                    MainActivity.zoneitemadapter.notifyDataSetChanged();
                    MainActivity.zoneadapter.notifyDataSetChanged();

                    SharedPreferences prefs = getContext().getSharedPreferences("KoJo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    Gson gson = new Gson();
                    String item_json = gson.toJson(MainActivity.myItemList);
                    String zone_json = gson.toJson(MainActivity.myZoneList);
                    editor.putString("MyObject", item_json);
                    editor.putString("MyZone", zone_json);
                    editor.apply();

                    return true;
                }
            });

            myPopup.show();

            return true;
        }
    };

}
