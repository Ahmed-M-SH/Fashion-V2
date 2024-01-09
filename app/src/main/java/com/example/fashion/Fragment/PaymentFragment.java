package com.example.fashion.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.example.fashion.Activity.CartActivity;
import com.example.fashion.Activity.PaymentActivity;
import com.example.fashion.Domain.City;
import com.example.fashion.Domain.Currency;
import com.example.fashion.Domain.PaymentDetail;
import com.example.fashion.Domain.PaymentType;
import com.example.fashion.Helper.RetrofitClient;
import com.example.fashion.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaymentFragment extends Fragment {
        private static final int IMAGE_PICK_REQUEST = 1;
        private ImageView imageView;
        private Button downloadButton;

        private String imageUrl = "http://example.com/image.jpg";
        private PaymentDetail paymentDetail;
       private AppCompatSpinner spinnerCity ;
    private AppCompatSpinner spinnerCurrency;
    private RadioGroup payment_group;



    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_payment, container, false);

            initView(view);
            sendPaymentDetailsRequest();

            // Load the image using a library like Picasso or Glide
            Glide.with(this).load(imageUrl).into(imageView);

            return view;
        }

        public void initView(View view) {
            spinnerCity = view.findViewById(R.id.spinner_city);
            spinnerCurrency = view.findViewById(R.id.spinner_currency);
            imageView = view.findViewById(R.id.imageView);
            downloadButton = view.findViewById(R.id.downloadButton);
            payment_group = view.findViewById(R.id.paynent_group);
            downloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDownloadDialog(imageUrl);
                }
            });
        }
        private void showDownloadDialog(String imageUrl) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, IMAGE_PICK_REQUEST);
        }
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == IMAGE_PICK_REQUEST && resultCode == PaymentActivity.RESULT_OK) {
                if (data != null) {
                    Uri imageUri = data.getData();
                    if (imageUri != null) {
                        Glide.with(this).load(imageUri).into(imageView);
                    }
                }
            }
        }

        public  void sendPaymentDetailsRequest(){
            String auth ="token 4ff24a3114344bc978419193eacdbca8316a82c8";
            if (isAdded() && getContext() != null) {
                Call<PaymentDetail> call = RetrofitClient.getInstance().getServerDetail().getPaymentDetail(auth);
                call.enqueue(new Callback<PaymentDetail>() {
                    @Override
                    public void onResponse(Call<PaymentDetail> call, Response<PaymentDetail> response) {
                        if (isAdded() && getContext() != null && !getActivity().isFinishing()) {
                            paymentDetail = response.body();
                            if (response.isSuccessful()){
                                for (PaymentType payment_type : paymentDetail.getPaymentType()) {
                                    RadioButton button = new RadioButton(requireContext());
                                    button.setText(payment_type.getName());
                                    button.setTag(payment_type.getId());
                                    payment_group.addView(button);
                                }
                                ArrayAdapter<City> adapter1 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, paymentDetail.getCity());
                                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerCity.setAdapter(adapter1);
                                ArrayAdapter<Currency> adapter2 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, paymentDetail.getCurrency());
                                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerCurrency.setAdapter(adapter2);
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<PaymentDetail> call, Throwable t) {
                    }
                });
            }
        }
    }