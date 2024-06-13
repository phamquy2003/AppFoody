package com.henrryd.appfoody2;



import static com.henrryd.appfoody2.R.id.imageView_header;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.henrryd.appfoody2.Adapters.AdapterViewHome;
import com.henrryd.appfoody2.BroadcastReceiverFoody.LowBatteryReceiver;
import com.henrryd.appfoody2.BroadcastReceiverFoody.NetworkChangeReceiver;
import com.henrryd.appfoody2.Entity.user1;
import com.henrryd.appfoody2.databinding.ActivityMainBinding;
import com.henrryd.appfoody2.other.DataLocalManager;
import com.henrryd.appfoody2.other.MyApplication;
import com.henrryd.appfoody2.other.user;
import com.henrryd.appfoody2.other.custom_Picture;

import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    ViewPager viewPagerHome;
    RadioButton rbWhere;
    RadioButton rbFood;
    RadioGroup grwhere_food;
    ImageView img_header;

    TextView email_header, name_header;
    private NetworkChangeReceiver receiver;
    private LowBatteryReceiver lowBatteryReceiver;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

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
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        viewPagerHome = findViewById(R.id.viewpager_home);
        grwhere_food = findViewById(R.id.grwhere_food);
        rbWhere = findViewById(R.id.rbWhere);
        rbFood = findViewById(R.id.rbFood);
        AdapterViewHome adapterViewHome = new AdapterViewHome(getSupportFragmentManager());
        viewPagerHome.setAdapter(adapterViewHome);
        viewPagerHome.addOnPageChangeListener(this);
        grwhere_food.setOnCheckedChangeListener(this);

        View view = binding.navView.getHeaderView(0);
        img_header = view.findViewById(imageView_header);
        email_header = view.findViewById(R.id.email_header);
        name_header = view.findViewById(R.id.name_header);

        Loadinfouser();
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.acction_logout){
                    log_out();
                }
                return false;
            }


        });

        // Tạo BroadcastReceiver
        receiver = new NetworkChangeReceiver();
        // Đăng ký BroadcastReceiver
        IntentFilter filterinternet = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filterinternet);


        // Tạo LowBatteryReceiver
        lowBatteryReceiver = new LowBatteryReceiver();
        // Đăng ký LowBatteryReceiver
        IntentFilter filterbattery = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(lowBatteryReceiver, filterbattery);




    }

    private void log_out() {
        Log.d("MainActivity", "Logging out");
        DataLocalManager.remove_user();
        Intent it = new Intent(MainActivity.this, LoginActivity.class);
        MyApplication.User = null;
        startActivity(it);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    private void Loadinfouser() {
        String tmpuser = DataLocalManager.get_user();
        if (tmpuser != null) {
            Gson gson = new Gson();
            Type objtype = new TypeToken<user1>() {
            }.getType();
            user1 tmp = gson.fromJson(tmpuser, objtype);
            img_header.setImageURI(custom_Picture.getUri(tmp.avatar));
            name_header.setText(tmp.name);
            email_header.setText(tmp.username);
        }
    }
    private void inituser(Bundle bundle) {
        user tmp = (user) bundle.getSerializable("dulieu");
        //Gson để chuyển dữ liệu từ Object sang string
        Gson gson = new Gson();
        DataLocalManager.update_user(gson.toJson(tmp));
    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                rbWhere.setChecked(true);
                break;
            case  1:
                rbFood.setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rbFood) {
            viewPagerHome.setCurrentItem(1);
        } else if (checkedId == R.id.rbWhere) {
            viewPagerHome.setCurrentItem(0);
        }

    }
}