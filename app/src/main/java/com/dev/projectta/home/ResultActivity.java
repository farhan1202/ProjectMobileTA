package com.dev.projectta.home;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dev.projectta.R;
import com.dev.projectta.home.adapter.AdapterResult;
import com.dev.projectta.home.model.Result;
import com.dev.projectta.utils.LoadingDialog;
import com.dev.projectta.utils.PrefManager;
import com.dev.projectta.utils.apihelper.ApiInterface;
import com.dev.projectta.utils.apihelper.UtilsApi;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
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
//    @BindView(R.id.recyclerResult)
//    RecyclerView recyclerResult;

    ApiInterface apiInterface;
    PrefManager prefManager;
    LoadingDialog loadingDialog;


    Context context;
    List<Result.DataBean> dataBeans;
    @BindView(R.id.barchart)
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarResult);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Result");

        context = this;
        apiInterface = UtilsApi.getApiService();
        prefManager = new PrefManager(context);
        loadingDialog = new LoadingDialog(context);

        fetchResult();
//        Graph();
    }

    private void Graph() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(8f, 0));
        entries.add(new BarEntry(5f, 1));
        entries.add(new BarEntry(20f, 2));
        entries.add(new BarEntry(15f, 3));
        entries.add(new BarEntry(19f, 4));

        BarDataSet bardataset = new BarDataSet(entries, "Cells");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("2016");
        labels.add("2015");
        labels.add("2014");
        labels.add("2013");
        labels.add("2012");

        BarData data = new BarData(labels, bardataset);
        barChart.setData(data); // set the data and list of labels into chart
        barChart.setDescription("Set Bar Chart Description Here");  // set the description
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.animateY(5000);
    }


    private void fetchResult() {
        loadingDialog.startLoadingDialog();
        apiInterface.getResultVote().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("status").equals("200")) {
                            loadingDialog.dismissLoadingDialog();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            ArrayList<BarEntry> entries = new ArrayList<>();
                            ArrayList<String> labels = new ArrayList<String>();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                int jum = Integer.parseInt(jsonArray.getJSONObject(i).getString("jumlah_suara"));
                                entries.add(new BarEntry(jum, i));
                            }

                            for (int i = 0; i < jsonArray.length(); i++) {
                                labels.add(jsonArray.getJSONObject(i).getString("nama"));
                            }

                            BarDataSet bardataset = new BarDataSet(entries, "Cells");
                            bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                            barChart.getAxisLeft().setAxisMinValue(0f);
                            barChart.getAxisRight().setAxisMinValue(0f);
                            BarData data = new BarData(labels, bardataset);
                            barChart.setData(data); // set the data and list of labels into chart
                            barChart.setDescription("E-voting Result");  // set the description
                            barChart.animateY(2000);


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
            case R.id.menu_refresh:
                Toast.makeText(context, "Refresh", Toast.LENGTH_SHORT).show();
                fetchResult();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

}
