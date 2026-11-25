package org.jetbrains.kotlin.fir.types;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirFunctionTypeParameter;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirFunctionTypeRef.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010\u001b\u001a\u0002H\u001c\"\u0004\b\u0000\u0010\u001c\"\u0004\b\u0001\u0010\u001d2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u0002H\u001c\u0012\u0004\u0012\u0002H\u001d0\u001f2\u0006\u0010 \u001a\u0002H\u001dH\u0016¢\u0006\u0002\u0010!J\u0016\u0010\"\u001a\u00020#2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H&J3\u0010%\u001a\u0002H&\"\b\b\u0000\u0010&*\u00020'\"\u0004\b\u0001\u0010\u001d2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H\u001d0)2\u0006\u0010 \u001a\u0002H\u001dH\u0016¢\u0006\u0002\u0010*J)\u0010+\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001d2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H\u001d0)2\u0006\u0010 \u001a\u0002H\u001dH&¢\u0006\u0002\u0010,R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0007R\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\rR\u0012\u0010\u000e\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\rR\u0018\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0007R\u0014\u0010\u0012\u001a\u0004\u0018\u00010\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0012\u0010\u0015\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0014R\u0014\u0010\u0017\u001a\u0004\u0018\u00010\u0018X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/FirFunctionTypeRef;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRefWithNullability;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "contextReceiverTypeRefs", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getContextReceiverTypeRefs", "isMarkedNullable", "", "()Z", "isSuspend", "parameters", "Lorg/jetbrains/kotlin/fir/FirFunctionTypeParameter;", "getParameters", "receiverTypeRef", "getReceiverTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "returnTypeRef", "getReturnTypeRef", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirFunctionTypeRef;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirFunctionTypeRef extends FirTypeRefWithNullability {
    @Override // org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    public abstract List<FirTypeRef> getContextReceiverTypeRefs();

    public abstract List<FirFunctionTypeParameter> getParameters();

    public abstract FirTypeRef getReceiverTypeRef();

    public abstract FirTypeRef getReturnTypeRef();

    @Override // org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability
    /* JADX INFO: renamed from: isMarkedNullable */
    public abstract boolean getIsMarkedNullable();

    public abstract boolean isSuspend();

    @Override // org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    @Override // org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirFunctionTypeRef transformAnnotations(FirTransformer<? super D> transformer, D data);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitFunctionTypeRef(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTypeRef firTypeRefTransformFunctionTypeRef = transformer.transformFunctionTypeRef(this, data);
        Intrinsics.checkNotNull(firTypeRefTransformFunctionTypeRef, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.types.FirFunctionTypeRef.transform");
        return firTypeRefTransformFunctionTypeRef;
    }
}
