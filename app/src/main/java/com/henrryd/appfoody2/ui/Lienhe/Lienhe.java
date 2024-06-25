package com.henrryd.appfoody2.ui.Lienhe;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lienhe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardViewmail = view.findViewById(R.id.cardmail);
        cardViewcall = view.findViewById(R.id.cardcall);
        cardViewfacebook = view.findViewById(R.id.cardfacebook);
        cardViewzalo = view.findViewById(R.id.cardzalo);

        cardViewmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"anhquyk797@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Support APP GTQA");
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Gửi email qua..."));
            }
        });

        cardViewcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCallIntent(Uri.parse("tel:0567284986"));
            }
        });

        cardViewzalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCallIntent(Uri.parse("tel:0567284986"));
            }
        });

        cardViewfacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String linkfb = "https://www.facebook.com/phamquy03";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkfb));
                startActivity(intent);
            }
        });
    }

    private void handleCallIntent(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        Context context = getActivity();
        if (context != null && isIntentSafe(intent, context)) {
            startActivity(intent);
        } else {
            Toast.makeText(context, "Thiết bị này không hỗ trợ chức năng gọi điện", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isIntentSafe(Intent intent, Context context) {
        PackageManager packageManager = context.getPackageManager();
        return intent.resolveActivity(packageManager) != null;
    }
}
