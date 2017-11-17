package com.kirillstom.walkmetest.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.kirillstom.walkmetest.database.AppDatabase;
import com.kirillstom.walkmetest.database.FormulaEntity;

import java.util.List;

/**
 * Created by kirill.stom on 17/11/2017.
 */

public class AllResultActivityViewModel extends AndroidViewModel{
    private AppDatabase mAppDatabase;

    public AllResultActivityViewModel(@NonNull Application application) {
        super(application);
        mAppDatabase = AppDatabase.getInstance(application);
    }

    public LiveData<List<FormulaEntity>> getAllFormulas() {
        return mAppDatabase.formulaDao().loadAllFormulas();
    }
}
