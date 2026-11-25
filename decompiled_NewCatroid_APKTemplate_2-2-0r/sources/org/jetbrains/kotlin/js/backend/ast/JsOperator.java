package org.jetbrains.kotlin.js.backend.ast;

/* JADX INFO: loaded from: classes5.dex */
public interface JsOperator {
    public static final int INFIX = 2;
    public static final int LEFT = 1;
    public static final int POSTFIX = 4;
    public static final int PREFIX = 8;

    int getPrecedence();

    String getSymbol();

    boolean isKeyword();

    boolean isLeftAssociative();

    boolean isPrecedenceLessThan(JsOperator jsOperator);

    boolean isValidInfix();

    boolean isValidPostfix();

    boolean isValidPrefix();
}
