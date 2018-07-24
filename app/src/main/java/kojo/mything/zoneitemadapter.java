package kojo.mything;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class zoneitemadapter extends BaseAdapter {
    private Context mContext;
    private List<ZoneObject> myZones;
    private LayoutInflater layoutinflater;

    public zoneitemadapter(Context c, int simple_list_item_1, List<ZoneObject> zones){
        this.mContext = c;
        layoutinflater =(LayoutInflater)c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
        myZones=zones;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        zoneitemadapter.ViewHolder listViewHolder;

        if (myZones.size() != 0) {
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                //imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8, 8, 8, 8);

                listViewHolder = new zoneitemadapter.ViewHolder();
                convertView = layoutinflater.inflate(R.layout.mything_item, parent, false);
                listViewHolder.nameInListView = (TextView) convertView.findViewById(R.id.nameView);
                listViewHolder.imageInListView = (ImageView) convertView.findViewById(R.id.imageView);
                listViewHolder.dataInListView = (TextView) convertView.findViewById(R.id.dataView);
                listViewHolder.statusInListView = (TextView) convertView.findViewById(R.id.mything_status);
                listViewHolder.zoneInListView = (TextView) convertView.findViewById(R.id.zoneView);

                listViewHolder.imageInListView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                //listViewHolder.imageInListView.setLayoutParams(new ViewGroup.LayoutParams(25,25));

                convertView.setTag(listViewHolder);
            } else {
                listViewHolder = (zoneitemadapter.ViewHolder) convertView.getTag();
            }


            listViewHolder.dataInListView.setVisibility(View.INVISIBLE);
            listViewHolder.zoneInListView.setVisibility(View.INVISIBLE);
            listViewHolder.statusInListView.setVisibility(View.INVISIBLE);
            listViewHolder.nameInListView.setText(myZones.get(position).getZone());
//            listViewHolder.statusInListView.setText(myZones.get(position).getId());

            int imageResourceId = this.mContext.getResources().getIdentifier(myZones.get(position).getImageResource(), "drawable", this.mContext.getPackageName());
            listViewHolder.imageInListView.setImageResource(imageResourceId);
            listViewHolder.imageInListView.setColorFilter(Color.WHITE);
        }


        return convertView;
    }

    @Override
    public int getCount() {
        return myZones.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder{
        TextView nameInListView;
        ImageView imageInListView;
        TextView dataInListView;
        TextView statusInListView;
        TextView zoneInListView;
    }
}
