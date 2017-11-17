package com.kirillstom.walkmetest.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by kirill.stom on 17/11/2017.
 */

@Entity(tableName = "formulas")
public class FormulaEntity {
    @PrimaryKey
    @NonNull
    private String formulaInfix;
    private String result;

    public FormulaEntity() {
    }

    public FormulaEntity(String formulaInfix, String result) {
        this.formulaInfix = formulaInfix;
        this.result = result;
    }

    public String getFormulaInfix() {
        return formulaInfix;
    }

    public void setFormulaInfix(String formulaInfix) {
        this.formulaInfix = formulaInfix;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormulaEntity that = (FormulaEntity) o;

        if (!formulaInfix.equals(that.formulaInfix)) return false;
        return result != null ? result.equals(that.result) : that.result == null;
    }

    @Override
    public int hashCode() {
        int result1 = formulaInfix.hashCode();
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }
}

