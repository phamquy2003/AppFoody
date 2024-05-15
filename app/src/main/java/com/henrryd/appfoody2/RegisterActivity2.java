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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.AbstractCollection;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity2 extends AppCompatActivity implements View.OnClickListener {

    Button btnDangKy;
    EditText edEmailDK, edPasswordDK, edConfirmPassword;
    FirebaseFirestore firestore;
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
        firestore = FirebaseFirestore.getInstance();
        CollectionReference reference = firestore.collection("user");


        // Khởi tạo ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.progressDialog));

//        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firestore;
        btnDangKy = findViewById(R.id.btnDangKy);
        edEmailDK = findViewById(R.id.edEmailDK);
        edPasswordDK = findViewById(R.id.edPasswordDK);
        edConfirmPassword = findViewById(R.id.edConfirmPassword);

        btnDangKy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        progressDialog.show();
        String email = edEmailDK.getText().toString();
        String password = edPasswordDK.getText().toString();
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
            Map<String, String> mymap = new HashMap<String , String>();
//                mymap.put("name",edName.getText().toString());
            mymap.put("username", edEmailDK.getText().toString());
            mymap.put("pass", edPasswordDK.getText().toString());
            mymap.put("avatar", "ss_"+(int)(Math.random()*10-1));
            firestore.collection("user").add(mymap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(RegisterActivity2.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(RegisterActivity2.this, LoginActivity.class);
                    it.putExtra("acc",edEmailDK.getText().toString());
                    it.putExtra("pass",edPasswordDK.getText().toString());
                    startActivity(it);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterActivity2.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
