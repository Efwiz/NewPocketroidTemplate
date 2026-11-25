package org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.Segment;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;

/* JADX INFO: loaded from: classes3.dex */
class HardElementInfo extends SmartPointerElementInfo {
    private final PsiElement myElement;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
                objArr[0] = "manager";
                break;
            case 3:
                objArr[0] = "other";
                break;
            default:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/HardElementInfo";
        switch (i) {
            case 1:
                objArr[2] = "restoreElement";
                break;
            case 2:
                objArr[2] = "restoreFile";
                break;
            case 3:
            case 4:
                objArr[2] = "pointsToTheSameElementAs";
                break;
            case 5:
                objArr[2] = "getRange";
                break;
            case 6:
                objArr[2] = "getPsiRange";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    HardElementInfo(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        this.myElement = psiElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    PsiElement restoreElement(SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(1);
        }
        return this.myElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    PsiFile restoreFile(SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(2);
        }
        if (this.myElement.isValid()) {
            return this.myElement.getContainingFile();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    int elementHashCode() {
        return this.myElement.hashCode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    boolean pointsToTheSameElementAs(SmartPointerElementInfo smartPointerElementInfo, SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerElementInfo == null) {
            $$$reportNull$$$0(3);
        }
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(4);
        }
        return (smartPointerElementInfo instanceof HardElementInfo) && this.myElement.equals(((HardElementInfo) smartPointerElementInfo).myElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    VirtualFile getVirtualFile() {
        return PsiUtilCore.getVirtualFile(this.myElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    Segment getRange(SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(5);
        }
        return this.myElement.getTextRange();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    Segment getPsiRange(SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(6);
        }
        return getRange(smartPointerManagerImpl);
    }

    public String toString() {
        return "hard{" + this.myElement + " of " + this.myElement.getClass() + "}";
    }
}
