package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;

/* JADX INFO: loaded from: classes3.dex */
public abstract class JavaRecursiveElementWalkingVisitor extends JavaElementVisitor implements PsiRecursiveVisitor {
    private final PsiWalkingState myWalkingState = new PsiWalkingState(this) { // from class: org.jetbrains.kotlin.com.intellij.psi.JavaRecursiveElementWalkingVisitor.1
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "org/jetbrains/kotlin/com/intellij/psi/JavaRecursiveElementWalkingVisitor$1", "elementFinished"));
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.WalkingState
        public void elementFinished(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(0);
            }
            JavaRecursiveElementWalkingVisitor.this.elementFinished(psiElement);
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/JavaRecursiveElementWalkingVisitor";
        if (i != 1) {
            objArr[2] = "visitElement";
        } else {
            objArr[2] = "elementFinished";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    protected void elementFinished(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
    public void visitElement(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        this.myWalkingState.elementStarted(psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitReferenceExpression(PsiReferenceExpression psiReferenceExpression) {
        visitExpression(psiReferenceExpression);
        this.myWalkingState.startedWalking();
        visitReferenceElement(psiReferenceExpression);
    }

    public void stopWalking() {
        this.myWalkingState.stopWalking();
    }
}
