package gr.gkortsaridis.uowmiquiz;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;


public class gameActivity extends ActionBarActivity {

    AsyncTask<String, Void, String> httptask;
    String myId;
    SharedPreferences sharedpreferences;

    private final String webAddress = "https://arch.icte.uowm.gr/iquiz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("myId")!= null)
        {
            myId = bundle.get("myId").toString();
        }

        Log.i("MY ID IS", myId);

        sharedpreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public void answerA(View view){

        httptask = new HttpAsyncTask().execute(webAddress + "/getQuestion.php", "make_guess", myId+":A");

        String x;
        try {
            //Pairnoume tin apantisi tou server
            x = httptask.get();
            Log.i("PIRA", x);
            Toast.makeText(this.getBaseContext(), x, Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void answerB(View view){
        httptask = new HttpAsyncTask().execute(webAddress + "/getQuestion.php", "make_guess", myId+":B");

        String x;
        try {
            //Pairnoume tin apantisi tou server
            x = httptask.get();
            Log.i("PIRA", x);
            Toast.makeText(this.getBaseContext(), x, Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void answerC(View view){
        httptask = new HttpAsyncTask().execute(webAddress + "/getQuestion.php", "make_guess", myId+":C");

        String x;
        try {
            //Pairnoume tin apantisi tou server
            x = httptask.get();
            Log.i("PIRA", x);
            Toast.makeText(this.getBaseContext(), x, Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void answerD(View view){
        httptask = new HttpAsyncTask().execute(webAddress + "/getQuestion.php", "make_guess", myId+":D");

        String x;
        try {
            //Pairnoume tin apantisi tou server
            x = httptask.get();
            Log.i("PIRA", x);
            Toast.makeText(this.getBaseContext(), x, Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void answerE(View view){
        httptask = new HttpAsyncTask().execute(webAddress + "/getQuestion.php", "make_guess", myId+":E");

        String x;
        try {
            //Pairnoume tin apantisi tou server
            x = httptask.get();
            Log.i("PIRA", x);
            Toast.makeText(this.getBaseContext(), x, Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
