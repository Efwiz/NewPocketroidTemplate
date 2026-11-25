package org.jetbrains.kotlin.com.intellij.psi;

import com.google.firebase.analytics.FirebaseAnalytics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicatorProvider;
import org.jetbrains.kotlin.com.intellij.psi.templateLanguages.OuterLanguageElement;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiElementVisitor {
    public static final PsiElementVisitor EMPTY_VISITOR = new PsiElementVisitor() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor.1
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 2:
            case 3:
                objArr[0] = "file";
                break;
            case 4:
            default:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 5:
                objArr[0] = FirebaseAnalytics.Param.CONTENT;
                break;
            case 6:
                objArr[0] = "dir";
                break;
            case 7:
                objArr[0] = "comment";
                break;
            case 8:
                objArr[0] = "space";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiElementVisitor";
        switch (i) {
            case 1:
                objArr[2] = "visitFile";
                break;
            case 2:
                objArr[2] = "visitBinaryFile";
                break;
            case 3:
                objArr[2] = "visitPlainTextFile";
                break;
            case 4:
                objArr[2] = "visitErrorElement";
                break;
            case 5:
                objArr[2] = "visitPlainText";
                break;
            case 6:
                objArr[2] = "visitDirectory";
                break;
            case 7:
                objArr[2] = "visitComment";
                break;
            case 8:
                objArr[2] = "visitWhiteSpace";
                break;
            case 9:
                objArr[2] = "visitOuterLanguageElement";
                break;
            default:
                objArr[2] = "visitElement";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public void visitElement(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        ProgressIndicatorProvider.checkCanceled();
    }

    public void visitFile(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(1);
        }
        visitElement(psiFile);
    }

    public void visitBinaryFile(PsiBinaryFile psiBinaryFile) {
        if (psiBinaryFile == null) {
            $$$reportNull$$$0(2);
        }
        visitFile(psiBinaryFile);
    }

    public void visitPlainTextFile(PsiPlainTextFile psiPlainTextFile) {
        if (psiPlainTextFile == null) {
            $$$reportNull$$$0(3);
        }
        visitFile(psiPlainTextFile);
    }

    public void visitErrorElement(PsiErrorElement psiErrorElement) {
        if (psiErrorElement == null) {
            $$$reportNull$$$0(4);
        }
        visitElement(psiErrorElement);
    }

    public void visitPlainText(PsiPlainText psiPlainText) {
        if (psiPlainText == null) {
            $$$reportNull$$$0(5);
        }
        visitElement(psiPlainText);
    }

    public void visitDirectory(PsiDirectory psiDirectory) {
        if (psiDirectory == null) {
            $$$reportNull$$$0(6);
        }
        visitElement(psiDirectory);
    }

    public void visitComment(PsiComment psiComment) {
        if (psiComment == null) {
            $$$reportNull$$$0(7);
        }
        visitElement(psiComment);
    }

    public void visitWhiteSpace(PsiWhiteSpace psiWhiteSpace) {
        if (psiWhiteSpace == null) {
            $$$reportNull$$$0(8);
        }
        visitElement(psiWhiteSpace);
    }

    public void visitOuterLanguageElement(OuterLanguageElement outerLanguageElement) {
        if (outerLanguageElement == null) {
            $$$reportNull$$$0(9);
        }
        visitElement(outerLanguageElement);
    }
}
