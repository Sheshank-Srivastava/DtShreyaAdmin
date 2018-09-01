package com.dietitianshreya.eatrightdietadmin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dietitianshreya.eatrightdietadmin.AdapterGridSingleLine;
import com.dietitianshreya.eatrightdietadmin.R;
import com.dietitianshreya.eatrightdietadmin.models.TemplateModel;

import java.util.ArrayList;

public class TemplateListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<TemplateModel> items = new ArrayList<>();
    private Context ctx;
    private TemplateListAdapter.OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, String value, int position);
    }

    public void setOnItemClickListener(final TemplateListAdapter.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public TemplateListAdapter(Context context, ArrayList<TemplateModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView placeHolder;
        public TextView name;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
//            image = (ImageView) v.findViewById(R.id.image);
            name = (TextView) v.findViewById(R.id.name);
            placeHolder = (TextView) v.findViewById(R.id.placeholder);
            lyt_parent = (LinearLayout)v.findViewById(R.id.bodyLayout);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_image_single_line, parent, false);
        vh = new TemplateListAdapter.OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        if (holder instanceof TemplateListAdapter.OriginalViewHolder) {
            TemplateListAdapter.OriginalViewHolder view = (TemplateListAdapter.OriginalViewHolder) holder;

            view.name.setText(items.get(position).getName());
            Log.d("Name ",items.get(position).getName());
//            view.image.setImageResource(R.drawable.image_24);
            char placeholdertext = items.get(position).getName().toUpperCase().charAt(0);
            view.placeHolder.setText(placeholdertext+"");
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position).getName(), position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
