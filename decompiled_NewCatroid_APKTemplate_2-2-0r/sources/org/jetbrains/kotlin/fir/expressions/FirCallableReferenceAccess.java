package org.jetbrains.kotlin.fir.expressions;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirImplementationDetail;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirCallableReferenceAccess.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010%\u001a\u0002H&\"\u0004\b\u0000\u0010&\"\u0004\b\u0001\u0010'2\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u0002H&\u0012\u0004\u0012\u0002H'0)2\u0006\u0010*\u001a\u0002H'H\u0016¢\u0006\u0002\u0010+J\u0016\u0010,\u001a\u00020-2\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H&J\u0010\u0010/\u001a\u00020-2\u0006\u00100\u001a\u00020\tH&J\u0010\u0010/\u001a\u00020-2\u0006\u00100\u001a\u000201H&J\u0016\u00102\u001a\u00020-2\f\u00103\u001a\b\u0012\u0004\u0012\u00020\r0\u0004H&J\u0010\u00104\u001a\u00020-2\u0006\u00105\u001a\u00020\rH&J\u0012\u00106\u001a\u00020-2\b\u00107\u001a\u0004\u0018\u00010\rH&J\u0010\u00108\u001a\u00020-2\u0006\u00109\u001a\u00020\rH&J\u0010\u0010:\u001a\u00020-2\u0006\u0010;\u001a\u00020\u0017H&J\u0012\u0010<\u001a\u00020-2\b\u0010=\u001a\u0004\u0018\u00010\u001bH'J\u0016\u0010>\u001a\u00020-2\f\u0010?\u001a\b\u0012\u0004\u0012\u00020\u001f0\u0004H&J\u0010\u0010@\u001a\u00020-2\u0006\u0010A\u001a\u00020\"H&J3\u0010B\u001a\u0002HC\"\b\b\u0000\u0010C*\u00020D\"\u0004\b\u0001\u0010'2\f\u0010E\u001a\b\u0012\u0004\u0012\u0002H'0F2\u0006\u0010*\u001a\u0002H'H\u0016¢\u0006\u0002\u0010GJ)\u0010H\u001a\u00020\u0000\"\u0004\b\u0000\u0010'2\f\u0010E\u001a\b\u0012\u0004\u0012\u0002H'0F2\u0006\u0010*\u001a\u0002H'H&¢\u0006\u0002\u0010IJ)\u0010J\u001a\u00020\u0000\"\u0004\b\u0000\u0010'2\f\u0010E\u001a\b\u0012\u0004\u0012\u0002H'0F2\u0006\u0010*\u001a\u0002H'H&¢\u0006\u0002\u0010IJ)\u0010K\u001a\u00020\u0000\"\u0004\b\u0000\u0010'2\f\u0010E\u001a\b\u0012\u0004\u0012\u0002H'0F2\u0006\u0010*\u001a\u0002H'H&¢\u0006\u0002\u0010IJ)\u0010L\u001a\u00020\u0000\"\u0004\b\u0000\u0010'2\f\u0010E\u001a\b\u0012\u0004\u0012\u0002H'0F2\u0006\u0010*\u001a\u0002H'H&¢\u0006\u0002\u0010IR\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0018\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0007R\u0012\u0010\u000f\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u0004\u0018\u00010\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0011R\u0012\u0010\u0014\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0011R\u0012\u0010\u0016\u001a\u00020\u0017X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001a\u001a\u0004\u0018\u00010\u001bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0018\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b \u0010\u0007R\u0012\u0010!\u001a\u00020\"X¦\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$¨\u0006M"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "contextReceiverArguments", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getContextReceiverArguments", "dispatchReceiver", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "explicitReceiver", "getExplicitReceiver", "extensionReceiver", "getExtensionReceiver", "hasQuestionMarkAtLHS", "", "getHasQuestionMarkAtLHS", "()Z", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "getTypeArguments", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "replaceCalleeReference", "newCalleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "replaceContextReceiverArguments", "newContextReceiverArguments", "replaceDispatchReceiver", "newDispatchReceiver", "replaceExplicitReceiver", "newExplicitReceiver", "replaceExtensionReceiver", "newExtensionReceiver", "replaceHasQuestionMarkAtLHS", "newHasQuestionMarkAtLHS", "replaceSource", "newSource", "replaceTypeArguments", "newTypeArguments", "replaceTypeRef", "newTypeRef", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "transformCalleeReference", "transformExplicitReceiver", "transformTypeArguments", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirCallableReferenceAccess extends FirQualifiedAccessExpression {
    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public abstract FirNamedReference getCalleeReference();

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirContextReceiverArgumentListOwner
    public abstract List<FirExpression> getContextReceiverArguments();

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public abstract FirExpression getDispatchReceiver();

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public abstract FirExpression getExplicitReceiver();

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public abstract FirExpression getExtensionReceiver();

    public abstract boolean getHasQuestionMarkAtLHS();

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public abstract List<FirTypeProjection> getTypeArguments();

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract FirTypeRef getTypeRef();

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    public abstract void replaceCalleeReference(FirNamedReference newCalleeReference);

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public abstract void replaceCalleeReference(FirReference newCalleeReference);

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirContextReceiverArgumentListOwner
    public abstract void replaceContextReceiverArguments(List<? extends FirExpression> newContextReceiverArguments);

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public abstract void replaceDispatchReceiver(FirExpression newDispatchReceiver);

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public abstract void replaceExplicitReceiver(FirExpression newExplicitReceiver);

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public abstract void replaceExtensionReceiver(FirExpression newExtensionReceiver);

    public abstract void replaceHasQuestionMarkAtLHS(boolean newHasQuestionMarkAtLHS);

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    @FirImplementationDetail
    public abstract void replaceSource(KtSourceElement newSource);

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public abstract void replaceTypeArguments(List<? extends FirTypeProjection> newTypeArguments);

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract void replaceTypeRef(FirTypeRef newTypeRef);

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirCallableReferenceAccess transformAnnotations(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public abstract <D> FirCallableReferenceAccess transformCalleeReference(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public abstract <D> FirCallableReferenceAccess transformExplicitReceiver(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public abstract <D> FirCallableReferenceAccess transformTypeArguments(FirTransformer<? super D> transformer, D data);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitCallableReferenceAccess(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformCallableReferenceAccess = transformer.transformCallableReferenceAccess(this, data);
        Intrinsics.checkNotNull(firStatementTransformCallableReferenceAccess, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess.transform");
        return firStatementTransformCallableReferenceAccess;
    }
}
