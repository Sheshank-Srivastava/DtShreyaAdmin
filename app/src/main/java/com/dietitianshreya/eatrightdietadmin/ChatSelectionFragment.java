package com.dietitianshreya.eatrightdietadmin;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dietitianshreya.eatrightdietadmin.Utils.VariablesModels;
import com.dietitianshreya.eatrightdietadmin.adapters.ClientListAdapter;
import com.dietitianshreya.eatrightdietadmin.adapters.ClientListAdapter2;
import com.dietitianshreya.eatrightdietadmin.models.ClientListModel;
import com.dietitianshreya.eatrightdietadmin.models.ClientListModel2;
import com.firebase.client.annotations.Nullable;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static com.dietitianshreya.eatrightdietadmin.Login.MyPREFERENCES;


public class ChatSelectionFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    ArrayList<ClientListModel2> clientList;
    ArrayList<ClientListModel2> filteredData;
    ClientListAdapter2 clientListAdapter;
    String userid;
    private ArrayList<Integer> new1;
    int searchFlag=0;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener mChildEventListener;
    public ChatSelectionFragment() {
        // Required empty public constructor
    }


    public static ChatSelectionFragment newInstance(String param1, String param2) {
        ChatSelectionFragment fragment = new ChatSelectionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View rootView =  inflater.inflate(R.layout.fragment_chat_selection, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.re);
        clientList=new ArrayList<>();
        filteredData = new ArrayList<>();
        clientList.clear();
        filteredData.clear();
        SharedPreferences sharedpreferences1 = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        userid= String.valueOf(sharedpreferences1.getInt("clientId",0));
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("overview").child(userid+"");
        clientListAdapter = new ClientListAdapter2(clientList,getActivity());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(clientListAdapter);
        sendR();


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem searchViewItem = menu.findItem(R.id.actionsearch);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchViewItem.getActionView();
//        searchView.setQueryHint("Type name here...");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);// Do not iconify the widget; expand it by defaul

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {

                // This is your adapter that will be filtered
                //Toast.makeText(getApplicationContext(),"textChanged :"+newText,Toast.LENGTH_LONG).show();

                filter(newText);
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                // **Here you can get the value "query" which is entered in the search box.**

                filter(query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
    }
    public void filter(String charSequence) {
        if (!(TextUtils.isEmpty(charSequence)))
        {
            searchFlag = 1;
            if (filteredData!= null)
                filteredData.clear();
            ClientListAdapter2 filteredAdapter = new ClientListAdapter2(filteredData,getActivity());

            for (ClientListModel2 row : clientList) {

                // name match condition. this might differ depending on your requirement
                // here we are looking for name or phone number match

                if (row.getClientName().toLowerCase().contains(charSequence.toLowerCase())) {
                    filteredData.add(row);
                }
            }

            recyclerView.setAdapter(filteredAdapter);
            filteredAdapter.notifyDataSetChanged();
        } else

        {
            searchFlag = 0;
            recyclerView.setAdapter(clientListAdapter);
            clientListAdapter.notifyDataSetChanged();
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void getData(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void sendR() {
        new1 = new ArrayList<>();
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching Clients...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "https://shreyaapi.herokuapp.com/getclients/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            progressDialog.dismiss();
                            JSONObject result = new JSONObject(response);
                            Log.d("JsonResponse",response.toString());
                            if(result.getInt("res")==1) {
                                JSONArray ar = result.getJSONArray("response");
                                if (ar.length() != 0){
                                    for (int i = 0; i < ar.length(); i++) {
                                        JSONObject ob = ar.getJSONObject(i);
                                        String name,id,daysleft,createIn;
                                        int creatinInt;
                                        name = ob.getString(VariablesModels.user_name);
                                        id = String.valueOf(ob.getInt(VariablesModels.userId));
                                        daysleft = ob.getInt("daysleft")+" days left";

                                        new1.add(Integer.parseInt(id));
                                        clientList.add(new ClientListModel2(name,id,daysleft,true,0));

                                    }
                                    clientListAdapter.notifyDataSetChanged();
                                    getListdata();

                                }else{
                                    //show that there are no appointments
                                }
                            }else{
                                //error
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity().getApplicationContext(),"Something went wrong!\nCheck your Internet connection and try again..", Toast.LENGTH_LONG).show();
                        //Toast.makeText(MedicineData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("dietitianId",userid);
                return params;
            }

        };

        int MY_SOCKET_TIMEOUT_MS = 50000;
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }
    public void getListdata() {
        if (mChildEventListener == null) {
//            Toast.makeText(getContext(), "Hello Snapshot", Toast.LENGTH_SHORT).show();
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    ClientListModel2 model = dataSnapshot.getValue(ClientListModel2.class);
                    Integer no =new Integer(model.getClientId());
                    int n = new1.indexOf(no);
                    if(n==-1){
                        Log.d("PositionOfDataFound",no+"data Not Found"+"");
                    }
                    else{
//                        clientList.remove(n);
                        clientList.get(n).setRead(model.isRead());
                        clientList.get(n).setTimeStamp(model.getTimeStamp());
//                        clientList.add(n,model);
                    }
                    Comparator<ClientListModel2> cmp = Collections.reverseOrder(new TimeStamp());
                    Collections.sort(clientList, cmp);
                    new1.clear();
                    for(int i=0;i<clientList.size();i++){
                        new1.add(Integer.parseInt(clientList.get(i).getClientId()));
                    }
//                    Toast.makeText(getContext(), "Hello Snapshot", Toast.LENGTH_SHORT).show();
                    Log.d("DataSnapShot", model.getClientName() + ".........." + model.isRead() + ".........." + model.getClientId() + "\n" + dataSnapshot.toString());
                    clientListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Log.d("ChildChange", dataSnapshot.toString());
                    ClientListModel2 model = dataSnapshot.getValue(ClientListModel2.class);
                    Integer no =new Integer(model.getClientId());
                    int n = new1.indexOf(no);
                    clientList.get(n).setRead(model.isRead());
                    clientList.get(n).setTimeStamp(model.getTimeStamp());
                    Comparator<ClientListModel2> cmp = Collections.reverseOrder(new TimeStamp());
                    Collections.sort(clientList, cmp);
                    new1.clear();
                    for(int i=0;i<clientList.size();i++){
                        new1.add(Integer.parseInt(clientList.get(i).getClientId()));
                    }
                    clientListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            };
            databaseReference.addChildEventListener(mChildEventListener);
        }
    }
    public class TimeStamp implements Comparator<ClientListModel2> {

        @Override
        public int compare(ClientListModel2 userListModel, ClientListModel2 t1) {
            return (userListModel.getTimeStamp()<t1.getTimeStamp()) ? -1 : (userListModel.getTimeStamp()>t1.getTimeStamp()) ? 1 : 0;
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        clientList.clear();
//        filteredData.clear();
//        sendR();
//        getListdata();
//    }
}


