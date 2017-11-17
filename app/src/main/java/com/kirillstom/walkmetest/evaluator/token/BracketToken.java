package com.kirillstom.walkmetest.evaluator.token;

import android.support.annotation.Nullable;

/**
 * Created by kirill.stom on 16/11/2017.
 */

public class BracketToken implements Token {
    private BracketType mBracketType;

    public BracketToken(BracketType bracketType) {
        mBracketType = bracketType;
    }

    public BracketToken(char c) {
        mBracketType = BracketType.parse(c);
        if (mBracketType == null) {
            throw new IllegalArgumentException(String.format("Unknown bracket '%c", c));
        }
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.BRACKET;
    }

    public BracketType getBracketType() {
        return mBracketType;
    }

    @Nullable
    public static BracketType parseOperatorType(char c) {
        return BracketType.parse(c);
    }

    @Override
    public String toString() {
        return mBracketType.name();
    }

    /**
     * Enum of brackets types
     */

    public enum BracketType {
        OPEN('('), CLOSE(')');

        private char mSymbol;

        BracketType(char symbol) {
            mSymbol = symbol;
        }

        public char getSymbol() {
            return mSymbol;
        }

        @Nullable
        private static BracketType parse(char c) {
            for (BracketType bracketType : BracketType.values()) {
                if (bracketType.getSymbol() == c) {
                    return bracketType;
                }
            }
            return null;
        }
    }
}
