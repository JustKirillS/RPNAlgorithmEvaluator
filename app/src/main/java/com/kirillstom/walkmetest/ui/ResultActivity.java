package com.kirillstom.walkmetest.ui;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.kirillstom.walkmetest.R;
import com.kirillstom.walkmetest.databinding.ActivityResultBinding;

/**
 * Created by kirill.stom on 15/11/2017.
 */

public class ResultActivity extends AppCompatActivity {
    private static final String RESULT_EXTRA_KEY = "result";

    private ActivityResultBinding mBinding;

    public static void start(Activity activity, String result) {
        Intent intent = new Intent(activity, ResultActivity.class);
        intent.putExtra(RESULT_EXTRA_KEY, result);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_result);
        initActionBar();

        mBinding.resultTextView.setText(getIntent().getStringExtra(RESULT_EXTRA_KEY));
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
