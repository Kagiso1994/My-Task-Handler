package com.kagiso.mytaskhandler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText reg_email;
    private EditText reg_pass;
    private Button btnRegister;

    private TextView login_text;


    FirebaseAuth mAuth;
    private ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        mDialog= new ProgressDialog(this);

        reg_email = findViewById(R.id.email_register);
        reg_pass = findViewById(R.id.password_register);
        btnRegister = findViewById(R.id.register_btn);
        login_text = findViewById(R.id.registerTxt);

        login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = reg_email.getText().toString();
                String password = reg_pass.getText().toString();

                if(TextUtils.isEmpty(email)){
                    reg_email.setError("Required Field...");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    reg_pass.setError("Required Field...");
                    return;
                }

                mDialog.setMessage("processing..");
                mDialog.show();
                Log.println(Log.DEBUG,"Email",email);
                Log.println(Log.DEBUG,"Password",password);

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Successful",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            mDialog.dismiss();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Problem: " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                    }
                });



            }
        });
    }

}
