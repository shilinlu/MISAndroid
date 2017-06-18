package com.example.shilin.ActorMovieFinder;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity implements AsyncResponse {
    private ArrayList<Actor> actors = new ArrayList();
    String output = null;
    int idValue=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Button button = (Button) findViewById(R.id.button);
        //TextView input = (TextView) findViewById(R.id.textView2);
        Bundle bundle = getIntent().getExtras();
        String stuff = bundle.getString("stuff");
        stuff = stuff.replace(" ","%20");
        RetrieveFeedTask downloader = new RetrieveFeedTask(stuff);

        downloader.delegate = this;
        downloader.execute();
        processFinish(output);



        //printInfo();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println(downloader.getActors().get(0).getName());
                goToThirdActivity();


            }
        });

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
                final Actor actor = new Actor();
                actor.setId(jsonActor.getInt("id"));
                actor.setName(jsonActor.getString("name"));
                actor.setPicturePath(jsonActor.getString("profile_path"));

                actors.add(actor);
                //System.out.println(actor.getId());
                //System.out.println(actor.getName());
                //System.out.println(actor.getPicturePath());


                final TextView textView = new TextView(this);
                TextView textView1 = new TextView(this);
                ImageView imageView = new ImageView(this);
                textView.setText("Name: " + actor.getName());
                textView1.setText("ID: " + actor.getId());
                String path = "https://image.tmdb.org/t/p/w185" + actor.getPicturePath();

                Picasso.with(this).load(path).resize(700, 700).into(imageView);

                linearLayout.addView(textView);    textView.setTextSize(20);
                linearLayout.addView(textView1);    textView1.setTextSize(20);
                linearLayout.addView(imageView);
                if (jsonActor.getString("profile_path").equals("null")) {
                    ImageView view = new ImageView(this);
                    Drawable myDrawable = getResources().getDrawable(R.drawable.profile);
                    view.setImageDrawable(myDrawable);   view.setMinimumHeight(700); view.setMinimumWidth(700);
                    linearLayout.addView(view);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            idValue=actor.getId();
                            System.out.println(idValue);
                            goToThirdActivity();
                        }
                    });
                }
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        idValue=actor.getId();
                        System.out.println(idValue);
                        goToThirdActivity();

                        //textView.setText("wassup nigs");
                        //setContentView(scrollView);
                        //linearLayout.setOrientation(LinearLayout.VERTICAL);
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


            //actor.setPicturePath(id);
            //actors.add(actor);
        } catch (JSONException e) {
            // Appropriate error handling code
        }


        System.out.println("Process Finish: " + output);

    }

    private void printInfo() {
        TextView input = (TextView) findViewById(R.id.textView2);
        input.setText(actors.get(0).getName());
    }


    private void showNotFoundNotification() {
        Toast.makeText(this,
                "Sorry we couldn't find anything, please try again",
                Toast.LENGTH_SHORT).
                show();
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
