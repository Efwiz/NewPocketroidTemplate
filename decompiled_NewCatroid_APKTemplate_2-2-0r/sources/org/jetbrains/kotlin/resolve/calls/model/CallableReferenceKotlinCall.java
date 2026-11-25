package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ResolutionAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCall;", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceResolutionAtom;", "call", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "lhsResult", "Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult;", "rhsName", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult;Lorg/jetbrains/kotlin/name/Name;)V", "getCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "getLhsResult", "()Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult;", "getRhsName", "()Lorg/jetbrains/kotlin/name/Name;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallableReferenceKotlinCall implements CallableReferenceResolutionAtom {
    private final KotlinCall call;
    private final LHSResult lhsResult;
    private final Name rhsName;

    public CallableReferenceKotlinCall(KotlinCall call, LHSResult lhsResult, Name rhsName) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(lhsResult, "lhsResult");
        Intrinsics.checkNotNullParameter(rhsName, "rhsName");
        this.call = call;
        this.lhsResult = lhsResult;
        this.rhsName = rhsName;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.CallableReferenceResolutionAtom
    public KotlinCall getCall() {
        return this.call;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.CallableReferenceResolutionAtom
    public LHSResult getLhsResult() {
        return this.lhsResult;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.CallableReferenceResolutionAtom
    public Name getRhsName() {
        return this.rhsName;
    }
}
