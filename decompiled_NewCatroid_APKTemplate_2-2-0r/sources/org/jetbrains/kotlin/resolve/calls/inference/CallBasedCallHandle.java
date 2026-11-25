package org.jetbrains.kotlin.resolve.calls.inference;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.Call;

/* JADX INFO: compiled from: TypeVariable.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0096\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/CallBasedCallHandle;", "Lorg/jetbrains/kotlin/resolve/calls/inference/CallHandle;", "call", "Lorg/jetbrains/kotlin/psi/Call;", "(Lorg/jetbrains/kotlin/psi/Call;)V", "getCall", "()Lorg/jetbrains/kotlin/psi/Call;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallBasedCallHandle implements CallHandle {
    private final Call call;

    public CallBasedCallHandle(Call call) {
        Intrinsics.checkNotNullParameter(call, "call");
        this.call = call;
    }

    public final Call getCall() {
        return this.call;
    }

    public boolean equals(Object other) {
        return (other instanceof CallBasedCallHandle) && this.call == ((CallBasedCallHandle) other).call;
    }

    public int hashCode() {
        return System.identityHashCode(this.call);
    }

    public String toString() {
        return this.call.toString();
    }
}
