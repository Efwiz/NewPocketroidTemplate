package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiPackageAccessibilityStatementStub extends StubElement<PsiPackageAccessibilityStatement> {
    String getPackageName();

    List<String> getTargets();
}
