package com.kirillstom.walkmetest.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by kirill.stom on 17/11/2017.
 */
@Dao
public interface FormulasDao {
    @Query("SELECT * FROM formulas")
    LiveData<List<FormulaEntity>> loadAllFormulas();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<FormulaEntity> formulas);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FormulaEntity formula);
}
