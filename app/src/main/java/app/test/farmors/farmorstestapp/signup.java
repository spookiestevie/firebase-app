package app.test.farmors.farmorstestapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class signup extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBar;
    EditText name_input, email_input, password_input, passwordconf_input;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name_input = (EditText) findViewById(R.id.name_input);
        email_input = (EditText) findViewById(R.id.email_input);
        password_input = (EditText) findViewById(R.id.password_input);
        passwordconf_input = (EditText) findViewById(R.id.passwordconf_input);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.button_signup).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);
    }

    private void registerUser(){
        String username = name_input.getText().toString().trim();
        String email = email_input.getText().toString().trim();
        String password = password_input.getText().toString().trim();
        String passwordconf = passwordconf_input.getText().toString().trim();

        if(email.isEmpty()){
            email_input.setError("Email is required");
            email_input.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_input.setError("Please enter a valid Email");
            email_input.requestFocus();
            return;
        }
        if(password.isEmpty()){
            password_input.setError("password is required");
            password_input.requestFocus();
            return;
        }
        if (password.length() < 6){
            password_input.setError("Minimum password length is 6");
            password_input.requestFocus();
            return;
        }
        if (! password.equals(passwordconf)){
            passwordconf_input.setError("Passwords are not the same");
            passwordconf_input.requestFocus();
            return;
        }

        if(username.isEmpty()){
            name_input.setError("Full name is required");
            name_input.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    Intent intent = new Intent(signup.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Registration Successful!", Toast.LENGTH_SHORT).show();
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Some Error Occurred", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_signup:
                registerUser();
                break;
            case R.id.textViewLogin:
                startActivity(new Intent(this, signin.class));
                break;

        }
    }
}

