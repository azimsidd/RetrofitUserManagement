package com.thecodingshef.usermanagement.NavFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thecodingshef.usermanagement.ModelResponse.LoginResponse;
import com.thecodingshef.usermanagement.ModelResponse.UpdatePassResponse;
import com.thecodingshef.usermanagement.R;
import com.thecodingshef.usermanagement.RetrofitClient;
import com.thecodingshef.usermanagement.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment implements View.OnClickListener{


    EditText etuserName,etuserEmail,currentPass, newPass;
    Button updateUserAccountBtn,updateuserPasswordBtn;
    SharedPrefManager sharedPrefManager;
    int userId;
    String userEmailId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_profile, container, false);

        //for update Account
        etuserName=view.findViewById(R.id.userName);
        etuserEmail=view.findViewById(R.id.userEmail);
        updateUserAccountBtn=view.findViewById(R.id.btnUpdateAccount);


        //for update password
        currentPass=view.findViewById(R.id.currentPass);
        newPass=view.findViewById(R.id.newPassword);
        updateuserPasswordBtn=view.findViewById(R.id.btnUpdatePassword);


        sharedPrefManager=new SharedPrefManager(getActivity());
         userId=sharedPrefManager.getUser().getId();
         userEmailId=sharedPrefManager.getUser().getEmail();


        updateUserAccountBtn.setOnClickListener(this);
        updateuserPasswordBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnUpdateAccount:
                updateUserAccount();
                break;

            case R.id.btnUpdatePassword:
                updatePassword();
                break;
        }

    }

    private void updatePassword() {

        String userCurrentPassword=currentPass.getText().toString().trim();
        String userNewPassword=newPass.getText().toString().trim();


        if(userCurrentPassword.isEmpty()){
            currentPass.setError("Enter current password");
            currentPass.requestFocus();
            return;
        }

        if(userCurrentPassword.length()<8){
            currentPass.setError("Enter 8 digit password");
            currentPass.requestFocus();
            return;
        }


        if(userNewPassword.isEmpty()){
            newPass.setError("Enter current password");
            newPass.requestFocus();
            return;
        }

        if(userNewPassword.length()<8){
            newPass.setError("Enter 8 digit password");
            newPass.requestFocus();
            return;
        }


        Call<UpdatePassResponse> call=RetrofitClient
                .getInstance()
                .getApi()
                .updateUserPass(userEmailId,userCurrentPassword,userNewPassword);

        call.enqueue(new Callback<UpdatePassResponse>() {
            @Override
            public void onResponse(Call<UpdatePassResponse> call, Response<UpdatePassResponse> response) {

                UpdatePassResponse passwordResponse=response.body();


                if(response.isSuccessful()){


                    if(passwordResponse.getError().equals("200")){

                        Toast.makeText(getActivity(), passwordResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdatePassResponse> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void updateUserAccount() {

        String username=etuserName.getText().toString().trim();
        String email=etuserEmail.getText().toString().trim();


        if(username.isEmpty()){
            etuserName.setError("Please enter user name");
            etuserName.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etuserEmail.requestFocus();
            etuserEmail.setError("Please enter correct email");
            return;
        }

        Call<LoginResponse> call= RetrofitClient
                .getInstance()
                .getApi()
                .updateUserAccount(userId,username,email);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {


                LoginResponse updateReponse=response.body();
                if(response.isSuccessful()){

                    if(updateReponse.getError().equals("200")){

                        sharedPrefManager.saveUser(updateReponse.getUser());
                        Toast.makeText(getActivity(), updateReponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getActivity(), updateReponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
                else{
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}