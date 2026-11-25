package org.jetbrains.kotlin.builtins.functions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FunctionTypeKind.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0015\u0010\u0004\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0003\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0003¨\u0006\u0006"}, d2 = {"isBasicFunctionOrKFunction", "", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "(Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;)Z", "isBuiltin", "isSuspendOrKSuspendFunction", "compiler.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FunctionTypeKindKt {
    public static final boolean isBuiltin(FunctionTypeKind functionTypeKind) {
        Intrinsics.checkNotNullParameter(functionTypeKind, "<this>");
        if (Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.Function.INSTANCE) ? true : Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.SuspendFunction.INSTANCE) ? true : Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.KFunction.INSTANCE)) {
            return true;
        }
        return Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.KSuspendFunction.INSTANCE);
    }

    public static final boolean isSuspendOrKSuspendFunction(FunctionTypeKind functionTypeKind) {
        Intrinsics.checkNotNullParameter(functionTypeKind, "<this>");
        return Intrinsics.areEqual(functionTypeKind.nonReflectKind(), FunctionTypeKind.SuspendFunction.INSTANCE);
    }

    public static final boolean isBasicFunctionOrKFunction(FunctionTypeKind functionTypeKind) {
        Intrinsics.checkNotNullParameter(functionTypeKind, "<this>");
        return Intrinsics.areEqual(functionTypeKind.nonReflectKind(), FunctionTypeKind.Function.INSTANCE);
    }
}
