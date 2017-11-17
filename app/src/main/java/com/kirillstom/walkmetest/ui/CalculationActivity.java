package com.kirillstom.walkmetest.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.kirillstom.walkmetest.R;
import com.kirillstom.walkmetest.databinding.ActivityCalculationBinding;
import com.kirillstom.walkmetest.evaluator.token.ParseFormulaException;
import com.kirillstom.walkmetest.ui.viewmodel.CalculateActivityViewModel;
import com.squareup.seismic.ShakeDetector;

public class CalculationActivity extends AppCompatActivity implements ShakeDetector.Listener {

    private ActivityCalculationBinding mBinding;
    private CalculateActivityViewModel mActivityViewModel;
    private ShakeDetector mShakeDetector;
    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_calculation);
        initViews();
        initViewModel();

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mShakeDetector = new ShakeDetector(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mShakeDetector.start(mSensorManager);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mShakeDetector.stop();
    }

    private void initViews() {
        mBinding.showAllResults.setOnClickListener(v -> AllResultsActivity.start(this));
    }

    private void initViewModel() {
        mActivityViewModel = ViewModelProviders.of(this).get(CalculateActivityViewModel.class);

        final Observer<String> resultObserver = result -> {
            if (result != null) {
                ResultActivity.start(this, result);
                mActivityViewModel.clear();
            }
        };
        mActivityViewModel.getResult().observe(this, resultObserver);
    }

    @Override
    public void hearShake() {
        mShakeDetector.stop();

        String formulaString = mBinding.formulaEditText.getText().toString();
        mBinding.textInputLayout.setError(null);
        if (!TextUtils.isEmpty(formulaString)) {
            try {
                mActivityViewModel.calculate(formulaString);
            } catch (ParseFormulaException e) {
                mBinding.textInputLayout.setError(getString(R.string.error_parsing_formula,
                        e.getPart(), e.getPosition()));
                mShakeDetector.start(mSensorManager);
            }
        } else {
            mBinding.textInputLayout.setError(getString(R.string.error_empty_formula));
            mShakeDetector.start(mSensorManager);
        }
    }
}
