package org.jetbrains.kotlin.fir.resolve.providers.impl;

import java.util.function.BiFunction;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FirProviderImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
final class FirProviderImplKt$sam$java_util_function_BiFunction$0 implements BiFunction {
    private final /* synthetic */ Function2 function;

    FirProviderImplKt$sam$java_util_function_BiFunction$0(Function2 function) {
        Intrinsics.checkNotNullParameter(function, "function");
        this.function = function;
    }

    @Override // java.util.function.BiFunction
    public final /* synthetic */ Object apply(Object obj, Object obj2) {
        return this.function.invoke(obj, obj2);
    }
}
