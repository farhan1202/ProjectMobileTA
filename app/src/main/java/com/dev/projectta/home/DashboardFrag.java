package com.dev.projectta.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
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
public class DashboardFrag extends Fragment {

    @BindView(R.id.voteButton)
    CardView voteButton;
    @BindView(R.id.candidateButton)
    CardView candidateButton;
    @BindView(R.id.resultButton)
    CardView resultButton;

    ApiInterface apiInterface;
    PrefManager prefManager;
    LoadingDialog loadingDialog;
    @BindView(R.id.voteBack)
    LinearLayout voteBack;


    public DashboardFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);
        apiInterface = UtilsApi.getApiService();
        prefManager = new PrefManager(view.getContext());
        loadingDialog = new LoadingDialog(view.getContext());
        getStatus();

        voteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekStatus();
            }
        });
        candidateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), CandidateActivity.class);
                startActivity(intent);
            }
        });
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), ResultActivity.class);
                startActivity(intent);
            }
        });
        return view;

    }

    private void getStatus() {
        loadingDialog.startLoadingDialog();
        apiInterface.getUserStatus(prefManager.getId(), prefManager.getToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("STATUS").equals("200")) {
                            loadingDialog.dismissLoadingDialog();
                            JSONObject jsonObject1 = new JSONObject(jsonObject.getString("DATA"));
                            int ket1 = Integer.parseInt(jsonObject1.getString("finger"));
                            int ket2 = Integer.parseInt(jsonObject1.getString("rfid"));
                            int status = Integer.parseInt(jsonObject1.getString("vote"));
                            if (ket1 == 0 || ket2 == 0) {
                                voteBack.setAlpha((float) 0.3);
                            } else if (status == 1) {
                                voteBack.setAlpha((float) 0.3);
                            }
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
                Toast.makeText(getContext(), "Connection Problem", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cekStatus() {
        loadingDialog.startLoadingDialog();
        apiInterface.getUserStatus(prefManager.getId(), prefManager.getToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("STATUS").equals("200")) {
                            loadingDialog.dismissLoadingDialog();
                            JSONObject jsonObject1 = new JSONObject(jsonObject.getString("DATA"));
                            int ket1 = Integer.parseInt(jsonObject1.getString("finger"));
                            int ket2 = Integer.parseInt(jsonObject1.getString("rfid"));
                            int status = Integer.parseInt(jsonObject1.getString("vote"));
                            if (ket1 == 1 && ket2 == 1 && status == 0) {
                                Intent intent = new Intent(getContext(), VoteActivity.class);
                                startActivity(intent);
                            } else if (ket1 == 0 || ket2 == 0) {
                                Toast.makeText(getContext(), "Please complete verification", Toast.LENGTH_SHORT).show();
                            } else if (status == 1) {
                                Toast.makeText(getContext(), "You have been vote!", Toast.LENGTH_SHORT).show();
                            }
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
                Toast.makeText(getContext(), "Connection Problem", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
