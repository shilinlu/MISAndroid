package com.example.shilin.ActorMovieFinder;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ThirdActivity extends AppCompatActivity implements AsyncResponse {
    String output = null;
    private ArrayList<Actor> actors = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Intent mIntent = getIntent();
        int idValue = mIntent.getIntExtra("idValue", 0);
        //TextView input = (TextView) findViewById(R.id.textView3);
       // input.setText(String.valueOf(idValue));
        RetrieveFeedTask1 downloader = new RetrieveFeedTask1(idValue);
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
           // System.out.println("wassup niggas");
        }

        Log.i("INFO", output);
        //System.out.println("Process Finish: " + output);
        try {

            JSONObject results = null;
            results = new JSONObject(output);
            String bio = results.getString("biography");
            String dob = results.getString("birthday");
            String birthPlace = results.getString("place_of_birth");
            String profilePath= results.getString("profile_path");
            final ScrollView scrollView = new ScrollView(this);
            final LinearLayout linearLayout = new LinearLayout(this);

            scrollView.addView(linearLayout);

            setContentView(scrollView);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            Actor actor=new Actor();
            actor.setBio(bio);    actor.setDOB(dob);   actor.setPlaceOfBirth(birthPlace);  actor.setPicturePath(profilePath);
            TextView textView = new TextView(this);   TextView textView4 = new TextView(this);
            TextView textView1 = new TextView(this);   TextView textView5 = new TextView(this);
            TextView textView2 = new TextView(this);   ImageView imageView=new ImageView(this);
            TextView textView3 = new TextView(this);    TextView textView8 = new TextView(this);
            String path = "https://image.tmdb.org/t/p/w185" + actor.getPicturePath();
            Picasso.with(this).load(path).resize(700, 700).into(imageView);
            textView.setText(actor.getBio());   textView.setTextSize(20);
            textView1.setText("Biography");   textView1.setTextSize(25); textView1.setTextColor(Color.RED);
            textView2.setText("Date Of Birth"); textView2.setTextSize(25); textView2.setTextColor(Color.RED);
            textView4.setText("Place Of Birth"); textView4.setTextSize(25); textView4.setTextColor(Color.RED);
            textView8.setText("Known for Films"); textView8.setTextSize(25); textView8.setTextColor(Color.RED);
            textView3.setText(actor.getDOB());   textView3.setTextSize(20);
            textView5.setText(actor.getPlaceOfBirth());    textView5.setTextSize(20);
            linearLayout.addView(imageView);
            linearLayout.addView(textView1);
            linearLayout.addView(textView);
            linearLayout.addView(textView2);
            linearLayout.addView(textView3);
            linearLayout.addView(textView4);
            linearLayout.addView(textView5);
            linearLayout.addView(textView8);

            String credits=results.getString("credits");
            System.out.println("Credit:"+credits);
            JSONObject creditResults = null;
            creditResults = new JSONObject(credits);
            JSONArray data = creditResults.getJSONArray("cast");
            for (int i = 0; i < data.length() - 1; i++) {
                JSONObject jsonActor = data.getJSONObject(i);
                final Actor actor2 = new Actor();
                actor2.setName(jsonActor.getString("original_title"));
                actor2.setPicturePath(jsonActor.getString("poster_path"));
                String release_date=jsonActor.getString("release_date");
                actors.add(actor2);

                //System.out.println(actor2.getName());
                //System.out.println(actor2.getPicturePath());

                final TextView textView6 = new TextView(this);  final TextView textView9 = new TextView(this);
                TextView textView7 = new TextView(this);  final TextView textView10 = new TextView(this);
                ImageView imageView1 = new ImageView(this);   textView6.setTypeface(null, Typeface.BOLD);  textView6.setTextSize(20);
                textView9.setTypeface(null, Typeface.BOLD);  textView9.setTextSize(20);

                textView6.setText("Title: " + actor2.getName());   textView10.setText("\n");
                textView9.setText("Release date: "+release_date);
                String paths = "https://image.tmdb.org/t/p/w185" + actor2.getPicturePath();

                Picasso.with(this).load(paths).resize(800, 800).into(imageView1);

                linearLayout.addView(textView6);
                linearLayout.addView(textView9);
                linearLayout.addView(textView7);
                linearLayout.addView(imageView1);
                linearLayout.addView(textView10);
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
            e.printStackTrace();
        }


    }


    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}