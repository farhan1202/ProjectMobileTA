package com.dev.projectta.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.dev.projectta.R;
import com.dev.projectta.utils.LoadingDialog;
import com.dev.projectta.utils.PrefManager;
import com.dev.projectta.utils.apihelper.ApiInterface;
import com.dev.projectta.utils.apihelper.UtilsApi;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCandidateActivity extends AppCompatActivity {

    @BindView(R.id.toolbarDetailCandidate)
    Toolbar toolbarDetailCandidate;

    @BindView(R.id.candidateDetailImg)
    ImageView candidateImg;
    @BindView(R.id.candidateDetailName)
    TextView candidateName;
    @BindView(R.id.candidateDetailBP)
    TextView candidateBP;
    @BindView(R.id.candidateDetailJurusan)
    TextView candidateJurusan;
    @BindView(R.id.candidateDetailKeterangan)
    TextView candidateKeterangan;

    Context context;
    ApiInterface apiInterface;
    PrefManager prefManager;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_candidate);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarDetailCandidate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        context = this;
        apiInterface = UtilsApi.getApiService();
        prefManager = new PrefManager(this);
        loadingDialog = new LoadingDialog(this);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id_candidate");
        getDataCandidate(id);
    }



    private void getDataCandidate(String id) {
        loadingDialog.startLoadingDialog();
        apiInterface.getCandidateById(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonObject1 = new JSONObject(response.body().string());
                        if (jsonObject1.getString("STATUS").equals("200")){
                            JSONObject jsonObject = jsonObject1.getJSONObject("DATA");

                            loadingDialog.dismissLoadingDialog();

                            candidateBP.setText(jsonObject.getString("nobp"));
                            candidateName.setText(jsonObject.getString("nama"));
                            candidateKeterangan.setText(jsonObject.getString("keterangan"));
                            candidateJurusan.setText(jsonObject.getString("jurusan"));

                            Glide
                                    .with(context)
                                    .load(UtilsApi.BASE_URL1 + jsonObject.getString("profile_image"))
                                    .centerCrop()
                                    .into(candidateImg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Internet Problem", Toast.LENGTH_SHORT).show();
                loadingDialog.dismissLoadingDialog();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

   /* @Override
    public void onResume() {
        super.onResume();
        shimmer.startShimmerAnimation();
    }

    @Override
    protected void onPause() {
        shimmer.stopShimmerAnimation();
        super.onPause();
    }*/
}