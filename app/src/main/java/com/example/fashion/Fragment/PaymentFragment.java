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

import com.bumptech.glide.Glide;
import com.example.fashion.Activity.CartActivity;
import com.example.fashion.Activity.PaymentActivity;
import com.example.fashion.R;


    public class PaymentFragment extends Fragment {
        private static final int IMAGE_PICK_REQUEST = 1;
        private ImageView imageView;
        private Button downloadButton;

        private String imageUrl = "http://example.com/image.jpg";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_payment, container, false);

            AppCompatSpinner spinner1 = view.findViewById(R.id.spinner_city);
            AppCompatSpinner spinner2 = view.findViewById(R.id.spinner_currency);

            String[] regions1 = {"محافظة 1", "محافظة 2", "محافظة 3"};
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, regions1);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);

            String[] regions2 = {"يمني عملة قديمة", "سعودي"};
            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, regions2);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);

            imageView = view.findViewById(R.id.imageView);
            downloadButton = view.findViewById(R.id.downloadButton);

            downloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDownloadDialog(imageUrl);
                }
            });

            // Load the image using a library like Picasso or Glide
            Glide.with(this).load(imageUrl).into(imageView);

            return view;
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
    }