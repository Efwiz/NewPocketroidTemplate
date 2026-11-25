package org.jetbrains.kotlin.fir.types;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirPureAbstractElement;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirTypeRef.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\b\u0004¢\u0006\u0002\u0010\u0003J5\u0010\r\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u0010\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u0002H\u000f0\u00112\u0006\u0010\u0012\u001a\u0002H\u000fH\u0016¢\u0006\u0002\u0010\u0013J\u0016\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&J3\u0010\u0017\u001a\u0002H\u0018\"\b\b\u0000\u0010\u0018*\u00020\u0019\"\u0004\b\u0001\u0010\u000f2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u001b2\u0006\u0010\u0012\u001a\u0002H\u000fH\u0016¢\u0006\u0002\u0010\u001cJ)\u0010\u001d\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u000f2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u001b2\u0006\u0010\u0012\u001a\u0002H\u000fH&¢\u0006\u0002\u0010\u001eR\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u0004\u0018\u00010\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f\u0082\u0001\u0003\u001f !¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "Lorg/jetbrains/kotlin/fir/FirPureAbstractElement;", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "Lorg/jetbrains/kotlin/fir/types/FirImplicitTypeRef;", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRefWithNullability;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirTypeRef extends FirPureAbstractElement implements FirAnnotationContainer {
    public /* synthetic */ FirTypeRef(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract List<FirAnnotation> getAnnotations();

    @Override // org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    public abstract <D> FirTypeRef transformAnnotations(FirTransformer<? super D> transformer, D data);

    private FirTypeRef() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitTypeRef(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTypeRef firTypeRefTransformTypeRef = transformer.transformTypeRef(this, data);
        Intrinsics.checkNotNull(firTypeRefTransformTypeRef, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.types.FirTypeRef.transform");
        return firTypeRefTransformTypeRef;
    }
}
