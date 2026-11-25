package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ResolutionAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r\u0082\u0001\u0002\u000e\u000fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceResolutionAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolutionAtom;", "call", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "getCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "lhsResult", "Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult;", "getLhsResult", "()Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult;", "rhsName", "Lorg/jetbrains/kotlin/name/Name;", "getRhsName", "()Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCall;", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCallArgument;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CallableReferenceResolutionAtom extends ResolutionAtom {
    KotlinCall getCall();

    LHSResult getLhsResult();

    Name getRhsName();
}
