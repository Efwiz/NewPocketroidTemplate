package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiTreeAnyChangeAbstractAdapter extends PsiTreeChangeAdapter {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "event";
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiTreeAnyChangeAbstractAdapter";
        if (i == 1) {
            objArr[2] = "childRemoved";
        } else if (i == 2) {
            objArr[2] = "childReplaced";
        } else if (i == 3) {
            objArr[2] = "childMoved";
        } else if (i == 4) {
            objArr[2] = "childrenChanged";
        } else if (i != 5) {
            objArr[2] = "childAdded";
        } else {
            objArr[2] = "propertyChanged";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    protected abstract void onChange(PsiFile psiFile);

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeAdapter, org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeListener
    public void childAdded(PsiTreeChangeEvent psiTreeChangeEvent) {
        if (psiTreeChangeEvent == null) {
            $$$reportNull$$$0(0);
        }
        onChange(psiTreeChangeEvent.getFile());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeAdapter, org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeListener
    public void childRemoved(PsiTreeChangeEvent psiTreeChangeEvent) {
        if (psiTreeChangeEvent == null) {
            $$$reportNull$$$0(1);
        }
        onChange(psiTreeChangeEvent.getFile());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeAdapter, org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeListener
    public void childReplaced(PsiTreeChangeEvent psiTreeChangeEvent) {
        if (psiTreeChangeEvent == null) {
            $$$reportNull$$$0(2);
        }
        onChange(psiTreeChangeEvent.getFile());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeAdapter, org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeListener
    public void childMoved(PsiTreeChangeEvent psiTreeChangeEvent) {
        if (psiTreeChangeEvent == null) {
            $$$reportNull$$$0(3);
        }
        onChange(psiTreeChangeEvent.getFile());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeAdapter, org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeListener
    public void childrenChanged(PsiTreeChangeEvent psiTreeChangeEvent) {
        if (psiTreeChangeEvent == null) {
            $$$reportNull$$$0(4);
        }
        onChange(psiTreeChangeEvent.getFile());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeAdapter, org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeListener
    public void propertyChanged(PsiTreeChangeEvent psiTreeChangeEvent) {
        if (psiTreeChangeEvent == null) {
            $$$reportNull$$$0(5);
        }
        onChange(psiTreeChangeEvent.getFile());
    }
}
