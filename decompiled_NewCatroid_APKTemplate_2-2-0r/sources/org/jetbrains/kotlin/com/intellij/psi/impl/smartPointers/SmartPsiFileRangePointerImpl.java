package org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers;

import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.injection.InjectedLanguageManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.ProperTextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiLanguageInjectionHost;
import org.jetbrains.kotlin.com.intellij.psi.SmartPointerManager;
import org.jetbrains.kotlin.com.intellij.psi.SmartPsiFileRange;
import org.jetbrains.kotlin.com.intellij.psi.impl.FreeThreadedFileViewProvider;

/* JADX INFO: loaded from: classes3.dex */
class SmartPsiFileRangePointerImpl extends SmartPsiElementPointerImpl<PsiFile> implements SmartPsiFileRange {
    /* JADX WARN: Code duplicated, block: B:8:0x0014  */
    /* JADX WARN: Code duplicated, block: B:9:0x0019  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "containingFile";
        } else if (i == 2) {
            objArr[0] = "range";
        } else if (i == 3) {
            objArr[0] = "containingFile";
        } else if (i != 4) {
            objArr[0] = "manager";
        } else {
            objArr[0] = "range";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/SmartPsiFileRangePointerImpl";
        if (i == 3 || i == 4) {
            objArr[2] = "createElementInfo";
        } else {
            objArr[2] = "<init>";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    SmartPsiFileRangePointerImpl(SmartPointerManagerImpl smartPointerManagerImpl, PsiFile psiFile, ProperTextRange properTextRange, boolean z) {
        super(smartPointerManagerImpl, psiFile, createElementInfo(psiFile, properTextRange, z));
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(0);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(1);
        }
        if (properTextRange == null) {
            $$$reportNull$$$0(2);
        }
    }

    private static SmartPointerElementInfo createElementInfo(PsiFile psiFile, ProperTextRange properTextRange, boolean z) {
        PsiLanguageInjectionHost injectionHost;
        if (psiFile == null) {
            $$$reportNull$$$0(3);
        }
        if (properTextRange == null) {
            $$$reportNull$$$0(4);
        }
        Project project = psiFile.getProject();
        if (!(psiFile.getViewProvider() instanceof FreeThreadedFileViewProvider) || (injectionHost = InjectedLanguageManager.getInstance(project).getInjectionHost(psiFile)) == null) {
            return (z || !properTextRange.equals(psiFile.getTextRange())) ? new SelfElementInfo(properTextRange, Identikit.fromTypes(PsiElement.class, null, Language.ANY), psiFile, z) : new FileElementInfo(psiFile);
        }
        return new InjectedSelfElementInfo(project, psiFile, properTextRange, psiFile, SmartPointerManager.getInstance(project).createSmartPsiElementPointer(injectionHost));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPsiElementPointerImpl, org.jetbrains.kotlin.com.intellij.psi.SmartPsiElementPointer
    public PsiFile getContainingFile() {
        return getElementInfo().restoreFile(this.myManager);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPsiElementPointerImpl, org.jetbrains.kotlin.com.intellij.psi.SmartPsiElementPointer
    public PsiFile getElement() {
        if (getRange() == null) {
            return null;
        }
        return getContainingFile();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPsiElementPointerImpl
    public String toString() {
        return "SmartPsiFileRangePointerImpl{" + getElementInfo() + "}";
    }
}
