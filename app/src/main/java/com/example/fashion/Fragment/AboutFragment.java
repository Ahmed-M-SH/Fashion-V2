package com.example.fashion.Fragment;


import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.fashion.R;
public class AboutFragment extends Fragment {

    private ImageView imageViewLogo;
    private TextView textViewAppName, textViewDescription;
    private Button buttonContactUs, buttonShareWhatsApp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        imageViewLogo = view.findViewById(R.id.imageViewLogo);
        textViewAppName = view.findViewById(R.id.textViewAppName);
        textViewDescription = view.findViewById(R.id.textViewDescription);
        buttonContactUs = view.findViewById(R.id.buttonContactUs);
        buttonShareWhatsApp = view.findViewById(R.id.buttonShareWhatsApp);

        imageViewLogo.setImageResource(R.drawable.cat1);
        textViewAppName.setText("اسم التطبيق");
        textViewDescription.setText("وصف التطبيق");

        buttonContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // قم بتنفيذ الإجراء المطلوب عند النقر على زر "اتصل بنا" هنا
            }
        });

        buttonShareWhatsApp.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            public void onClick(View v) {
                String phoneNumber = "776965383";
                String message = "مرحبا بكم في تطبيق فاشيون: ";

                String url = "https://wa.me/" + phoneNumber + "?text=" + Uri.encode(message);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));

                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "تطبيق الواتساب غير موجود على جهازك", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}