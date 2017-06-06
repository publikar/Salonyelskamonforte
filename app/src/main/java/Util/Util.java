package Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by A on 06/06/2017.
 */

public class Util {

    public static boolean checkInternetConnection(Context ctx)
    {
        ConnectivityManager conMgr =  (ConnectivityManager)ctx.getSystemService(ctx.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){
           return false;
        }else{
           return true;
        }
    }
}
