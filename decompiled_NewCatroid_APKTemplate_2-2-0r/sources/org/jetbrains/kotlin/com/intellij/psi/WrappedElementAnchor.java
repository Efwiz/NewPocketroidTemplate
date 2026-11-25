package org.jetbrains.kotlin.com.intellij.psi;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerAnchorProvider;

/* JADX INFO: loaded from: classes3.dex */
public class WrappedElementAnchor extends PsiAnchor {
    private final SmartPointerAnchorProvider myAnchorProvider;
    private final PsiAnchor myBaseAnchor;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = Constants.EXTRA_PROVIDER;
        } else {
            objArr[0] = "anchor";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/WrappedElementAnchor";
        objArr[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public WrappedElementAnchor(SmartPointerAnchorProvider smartPointerAnchorProvider, PsiAnchor psiAnchor) {
        if (smartPointerAnchorProvider == null) {
            $$$reportNull$$$0(0);
        }
        if (psiAnchor == null) {
            $$$reportNull$$$0(1);
        }
        this.myAnchorProvider = smartPointerAnchorProvider;
        this.myBaseAnchor = psiAnchor;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnchor
    public PsiElement retrieve() {
        PsiElement psiElementRetrieve = this.myBaseAnchor.retrieve();
        if (psiElementRetrieve == null) {
            return null;
        }
        return this.myAnchorProvider.restoreElement(psiElementRetrieve);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnchor
    public PsiFile getFile() {
        PsiElement psiElementRetrieve = retrieve();
        if (psiElementRetrieve == null) {
            return null;
        }
        return psiElementRetrieve.getContainingFile();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnchor
    public int getStartOffset() {
        PsiElement psiElementRetrieve = retrieve();
        if (psiElementRetrieve == null || psiElementRetrieve.getTextRange() == null) {
            return -1;
        }
        return psiElementRetrieve.getTextRange().getStartOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnchor
    public int getEndOffset() {
        PsiElement psiElementRetrieve = retrieve();
        if (psiElementRetrieve == null || psiElementRetrieve.getTextRange() == null) {
            return -1;
        }
        return psiElementRetrieve.getTextRange().getEndOffset();
    }

    public String toString() {
        return "WrappedElementAnchor(" + this.myBaseAnchor + "; provider=" + this.myAnchorProvider + Constant.AFTER_QUTO;
    }
}
