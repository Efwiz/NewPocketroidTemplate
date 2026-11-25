package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.impl.manipulators;

import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.AbstractElementManipulator;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiLiteralExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class StringLiteralManipulator extends AbstractElementManipulator<PsiLiteralExpression> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 4 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "range";
        } else if (i == 2) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else if (i == 3) {
            objArr[0] = "expression";
        } else if (i != 4) {
            objArr[0] = "expr";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/impl/manipulators/StringLiteralManipulator";
        }
        if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/impl/manipulators/StringLiteralManipulator";
        } else {
            objArr[1] = "getValueRange";
        }
        if (i == 2) {
            objArr[2] = "getRangeInElement";
        } else if (i == 3) {
            objArr[2] = "getValueRange";
        } else if (i != 4) {
            objArr[2] = "handleContentChange";
        }
        String str2 = String.format(str, objArr);
        if (i == 4) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.ElementManipulator
    public PsiLiteralExpression handleContentChange(PsiLiteralExpression psiLiteralExpression, TextRange textRange, String str) throws IncorrectOperationException {
        if (psiLiteralExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (textRange == null) {
            $$$reportNull$$$0(1);
        }
        String text = psiLiteralExpression.getText();
        if (text.startsWith("\"")) {
            str = StringUtil.escapeStringCharacters(str);
        } else if (text.startsWith(FormatHelper.QUOTE) && str.length() <= 1) {
            if (str.length() == 1 && str.charAt(0) == '\'') {
                str = "\\'";
            }
        } else {
            throw new IncorrectOperationException("cannot handle content change for: " + text + ", expr: " + psiLiteralExpression);
        }
        return (PsiLiteralExpression) psiLiteralExpression.replace(JavaPsiFacade.getElementFactory(psiLiteralExpression.getProject()).createExpressionFromText(text.substring(0, textRange.getStartOffset()) + str + text.substring(textRange.getEndOffset()), (PsiElement) null));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractElementManipulator, org.jetbrains.kotlin.com.intellij.psi.ElementManipulator
    public TextRange getRangeInElement(PsiLiteralExpression psiLiteralExpression) {
        if (psiLiteralExpression == null) {
            $$$reportNull$$$0(2);
        }
        return getValueRange(psiLiteralExpression);
    }

    public static TextRange getValueRange(PsiLiteralExpression psiLiteralExpression) {
        if (psiLiteralExpression == null) {
            $$$reportNull$$$0(3);
        }
        int textLength = psiLiteralExpression.getTextLength();
        if (psiLiteralExpression.isTextBlock()) {
            String text = psiLiteralExpression.getText();
            int iFindBlockStart = findBlockStart(text);
            if (iFindBlockStart < 0) {
                return new TextRange(0, textLength);
            }
            return new TextRange(iFindBlockStart, textLength - (text.endsWith("\"\"\"") ? 3 : 0));
        }
        PsiType type = psiLiteralExpression.getType();
        TextRange textRange = (PsiType.CHAR.equals(type) || (type != null && type.equalsToText(CommonClassNames.JAVA_LANG_STRING))) ? new TextRange(1, Math.max(1, textLength - 1)) : TextRange.from(0, textLength);
        if (textRange == null) {
            $$$reportNull$$$0(4);
        }
        return textRange;
    }

    private static int findBlockStart(String str) {
        if (!str.startsWith("\"\"\"")) {
            return -1;
        }
        int length = str.length();
        for (int i = 3; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\n') {
                return i + 1;
            }
            if (!Character.isWhitespace(cCharAt)) {
                return -1;
            }
        }
        return -1;
    }
}
