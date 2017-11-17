package com.kirillstom.walkmetest.evaluator.token;

/**
 * Created by kirill.stom on 16/11/2017.
 */

public class OperandToken implements Token {
    private static final String PI = "pi";
    private static final String E = "e";
    private double mValue;

    public OperandToken(String s) throws IllegalArgumentException {
        switch (s.toLowerCase()) {
            case PI:
                mValue = Math.PI;
                break;

            case E:
                mValue = Math.E;
                break;

            default:
                mValue = Double.parseDouble(s);
                break;
        }
    }

    public double getValue() {
        return mValue;
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.OPERAND;
    }

    @Override
    public String toString() {
        return Double.toString(mValue);
    }
}
