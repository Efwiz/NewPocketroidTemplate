package org.eclipse.jdt.internal.compiler.util;

import java.util.Arrays;
import java.util.Comparator;
import org.eclipse.jdt.internal.compiler.lookup.InferenceVariable;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class Sorting {
    public static ReferenceBinding[] sortTypes(ReferenceBinding[] referenceBindingArr) {
        int length = referenceBindingArr.length;
        ReferenceBinding[] referenceBindingArr2 = new ReferenceBinding[length];
        ReferenceBinding[] referenceBindingArr3 = new ReferenceBinding[length];
        System.arraycopy(referenceBindingArr, 0, referenceBindingArr2, 0, length);
        int iSort = 0;
        for (int i = 0; i < length; i++) {
            iSort = sort(referenceBindingArr2, i, referenceBindingArr3, iSort);
        }
        return referenceBindingArr3;
    }

    private static int sort(ReferenceBinding[] referenceBindingArr, int i, ReferenceBinding[] referenceBindingArr2, int i2) {
        ReferenceBinding referenceBinding = referenceBindingArr[i];
        if (referenceBinding == null) {
            return i2;
        }
        int iSortSuper = sortSuper(referenceBinding.superclass(), referenceBindingArr, referenceBindingArr2, i2);
        for (ReferenceBinding referenceBinding2 : referenceBindingArr[i].superInterfaces()) {
            iSortSuper = sortSuper(referenceBinding2, referenceBindingArr, referenceBindingArr2, iSortSuper);
        }
        int i3 = iSortSuper + 1;
        referenceBindingArr2[iSortSuper] = referenceBindingArr[i];
        referenceBindingArr[i] = null;
        return i3;
    }

    private static int sortSuper(ReferenceBinding referenceBinding, ReferenceBinding[] referenceBindingArr, ReferenceBinding[] referenceBindingArr2, int i) {
        if (referenceBinding.f626id == 1) {
            return i;
        }
        int i2 = 0;
        while (i2 < referenceBindingArr.length && !TypeBinding.equalsEquals(referenceBindingArr[i2], referenceBinding)) {
            i2++;
        }
        return i2 < referenceBindingArr.length ? sort(referenceBindingArr, i2, referenceBindingArr2, i) : i;
    }

    public static MethodBinding[] concreteFirst(MethodBinding[] methodBindingArr, int i) {
        if (i != 0) {
            if (i <= 0 || methodBindingArr[0].isAbstract()) {
                MethodBinding[] methodBindingArr2 = new MethodBinding[i];
                int i2 = 0;
                for (int i3 = 0; i3 < i; i3++) {
                    if (!methodBindingArr[i3].isAbstract()) {
                        methodBindingArr2[i2] = methodBindingArr[i3];
                        i2++;
                    }
                }
                for (int i4 = 0; i4 < i; i4++) {
                    if (methodBindingArr[i4].isAbstract()) {
                        methodBindingArr2[i2] = methodBindingArr[i4];
                        i2++;
                    }
                }
                return methodBindingArr2;
            }
        }
        return methodBindingArr;
    }

    public static MethodBinding[] abstractFirst(MethodBinding[] methodBindingArr, int i) {
        if (i != 0) {
            if (i <= 0 || !methodBindingArr[0].isAbstract()) {
                MethodBinding[] methodBindingArr2 = new MethodBinding[i];
                int i2 = 0;
                for (int i3 = 0; i3 < i; i3++) {
                    if (methodBindingArr[i3].isAbstract()) {
                        methodBindingArr2[i2] = methodBindingArr[i3];
                        i2++;
                    }
                }
                for (int i4 = 0; i4 < i; i4++) {
                    if (!methodBindingArr[i4].isAbstract()) {
                        methodBindingArr2[i2] = methodBindingArr[i4];
                        i2++;
                    }
                }
                return methodBindingArr2;
            }
        }
        return methodBindingArr;
    }

    public static void sortInferenceVariables(InferenceVariable[] inferenceVariableArr) {
        Arrays.sort(inferenceVariableArr, new Comparator<InferenceVariable>() { // from class: org.eclipse.jdt.internal.compiler.util.Sorting.1
            @Override // java.util.Comparator
            public int compare(InferenceVariable inferenceVariable, InferenceVariable inferenceVariable2) {
                return inferenceVariable.rank - inferenceVariable2.rank;
            }
        });
    }
}
