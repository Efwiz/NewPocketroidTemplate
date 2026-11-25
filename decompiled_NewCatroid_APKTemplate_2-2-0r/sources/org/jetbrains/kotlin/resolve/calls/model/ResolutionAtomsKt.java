package org.jetbrains.kotlin.resolve.calls.model;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: ResolutionAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0005\u001a\u0004\u0018\u00010\u0002*\u00020\u0006\u001a\n\u0010\u0007\u001a\u00020\b*\u00020\b\"\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\t"}, d2 = {"freshReturnType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "getFreshReturnType", "(Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;)Lorg/jetbrains/kotlin/types/UnwrappedType;", "resultCallAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/CallResolutionResult;", "unwrap", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedLambdaAtom;", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ResolutionAtomsKt {
    public static final ResolvedLambdaAtom unwrap(ResolvedLambdaAtom resolvedLambdaAtom) {
        Intrinsics.checkNotNullParameter(resolvedLambdaAtom, "<this>");
        if (resolvedLambdaAtom.getResultArgumentsInfo() != null) {
            return resolvedLambdaAtom;
        }
        List<ResolvedAtom> subResolvedAtoms = resolvedLambdaAtom.getSubResolvedAtoms();
        Intrinsics.checkNotNull(subResolvedAtoms);
        Object objSingle = CollectionsKt.single((List<? extends Object>) subResolvedAtoms);
        Intrinsics.checkNotNull(objSingle, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.ResolvedLambdaAtom");
        return (ResolvedLambdaAtom) objSingle;
    }

    public static final ResolvedCallAtom resultCallAtom(CallResolutionResult callResolutionResult) {
        Intrinsics.checkNotNullParameter(callResolutionResult, "<this>");
        if (callResolutionResult instanceof SingleCallResolutionResult) {
            return ((SingleCallResolutionResult) callResolutionResult).getResultCallAtom();
        }
        return null;
    }

    public static final UnwrappedType getFreshReturnType(ResolvedCallAtom resolvedCallAtom) {
        Intrinsics.checkNotNullParameter(resolvedCallAtom, "<this>");
        KotlinType returnType = resolvedCallAtom.get_candidateDescriptor().getReturnType();
        if (returnType == null) {
            return null;
        }
        return resolvedCallAtom.getFreshVariablesSubstitutor().safeSubstitute(returnType.unwrap());
    }
}
