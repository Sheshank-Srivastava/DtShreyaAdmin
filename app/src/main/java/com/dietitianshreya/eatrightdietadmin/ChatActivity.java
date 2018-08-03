package com.dietitianshreya.eatrightdietadmin;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dietitianshreya.eatrightdietadmin.Utils.Util;
import com.dietitianshreya.eatrightdietadmin.Utils.VariablesModels;
import com.dietitianshreya.eatrightdietadmin.adapters.ChatFirebaseAdapter;
import com.dietitianshreya.eatrightdietadmin.adapters.ClickListenerChatFirebase;
import com.dietitianshreya.eatrightdietadmin.models.ChatModelNew;
import com.dietitianshreya.eatrightdietadmin.models.FileModel;
import com.dietitianshreya.eatrightdietadmin.models.UserModel;
import com.dietitianshreya.eatrightdietadmin.view.FullScreenImageActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

import static com.dietitianshreya.eatrightdietadmin.Login.MyPREFERENCES;


public class ChatActivity extends AppCompatActivity implements  View.OnClickListener, ClickListenerChatFirebase {

    private static final int IMAGE_GALLERY_REQUEST = 1;
    private static final int IMAGE_CAMERA_REQUEST = 2;
    private static final int PLACE_PICKER_REQUEST = 3;


    static final String TAG = MainActivity.class.getSimpleName();
    String CHAT_REFERENCE ;

    //Firebase and GoogleApiClient
    private FirebaseAuth mFirebaseAuth;
    Menu menu;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabaseReference;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    //CLass Model
    private UserModel userModel;

    //Views UI
    private RecyclerView rvListMessage;

    private LinearLayoutManager mLinearLayoutManager;
    private ImageView btSendMessage,btEmoji,edattach;
    private EmojiconEditText edMessage;
    private View contentRoot;
    private EmojIconActions emojIcon;
String clientID,dietitianId;
    ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> idList = new ArrayList<>();
    ArrayList<String> urlList = new ArrayList<>();



    //Fil
    private File filePathImageCamera;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        Bundle extras = getIntent().getExtras();
        clientID = extras.getString(VariablesModels.userId);
        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        dietitianId = sharedpreferences.getInt("clientId",0)+"";

        CHAT_REFERENCE = clientID + "-"+ dietitianId;
        if (!Util.verificaConexao(this)){
            Util.initToast(this,"Você não tem conexão com internet");
            finish();
        }else{
            bindViews();
            verificaUsuarioLogado();
        }

        UpdateDietitians();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        StorageReference storageRef = storage.getReferenceFromUrl(Util.URL_STORAGE_REFERENCE).child(Util.FOLDER_STORAGE_IMG);

        if (requestCode == IMAGE_GALLERY_REQUEST){
            if (resultCode == RESULT_OK){
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null){
                    sendFileFirebase(storageRef,selectedImageUri);
                }else{
                    //URI IS NULL
                }
            }
        }else if (requestCode == IMAGE_CAMERA_REQUEST){
            if (resultCode == RESULT_OK){
                if (filePathImageCamera != null && filePathImageCamera.exists()){
                    StorageReference imageCameraRef = storageRef.child(filePathImageCamera.getName()+"_camera");
                    sendFileFirebase(imageCameraRef,filePathImageCamera);
                }else{
                    //IS NULL
                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.dietitian1:

                // verifyStoragePermissions();
            {

                Intent i = new Intent(getApplicationContext(), ChatView.class);
                i.putExtra(VariablesModels.userId, clientID);
                i.putExtra(VariablesModels.dietitianId,idList.get(0));
                startActivity(i);
            }
//                photoCameraIntent();
                break;
            case R.id.superviser:

            {

                Intent i = new Intent(getApplicationContext(), ChatView.class);
                i.putExtra(VariablesModels.userId, clientID);
                i.putExtra(VariablesModels.dietitianId,idList.get(1));
                startActivity(i);
            }

                break;

            case R.id.addnotes:
                clinicalnotes();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

public void clinicalnotes()
    {
        final EditText editText;
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChatActivity.this);
        // Get the layout inflater
        LayoutInflater linf = LayoutInflater.from(getApplicationContext());
        final View inflator = linf.inflate(R.layout.custom_dialog, null);
        alertDialog.setTitle("Add a note...");
        alertDialog.setView(inflator);
        editText = (EditText) inflator.findViewById(R.id.quant);
        editText.setHint("Type here the note");
        TextView text = (TextView) inflator.findViewById(R.id.text);
        TextView dot = (TextView) inflator.findViewById(R.id.dot);
        text.setVisibility(View.GONE);
        dot.setVisibility(View.GONE);
        alertDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!(TextUtils.isEmpty(editText.getText().toString().trim()))){
                    // notes.add(editText.getText().toString().trim());

                    UpdateNotesData(editText.getText().toString());

                }
            }
        });
        alertDialog.show();


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonMessage:
                sendMessageFirebase();


                break;
            case R.id.attachment:
                photoGalleryIntent();
                break;

        }
    }

    @Override
    public void clickImageChat(View view, int position,String nameUser,String urlPhotoUser,String urlPhotoClick) {
        Intent intent = new Intent(this, FullScreenImageActivity.class);
        intent.putExtra("nameUser",nameUser);
        intent.putExtra("urlPhotoUser",urlPhotoUser);
        intent.putExtra("urlPhotoClick",urlPhotoClick);
        startActivity(intent);
    }

    @Override
    public void clickImageMapChat(View view, int position, String latitude, String longitude) {

    }


    /**
     * Envia o arvquivo para o firebase
     */
    private void sendFileFirebase(StorageReference storageReference, final Uri file){
        if (storageReference != null){
            final String name = DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString();
            StorageReference imageGalleryRef = storageReference.child(name+"_gallery");
            UploadTask uploadTask = imageGalleryRef.putFile(file);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG,"onFailure sendFileFirebase "+e.getMessage());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.i(TAG,"onSuccess sendFileFirebase");
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    FileModel fileModel = new FileModel("img",downloadUrl.toString(),name,"");
                    ChatModelNew chatModel = new ChatModelNew(userModel,"",Calendar.getInstance().getTime().getTime()+"",fileModel);
                    mFirebaseDatabaseReference.child(CHAT_REFERENCE).push().setValue(chatModel);
                }
            });
        }else{
            //IS NULL
        }

    }

    /**
     * Envia o arvquivo para o firebase
     */
    private void sendFileFirebase(StorageReference storageReference, final File file){
        if (storageReference != null){
            Uri photoURI = FileProvider.getUriForFile(ChatActivity.this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    file);
            UploadTask uploadTask = storageReference.putFile(photoURI);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG,"onFailure sendFileFirebase "+e.getMessage());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.i(TAG,"onSuccess sendFileFirebase");
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    FileModel fileModel = new FileModel("img",downloadUrl.toString(),file.getName(),file.length()+"");
                    ChatModelNew chatModel = new ChatModelNew(userModel,"",Calendar.getInstance().getTime().getTime()+"",fileModel);
                    mFirebaseDatabaseReference.child(CHAT_REFERENCE).push().setValue(chatModel);
                }
            });
        }else{
            //IS NULL
        }

    }

    /**
     * Obter local do usuario
     */

    /**
     * Enviar foto tirada pela camera
     */
    private void photoCameraIntent(){
        String nomeFoto = DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString();
        filePathImageCamera = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), nomeFoto+"camera.jpg");
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri photoURI = FileProvider.getUriForFile(ChatActivity.this,
                BuildConfig.APPLICATION_ID + ".provider",
                filePathImageCamera);
        it.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
        startActivityForResult(it, IMAGE_CAMERA_REQUEST);
    }

    /**
     * Enviar foto pela galeria
     */
    private void photoGalleryIntent(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture_title)), IMAGE_GALLERY_REQUEST);
    }


    private void sendMessageFirebase(){
        if(edMessage.getText().toString().length()>0) {
            mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
            Log.d("manya", FirebaseDatabase.getInstance().getReference() + "");

            ChatModelNew model = new ChatModelNew(userModel, edMessage.getText().toString(), Calendar.getInstance().getTime().getTime() + "", null);
            //mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

            mFirebaseDatabaseReference.child(CHAT_REFERENCE).push().setValue(model);
            sendNotification(edMessage.getText().toString());
            edMessage.setText(null);

        }
    }

    private void sendNotification(final String message){

        String url = "https://shreyaapi.herokuapp.com/chatnotification/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

//                        Toast.makeText(getApplicationContext(),"Something went wrong!\nCheck your Internet connection and try again..", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicineData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("from",dietitianId);
                params.put("to",clientID);
                params.put("message",message);

                return params;
            }

        };

        int MY_SOCKET_TIMEOUT_MS = 50000;
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void lerMessagensFirebase(){

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        final ChatFirebaseAdapter firebaseAdapter = new ChatFirebaseAdapter(mFirebaseDatabaseReference.child(CHAT_REFERENCE),userModel.getId(),this);
        firebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = firebaseAdapter.getItemCount();
                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    rvListMessage.scrollToPosition(positionStart);
                }
            }
        });
        rvListMessage.setLayoutManager(mLinearLayoutManager);
        rvListMessage.setAdapter(firebaseAdapter);
    }


    private void verificaUsuarioLogado(){
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null){
            startActivity(new Intent(this, Login.class));
            finish();
        }else{
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName("Dietitian"+clientID)
                    .setPhotoUri(Uri.parse("www.uic.mx/posgrados/files/2018/05/default-user.png"))
                    .build();

            mFirebaseUser.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("hehe", "User profile updated.");
                                userModel = new UserModel(mFirebaseUser.getDisplayName(),mFirebaseUser.getPhotoUrl().toString(), clientID);
                                lerMessagensFirebase();
                            }
                            else {
                                Log.d("hehe", "no");
                            }
                        }
                    });
            // Log.d("hehe", mFirebaseUser.getDisplayName());

        }
    }

    private void bindViews(){
        contentRoot = findViewById(R.id.contentRoot);
        edMessage = (EmojiconEditText)findViewById(R.id.editTextMessage);
        btSendMessage = (ImageView)findViewById(R.id.buttonMessage);

        edattach = (ImageView)findViewById(R.id.attachment);
        btSendMessage.setOnClickListener(ChatActivity.this);
        edattach.setOnClickListener(ChatActivity.this);
        btEmoji = (ImageView)findViewById(R.id.buttonEmoji);
        emojIcon = new EmojIconActions(getApplicationContext(),contentRoot,edMessage,btEmoji);
        emojIcon.ShowEmojIcon();
        rvListMessage = (RecyclerView)findViewById(R.id.messageRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLinearLayoutManager.setStackFromEnd(true);

    }

    /**
     * Sign Out no login
     */


    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     */
    public void verifyStoragePermissions() {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(ChatActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    ChatActivity.this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }else{
            // we already have permission, lets go ahead and call camera intent
            photoCameraIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case REQUEST_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    photoCameraIntent();
                }
                break;
        }
    }

    public void UpdateNotesData(final String notes) {

        String url = "https://shreyaapi.herokuapp.com/addnote/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject object = new JSONObject(response);
                            String msg = object.getString("msg");
                            int res= object.getInt("res");


                            if(res==1)
                            {
                                Toast.makeText(getApplicationContext(),"data saved",Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),"Something went wrong!", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicineData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("userId",clientID);
                params.put("note",notes);
                return params;
            }

        };

        int MY_SOCKET_TIMEOUT_MS = 50000;
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }



    public void UpdateDietitians() {

        String url = "https://shreyaapi.herokuapp.com/dietitianids/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONArray array = new JSONArray();

                        try {


                            JSONObject object = new JSONObject(response);
                            String msg = object.getString("msg");
                            int res= object.getInt("res");
                            int count = object.getInt("count");


                            Log.d("tag",count+"");

                            if(res==1)
                            {
                                array = object.getJSONArray("response");

                                for(int i =0;i< array.length();i++)
                                {
                                    JSONObject object1 = array.getJSONObject(i);

                                    if(!String.valueOf(object1.getInt("id")).equals(dietitianId)) {
                                        nameList.add(object1.getString("name"));
                                        idList.add(object1.getString("id"));
                                        urlList.add(object1.getString("url"));
                                    }

                                }
                                if( count ==1)
                                {
                                    MenuItem dietitian1menu= menu.findItem(R.id.dietitian1);
                                    dietitian1menu.setVisible(false);
                                    MenuItem dietitian2menu= menu.findItem(R.id.superviser);
                                    dietitian2menu.setVisible(false);
                                    updateMenuTitles("dummy1","dummy2");


                                }
                                else if( count ==2)
                                {
                                    MenuItem dietitian2menu= menu.findItem(R.id.superviser);
                                    dietitian2menu.setVisible(false);
                                    updateMenuTitles(nameList.get(0),"dummy2");
                                }
                                else
                                {
                                    updateMenuTitles(nameList.get(0),nameList.get(1));

                                }



                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),"Something went wrong!", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicineData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("userId",clientID);

                return params;
            }

        };

        int MY_SOCKET_TIMEOUT_MS = 50000;
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void updateMenuTitles(String dietitian1, String dietitian2) {
        MenuItem dietitian1menu= menu.findItem(R.id.dietitian1);
        dietitian1menu.setTitle(dietitian1);
        MenuItem dietitian2menu= menu.findItem(R.id.superviser);
        dietitian2menu.setTitle(dietitian2);

    }
}