package org.jetbrains.kotlin.com.intellij.openapi.extensions;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;

/* JADX INFO: loaded from: classes3.dex */
public final class SortingException extends RuntimeException {
    private final LoadingOrder.Orderable[] myConflictingElements;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "conflictingElements";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/SortingException";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/SortingException";
        } else {
            objArr[1] = "getConflictingElements";
        }
        if (i != 1) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    SortingException(String str, LoadingOrder.Orderable... orderableArr) {
        super(str + ": " + StringUtil.join(orderableArr, new Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.SortingException$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return SortingException.lambda$new$0((LoadingOrder.Orderable) obj);
            }
        }, "; "));
        if (orderableArr == null) {
            $$$reportNull$$$0(0);
        }
        this.myConflictingElements = orderableArr;
    }

    static /* synthetic */ String lambda$new$0(LoadingOrder.Orderable orderable) {
        return orderable.getOrderId() + "(" + orderable.getOrder() + Constant.AFTER_QUTO;
    }
}
