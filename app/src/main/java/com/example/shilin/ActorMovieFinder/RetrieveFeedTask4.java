package com.example.shilin.ActorMovieFinder;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by shilinlu on 8/20/2016.
 */
public class RetrieveFeedTask4  extends AsyncTask <Void, Void, String> implements Serializable {
    private Exception exception;
    private ArrayList<Actor> actors=new ArrayList();
    String search="";
    String responses="";
    public AsyncResponse delegate = null;
    public RetrieveFeedTask4() {

    }

    protected void onPreExecute() {

    }

    protected String doInBackground(Void... urls) {
        //String email = emailText.getText().toString();
        // Do some validation here

        try {

            //Bundle bundle = getIntent().getExtras();
            //String stuff = bundle.getString("stuff");

            MovieDbUrl movieDb= MovieDbUrl.getInstance();
            String url=movieDb.getNowPlaying();
            URL Realurl = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) Realurl.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String response) {
        /**
         if (response == null) {
         response = "THERE WAS AN ERROR";
         }

         Log.i("INFO", response);
         //textView2.setText(response);
         responses = response;
         try {
         JSONObject results = null;
         results = new JSONObject(response);
         JSONArray data = results.getJSONArray("results");
         for(int i=0;i<data.length()-1;i++) {
         JSONObject jsonActor = data.getJSONObject(i);
         Actor actor = new Actor();
         actor.setId(jsonActor.getInt("id"));
         actor.setName(jsonActor.getString("name"));
         actor.setPicturePath(jsonActor.getString("profile_path"));
         actors.add(actor);
         System.out.println(actor.getId());
         System.out.println(actor.getName());
         System.out.println(actor.getPicturePath());
         }
         //actor.setPicturePath(id);
         //actors.add(actor);
         } catch (JSONException e) {
         // Appropriate error handling code
         }
         **/


        delegate.processFinish(response);


    }
    public ArrayList<Actor> getActors(){
        return actors;
    }




    protected String getResponses(){
        return responses;
    }

}