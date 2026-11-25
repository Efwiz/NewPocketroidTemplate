package org.jetbrains.kotlin.resolve.calls.inference.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;

/* JADX INFO: compiled from: ConstraintPositionAndErrorsImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/NotEnoughInformationForTypeParameterImpl;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NotEnoughInformationForTypeParameter;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "typeVariable", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "resolvedAtom", "couldBeResolvedWithUnrestrictedBuilderInference", "", "(Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;Z)V", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NotEnoughInformationForTypeParameterImpl extends NotEnoughInformationForTypeParameter<ResolvedAtom> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotEnoughInformationForTypeParameterImpl(TypeVariableMarker typeVariable, ResolvedAtom resolvedAtom, boolean z) {
        super(typeVariable, resolvedAtom, z);
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        Intrinsics.checkNotNullParameter(resolvedAtom, "resolvedAtom");
    }
}
