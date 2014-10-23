package com.mahmud.test;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/*
 * This class using to check the network connectivity.
 */
public class Utilites 
{
	
	public static boolean checkConn(Context ctx)
    {
        ConnectivityManager conMgr =  (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conMgr.getActiveNetworkInfo();
        if (info == null)
            return false;
          if (!info.isConnected())
            return false;
          if (!info.isAvailable())
            return false;
          return true;


    }

}
