package com.dev.projectta.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.dev.projectta.R;
import com.dev.projectta.utils.LoadingDialog;
import com.dev.projectta.utils.PrefManager;
import com.dev.projectta.utils.apihelper.ApiInterface;
import com.dev.projectta.utils.apihelper.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatusFrag extends Fragment {

    @BindView(R.id.status1)
    TextView status1;
    @BindView(R.id.status2)
    TextView status2;
    @BindView(R.id.status3)
    TextView status3;

    ApiInterface apiInterface;
    PrefManager prefManager;
    LoadingDialog loadingDialog;

    public StatusFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_status, container, false);
        ButterKnife.bind(this, view);
        apiInterface = UtilsApi.getApiService();
        prefManager = new PrefManager(view.getContext());
        loadingDialog = new LoadingDialog(view.getContext());
        fetchDataStatus();
        return view;
    }

    private void fetchDataStatus() {
        loadingDialog.startLoadingDialog();
        apiInterface.getUserStatus(prefManager.getId(), prefManager.getToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("STATUS").equals("200")){
                            loadingDialog.dismissLoadingDialog();
                            JSONObject jsonObject1 = new JSONObject(jsonObject.getString("DATA"));
                            if (jsonObject1.getString("finger").equals("0")){
                                status1.setText("Not Verified");
                                status1.setTextColor(Color.parseColor("#ffcc0000"));
                            }else{
                                status1.setText("Verified");
                                status1.setTextColor(Color.parseColor("#ff99cc00"));
                            }
                            if (jsonObject1.getString("rfid").equals("0")){
                                status2.setText("Not Verified");
                                status2.setTextColor(Color.parseColor("#ffcc0000"));
                            }else{
                                status2.setText("Verified");
                                status2.setTextColor(Color.parseColor("#ff99cc00"));
                            }
                            if (jsonObject1.getString("vote").equals("0")){
                                status3.setText("Not Voted Yet");
                                status3.setTextColor(Color.parseColor("#ffcc0000"));
                            }else{
                                status3.setText("Voted");
                                status3.setTextColor(Color.parseColor("#ff99cc00"));
                            }
                        }else{
                            loadingDialog.dismissLoadingDialog();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismissLoadingDialog();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                loadingDialog.dismissLoadingDialog();
            }
        });
    }
}
