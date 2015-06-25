package gr.gkortsaridis.uowmiquiz;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SettingsActivity extends ActionBarActivity {

    EditText serverAddress;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    Spinner connectionType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedpreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);

        serverAddress = (EditText) findViewById(R.id.serverEditText);
        serverAddress.setText(sharedpreferences.getString("address", ""));

        connectionType = (Spinner) findViewById(R.id.connectionTypeSpinner);
        List<String> list = new ArrayList<String>();
        if(sharedpreferences.getString("connType","https://").equals("https://")) {
            list.add("https://");
            list.add("http://");
        }
        else
        {
            list.add("http://");
            list.add("https://");
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        connectionType.setAdapter(dataAdapter);
        connectionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor = sharedpreferences.edit();
                if(i == 0)editor.putString("connType","http://");
                else editor.putString("connType","https://");
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                editor = sharedpreferences.edit();
                editor.putString("connType","https://");
                editor.commit();
            }
        });
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //An patithei to koumpi BACK
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {

            //Dimiourgo tin full dieuthinsi
            String adrs = "";
            adrs += serverAddress.getText().toString();

            //Tin apothikeuo sta settings
            editor = sharedpreferences.edit();
            editor.putString("address", adrs);
            //editor.putBoolean("autoSend", autoSend.isChecked());
            //editor.putInt("secondsToWait", progressChanged);
            editor.commit();

            //Emfanizo to Toast gia tin epitiximeni allagi
            Toast.makeText(getBaseContext(), "Server Address has changed to "+connectionType.getSelectedItem().toString() + adrs, Toast.LENGTH_SHORT).show();

            //Kai teliono
            finish();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}