package com.example.myapplicationnote;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainLogin extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameInput = username.getText().toString();
                String passwordInput = password.getText().toString();

                if (usernameInput.equals("user") && passwordInput.equals("1234")) {
                    Toast.makeText(MainLogin.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                    // Lancer MainActivity
                    Intent intent = new Intent(MainLogin.this, MainActivity.class);
                    startActivity(intent);

                    // Facultatif : Fermer l'activité de connexion
                    finish();
                } else {
                    Toast.makeText(MainLogin.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
