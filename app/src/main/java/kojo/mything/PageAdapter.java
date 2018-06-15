package kojo.mything;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;
import android.widget.GridView;

public class PageAdapter extends FragmentStatePagerAdapter {

    int tabCount;

    public PageAdapter (FragmentManager fm, int tabCount){
        super(fm);
        this.tabCount=tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                overview_fragment tab1 = new overview_fragment();
                return tab1;
            case 1:
                ZoneGridview_fragment tab2 = new ZoneGridview_fragment();
                return tab2;
            case 2:
                ItemGridview_fragment tab3 = new ItemGridview_fragment();
                Bundle arguments = new Bundle();
                arguments.putBoolean("ZoneitemView", false);
                tab3.setArguments(arguments);
                return tab3;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }

//    @Override
//    public int getItemPosition(Object object) {
//        return POSITION_NONE;
//    }

}
