package org.eclipse.jdt.internal.compiler.ast;

/* JADX INFO: loaded from: classes14.dex */
public abstract class NumberLiteral extends Literal {
    char[] source;

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public boolean isValidJavaStatement() {
        return false;
    }

    public NumberLiteral(char[] cArr, int i, int i2) {
        this(i, i2);
        this.source = cArr;
    }

    public NumberLiteral(int i, int i2) {
        super(i, i2);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Literal
    public char[] source() {
        return this.source;
    }

    protected static char[] removePrefixZerosAndUnderscores(char[] cArr, boolean z) {
        int i;
        char c;
        char c2;
        int length = cArr.length;
        int i2 = length - 1;
        if (z) {
            i2 = length - 2;
        }
        boolean z2 = true;
        if (length <= 1 || cArr[0] != '0') {
            i = 0;
        } else {
            i = 2;
            if ((length <= 2 || ((c2 = cArr[1]) != 'x' && c2 != 'X')) && (length <= 2 || ((c = cArr[1]) != 'b' && c != 'B'))) {
                i = 1;
            }
        }
        boolean z3 = false;
        boolean z4 = true;
        for (int i3 = i; i3 < length; i3++) {
            char c3 = cArr[i3];
            if (c3 != '0') {
                if (c3 == '_') {
                    z3 = true;
                    break;
                }
                z4 = false;
            } else if (z4 && !z3 && i3 < i2) {
                z3 = true;
            }
        }
        if (!z3) {
            return cArr;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(cArr, 0, i);
        while (i < length) {
            char c4 = cArr[i];
            if (c4 != '0') {
                if (c4 != '_') {
                    z2 = false;
                    stringBuffer.append(c4);
                }
            } else if (!z2 || i >= i2) {
                stringBuffer.append(c4);
            }
            i++;
        }
        return stringBuffer.toString().toCharArray();
    }
}
