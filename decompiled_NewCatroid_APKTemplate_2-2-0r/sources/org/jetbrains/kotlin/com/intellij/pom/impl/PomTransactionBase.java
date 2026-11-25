package org.jetbrains.kotlin.com.intellij.pom.impl;

import org.jetbrains.kotlin.com.intellij.pom.PomManager;
import org.jetbrains.kotlin.com.intellij.pom.PomTransaction;
import org.jetbrains.kotlin.com.intellij.pom.event.PomModelEvent;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PomTransactionBase implements PomTransaction {
    private final PomModelEvent myAccumulatedEvent;
    private final PsiElement myScope;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/pom/impl/PomTransactionBase";
        } else {
            objArr[0] = "scope";
        }
        if (i == 1) {
            objArr[1] = "getAccumulatedEvent";
        } else if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/pom/impl/PomTransactionBase";
        } else {
            objArr[1] = "getChangeScope";
        }
        if (i != 1 && i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public abstract PomModelEvent runInner() throws IncorrectOperationException;

    public PomTransactionBase(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        this.myScope = psiElement;
        this.myAccumulatedEvent = new PomModelEvent(PomManager.getModel(psiElement.getProject()), null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.pom.PomTransaction
    public PomModelEvent getAccumulatedEvent() {
        PomModelEvent pomModelEvent = this.myAccumulatedEvent;
        if (pomModelEvent == null) {
            $$$reportNull$$$0(1);
        }
        return pomModelEvent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.pom.PomTransaction
    public void run() throws IncorrectOperationException {
        this.myAccumulatedEvent.merge(runInner());
    }

    @Override // org.jetbrains.kotlin.com.intellij.pom.PomTransaction
    public PsiElement getChangeScope() {
        PsiElement psiElement = this.myScope;
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        return psiElement;
    }
}
