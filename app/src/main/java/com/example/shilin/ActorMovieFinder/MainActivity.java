package com.example.shilin.ActorMovieFinder;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
     String user=null;
     boolean actorButton=false;
     boolean movieButton=false;
     boolean whatsnew = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);       //search button
        final Button button2= (Button) findViewById(R.id.button2);     //actor button
        final Button button3= (Button) findViewById(R.id.button3);     //whats new button
        final Button button4= (Button) findViewById(R.id.button4);      //movie button

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actorButton=true;
                movieButton=false;
                whatsnew=false;
                button2.setTextColor(Color.RED);
                button4.setTextColor(Color.BLACK);
                button3.setTextColor(Color.BLACK);

        }});
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actorButton=false;
                whatsnew=false;
                movieButton=true;
                button4.setTextColor(Color.RED);
                button2.setTextColor(Color.BLACK);
                button3.setTextColor(Color.BLACK);
            }});
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actorButton=false;
                movieButton=false;
                whatsnew=true;
                button4.setTextColor(Color.BLACK);
                button2.setTextColor(Color.BLACK);
                button3.setTextColor(Color.RED);
                searchButtonClick3();
            }});

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actorButton==true) {
                    getStuff();
                    searchButtonClick();
                }
                if(movieButton==true){
                    getStuff();
                    searchButtonClick2();
                }

            }
        });



    }

    private void goToSecondActivity() {
        Intent intent = new Intent(this, SecondActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("stuff",user);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void goToFourthActivity() {
        Intent intent = new Intent(this, FourthActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("stuff",user);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void searchButtonClick() {

        String actorNameToSearch = user;
        if(actorNameToSearch.length() >= 2){

            //MovieDbUrl url = MovieDbUrl.getInstance();
            //String getActorHttpMethod = url.getActorQuery(actorNameToSearch);
            //System.out.println(getActorHttpMethod);
            goToSecondActivity();
            //RetrieveFeedTask downloader= new RetrieveFeedTask();
            //downloader.execute();
        } else {

        }
    }
    public void searchButtonClick2() {

        String actorNameToSearch = user;
        if(actorNameToSearch.length() >= 2){
            goToFourthActivity();

        } else {

        }
    }
    public void searchButtonClick3(){
        Intent intent = new Intent(this, FifthActivity.class);
        startActivity(intent);
    }


    private void getStuff(){
        EditText text = (EditText) findViewById(R.id.editText);
        user=text.getText().toString();
    }

    public String getText(){
        return user;
    }



}
