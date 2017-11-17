package com.kirillstom.walkmetest.evaluator.token;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill.stom on 16/11/2017.
 *
 * Class for transforming String infix formula to List<Token>
 */

public class TokenFactory {


    public static List<Token> convertToTokens(String infixExpression) throws ParseFormulaException {
        List<Token> tokens = new ArrayList<>();
        StringBuilder operandBuilder = new StringBuilder();
        char[] infixChars = infixExpression.toCharArray();

        //we need to check number of brackets
        int numberOfBrackets = 0;
        int lastOpenedBracketIndex = 0;

        for (int i = 0; i < infixChars.length; i++) {
            char c = infixChars[i];

            switch (getTokenType(c)) {
                case OPERATOR:
                    tryToSaveOperand(tokens, i, operandBuilder);

                    OperatorToken currentOperatorToken = new OperatorToken(c);

                    if (tokens.isEmpty() && !currentOperatorToken.isSign()) {
                        //first character in formula is operator but not minus or plus
                        throw new ParseFormulaException(c, i);
                    }

                    if (i == infixChars.length - 1) {
                        //last character in formula is operator
                        throw new ParseFormulaException(c, i);
                    }

                    switch (currentOperatorToken.getOperatorType()) {
                        case MINUS:
                            if (tokens.isEmpty()) {
                                //minus in the start of formula is negation
                                tokens.add(new OperatorToken(OperatorToken.OperatorType.NEGATION));
                            } else {
                                Token lastToken = tokens.get(tokens.size() - 1);

                                //operator can go only after operand or closing bracket everything else is negation
                                if (lastToken.getTokenType() == TokenType.OPERAND ||
                                        (lastToken.getTokenType() == TokenType.BRACKET &&
                                                ((BracketToken) lastToken).getBracketType() ==
                                                        BracketToken.BracketType.CLOSE)) {
                                    //this is minus operator
                                    tokens.add(currentOperatorToken);
                                } else {
                                    //this is negation
                                    tokens.add(new OperatorToken(OperatorToken.OperatorType.NEGATION));
                                }
                            }
                            break;

                        default:
                            //we already filtered all cases where formula starts from operators *, /, ^
                            if (!tokens.isEmpty()) {
                                Token lastToken = tokens.get(tokens.size() - 1);
                                //operator can go only after operand or closing bracket
                                if (lastToken.getTokenType() == TokenType.OPERAND ||
                                        (lastToken.getTokenType() == TokenType.BRACKET &&
                                                ((BracketToken) lastToken).getBracketType() ==
                                                        BracketToken.BracketType.CLOSE)) {
                                    //this is operator
                                    tokens.add(currentOperatorToken);
                                } else {
                                    if (currentOperatorToken.getOperatorType() != OperatorToken.OperatorType.PLUS) {
                                        //operator can go only after operand or closing bracket, except plus
                                        throw new ParseFormulaException(c, i);
                                    }
                                }
                            }
                            break;
                    }

                    break;

                case BRACKET:
                    BracketToken bracketToken = new BracketToken(c);

                    //counting brackets
                    if (bracketToken.getBracketType() == BracketToken.BracketType.OPEN) {
                        numberOfBrackets++;
                        lastOpenedBracketIndex = i;
                    } else if (bracketToken.getBracketType() == BracketToken.BracketType.CLOSE) {
                        numberOfBrackets--;
                        if (numberOfBrackets < 0) {
                            //we close bracket before opening it
                            throw new ParseFormulaException(c, i);
                        }
                    }

                    tryToSaveOperand(tokens, i, operandBuilder);

                    tokens.add(bracketToken);
                    break;

                case OPERAND:
                    //saving part of operand
                    operandBuilder.append(c);
                    break;
            }
        }

        //save unsaved operand builder if exist
        tryToSaveOperand(tokens, infixChars.length, operandBuilder);

        //check number of brackets in the end
        //case when number of brackets < 0 already handled
        if (numberOfBrackets > 0) {
            throw new ParseFormulaException(
                    BracketToken.BracketType.OPEN.getSymbol(),
                    lastOpenedBracketIndex);
        }

        return tokens;
    }

    private static void tryToSaveOperand(List<Token> tokens, int index,
                                         StringBuilder operandBuilder) throws ParseFormulaException {
        if (operandBuilder.length() != 0) {
            String operand = operandBuilder.toString();
            try {
                tokens.add(new OperandToken(operand));
                operandBuilder.setLength(0);
            } catch (IllegalArgumentException e) {
                throw new ParseFormulaException(operand, index - operand.length());
            }
        }
    }

    public static TokenType getTokenType(char c) {
        if (BracketToken.parseOperatorType(c) != null) {
            return TokenType.BRACKET;
        } else if (OperatorToken.parseOperatorType(c) != null) {
            return TokenType.OPERATOR;
        } else {
            return TokenType.OPERAND;
        }
    }

    public static void log(List<Token> tokens) {
        Log.d("Tokens", "*************************");
        for (Token token : tokens) {
            Log.d("Tokens", String.format("[ %s ]", token.toString()));
        }
        Log.d("Tokens", "*************************");
    }
}
