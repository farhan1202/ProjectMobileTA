package com.dev.projectta.home;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.projectta.R;
import com.dev.projectta.home.adapter.AdapterCandidate;
import com.dev.projectta.home.model.Candidate;
import com.dev.projectta.utils.LoadingDialog;
import com.dev.projectta.utils.PrefManager;
import com.dev.projectta.utils.apihelper.ApiInterface;
import com.dev.projectta.utils.apihelper.UtilsApi;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CandidateActivity extends AppCompatActivity {

    @BindView(R.id.toolbarCandidate)
    Toolbar toolbarCandidate;
    @BindView(R.id.recyclerCandidate)
    RecyclerView recyclerCandidate;

    ApiInterface apiInterface;
    PrefManager prefManager;

    LoadingDialog loadingDialog;
    List<Candidate.DataBean> dataBeans;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarCandidate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        apiInterface = UtilsApi.getApiService();
        prefManager = new PrefManager(this);
        loadingDialog = new LoadingDialog(this);

        fetchData();

    }

    private void fetchData() {
        loadingDialog.startLoadingDialog();
        apiInterface.getAllCandidate().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("status").equals("200")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            loadingDialog.dismissLoadingDialog();

                            dataBeans = new ArrayList<>();
                            Gson gson = new Gson();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                Candidate.DataBean bean = gson.fromJson(jsonArray.get(i).toString(), Candidate.DataBean.class);
                                dataBeans.add(bean);
                            }

                            AdapterCandidate adapterCandidate = new AdapterCandidate(context, dataBeans);
                            recyclerCandidate.setAdapter(adapterCandidate);
                            recyclerCandidate.setLayoutManager(new LinearLayoutManager(context));

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
                loadingDialog.dismissLoadingDialog();
                Toast.makeText(context, "internet problem", Toast.LENGTH_SHORT).show();
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
}
