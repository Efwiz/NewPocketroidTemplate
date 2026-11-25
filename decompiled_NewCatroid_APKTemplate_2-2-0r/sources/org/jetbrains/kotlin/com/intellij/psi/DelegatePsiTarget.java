package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.ide.util.PsiNavigationSupport;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.pom.PsiDeclaredTarget;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;

/* JADX INFO: loaded from: classes3.dex */
public class DelegatePsiTarget implements PsiTarget {
    private final PsiElement myElement;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/DelegatePsiTarget";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/DelegatePsiTarget";
        } else {
            objArr[1] = "getNavigationElement";
        }
        if (i != 1) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public DelegatePsiTarget(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        this.myElement = psiElement.getNavigationElement();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getTextOffset() {
        TextRange nameIdentifierRange;
        if ((this instanceof PsiDeclaredTarget) && (nameIdentifierRange = ((PsiDeclaredTarget) this).getNameIdentifierRange()) != null) {
            return nameIdentifierRange.getStartOffset() + this.myElement.getTextRange().getStartOffset();
        }
        return this.myElement.getTextOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.pom.Navigatable
    public void navigate(boolean z) {
        int textOffset = getTextOffset();
        VirtualFile virtualFile = PsiUtilCore.getVirtualFile(this.myElement);
        if (virtualFile == null || !virtualFile.isValid()) {
            return;
        }
        PsiNavigationSupport.getInstance().createNavigatable(this.myElement.getProject(), virtualFile, textOffset).navigate(z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.pom.Navigatable
    public boolean canNavigate() {
        return PsiNavigationSupport.getInstance().canNavigate(this.myElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.pom.Navigatable
    public boolean canNavigateToSource() {
        return PsiNavigationSupport.getInstance().canNavigate(this.myElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTarget
    public final PsiElement getNavigationElement() {
        PsiElement psiElement = this.myElement;
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        return psiElement;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.myElement.equals(((DelegatePsiTarget) obj).myElement);
    }

    public int hashCode() {
        return this.myElement.hashCode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        return getNavigationElement().isValid();
    }
}
