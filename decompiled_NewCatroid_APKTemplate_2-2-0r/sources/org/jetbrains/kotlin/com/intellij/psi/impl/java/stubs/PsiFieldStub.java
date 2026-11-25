package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeInfo;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiFieldStub extends PsiMemberStub<PsiField> {
    public static final String INITIALIZER_NOT_STORED = ";INITIALIZER_NOT_STORED;";
    public static final String INITIALIZER_TOO_LONG = ";INITIALIZER_TOO_LONG;";

    String getInitializerText();

    TypeInfo getType();

    boolean isEnumConstant();
}
