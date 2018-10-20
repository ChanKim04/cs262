package ck45.cs262.calvin.edu.homework2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    private EditText mIdInput;
    private TextView mFetchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIdInput = (EditText) findViewById(R.id.IdInput);
        mFetchText = (TextView) findViewById(R.id.fetchText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void fetchID(View view) {

        String queryString = mIdInput.getText().toString();

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            mFetchText.setText(R.string.loading);
            Bundle queryBundle = new Bundle();
            queryBundle.putString("playerInt", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
        }

        else {
            mFetchText.setText("");
        }
    }

    @Nullable
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle args) {
        Log.d("args bundle", args.getString("playerInt"));
        return new BookLoader(this, args.getString("playerInt"));
    }

    @Override
    public void onLoadFinished(@Nullable Loader<String> loader, String s) {
        String queryInt = s.split("%")[0];
        s = s.split("%")[1];
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            mFetchText.setText("");
            //Iterate through the results
            for(int i = 0; i<itemsArray.length(); i++){
                String playerID=null;
                String name=null;
                String email=null;
                JSONObject info = itemsArray.getJSONObject(i); //Get the current item

                playerID = info.getString("id");
                email = info.getString("emailAddress");
                try {
                    name = info.getString("name");
                } catch (Exception e){
                    e.printStackTrace();
                    name = "no name";
                }
                if (queryInt.equals("")){
                    mFetchText.append(playerID + ", " + name + ", " + email + "\n");

                } else {
                    if (queryInt.equals(playerID)) {
                        mFetchText.append(playerID + ", " + name + ", " + email);
                        break;
                    }
                }
            }
        } catch (Exception e){
            mFetchText.setText("");
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@Nullable Loader<String> loader) {

    }
}
