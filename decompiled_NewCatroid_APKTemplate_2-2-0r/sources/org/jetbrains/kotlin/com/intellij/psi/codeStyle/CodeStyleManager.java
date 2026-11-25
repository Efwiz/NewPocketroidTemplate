package org.jetbrains.kotlin.com.intellij.psi.codeStyle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.formatting.FormattingMode;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable;

/* JADX INFO: loaded from: classes3.dex */
public abstract class CodeStyleManager {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 13 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 13 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "manager";
                break;
            case 2:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 12:
            case 14:
                objArr[0] = "file";
                break;
            case 3:
                objArr[0] = "ranges";
                break;
            case 4:
                objArr[0] = "document";
                break;
            case 9:
            default:
                objArr[0] = "project";
                break;
            case 11:
                objArr[0] = "runnable";
                break;
            case 13:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/codeStyle/CodeStyleManager";
                break;
        }
        if (i != 13) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/codeStyle/CodeStyleManager";
        } else {
            objArr[1] = "getDocCommentSettings";
        }
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "reformatTextWithContext";
                break;
            case 4:
                objArr[2] = "scheduleIndentAdjustment";
                break;
            case 5:
                objArr[2] = "getLineIndents";
                break;
            case 6:
                objArr[2] = "getLineIndent";
                break;
            case 7:
                objArr[2] = "getSpacing";
                break;
            case 8:
                objArr[2] = "getMinLineFeeds";
                break;
            case 9:
                objArr[2] = "getCurrentFormattingMode";
                break;
            case 10:
            case 11:
                objArr[2] = "runWithDocCommentFormattingDisabled";
                break;
            case 12:
                objArr[2] = "getDocCommentSettings";
                break;
            case 13:
                break;
            case 14:
                objArr[2] = "scheduleReformatWhenSettingsComputed";
                break;
            default:
                objArr[2] = "getInstance";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 13) {
            throw new IllegalStateException(str2);
        }
    }

    public abstract int adjustLineIndent(Document document, int i);

    public abstract int adjustLineIndent(PsiFile psiFile, int i) throws IncorrectOperationException;

    public abstract void adjustLineIndent(PsiFile psiFile, TextRange textRange) throws IncorrectOperationException;

    @Deprecated
    public abstract String fillIndent(Indent indent, FileType fileType);

    @Deprecated
    public abstract Indent getIndent(String str, FileType fileType);

    public abstract String getLineIndent(Document document, int i);

    public abstract String getLineIndent(PsiFile psiFile, int i);

    public List<String> getLineIndents(PsiFile psiFile) {
        if (psiFile != null) {
            return null;
        }
        $$$reportNull$$$0(5);
        return null;
    }

    public int getMinLineFeeds(PsiFile psiFile, int i) {
        if (psiFile != null) {
            return -1;
        }
        $$$reportNull$$$0(8);
        return -1;
    }

    public abstract Project getProject();

    public int getSpacing(PsiFile psiFile, int i) {
        if (psiFile != null) {
            return -1;
        }
        $$$reportNull$$$0(7);
        return -1;
    }

    @Deprecated
    public abstract boolean isLineToBeIndented(PsiFile psiFile, int i);

    public abstract boolean isSequentialProcessingAllowed();

    public abstract <T> T performActionWithFormatterDisabled(Computable<T> computable);

    public abstract void performActionWithFormatterDisabled(Runnable runnable);

    public abstract <T extends Throwable> void performActionWithFormatterDisabled(ThrowableRunnable<T> throwableRunnable) throws Throwable;

    public abstract PsiElement reformat(PsiElement psiElement) throws IncorrectOperationException;

    public abstract PsiElement reformat(PsiElement psiElement, boolean z) throws IncorrectOperationException;

    public abstract void reformatNewlyAddedElement(ASTNode aSTNode, ASTNode aSTNode2) throws IncorrectOperationException;

    public abstract PsiElement reformatRange(PsiElement psiElement, int i, int i2) throws IncorrectOperationException;

    public abstract PsiElement reformatRange(PsiElement psiElement, int i, int i2, boolean z) throws IncorrectOperationException;

    public abstract void reformatText(PsiFile psiFile, int i, int i2) throws IncorrectOperationException;

    public abstract void reformatText(PsiFile psiFile, Collection<? extends TextRange> collection) throws IncorrectOperationException;

    public abstract void reformatTextWithContext(PsiFile psiFile, ChangedRangesInfo changedRangesInfo) throws IncorrectOperationException;

    public void scheduleIndentAdjustment(Document document, int i) {
        if (document == null) {
            $$$reportNull$$$0(4);
        }
    }

    @Deprecated
    public abstract Indent zeroIndent();

    public static CodeStyleManager getInstance(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        return (CodeStyleManager) project.getService(CodeStyleManager.class);
    }

    public static CodeStyleManager getInstance(PsiManager psiManager) {
        if (psiManager == null) {
            $$$reportNull$$$0(1);
        }
        return getInstance(psiManager.getProject());
    }

    public void reformatTextWithContext(PsiFile psiFile, Collection<? extends TextRange> collection) throws IncorrectOperationException {
        if (psiFile == null) {
            $$$reportNull$$$0(2);
        }
        if (collection == null) {
            $$$reportNull$$$0(3);
        }
        reformatTextWithContext(psiFile, new ChangedRangesInfo(new ArrayList(collection), null));
    }

    public String getLineIndent(PsiFile psiFile, int i, FormattingMode formattingMode) {
        if (psiFile == null) {
            $$$reportNull$$$0(6);
        }
        return getLineIndent(psiFile, i);
    }

    public static FormattingMode getCurrentFormattingMode(Project project) {
        if (project == null) {
            $$$reportNull$$$0(9);
        }
        if (!project.isDisposed()) {
            Object codeStyleManager = getInstance(project);
            if (codeStyleManager instanceof FormattingModeAwareIndentAdjuster) {
                return ((FormattingModeAwareIndentAdjuster) codeStyleManager).getCurrentFormattingMode();
            }
        }
        return FormattingMode.REFORMAT;
    }

    public void runWithDocCommentFormattingDisabled(PsiFile psiFile, Runnable runnable) {
        if (psiFile == null) {
            $$$reportNull$$$0(10);
        }
        if (runnable == null) {
            $$$reportNull$$$0(11);
        }
        runnable.run();
    }

    public DocCommentSettings getDocCommentSettings(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(12);
        }
        DocCommentSettings docCommentSettings = DocCommentSettings.DEFAULTS;
        if (docCommentSettings == null) {
            $$$reportNull$$$0(13);
        }
        return docCommentSettings;
    }

    public void scheduleReformatWhenSettingsComputed(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(14);
        }
        throw new UnsupportedOperationException();
    }
}
