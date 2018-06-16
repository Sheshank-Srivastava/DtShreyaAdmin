package com.dietitianshreya.dtshreyaadmin.Utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;


public class Util {

    public static final String URL_STORAGE_REFERENCE = "gs://dtshreya-766ef.appspot.com";
    public static final String FOLDER_STORAGE_IMG = "images";

    public static void initToast(Context c, String message){
        Toast.makeText(c,message, Toast.LENGTH_SHORT).show();
    }

    public  static boolean verificaConexao(Context context) {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        conectado = conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected();
        return conectado;
    }


}