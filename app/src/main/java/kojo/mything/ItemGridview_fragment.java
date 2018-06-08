package kojo.mything;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.time.chrono.MinguoChronology;

public class ItemGridview_fragment extends Fragment {

    GridView gridView;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_item_gridview, container, false);

        gridView = (GridView) rootView.findViewById(R.id.fragment_gridview);
        gridView.setAdapter(MainActivity.itemadapter);

        gridView.setOnItemClickListener(OnGridItemClick);
        gridView.setOnItemLongClickListener(OnGridItemLongclick);

        //return inflater.inflate(R.layout.fragment_item_gridview, container, false);

        return rootView;
    }

    AdapterView.OnItemClickListener OnGridItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            final int selectedItem = ((int) adapterView.getItemIdAtPosition(i));
            Integer id = selectedItem;

            Toast toast = Toast.makeText(getContext(), "Zone: " + i, Toast.LENGTH_SHORT);
            toast.show();

        }
    };

    AdapterView.OnItemLongClickListener OnGridItemLongclick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            final int selectedItem = ((int) adapterView.getItemIdAtPosition(i));

            PopupMenu myPopup = new PopupMenu(getContext(), view);

            myPopup.getMenuInflater().inflate(R.menu.mything_popup, myPopup.getMenu());

            myPopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {

                    String itemTitle = item.getTitle().toString();

                    //if (itemTitle.equals("@strings/myPopup01")){
                    if (itemTitle.equals("Delete...")){
                        try {
                            MainActivity.mqttHelper.mqttAndroidClient.unsubscribe(MainActivity.myItemList.get(selectedItem).getTopic().toString());
                        }
                        catch (MqttException ex){

                        }
                        MainActivity.myItemList.remove(MainActivity.myItemList.get(selectedItem));

                    }

                    //else if (itemTitle.equals("@strings/myPopup02")){
                    if (itemTitle.equals("Edit...")){
                        Intent intent = new Intent(getContext(), ItemConfiguration.class);

                        Bundle mBundle = new Bundle();

                        Integer id = selectedItem;
                        String name = MainActivity.myItemList.get(selectedItem).getName().toString();
                        String zone = MainActivity.myItemList.get(selectedItem).getZone().toString();
                        String type = MainActivity.myItemList.get(selectedItem).getType().toString();
                        String topic = MainActivity.myItemList.get(selectedItem).getTopic().toString();
                        Boolean actor = MainActivity.myItemList.get(selectedItem).getActor();
                        String publish = MainActivity.myItemList.get(selectedItem).getPublishTopic();

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

                    MainActivity.itemadapter.notifyDataSetChanged();
                    return true;
                }
            });

            myPopup.show();

            return true;
        }
    };

}
