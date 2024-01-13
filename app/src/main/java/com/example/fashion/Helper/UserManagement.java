package com.example.fashion.Helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.fashion.Domain.ErrorResponse;
import com.example.fashion.Domain.UserAuthentication;
import com.example.fashion.Domain.UserProfile;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public  class UserManagement {
    private static TinyDB tinyDB ;
    private static   Context context ;
    private static UserAuthentication  userResult;
    public UserManagement(Context context) {
        this.context =context;
        tinyDB = new TinyDB(context);
    }
    public static void sendRequestProfile(UserAuthentication authentication,Context context) {
        tinyDB = new TinyDB(context);
        UserAuthentication auth = tinyDB.getObject("userAuth", UserAuthentication.class);
        Call<List<UserProfile>> callProfile = RetrofitClient.getInstance().getServerDetail().getUserProfile(authentication.getToken());
        callProfile.enqueue(new Callback<List<UserProfile>>() {
            @Override
            public void onResponse(Call<List<UserProfile>> call, Response<List<UserProfile>> response) {
                List<UserProfile> user = response.body();

                if (response.isSuccessful()) {
                    tinyDB.putObject("profile", user.stream().findFirst());
                }
//                else
//                    Toast.makeText(context,"Error Code:"+response.code(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<UserProfile>> call, Throwable t) {
                Toast.makeText(context,"Error sending request To Get Profile and error code:"+t.getMessage(), Toast.LENGTH_LONG).show();

            }

        });
    }
    public static void sendRequestProfile(UserAuthentication authentication) {
        UserAuthentication auth = tinyDB.getObject("userAuth", UserAuthentication.class);
        Call<List<UserProfile>> callProfile = RetrofitClient.getInstance().getServerDetail().getUserProfile(authentication.getToken());
        callProfile.enqueue(new Callback<List<UserProfile>>() {
            @Override
            public void onResponse(Call<List<UserProfile>> call, Response<List<UserProfile>> response) {
                List<UserProfile> user = response.body();

                if (response.isSuccessful()) {
                    tinyDB.putObject("profile", user.get(0));
                }
//                else
                Toast.makeText(context,"Error Code:"+response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<UserProfile>> call, Throwable t) {
                Toast.makeText(context,"Error sending request To Get Profile and error code:"+t.getMessage(), Toast.LENGTH_LONG).show();

            }

        });

    }
        public static void registerRequest(UserProfile userProfile) {
            Call<UserProfile> user = RetrofitClient.getInstance().getServerDetail().getUserRegistration(userProfile);
            user.enqueue(new Callback<UserProfile>() {
                @Override
                public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                    UserProfile userProfile = response.body();
                    if (userProfile != null) {
                        tinyDB.putBoolean("isAuthent", true);
                        tinyDB.putObject("userAuth", userProfile.getUserAuth());
                        UserManagement.sendRequestProfile(userProfile.getUserAuth());
                    } else if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            // Parse the errorBody using Gson and your ErrorResponse class
                            ErrorResponse errorResponse = new Gson().fromJson(errorBody, ErrorResponse.class);

                            if (errorResponse != null && errorResponse.getEmailErrors() != null && !errorResponse.getEmailErrors().isEmpty()) {
                                // Handle email-related errors
                                String errorMessage = errorResponse.getEmailErrors().toString();
                                        Toast.makeText(context, "Email error: " + errorMessage, Toast.LENGTH_SHORT).show();
                            } else {
                                // Handle other types of errors
                                Toast.makeText(context, "Unknown error occurred", Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (response.isSuccessful()) {
                    }
                            Toast.makeText(context,"error:"+response.toString(), Toast.LENGTH_SHORT).show();
                    Log.i("ErrorResponse", response.toString());
                }
                @Override
                public void onFailure(Call<UserProfile> call, Throwable t) {

                }
            });


        }
        public static UserAuthentication loginRequest(String email,String password){

            UserAuthentication userAuth = new UserAuthentication(email, password);
            Call<UserAuthentication> call = RetrofitClient.getInstance().getServerDetail().getUserAuthentication(userAuth);
            call.enqueue(new Callback<UserAuthentication>() {
                @Override
                public void onResponse(Call<UserAuthentication> call, Response<UserAuthentication> response) {

                     userResult = response.body();
                    if (response.isSuccessful()){
                        tinyDB.putObject("userAuth", userResult);
                        tinyDB.putBoolean("isAuthent",true);
                        UserManagement.sendRequestProfile(userResult);
//                                UserProfile profile = UserManagment.getUserProfile();
                    } else if (response.code() == 400) {
                        Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserAuthentication> call, Throwable t) {
                    Toast.makeText(context, "Network Error Check your connection", Toast.LENGTH_SHORT).show();
                }
            });
            return userResult;
        }
}
