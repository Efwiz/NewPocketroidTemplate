package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.impl.LongConstant;

/* JADX INFO: loaded from: classes14.dex */
public class LongLiteralMinValue extends LongLiteral {
    static final char[] CharValue = {'-', '9', '2', '2', '3', '3', '7', '2', ExternalAnnotationProvider.NULLABLE, '3', '6', '8', '5', '4', '7', '7', '5', '8', ExternalAnnotationProvider.NULLABLE, '8', 'L'};

    @Override // org.eclipse.jdt.internal.compiler.ast.LongLiteral, org.eclipse.jdt.internal.compiler.ast.Literal
    public void computeConstant() {
    }

    public LongLiteralMinValue(char[] cArr, char[] cArr2, int i, int i2) {
        super(cArr, cArr2, i, i2);
        this.constant = LongConstant.fromValue(Long.MIN_VALUE);
    }
}
