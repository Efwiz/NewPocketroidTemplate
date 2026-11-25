package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiTreeChangeAdapter implements PsiTreeChangeListener {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "event";
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiTreeChangeAdapter";
        switch (i) {
            case 1:
                objArr[2] = "beforeChildRemoval";
                break;
            case 2:
                objArr[2] = "beforeChildReplacement";
                break;
            case 3:
                objArr[2] = "beforeChildMovement";
                break;
            case 4:
                objArr[2] = "beforeChildrenChange";
                break;
            case 5:
                objArr[2] = "beforePropertyChange";
                break;
            case 6:
                objArr[2] = "childAdded";
                break;
            case 7:
                objArr[2] = "childRemoved";
                break;
            case 8:
                objArr[2] = "childReplaced";
                break;
            case 9:
                objArr[2] = "childMoved";
                break;
            case 10:
                objArr[2] = "childrenChanged";
                break;
            case 11:
                objArr[2] = "propertyChanged";
                break;
            default:
                objArr[2] = "beforeChildAddition";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeListener
    public void beforeChildAddition(PsiTreeChangeEvent psiTreeChangeEvent) {
        if (psiTreeChangeEvent == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeListener
    public void beforeChildMovement(PsiTreeChangeEvent psiTreeChangeEvent) {
        if (psiTreeChangeEvent == null) {
            $$$reportNull$$$0(3);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeListener
    public void beforeChildRemoval(PsiTreeChangeEvent psiTreeChangeEvent) {
        if (psiTreeChangeEvent == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeListener
    public void beforeChildReplacement(PsiTreeChangeEvent psiTreeChangeEvent) {
        if (psiTreeChangeEvent == null) {
            $$$reportNull$$$0(2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeListener
    public void beforeChildrenChange(PsiTreeChangeEvent psiTreeChangeEvent) {
        if (psiTreeChangeEvent == null) {
            $$$reportNull$$$0(4);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeListener
    public void beforePropertyChange(PsiTreeChangeEvent psiTreeChangeEvent) {
        if (psiTreeChangeEvent == null) {
            $$$reportNull$$$0(5);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeListener
    public void childAdded(PsiTreeChangeEvent psiTreeChangeEvent) {
        if (psiTreeChangeEvent == null) {
            $$$reportNull$$$0(6);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeListener
    public void childMoved(PsiTreeChangeEvent psiTreeChangeEvent) {
        if (psiTreeChangeEvent == null) {
            $$$reportNull$$$0(9);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeListener
    public void childRemoved(PsiTreeChangeEvent psiTreeChangeEvent) {
        if (psiTreeChangeEvent == null) {
            $$$reportNull$$$0(7);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeListener
    public void childReplaced(PsiTreeChangeEvent psiTreeChangeEvent) {
        if (psiTreeChangeEvent == null) {
            $$$reportNull$$$0(8);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeListener
    public void childrenChanged(PsiTreeChangeEvent psiTreeChangeEvent) {
        if (psiTreeChangeEvent == null) {
            $$$reportNull$$$0(10);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeListener
    public void propertyChanged(PsiTreeChangeEvent psiTreeChangeEvent) {
        if (psiTreeChangeEvent == null) {
            $$$reportNull$$$0(11);
        }
    }
}
