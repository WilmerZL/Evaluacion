package com.example.evaluacion;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RevistaAdapter extends RecyclerView.Adapter<RevistaAdapter.ViewHolder> {

    private final List<Revista> lista;
    private final Context context;

    public RevistaAdapter(Context context, List<Revista> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_revista, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Revista r = lista.get(position);
        holder.tvNombre.setText(r.name);
        holder.tvDescripcion.setText(Html.fromHtml(r.description, Html.FROM_HTML_MODE_LEGACY));
        holder.tvDescripcion.setMaxLines(5);
        holder.tvDescripcion.setEllipsize(TextUtils.TruncateAt.END);
        holder.btnVerMas.setText("Ver más");
        holder.isExpanded = false;

        holder.btnVerMas.setOnClickListener(v -> {
            if (holder.isExpanded) {
                holder.tvDescripcion.setMaxLines(5);
                holder.tvDescripcion.setEllipsize(TextUtils.TruncateAt.END);
                holder.btnVerMas.setText("Ver más");
                holder.isExpanded = false;
            } else {
                holder.tvDescripcion.setMaxLines(Integer.MAX_VALUE);
                holder.tvDescripcion.setEllipsize(null);
                holder.btnVerMas.setText("Ver menos");
                holder.isExpanded = true;
            }
        });
        Picasso.get().load(r.portada).into(holder.imgLogo);
        holder.itemView.setOnClickListener(view -> {
            Intent i = new Intent(context, VolumenActivity.class);
            i.putExtra("journal_id", r.journal_id);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvDescripcion;
        ImageView imgLogo;
        Button btnVerMas;
        boolean isExpanded = false;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            btnVerMas = itemView.findViewById(R.id.btnVerMas);
        }
    }
}
