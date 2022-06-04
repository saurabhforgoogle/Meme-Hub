package com.example.memehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity<loading> extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
    private Spinner spinner;
    private String currentFilter="All";
    private RecyclerView mrecyclerview;
    private MemeAdapter mAdapter;
    private static final String[] pages={"All","Desihumor","wholesomememes","dankmemes","desimemes"};
    private ImageView reload;
    private Button videos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner=(Spinner) findViewById(R.id.filters);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_item,pages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        reload=findViewById(R.id.reload);
        videos=findViewById(R.id.videopage);
        videos.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this,VideoMemes.class);
            startActivity(intent);
        });
        reload.setOnClickListener(v -> fetchImage());
        mrecyclerview=findViewById(R.id.recyclerview);
        mrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mAdapter= new MemeAdapter(this);
        mrecyclerview.setAdapter(mAdapter);
        fetchImage();




    }

    private  void fetchImage() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://meme-api.herokuapp.com/gimme/"+currentFilter+"/50";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest //called class object here only
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ArrayList<String> titles=new ArrayList<>();
                            ArrayList<String> urls= new ArrayList<>();
                            for(int i=0;i<50;i++){
                                titles.add(response.getJSONArray("memes").getJSONObject(i).getString("title"));
                                urls.add(response.getJSONArray("memes").getJSONObject(i).getString("url"));
                            }
                            mAdapter.refreshArr(titles,urls);



                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, "Failed loading Image", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(MainActivity.this, "Failed Calling API", Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(jsonObjectRequest);
    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==0){
            currentFilter="";
        }
        else {
            currentFilter=pages[position];
        }
        mAdapter.reset();
        fetchImage();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public  void nextmeme(View view) {
        fetchImage();
    }
}