package com.example.myapplicationnote;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    private EditText etName, etEmail, etPassword, etConfirmPassword;  // Ajout du champ de confirmation du mot de passe
    private Button btnSignup;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialisation des vues
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);  // Initialisation du champ de confirmation du mot de passe
        btnSignup = findViewById(R.id.btn_signup);
        db = new DatabaseHelper(this);

        // Logique du bouton d'inscription
        btnSignup.setOnClickListener(view -> {
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            String confirmPassword = etConfirmPassword.getText().toString();  // Récupère la confirmation du mot de passe

            // Vérification que tous les champs sont remplis
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Vérification que le mot de passe et la confirmation correspondent
            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Vérification de la validité de l'email (optionnel)
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                return;
            }

            // Enregistrement de l'utilisateur dans la base de données
            if (db.addUser(name, email, password)) {
                Toast.makeText(this, "Sign-Up Successful!", Toast.LENGTH_SHORT).show();
                finish();  // Ferme l'activité d'inscription et revient à la page de login
            } else {
                Toast.makeText(this, "Sign-Up Failed! User may already exist.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();  // Vérifie si l'email est valide
    }
}