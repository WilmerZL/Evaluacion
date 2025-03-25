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

public class VolumenActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Volumen> lista = new ArrayList<>();
    VolumenAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volumen);

        recyclerView = findViewById(R.id.recyclerVolumenes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VolumenAdapter(this, lista);
        recyclerView.setAdapter(adapter);

        int journal_id = getIntent().getIntExtra("journal_id", 0);
        cargarVolumenes(journal_id);
    }

    private void cargarVolumenes(int j_id) {
        String url = "https://revistas.uteq.edu.ec/ws/issues.php?j_id=" + j_id;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            Volumen v = new Volumen();
                            v.issue_id = obj.getInt("issue_id");
                            v.title = obj.getString("title");
                            v.date_published = obj.optString("date_published", "").split(" ")[0];
                            v.cover = obj.getString("cover");
                            v.volume = obj.optString("volume", "");
                            v.number = obj.optString("number", "");
                            lista.add(v);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                }, Throwable::printStackTrace);

        queue.add(request);
    }
}
