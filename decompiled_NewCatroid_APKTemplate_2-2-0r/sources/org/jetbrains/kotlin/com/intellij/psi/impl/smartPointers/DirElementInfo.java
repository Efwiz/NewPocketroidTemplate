package org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers;

import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Segment;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;

/* JADX INFO: loaded from: classes3.dex */
class DirElementInfo extends SmartPointerElementInfo {
    private final Project myProject;
    private final VirtualFile myVirtualFile;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 5 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 5 ? 3 : 2];
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 6:
            case 7:
                objArr[0] = "manager";
                break;
            case 3:
                objArr[0] = "other";
                break;
            case 5:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/DirElementInfo";
                break;
            default:
                objArr[0] = "directory";
                break;
        }
        if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/DirElementInfo";
        } else {
            objArr[1] = "getVirtualFile";
        }
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
                break;
            case 6:
                objArr[2] = "getRange";
                break;
            case 7:
                objArr[2] = "getPsiRange";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 5) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    Segment getPsiRange(SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerManagerImpl != null) {
            return null;
        }
        $$$reportNull$$$0(7);
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    Segment getRange(SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerManagerImpl != null) {
            return null;
        }
        $$$reportNull$$$0(6);
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    PsiFile restoreFile(SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerManagerImpl != null) {
            return null;
        }
        $$$reportNull$$$0(2);
        return null;
    }

    DirElementInfo(PsiDirectory psiDirectory) {
        if (psiDirectory == null) {
            $$$reportNull$$$0(0);
        }
        this.myProject = psiDirectory.getProject();
        this.myVirtualFile = psiDirectory.getVirtualFile();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    PsiElement restoreElement(SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(1);
        }
        return SelfElementInfo.restoreDirectoryFromVirtual(this.myVirtualFile, this.myProject);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    int elementHashCode() {
        return this.myVirtualFile.hashCode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    boolean pointsToTheSameElementAs(SmartPointerElementInfo smartPointerElementInfo, SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerElementInfo == null) {
            $$$reportNull$$$0(3);
        }
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(4);
        }
        return (smartPointerElementInfo instanceof DirElementInfo) && Comparing.equal(this.myVirtualFile, ((DirElementInfo) smartPointerElementInfo).myVirtualFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerElementInfo
    VirtualFile getVirtualFile() {
        VirtualFile virtualFile = this.myVirtualFile;
        if (virtualFile == null) {
            $$$reportNull$$$0(5);
        }
        return virtualFile;
    }

    public String toString() {
        return "dir{" + this.myVirtualFile + "}";
    }
}
