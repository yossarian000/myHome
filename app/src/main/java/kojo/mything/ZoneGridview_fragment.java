package kojo.mything;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import static kojo.mything.MainActivity.zoneadapter;

public class ZoneGridview_fragment extends Fragment {


    @Override
    public void onResume() {
        gridView.setAdapter(MainActivity.zoneadapter);
        super.onResume();
    }

    GridView gridView;
    View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_zoneitem_gridview, container, false);

        gridView = (GridView) rootView.findViewById(R.id.fragment_zonitem_gridview);

        gridView.setAdapter(MainActivity.zoneadapter);

//        gridView.setAdapter( new zoneitemadapter(getContext(), android.R.layout.simple_list_item_1, MainActivity.myZoneList));

//        gridView.getAdapter();

        gridView.setOnItemClickListener(OnGridItemClick);
        gridView.setOnItemLongClickListener(OnGridItemLongclick);

        //return inflater.inflate(R.layout.fragment_item_gridview, container, false);

        return rootView;
    }

    AdapterView.OnItemClickListener OnGridItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            final int selectedItem = ((int) adapterView.getItemIdAtPosition(i));

            String selectedzone = MainActivity.myZoneList.get(i).getZone();

//            Toast toast = Toast.makeText(getContext(), "Zone: " + selectedzone, Toast.LENGTH_SHORT);
//            toast.show();

            Intent intent = new Intent(getContext(), ZoneItemView.class);
            Bundle mBundle = new Bundle();

            gridView = (GridView) adapterView.findViewById(R.id.fragment_zonitem_gridview);
//            gridView.setAdapter(null);

            String zone = selectedzone;
            mBundle.putString("zone", zone);
            intent.putExtras(mBundle);
            startActivity(intent);

        }
    };

    AdapterView.OnItemLongClickListener OnGridItemLongclick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

//            MainActivity.myZoneList.clear();
//            zoneadapter.notifyDataSetChanged();
            return true;
        }
    };
}
