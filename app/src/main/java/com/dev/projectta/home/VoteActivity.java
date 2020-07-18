package com.dev.projectta.home;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.projectta.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VoteActivity extends AppCompatActivity {

    @BindView(R.id.toolbarVote)
    Toolbar toolbarVote;
    @BindView(R.id.recyclerVote)
    RecyclerView recyclerVote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarVote);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
