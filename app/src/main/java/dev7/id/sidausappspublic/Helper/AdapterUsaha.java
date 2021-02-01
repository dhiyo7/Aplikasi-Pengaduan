package dev7.id.sidausappspublic.Helper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


import dev7.id.pakhendrawan.Helper.Utils;
import dev7.id.sidausappspublic.Activity.AduanActivity;
import dev7.id.sidausappspublic.Model.Desa;
import dev7.id.sidausappspublic.Model.JenisIzin;
import dev7.id.sidausappspublic.Model.Kecamatan;
import dev7.id.sidausappspublic.Model.Kepemilikan;
import dev7.id.sidausappspublic.Model.Usaha;
import dev7.id.sidausappspublic.R;

public class AdapterUsaha extends RecyclerView.Adapter<AdapterUsaha.ViewHolder>{

    private String[] choices = {"Hapus", "Ubah"};
    private ArrayList<Usaha> data = new ArrayList<>();
    private ArrayList<Desa> desas = new ArrayList<>();
    private ArrayList<JenisIzin> jenisIzins = new ArrayList<>();
    private ArrayList<Kecamatan> kecamatans = new ArrayList<>();
    private ArrayList<Kepemilikan> kepemilikans = new ArrayList<>();
    private Context context;

    public AdapterUsaha(ArrayList<Usaha> data, ArrayList<Desa> desas, ArrayList<Kecamatan> kecamatans, Context context) {
        this.data = data;
        this.desas = desas;
        this.kecamatans = kecamatans;
        this.context = context;
    }

    public AdapterUsaha(ArrayList<Usaha> data, ArrayList<Desa> desas, ArrayList<JenisIzin> jenisIzins, ArrayList<Kecamatan> kecamatans, ArrayList<Kepemilikan> kepemilikans, Context context) {
        this.data = data;
        this.desas = desas;
        this.jenisIzins = jenisIzins;
        this.kecamatans = kecamatans;
        this.kepemilikans = kepemilikans;
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_usaha, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String tvNamaUsaha = data.get(position).getNama();
        final String tvNamaPenanggung = data.get(position).getNamaPenanggungJawab();
        final String tvTelepon = data.get(position).getTelpon();
        final String tvKepemilikan = Utils.Companion.getKepemilikanName(data.get(position).getKepemilikan());
        final String tvIzin = Utils.Companion.getNameJenisIzin(data.get(position).getJenis());
        final String tvDesa = Utils.Companion.getDesaName(Integer.parseInt(data.get(position).getDesa()), desas);
        final String tvAlamat = data.get(position).getAlamat();
        final String kec = Utils.Companion.getKecamatanName(Integer.parseInt(data.get(position).getDesa()), desas, kecamatans);
        holder.tvNamaUsaha.setText(tvNamaUsaha);
        holder.tvNamaPenanggung.setText(tvNamaPenanggung);
        holder.tvTelepon.setText(tvTelepon);
        holder.tvDesa.setText(tvDesa);
        holder.tvAlamat.setText(tvAlamat);
        holder.tvKepemilikan.setText(tvKepemilikan);
        holder.tvIzin.setText(tvIzin);
        holder.tvKecamatan.setText(kec);
//        if (data)

//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setItems(choices, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which){
//                            case 0 :
//                                notifyItemRemoved(position);
//                                data.remove(position);
//                                notifyDataSetChanged();
//                                break;
//                            case 1 :
////                                Toast.makeText(context, "Update", Toast.LENGTH_SHORT).show();
//                                Intent a = new Intent(context, UbahUsahaActivity.class);
//                                a.putExtra("USAHA", data.get(position).getId());
//                                context.startActivity(a);
//                                break;
//                        }
//                    }
//                });
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
//                return false;
//            }
//        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(context, AduanActivity.class);
                a.putExtra("USAHA", data.get(position).getId());
                context.startActivity(a);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNamaUsaha, tvNamaPenanggung, tvTelepon, tvKepemilikan, tvIzin, tvAlamat, tvKecamatan, tvDesa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaUsaha = itemView.findViewById(R.id.tvNamaUsaha);
            tvNamaPenanggung = itemView.findViewById(R.id.tvNamaPenanggung);
            tvTelepon = itemView.findViewById(R.id.tvTelepon);
            tvKepemilikan = itemView.findViewById(R.id.tvKepemilikan);
            tvIzin = itemView.findViewById(R.id.tvIzin);
            tvKecamatan = itemView.findViewById(R.id.tvKecamatan);
            tvDesa = itemView.findViewById(R.id.tvDesa);
            tvAlamat = itemView.findViewById(R.id.tvAlamat);
        }
    }
}
