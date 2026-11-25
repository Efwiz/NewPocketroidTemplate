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
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.FirDiagnosticHolder;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirErrorAnnotationCall.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J5\u0010.\u001a\u0002H/\"\u0004\b\u0000\u0010/\"\u0004\b\u0001\u001002\u0012\u00101\u001a\u000e\u0012\u0004\u0012\u0002H/\u0012\u0004\u0012\u0002H0022\u0006\u00103\u001a\u0002H0H\u0016¢\u0006\u0002\u00104J\u0010\u00105\u001a\u0002062\u0006\u00107\u001a\u00020\u0005H&J\u0010\u00108\u001a\u0002062\u0006\u00109\u001a\u00020\tH&J\u0016\u0010:\u001a\u0002062\f\u0010;\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH&J\u0010\u0010<\u001a\u0002062\u0006\u0010=\u001a\u00020\u0012H&J\u0010\u0010>\u001a\u0002062\u0006\u0010?\u001a\u00020\u0016H&J\u0010\u0010@\u001a\u0002062\u0006\u0010A\u001a\u00020\u001aH&J\u0016\u0010B\u001a\u0002062\f\u0010C\u001a\b\u0012\u0004\u0012\u00020&0\rH&J\u0010\u0010D\u001a\u0002062\u0006\u0010E\u001a\u00020\tH&J\u0012\u0010F\u001a\u0002062\b\u0010G\u001a\u0004\u0018\u00010+H&J3\u0010H\u001a\u0002HI\"\b\b\u0000\u0010I*\u00020J\"\u0004\b\u0001\u001002\f\u0010K\u001a\b\u0012\u0004\u0012\u0002H00L2\u0006\u00103\u001a\u0002H0H\u0016¢\u0006\u0002\u0010MJ)\u0010N\u001a\u00020\u0000\"\u0004\b\u0000\u001002\f\u0010K\u001a\b\u0012\u0004\u0012\u0002H00L2\u0006\u00103\u001a\u0002H0H&¢\u0006\u0002\u0010OJ)\u0010P\u001a\u00020\u0000\"\u0004\b\u0000\u001002\f\u0010K\u001a\b\u0012\u0004\u0012\u0002H00L2\u0006\u00103\u001a\u0002H0H&¢\u0006\u0002\u0010OJ)\u0010Q\u001a\u00020\u0000\"\u0004\b\u0000\u001002\f\u0010K\u001a\b\u0012\u0004\u0012\u0002H00L2\u0006\u00103\u001a\u0002H0H&¢\u0006\u0002\u0010OJ)\u0010R\u001a\u00020\u0000\"\u0004\b\u0000\u001002\f\u0010K\u001a\b\u0012\u0004\u0012\u0002H00L2\u0006\u00103\u001a\u0002H0H&¢\u0006\u0002\u0010OR\u0012\u0010\u0004\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0018\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0012\u0010\u0015\u001a\u00020\u0016X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0012\u0010\u0019\u001a\u00020\u001aX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0012\u0010\u001d\u001a\u00020\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u0004\u0018\u00010\"X¦\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$R\u0018\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\rX¦\u0004¢\u0006\u0006\u001a\u0004\b'\u0010\u0010R\u0012\u0010(\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b)\u0010\u000bR\u0014\u0010*\u001a\u0004\u0018\u00010+X¦\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-¨\u0006S"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/FirErrorAnnotationCall;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "Lorg/jetbrains/kotlin/fir/diagnostics/FirDiagnosticHolder;", "()V", "annotationResolvePhase", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationResolvePhase;", "getAnnotationResolvePhase", "()Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationResolvePhase;", "annotationTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getAnnotationTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "argumentList", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "getArgumentList", "()Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "argumentMapping", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;", "getArgumentMapping", "()Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirReference;", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "getDiagnostic", "()Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "getTypeArguments", "typeRef", "getTypeRef", "useSiteTarget", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "getUseSiteTarget", "()Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotationResolvePhase", "", "newAnnotationResolvePhase", "replaceAnnotationTypeRef", "newAnnotationTypeRef", "replaceAnnotations", "newAnnotations", "replaceArgumentList", "newArgumentList", "replaceArgumentMapping", "newArgumentMapping", "replaceCalleeReference", "newCalleeReference", "replaceTypeArguments", "newTypeArguments", "replaceTypeRef", "newTypeRef", "replaceUseSiteTarget", "newUseSiteTarget", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotationTypeRef", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirErrorAnnotationCall;", "transformAnnotations", "transformCalleeReference", "transformTypeArguments", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirErrorAnnotationCall extends FirAnnotationCall implements FirDiagnosticHolder {
    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall
    public abstract FirAnnotationResolvePhase getAnnotationResolvePhase();

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public abstract FirTypeRef getAnnotationTypeRef();

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirCall
    public abstract FirArgumentList getArgumentList();

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public abstract FirAnnotationArgumentMapping getArgumentMapping();

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public abstract FirReference getCalleeReference();

    @Override // org.jetbrains.kotlin.fir.diagnostics.FirDiagnosticHolder
    public abstract ConeDiagnostic getDiagnostic();

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public abstract List<FirTypeProjection> getTypeArguments();

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract FirTypeRef getTypeRef();

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public abstract AnnotationUseSiteTarget getUseSiteTarget();

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall
    public abstract void replaceAnnotationResolvePhase(FirAnnotationResolvePhase newAnnotationResolvePhase);

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public abstract void replaceAnnotationTypeRef(FirTypeRef newAnnotationTypeRef);

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirCall
    public abstract void replaceArgumentList(FirArgumentList newArgumentList);

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public abstract void replaceArgumentMapping(FirAnnotationArgumentMapping newArgumentMapping);

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public abstract void replaceCalleeReference(FirReference newCalleeReference);

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public abstract void replaceTypeArguments(List<? extends FirTypeProjection> newTypeArguments);

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract void replaceTypeRef(FirTypeRef newTypeRef);

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public abstract void replaceUseSiteTarget(AnnotationUseSiteTarget newUseSiteTarget);

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public abstract <D> FirErrorAnnotationCall transformAnnotationTypeRef(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirErrorAnnotationCall transformAnnotations(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public abstract <D> FirErrorAnnotationCall transformCalleeReference(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public abstract <D> FirErrorAnnotationCall transformTypeArguments(FirTransformer<? super D> transformer, D data);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitErrorAnnotationCall(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformErrorAnnotationCall = transformer.transformErrorAnnotationCall(this, data);
        Intrinsics.checkNotNull(firStatementTransformErrorAnnotationCall, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.expressions.FirErrorAnnotationCall.transform");
        return firStatementTransformErrorAnnotationCall;
    }
}
