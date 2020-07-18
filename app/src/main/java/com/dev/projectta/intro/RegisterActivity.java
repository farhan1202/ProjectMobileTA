package com.dev.projectta.intro;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dev.projectta.R;
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

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.editNama)
    EditText editNama;
    @BindView(R.id.editNoBP)
    EditText editNoBP;
    @BindView(R.id.editPassword)
    EditText editPassword;
    @BindView(R.id.editRePassword)
    EditText editRePassword;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        apiInterface = UtilsApi.getApiService();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editNama.getText().toString())){
                    editNama.setError("Field is Empty");
                }else if (TextUtils.isEmpty(editNoBP.getText().toString())){
                    editNoBP.setError("Field is Empty");
                }else if (TextUtils.isEmpty(editPassword.getText().toString())){
                    editPassword.setError("Field is Empty");
                }else if (TextUtils.isEmpty(editRePassword.getText().toString())){
                    editRePassword.setError("Field is Empty");
                }else if(!editPassword.getText().toString().equals(editRePassword.getText().toString())){
                    editPassword.setError("Password doesnt match");
                    editRePassword.setError("Password doesnt match");
                }else{
                    fetchData();
                }
            }
        });
    }

    private void fetchData() {
        apiInterface.registerUser(editNoBP.getText().toString(),
                editPassword.getText().toString(),
                editNama.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("STATUS").equals("200")){
                            Toast.makeText(RegisterActivity.this, jsonObject.getString("MESSAGE") +"", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(RegisterActivity.this, "Something when wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }



    public void login(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}
