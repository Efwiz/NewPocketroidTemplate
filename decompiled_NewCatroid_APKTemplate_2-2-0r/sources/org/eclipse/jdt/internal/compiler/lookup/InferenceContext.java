package org.eclipse.jdt.internal.compiler.lookup;

import com.huawei.hms.framework.common.ContainerUtils;
import java.lang.reflect.Array;

/* JADX INFO: loaded from: classes14.dex */
public class InferenceContext {
    static final int FAILED = 1;
    private TypeBinding[][][] collectedSubstitutes;
    int depth;
    TypeBinding expectedType;
    MethodBinding genericMethod;
    boolean hasExplicitExpectedType;
    public boolean isUnchecked;
    int status;
    TypeBinding[] substitutes;

    public InferenceContext(MethodBinding methodBinding) {
        this.genericMethod = methodBinding;
        int length = methodBinding.typeVariables.length;
        this.collectedSubstitutes = (TypeBinding[][][]) Array.newInstance((Class<?>) TypeBinding[].class, length, 3);
        this.substitutes = new TypeBinding[length];
    }

    public TypeBinding[] getSubstitutes(TypeVariableBinding typeVariableBinding, int i) {
        return this.collectedSubstitutes[typeVariableBinding.rank][i];
    }

    public boolean hasUnresolvedTypeArgument() {
        int length = this.substitutes.length;
        for (int i = 0; i < length; i++) {
            if (this.substitutes[i] == null) {
                return true;
            }
        }
        return false;
    }

    public void recordSubstitute(TypeVariableBinding typeVariableBinding, TypeBinding typeBinding, int i) {
        TypeBinding[] typeBindingArr;
        TypeBinding[][] typeBindingArr2 = this.collectedSubstitutes[typeVariableBinding.rank];
        TypeBinding[] typeBindingArr3 = typeBindingArr2[i];
        int i2 = 0;
        if (typeBindingArr3 == null) {
            typeBindingArr = new TypeBinding[1];
        } else {
            int length = typeBindingArr3.length;
            for (int i3 = 0; i3 < length; i3++) {
                TypeBinding typeBinding2 = typeBindingArr3[i3];
                if (typeBinding2 == typeBinding) {
                    return;
                }
                if (typeBinding2 == null) {
                    typeBindingArr3[i3] = typeBinding;
                    return;
                }
            }
            TypeBinding[] typeBindingArr4 = new TypeBinding[length + 1];
            System.arraycopy(typeBindingArr3, 0, typeBindingArr4, 0, length);
            i2 = length;
            typeBindingArr = typeBindingArr4;
        }
        typeBindingArr[i2] = typeBinding;
        typeBindingArr2[i] = typeBindingArr;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(20);
        stringBuffer.append("InferenceContex for ");
        int length = this.genericMethod.typeVariables.length;
        for (int i = 0; i < length; i++) {
            stringBuffer.append(this.genericMethod.typeVariables[i]);
        }
        stringBuffer.append(this.genericMethod);
        stringBuffer.append("\n\t[status=");
        int i2 = this.status;
        if (i2 == 0) {
            stringBuffer.append("ok]");
        } else if (i2 == 1) {
            stringBuffer.append("failed]");
        }
        if (this.expectedType == null) {
            stringBuffer.append(" [expectedType=null]");
        } else {
            stringBuffer.append(" [expectedType=").append(this.expectedType.shortReadableName()).append(']');
        }
        stringBuffer.append(" [depth=").append(this.depth).append("]\n\t[collected={");
        TypeBinding[][][] typeBindingArr = this.collectedSubstitutes;
        int length2 = typeBindingArr == null ? 0 : typeBindingArr.length;
        for (int i3 = 0; i3 < length2; i3++) {
            TypeBinding[][] typeBindingArr2 = this.collectedSubstitutes[i3];
            for (int i4 = 0; i4 <= 2; i4++) {
                TypeBinding[] typeBindingArr3 = typeBindingArr2[i4];
                if (typeBindingArr3 != null) {
                    for (TypeBinding typeBinding : typeBindingArr3) {
                        stringBuffer.append("\n\t\t").append(this.genericMethod.typeVariables[i3].sourceName);
                        if (i4 == 0) {
                            stringBuffer.append(ContainerUtils.KEY_VALUE_DELIMITER);
                        } else if (i4 == 1) {
                            stringBuffer.append("<:");
                        } else if (i4 == 2) {
                            stringBuffer.append(">:");
                        }
                        if (typeBinding != null) {
                            stringBuffer.append(typeBinding.shortReadableName());
                        }
                    }
                }
            }
        }
        stringBuffer.append("}]\n\t[inferred=");
        TypeBinding[] typeBindingArr4 = this.substitutes;
        int length3 = typeBindingArr4 == null ? 0 : typeBindingArr4.length;
        int i5 = 0;
        for (int i6 = 0; i6 < length3; i6++) {
            if (this.substitutes[i6] != null) {
                i5++;
                stringBuffer.append('{').append(this.genericMethod.typeVariables[i6].sourceName);
                stringBuffer.append(ContainerUtils.KEY_VALUE_DELIMITER).append(this.substitutes[i6].shortReadableName()).append('}');
            }
        }
        if (i5 == 0) {
            stringBuffer.append("{}");
        }
        stringBuffer.append(']');
        return stringBuffer.toString();
    }
}
