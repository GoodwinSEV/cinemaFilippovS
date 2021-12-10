package com.example.cinema.Common;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cinema.Api.ApiClient;
import com.example.cinema.R;
import com.example.cinema.Request.RegisterRequest;
import com.example.cinema.Response.RegisterResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {
    Button btnSignUp1, btnSignIn1;
    EditText editEmail1, editPassword1, editRePassword, editFirstname, editLastname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignUp1 = findViewById(R.id.btnSignUp1);
        btnSignIn1 = findViewById(R.id.btnSignIn1);
        editEmail1 = findViewById(R.id.editEmail1);
        editPassword1 = findViewById(R.id.editPassword1);
        editRePassword = findViewById(R.id.editEmail1);
        editFirstname = findViewById(R.id.editFirstname);
        editLastname = findViewById(R.id.editLastname);

        btnSignIn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
            }
        });
        btnSignUp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editEmail1.getText().toString())||
                        TextUtils.isEmpty(editPassword1.getText().toString())||
                        TextUtils.isEmpty(editRePassword.getText().toString())||
                        TextUtils.isEmpty(editFirstname.getText().toString())||
                        TextUtils.isEmpty(editLastname.getText().toString())){
                    {

                    ShowAlertDialogWindow("Заполните пустые поля!");
                    }

                }
               /* else if (!editPassword1.getText().toString().equals(editRePassword.getText().toString()))
                {

                    ShowAlertDialogWindow("Пароли не совпадают");
                    }
                else if (!emailValid(editEmail1.getText().toString())){
                    ShowAlertDialogWindow("Email не полходит шаблону"); } */

                else{
                    registerUser();
                }
            }
        });
    }

   /* private boolean emailValid(String email)
    {
        Pattern emailPattern = Pattern.compile("a-z.+@[a=z]+\\.[a-z]+");
        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.matches();
    } */

    public void ShowAlertDialogWindow(String text) {
        final AlertDialog alertDialog = new AlertDialog.Builder(SignUp.this).setMessage(text).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.cancel();
            }
        }).create();
        alertDialog.show();
    }

    public void registerUser(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(editEmail1.getText().toString());
        registerRequest.setPassword(editPassword1.getText().toString());
        Call<RegisterResponse> registerResponseCall = ApiClient.getRegister().registerUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()){
                   String message = "Ok";
                    Toast.makeText(SignUp.this, message, Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    String message = "Ошибка";
                    ShowAlertDialogWindow(message);
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                String message = "Регистрация не прошла";
                ShowAlertDialogWindow(message);


            }
        });

    }
}



