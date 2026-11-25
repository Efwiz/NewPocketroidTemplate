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

/* JADX INFO: compiled from: FirComponentCall.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010-\u001a\u0002H.\"\u0004\b\u0000\u0010.\"\u0004\b\u0001\u0010/2\u0012\u00100\u001a\u000e\u0012\u0004\u0012\u0002H.\u0012\u0004\u0012\u0002H/012\u0006\u00102\u001a\u0002H/H\u0016¢\u0006\u0002\u00103J\u0016\u00104\u001a\u0002052\f\u00106\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H&J\u0010\u00107\u001a\u0002052\u0006\u00108\u001a\u00020\tH&J\u0010\u00109\u001a\u0002052\u0006\u0010:\u001a\u00020\rH&J\u0010\u00109\u001a\u0002052\u0006\u0010:\u001a\u00020;H&J\u0016\u0010<\u001a\u0002052\f\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00150\u0004H&J\u0010\u0010>\u001a\u0002052\u0006\u0010?\u001a\u00020\u0015H&J\u0012\u0010@\u001a\u0002052\b\u0010A\u001a\u0004\u0018\u00010\u0015H&J\u0010\u0010B\u001a\u0002052\u0006\u0010C\u001a\u00020\u0015H&J\u0012\u0010D\u001a\u0002052\b\u0010E\u001a\u0004\u0018\u00010#H'J\u0016\u0010F\u001a\u0002052\f\u0010G\u001a\b\u0012\u0004\u0012\u00020'0\u0004H&J\u0010\u0010H\u001a\u0002052\u0006\u0010I\u001a\u00020*H&J3\u0010J\u001a\u0002HK\"\b\b\u0000\u0010K*\u00020L\"\u0004\b\u0001\u0010/2\f\u0010M\u001a\b\u0012\u0004\u0012\u0002H/0N2\u0006\u00102\u001a\u0002H/H\u0016¢\u0006\u0002\u0010OJ)\u0010P\u001a\u00020\u0000\"\u0004\b\u0000\u0010/2\f\u0010M\u001a\b\u0012\u0004\u0012\u0002H/0N2\u0006\u00102\u001a\u0002H/H&¢\u0006\u0002\u0010QJ)\u0010R\u001a\u00020\u0000\"\u0004\b\u0000\u0010/2\f\u0010M\u001a\b\u0012\u0004\u0012\u0002H/0N2\u0006\u00102\u001a\u0002H/H&¢\u0006\u0002\u0010QJ)\u0010S\u001a\u00020\u0000\"\u0004\b\u0000\u0010/2\f\u0010M\u001a\b\u0012\u0004\u0012\u0002H/0N2\u0006\u00102\u001a\u0002H/H&¢\u0006\u0002\u0010QJ)\u0010T\u001a\u00020\u0000\"\u0004\b\u0000\u0010/2\f\u0010M\u001a\b\u0012\u0004\u0012\u0002H/0N2\u0006\u00102\u001a\u0002H/H&¢\u0006\u0002\u0010QR\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0012\u0010\u0010\u001a\u00020\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0018\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0007R\u0012\u0010\u0017\u001a\u00020\u0015X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0012\u0010\u001a\u001a\u00020\u0015X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0019R\u0012\u0010\u001c\u001a\u00020\u0015X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0019R\u0012\u0010\u001e\u001a\u00020\u001fX¦\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\u0014\u0010\"\u001a\u0004\u0018\u00010#X¦\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u0018\u0010&\u001a\b\u0012\u0004\u0012\u00020'0\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b(\u0010\u0007R\u0012\u0010)\u001a\u00020*X¦\u0004¢\u0006\u0006\u001a\u0004\b+\u0010,¨\u0006U"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/FirComponentCall;", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "argumentList", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "getArgumentList", "()Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "componentIndex", "", "getComponentIndex", "()I", "contextReceiverArguments", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getContextReceiverArguments", "dispatchReceiver", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "explicitReceiver", "getExplicitReceiver", "extensionReceiver", "getExtensionReceiver", "origin", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCallOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCallOrigin;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "getTypeArguments", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "replaceArgumentList", "newArgumentList", "replaceCalleeReference", "newCalleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "replaceContextReceiverArguments", "newContextReceiverArguments", "replaceDispatchReceiver", "newDispatchReceiver", "replaceExplicitReceiver", "newExplicitReceiver", "replaceExtensionReceiver", "newExtensionReceiver", "replaceSource", "newSource", "replaceTypeArguments", "newTypeArguments", "replaceTypeRef", "newTypeRef", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirComponentCall;", "transformCalleeReference", "transformExplicitReceiver", "transformTypeArguments", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirComponentCall extends FirFunctionCall {
    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirCall
    public abstract FirArgumentList getArgumentList();

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public abstract FirNamedReference getCalleeReference();

    public abstract int getComponentIndex();

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirContextReceiverArgumentListOwner
    public abstract List<FirExpression> getContextReceiverArguments();

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public abstract FirExpression getDispatchReceiver();

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public abstract FirExpression getExplicitReceiver();

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public abstract FirExpression getExtensionReceiver();

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall
    public abstract FirFunctionCallOrigin getOrigin();

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public abstract List<FirTypeProjection> getTypeArguments();

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract FirTypeRef getTypeRef();

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirCall
    public abstract void replaceArgumentList(FirArgumentList newArgumentList);

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall
    public abstract void replaceCalleeReference(FirNamedReference newCalleeReference);

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public abstract void replaceCalleeReference(FirReference newCalleeReference);

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirContextReceiverArgumentListOwner
    public abstract void replaceContextReceiverArguments(List<? extends FirExpression> newContextReceiverArguments);

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public abstract void replaceDispatchReceiver(FirExpression newDispatchReceiver);

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public abstract void replaceExplicitReceiver(FirExpression newExplicitReceiver);

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public abstract void replaceExtensionReceiver(FirExpression newExtensionReceiver);

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    @FirImplementationDetail
    public abstract void replaceSource(KtSourceElement newSource);

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public abstract void replaceTypeArguments(List<? extends FirTypeProjection> newTypeArguments);

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract void replaceTypeRef(FirTypeRef newTypeRef);

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirComponentCall transformAnnotations(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public abstract <D> FirComponentCall transformCalleeReference(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public abstract <D> FirComponentCall transformExplicitReceiver(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public abstract <D> FirComponentCall transformTypeArguments(FirTransformer<? super D> transformer, D data);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitComponentCall(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformComponentCall = transformer.transformComponentCall(this, data);
        Intrinsics.checkNotNull(firStatementTransformComponentCall, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.expressions.FirComponentCall.transform");
        return firStatementTransformComponentCall;
    }
}
