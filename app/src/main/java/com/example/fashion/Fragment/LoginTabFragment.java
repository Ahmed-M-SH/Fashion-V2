package com.example.fashion.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.fashion.Activity.MainActivity;
import com.example.fashion.Domain.UserAuthentication;
import com.example.fashion.Helper.RetrofitClient;
import com.example.fashion.Helper.TinyDB;
import com.example.fashion.Helper.UserManagement;
import com.example.fashion.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginTabFragment extends Fragment {

    private EditText editTextEmail,editTextPassword;
    private TinyDB tinyDB;
    private Button login_button;
    private UserManagement userManagement;
    private UserAuthentication userResult;

    public LoginTabFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_login_tab, container, false);
        initView(view);
        return view;
    }


    private void initView(View view) {
        editTextEmail = view.findViewById(R.id.login_email);
        editTextPassword = view.findViewById(R.id.login_password);
        login_button =view.findViewById(R.id.login_button);
        tinyDB = new TinyDB(getActivity().getApplicationContext());

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextEmail.getText().toString().isEmpty()) {
                    editTextEmail.setError("الرجاء ادخال الايميل للمتابعة");
                    return;
                }
                if (editTextPassword.getText().toString().isEmpty()) {
                    editTextPassword.setError("الرجاء ادخال كلمة السر للمتابعة");
                    return;
                }
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                UserAuthentication userAuth = new UserAuthentication(email, password);
                Call<UserAuthentication> call = RetrofitClient.getInstance().getServerDetail().getUserAuthentication(userAuth);
                call.enqueue(new Callback<UserAuthentication>() {
                    @Override
                    public void onResponse(Call<UserAuthentication> call, Response<UserAuthentication> response) {

                        userResult = response.body();
                        if (response.isSuccessful()){
                            tinyDB.putObject("userAuth", userResult);
                            tinyDB.putBoolean("isAuthent",true);
                            userManagement.sendRequestProfile(userResult,requireContext());
//                                UserProfile profile = UserManagment.getUserProfile();
                            Intent intent = new Intent(requireContext(), MainActivity.class);
                            startActivity(intent);
                        } else if (response.code() == 400) {
                            Toast.makeText(requireContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserAuthentication> call, Throwable t) {
                        Toast.makeText(requireContext(), "Network Error Check your connection", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}