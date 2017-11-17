package com.kirillstom.walkmetest.evaluator;

import com.kirillstom.walkmetest.evaluator.token.ParseFormulaException;

/**
 * Created by kirill.stom on 15/11/2017.
 */

public interface FormulaEvaluator {
    String calculate(String formulaString) throws ParseFormulaException;
}
