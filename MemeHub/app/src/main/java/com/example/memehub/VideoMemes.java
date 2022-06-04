package com.example.memehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import org.json.JSONException;
import org.json.JSONObject;

public class VideoMemes extends YouTubeBaseActivity {

    private YouTubePlayerView Player;
    private TextView title;
    private  TextView description;
    private String api_key="AIzaSyD5qmomU6C0hLL-exk1YirCq0a54ctmuK0";
    private String url="https://youtube-meme-api.herokuapp.com/api/v1/random/playlist/item";
    private String vidId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_memes);
        Player=findViewById(R.id.Player);
        title=findViewById(R.id.VideoTitle);
        description=findViewById(R.id.VideoDescription);
        //loadnext();
        loadVideo();
    }

    private void loadnext() {
        RequestQueue queue1 = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            vidId=response.getJSONObject("contentDetails").getString("videoId");
                            System.out.println(vidId);
                            loadVideo();

                        } catch (JSONException e) {
                            Toast.makeText(VideoMemes.this, "API format Error", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VideoMemes.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                    }


                });
        queue1.add(jsonObjectRequest);
    }

    private void loadVideo() {
        Player.initialize(api_key, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("YAscOYMTgTs");
                //if(youTubePlayer.getDurationMillis()>60000){
                    //loadnext();
                    //return;
                //}
                //youTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(VideoMemes.this, "Some Error while Loading Video", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void activityone(View view) {
        Intent i=new Intent(VideoMemes.this,MainActivity.class);
        startActivity(i);
    }

    public void tempload(View view) {

        loadVideo();
    }
}
