package com.example.cinema.Common;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cinema.Api.ApiClient;
import com.example.cinema.R;
import com.example.cinema.Request.LoginRequest;
import com.example.cinema.Response.LoginResponse;
import com.example.cinema.Services.LoginService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {
    Button btnSignIn, btnSignUp;
    EditText editEmail, editPassword;
    SharedPreferences sPref;
    final String saveg="key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editEmail.getText().toString())||TextUtils.isEmpty(editPassword.getText().toString())){
                    String message = "Заполните пустые поля!";
                    ShowAlertDialogWindow(message);

                }
                else{
                    if(Patterns.EMAIL_ADDRESS.matcher(editEmail.getText().toString()).matches()){
                        loginUser();
                    }else{
                        String message = "Неправильный email";
                        ShowAlertDialogWindow(message);
                    }
                }
            }
            });
    }

            public void ShowAlertDialogWindow(String text) {
                final AlertDialog alertDialog = new AlertDialog.Builder(SignIn.this).setMessage(text).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.cancel();
                    }
                }).create();
                alertDialog.show();
            }

            public void loginUser(){
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setEmail(editEmail.getText().toString());
                loginRequest.setPassword(editPassword.getText().toString());
                Call<LoginResponse> loginResponseCall = ApiClient.getLogin().loginUser(loginRequest);
                loginResponseCall.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()){
                            LoginResponse loginResponse = response.body();

                            sPref = getSharedPreferences("pref", MODE_PRIVATE);
                            SharedPreferences.Editor ed = sPref.edit();
                            int message = loginResponse.getToken();
                            ed.putString(saveg, String.valueOf(message));
                            ed.apply();
                             Intent intent = new Intent(SignIn.this, MainActivity.class);
                             startActivity(intent);
                             finish();

                        }
                        else{
                            String message = "Неправильные данные";
                            ShowAlertDialogWindow(message);
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        String message = "Неправильные данные";
                        ShowAlertDialogWindow(message);

                    }
                });

            }
}



