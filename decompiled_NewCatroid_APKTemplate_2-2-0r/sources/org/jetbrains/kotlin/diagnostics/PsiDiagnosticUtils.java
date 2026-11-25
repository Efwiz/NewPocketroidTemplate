package org.jetbrains.kotlin.diagnostics;

import com.beust.jcommander.Parameters;
import com.huawei.hms.ml.language.common.utils.Constant;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;

/* JADX INFO: loaded from: classes4.dex */
public class PsiDiagnosticUtils {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 4 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "node";
        } else if (i == 2) {
            objArr[0] = "file";
        } else if (i == 3) {
            objArr[0] = "textRange";
        } else if (i != 4) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "org/jetbrains/kotlin/diagnostics/PsiDiagnosticUtils";
        }
        if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/diagnostics/PsiDiagnosticUtils";
        } else {
            objArr[1] = "atLocation";
        }
        if (i != 4) {
            objArr[2] = "atLocation";
        }
        String str2 = String.format(str, objArr);
        if (i == 4) {
            throw new IllegalStateException(str2);
        }
    }

    public static String atLocation(PsiElement psiElement) {
        Object containingFile;
        int textOffset;
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElement.isValid()) {
            return atLocation(psiElement.getContainingFile(), psiElement.getTextRange());
        }
        try {
            containingFile = psiElement.getContainingFile();
            try {
                textOffset = psiElement.getTextOffset();
            } catch (PsiInvalidElementAccessException unused) {
                textOffset = -1;
            }
        } catch (PsiInvalidElementAccessException unused2) {
            containingFile = null;
        }
        StringBuilder sbAppend = new StringBuilder("at offset: ").append(textOffset != -1 ? Integer.valueOf(textOffset) : "<unknown>").append(" file: ");
        if (containingFile == null) {
            containingFile = "<unknown>";
        }
        return sbAppend.append(containingFile).toString();
    }

    public static String atLocation(KtExpression ktExpression) {
        return atLocation(ktExpression.getNode());
    }

    public static String atLocation(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        int startOffset = aSTNode.getStartOffset();
        PsiElement psiElementClosestPsiElement = PsiUtilsKt.closestPsiElement(aSTNode);
        if (psiElementClosestPsiElement != null) {
            return atLocation(psiElementClosestPsiElement);
        }
        return "at offset " + startOffset + " (line and file unknown: no PSI element)";
    }

    public static String atLocation(PsiFile psiFile, TextRange textRange) {
        if (psiFile == null) {
            $$$reportNull$$$0(2);
        }
        if (textRange == null) {
            $$$reportNull$$$0(3);
        }
        return atLocation(psiFile, textRange, psiFile.getViewProvider().getDocument());
    }

    public static String atLocation(PsiFile psiFile, TextRange textRange, Document document) {
        int startOffset = textRange.getStartOffset();
        VirtualFile virtualFile = psiFile.getVirtualFile();
        String str = offsetToLineAndColumn(document, startOffset) + (" in " + (virtualFile == null ? psiFile.getName() : virtualFile.getPath()));
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        return str;
    }

    public static LineAndColumn offsetToLineAndColumn(Document document, int i) {
        if (document == null || document.getTextLength() == 0) {
            return new LineAndColumn(-1, i, null);
        }
        int lineNumber = document.getLineNumber(i);
        int lineStartOffset = document.getLineStartOffset(lineNumber);
        return new LineAndColumn(lineNumber + 1, (i - lineStartOffset) + 1, document.getCharsSequence().subSequence(lineStartOffset, document.getLineEndOffset(lineNumber)).toString());
    }

    public static final class LineAndColumn {
        public static final LineAndColumn NONE = new LineAndColumn(-1, -1, null);
        private final int column;
        private final int line;
        private final String lineContent;

        public LineAndColumn(int i, int i2, String str) {
            this.line = i;
            this.column = i2;
            this.lineContent = str;
        }

        public int getLine() {
            return this.line;
        }

        public int getColumn() {
            return this.column;
        }

        public String getLineContent() {
            return this.lineContent;
        }

        public String toString() {
            if (this.line < 0) {
                return "(offset: " + this.column + " line unknown)";
            }
            return "(" + this.line + "," + this.column + Constant.AFTER_QUTO;
        }
    }

    public static final class LineAndColumnRange {
        public static final LineAndColumnRange NONE = new LineAndColumnRange(LineAndColumn.NONE, LineAndColumn.NONE);
        private final LineAndColumn end;
        private final LineAndColumn start;

        public LineAndColumnRange(LineAndColumn lineAndColumn, LineAndColumn lineAndColumn2) {
            this.start = lineAndColumn;
            this.end = lineAndColumn2;
        }

        public LineAndColumn getStart() {
            return this.start;
        }

        public LineAndColumn getEnd() {
            return this.end;
        }

        public String toString() {
            if (this.start.line == this.end.line) {
                return "(" + this.start.line + "," + this.start.column + Parameters.DEFAULT_OPTION_PREFIXES + this.end.column + Constant.AFTER_QUTO;
            }
            return this.start + " - " + this.end;
        }
    }
}
