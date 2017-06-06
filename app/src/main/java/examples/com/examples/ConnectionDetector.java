package examples.com.examples;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Rajesh on 14-May-17.
 */

public class  ConnectionDetector {

    private Context _context;

    public ConnectionDetector(Context context){
        this._context = context;
    }

    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }
}

  /*  public Boolean isInternetPresent(){
        ConnectionDetector  cd = new ConnectionDetector(InstituteProfile.this);
        return cd.isConnectingToInternet();
    }
*/


  /*  public Boolean isInternetPresent(){
        ConnectionDetector  cd = new ConnectionDetector(MainActivity.this);
        return cd.isConnectingToInternet();
    }*/
