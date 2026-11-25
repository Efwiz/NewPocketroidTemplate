package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.impl.IntConstant;

/* JADX INFO: loaded from: classes14.dex */
public class IntLiteralMinValue extends IntLiteral {
    static final char[] CharValue = {'-', '2', ExternalAnnotationProvider.NONNULL, '4', '7', '4', '8', '3', '6', '4', '8'};

    @Override // org.eclipse.jdt.internal.compiler.ast.IntLiteral, org.eclipse.jdt.internal.compiler.ast.Literal
    public void computeConstant() {
    }

    public IntLiteralMinValue(char[] cArr, char[] cArr2, int i, int i2) {
        super(cArr, cArr2, i, i2, Integer.MIN_VALUE, IntConstant.fromValue(Integer.MIN_VALUE));
    }
}
