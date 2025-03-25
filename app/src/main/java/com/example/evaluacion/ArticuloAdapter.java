package com.example.evaluacion;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ArticuloAdapter extends RecyclerView.Adapter<ArticuloAdapter.ViewHolder> {

    private final List<Articulo> lista;
    private final Context context;

    public ArticuloAdapter(Context context, List<Articulo> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_articulo, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Articulo a = lista.get(position);
        holder.tvTitulo.setText(a.title);
        holder.tvDoi.setText("DOI: " + a.doi);
        holder.tvAutores.setText("Autores: " + a.autores);

        holder.btnPdf.setOnClickListener(view -> {
            if (!a.pdf.isEmpty()) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(a.pdf));
                context.startActivity(i);
            } else {
                Toast.makeText(context, "PDF no disponible", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnHtml.setOnClickListener(view -> {
            if (!a.html.isEmpty()) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(a.html));
                context.startActivity(i);
            } else {
                Toast.makeText(context, "HTML no disponible", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvDoi, tvAutores;
        Button btnPdf, btnHtml;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvDoi = itemView.findViewById(R.id.tvDoi);
            tvAutores = itemView.findViewById(R.id.tvAutores);
            btnPdf = itemView.findViewById(R.id.btnPdf);
            btnHtml = itemView.findViewById(R.id.btnHtml);
        }
    }
}
