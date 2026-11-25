package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiRequiresStatement extends PsiModifierListOwner, PsiStatement {
    public static final PsiRequiresStatement[] EMPTY_ARRAY = new PsiRequiresStatement[0];

    String getModuleName();

    PsiJavaModuleReference getModuleReference();

    PsiJavaModuleReferenceElement getReferenceElement();

    default PsiJavaModule resolve() {
        PsiJavaModuleReference moduleReference = getModuleReference();
        if (moduleReference != null) {
            return moduleReference.resolve();
        }
        return null;
    }
}
