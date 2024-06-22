package com.henrryd.appfoody2.ui.Thongtintaikhoan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.henrryd.appfoody2.Controller.Interfaces.DialogListener;
import com.henrryd.appfoody2.Dialog.AlertDialogg;
import com.henrryd.appfoody2.Dialog.ChangePasswordDialog_second;
import com.henrryd.appfoody2.Dialog.forgotPasswordDialog;
import com.henrryd.appfoody2.R;
import com.henrryd.appfoody2.RegisterActivity2;
import com.henrryd.appfoody2.other.DataLocalManager;
import com.henrryd.appfoody2.other.MyApplication;
import com.henrryd.appfoody2.other.custom_Picture;


public class Thongtintaikhoan extends Fragment {

    TextView name, username, password;

    ImageView imgavt;

    Button btn_doimk, btn_quenmk, btn_dangxuat;

    Thongtintaikhoan current;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thongtintaikhoan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.hotenuser);
        username = view.findViewById(R.id.emailuser);
        password = view.findViewById(R.id.matkhauuser);
        btn_doimk = view.findViewById(R.id.btnDoimatkhau);
        btn_quenmk = view.findViewById(R.id.btnquenmk);
        btn_dangxuat = view.findViewById(R.id.btndangxuat);
        imgavt = view.findViewById(R.id.imguser);
        loaddataUI();
        btn_doimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordDialog_second changePasswordDialogSecond = new ChangePasswordDialog_second(getContext(), MyApplication.User.username);
                changePasswordDialogSecond.show();
                loaddataUI();
            }
        });

        btn_quenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPasswordDialog forgotPasswordDialog = new forgotPasswordDialog(getContext()
                        , ""
                        , MyApplication.User.username
                        , com.henrryd.appfoody2.Dialog.forgotPasswordDialog.TYPE_EMAIL);
                forgotPasswordDialog.show();
                loaddataUI();
            }
        });

        btn_dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogg alertDialogg = new AlertDialogg(getContext()
                        , "Thông báo"
                        , "Bạn có chắc chắn muốn đăng xuất?"
                        , R.drawable.thantai);
                alertDialogg.setDialogListener(new DialogListener() {
                    @Override
                    public void dialogPositive() {
                        signout();
                    }
                });
                alertDialogg.show();
            }
        });
    }

    public void loaddataUI() {
        name.setText(MyApplication.User.name);
        username.setText(MyApplication.User.username);
        password.setText(MyApplication.User.Pass);
        imgavt.setImageAlpha(R.mipmap.image_avt);
    }

    private void signout() {
        DataLocalManager.remove_user();
        Intent it = new Intent(getContext(), RegisterActivity2.class);
        MyApplication.User = null;
        startActivity(it);
    }

}