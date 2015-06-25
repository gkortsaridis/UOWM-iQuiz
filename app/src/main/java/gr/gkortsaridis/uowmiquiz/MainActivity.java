package gr.gkortsaridis.uowmiquiz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends ActionBarActivity {

    AsyncTask<String, Void, String> httptask;
    EditText myNIckname;
    SharedPreferences sharedpreferences;

    private final String webAddress = "https://arch.icte.uowm.gr/iquiz";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myNIckname = (EditText) findViewById(R.id.nicknameET);

        sharedpreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);

        if(!isConnected())
        {
            //Emfanizo to katallilo Dialod Box
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Δεν υπάρχει σύνδεση Internet");
            builder.setMessage("Η συσκευή σας δεν είναι συνδεδεμένη στο Internet");
            builder.setPositiveButton("Ενεργοποίηση WIFI", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent settings = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    settings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(settings);
                }

            });
            builder.setNegativeButton("Έξοδος", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }

            });
            AlertDialog alert = builder.create();
            alert.show();
        }

    }

    public void connectToServer(View view) {

        if (myNIckname.getText().toString().length() < 25 && (!myNIckname.getText().toString().equals("") || !myNIckname.getText().toString().equals(" ") || !myNIckname.getText().toString().equals("   ") || !myNIckname.getText().toString().equals("    ") || !myNIckname.getText().toString().equals("     "))) {
                httptask = new HttpAsyncTask().execute(webAddress + "/lobby.php", "connect_player", myNIckname.getText().toString());
                String x;
                try {
                    //Pairnoume tin apantisi tou server
                    x = httptask.get();
                    Log.i("PIRA", x);

                    String[] parts = x.split(":");
                    if (parts[0].equals("OK")) {
                        Intent i = new Intent(MainActivity.this, gameActivity.class);
                        i.putExtra("myId", parts[1]);

                        Toast.makeText(this.getBaseContext(), "You succesfully checked in.", Toast.LENGTH_SHORT).show();
                        startActivity(i);
                    } else {
                        Toast.makeText(this.getBaseContext(), "ERROR:" + x, Toast.LENGTH_SHORT).show();
                    }

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        } else {
            Toast.makeText(this.getBaseContext(), "Select valid nickname", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_about){
            Intent intent = new Intent(MainActivity.this , AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isConnected()
    {
        //Elegxei an i siskeui einai sindedemeni sto internet, me WIFI i me Data sindesi
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

}
