package com.example.aplicatieexercitiuseminar4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class BMWAdapter extends BaseAdapter {

    private List<BMW> masini;
    private Context ctx;
    private int resursaLayout;

    public BMWAdapter(List<BMW> masini, Context ctx, int resursaLayout) {
        this.masini = masini;
        this.ctx = ctx;
        this.resursaLayout = resursaLayout;
    }

    @Override
    public int getCount() {
        return masini != null ? masini.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return masini.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        // 1. Încărcare (inflate) a layout-ului rândului
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View v = inflater.inflate(resursaLayout, parent, false);

        // 2. Identificare elemente din layout (layout_nou.xml)
        TextView tvNume = v.findViewById(R.id.tvNume);
        TextView tvAnKg = v.findViewById(R.id.tvAnKg);
        TextView tvCombustibil = v.findViewById(R.id.tvCombustibil);
        CheckBox cbSport = v.findViewById(R.id.cbSport);

        // 3. Preluare obiect curent
        BMW bmw = (BMW) getItem(i);

        // 4. Populare elemente vizuale cu datele din obiectul BMW
        if (bmw != null) {
            tvNume.setText(bmw.getNume());
            tvAnKg.setText("An: " + bmw.getAn() + " | Greutate: " + bmw.getKg() + " kg");
            tvCombustibil.setText(bmw.getCombustibil());
            cbSport.setChecked(bmw.isEsteSport());
        }

        return v;
    }
}
