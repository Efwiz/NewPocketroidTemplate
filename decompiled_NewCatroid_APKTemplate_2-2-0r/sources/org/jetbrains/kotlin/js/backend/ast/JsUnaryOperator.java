package org.jetbrains.kotlin.js.backend.ast;

import com.beust.jcommander.Parameters;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes5.dex */
public enum JsUnaryOperator implements JsOperator {
    BIT_NOT("~", 14, 8),
    DEC("--", 14, 12),
    DELETE("delete", 14, 8),
    INC("++", 14, 12),
    NEG(Parameters.DEFAULT_OPTION_PREFIXES, 14, 8),
    POS(Marker.ANY_NON_NULL_MARKER, 14, 8),
    NOT("!", 14, 8),
    TYPEOF("typeof", 14, 8),
    VOID(PsiKeyword.VOID, 14, 8);

    private final int mask;
    private final int precedence;
    private final String symbol;

    JsUnaryOperator(String str, int i, int i2) {
        this.symbol = str;
        this.precedence = i;
        this.mask = i2;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsOperator
    public int getPrecedence() {
        return this.precedence;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsOperator
    public String getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsOperator
    public boolean isKeyword() {
        return this == DELETE || this == TYPEOF || this == VOID;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsOperator
    public boolean isLeftAssociative() {
        return (this.mask & 1) != 0;
    }

    public boolean isModifying() {
        return this == DEC || this == INC || this == DELETE;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsOperator
    public boolean isPrecedenceLessThan(JsOperator jsOperator) {
        return this.precedence < jsOperator.getPrecedence();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsOperator
    public boolean isValidInfix() {
        return (this.mask & 2) != 0;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsOperator
    public boolean isValidPostfix() {
        return (this.mask & 4) != 0;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsOperator
    public boolean isValidPrefix() {
        return (this.mask & 8) != 0;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.symbol;
    }
}
