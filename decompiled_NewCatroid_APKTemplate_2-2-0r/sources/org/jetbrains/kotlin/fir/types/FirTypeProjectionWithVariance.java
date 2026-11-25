package org.jetbrains.kotlin.fir.types;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: FirTypeProjectionWithVariance.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010\u00112\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u00110\u00132\u0006\u0010\u0014\u001a\u0002H\u0011H\u0016¢\u0006\u0002\u0010\u0015J3\u0010\u0016\u001a\u0002H\u0017\"\b\b\u0000\u0010\u0017*\u00020\u0018\"\u0004\b\u0001\u0010\u00112\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00110\u001a2\u0006\u0010\u0014\u001a\u0002H\u0011H\u0016¢\u0006\u0002\u0010\u001bR\u0014\u0010\u0003\u001a\u0004\u0018\u00010\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/FirTypeProjectionWithVariance;", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "()V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "getVariance", "()Lorg/jetbrains/kotlin/types/Variance;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirTypeProjectionWithVariance extends FirTypeProjection {
    @Override // org.jetbrains.kotlin.fir.types.FirTypeProjection, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    public abstract FirTypeRef getTypeRef();

    public abstract Variance getVariance();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.types.FirTypeProjection, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitTypeProjectionWithVariance(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.types.FirTypeProjection, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTypeProjection firTypeProjectionTransformTypeProjectionWithVariance = transformer.transformTypeProjectionWithVariance(this, data);
        Intrinsics.checkNotNull(firTypeProjectionTransformTypeProjectionWithVariance, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.types.FirTypeProjectionWithVariance.transform");
        return firTypeProjectionTransformTypeProjectionWithVariance;
    }
}
