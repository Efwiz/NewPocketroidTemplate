package org.jetbrains.kotlin.com.intellij.psi;

import java.util.List;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicatorProvider;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiRecursiveElementVisitor extends PsiElementVisitor implements PsiRecursiveVisitor {
    private final boolean myVisitAllFileRoots;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "file";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiRecursiveElementVisitor";
        if (i != 1) {
            objArr[2] = "visitElement";
        } else {
            objArr[2] = "visitFile";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    protected PsiRecursiveElementVisitor() {
        this(false);
    }

    protected PsiRecursiveElementVisitor(boolean z) {
        this.myVisitAllFileRoots = z;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
    public void visitElement(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        ProgressIndicatorProvider.checkCanceled();
        psiElement.acceptChildren(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
    public void visitFile(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(1);
        }
        if (this.myVisitAllFileRoots) {
            FileViewProvider viewProvider = psiFile.getViewProvider();
            List<PsiFile> allFiles = viewProvider.getAllFiles();
            if (allFiles.size() > 1 && psiFile == viewProvider.getPsi(viewProvider.getBaseLanguage())) {
                for (PsiFile psiFile2 : allFiles) {
                    ProgressIndicatorProvider.checkCanceled();
                    psiFile2.acceptChildren(this);
                }
                return;
            }
        }
        super.visitFile(psiFile);
    }
}
