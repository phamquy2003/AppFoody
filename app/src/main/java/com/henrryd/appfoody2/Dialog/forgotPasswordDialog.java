package com.henrryd.appfoody2.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;
import com.henrryd.appfoody2.R;
import com.henrryd.appfoody2.other.MAILSender;

import java.util.Random;

public class forgotPasswordDialog {

    public static final int TYPE_EMAIL = 0;
    public static final int TYPE_OTP = 1;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Context context;
    String value;
    String email;
    int type;

    public forgotPasswordDialog(Context context, String value,String email, int type)
    {
        this.context = context;
        this.value = value;
        this.type = type;
        this.email = email;
    }

    public void show(){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_forgot_password);
        Window window = dialog.getWindow();
        if (window == null) return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttr = window.getAttributes();
        windowAttr.gravity = Gravity.CENTER;
        window.setAttributes(windowAttr);
        dialog.setCancelable(true);

        Button btn_thoat, btn_continue;
        EditText ed_email_otp;
        TextView tv_title;

        btn_thoat = dialog.findViewById(R.id.btn_thoat);
        btn_continue = dialog.findViewById(R.id.btn_continue);
        tv_title = dialog.findViewById(R.id.tv_title);
        ed_email_otp = dialog.findViewById(R.id.ed_email_OTP);
        if (type == TYPE_EMAIL)
        {
            tv_title.setText("Vui lòng nhập đúng email");
            ed_email_otp.setHint("Vui lòng nhập email");
        }
        else
        {
            tv_title.setText("Xác thực OTP");
            ed_email_otp.setHint("Kiểm tra thư email của bạn.");
        }


        if (type == TYPE_EMAIL)
        {
            ed_email_otp.setText(email);
        }
        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            private String generateOTP() {
                // Tạo mã OTP ngẫu nhiên gồm 5 chữ số
                Random random = new Random();
                int otp = random.nextInt(89999) + 10000;
                return String.valueOf(otp);
            }
            @Override
            public void onClick(View v) {
                if (type == TYPE_EMAIL)
                {
                    db.collection("user")
                            .whereEqualTo("username",ed_email_otp.getText().toString())
                            .get().addOnCompleteListener(task -> {
                                if (task.isSuccessful() && task.getResult() != null && task.getResult().size() == 1)
                                {
                                    String tmpOTP = generateOTP();
                                    forgotPasswordDialog dialogOTP = new forgotPasswordDialog(context,tmpOTP, ed_email_otp.getText().toString(), TYPE_OTP);
                                    MAILSender mailSender = new MAILSender(ed_email_otp.getText().toString(),"[Ứng dụng Foody] Mã xác thực OTP", "Xin chào, Bạn vừa cập nhật lại mật khẩu trên ứng dụng Foody, đây là mã OTP xác thực của bạn " + tmpOTP + ". Lưu ý: TUYỆT ĐỐI không cung cấp OTP cho bất cứ ai về bất cứ lý do gì!");
                                    mailSender.start();
                                    Toast.makeText(context, "Vui lòng kiểm tra email.", Toast.LENGTH_SHORT).show();
                                    dialogOTP.show();
                                    dialog.dismiss();
                                }
                                else
                                    ed_email_otp.setError("Email sai hoặc không tồn tại");
                            });
                } else if (type == TYPE_OTP)
                {
                    if (!ed_email_otp.getText().toString().equals(value))
                    {
                        ed_email_otp.setError("OTP không chính xác");
                    } else
                    {
                        ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog(context,email);
                        changePasswordDialog.show();
                        dialog.dismiss();
                    }
                }
            }
        });
        dialog.show();
    }
}