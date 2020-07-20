package com.dev.projectta.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.projectta.R;
import com.dev.projectta.home.adapter.AdapterCandidate;
import com.dev.projectta.home.adapter.AdapterVote;
import com.dev.projectta.home.interfaces.VoteInterface;
import com.dev.projectta.home.model.Candidate;
import com.dev.projectta.intro.LoginActivity;
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

public class VoteActivity extends AppCompatActivity {

    @BindView(R.id.toolbarVote)
    Toolbar toolbarVote;
    @BindView(R.id.recyclerVote)
    RecyclerView recyclerVote;
    @BindView(R.id.btnVote)
    Button btnVote;

    ApiInterface apiInterface;
    PrefManager prefManager;
    LoadingDialog loadingDialog;

    List<Candidate.DataBean> dataBeans;
    Context context;

    int nobp_candidate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarVote);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        apiInterface = UtilsApi.getApiService();
        prefManager = new PrefManager(context);
        loadingDialog = new LoadingDialog(context);

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

                            AdapterVote adapterVote = new AdapterVote(context, dataBeans, new VoteInterface() {
                                @Override
                                public void onItemClick(int id) {
                                    nobp_candidate = id;
                                }
                            });
                            recyclerVote.setAdapter(adapterVote);
                            recyclerVote.setLayoutManager(new LinearLayoutManager(context));
                            btnVote.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Voting();  
                                }
                            });




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

    private void Voting() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setMessage("Are you sure?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loadingDialog.startLoadingDialog();
                apiInterface.vote(prefManager.getId(), nobp_candidate + "", prefManager.getToken()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                if (jsonObject.getString("status").equals("200")) {
                                    loadingDialog.dismissLoadingDialog();
                                    Toast.makeText(context, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
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
                        Toast.makeText(context, "Connection Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


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
