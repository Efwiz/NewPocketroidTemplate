package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiClassHolderFileStub;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiJavaFileStub extends PsiClassHolderFileStub<PsiJavaFile> {
    LanguageLevel getLanguageLevel();

    PsiJavaModule getModule();

    String getPackageName();

    StubPsiFactory getPsiFactory();

    boolean isCompiled();

    @Deprecated
    void setPsiFactory(StubPsiFactory stubPsiFactory);
}
