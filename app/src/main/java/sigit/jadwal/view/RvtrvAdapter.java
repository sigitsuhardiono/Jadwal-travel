package sigit.jadwal.view;

/**
 * Created by sigit on 19/12/2016.
 */
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import sigit.jadwal.R;
import sigit.jadwal.model.listtravel.Penumpang;

public class RvtrvAdapter extends RecyclerView.Adapter<RvtrvAdapter.ViewHolder> {
    NumberFormat rupiahFormat;
    private List<Penumpang> rvData;
    public RvtrvAdapter(List<Penumpang> inputData) {
        rvData = inputData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvSubtitle;
        public TextView tvNomor;
        public TextView tvHarga;
        public CardView cardViewMlg;

        public ViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tv_title);
            tvSubtitle = (TextView) v.findViewById(R.id.tv_subtitle);
            tvHarga = (TextView) v.findViewById(R.id.tv_harga);
            tvNomor = (TextView) v.findViewById(R.id.tv_nomor);
            cardViewMlg = (CardView) v.findViewById(R.id.cdViewMlg);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // membuat view baru
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_viewtrvmalang, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvNomor.setText(Integer.toString(position+1)+". ");
        holder.tvTitle.setText(rvData.get(position).getNama());
        holder.tvSubtitle.setText(rvData.get(position).getJam());
        if(rvData.get(position).getMetodeBayar().equals("LA")){
            holder.tvHarga.setText("");
        }
        else{
            rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
            String rupiah = rupiahFormat.format(Double.parseDouble(rvData.get(position).getHarga()));
            holder.tvHarga.setText(rupiah);
        }
        final String id_penumpang =  rvData.get(position).getId();
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetiltravelActivity.class);
                intent.putExtra("id_penumpang",id_penumpang);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(rvData == null){
            return 0;
        }
        else{
            return rvData.size();
        }
    }
}