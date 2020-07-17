package com.dev.projectta.home;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.dev.projectta.R;
import com.dev.projectta.home.adapter.PagerOrderAdapter;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private boolean doubleBack;
    private Toast backToast;

    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.userNoBP)
    TextView userNoBP;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.vPager)
    ViewPager vPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tablayout.setupWithViewPager(vPager);
        PagerOrderAdapter pagerOrderAdapter = new PagerOrderAdapter(getSupportFragmentManager(), 0);
        pagerOrderAdapter.addFrag(new DashboardFrag(), "Dashboard");
        pagerOrderAdapter.addFrag(new StatusFrag(), "Status");
        pagerOrderAdapter.addFrag(new OthersFrag(), "Others");
        vPager.setAdapter(pagerOrderAdapter);
    }

    @Override
    public void onBackPressed() {
        if (doubleBack) {
            backToast.cancel();
            super.onBackPressed();
            moveTaskToBack(true);
        } else {
            backToast = Toast.makeText(this, "Press back againt to exit ", Toast.LENGTH_SHORT);
            backToast.show();
            doubleBack = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBack = false;
                }
            }, 2000);
        }

    }
}
