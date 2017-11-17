package com.kirillstom.walkmetest.ui;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.kirillstom.walkmetest.R;
import com.kirillstom.walkmetest.database.FormulaEntity;
import com.kirillstom.walkmetest.databinding.ActivityAllResultsBinding;
import com.kirillstom.walkmetest.ui.adapter.FormulaAdapter;
import com.kirillstom.walkmetest.ui.viewmodel.AllResultActivityViewModel;

import java.util.List;

/**
 * Created by kirill.stom on 17/11/2017.
 */

public class AllResultsActivity extends AppCompatActivity {
    private ActivityAllResultsBinding mBinding;
    private AllResultActivityViewModel mActivityViewModel;
    private FormulaAdapter mAdapter;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, AllResultsActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_all_results);
        mActivityViewModel = ViewModelProviders.of(this).get(AllResultActivityViewModel.class);
        initActionBar();

        mAdapter = new FormulaAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        mBinding.recyclerView.setLayoutManager(linearLayoutManager);
        mBinding.recyclerView.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                linearLayoutManager.getOrientation());
        mBinding.recyclerView.addItemDecoration(dividerItemDecoration);

        Observer<List<FormulaEntity>> formulasObserver = formulaEntities -> {
            if (formulaEntities == null || formulaEntities.isEmpty()) {
                showEmpty();
            } else {
                mAdapter.setFormulas(formulaEntities);
                showList();
            }
        };
        mActivityViewModel.getAllFormulas().observe(this, formulasObserver);
    }

    private void showEmpty() {
        mBinding.emptyList.setVisibility(View.VISIBLE);
        mBinding.recyclerView.setVisibility(View.GONE);
    }

    private void showList() {
        mBinding.emptyList.setVisibility(View.GONE);
        mBinding.recyclerView.setVisibility(View.VISIBLE);
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
