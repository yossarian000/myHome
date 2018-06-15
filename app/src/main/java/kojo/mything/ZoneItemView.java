package kojo.mything;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class ZoneItemView extends AppCompatActivity {

    String zone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Toast toast = Toast.makeText(getApplicationContext(), "oncreate: " , Toast.LENGTH_SHORT);
//        toast.show();

        setContentView(R.layout.activity_zone_item_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.ZoneView_toolbar);
        setSupportActionBar(toolbar);

        MainActivity.myZoneItems.clear();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            zone = bundle.getString("zone");
            toolbar.setTitle(zone);
        }

        for (int i = 0; i < MainActivity.myItemList.size(); i++){
            if (MainActivity.myItemList.get(i).getZone().equals(zone)){
                MainActivity.myZoneItems.add(MainActivity.myItemList.get(i));
            }
        }

        FragmentManager fm = getSupportFragmentManager();
        Bundle arguments = new Bundle();
        arguments.putBoolean("ZoneitemView", true);

        ItemGridview_fragment myFragment = new ItemGridview_fragment();
        myFragment.setArguments(arguments);
        fm.beginTransaction().replace(R.id.fragment_placeholder, myFragment).commit();
//        loadFragment(new ItemGridview_fragment());

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
//                MainActivity.myZoneItems.clear();
                finish();
            }
        });
    }


//    private void loadFragment(Fragment fragment){
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        // Replace the contents of the container with the new fragment
//        ft.replace(R.id.fragment_placeholder, new ItemGridview_fragment());
//        // or ft.add(R.id.your_placeholder, new FooFragment());
//        // Complete the changes added above
//        ft.commit();
//    }
}
