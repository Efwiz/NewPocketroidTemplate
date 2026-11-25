package org.jetbrains.kotlin.backend.common;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: commonCoroutineCodegenUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0004\u001a\u00020\u0005*\u00020\u0006\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u0007"}, d2 = {"COROUTINE_SUSPENDED_NAME", "Lorg/jetbrains/kotlin/name/Name;", "getCOROUTINE_SUSPENDED_NAME", "()Lorg/jetbrains/kotlin/name/Name;", "isBuiltInSuspendCoroutineUninterceptedOrReturn", "", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "backend-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CommonCoroutineCodegenUtilKt {
    private static final Name COROUTINE_SUSPENDED_NAME;

    static {
        Name nameIdentifier = Name.identifier("COROUTINE_SUSPENDED");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        COROUTINE_SUSPENDED_NAME = nameIdentifier;
    }

    public static final Name getCOROUTINE_SUSPENDED_NAME() {
        return COROUTINE_SUSPENDED_NAME;
    }

    public static final boolean isBuiltInSuspendCoroutineUninterceptedOrReturn(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
        String strAsString = StandardNames.COROUTINES_INTRINSICS_PACKAGE_FQ_NAME.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return DescriptorUtilKt.isTopLevelInPackage(functionDescriptor, "suspendCoroutineUninterceptedOrReturn", strAsString);
    }
}
