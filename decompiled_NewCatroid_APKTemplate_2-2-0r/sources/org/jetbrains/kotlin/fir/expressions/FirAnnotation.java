package org.jetbrains.kotlin.fir.expressions;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirAnnotation.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010\u001c\u001a\u0002H\u001d\"\u0004\b\u0000\u0010\u001d\"\u0004\b\u0001\u0010\u001e2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u0002H\u001d\u0012\u0004\u0012\u0002H\u001e0 2\u0006\u0010!\u001a\u0002H\u001eH\u0016¢\u0006\u0002\u0010\"J\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u0004H&J\u0016\u0010&\u001a\u00020$2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00000\bH&J\u0010\u0010(\u001a\u00020$2\u0006\u0010)\u001a\u00020\fH&J\u0016\u0010*\u001a\u00020$2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00140\bH&J\u0010\u0010,\u001a\u00020$2\u0006\u0010-\u001a\u00020\u0004H&J\u0012\u0010.\u001a\u00020$2\b\u0010/\u001a\u0004\u0018\u00010\u0019H&J3\u00100\u001a\u0002H1\"\b\b\u0000\u00101*\u000202\"\u0004\b\u0001\u0010\u001e2\f\u00103\u001a\b\u0012\u0004\u0012\u0002H\u001e042\u0006\u0010!\u001a\u0002H\u001eH\u0016¢\u0006\u0002\u00105J)\u00106\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001e2\f\u00103\u001a\b\u0012\u0004\u0012\u0002H\u001e042\u0006\u0010!\u001a\u0002H\u001eH&¢\u0006\u0002\u00107J)\u00108\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001e2\f\u00103\u001a\b\u0012\u0004\u0012\u0002H\u001e042\u0006\u0010!\u001a\u0002H\u001eH&¢\u0006\u0002\u00107J)\u00109\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001e2\f\u00103\u001a\b\u0012\u0004\u0012\u0002H\u001e042\u0006\u0010!\u001a\u0002H\u001eH&¢\u0006\u0002\u00107R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00000\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u0004\u0018\u00010\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0018\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\nR\u0012\u0010\u0016\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0006R\u0014\u0010\u0018\u001a\u0004\u0018\u00010\u0019X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b¨\u0006:"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "()V", "annotationTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getAnnotationTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "annotations", "", "getAnnotations", "()Ljava/util/List;", "argumentMapping", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;", "getArgumentMapping", "()Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "getTypeArguments", "typeRef", "getTypeRef", "useSiteTarget", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "getUseSiteTarget", "()Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotationTypeRef", "", "newAnnotationTypeRef", "replaceAnnotations", "newAnnotations", "replaceArgumentMapping", "newArgumentMapping", "replaceTypeArguments", "newTypeArguments", "replaceTypeRef", "newTypeRef", "replaceUseSiteTarget", "newUseSiteTarget", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotationTypeRef", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "transformAnnotations", "transformTypeArguments", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirAnnotation extends FirExpression {
    public abstract FirTypeRef getAnnotationTypeRef();

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    public abstract FirAnnotationArgumentMapping getArgumentMapping();

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    public abstract List<FirTypeProjection> getTypeArguments();

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract FirTypeRef getTypeRef();

    public abstract AnnotationUseSiteTarget getUseSiteTarget();

    public abstract void replaceAnnotationTypeRef(FirTypeRef newAnnotationTypeRef);

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    public abstract void replaceArgumentMapping(FirAnnotationArgumentMapping newArgumentMapping);

    public abstract void replaceTypeArguments(List<? extends FirTypeProjection> newTypeArguments);

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract void replaceTypeRef(FirTypeRef newTypeRef);

    public abstract void replaceUseSiteTarget(AnnotationUseSiteTarget newUseSiteTarget);

    public abstract <D> FirAnnotation transformAnnotationTypeRef(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirAnnotation transformAnnotations(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirAnnotation transformTypeArguments(FirTransformer<? super D> transformer, D data);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitAnnotation(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformAnnotation = transformer.transformAnnotation(this, data);
        Intrinsics.checkNotNull(firStatementTransformAnnotation, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.expressions.FirAnnotation.transform");
        return firStatementTransformAnnotation;
    }
}
