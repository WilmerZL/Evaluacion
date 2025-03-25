package com.example.evaluacion;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class VolumenAdapter extends RecyclerView.Adapter<VolumenAdapter.ViewHolder> {

    private final List<Volumen> lista;
    private final Context context;

    public VolumenAdapter(Context context, List<Volumen> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_volumen, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Volumen v = lista.get(position);
        holder.tvTitulo.setText(v.title);
        holder.tvFecha.setText("Volumen: " + v.volume + " - Número: " + v.number + " - Fecha: " + v.date_published);
        Picasso.get().load(v.cover).into(holder.imgPortada);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ArticuloActivity.class);
            intent.putExtra("issue_id", v.issue_id);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvFecha;
        ImageView imgPortada;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            imgPortada = itemView.findViewById(R.id.imgPortada);
        }
    }
}
