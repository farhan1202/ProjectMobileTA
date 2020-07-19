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
import com.dev.projectta.home.adapter.AdapterResult;
import com.dev.projectta.home.model.Result;
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

public class ResultActivity extends AppCompatActivity {

    @BindView(R.id.toolbarResult)
    Toolbar toolbarResult;
    @BindView(R.id.recyclerResult)
    RecyclerView recyclerResult;

    ApiInterface apiInterface;
    PrefManager prefManager;
    LoadingDialog loadingDialog;
    
    Context context;
    List<Result.DataBean> dataBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarResult);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        context = this;
        apiInterface = UtilsApi.getApiService();
        prefManager = new PrefManager(context);
        loadingDialog = new LoadingDialog(context);
        
        fetchResult();
    }

    private void fetchResult() {
        loadingDialog.startLoadingDialog();
        apiInterface.getResultVote().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("status").equals("200")){
                            loadingDialog.dismissLoadingDialog();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            
                            dataBeans = new ArrayList<>();
                            Gson gson = new Gson();
                            
                            for (int i = 0; i < jsonArray.length() ; i++){
                                Result.DataBean dataBean = gson.fromJson(jsonArray.get(i).toString(), Result.DataBean.class);
                                dataBeans.add(dataBean);
                            }

                            AdapterResult adapterResult = new AdapterResult(context, dataBeans);
                            recyclerResult.setAdapter(adapterResult);
                            recyclerResult.setLayoutManager(new LinearLayoutManager(context));
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
                Toast.makeText(context, "Internet problem", Toast.LENGTH_SHORT).show();
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
