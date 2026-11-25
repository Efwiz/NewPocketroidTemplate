package org.jetbrains.kotlin.com.intellij.psi;

import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiPackageAccessibilityStatement extends PsiStatement {
    public static final PsiPackageAccessibilityStatement[] EMPTY_ARRAY = new PsiPackageAccessibilityStatement[0];

    public enum Role {
        EXPORTS,
        OPENS
    }

    List<String> getModuleNames();

    Iterable<PsiJavaModuleReferenceElement> getModuleReferences();

    String getPackageName();

    PsiJavaCodeReferenceElement getPackageReference();

    Role getRole();
}
