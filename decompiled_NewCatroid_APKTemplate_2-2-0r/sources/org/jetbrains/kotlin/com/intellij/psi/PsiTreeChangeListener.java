package org.jetbrains.kotlin.com.intellij.psi;

import java.util.EventListener;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ProjectExtensionPointName;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiTreeChangeListener extends EventListener {
    public static final ProjectExtensionPointName<PsiTreeChangeListener> EP = new ProjectExtensionPointName<>("org.jetbrains.kotlin.com.intellij.psi.treeChangeListener");

    void beforeChildAddition(PsiTreeChangeEvent psiTreeChangeEvent);

    void beforeChildMovement(PsiTreeChangeEvent psiTreeChangeEvent);

    void beforeChildRemoval(PsiTreeChangeEvent psiTreeChangeEvent);

    void beforeChildReplacement(PsiTreeChangeEvent psiTreeChangeEvent);

    void beforeChildrenChange(PsiTreeChangeEvent psiTreeChangeEvent);

    void beforePropertyChange(PsiTreeChangeEvent psiTreeChangeEvent);

    void childAdded(PsiTreeChangeEvent psiTreeChangeEvent);

    void childMoved(PsiTreeChangeEvent psiTreeChangeEvent);

    void childRemoved(PsiTreeChangeEvent psiTreeChangeEvent);

    void childReplaced(PsiTreeChangeEvent psiTreeChangeEvent);

    void childrenChanged(PsiTreeChangeEvent psiTreeChangeEvent);

    void propertyChanged(PsiTreeChangeEvent psiTreeChangeEvent);
}
