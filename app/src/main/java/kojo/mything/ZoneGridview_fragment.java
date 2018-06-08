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

public class ZoneGridview_fragment extends Fragment {

    GridView gridView;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_item_gridview, container, false);

        gridView = (GridView) rootView.findViewById(R.id.fragment_gridview);
        gridView.setAdapter(MainActivity.zoneadapter);

        gridView.setOnItemClickListener(OnGridItemClick);
//        gridView.setOnItemLongClickListener(OnGridItemLongclick);

        //return inflater.inflate(R.layout.fragment_item_gridview, container, false);

        return rootView;
    }

    AdapterView.OnItemClickListener OnGridItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            final int selectedItem = ((int) adapterView.getItemIdAtPosition(i));
            Integer id = selectedItem;

            String selectedzone = MainActivity.myZoneList.get(i).getZone().toString();

            Toast toast = Toast.makeText(getContext(), "Zone: " + selectedzone, Toast.LENGTH_SHORT);
            toast.show();

            Intent intent = new Intent(getContext(), ZoneItemView.class);
            Bundle mBundle = new Bundle();

//        Integer id = selectedItem;
//        String zone = MainActivity.myZoneList.get(selectedItem).getZone().toString();
//
//        mBundle.putString("zone", zone);
//
//        intent.putExtras(mBundle);

            startActivity(intent);


        }
    };

}
