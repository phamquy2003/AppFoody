package com.henrryd.appfoody2;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity2 extends AppCompatActivity implements View.OnClickListener {

    Button btnDangKy;
    EditText edEmailDK, edPassworDK, edConfirmPassword;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;


    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.progressDialog));

        firebaseAuth = FirebaseAuth.getInstance();

        btnDangKy = findViewById(R.id.btnDangKy);
        edEmailDK = findViewById(R.id.edEmailDK);
        edPassworDK = findViewById(R.id.edPasswordDK);
        edConfirmPassword = findViewById(R.id.edConfirmPassword);

        btnDangKy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        progressDialog.show();
        String email = edEmailDK.getText().toString();
        String password = edPassworDK.getText().toString();
        String confirmPassword = edConfirmPassword.getText().toString();

        if (email.trim().length() == 0 || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Vui lòng nhập email hoặc định dạng email chưa đúng!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else if (password.trim().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else if (confirmPassword.trim().length() == 0) {
            Toast.makeText(this, "Bạn chưa nhập lại mật khẩu!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else if (!confirmPassword.equals(password)) {
            Toast.makeText(this, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity2.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent it = new Intent(RegisterActivity2.this, LoginActivity.class);
                        startActivity(it);
                    } else {
                        Toast.makeText(RegisterActivity2.this, "Tạo tài khoản thất bại", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }
    }
}
