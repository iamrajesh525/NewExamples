package examples.com.examples.recyclerview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import my.app.educationinstitutes.adapters.NotificationAdapter;
import my.app.educationinstitutes.models.NotificationsModel;
import my.app.educationinstitutes.utils.LruCacheSave;
import my.app.educationinstitutes.utils.OnLoadMoreListener;
import my.app.educationinstitutes.utils.UtilsConstants;
import my.app.educationinstitutes.utils.VolleySingleton;

public class Notifications extends AppCompatActivity {

    ProgressBar progressBarItem;
    RecyclerView recyclerView;
    NotificationAdapter mAdapter;
    public ArrayList<NotificationsModel> list = new ArrayList<>();
    TextView alert;
    //Button select_area;
    static final int ALL_AREA_FLAG = 45;
    SharedPreferences sp;
    String offset = "0";
    int fixed_offset;
    int previous_offset_count;
    Boolean NO_POST_DATA = false;
    Boolean API_IS_LOADING=false;
    Typeface FONT_REGULAR,FONT_BOLD;
    TextView title;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        progressBarItem = (ProgressBar) findViewById(R.id.progressBarItem);
        FONT_REGULAR = Typeface.createFromAsset(getApplicationContext().getAssets(), UtilsConstants.Custom_Font_Regular);
        FONT_BOLD = Typeface.createFromAsset(getApplicationContext().getAssets(), UtilsConstants.Custom_Font_bold);
        title= (TextView) findViewById(R.id.title);
        title.setTypeface(FONT_REGULAR);

        alert = (TextView) findViewById(R.id.alert);
        RelativeLayout up_nav_rl = (RelativeLayout) findViewById(R.id.up_nav_rl);
        up_nav_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new NotificationAdapter(this, list,recyclerView);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if(API_IS_LOADING==false && NO_POST_DATA==false){
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
                    if(  isInternetPresent()){
                        //showLoading();
                        checkCache();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),UtilsConstants.NO_INTERNET,Toast.LENGTH_LONG).show();
                        //showAlertDialog(this,"Alert",UtilsConstants.NO_INTERNET,true);
                    }

                }

            }
        });

            if(  isInternetPresent()){
                showLoading();
                checkCache();
            }
            else{
                showAlertDialog(this,"Alert",UtilsConstants.NO_INTERNET,true);
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

        return UtilsConstants.API_NOTIFICATIONS+"?start=" + previous_offset_count;

    }

    public void checkCache() {
       // showLoading();
       // String response_from_cache = retrieveDatFromCache(setURL());
        String response_from_cache = null;
        if (response_from_cache == null) {
            getNotifications();
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
        JSONObject obMain = null;
        try {
            obMain = new JSONObject(response);
            JSONArray arry = obMain.getJSONArray("result");
            for (int i = 0; i < arry.length(); i++) {
                JSONObject ob = arry.getJSONObject(i);
                NotificationsModel mm = new NotificationsModel();
                mm.setNoti_message(ob.getString("text"));
                mm.setDate(getRelativeDate(ob.getString("createdTime")));

                list.add(mm);
            }
            mAdapter.notifyDataSetChanged();
            mAdapter.setLoaded();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        hideLoading();
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

    private void getNotifications() {
        API_IS_LOADING=true;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, setURL(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject ob = new JSONObject(response);
                            if(ob.has("offset")){
                                fixed_offset = ob.getInt("offset");
                                offset = ob.getString("offset");
                            }
                            if (ob.getString("status").equals("1")) {
                                alert.setVisibility(View.GONE);
                                //saveDataCache(setURL(), response);
                                if(!list.isEmpty()){
                                    list.remove(list.size() - 1);
                                    mAdapter.notifyItemRemoved(list.size());
                                }
                                setResponse(response);
                            }
                            else
                            {
                                NO_POST_DATA=true;
                                if(!list.isEmpty()){
                                    list.remove(list.size() - 1);
                                    mAdapter.notifyItemRemoved(list.size());
                                }
                                if(previous_offset_count==0){
                                    //no results so show some message
                                    alert.setVisibility(View.VISIBLE);
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                        API_IS_LOADING=false;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        API_IS_LOADING=false;
                        hideLoading();
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
        };

        RequestQueue requestQueue1 = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue(this.getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(my.app.educationinstitutes.utils.UtilsConstants.VOLLEY_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue1.add(stringRequest);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        switch (requestCode) {
            case ALL_AREA_FLAG:

                // if (requestCode == COURSE_ACT) {
                if (resultCode == this.RESULT_OK) {

                    recyclerView.setVisibility(View.VISIBLE);
                    String result = data.getStringExtra("data");
                    saveSP(UtilsConstants.SELECTED_AREA_INSTITUE_LIST_NAME, result);
                    saveSP(UtilsConstants.SELECTED_AREA_INSTITUE_LIST_ID, data.getStringExtra("id"));

                    list.clear();
                    //resetting all offset parameters
                     offset = "0";
                     fixed_offset=0;
                     previous_offset_count=0;
                     NO_POST_DATA = false;
                     API_IS_LOADING=false;

                    if(  isInternetPresent()){
                        showLoading();
                        checkCache();
                    }
                    else{
                        showAlertDialog(this,"Alert",UtilsConstants.NO_INTERNET,true);
                    }


                }
                if (resultCode == this.RESULT_CANCELED) {
                    //Write your code if there's no result
                }
                //}
                break;

        }
    }
    public void showAlertDialog(Context context, String title, final String message , final Boolean status ) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Notifications.this);
        // set title
        alertDialogBuilder.setTitle(title);
        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        if(status){
                            if(message.equals(UtilsConstants.NO_INTERNET)){
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
    public Boolean isInternetPresent(){
        ConnectionDetector  cd = new ConnectionDetector(Notifications.this);
        return cd.isConnectingToInternet();
    }
    public void tutorialDialog(){
        final Dialog alertDialog = new Dialog(this, android.R.style.Theme_Light);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.tutorial_main);
        alertDialog.setCancelable(true);

        TextView txt = (TextView) alertDialog.findViewById(R.id.txt);
        txt.setText("Click on it to filter the Institutes.");
        TextView ok = (TextView) alertDialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        CheckBox tutorial_cb = (CheckBox) alertDialog.findViewById(R.id.tutorial_cb);
        tutorial_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    saveSP(UtilsConstants.TUTORIAL_INSTITUTE,UtilsConstants.TUTORIAL_INSTITUTE);
                }
                else
                {
                    saveSP(UtilsConstants.TUTORIAL_INSTITUTE,"no");
                }
            }
        });


        // setting custom font
        txt.setTypeface(FONT_REGULAR);


        //alertDialog.setCanceledOnTouchOutside(true);

        alertDialog.getWindow().setBackgroundDrawableResource(R.color.translucent_black);
        alertDialog.show();
    }
    //method to get "time ago" type timmings.
    public String getRelativeDate(String date) {
        int dat[] = {0, 0, 0, 0, 0, 0};
        Calendar cal = Calendar.getInstance();
        date = date.replace("-", ",").replace(" ", ",").replace(":", ",");
        StringTokenizer st = new StringTokenizer(date, ",");
        int i = 0;
        while (st.hasMoreTokens()) {
            dat[i] = Integer.parseInt(st.nextToken());
            i++;
        }
        cal.set(dat[0], dat[1], dat[2], dat[3], dat[4], dat[5]);
        long ms = cal.getTimeInMillis();
        return getDisplayableTime(ms);
    }

    public String getDisplayableTime(long delta) {
        int dat1[] = {0, 0, 0, 0, 0, 0};
        long difference = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy,MM,dd,HH,mm,ss");
        Calendar cal = Calendar.getInstance();
        StringTokenizer st = new StringTokenizer(dateFormat.format(cal.getTime()), ",");
        int i = 0;
        while (st.hasMoreTokens()) {
            dat1[i] = Integer.parseInt(st.nextToken());
            i++;
        }

        cal.set(dat1[0], dat1[1], dat1[2], dat1[3], dat1[4], dat1[5]);
        long mDate = cal.getTimeInMillis();
        if (mDate > delta) {
            difference = mDate - delta;
            final long seconds = difference / 1000;
            final long minutes = seconds / 60;
            final long hours = minutes / 60;
            final long days = hours / 24;
            final long months = days / 31;
            final long years = days / 365;
            if (seconds < 0) {
                return "not yet";
            } else if (seconds < 60) {
                return seconds == 1 ? "one second ago" : seconds + " seconds ago";
            } else if (seconds < 120) {
                return "a minute ago";
            } else if (seconds < 2700) // 45 * 60
            {
                return minutes + " minutes ago";
            } else if (seconds < 5400) // 90 * 60
            {
                return "an hour ago";
            } else if (seconds < 86400) // 24 * 60 * 60
            {
                return hours + " hours ago";
            } else if (seconds < 172800) // 48 * 60 * 60
            {
                return "yesterday";
            } else if (seconds < 2592000) // 30 * 24 * 60 * 60
            {
                return days + " days ago";
            } else if (seconds < 31104000) // 12 * 30 * 24 * 60 * 60
            {
                return months <= 1 ? "one month ago" : months + " months ago";
            } else {
                return years <= 1 ? "one year ago" : years + " years ago";
            }
        }
        return null;
    }
}
