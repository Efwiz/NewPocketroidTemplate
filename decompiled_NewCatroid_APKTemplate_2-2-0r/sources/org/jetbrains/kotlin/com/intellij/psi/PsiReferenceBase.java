package org.jetbrains.kotlin.com.intellij.psi;

import com.huawei.hms.ml.language.common.utils.Constant;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.diagnostic.PluginException;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiReferenceBase<T extends PsiElement> implements PsiReference {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiReferenceBase.class);
    protected final T myElement;
    private TextRange myRangeInElement;
    protected boolean mySoft;

    /* JADX WARN: Code duplicated, block: B:11:0x0014  */
    /* JADX WARN: Code duplicated, block: B:17:0x0029  */
    /* JADX WARN: Code duplicated, block: B:6:0x000a  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 12) {
            switch (i) {
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 12) {
            switch (i) {
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        if (i != 12) {
            switch (i) {
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiReferenceBase";
                    break;
                case 9:
                    objArr[0] = "newElementName";
                    break;
                default:
                    objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                    break;
            }
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiReferenceBase";
        }
        if (i != 12) {
            switch (i) {
                case 4:
                case 5:
                    objArr[1] = "getValue";
                    break;
                case 6:
                    objArr[1] = "getElement";
                    break;
                case 7:
                    objArr[1] = "getRangeInElement";
                    break;
                case 8:
                    objArr[1] = "getCanonicalText";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiReferenceBase";
                    break;
            }
        } else {
            objArr[1] = "getManipulator";
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 12:
                break;
            case 9:
                objArr[2] = "handleElementRename";
                break;
            case 10:
                objArr[2] = "bindToElement";
                break;
            case 11:
                objArr[2] = "isReferenceTo";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 12) {
            switch (i) {
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    public PsiReferenceBase(T t, TextRange textRange, boolean z) {
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        this.myElement = t;
        this.myRangeInElement = textRange;
        this.mySoft = z;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PsiReferenceBase(T t, TextRange textRange) {
        this(t);
        if (t == null) {
            $$$reportNull$$$0(1);
        }
        this.myRangeInElement = textRange;
    }

    public PsiReferenceBase(T t, boolean z) {
        if (t == null) {
            $$$reportNull$$$0(2);
        }
        this.myElement = t;
        this.mySoft = z;
    }

    public PsiReferenceBase(T t) {
        if (t == null) {
            $$$reportNull$$$0(3);
        }
        this.myElement = t;
        this.mySoft = false;
    }

    public void setRangeInElement(TextRange textRange) {
        this.myRangeInElement = textRange;
    }

    public String getValue() {
        String text = this.myElement.getText();
        TextRange rangeInElement = getRangeInElement();
        try {
            String strSubstring = rangeInElement.substring(text);
            if (strSubstring == null) {
                $$$reportNull$$$0(4);
            }
            return strSubstring;
        } catch (StringIndexOutOfBoundsException e) {
            LOG.error("Wrong range in reference " + this + ": " + rangeInElement + ". Reference text: '" + text + FormatHelper.QUOTE, e);
            if (text == null) {
                $$$reportNull$$$0(5);
            }
            return text;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public T getElement() {
        T t = this.myElement;
        if (t == null) {
            $$$reportNull$$$0(6);
        }
        return t;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public TextRange getRangeInElement() {
        TextRange textRangeCalculateDefaultRangeInElement = this.myRangeInElement;
        if (textRangeCalculateDefaultRangeInElement == null) {
            textRangeCalculateDefaultRangeInElement = calculateDefaultRangeInElement();
            this.myRangeInElement = textRangeCalculateDefaultRangeInElement;
        }
        if (textRangeCalculateDefaultRangeInElement == null) {
            $$$reportNull$$$0(7);
        }
        return textRangeCalculateDefaultRangeInElement;
    }

    protected TextRange calculateDefaultRangeInElement() {
        return getManipulator().getRangeInElement(this.myElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public String getCanonicalText() {
        String value = getValue();
        if (value == null) {
            $$$reportNull$$$0(8);
        }
        return value;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement handleElementRename(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        return getManipulator().handleContentChange(this.myElement, getRangeInElement(), str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement bindToElement(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(10);
        }
        throw new IncorrectOperationException("Rebind cannot be performed for " + getClass());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isReferenceTo(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(11);
        }
        return getElement().getManager().areElementsEquivalent(resolve(), psiElement);
    }

    public static <T extends PsiElement> PsiReferenceBase<T> createSelfReference(T t, PsiElement psiElement) {
        return new Immediate((PsiElement) t, true, psiElement);
    }

    public static <T extends PsiElement> PsiReferenceBase<T> createSelfReference(T t, TextRange textRange, PsiElement psiElement) {
        return new Immediate(t, textRange, psiElement);
    }

    private ElementManipulator<T> getManipulator() {
        ElementManipulator<T> manipulator = ElementManipulators.getManipulator(this.myElement);
        if (manipulator == null) {
            throw PluginException.createByClass("Cannot find manipulator for " + this.myElement + " in " + this + " class " + getClass(), null, this.myElement.getClass());
        }
        if (manipulator == null) {
            $$$reportNull$$$0(12);
        }
        return manipulator;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isSoft() {
        return this.mySoft;
    }

    public static abstract class Poly<T extends PsiElement> extends PsiReferenceBase<T> implements PsiPolyVariantReference {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "org/jetbrains/kotlin/com/intellij/psi/PsiReferenceBase$Poly", "isReferenceTo"));
        }

        public Poly(T t) {
            super(t);
        }

        public Poly(T t, boolean z) {
            super(t, z);
        }

        public Poly(T t, TextRange textRange, boolean z) {
            super(t, textRange, z);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public boolean isReferenceTo(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(0);
            }
            for (ResolveResult resolveResult : multiResolve(false)) {
                if (psiElement.getManager().areElementsEquivalent(resolveResult.getElement(), psiElement)) {
                    return true;
                }
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public PsiElement resolve() {
            ResolveResult[] resolveResultArrMultiResolve = multiResolve(false);
            if (resolveResultArrMultiResolve.length == 1) {
                return resolveResultArrMultiResolve[0].getElement();
            }
            return null;
        }
    }

    public static class Immediate<T extends PsiElement> extends PsiReferenceBase<T> {
        private final PsiElement myResolveTo;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
            } else {
                objArr[0] = "newElementName";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiReferenceBase$Immediate";
            if (i != 1) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "handleElementRename";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        public Immediate(T t, TextRange textRange, boolean z, PsiElement psiElement) {
            super(t, textRange, z);
            this.myResolveTo = psiElement;
        }

        public Immediate(T t, TextRange textRange, PsiElement psiElement) {
            super(t, textRange);
            this.myResolveTo = psiElement;
        }

        public Immediate(T t, boolean z, PsiElement psiElement) {
            super(t, z);
            this.myResolveTo = psiElement;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Immediate(T t, PsiElement psiElement) {
            super(t);
            if (t == null) {
                $$$reportNull$$$0(0);
            }
            this.myResolveTo = psiElement;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public PsiElement handleElementRename(String str) throws IncorrectOperationException {
            if (str == null) {
                $$$reportNull$$$0(1);
            }
            return getElement();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
        public PsiElement resolve() {
            return this.myResolveTo;
        }
    }

    public String toString() {
        return getClass().getName() + "(" + this.myElement + ":" + this.myRangeInElement + Constant.AFTER_QUTO;
    }
}
