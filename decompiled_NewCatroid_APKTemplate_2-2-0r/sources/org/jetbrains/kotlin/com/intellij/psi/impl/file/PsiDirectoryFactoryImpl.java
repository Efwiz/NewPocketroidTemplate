package org.jetbrains.kotlin.com.intellij.psi.impl.file;

import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectoryContainer;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerImpl;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiDirectoryFactoryImpl extends PsiDirectoryFactory {
    private final Project myProject;

    /* JADX WARN: Code duplicated, block: B:18:0x0028  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 3 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "file";
        } else if (i == 2) {
            objArr[0] = "directory";
        } else if (i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/file/PsiDirectoryFactoryImpl";
        } else if (i == 4 || i == 5) {
            objArr[0] = "directory";
        } else {
            objArr[0] = "project";
        }
        if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/file/PsiDirectoryFactoryImpl";
        } else {
            objArr[1] = "getQualifiedName";
        }
        if (i == 1) {
            objArr[2] = "createDirectory";
        } else if (i == 2) {
            objArr[2] = "getQualifiedName";
        } else if (i != 3) {
            if (i == 4) {
                objArr[2] = "getDirectoryContainer";
            } else if (i != 5) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "isPackage";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiDirectoryFactory
    public PsiDirectoryContainer getDirectoryContainer(PsiDirectory psiDirectory) {
        if (psiDirectory != null) {
            return null;
        }
        $$$reportNull$$$0(4);
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiDirectoryFactory
    public boolean isPackage(PsiDirectory psiDirectory) {
        if (psiDirectory != null) {
            return false;
        }
        $$$reportNull$$$0(5);
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiDirectoryFactory
    public boolean isValidPackageName(String str) {
        return true;
    }

    public PsiDirectoryFactoryImpl(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        this.myProject = project;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiDirectoryFactory
    public PsiDirectory createDirectory(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(1);
        }
        return new PsiDirectoryImpl((PsiManagerImpl) PsiManager.getInstance(this.myProject), virtualFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiDirectoryFactory
    public String getQualifiedName(PsiDirectory psiDirectory, boolean z) {
        if (psiDirectory == null) {
            $$$reportNull$$$0(2);
        }
        if (z) {
            String locationRelativeToUserHome = FileUtil.getLocationRelativeToUserHome(psiDirectory.getVirtualFile().getPresentableUrl());
            if (locationRelativeToUserHome == null) {
                $$$reportNull$$$0(3);
            }
            return locationRelativeToUserHome;
        }
        return "";
    }
}
