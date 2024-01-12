package com.example.fashion.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.fashion.Activity.MainActivity;
import com.example.fashion.Domain.ErrorResponse;
import com.example.fashion.Domain.UserProfile;
import com.example.fashion.Helper.RetrofitClient;
import com.example.fashion.Helper.TinyDB;
import com.example.fashion.Helper.UserManagement;
import com.example.fashion.R;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupTabFragment extends Fragment {

    EditText signup_email,signup_password,signup_confirm, signup_name,signup_phoneNumber;
    Button signup_button;
    TinyDB tinyDB;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignupTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupTabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupTabFragment newInstance(String param1, String param2) {
        SignupTabFragment fragment = new SignupTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_signup_tab, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

//        EditText signup_email,signup_password,signup_confirm, signup_name,signup_phoneNumber;
//        Button signup_button;
//        TinyDB tinyDB;

        signup_email =  view.findViewById(R.id.signup_email);
        signup_name = view.findViewById(R.id.signup_name);
        signup_password = view.findViewById(R.id.signup_password);
        signup_confirm = view.findViewById(R.id.signup_confirm);
        signup_phoneNumber = view.findViewById(R.id.signup_confirm);
        signup_button = view.findViewById(R.id.signup_button);
        tinyDB = new TinyDB(getActivity().getApplicationContext());

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = signup_email.getText().toString();
                String password = signup_password.getText().toString();
                String password1 = signup_confirm.getText().toString();
                String fullName = signup_name.getText().toString();
                String phoneNumber = signup_phoneNumber.getText().toString();
                if (signup_password.getText().toString().isEmpty()){
                    signup_password.setError("هذا الحقل مطلوب");
                    return;
                }
                if (signup_confirm.getText().toString().isEmpty()){
                    signup_confirm.setError("هذا الحقل مطلوب");
                    return;
                }
                if (!password.equals(password1) && !password1.equals(password)){
                    signup_confirm.setError("كلمة السر لاتتطابق مع كلمة السر التي وضعتها الرجاء التاكد.");
                    return;
                }
                if (signup_name.getText().toString().isEmpty()){
                    signup_name.setError("هذا الحقل مطلوب");
                    return;
                }
                if (signup_email.getText().toString().isEmpty()){
                    signup_email.setError("هذا الحقل مطلوب");
                    return;
                }
                if (signup_phoneNumber.getText().toString().isEmpty()){
                    signup_phoneNumber.setError("هذا الحقل مطلوب");
                    return;
                }

                UserProfile profile =new UserProfile();
                profile.setEmail(email);
                profile.setPassword(password);
                profile.setPhoneNumber(phoneNumber);
                profile.setName(fullName);
                Call<UserProfile> user = RetrofitClient.getInstance().getServerDetail().getUserRegistration(profile);
                user.enqueue(new Callback<UserProfile>() {
                    @Override
                    public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                        UserProfile userProfile = response.body();
                        if (userProfile != null){
//                                Toast.makeText(getApplicationContext(), "User profile Created", Toast.LENGTH_SHORT).show();
//                                tinyDB.putObject("profile", userProfile);
                            tinyDB.putBoolean("isAuthent",true);
                            tinyDB.putObject("userAuth",userProfile.getUserAuth());
                            tinyDB.putObject("profile", userProfile);
                            UserManagement.sendRequestProfile(userProfile.getUserAuth(),requireContext());
                            Intent intent = new Intent(requireContext(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        } else if (response.errorBody() !=null) {
                            try {
                                String errorBody = response.errorBody().string();
                                // Parse the errorBody using Gson and your ErrorResponse class
                                ErrorResponse errorResponse = new Gson().fromJson(errorBody, ErrorResponse.class);

                                if (errorResponse != null && errorResponse.getEmailErrors() != null && !errorResponse.getEmailErrors().isEmpty()) {
                                    // Handle email-related errors
                                    String errorMessage = errorResponse.getEmailErrors().toString();
                                    signup_email.setError(errorMessage);

//                                        Toast.makeText(getApplicationContext(), "Email error: " + errorMessage, Toast.LENGTH_SHORT).show();
                                } else {
                                    // Handle other types of errors
                                    Toast.makeText(requireContext(), "Unknown error occurred", Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if(response.isSuccessful()) {
                        }
//                            Toast.makeText(getApplication(),"error:"+response.toString(), Toast.LENGTH_SHORT).show();
                        Log.i("ErrorResponse",response.toString());
                    }

                    @Override
                    public void onFailure(Call<UserProfile> call, Throwable t) {
                    }
                });
            }
        });



    }
}