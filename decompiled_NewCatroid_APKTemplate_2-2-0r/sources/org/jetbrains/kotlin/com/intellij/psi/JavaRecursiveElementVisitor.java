package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.util.containers.Stack;

/* JADX INFO: loaded from: classes3.dex */
public abstract class JavaRecursiveElementVisitor extends JavaElementVisitor implements PsiRecursiveVisitor {
    private final Stack<PsiReferenceExpression> myRefExprsInVisit = new Stack<>();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "org/jetbrains/kotlin/com/intellij/psi/JavaRecursiveElementVisitor", "visitElement"));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
    public void visitElement(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        if (!this.myRefExprsInVisit.isEmpty() && this.myRefExprsInVisit.peek() == psiElement) {
            this.myRefExprsInVisit.pop();
            this.myRefExprsInVisit.push(null);
        } else {
            psiElement.acceptChildren(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitReferenceExpression(PsiReferenceExpression psiReferenceExpression) {
        this.myRefExprsInVisit.push(psiReferenceExpression);
        try {
            visitExpression(psiReferenceExpression);
            visitReferenceElement(psiReferenceExpression);
        } finally {
            this.myRefExprsInVisit.pop();
        }
    }
}
