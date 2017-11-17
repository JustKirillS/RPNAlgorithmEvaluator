package com.kirillstom.walkmetest.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.kirillstom.walkmetest.database.AppDatabase;
import com.kirillstom.walkmetest.database.FormulaEntity;
import com.kirillstom.walkmetest.evaluator.FormulaEvaluator;
import com.kirillstom.walkmetest.evaluator.RPNFormulaEvaluator;
import com.kirillstom.walkmetest.evaluator.token.ParseFormulaException;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.internal.operators.completable.CompletableFromAction;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by kirill.stom on 15/11/2017.
 */

public class CalculateActivityViewModel extends AndroidViewModel {
    private MutableLiveData<String> mResult;
    private FormulaEvaluator mFormulaEvaluator;
    private AppDatabase mAppDatabase;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public CalculateActivityViewModel(@NonNull Application application) {
        super(application);
        mAppDatabase = AppDatabase.getInstance(application);
    }

    public LiveData<String> getResult() {
        if (mResult == null) {
            mResult = new MutableLiveData<>();
        }
        return mResult;
    }

    public void clear() {
        mResult.setValue(null);
    }

    public void calculate(String formulaString) throws ParseFormulaException {
        if (mFormulaEvaluator == null) {
            mFormulaEvaluator = new RPNFormulaEvaluator();
        }

        String result = mFormulaEvaluator.calculate(formulaString);

        mDisposable.add(saveFormulaResult(formulaString, result)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                        },
                        throwable -> Log.e(TAG, "Unable to save formula", throwable)));

        mResult.setValue(result);
    }

    public Completable saveFormulaResult(final String formulaInfix, final String result) {
        return new CompletableFromAction(() -> mAppDatabase.formulaDao().insert(new FormulaEntity(formulaInfix, result)));
    }
}