package org.jetbrains.kotlin.fir.resolve.transformers;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;

/* JADX INFO: compiled from: FirAbstractTreeTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J'\u0010\u0006\u001a\u0002H\u0007\"\b\b\u0001\u0010\u0007*\u00020\b2\u0006\u0010\t\u001a\u0002H\u00072\u0006\u0010\n\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/FirAbstractTreeTransformer;", "D", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirAbstractPhaseTransformer;", TypedValues.CycleType.S_WAVE_PHASE, "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", "transformElement", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "data", "(Lorg/jetbrains/kotlin/fir/FirElement;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirAbstractTreeTransformer<D> extends FirAbstractPhaseTransformer<D> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirAbstractTreeTransformer(FirResolvePhase phase) {
        super(phase);
        Intrinsics.checkNotNullParameter(phase, "phase");
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public <E extends FirElement> E transformElement(E element, D data) {
        Intrinsics.checkNotNullParameter(element, "element");
        E e = (E) element.transformChildren(this, data);
        Intrinsics.checkNotNull(e, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.resolve.transformers.FirAbstractTreeTransformer.transformElement");
        return e;
    }
}
