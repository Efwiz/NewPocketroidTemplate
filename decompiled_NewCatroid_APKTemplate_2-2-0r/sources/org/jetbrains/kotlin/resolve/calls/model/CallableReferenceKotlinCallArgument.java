package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;

/* JADX INFO: compiled from: KotlinCallArguments.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u00012\u00020\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000eÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCallArgument;", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponableKotlinCallArgument;", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceResolutionAtom;", "call", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "getCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "isSpread", "", "()Z", "lhsResult", "Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult;", "getLhsResult", "()Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CallableReferenceKotlinCallArgument extends CallableReferenceResolutionAtom, PostponableKotlinCallArgument {
    @Override // org.jetbrains.kotlin.resolve.calls.model.CallableReferenceResolutionAtom
    KotlinCall getCall();

    @Override // org.jetbrains.kotlin.resolve.calls.model.CallableReferenceResolutionAtom
    LHSResult getLhsResult();

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument
    default boolean isSpread() {
        return false;
    }
}
