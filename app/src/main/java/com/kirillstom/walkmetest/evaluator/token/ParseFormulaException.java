package com.kirillstom.walkmetest.evaluator.token;

/**
 * Created by kirill.stom on 16/11/2017.
 */

public class ParseFormulaException extends Exception {

    private String mPart;
    private int mPosition;

    public ParseFormulaException(String part, int position) {
        super(String.format("input error near '%s' index '%d'", part, position));
        mPart = part;
        mPosition = position;
    }

    public ParseFormulaException(char c, int position) {
        this(Character.toString(c), position);
    }

    public String getPart() {
        return mPart;
    }

    public int getPosition() {
        return mPosition;
    }
}
