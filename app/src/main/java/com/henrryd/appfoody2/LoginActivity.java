package com.henrryd.appfoody2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.facebook.FacebookSdk;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.henrryd.appfoody2.Dialog.forgotPasswordDialog;
import com.henrryd.appfoody2.other.MyApplication;
import com.henrryd.appfoody2.other.user;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {

    EditText edEmailDN, edPasswordDN;
    Button btnDangNhap;
    TextView txtDangKy, txtQuenMatKhau;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edEmailDN = findViewById(R.id.edEmailDK);
        edPasswordDN = findViewById(R.id.edPasswordDK);
        btnDangNhap = findViewById(R.id.btnDangKy);
        txtDangKy = findViewById(R.id.txtDangKy);
        txtQuenMatKhau = findViewById(R.id.txtQuenMatKhau);

        sharedPreferences = getSharedPreferences("luudangnhap", MODE_PRIVATE);

        Intent it = getIntent();
        Bundle bundle = it.getExtras();

        if (bundle != null) {
            edEmailDN.setText(bundle.getString("acc"));
            edPasswordDN.setText(bundle.getString("pass"));
        }

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(edEmailDN.getText().toString(), edPasswordDN.getText().toString());
            }
        });

        txtDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        txtQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPasswordDialog dialog = new forgotPasswordDialog(LoginActivity.this,
                        "",
                        edEmailDN.getText().toString(),
                        forgotPasswordDialog.TYPE_EMAIL);
                dialog.show();
            }
        });
    }

    private void register() {
        Intent i = new Intent(LoginActivity.this, RegisterActivity2.class);
        startActivity(i);
    }

    private void login(String username, String pass) {
        // Tìm kiếm trong collection "user"
        db.collection("user")
                .whereEqualTo("username", username)
                .whereEqualTo("pass", pass)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && task.getResult().size() == 1) {
                        handleLoginSuccess(task.getResult().getDocuments().get(0));
                    } else {
                        // Nếu không tìm thấy trong "user", tiếp tục tìm trong "admin"
                        db.collection("admin")
                                .whereEqualTo("username", username)
                                .whereEqualTo("password", pass)
                                .get().addOnCompleteListener(adminTask -> {
                                    if (adminTask.isSuccessful() && adminTask.getResult() != null && adminTask.getResult().size() == 1) {
                                        handleLoginSuccess(adminTask.getResult().getDocuments().get(0));
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
    }

    private void handleLoginSuccess(DocumentSnapshot document) {
        String documentID = document.getId();
        String role = document.getString("role");

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mauser", documentID);
        editor.apply();

        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

        user tmpuser = new user(
                document.getString("name"),
                document.getString("username"),
                document.getString("pass"),
                document.getString("avatar"),
                role); // Thêm role vào khởi tạo user
        MyApplication.User = tmpuser;

        if ("admin".equals(role)) {
            Intent it = new Intent(LoginActivity.this, ThemQuanAnActivity.class);
            it.putExtra("dulieu", (Serializable) tmpuser);
            startActivity(it);
        } else {
            Intent it = new Intent(LoginActivity.this, MainActivity.class);
            it.putExtra("dulieu", (Serializable) tmpuser);
            startActivity(it);
        }
        finish();
    }


}
