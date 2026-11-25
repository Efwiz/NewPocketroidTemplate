package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiManager extends UserDataHolderBase {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "project";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiManager";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiManager";
        } else {
            objArr[1] = "getInstance";
        }
        if (i != 1) {
            objArr[2] = "getInstance";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    @Deprecated
    public abstract void addPsiTreeChangeListener(PsiTreeChangeListener psiTreeChangeListener);

    public abstract void addPsiTreeChangeListener(PsiTreeChangeListener psiTreeChangeListener, Disposable disposable);

    public abstract boolean areElementsEquivalent(PsiElement psiElement, PsiElement psiElement2);

    public abstract void dropPsiCaches();

    public abstract void dropResolveCaches();

    public abstract PsiDirectory findDirectory(VirtualFile virtualFile);

    public abstract PsiFile findFile(VirtualFile virtualFile);

    public abstract FileViewProvider findViewProvider(VirtualFile virtualFile);

    public abstract void finishBatchFilesProcessingMode();

    public abstract PsiModificationTracker getModificationTracker();

    public abstract Project getProject();

    public abstract boolean isDisposed();

    public abstract boolean isInProject(PsiElement psiElement);

    public abstract void reloadFromDisk(PsiFile psiFile);

    public abstract void removePsiTreeChangeListener(PsiTreeChangeListener psiTreeChangeListener);

    public abstract void startBatchFilesProcessingMode();

    public static PsiManager getInstance(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        PsiManager psiManager = (PsiManager) project.getService(PsiManager.class);
        if (psiManager == null) {
            $$$reportNull$$$0(1);
        }
        return psiManager;
    }
}
