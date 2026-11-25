package org.jetbrains.kotlin.fir.resolve.inference;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateFactoryKt;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.visitors.FirDefaultTransformer;

/* JADX INFO: compiled from: FirBuilderInferenceSession.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002J)\u0010\n\u001a\u0002H\u000b\"\b\b\u0000\u0010\u000b*\u00020\f2\u0006\u0010\r\u001a\u0002H\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0002H\u0016¢\u0006\u0002\u0010\u000fJ\u001a\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u000e\u001a\u0004\u0018\u00010\u0002H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/FirStubTypeTransformer;", "Lorg/jetbrains/kotlin/fir/visitors/FirDefaultTransformer;", "", "substitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "(Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;)V", "processCandidate", "", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "transformElement", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "data", "(Lorg/jetbrains/kotlin/fir/FirElement;Ljava/lang/Void;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformResolvedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "resolvedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirStubTypeTransformer extends FirDefaultTransformer {
    private final ConeSubstitutor substitutor;

    public FirStubTypeTransformer(ConeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        this.substitutor = substitutor;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public <E extends FirElement> E transformElement(E element, Void data) {
        Candidate candidate;
        Intrinsics.checkNotNullParameter(element, "element");
        if ((element instanceof FirResolvable) && (candidate = CandidateFactoryKt.candidate((FirResolvable) element)) != null) {
            processCandidate(candidate);
        }
        E e = (E) element.transformChildren(this, null);
        Intrinsics.checkNotNull(e, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.resolve.inference.FirStubTypeTransformer.transformElement");
        return e;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirTypeRef transformResolvedTypeRef(FirResolvedTypeRef resolvedTypeRef, Void data) {
        FirResolvedTypeRef firResolvedTypeRefWithReplacedConeType$default;
        Intrinsics.checkNotNullParameter(resolvedTypeRef, "resolvedTypeRef");
        ConeKotlinType coneKotlinTypeSubstituteOrNull = this.substitutor.substituteOrNull(resolvedTypeRef.getType());
        if (coneKotlinTypeSubstituteOrNull != null && (firResolvedTypeRefWithReplacedConeType$default = TypeUtilsKt.withReplacedConeType$default(resolvedTypeRef, coneKotlinTypeSubstituteOrNull, null, 2, null)) != null) {
            return firResolvedTypeRefWithReplacedConeType$default;
        }
        return resolvedTypeRef;
    }

    private final void processCandidate(Candidate candidate) {
        FirExpression dispatchReceiver = candidate.getDispatchReceiver();
        ArrayList arrayList = null;
        candidate.setDispatchReceiver(dispatchReceiver != null ? (FirExpression) dispatchReceiver.transform(this, null) : null);
        FirExpression chosenExtensionReceiver = candidate.getChosenExtensionReceiver();
        candidate.setChosenExtensionReceiver(chosenExtensionReceiver != null ? (FirExpression) chosenExtensionReceiver.transform(this, null) : null);
        List<FirExpression> contextReceiverArguments = candidate.getContextReceiverArguments();
        if (contextReceiverArguments != null) {
            List<FirExpression> list = contextReceiverArguments;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList2.add((FirExpression) ((FirExpression) it.next()).transform(this, null));
            }
            arrayList = arrayList2;
        }
        candidate.setContextReceiverArguments(arrayList);
    }
}
