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
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirQualifiedAccessExpression.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J5\u0010\"\u001a\u0002H#\"\u0004\b\u0000\u0010#\"\u0004\b\u0001\u0010$2\u0012\u0010%\u001a\u000e\u0012\u0004\u0012\u0002H#\u0012\u0004\u0012\u0002H$0&2\u0006\u0010'\u001a\u0002H$H\u0016¢\u0006\u0002\u0010(J\u0016\u0010)\u001a\u00020*2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H&J\u0010\u0010,\u001a\u00020*2\u0006\u0010-\u001a\u00020\u000bH&J\u0016\u0010.\u001a\u00020*2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H&J\u0010\u00100\u001a\u00020*2\u0006\u00101\u001a\u00020\u0001H&J\u0012\u00102\u001a\u00020*2\b\u00103\u001a\u0004\u0018\u00010\u0001H&J\u0010\u00104\u001a\u00020*2\u0006\u00105\u001a\u00020\u0001H&J\u0012\u00106\u001a\u00020*2\b\u00107\u001a\u0004\u0018\u00010\u0018H'J\u0016\u00108\u001a\u00020*2\f\u00109\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0006H&J\u0010\u0010:\u001a\u00020*2\u0006\u0010;\u001a\u00020\u001fH&J3\u0010<\u001a\u0002H=\"\b\b\u0000\u0010=*\u00020>\"\u0004\b\u0001\u0010$2\f\u0010?\u001a\b\u0012\u0004\u0012\u0002H$0@2\u0006\u0010'\u001a\u0002H$H\u0016¢\u0006\u0002\u0010AJ)\u0010B\u001a\u00020\u0000\"\u0004\b\u0000\u0010$2\f\u0010?\u001a\b\u0012\u0004\u0012\u0002H$0@2\u0006\u0010'\u001a\u0002H$H&¢\u0006\u0002\u0010CJ)\u0010D\u001a\u00020\u0000\"\u0004\b\u0000\u0010$2\f\u0010?\u001a\b\u0012\u0004\u0012\u0002H$0@2\u0006\u0010'\u001a\u0002H$H&¢\u0006\u0002\u0010CJ)\u0010E\u001a\u00020\u0000\"\u0004\b\u0000\u0010$2\f\u0010?\u001a\b\u0012\u0004\u0012\u0002H$0@2\u0006\u0010'\u001a\u0002H$H&¢\u0006\u0002\u0010CJ)\u0010F\u001a\u00020\u0000\"\u0004\b\u0000\u0010$2\f\u0010?\u001a\b\u0012\u0004\u0012\u0002H$0@2\u0006\u0010'\u001a\u0002H$H&¢\u0006\u0002\u0010CR\u0018\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\tR\u0012\u0010\u0010\u001a\u00020\u0001X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u0004\u0018\u00010\u0001X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0012R\u0012\u0010\u0015\u001a\u00020\u0001X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0012R\u0014\u0010\u0017\u001a\u0004\u0018\u00010\u0018X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0018\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\tR\u0012\u0010\u001e\u001a\u00020\u001fX¦\u0004¢\u0006\u0006\u001a\u0004\b \u0010!¨\u0006G"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;", "Lorg/jetbrains/kotlin/fir/expressions/FirContextReceiverArgumentListOwner;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirReference;", "contextReceiverArguments", "getContextReceiverArguments", "dispatchReceiver", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "explicitReceiver", "getExplicitReceiver", "extensionReceiver", "getExtensionReceiver", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "getTypeArguments", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "replaceCalleeReference", "newCalleeReference", "replaceContextReceiverArguments", "newContextReceiverArguments", "replaceDispatchReceiver", "newDispatchReceiver", "replaceExplicitReceiver", "newExplicitReceiver", "replaceExtensionReceiver", "newExtensionReceiver", "replaceSource", "newSource", "replaceTypeArguments", "newTypeArguments", "replaceTypeRef", "newTypeRef", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "transformCalleeReference", "transformExplicitReceiver", "transformTypeArguments", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirQualifiedAccessExpression extends FirExpression implements FirContextReceiverArgumentListOwner, FirResolvable {
    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    public abstract FirReference getCalleeReference();

    public abstract List<FirExpression> getContextReceiverArguments();

    public abstract FirExpression getDispatchReceiver();

    public abstract FirExpression getExplicitReceiver();

    public abstract FirExpression getExtensionReceiver();

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    public abstract List<FirTypeProjection> getTypeArguments();

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract FirTypeRef getTypeRef();

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    public abstract void replaceCalleeReference(FirReference newCalleeReference);

    public abstract void replaceContextReceiverArguments(List<? extends FirExpression> newContextReceiverArguments);

    public abstract void replaceDispatchReceiver(FirExpression newDispatchReceiver);

    public abstract void replaceExplicitReceiver(FirExpression newExplicitReceiver);

    public abstract void replaceExtensionReceiver(FirExpression newExtensionReceiver);

    @FirImplementationDetail
    public abstract void replaceSource(KtSourceElement newSource);

    public abstract void replaceTypeArguments(List<? extends FirTypeProjection> newTypeArguments);

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression
    public abstract void replaceTypeRef(FirTypeRef newTypeRef);

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirQualifiedAccessExpression transformAnnotations(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirQualifiedAccessExpression transformCalleeReference(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirQualifiedAccessExpression transformExplicitReceiver(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirQualifiedAccessExpression transformTypeArguments(FirTransformer<? super D> transformer, D data);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitQualifiedAccessExpression(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformQualifiedAccessExpression = transformer.transformQualifiedAccessExpression(this, data);
        Intrinsics.checkNotNull(firStatementTransformQualifiedAccessExpression, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression.transform");
        return firStatementTransformQualifiedAccessExpression;
    }
}
