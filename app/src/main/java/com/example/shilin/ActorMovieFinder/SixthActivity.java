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

public class SixthActivity extends AppCompatActivity implements AsyncResponse {
    String output=null;
    int idValue=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixth);
        Intent mIntent = getIntent();
        int idValue = mIntent.getIntExtra("idValue", 0);
        RetrieveFeedTask5 downloader = new RetrieveFeedTask5(idValue);
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
        //System.out.println("Process Finish: " + output);
        try {
            JSONObject results = null;
            results = new JSONObject(output);
            JSONArray data = results.getJSONArray("cast");
            final ScrollView scrollView = new ScrollView(this);
            final LinearLayout linearLayout = new LinearLayout(this);
            scrollView.addView(linearLayout);
            setContentView(scrollView);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            for (int i = 0; i < data.length() - 1; i++) {
                JSONObject jsonActor = data.getJSONObject(i);

                String character=jsonActor.getString("character");
                String actor = jsonActor.getString("name");
                String profilePath = jsonActor.getString("profile_path");
                final int movieId= jsonActor.getInt("id");
                final TextView textView = new TextView(this);
                TextView textView1 = new TextView(this);   TextView textView2= new TextView(this);
                ImageView imageView = new ImageView(this);
                String chara="<b>Character: </b>" + character;  textView.setTextSize(20);textView1.setTextSize(20);

                textView.setText(Html.fromHtml(chara));
                String actora="<b>Actor: </b>" + actor;
                textView1.setText(Html.fromHtml(actora));
                String path = "https://image.tmdb.org/t/p/w185" + profilePath;
                textView2.setText("\n");
                Picasso.with(this).load(path).resize(750, 800).into(imageView);
                linearLayout.addView(textView);
                linearLayout.addView(textView1);
                linearLayout.addView(imageView);
                linearLayout.addView(textView2);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        idValue=movieId;
                       // System.out.println(idValue);
                        goToThirdActivity();
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
            e.printStackTrace();
        }
    }
    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void goToThirdActivity() {
        Intent intent = new Intent(this, ThirdActivity.class);
        intent.putExtra("idValue",idValue);
        startActivity(intent);
    }
}