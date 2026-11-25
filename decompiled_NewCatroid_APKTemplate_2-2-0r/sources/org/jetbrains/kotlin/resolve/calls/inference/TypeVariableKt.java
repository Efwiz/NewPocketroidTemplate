package org.jetbrains.kotlin.resolve.calls.inference;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.psi.Call;

/* JADX INFO: compiled from: TypeVariable.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"toHandle", "Lorg/jetbrains/kotlin/resolve/calls/inference/CallHandle;", "Lorg/jetbrains/kotlin/psi/Call;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeVariableKt {
    public static final CallHandle toHandle(Call call) {
        Intrinsics.checkNotNullParameter(call, "<this>");
        return new CallBasedCallHandle(call);
    }
}
