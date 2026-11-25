package org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers;

import org.jetbrains.kotlin.com.intellij.openapi.util.Segment;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnchor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;

/* JADX INFO: loaded from: classes3.dex */
class ClsElementInfo extends SmartPointerElementInfo {
    private final PsiAnchor.StubIndexReference myStubIndexReference;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 4 ? 3 : 2];
        switch (i) {
            case 1:
            case 3:
            case 5:
            case 6:
            case 7:
                objArr[0] = "manager";
                break;
            case 2:
                objArr[0] = "other";
                break;
            case 4:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/ClsElementInfo";
                break;
            default:
                objArr[0] = "stubReference";
                break;
        }
        if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/ClsElementInfo";
        } else {
            objArr[1] = "getVirtualFile";
        }
        switch (i) {
            case 1:
                objArr[2] = "restoreElement";
                break;
            case 2:
            case 3:
                objArr[2] = "pointsToTheSameElementAs";
                break;
            case 4:
                break;
            case 5:
                objArr[2] = "getRange";
                break;
            case 6:
                objArr[2] = "getPsiRange";
                break;
            case 7:
                objArr[2] = "restoreFile";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 4) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    Segment getPsiRange(SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerManagerImpl != null) {
            return null;
        }
        $$$reportNull$$$0(6);
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    Segment getRange(SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerManagerImpl != null) {
            return null;
        }
        $$$reportNull$$$0(5);
        return null;
    }

    ClsElementInfo(PsiAnchor.StubIndexReference stubIndexReference) {
        if (stubIndexReference == null) {
            $$$reportNull$$$0(0);
        }
        this.myStubIndexReference = stubIndexReference;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    PsiElement restoreElement(SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(1);
        }
        return this.myStubIndexReference.retrieve();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    int elementHashCode() {
        return this.myStubIndexReference.hashCode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    boolean pointsToTheSameElementAs(SmartPointerElementInfo smartPointerElementInfo, SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerElementInfo == null) {
            $$$reportNull$$$0(2);
        }
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(3);
        }
        return (smartPointerElementInfo instanceof ClsElementInfo) && this.myStubIndexReference.equals(((ClsElementInfo) smartPointerElementInfo).myStubIndexReference);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    VirtualFile getVirtualFile() {
        VirtualFile virtualFile = this.myStubIndexReference.getVirtualFile();
        if (virtualFile == null) {
            $$$reportNull$$$0(4);
        }
        return virtualFile;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    PsiFile restoreFile(SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(7);
        }
        return this.myStubIndexReference.getFile();
    }

    public String toString() {
        return this.myStubIndexReference.toString();
    }
}
