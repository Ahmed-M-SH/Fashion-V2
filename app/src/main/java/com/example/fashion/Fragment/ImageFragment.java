package com.example.fashion.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import com.example.fashion.R;
public class ImageFragment extends Fragment {

    public static ImageFragment newInstance(int imageRes) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putInt("imageRes", imageRes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.image_fragment, container, false);
        // Initialize views and handle fragment logic here
        return view;
    }
}