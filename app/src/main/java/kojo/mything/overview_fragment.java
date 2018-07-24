package kojo.mything;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class overview_fragment extends Fragment {

//    GridView gridView;
    View rootView;
    ImageView imgview;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_floorplan, container, false);
        imgview =  (ImageView) rootView.findViewById(R.id.floorplanview);
        imgview.setColorFilter(Color.WHITE);

//        gridView = (GridView) rootView.findViewById(R.id.fragment_gridview);
//        gridView.setAdapter(MainActivity.itemadapter);
//
//        gridView.setOnItemClickListener(OnGridItemClick);
//        gridView.setOnItemLongClickListener(OnGridItemLongclick);

        //return inflater.inflate(R.layout.fragment_item_gridview, container, false);

        return rootView;
    }
}
