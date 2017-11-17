package com.kirillstom.walkmetest.evaluator;

import com.kirillstom.walkmetest.evaluator.token.BracketToken;
import com.kirillstom.walkmetest.evaluator.token.OperandToken;
import com.kirillstom.walkmetest.evaluator.token.OperatorToken;
import com.kirillstom.walkmetest.evaluator.token.ParseFormulaException;
import com.kirillstom.walkmetest.evaluator.token.Token;
import com.kirillstom.walkmetest.evaluator.token.TokenFactory;
import com.kirillstom.walkmetest.evaluator.token.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by kirill.stom on 15/11/2017.
 *
 * Reverse Polish Notation algorithm used
 */

public class RPNFormulaEvaluator implements FormulaEvaluator {

    @Override
    public String calculate(String formulaString) throws ParseFormulaException {
        List<Token> tokens = TokenFactory.convertToTokens(formulaString);
        TokenFactory.log(tokens);

        tokens = convertToPostfixExpression(tokens);
        TokenFactory.log(tokens);

        return Double.toString(evaluate(tokens));
    }

    private List<Token> convertToPostfixExpression(List<Token> tokens) {
        Stack<Token> operatorStack = new Stack<>();
        List<Token> postfixTokenExpression = new ArrayList<>();

        for (Token token : tokens) {
            switch (token.getTokenType()) {
                case OPERAND:
                    postfixTokenExpression.add(token);
                    break;

                case OPERATOR:
                    OperatorToken current = (OperatorToken) token;
                    while (!operatorStack.isEmpty() && operatorStack.peek().getTokenType() == TokenType.OPERATOR) {
                        //if last token in stack is operator and it's priority higher or same as current
                        //we need to pop last operator
                        if (current.getPriority() <=
                                ((OperatorToken) operatorStack.peek()).getPriority()) {
                            postfixTokenExpression.add(operatorStack.pop());
                        } else {
                            break;
                        }
                    }
                    operatorStack.add(current);
                    break;

                case BRACKET:
                    switch (((BracketToken) token).getBracketType()) {
                        case CLOSE:
                            do {
                                //in this case we need to move all operators to postfix expression until open bracket
                                if (operatorStack.peek().getTokenType() != TokenType.BRACKET) {
                                    postfixTokenExpression.add(operatorStack.pop());
                                } else {
                                    operatorStack.pop();
                                    break;
                                }
                            } while (!operatorStack.isEmpty());
                            break;

                        case OPEN:
                            operatorStack.add(token);
                            break;
                    }
                    break;
            }
        }

        while (!operatorStack.isEmpty()) {
            postfixTokenExpression.add(operatorStack.pop());
        }

        return postfixTokenExpression;
    }

    private double evaluate(List<Token> postfixTokenFormula) {
        Stack<Double> stack = new Stack<>();
        for (Token token : postfixTokenFormula) {
            switch (token.getTokenType()) {
                case BRACKET:
                    //just ignore in prefix form there no brackets
                    break;

                case OPERAND:
                    stack.add(((OperandToken) token).getValue());
                    break;

                case OPERATOR:
                    OperatorToken operatorToken = (OperatorToken) token;
                    switch (operatorToken.getOperatorType()) {
                        case PLUS:
                            double op1 = stack.pop();
                            double op2 = stack.pop();
                            stack.add(op2 + op1);
                            break;

                        case MINUS:
                            op1 = stack.pop();
                            op2 = stack.pop();
                            stack.add(op2 - op1);
                            break;

                        case MULTIPY:
                            op1 = stack.pop();
                            op2 = stack.pop();
                            stack.add(op2 * op1);
                            break;

                        case DIVIDE:
                            op1 = stack.pop();
                            op2 = stack.pop();
                            stack.add(op2 / op1);
                            break;

                        case POWER:
                            op1 = stack.pop();
                            op2 = stack.pop();
                            stack.add(Math.pow(op2, op1));
                            break;

                        case NEGATION:
                            op1 = stack.pop();
                            stack.add(-op1);
                            break;
                    }
                    break;
            }
        }

        return stack.pop();
    }
}
