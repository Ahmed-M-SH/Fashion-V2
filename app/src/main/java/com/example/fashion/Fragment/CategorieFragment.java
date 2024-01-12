package com.example.fashion.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fashion.Adapter.CategoryAdapter;
import com.example.fashion.Adapter.HomeCategoryAdapter;
import com.example.fashion.Domain.Category;
import com.example.fashion.Helper.RetrofitClient;
import com.example.fashion.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategorieFragment extends Fragment  {

    private CategoryAdapter adapter;
    private RecyclerView categoryRecyclerView;

    private int category;
    private ProgressBar progressBar7;

//    public CategorieFragment(int category) {
//        this.category = category;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categorie, container, false);

        initView(view); // Initialize views here
        progressBar7.setVisibility(View.VISIBLE);
        sendCategoryRequest();
        progressBar7.setVisibility(View.GONE);
        return view;
    }

    private void initView(View view) {
        categoryRecyclerView = view.findViewById(R.id.categoriRecle);
        progressBar7 = view.findViewById(R.id.progressBar7);
        category= getActivity().getIntent().getIntExtra("category", 0);
    }
    private void sendCategoryRequest() {
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(Category.class, new CategoryDeserializer())
//                .create();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(ServerDetail.endpoint)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//        ServerDetail serverDetail =  RetrofitClient.getInstance().create(ServerDetail.class);
        Call<List<Category>> call = RetrofitClient.getInstance()
                .getServerDetail()
                .getCategory(category);
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> item = response.body();
                categoryRecyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 3));
                 adapter = new CategoryAdapter(item, category);
//                 adapter.updateData(item);
                categoryRecyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "An error has occured on Category", Toast.LENGTH_LONG)
                        .show();
                Log.i("onFailure", "Error: " + t.getMessage());
            }

        });


    }


}