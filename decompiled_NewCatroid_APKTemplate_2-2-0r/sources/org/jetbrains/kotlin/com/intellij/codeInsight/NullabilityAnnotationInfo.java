package org.jetbrains.kotlin.com.intellij.codeInsight;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;

/* JADX INFO: loaded from: classes3.dex */
public class NullabilityAnnotationInfo {
    private final PsiAnnotation myAnnotation;
    private final boolean myContainer;
    private final PsiModifierListOwner myInheritedFrom;
    private final Nullability myNullability;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5) ? 2 : 3];
        if (i == 1 || i == 3) {
            objArr[0] = "nullability";
        } else if (i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/codeInsight/NullabilityAnnotationInfo";
        } else {
            objArr[0] = "annotation";
        }
        if (i == 4) {
            objArr[1] = "getAnnotation";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/codeInsight/NullabilityAnnotationInfo";
        } else {
            objArr[1] = "getNullability";
        }
        if (i != 4 && i != 5) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public NullabilityAnnotationInfo(PsiAnnotation psiAnnotation, Nullability nullability, boolean z) {
        this(psiAnnotation, nullability, null, z);
        if (psiAnnotation == null) {
            $$$reportNull$$$0(0);
        }
        if (nullability == null) {
            $$$reportNull$$$0(1);
        }
    }

    NullabilityAnnotationInfo(PsiAnnotation psiAnnotation, Nullability nullability, PsiModifierListOwner psiModifierListOwner, boolean z) {
        if (psiAnnotation == null) {
            $$$reportNull$$$0(2);
        }
        if (nullability == null) {
            $$$reportNull$$$0(3);
        }
        this.myAnnotation = psiAnnotation;
        this.myNullability = nullability;
        this.myInheritedFrom = psiModifierListOwner;
        this.myContainer = z;
    }

    public PsiAnnotation getAnnotation() {
        PsiAnnotation psiAnnotation = this.myAnnotation;
        if (psiAnnotation == null) {
            $$$reportNull$$$0(4);
        }
        return psiAnnotation;
    }

    public Nullability getNullability() {
        Nullability nullability = this.myNullability;
        if (nullability == null) {
            $$$reportNull$$$0(5);
        }
        return nullability;
    }

    public boolean isContainer() {
        return this.myContainer;
    }

    public PsiModifierListOwner getInheritedFrom() {
        return this.myInheritedFrom;
    }

    NullabilityAnnotationInfo withInheritedFrom(PsiModifierListOwner psiModifierListOwner) {
        return new NullabilityAnnotationInfo(this.myAnnotation, this.myNullability, psiModifierListOwner, this.myContainer);
    }

    public String toString() {
        return "NullabilityAnnotationInfo{" + this.myNullability + "(" + this.myAnnotation.getQualifiedName() + Constant.AFTER_QUTO + (this.myContainer ? ", container" : "") + (this.myInheritedFrom != null ? ", inherited from: " + this.myInheritedFrom : "") + "}";
    }
}
