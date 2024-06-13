package com.henrryd.appfoody2;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.henrryd.appfoody2.Controller.DangKyController;
import com.henrryd.appfoody2.Model.ThanhVienModel;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity2 extends AppCompatActivity implements View.OnClickListener {

    private Button btnDangKy;
    private EditText edEmailDK, edPasswordDK, edConfirmPassword, edName;
    private FirebaseFirestore firestore;
    private ProgressDialog progressDialog;
    private DangKyController dangKyController;
    private TextView btnSignIn;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.progressDialog));

        btnDangKy = findViewById(R.id.btnDangKy);
        edEmailDK = findViewById(R.id.edEmailDK);
        edPasswordDK = findViewById(R.id.edPasswordDK);
        edConfirmPassword = findViewById(R.id.edConfirmPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        edName = findViewById(R.id.edName);
        dangKyController = new DangKyController();

        btnDangKy.setOnClickListener(this);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(RegisterActivity2.this, LoginActivity.class);
                startActivity(it);
                finish();
            }
        });
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
            Map<String, String> userMap = new HashMap<>();
            userMap.put("name",edName.getText().toString());
            userMap.put("username", edEmailDK.getText().toString());
            userMap.put("pass", edPasswordDK.getText().toString());
            userMap.put("avatar", "ss_" + (int) (Math.random() * 10 - 1));

            firestore.collection("user").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    String documentId = documentReference.getId();

                    ThanhVienModel thanhVienModel = new ThanhVienModel();
                    thanhVienModel.setHoten(email);
                    thanhVienModel.setHinhanh("user.png");

                    dangKyController.ThemThongTinThanhVienController(thanhVienModel, documentId);

                    Toast.makeText(RegisterActivity2.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity2.this, LoginActivity.class);
                    intent.putExtra("acc", edEmailDK.getText().toString());
                    intent.putExtra("pass", edPasswordDK.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterActivity2.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }
}
