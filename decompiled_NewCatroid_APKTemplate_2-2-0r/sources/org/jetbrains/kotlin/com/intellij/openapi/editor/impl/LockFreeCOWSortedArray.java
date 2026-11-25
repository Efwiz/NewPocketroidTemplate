package org.jetbrains.kotlin.com.intellij.openapi.editor.impl;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.reandroid.archive.PathTree;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicReference;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
class LockFreeCOWSortedArray<T> extends AtomicReference<T[]> {
    private final ArrayFactory<? extends T> arrayFactory;
    private final Comparator<? super T> comparator;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 6 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 6 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "arrayFactory";
                break;
            case 2:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 3:
                objArr[0] = ServiceSpecificExtraArgs.CastExtraArgs.LISTENER;
                break;
            case 4:
                objArr[0] = "e";
                break;
            case 5:
                objArr[0] = PathTree.NAME_elements;
                break;
            case 6:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/LockFreeCOWSortedArray";
                break;
            default:
                objArr[0] = "comparator";
                break;
        }
        if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/LockFreeCOWSortedArray";
        } else {
            objArr[1] = "getArray";
        }
        if (i == 2) {
            objArr[2] = ProductAction.ACTION_ADD;
        } else if (i == 3) {
            objArr[2] = ProductAction.ACTION_REMOVE;
        } else if (i == 4 || i == 5) {
            objArr[2] = "insertionIndex";
        } else if (i != 6) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 6) {
            throw new IllegalStateException(str2);
        }
    }

    LockFreeCOWSortedArray(Comparator<? super T> comparator, ArrayFactory<? extends T> arrayFactory) {
        if (comparator == null) {
            $$$reportNull$$$0(0);
        }
        if (arrayFactory == null) {
            $$$reportNull$$$0(1);
        }
        this.comparator = comparator;
        this.arrayFactory = arrayFactory;
        set(arrayFactory.create(0));
    }

    /* JADX WARN: Multi-variable type inference failed */
    void add(T t) {
        Object[] objArr;
        if (t == null) {
            $$$reportNull$$$0(2);
        }
        do {
            objArr = (Object[]) get();
        } while (!compareAndSet(objArr, ArrayUtil.insert(objArr, insertionIndex(objArr, t), t)));
    }

    private int insertionIndex(T[] tArr, T t) {
        if (t == null) {
            $$$reportNull$$$0(4);
        }
        if (tArr == null) {
            $$$reportNull$$$0(5);
        }
        for (int i = 0; i < tArr.length; i++) {
            if (this.comparator.compare(t, tArr[i]) < 0) {
                return i;
            }
        }
        return tArr.length;
    }

    T[] getArray() {
        T[] tArr = get();
        if (tArr == null) {
            $$$reportNull$$$0(6);
        }
        return tArr;
    }
}
