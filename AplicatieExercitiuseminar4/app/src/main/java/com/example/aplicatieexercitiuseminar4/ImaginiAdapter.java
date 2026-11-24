package com.example.aplicatieexercitiuseminar4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ImaginiAdapter extends BaseAdapter {

    private final List<BMW_imagini> listImagini;
    private final Context ctx;
    private final int resursaLayout;

    public ImaginiAdapter(List<BMW_imagini> listImagini, Context ctx, int resursaLayout) {
        this.listImagini = listImagini;
        this.ctx = ctx;
        this.resursaLayout = resursaLayout;
    }

    @Override
    public int getCount() {
        return listImagini != null ? listImagini.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return listImagini.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View v = inflater.inflate(resursaLayout, parent, false);

        ImageView ivImagine = v.findViewById(R.id.ivImagine);
        TextView tvTextDescriptiv = v.findViewById(R.id.tvTextDescriptiv);

        BMW_imagini bmwImagine = (BMW_imagini) getItem(position);

        if (bmwImagine != null) {
            tvTextDescriptiv.setText(bmwImagine.getTextAfisat());

            if (bmwImagine.getImagine() != null) {
                ivImagine.setImageBitmap(bmwImagine.getImagine());
            } else {
                ivImagine.setImageResource(R.drawable.ic_launcher_foreground);
            }
        }

        return v;
    }
}
