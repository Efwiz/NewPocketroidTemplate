package org.jetbrains.kotlin.com.intellij.psi.codeStyle;

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
public interface Indent {
    Indent add(Indent indent);

    boolean isGreaterThan(Indent indent);

    boolean isZero();

    Indent max(Indent indent);

    Indent min(Indent indent);

    Indent subtract(Indent indent);
}
