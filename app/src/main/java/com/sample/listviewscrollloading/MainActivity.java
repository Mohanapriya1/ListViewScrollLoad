package com.sample.listviewscrollloading;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    List<ListModel> arrayList;
    ApiInterface service;
    ListAdapter listAdapter;
    EditText editSearch;
    boolean isLoading = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        editSearch = findViewById(R.id.edit_search);
        arrayList = new ArrayList<>();
        service = ApiClient.getRetrofitInstance().create(ApiInterface.class);
        loadMoreData();

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            private int currentScrollState = 0;
//            private int currentVisibleItemCount = 0;
//            private int currentFirstVisibleItem = 0;
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {}
//            private void isScrollCompleted() {
//
//                if (this.currentVisibleItemCount > 0 && this.currentScrollState == SCROLL_STATE_IDLE) {
//
//                    if(!isLoading){
//                        isLoading = true;
//                        loadMoreData();
//                    }
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                this.currentFirstVisibleItem = firstVisibleItem;
//                this.currentVisibleItemCount = visibleItemCount;
//            }
//        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener(){

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                int lastInScreen = firstVisibleItem + visibleItemCount;
                if((lastInScreen == totalItemCount) && !(isLoading)){
                    isLoading = true;
                    loadMoreData();
                }
            }
        });
    }

    private void loadMoreData() {
        Call<List<ListModel>> call = service.getAllList();
        call.enqueue(new Callback<List<ListModel>>() {
            @Override
            public void onResponse(Call<List<ListModel>> call, Response<List<ListModel>> response) {

                arrayList = response.body();
                listAdapter = new ListAdapter(MainActivity.this,arrayList);
                listView.setAdapter(listAdapter);
                Log.d("TAG","Response = "+arrayList);
            }

            @Override
            public void onFailure(Call<List<ListModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filter(String data) {
        List<ListModel> filterList = new ArrayList<>();
        for(ListModel lm :arrayList){
           if(lm.getTitle().toLowerCase().contains(data.toLowerCase())){
            filterList.add(lm);
           }
        }
        listAdapter.filterList(filterList);
    }

}
