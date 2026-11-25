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
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirAnnotationCall.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J5\u0010*\u001a\u0002H+\"\u0004\b\u0000\u0010+\"\u0004\b\u0001\u0010,2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u0002H+\u0012\u0004\u0012\u0002H,0.2\u0006\u0010/\u001a\u0002H,H\u0016¢\u0006\u0002\u00100J\u0010\u00101\u001a\u0002022\u0006\u00103\u001a\u00020\u0006H&J\u0010\u00104\u001a\u0002022\u0006\u00105\u001a\u00020\nH&J\u0016\u00106\u001a\u0002022\f\u00107\u001a\b\u0012\u0004\u0012\u00020\u00010\u000eH&J\u0010\u00108\u001a\u0002022\u0006\u00109\u001a\u00020\u0012H&J\u0010\u0010:\u001a\u0002022\u0006\u0010;\u001a\u00020\u0016H&J\u0010\u0010<\u001a\u0002022\u0006\u0010=\u001a\u00020\u001aH&J\u0016\u0010>\u001a\u0002022\f\u0010?\u001a\b\u0012\u0004\u0012\u00020\"0\u000eH&J\u0010\u0010@\u001a\u0002022\u0006\u0010A\u001a\u00020\nH&J\u0012\u0010B\u001a\u0002022\b\u0010C\u001a\u0004\u0018\u00010'H&J3\u0010D\u001a\u0002HE\"\b\b\u0000\u0010E*\u00020F\"\u0004\b\u0001\u0010,2\f\u0010G\u001a\b\u0012\u0004\u0012\u0002H,0H2\u0006\u0010/\u001a\u0002H,H\u0016¢\u0006\u0002\u0010IJ)\u0010J\u001a\u00020\u0000\"\u0004\b\u0000\u0010,2\f\u0010G\u001a\b\u0012\u0004\u0012\u0002H,0H2\u0006\u0010/\u001a\u0002H,H&¢\u0006\u0002\u0010KJ)\u0010L\u001a\u00020\u0000\"\u0004\b\u0000\u0010,2\f\u0010G\u001a\b\u0012\u0004\u0012\u0002H,0H2\u0006\u0010/\u001a\u0002H,H&¢\u0006\u0002\u0010KJ)\u0010M\u001a\u00020\u0000\"\u0004\b\u0000\u0010,2\f\u0010G\u001a\b\u0012\u0004\u0012\u0002H,0H2\u0006\u0010/\u001a\u0002H,H&¢\u0006\u0002\u0010KJ)\u0010N\u001a\u00020\u0000\"\u0004\b\u0000\u0010,2\f\u0010G\u001a\b\u0012\u0004\u0012\u0002H,0H2\u0006\u0010/\u001a\u0002H,H&¢\u0006\u0002\u0010KR\u0012\u0010\u0005\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0018\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0012\u0010\u0015\u001a\u00020\u0016X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0012\u0010\u0019\u001a\u00020\u001aX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u001d\u001a\u0004\u0018\u00010\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0018\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b#\u0010\u0010R\u0012\u0010$\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b%\u0010\fR\u0014\u0010&\u001a\u0004\u0018\u00010'X¦\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)¨\u0006O"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "Lorg/jetbrains/kotlin/fir/expressions/FirCall;", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;", "()V", "annotationResolvePhase", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationResolvePhase;", "getAnnotationResolvePhase", "()Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationResolvePhase;", "annotationTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getAnnotationTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "annotations", "", "getAnnotations", "()Ljava/util/List;", "argumentList", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "getArgumentList", "()Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "argumentMapping", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;", "getArgumentMapping", "()Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirReference;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "getTypeArguments", "typeRef", "getTypeRef", "useSiteTarget", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "getUseSiteTarget", "()Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotationResolvePhase", "", "newAnnotationResolvePhase", "replaceAnnotationTypeRef", "newAnnotationTypeRef", "replaceAnnotations", "newAnnotations", "replaceArgumentList", "newArgumentList", "replaceArgumentMapping", "newArgumentMapping", "replaceCalleeReference", "newCalleeReference", "replaceTypeArguments", "newTypeArguments", "replaceTypeRef", "newTypeRef", "replaceUseSiteTarget", "newUseSiteTarget", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotationTypeRef", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "transformAnnotations", "transformCalleeReference", "transformTypeArguments", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirAnnotationCall extends FirAnnotation implements FirCall, FirResolvable {
    public abstract FirAnnotationResolvePhase getAnnotationResolvePhase();

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public abstract FirTypeRef getAnnotationTypeRef();

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    @Override // org.jetbrains.kotlin.fir.expressions.FirCall
    public abstract FirArgumentList getArgumentList();

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public abstract FirAnnotationArgumentMapping getArgumentMapping();

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvable
    public abstract FirReference getCalleeReference();

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public abstract List<FirTypeProjection> getTypeArguments();

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract FirTypeRef getTypeRef();

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public abstract AnnotationUseSiteTarget getUseSiteTarget();

    public abstract void replaceAnnotationResolvePhase(FirAnnotationResolvePhase newAnnotationResolvePhase);

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public abstract void replaceAnnotationTypeRef(FirTypeRef newAnnotationTypeRef);

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    @Override // org.jetbrains.kotlin.fir.expressions.FirCall
    public abstract void replaceArgumentList(FirArgumentList newArgumentList);

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public abstract void replaceArgumentMapping(FirAnnotationArgumentMapping newArgumentMapping);

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvable
    public abstract void replaceCalleeReference(FirReference newCalleeReference);

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public abstract void replaceTypeArguments(List<? extends FirTypeProjection> newTypeArguments);

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract void replaceTypeRef(FirTypeRef newTypeRef);

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public abstract void replaceUseSiteTarget(AnnotationUseSiteTarget newUseSiteTarget);

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public abstract <D> FirAnnotationCall transformAnnotationTypeRef(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirAnnotationCall transformAnnotations(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvable
    public abstract <D> FirAnnotationCall transformCalleeReference(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public abstract <D> FirAnnotationCall transformTypeArguments(FirTransformer<? super D> transformer, D data);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitAnnotationCall(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformAnnotationCall = transformer.transformAnnotationCall(this, data);
        Intrinsics.checkNotNull(firStatementTransformAnnotationCall, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.expressions.FirAnnotationCall.transform");
        return firStatementTransformAnnotationCall;
    }
}
