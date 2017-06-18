package com.example.shilin.ActorMovieFinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FourthActivity extends AppCompatActivity implements AsyncResponse {
    private ArrayList<Actor> actors = new ArrayList();
    String output = null;
    int idValue=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        Bundle bundle = getIntent().getExtras();
        String stuff = bundle.getString("stuff");
        stuff = stuff.replace(" ","%20");
        RetrieveFeedTask2 downloader = new RetrieveFeedTask2(stuff);

        downloader.delegate = this;
        downloader.execute();
        processFinish(output);


    }

    @Override
    public void processFinish(String output) {
        //Here you will receive the result fired from async class
        //of onPostExecute(result) method.
        //System.out.println("Process Finish: "+output);

        if (output == null) {
            output = "THERE WAS AN ERROR";
            //System.out.println("wassup niggas");
        }

        Log.i("INFO", output);
        try {
            JSONObject results = null;
            results = new JSONObject(output);
            JSONArray data = results.getJSONArray("results");
            final ScrollView scrollView = new ScrollView(this);
            final LinearLayout linearLayout = new LinearLayout(this);
            scrollView.addView(linearLayout);
            setContentView(scrollView);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            for (int i = 0; i < data.length() - 1; i++) {
                JSONObject jsonActor = data.getJSONObject(i);

                String title=jsonActor.getString("original_title");
                String releaseDate=jsonActor.getString("release_date");
                String posterPath=jsonActor.getString("poster_path");
                String overView=jsonActor.getString("overview");
                final int movieId=jsonActor.getInt("id");
                final TextView textView = new TextView(this);
                TextView textView1 = new TextView(this);  TextView textView2 = new TextView(this);  TextView textView3 = new TextView(this);
                ImageView imageView = new ImageView(this);
                String titleB="<b>Original title: </b>" + title;
                textView.setText(Html.fromHtml(titleB));
                String releaseDateB="<b>Release date: </b>" + releaseDate;
                textView1.setText(Html.fromHtml(releaseDateB));
                String overviewB="<b>Overview: </b>"+overView;
                textView2.setText(Html.fromHtml(overviewB));

                String path = "https://image.tmdb.org/t/p/w185" + posterPath;
                textView3.setText("\n\n");

                Picasso.with(this).load(path).resize(800, 800).into(imageView);

                linearLayout.addView(textView);
                textView.setTextSize(20);
                linearLayout.addView(textView1);
                textView1.setTextSize(20);
                linearLayout.addView(textView2);
                textView2.setTextSize(20);
                linearLayout.addView(imageView);
                linearLayout.addView(textView3);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        idValue=movieId;
                        System.out.println(idValue);
                        goToSixthActivity();     // movie cast activity
                    }
                });

            }
            Button back = new Button(this);
            back.setText("Back");
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToMainActivity();
                }
            });
            linearLayout.addView(back);

            } catch (JSONException e) {
            // Appropriate error handling code
        }
    }
    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void goToSixthActivity() {
        Intent intent = new Intent(this, SixthActivity.class);
        intent.putExtra("idValue",idValue);
        startActivity(intent);
    }


}