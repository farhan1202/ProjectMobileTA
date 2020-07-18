package com.dev.projectta.home;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.dev.projectta.R;
import com.dev.projectta.home.adapter.PagerOrderAdapter;
import com.dev.projectta.utils.LoadingDialog;
import com.dev.projectta.utils.PrefManager;
import com.dev.projectta.utils.apihelper.ApiInterface;
import com.dev.projectta.utils.apihelper.UtilsApi;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    LoadingDialog loadingDialog;
    ApiInterface apiInterface;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiInterface = UtilsApi.getApiService();
        prefManager = new PrefManager(this);
        loadingDialog = new LoadingDialog(this);
        fetchDataProfile();

        ButterKnife.bind(this);
        tablayout.setupWithViewPager(vPager);
        PagerOrderAdapter pagerOrderAdapter = new PagerOrderAdapter(getSupportFragmentManager(), 0);
        pagerOrderAdapter.addFrag(new DashboardFrag(), "Dashboard");
        pagerOrderAdapter.addFrag(new StatusFrag(), "Status");
        pagerOrderAdapter.addFrag(new OthersFrag(), "Others");
        vPager.setAdapter(pagerOrderAdapter);
    }

    private void fetchDataProfile() {
        loadingDialog.startLoadingDialog();
        apiInterface.userData(prefManager.getId()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("STATUS").equals("200")){
                            JSONObject jsonObject1 = new JSONObject(jsonObject.getString("DATA"));
                            userName.setText(jsonObject1.getString("nama"));
                            userNoBP.setText(jsonObject1.getString("nobp"));
                            loadingDialog.dismissLoadingDialog();
                        }else{
                            loadingDialog.dismissLoadingDialog();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    loadingDialog.dismissLoadingDialog();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                loadingDialog.dismissLoadingDialog();
            }
        });
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
