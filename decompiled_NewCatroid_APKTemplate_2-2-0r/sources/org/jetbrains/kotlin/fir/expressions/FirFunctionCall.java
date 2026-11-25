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

/* JADX INFO: compiled from: FirFunctionCall.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J5\u0010*\u001a\u0002H+\"\u0004\b\u0000\u0010+\"\u0004\b\u0001\u0010,2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u0002H+\u0012\u0004\u0012\u0002H,0.2\u0006\u0010/\u001a\u0002H,H\u0016¢\u0006\u0002\u00100J\u0016\u00101\u001a\u0002022\f\u00103\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&J\u0010\u00104\u001a\u0002022\u0006\u00105\u001a\u00020\nH&J\u0010\u00106\u001a\u0002022\u0006\u00107\u001a\u00020\u000eH&J\u0010\u00106\u001a\u0002022\u0006\u00107\u001a\u000208H&J\u0016\u00109\u001a\u0002022\f\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00120\u0005H&J\u0010\u0010;\u001a\u0002022\u0006\u0010<\u001a\u00020\u0012H&J\u0012\u0010=\u001a\u0002022\b\u0010>\u001a\u0004\u0018\u00010\u0012H&J\u0010\u0010?\u001a\u0002022\u0006\u0010@\u001a\u00020\u0012H&J\u0012\u0010A\u001a\u0002022\b\u0010B\u001a\u0004\u0018\u00010 H'J\u0016\u0010C\u001a\u0002022\f\u0010D\u001a\b\u0012\u0004\u0012\u00020$0\u0005H&J\u0010\u0010E\u001a\u0002022\u0006\u0010F\u001a\u00020'H&J3\u0010G\u001a\u0002HH\"\b\b\u0000\u0010H*\u00020I\"\u0004\b\u0001\u0010,2\f\u0010J\u001a\b\u0012\u0004\u0012\u0002H,0K2\u0006\u0010/\u001a\u0002H,H\u0016¢\u0006\u0002\u0010LJ)\u0010M\u001a\u00020\u0000\"\u0004\b\u0000\u0010,2\f\u0010J\u001a\b\u0012\u0004\u0012\u0002H,0K2\u0006\u0010/\u001a\u0002H,H&¢\u0006\u0002\u0010NJ)\u0010O\u001a\u00020\u0000\"\u0004\b\u0000\u0010,2\f\u0010J\u001a\b\u0012\u0004\u0012\u0002H,0K2\u0006\u0010/\u001a\u0002H,H&¢\u0006\u0002\u0010NJ)\u0010P\u001a\u00020\u0000\"\u0004\b\u0000\u0010,2\f\u0010J\u001a\b\u0012\u0004\u0012\u0002H,0K2\u0006\u0010/\u001a\u0002H,H&¢\u0006\u0002\u0010NJ)\u0010Q\u001a\u00020\u0000\"\u0004\b\u0000\u0010,2\f\u0010J\u001a\b\u0012\u0004\u0012\u0002H,0K2\u0006\u0010/\u001a\u0002H,H&¢\u0006\u0002\u0010NR\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0018\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\bR\u0012\u0010\u0014\u001a\u00020\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u0004\u0018\u00010\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0016R\u0012\u0010\u0019\u001a\u00020\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0016R\u0012\u0010\u001b\u001a\u00020\u001cX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010\u001f\u001a\u0004\u0018\u00010 X¦\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u0018\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b%\u0010\bR\u0012\u0010&\u001a\u00020'X¦\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)¨\u0006R"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/expressions/FirCall;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "argumentList", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "getArgumentList", "()Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "contextReceiverArguments", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getContextReceiverArguments", "dispatchReceiver", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "explicitReceiver", "getExplicitReceiver", "extensionReceiver", "getExtensionReceiver", "origin", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCallOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCallOrigin;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "getTypeArguments", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "replaceArgumentList", "newArgumentList", "replaceCalleeReference", "newCalleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "replaceContextReceiverArguments", "newContextReceiverArguments", "replaceDispatchReceiver", "newDispatchReceiver", "replaceExplicitReceiver", "newExplicitReceiver", "replaceExtensionReceiver", "newExtensionReceiver", "replaceSource", "newSource", "replaceTypeArguments", "newTypeArguments", "replaceTypeRef", "newTypeRef", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "transformCalleeReference", "transformExplicitReceiver", "transformTypeArguments", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirFunctionCall extends FirQualifiedAccessExpression implements FirCall {
    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    public abstract FirArgumentList getArgumentList();

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

    public abstract FirFunctionCallOrigin getOrigin();

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public abstract List<FirTypeProjection> getTypeArguments();

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract FirTypeRef getTypeRef();

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    public abstract void replaceArgumentList(FirArgumentList newArgumentList);

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

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    @FirImplementationDetail
    public abstract void replaceSource(KtSourceElement newSource);

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public abstract void replaceTypeArguments(List<? extends FirTypeProjection> newTypeArguments);

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract void replaceTypeRef(FirTypeRef newTypeRef);

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirFunctionCall transformAnnotations(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public abstract <D> FirFunctionCall transformCalleeReference(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public abstract <D> FirFunctionCall transformExplicitReceiver(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public abstract <D> FirFunctionCall transformTypeArguments(FirTransformer<? super D> transformer, D data);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitFunctionCall(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformFunctionCall = transformer.transformFunctionCall(this, data);
        Intrinsics.checkNotNull(firStatementTransformFunctionCall, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.expressions.FirFunctionCall.transform");
        return firStatementTransformFunctionCall;
    }
}
