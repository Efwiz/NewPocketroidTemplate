package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.injected;

import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.LiteralTextEscaper;
import org.jetbrains.kotlin.com.intellij.psi.PsiLanguageInjectionHost;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiLiteralExpressionImpl;

/* JADX INFO: loaded from: classes3.dex */
public class StringLiteralEscaper<T extends PsiLanguageInjectionHost> extends LiteralTextEscaper<T> {
    private int[] outSourceOffsets;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "rangeInsideHost";
        } else {
            objArr[0] = "outChars";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/injected/StringLiteralEscaper";
        if (i != 2) {
            objArr[2] = "decode";
        } else {
            objArr[2] = "getOffsetInHost";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.LiteralTextEscaper
    public boolean isOneLine() {
        return true;
    }

    public StringLiteralEscaper(T t) {
        super(t);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.LiteralTextEscaper
    public boolean decode(TextRange textRange, StringBuilder sb) {
        if (textRange == null) {
            $$$reportNull$$$0(0);
        }
        if (sb == null) {
            $$$reportNull$$$0(1);
        }
        String strSubstring = textRange.substring(this.myHost.getText());
        int[] iArr = new int[strSubstring.length() + 1];
        this.outSourceOffsets = iArr;
        return PsiLiteralExpressionImpl.parseStringCharacters(strSubstring, sb, iArr);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.LiteralTextEscaper
    public int getOffsetInHost(int i, TextRange textRange) {
        if (textRange == null) {
            $$$reportNull$$$0(2);
        }
        int[] iArr = this.outSourceOffsets;
        int i2 = i < iArr.length ? iArr[i] : -1;
        if (i2 == -1) {
            return -1;
        }
        return Math.min(i2, textRange.getLength()) + textRange.getStartOffset();
    }
}
