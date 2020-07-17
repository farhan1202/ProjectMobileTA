package com.dev.projectta.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.dev.projectta.R;
import com.dev.projectta.intro.LoginActivity;
import com.dev.projectta.utils.PrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OthersFrag extends Fragment {
    PrefManager prefManager;

    @BindView(R.id.information)
    TextView information;
    @BindView(R.id.aboutUs)
    TextView aboutUs;
    @BindView(R.id.logOut)
    TextView logOut;

    public OthersFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_others, container, false);
        ButterKnife.bind(this, view);
        prefManager = new PrefManager(getContext());
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                prefManager.removeSession();
            }
        });
        return view;
    }
}
