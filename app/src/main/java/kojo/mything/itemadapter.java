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

public class itemadapter extends BaseAdapter {
    private Context mContext;
    private List<ItemObject> myList;
    private LayoutInflater layoutinflater;

    //private ArrayAdapter<ItemObject> myList;

        public itemadapter(Context c, int simple_list_item_1, List<ItemObject> items) {
        //public itemadapter(Context c, ArrayAdapter<ItemObject> images) {
        this.mContext = c;
        layoutinflater =(LayoutInflater)c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
        myList = items;
    }

    @Override
    public int getCount() {
        return myList.size();
    }

//    @Override
//    public Object getItem(int position) {
//        return position;
//    }

    @Override
    public Object getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
//
//
//    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        //ImageView imageView;

        ViewHolder listViewHolder;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            //imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8, 8, 8, 8);

            listViewHolder = new ViewHolder();
            convertView = layoutinflater.inflate(R.layout.mything_item, parent, false);
            listViewHolder.textInListView = (TextView)convertView.findViewById(R.id.nameView);
            listViewHolder.imageInListView = (ImageView)convertView.findViewById(R.id.imageView);
            listViewHolder.dataInListView = (TextView) convertView.findViewById(R.id.dataView);
            listViewHolder.statusInListView = (TextView) convertView.findViewById(R.id.mything_status);
            listViewHolder.zoneInListView = (TextView) convertView.findViewById(R.id.zoneView);

            listViewHolder.imageInListView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //listViewHolder.imageInListView.setLayoutParams(new ViewGroup.LayoutParams(25,25));

            convertView.setTag(listViewHolder);
        }
        else {
            listViewHolder = (ViewHolder)convertView.getTag();
        }

        if (myList.get(position).getActor()){
            listViewHolder.dataInListView.setVisibility(View.INVISIBLE);
            listViewHolder.imageInListView.setVisibility(View.VISIBLE);
            int imageResourceId = this.mContext.getResources().getIdentifier(myList.get(position).getImageResource(), "drawable", this.mContext.getPackageName());
            listViewHolder.imageInListView.setImageResource(imageResourceId);

            if (myList.get(position).getDataValue().equals("1")){
                listViewHolder.imageInListView.setColorFilter(Color.YELLOW);
            }
            else {
                listViewHolder.imageInListView.setColorFilter(Color.WHITE);
            }
        }
        else{
            listViewHolder.dataInListView.setVisibility(View.VISIBLE);
            listViewHolder.imageInListView.setVisibility(View.INVISIBLE);
            listViewHolder.dataInListView.setText(myList.get(position).getDataValue());
        }

        listViewHolder.statusInListView.setText(myList.get(position).getStatus());
        listViewHolder.textInListView.setText(myList.get(position).getName());
//        listViewHolder.zoneInListView.setText(myList.get(position).getZone());
        listViewHolder.zoneInListView.setVisibility(View.INVISIBLE);

//        int imageResourceId = this.mContext.getResources().getIdentifier(myList.get(position).getImageResource(), "drawable", this.mContext.getPackageName());
//        listViewHolder.imageInListView.setImageResource(imageResourceId);

        return convertView;
    }

    static class ViewHolder{
        TextView textInListView;
        ImageView imageInListView;
        TextView dataInListView;
        TextView statusInListView;
        TextView zoneInListView;
    }

}
