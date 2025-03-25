package com.example.evaluacion;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArticuloActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Articulo> lista = new ArrayList<>();
    ArticuloAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo);

        recyclerView = findViewById(R.id.recyclerArticulos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ArticuloAdapter(this, lista);
        recyclerView.setAdapter(adapter);

        int issue_id = getIntent().getIntExtra("issue_id", 0);
        Log.d("ID_RECIBIDO", "issue_id = " + issue_id);

        cargarArticulos(issue_id);
    }

    private void cargarArticulos(int i_id) {
        String url = "https://revistas.uteq.edu.ec/ws/pubs.php?i_id=" + i_id;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    if (response == null || response.length() == 0) {
                        Log.w("API_RESPONSE", "No hay artículos disponibles para este issue_id");
                        return;
                    }

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);

                            JSONArray galeys = obj.getJSONArray("galeys");
                            String urlPDF = "";
                            String urlHTML = "";

                            for (int j = 0; j < galeys.length(); j++) {
                                JSONObject g = galeys.getJSONObject(j);
                                String label = g.optString("label", "");
                                if (label.equalsIgnoreCase("PDF")) {
                                    urlPDF = g.optString("UrlViewGalley", "");
                                } else if (label.equalsIgnoreCase("HTML")) {
                                    urlHTML = g.optString("UrlViewGalley", "");
                                }
                            }

                            Articulo a = new Articulo();
                            a.title = obj.optString("title", "Sin título");
                            a.doi = obj.optString("doi", "Sin DOI");
                            StringBuilder autoresBuilder = new StringBuilder();
                            JSONArray autoresArray = obj.getJSONArray("authors");

                            for (int k = 0; k < autoresArray.length(); k++) {
                                JSONObject autorObj = autoresArray.getJSONObject(k);
                                String nombre = autorObj.optString("nombres", "");
                                if (!nombre.isEmpty()) {
                                    autoresBuilder.append(nombre);
                                    if (k < autoresArray.length() - 1) {
                                        autoresBuilder.append(", ");
                                    }
                                }
                            }

                            a.autores = autoresBuilder.toString();
                            a.pdf = urlPDF;
                            a.html = urlHTML;
                            lista.add(a);


                        } catch (JSONException e) {
                            Log.e("JSON_ERROR", "Error al parsear el artículo", e);
                        }
                    }
                    adapter.notifyDataSetChanged();
                },
                error -> {
                    Log.e("API_ERROR", "Error en la petición a la API", error);
                });

        queue.add(request);
    }
}
