package org.jetbrains.kotlin.resolve.jvm.jvmSignature;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: KotlinToJvmSignatureMapper.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003\u001a\f\u0010\u0005\u001a\u00020\u0006*\u00020\u0003H\u0002¨\u0006\u0007"}, d2 = {"erasedSignaturesEqualIgnoringReturnTypes", "", "subFunction", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "superFunction", "parametersDescriptor", "", "frontend.java"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinToJvmSignatureMapperKt {
    public static final boolean erasedSignaturesEqualIgnoringReturnTypes(Method subFunction, Method superFunction) {
        Intrinsics.checkNotNullParameter(subFunction, "subFunction");
        Intrinsics.checkNotNullParameter(superFunction, "superFunction");
        return Intrinsics.areEqual(parametersDescriptor(subFunction), parametersDescriptor(superFunction));
    }

    private static final String parametersDescriptor(Method method) {
        String descriptor = method.getDescriptor();
        Intrinsics.checkNotNullExpressionValue(descriptor, "getDescriptor(...)");
        String descriptor2 = method.getDescriptor();
        Intrinsics.checkNotNullExpressionValue(descriptor2, "getDescriptor(...)");
        String strSubstring = descriptor.substring(1, StringsKt.lastIndexOf$default((CharSequence) descriptor2, Constant.AFTER_QUTO, 0, false, 6, (Object) null));
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return strSubstring;
    }
}
