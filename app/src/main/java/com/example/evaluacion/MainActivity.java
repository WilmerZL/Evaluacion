package com.example.evaluacion;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Revista> lista = new ArrayList<>();
    RevistaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerRevistas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RevistaAdapter(this, lista);
        recyclerView.setAdapter(adapter);

        cargarRevistas();

    }

    private void cargarRevistas() {
        String url = "https://revistas.uteq.edu.ec/ws/journals.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            Revista r = new Revista();
                            r.journal_id = obj.getInt("journal_id");
                            r.name = obj.getString("name");
                            r.description = obj.getString("description");
                            r.portada = obj.getString("portada");
                            lista.add(r);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                }, Throwable::printStackTrace);

        queue.add(request);
    }
}
