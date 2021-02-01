package dev7.id.sidausappspublic.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dev7.id.pakhendrawan.Helper.Utils;
import dev7.id.sidausappspublic.Model.Aduan;
import dev7.id.sidausappspublic.Model.Desa;
import dev7.id.sidausappspublic.Model.JenisIzin;
import dev7.id.sidausappspublic.Model.Kecamatan;
import dev7.id.sidausappspublic.Model.Kepemilikan;
import dev7.id.sidausappspublic.R;

public class AdapterAduan extends RecyclerView.Adapter<AdapterAduan.ViewHolder> {

    private String[] choices = {"Hapus", "Ubah"};
    private ArrayList<Aduan> data = new ArrayList<>();
    private ArrayList<Desa> desas = new ArrayList<>();
    private ArrayList<JenisIzin> jenisIzins = new ArrayList<>();
    private ArrayList<Kecamatan> kecamatans = new ArrayList<>();
    private ArrayList<Kepemilikan> kepemilikans = new ArrayList<>();
    private Context context;

    public AdapterAduan(ArrayList<Aduan> data, ArrayList<Desa> desas, ArrayList<Kecamatan> kecamatans, Context context) {
        this.data = data;
        this.desas = desas;
        this.kecamatans = kecamatans;
        this.context = context;
    }

    public AdapterAduan(ArrayList<Aduan> data, ArrayList<Desa> desas, ArrayList<JenisIzin> jenisIzins, ArrayList<Kecamatan> kecamatans, ArrayList<Kepemilikan> kepemilikans, Context context) {
        this.data = data;
        this.desas = desas;
        this.jenisIzins = jenisIzins;
        this.kecamatans = kecamatans;
        this.kepemilikans = kepemilikans;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adpter_aduan, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterAduan.ViewHolder holder, final int position) {
        final String tvNamaUsaha = data.get(position).getNama();
//        final String tvNamaPenanggung = data.get(position).getPenanggung_jawab();
        final String tvAduan = data.get(position).getIsi_aduan();
        final String tvIzin = Utils.Companion.getNameJenisIzin(data.get(position).getJenis_usaha());
        final String tvDesa = Utils.Companion.getDesaName(Integer.parseInt(String.valueOf(data.get(position).getDesa())), desas);
        final String tvAlamat = data.get(position).getAlamat();
        final String kec = Utils.Companion.getKecamatanName(Integer.parseInt(String.valueOf(data.get(position).getDesa())), desas, kecamatans);
        holder.tvNamaUsaha.setText(tvNamaUsaha);
//        holder.tvNamaPenanggung.setText(tvNamaPenanggung);
        holder.tvDesa.setText(tvDesa);
        holder.tvAlamat.setText(tvAlamat);
//        holder.tvIzin.setText(tvIzin);
        holder.tvKecamatan.setText(kec);
        holder.tvAduan.setText(tvAduan);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent a = new Intent(context, UbahUsahaActivity.class);
//                a.putExtra("ADUAN", data.get(position).getId());
//                context.startActivity(a);

//                Toast.makeText(context, "Cek", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNamaUsaha, tvNamaPenanggung, tvKepemilikan, tvIzin, tvAlamat, tvKecamatan, tvDesa, tvAduan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaUsaha = itemView.findViewById(R.id.tvNamaUsaha);
//            tvNamaPenanggung = itemView.findViewById(R.id.tvNamaPenanggung);
//            tvKepemilikan = itemView.findViewById(R.id.tvKepemilikan);
//            tvIzin = itemView.findViewById(R.id.tvIzin);
            tvKecamatan = itemView.findViewById(R.id.tvKecamatan);
            tvDesa = itemView.findViewById(R.id.tvDesa);
            tvAlamat = itemView.findViewById(R.id.tvAlamat);
            tvAduan = itemView.findViewById(R.id.tvAduan);
        }
    }
}
