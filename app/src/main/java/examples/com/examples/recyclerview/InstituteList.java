package examples.com.examples.recyclerview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import my.app.educationinstitutes.adapters.InstituteViewAdapter;
import my.app.educationinstitutes.models.InstituteListModel;
import my.app.educationinstitutes.utils.LruCacheSave;
import my.app.educationinstitutes.utils.OnLoadMoreListener;
import my.app.educationinstitutes.utils.UtilsConstants;
import my.app.educationinstitutes.utils.VolleySingleton;

public class InstituteList extends AppCompatActivity {

    ProgressBar progressBarItem;
    RecyclerView recyclerView;
    InstituteViewAdapter mAdapter;
    public ArrayList<InstituteListModel> list = new ArrayList<>();
    TextView alert;
    Button select_area;
    static final int ALL_AREA_FLAG = 45;
    SharedPreferences sp;
    RelativeLayout filter_rl;
    //offset related to visits
    String offset = "0";
    int fixed_offset;
    int previous_offset_count;
    Boolean NO_POST_DATA = false;
    Boolean API_IS_LOADING = false;
    Typeface FONT_REGULAR, FONT_BOLD;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute_list);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new InstituteViewAdapter(this, list, recyclerView);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (API_IS_LOADING == false && NO_POST_DATA == false) {
                    list.add(null);
                    mAdapter.notifyItemInserted(list.size() - 1);

                    if (offset.equals("0")) {
                        previous_offset_count = Integer.parseInt(offset);
                    } else {
                        previous_offset_count = fixed_offset + previous_offset_count;
                        //  Log.e("", "off previous_offset_count is " + previous_offset_count);
                    }
                    if (previous_offset_count == 0) {
                        // visitsAdapter.clearData();
                    }
                    if (isInternetPresent()) {
                        //showLoading();
                        checkCache();
                    } else {
                        Toast.makeText(getApplicationContext(), UtilsConstants.NO_INTERNET, Toast.LENGTH_LONG).show();
                        //showAlertDialog(this,"Alert",UtilsConstants.NO_INTERNET,true);
                    }

                }

            }
        });
        if (!getSP(UtilsConstants.SELECTED_AREA_INSTITUE_LIST_ID).equals("no") && !getSP(UtilsConstants.SELECTED_AREA_INSTITUE_LIST_NAME).equals("no")) {
            if (isInternetPresent()) {
                showLoading();
                checkCache();
            } else {
                showAlertDialog(this, "Alert", UtilsConstants.NO_INTERNET, true);
            }

        } else {
            recyclerView.setVisibility(View.GONE);
            select_area.setVisibility(View.VISIBLE);
            alert.setVisibility(View.GONE);

        }
        // checkCache();
    }

    //new code
    public void saveSP(String key, String data) {
        sp = this.getSharedPreferences(UtilsConstants.SP_NAME_ANOTHER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, data);
        editor.commit();

    }


    public String getSP(String key) {
        sp = this.getSharedPreferences(UtilsConstants.SP_NAME_ANOTHER, Context.MODE_PRIVATE);
        String data = sp.getString(key, "no");

        return data;
    }

    public String setURL() {

        return "url here";
    }

    public void checkCache() {
        // showLoading();
        // String response_from_cache = retrieveDatFromCache(setURL());
        String response_from_cache = null;
        if (response_from_cache == null) {

            getInstitutes();

        } else {

            setResponse(response_from_cache);

        }
    }

    public String retrieveDatFromCache(String url) {
        //To get data from cache using the key.
        return (String) LruCacheSave.getInstance().getLru().get(url);


    }

    public void saveDataCache(String url, String response) {
        //Saving data to cache. it will later be retrieved using the key
        LruCacheSave.getInstance().getLru().put(url, response);

    }

    public void setResponse(String response) {

    }

    public void showLoading() {
        alert.setVisibility(View.GONE);
        progressBarItem.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    public void hideLoading() {
        progressBarItem.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void getInstitutes() {

    }

    public void showAlertDialog(Context context, String title, final String message, final Boolean status) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InstituteList.this);
        // set title
        alertDialogBuilder.setTitle(title);
        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        if (status) {
                            if (message.equals(UtilsConstants.NO_INTERNET)) {
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }


                        }

                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public Boolean isInternetPresent() {
        ConnectionDetector cd = new ConnectionDetector(InstituteList.this);
        return cd.isConnectingToInternet();
    }

}
