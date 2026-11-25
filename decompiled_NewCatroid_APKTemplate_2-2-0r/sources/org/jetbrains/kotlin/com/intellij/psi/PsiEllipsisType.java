package org.jetbrains.kotlin.com.intellij.psi;

import org.catrobat.catroid.common.Constants;

/* JADX INFO: loaded from: classes3.dex */
public class PsiEllipsisType extends PsiArrayType {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 5 || i == 6 || i == 7) ? 2 : 3];
        switch (i) {
            case 2:
                objArr[0] = "annotations";
                break;
            case 3:
            default:
                objArr[0] = "componentType";
                break;
            case 4:
                objArr[0] = Constants.EXTRA_PROVIDER;
                break;
            case 5:
            case 6:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiEllipsisType";
                break;
            case 8:
                objArr[0] = "text";
                break;
            case 9:
                objArr[0] = "visitor";
                break;
        }
        if (i == 5) {
            objArr[1] = "getPresentableText";
        } else if (i == 6) {
            objArr[1] = "getCanonicalText";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiEllipsisType";
        } else {
            objArr[1] = "getInternalCanonicalText";
        }
        switch (i) {
            case 5:
            case 6:
            case 7:
                break;
            case 8:
                objArr[2] = "equalsToText";
                break;
            case 9:
                objArr[2] = "accept";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiEllipsisType(PsiType psiType) {
        super(psiType);
        if (psiType == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiEllipsisType(PsiType psiType, PsiAnnotation[] psiAnnotationArr) {
        super(psiType, psiAnnotationArr);
        if (psiType == null) {
            $$$reportNull$$$0(1);
        }
        if (psiAnnotationArr == null) {
            $$$reportNull$$$0(2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiEllipsisType(PsiType psiType, TypeAnnotationProvider typeAnnotationProvider) {
        super(psiType, typeAnnotationProvider);
        if (psiType == null) {
            $$$reportNull$$$0(3);
        }
        if (typeAnnotationProvider == null) {
            $$$reportNull$$$0(4);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiArrayType, org.jetbrains.kotlin.com.intellij.psi.PsiType.Stub, org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getPresentableText(boolean z) {
        String text = getText(getDeepComponentType().getPresentableText(z), "...", false, z);
        if (text == null) {
            $$$reportNull$$$0(5);
        }
        return text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiArrayType, org.jetbrains.kotlin.com.intellij.psi.PsiType.Stub, org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getCanonicalText(boolean z) {
        String text = getText(getDeepComponentType().getCanonicalText(z), "...", true, z);
        if (text == null) {
            $$$reportNull$$$0(6);
        }
        return text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiArrayType, org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getInternalCanonicalText() {
        String text = getText(getDeepComponentType().getInternalCanonicalText(), "...", true, true);
        if (text == null) {
            $$$reportNull$$$0(7);
        }
        return text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiArrayType, org.jetbrains.kotlin.com.intellij.psi.PsiType
    public boolean equalsToText(String str) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        return (str.endsWith("...") && getComponentType().equalsToText(str.substring(0, str.length() + (-3)))) || super.equalsToText(str);
    }

    public PsiType toArrayType() {
        return new PsiArrayType(getComponentType(), getAnnotationProvider());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiArrayType, org.jetbrains.kotlin.com.intellij.psi.PsiType
    public <A> A accept(PsiTypeVisitor<A> psiTypeVisitor) {
        if (psiTypeVisitor == null) {
            $$$reportNull$$$0(9);
        }
        return psiTypeVisitor.visitEllipsisType(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiArrayType
    public int hashCode() {
        return super.hashCode() * 5;
    }
}
