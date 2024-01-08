package com.example.fashion.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fashion.Activity.NotificationActivity;
import com.example.fashion.Adapter.HomeCategoryAdapter;
import com.example.fashion.Adapter.HomeProductAdapter;
import com.example.fashion.Domain.Category;
import com.example.fashion.Domain.NotificationDomain;
import com.example.fashion.Domain.ProductResult;
import com.example.fashion.Helper.RetrofitClient;
import com.example.fashion.Helper.TinyDB;
import com.example.fashion.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private RecyclerView productrecyclerView,categoryRecyclerView;
    RecyclerView.Adapter productAdapter,categoryAdapter;
    AppCompatButton notification_status;
    private ImageView notificationImg;
    private TinyDB tinyDB;
    private ViewPager2 viewPager;
    private MyAdapter adapter;

    private Handler handler;
    private Runnable runnable;
    private int delayTime = 5000; // تأخير بين كل انتقال صفحة (بالميلي ثانية)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initView(view);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view); // Initialize views here
        return view;
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
                .getCategory();
//

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
               List<Category> item = response.body();
                categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                categoryAdapter = new HomeCategoryAdapter(item);
                categoryRecyclerView.setAdapter(categoryAdapter);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "An error has occured on Category", Toast.LENGTH_LONG)
                        .show();
                Log.i("onFailure", "Error: " + t.getMessage());
            }

        });


    }

    @Override
    public void onResume() {
        super.onResume();
        List<NotificationDomain> notificationCounts = tinyDB.getListObjectNotification("unReadNotifications");
        if (notificationCounts.size() == 0) {
            notification_status.setVisibility(View.INVISIBLE);
        }
        else {
            notification_status.setVisibility(View.VISIBLE);
            notification_status.setText("" + notificationCounts.size());
        }

        handler.postDelayed(runnable, delayTime);

    }

    private void sendProductRequest() {
        Call<ProductResult> call = RetrofitClient.getInstance()
                .getServerDetail()
                .getProduct();
        call.enqueue(new Callback<ProductResult>() {
            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                ProductResult item = response.body();
                productrecyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));
                Log.i("RESPONSE", "OnResponse: " + item);
                productAdapter = new HomeProductAdapter(item);
                productrecyclerView.setAdapter(productAdapter);
                Log.i("RESPONSE", "OnResponse: " + item);
            }


            @Override
            public void onFailure(Call<ProductResult> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "An error has occured on product", Toast.LENGTH_LONG)
                        .show();
                Log.i("onFailure", "Error: " + t.getMessage());

            }
        });
    }

//    private void initView(View view) {
//        productrecyclerView=view.findViewById(R.id.itme_recyclerView);
//        categoryRecyclerView =view.findViewById(R.id.icon_recyclerView);
//        notificationImg = view.findViewById(R.id.notificationImg);
//        notification_status = view.findViewById(R.id.noitficatin_stetes);
//        tinyDB = new TinyDB(getActivity().getApplicationContext());
//
//        notificationImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity().getApplicationContext(), NotificationActivity.class);
//                getActivity().startActivity(intent);
//            }
//        });
//
//    }
    private void initView(View view) {
        // تحضير البيانات (الصور) لعرضها في ViewPager2
        List<Integer> images = getImages();

        productrecyclerView = view.findViewById(R.id.itme_recyclerView);
        categoryRecyclerView = view.findViewById(R.id.icon_recyclerView);
        notificationImg = view.findViewById(R.id.notificationImg);
        notification_status = view.findViewById(R.id.noitficatin_stetes);
        tinyDB = new TinyDB(getActivity().getApplicationContext());
        viewPager = view.findViewById(R.id.view_pager);

        notificationImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), NotificationActivity.class);
                getActivity().startActivity(intent);
            }
        });
// قم بإعداد محول الصفحات الخاص بك في ViewPager2

// إعداد المتغيرات للتحكم في انتقال الصفحات تلقائيًا
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // تحقق مما إذا كانت الصفحة الحالية هي الصفحة الأخيرة، وفي ذلك الحالة الانتقال إلى الصفحة الأولى
                if (viewPager.getCurrentItem() == viewPager.getAdapter().getItemCount() - 1) {
                    viewPager.setCurrentItem(0);
                } else {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
                handler.postDelayed(this, delayTime);
            }
        };

        // تهيئة ViewPager2
        viewPager = view.findViewById(R.id.view_pager);
        adapter = new MyAdapter(images);
        viewPager.setAdapter(adapter);

        sendProductRequest();
        sendCategoryRequest();
    }

    private List<Integer> getImages() {
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.cat1); // أضف مورد الصورة الأولى هنا
        images.add(R.drawable.pic2); // أضف مورد الصورة الثانية هنا
        images.add(R.drawable.cat1); // أضف مورد الصورة الثالثة هنا
        // وهكذا يمكنك إضافة المزيد من الصور حسب الحاجة

        return images;
    }

    private static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<Integer> images;

        public MyAdapter(List<Integer> images) {
            this.images = images;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_fragment, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            int imageRes = images.get(position);
            holder.imageView.setImageResource(imageRes);
        }

        @Override
        public int getItemCount() {
            return images.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.image_view);
            }
        }

    }
}
