package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: ArgumentMapping.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u0002¨\u0006\u0003"}, d2 = {"isReallySuccess", "", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ArgumentMappingKt {
    public static final boolean isReallySuccess(ResolvedCall<?> resolvedCall) {
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        return resolvedCall.getStatus().isSuccess() && !ErrorUtils.isError(resolvedCall.getResultingDescriptor());
    }
}
