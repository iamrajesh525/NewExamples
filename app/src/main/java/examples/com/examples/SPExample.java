package examples.com.examples;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Rajesh on 14-May-17.
 */

public class SPExample {

    public void saveSP(String key, String data) {

        sp = this.getSharedPreferences(UtilsConstants.SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, data);
        editor.commit();

    }

    public String getSP(String key) {

        sp = this.getSharedPreferences(UtilsConstants.SP_NAME, Context.MODE_PRIVATE);
        String data = sp.getString(key, "no");

        return data;
    }

    public void clearSP(){
        SharedPreferences preferences = getSharedPreferences("MyPrefs", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

}
