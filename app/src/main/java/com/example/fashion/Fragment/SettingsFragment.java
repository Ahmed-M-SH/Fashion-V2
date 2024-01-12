package com.example.fashion.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.fashion.Activity.LoginActivity;
import com.example.fashion.Activity.PaymentActivity;
import com.example.fashion.Activity.UploadActivity;
import com.example.fashion.Domain.UpdateUserProfile;
import com.example.fashion.Domain.UserAuthentication;
import com.example.fashion.Domain.UserProfile;
import com.example.fashion.Helper.DBHelper;
import com.example.fashion.Helper.RetrofitClient;
import com.example.fashion.Helper.TinyDB;
import com.example.fashion.R;
import com.example.fashion.Services.FashionApplication;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {

    EditText uploadName, uploadEmail,uploadPhone;
    ImageView uploadImage;
    Button saveButton;
    private Uri imageUri;
    private Bitmap bitmapImage;
    DBHelper dbHelper;
    private TinyDB tinyDB;
    private static final int IMAGE_PICK_REQUEST = 1;
    private boolean isAuthent;
    private UserProfile userProfile;
    private UserAuthentication auth;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private ProgressBar progressBar11;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_settings, container, false);
        if (!FashionApplication.isIsAuthent()){
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            startActivity(intent);
        }
        initView(view);
        return view;
    }

    private void initView(View view) {
        progressBar11 = view.findViewById(R.id.progressBar11);
        uploadEmail =view. findViewById(R.id.profileEmailTxt);
        uploadImage =view. findViewById(R.id.profileImageView);
        uploadName =view. findViewById(R.id.profileNameTxt);
        uploadPhone =view. findViewById(R.id.profilePhoneTxt);
        saveButton =view. findViewById(R.id.saveButton);
        tinyDB = new TinyDB(requireContext());
        userProfile = tinyDB.getObject("profile",UserProfile.class);
        auth = tinyDB.getObject("userAuth", UserAuthentication.class);

        uploadEmail.setText(userProfile.getEmail());
        uploadName.setText(userProfile.getName());
        uploadPhone.setText(userProfile.getPhoneNumber());
        if (!userProfile.getImage().isEmpty())
        Glide.with(requireContext()).load(userProfile.getImage()).into(uploadImage);
        else {
            Glide.with(requireContext())
                    .load(R.drawable.baseline_person_24)
                    .transform(new GranularRoundedCorners(30,30,0,0))
                    .into(uploadImage);
        }

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDownloadDialog();
//                if (imageUri != null) {
//                    progressBar11.setVisibility(View.VISIBLE);
//                    sendProfileImage();
//                    progressBar11.setVisibility(View.GONE);
//                }
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar11.setVisibility(View.VISIBLE);
                sendUpdateProfileRequest();
                progressBar11.setVisibility(View.GONE);
            }
        });
    }
    private void sendUpdateProfileRequest(){
        boolean isUpdated = false;
        UpdateUserProfile updateUserProfile = new UpdateUserProfile();
        if (!uploadPhone.getText().toString().equals(userProfile.getPhoneNumber())) {
            userProfile.setPhoneNumber(uploadPhone.getText().toString());
            updateUserProfile.setPhoneNumber(uploadPhone.getText().toString());
            isUpdated = true;
        }

        if (!uploadName.getText().toString().equals(userProfile.getName())) {
            userProfile.setName(uploadName.getText().toString());
            updateUserProfile.setName(uploadName.getText().toString());
            isUpdated = true;
        }

        if (!uploadEmail.getText().toString().equals(userProfile.getEmail())) {
            userProfile.setEmail(uploadEmail.getText().toString());
            updateUserProfile.setEmail(uploadEmail.getText().toString());
            isUpdated = true;
        }
        if (imageUri != null){
            sendProfileImage();
            if (!isUpdated)
                return;
            if (isUpdated) {
                Call<UserProfile> call = RetrofitClient.getInstance().getServerDetail().updateProfileUser(auth.getToken(), updateUserProfile);
                call.enqueue(new Callback<UserProfile>() {
                    @Override
                    public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                        Toast.makeText(requireContext(),"Updated successfully",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onFailure(Call<UserProfile> call, Throwable t) {

                    }
                });
            }

        }
    }
    private void sendProfileImage(){


            File imageFile = new File(getRealPathFromURI(imageUri));
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData(
                    "image",
                    imageFile.getName(),
                    RequestBody.create(MediaType.parse("image/*"), imageFile)
            );
            Call<UserProfile> call = RetrofitClient.getInstance().getServerDetail().postProfileImage(auth.getToken(), imagePart);
            call.enqueue(new Callback<UserProfile>() {
                @Override
                public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {

                }

                @Override
                public void onFailure(Call<UserProfile> call, Throwable t) {

                }
            });
        }


    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = requireActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(column_index);
            cursor.close();
            return filePath;
        }
        return null;
    }
    private void showDownloadDialog() {
        // Check if the permission is granted
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // If not, request the permission
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_REQUEST && resultCode == PaymentActivity.RESULT_OK) {
            if (data != null) {
                imageUri = data.getData();
                if (imageUri != null) {
                    Glide.with(this).load(imageUri).into(uploadImage);
                }
            }
        }
    }
}