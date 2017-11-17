package com.kirillstom.walkmetest.evaluator.token;

import android.support.annotation.Nullable;

/**
 * Created by kirill.stom on 16/11/2017.
 */

public class OperatorToken implements Token {
    private OperatorType mOperatorType;

    public OperatorToken(OperatorType operatorType) {
        mOperatorType = operatorType;
    }

    public OperatorToken(char c) throws IllegalArgumentException {
        mOperatorType = OperatorType.parse(c);
        if (mOperatorType == null) {
            throw new IllegalArgumentException(String.format("Unknown operator '%c", c));
        }
    }

    public OperatorType getOperatorType() {
        return mOperatorType;
    }

    @Nullable
    public static OperatorType parseOperatorType(char c) {
        return OperatorType.parse(c);
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.OPERATOR;
    }

    public int getPriority() {
        return mOperatorType.getPriority();
    }

    @Override
    public String toString() {
        return mOperatorType.name();
    }

    public boolean isSign() {
        return mOperatorType == OperatorType.MINUS ||
                mOperatorType == OperatorType.PLUS ||
                mOperatorType == OperatorType.NEGATION;
    }

    /**
     * Enum of operators types
     */

    public enum OperatorType {
        PLUS('+', 1), MINUS('-', 1), MULTIPY('*', 2), DIVIDE('/', 2), POWER('^', 3), NEGATION('!', 4);

        private char mSymbol;
        private int mPriority;

        OperatorType(char symbol, int priority) {
            mSymbol = symbol;
            mPriority = priority;
        }

        public char getSymbol() {
            return mSymbol;
        }

        public int getPriority() {
            return mPriority;
        }

        @Nullable
        private static OperatorType parse(char c) {
            for (OperatorType operatorType : OperatorType.values()) {
                if (operatorType.getSymbol() == c) {
                    return operatorType;
                }
            }
            return null;
        }
    }
}
