package org.jetbrains.kotlin.com.intellij.psi.util;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jetbrains.kotlin.com.intellij.psi.PsiEllipsisType;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;

/* JADX INFO: loaded from: classes3.dex */
public abstract class MethodSignatureBase implements MethodSignature {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private volatile PsiType[] myErasedParameterTypes;
    private final PsiType[] myParameterTypes;
    private final PsiSubstitutor mySubstitutor;
    protected final PsiTypeParameter[] myTypeParameters;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5 || i == 6 || i == 7) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "parameterTypes";
        } else if (i == 2) {
            objArr[0] = "typeParameters";
        } else if (i == 4 || i == 5 || i == 6 || i == 7) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/MethodSignatureBase";
        } else {
            objArr[0] = "substitutor";
        }
        if (i == 4) {
            objArr[1] = "getParameterTypes";
        } else if (i == 5) {
            objArr[1] = "getTypeParameters";
        } else if (i == 6) {
            objArr[1] = "getErasedParameterTypes";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/MethodSignatureBase";
        } else {
            objArr[1] = "getSubstitutor";
        }
        if (i != 4 && i != 5 && i != 6 && i != 7) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5 && i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    protected MethodSignatureBase(PsiSubstitutor psiSubstitutor, PsiType[] psiTypeArr, PsiTypeParameter[] psiTypeParameterArr) {
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(0);
        }
        if (psiTypeArr == null) {
            $$$reportNull$$$0(1);
        }
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(2);
        }
        this.mySubstitutor = psiSubstitutor;
        this.myParameterTypes = PsiType.createArray(psiTypeArr.length);
        for (int i = 0; i < psiTypeArr.length; i++) {
            PsiType arrayType = psiTypeArr[i];
            if (arrayType instanceof PsiEllipsisType) {
                arrayType = ((PsiEllipsisType) arrayType).toArrayType();
            }
            this.myParameterTypes[i] = psiSubstitutor.substitute(arrayType);
        }
        this.myTypeParameters = psiTypeParameterArr;
    }

    protected MethodSignatureBase(PsiSubstitutor psiSubstitutor, PsiParameterList psiParameterList, PsiTypeParameterList psiTypeParameterList) {
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(3);
        }
        this.mySubstitutor = psiSubstitutor;
        if (psiParameterList == null) {
            this.myParameterTypes = PsiType.EMPTY_ARRAY;
        } else {
            PsiParameter[] parameters = psiParameterList.getParameters();
            this.myParameterTypes = PsiType.createArray(parameters.length);
            for (int i = 0; i < parameters.length; i++) {
                PsiType type = parameters[i].getType();
                if (type instanceof PsiEllipsisType) {
                    type = ((PsiEllipsisType) type).toArrayType();
                }
                this.myParameterTypes[i] = psiSubstitutor.substitute(type);
            }
        }
        this.myTypeParameters = psiTypeParameterList == null ? PsiTypeParameter.EMPTY_ARRAY : psiTypeParameterList.getTypeParameters();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature
    public PsiType[] getParameterTypes() {
        PsiType[] psiTypeArr = this.myParameterTypes;
        if (psiTypeArr == null) {
            $$$reportNull$$$0(4);
        }
        return psiTypeArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature
    public PsiTypeParameter[] getTypeParameters() {
        PsiTypeParameter[] psiTypeParameterArr = this.myTypeParameters;
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(5);
        }
        return psiTypeParameterArr;
    }

    public PsiType[] getErasedParameterTypes() {
        PsiType[] psiTypeArrCalcErasedParameterTypes = this.myErasedParameterTypes;
        if (psiTypeArrCalcErasedParameterTypes == null) {
            psiTypeArrCalcErasedParameterTypes = MethodSignatureUtil.calcErasedParameterTypes(this);
            this.myErasedParameterTypes = psiTypeArrCalcErasedParameterTypes;
        }
        if (psiTypeArrCalcErasedParameterTypes == null) {
            $$$reportNull$$$0(6);
        }
        return psiTypeArrCalcErasedParameterTypes;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MethodSignature) {
            return MethodSignatureUtil.areSignaturesEqual((MethodSignature) obj, this);
        }
        return false;
    }

    public int hashCode() {
        int iHashCode = getName().hashCode();
        PsiType[] erasedParameterTypes = getErasedParameterTypes();
        int length = (iHashCode * 31) + erasedParameterTypes.length;
        int iMin = Math.min(3, erasedParameterTypes.length);
        for (int i = 0; i < iMin; i++) {
            PsiType psiType = erasedParameterTypes[i];
            if (psiType != null) {
                length = (length * 31) + psiType.hashCode();
            }
        }
        return length;
    }

    public String toString() {
        String str = getClass().getSimpleName() + ": ";
        PsiTypeParameter[] typeParameters = getTypeParameters();
        if (typeParameters.length != 0) {
            str = str + ((String) Arrays.stream(typeParameters).map(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureBase$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((PsiTypeParameter) obj).getName();
                }
            }).collect(Collectors.joining(", ", "<", ">")));
        }
        return str + getName() + "(" + Arrays.asList(getParameterTypes()) + Constant.AFTER_QUTO;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature
    public PsiSubstitutor getSubstitutor() {
        PsiSubstitutor psiSubstitutor = this.mySubstitutor;
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(7);
        }
        return psiSubstitutor;
    }
}
