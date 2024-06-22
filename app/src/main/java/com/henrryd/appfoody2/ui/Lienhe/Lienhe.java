package com.henrryd.appfoody2.ui.Lienhe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.henrryd.appfoody2.R;


public class Lienhe extends Fragment {

    CardView cardViewmail;
    CardView cardViewcall;
    CardView cardViewfacebook;
    CardView cardViewzalo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lienhe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         cardViewmail       = view.findViewById(R.id.cardmail);
         cardViewcall       = view.findViewById(R.id.cardcall);
         cardViewfacebook   = view.findViewById(R.id.cardfacebook);
         cardViewzalo       = view.findViewById(R.id.cardzalo);

         cardViewmail.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(Intent.ACTION_SEND);
                 intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"anhquyk797@gmail.com"});
                 intent.putExtra(Intent.EXTRA_SUBJECT,"Support APP GTQA");
                 intent.setType("message/rfc822");
                 startActivity(Intent.createChooser(intent,"Gửi email qua..."));
             }
         });

        cardViewcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0567284986"));
                startActivity(intent);
            }
        });
        cardViewzalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:0567284986"));
                startActivity(intent);
            }
        });
        cardViewfacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String linkfb = "https://www.facebook.com/phamquy03";
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(linkfb));
                startActivity(intent);
            }
        });

    }
}