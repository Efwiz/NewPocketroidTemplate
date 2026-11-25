package org.jetbrains.kotlin.com.intellij.pom;

import org.jetbrains.kotlin.com.intellij.pom.event.PomModelEvent;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public interface PomTransaction {
    PomModelEvent getAccumulatedEvent();

    PsiElement getChangeScope();

    void run() throws IncorrectOperationException;
}
