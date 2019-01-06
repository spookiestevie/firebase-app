package app.test.farmors.farmorstestapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signin extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    EditText email_input, password_input;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();

        email_input = (EditText) findViewById(R.id.email_input);
        password_input = (EditText) findViewById(R.id.password_input);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        findViewById(R.id.textViewSignup).setOnClickListener(this);
        findViewById(R.id.button_signin).setOnClickListener(this);

    }

    private void userLogin(){

        String email = email_input.getText().toString().trim();
        String password = password_input.getText().toString().trim();


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

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    Intent intent = new Intent(signin.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewSignup:
                startActivity(new Intent(this, signup.class));
                break;
            case R.id.button_signin:
                userLogin();
                break;
        }
    }
}