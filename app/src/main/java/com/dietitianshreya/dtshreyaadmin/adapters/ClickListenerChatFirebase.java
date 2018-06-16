package com.dietitianshreya.dtshreyaadmin.adapters;

/**
 * Created by manyamadan on 07/05/18.
 */

import android.view.View;


public interface ClickListenerChatFirebase {


    void clickImageChat(View view, int position, String nameUser, String urlPhotoUser, String urlPhotoClick);

    void clickImageMapChat(View view, int position, String latitude, String longitude);

}