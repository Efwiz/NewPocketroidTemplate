package org.jetbrains.kotlin.com.intellij.psi.templateLanguages;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;

/* JADX INFO: loaded from: classes3.dex */
public final class TemplateLanguageUtil {
    /* JADX WARN: Code duplicated, block: B:13:0x001c  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else if (i == 3 || i == 4) {
            objArr[0] = "node";
        } else if (i == 5 || i == 6) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "file";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/templateLanguages/TemplateLanguageUtil";
        switch (i) {
            case 1:
                objArr[2] = "isInsideTemplateFile";
                break;
            case 2:
                objArr[2] = "isTemplateDataFile";
                break;
            case 3:
            case 5:
                objArr[2] = "getSameLanguageTreePrev";
                break;
            case 4:
            case 6:
                objArr[2] = "getSameLanguageTreeNext";
                break;
            default:
                objArr[2] = "getBaseFile";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private TemplateLanguageUtil() {
    }

    public static PsiFile getTemplateFile(PsiFile psiFile) {
        FileViewProvider viewProvider = psiFile.getViewProvider();
        if (viewProvider instanceof TemplateLanguageFileViewProvider) {
            return viewProvider.getPsi(((TemplateLanguageFileViewProvider) viewProvider).getTemplateDataLanguage());
        }
        return null;
    }

    public static PsiFile getBaseFile(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(0);
        }
        FileViewProvider viewProvider = psiFile.getViewProvider();
        return viewProvider.getPsi(viewProvider.getBaseLanguage());
    }

    public static boolean isInsideTemplateFile(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        return psiElement.getContainingFile().getViewProvider() instanceof TemplateLanguageFileViewProvider;
    }

    public static boolean isTemplateDataFile(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(2);
        }
        FileViewProvider viewProvider = psiFile.getViewProvider();
        return (viewProvider instanceof TemplateLanguageFileViewProvider) && psiFile == viewProvider.getPsi(((TemplateLanguageFileViewProvider) viewProvider).getTemplateDataLanguage());
    }

    public static ASTNode getSameLanguageTreePrev(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(3);
        }
        ASTNode treePrev = aSTNode.getTreePrev();
        while (treePrev instanceof OuterLanguageElement) {
            treePrev = treePrev.getTreePrev();
        }
        return treePrev;
    }

    public static ASTNode getSameLanguageTreeNext(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(4);
        }
        ASTNode treeNext = aSTNode.getTreeNext();
        while (treeNext instanceof OuterLanguageElement) {
            treeNext = treeNext.getTreeNext();
        }
        return treeNext;
    }

    public static PsiElement getSameLanguageTreePrev(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        PsiElement nextSibling = psiElement.getNextSibling();
        while (nextSibling instanceof OuterLanguageElement) {
            nextSibling = nextSibling.getPrevSibling();
        }
        return nextSibling;
    }

    public static PsiElement getSameLanguageTreeNext(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(6);
        }
        PsiElement nextSibling = psiElement.getNextSibling();
        while (nextSibling instanceof OuterLanguageElement) {
            nextSibling = nextSibling.getNextSibling();
        }
        return nextSibling;
    }
}
