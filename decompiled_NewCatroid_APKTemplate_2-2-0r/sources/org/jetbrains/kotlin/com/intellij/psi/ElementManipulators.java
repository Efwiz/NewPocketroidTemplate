package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.ClassExtension;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;

/* JADX INFO: loaded from: classes3.dex */
public final class ElementManipulators extends ClassExtension<ElementManipulator> {
    public static final String EP_NAME = "org.jetbrains.kotlin.com.intellij.lang.elementManipulator";
    public static final ElementManipulators INSTANCE = new ElementManipulators();
    private static final Logger LOG = Logger.getInstance((Class<?>) ElementManipulators.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 6 || i == 12) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 6 || i == 12) ? 2 : 3];
        if (i == 3 || i == 6 || i == 12) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/ElementManipulators";
        } else if (i == 9) {
            objArr[0] = "range";
        } else if (i != 10) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "manipulator";
        }
        if (i == 3) {
            objArr[1] = "getNotNullManipulator";
        } else if (i == 6) {
            objArr[1] = "getValueText";
        } else if (i != 12) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/ElementManipulators";
        } else {
            objArr[1] = "getManipulatorRange";
        }
        switch (i) {
            case 1:
                objArr[2] = "getOffsetInElement";
                break;
            case 2:
                objArr[2] = "getNotNullManipulator";
                break;
            case 3:
            case 6:
            case 12:
                break;
            case 4:
                objArr[2] = "getValueTextRange";
                break;
            case 5:
                objArr[2] = "getValueText";
                break;
            case 7:
            case 8:
            case 9:
                objArr[2] = "handleContentChange";
                break;
            case 10:
            case 11:
                objArr[2] = "getManipulatorRange";
                break;
            default:
                objArr[2] = "getManipulator";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 6 && i != 12) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private ElementManipulators() {
        super(EP_NAME);
    }

    public static <T extends PsiElement> ElementManipulator<T> getManipulator(T t) {
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        return INSTANCE.forClass(t.getClass());
    }

    public static int getOffsetInElement(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        return getManipulatorRange(getNotNullManipulator(psiElement), psiElement).getStartOffset();
    }

    public static <T extends PsiElement> ElementManipulator<T> getNotNullManipulator(T t) {
        if (t == null) {
            $$$reportNull$$$0(2);
        }
        ElementManipulator<T> manipulator = getManipulator(t);
        LOG.assertTrue(manipulator != null, t.getClass().getName());
        if (manipulator == null) {
            $$$reportNull$$$0(3);
        }
        return manipulator;
    }

    public static TextRange getValueTextRange(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(4);
        }
        ElementManipulator manipulator = getManipulator(psiElement);
        return manipulator == null ? TextRange.from(0, psiElement.getTextLength()) : getManipulatorRange(manipulator, psiElement);
    }

    public static String getValueText(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        TextRange valueTextRange = getValueTextRange(psiElement);
        if (valueTextRange.isEmpty()) {
            return "";
        }
        String text = psiElement.getText();
        if (valueTextRange.getEndOffset() > text.length()) {
            LOG.error("Wrong range for " + psiElement + " text: " + text + " range " + valueTextRange);
        }
        String strSubstring = valueTextRange.substring(text);
        if (strSubstring == null) {
            $$$reportNull$$$0(6);
        }
        return strSubstring;
    }

    public static <T extends PsiElement> T handleContentChange(T t, String str) {
        if (t == null) {
            $$$reportNull$$$0(7);
        }
        return (T) getNotNullManipulator(t).handleContentChange(t, str);
    }

    public static <T extends PsiElement> T handleContentChange(T t, TextRange textRange, String str) {
        if (t == null) {
            $$$reportNull$$$0(8);
        }
        if (textRange == null) {
            $$$reportNull$$$0(9);
        }
        return (T) getNotNullManipulator(t).handleContentChange(t, textRange, str);
    }

    private static TextRange getManipulatorRange(ElementManipulator<? super PsiElement> elementManipulator, PsiElement psiElement) {
        if (elementManipulator == null) {
            $$$reportNull$$$0(10);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(11);
        }
        TextRange rangeInElement = elementManipulator.getRangeInElement(psiElement);
        TextRange textRangeFrom = TextRange.from(0, psiElement.getTextLength());
        if (!textRangeFrom.contains(rangeInElement)) {
            LOG.error("Element range: " + textRangeFrom + ";\nmanipulator range: " + rangeInElement + ";\nelement: " + psiElement.getClass() + ";\nmanipulator: " + elementManipulator.getClass() + ".");
        }
        if (rangeInElement == null) {
            $$$reportNull$$$0(12);
        }
        return rangeInElement;
    }
}
