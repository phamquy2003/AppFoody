package com.henrryd.appfoody2;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.henrryd.appfoody2.BroadcastReceiverFoody.LowBatteryReceiver;
import com.henrryd.appfoody2.BroadcastReceiverFoody.NetworkChangeReceiver;
import com.henrryd.appfoody2.Entity.user1;
import com.henrryd.appfoody2.databinding.ActivityMainBinding;
import com.henrryd.appfoody2.other.DataLocalManager;
import com.henrryd.appfoody2.other.MyApplication;
import com.henrryd.appfoody2.other.custom_Picture;
import com.henrryd.appfoody2.other.user;

import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity {

    ImageView img_header;
    private ActivityMainBinding binding;
    TextView email_header, name_header;
    private NetworkChangeReceiver receiver;
    private LowBatteryReceiver lowBatteryReceiver;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("MainActivity", "Calling DataLocalManager.init");
        DataLocalManager.init(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        Intent it = getIntent();
        Bundle bundle = it.getExtras();

        if (bundle != null) inituser(bundle);

        Toolbar toolbar = binding.appBarMain.toolbar;
        setSupportActionBar(toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_thongtinntaikhoan, R.id.nav_lienhe)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View view = binding.navView.getHeaderView(0);
        img_header = view.findViewById(R.id.imageView_header);
        email_header = view.findViewById(R.id.email_header);
        name_header = view.findViewById(R.id.name_header);

        loadUserInfo();
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.acction_logout) {
                    logOut();
                }
                return false;
            }
        });

        // Register BroadcastReceivers
        receiver = new NetworkChangeReceiver();
        IntentFilter filterInternet = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filterInternet);

        lowBatteryReceiver = new LowBatteryReceiver();
        IntentFilter filterBattery = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(lowBatteryReceiver, filterBattery);
    }

    private void logOut() {
        Log.d("MainActivity", "Logging out");
        DataLocalManager.remove_user();
        Intent it = new Intent(MainActivity.this, LoginActivity.class);
        MyApplication.User = null;
        startActivity(it);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    private void loadUserInfo() {
        String tmpUser = DataLocalManager.get_user();
        if (tmpUser != null) {
            Gson gson = new Gson();
            Type objType = new TypeToken<user1>() {}.getType();
            user1 tmp = gson.fromJson(tmpUser, objType);
            img_header.setImageAlpha(R.mipmap.image_avt);
            name_header.setText(tmp.name);
            email_header.setText(tmp.username);
        }
    }

    private void inituser(Bundle bundle) {
        user tmp = (user) bundle.getSerializable("dulieu");
        if (tmp != null) {
            Gson gson = new Gson();
            DataLocalManager.update_user(gson.toJson(tmp));
        }
    }
}
