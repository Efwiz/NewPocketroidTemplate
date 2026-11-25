package org.jetbrains.kotlin.com.intellij.model;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.injected.editor.VirtualFileWindow;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;

/* JADX INFO: loaded from: classes3.dex */
public interface ModelBranch extends UserDataHolder {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "action";
        } else if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/model/ModelBranch";
        } else if (i == 3) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else if (i != 4) {
            objArr[0] = "project";
        } else {
            objArr[0] = "file";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/model/ModelBranch";
        } else {
            objArr[1] = "performInBranch";
        }
        if (i != 2) {
            if (i == 3) {
                objArr[2] = "getPsiBranch";
            } else if (i != 4) {
                objArr[2] = "performInBranch";
            } else {
                objArr[2] = "getFileBranch";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    long getBranchedPsiModificationCount();

    Project getProject();

    <T extends PsiElement> T obtainPsiCopy(T t);

    static ModelBranch getPsiBranch(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(3);
        }
        if (psiElement instanceof PsiDirectory) {
            return getFileBranch(((PsiDirectory) psiElement).getVirtualFile());
        }
        if (psiElement instanceof BranchableSyntheticPsiElement) {
            return ((BranchableSyntheticPsiElement) psiElement).getModelBranch();
        }
        PsiFile containingFile = psiElement instanceof PsiFile ? (PsiFile) psiElement : psiElement.getContainingFile();
        if (containingFile == null) {
            return null;
        }
        return getFileBranch(containingFile.getViewProvider().getVirtualFile());
    }

    /* JADX WARN: Multi-variable type inference failed */
    static ModelBranch getFileBranch(VirtualFile virtualFile) {
        if (virtualFile == 0) {
            $$$reportNull$$$0(4);
        }
        boolean z = virtualFile instanceof VirtualFileWindow;
        Object delegate = virtualFile;
        if (z) {
            delegate = ((VirtualFileWindow) virtualFile).getDelegate();
        }
        if (delegate instanceof BranchedVirtualFile) {
            return ((BranchedVirtualFile) delegate).getBranch();
        }
        return null;
    }
}
