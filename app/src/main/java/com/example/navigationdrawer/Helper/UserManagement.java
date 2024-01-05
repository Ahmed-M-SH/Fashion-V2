package com.example.navigationdrawer.Helper;

import com.example.navigationdrawer.Domain.UserAuthentication;
import com.example.navigationdrawer.Domain.UserProfile;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public  class UserManagement {
    private static TinyDB tinyDB;
    public static void sendRequestProfile(UserAuthentication authentication) {
//        UserAuthentication auth = tinyDB.getObject("userAuth", UserAuthentication.class);
        Call<List<UserProfile>> callProfile = RetrofitClient.getInstance().getServerDetail().getUserProfile(authentication.getToken());
        callProfile.enqueue(new Callback<List<UserProfile>>() {
            @Override
            public void onResponse(Call<List<UserProfile>> call, Response<List<UserProfile>> response) {
                List<UserProfile> user = response.body();

                if (response.isSuccessful()) {
                    tinyDB.putObject("profile", user.get(0));
                }
//                else
//                    Toast.makeText(getApplicationContext(),"Error Code:"+response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<UserProfile>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),"Error sending request To Get Profile and error code:"+t.getMessage(), Toast.LENGTH_LONG).show();

            }

        });
    }
}
