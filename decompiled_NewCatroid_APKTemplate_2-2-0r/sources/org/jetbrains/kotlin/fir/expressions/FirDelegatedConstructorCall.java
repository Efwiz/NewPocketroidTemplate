package org.jetbrains.kotlin.fir.expressions;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirPureAbstractElement;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirDelegatedConstructorCall.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004B\u0005¢\u0006\u0002\u0010\u0005J5\u0010%\u001a\u0002H&\"\u0004\b\u0000\u0010&\"\u0004\b\u0001\u0010'2\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u0002H&\u0012\u0004\u0012\u0002H'0)2\u0006\u0010*\u001a\u0002H'H\u0016¢\u0006\u0002\u0010+J\u0016\u0010,\u001a\u00020-2\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H&J\u0010\u0010/\u001a\u00020-2\u0006\u00100\u001a\u00020\fH&J\u0010\u00101\u001a\u00020-2\u0006\u00102\u001a\u00020\u0010H&J\u0010\u00103\u001a\u00020-2\u0006\u00104\u001a\u00020\u0014H&J\u0016\u00105\u001a\u00020-2\f\u00106\u001a\b\u0012\u0004\u0012\u00020\u00180\u0007H&J\u0010\u00107\u001a\u00020-2\u0006\u00108\u001a\u00020\u0018H&J3\u00109\u001a\u0002H:\"\b\b\u0000\u0010:*\u00020;\"\u0004\b\u0001\u0010'2\f\u0010<\u001a\b\u0012\u0004\u0012\u0002H'0=2\u0006\u0010*\u001a\u0002H'H\u0016¢\u0006\u0002\u0010>J)\u0010?\u001a\u00020\u0000\"\u0004\b\u0000\u0010'2\f\u0010<\u001a\b\u0012\u0004\u0012\u0002H'0=2\u0006\u0010*\u001a\u0002H'H&¢\u0006\u0002\u0010@J)\u0010A\u001a\u00020\u0000\"\u0004\b\u0000\u0010'2\f\u0010<\u001a\b\u0012\u0004\u0012\u0002H'0=2\u0006\u0010*\u001a\u0002H'H&¢\u0006\u0002\u0010@J)\u0010B\u001a\u00020\u0000\"\u0004\b\u0000\u0010'2\f\u0010<\u001a\b\u0012\u0004\u0012\u0002H'0=2\u0006\u0010*\u001a\u0002H'H&¢\u0006\u0002\u0010@R\u0018\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0012\u0010\u000f\u001a\u00020\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0012\u0010\u0013\u001a\u00020\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0018\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\nR\u0012\u0010\u001a\u001a\u00020\u0018X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0012\u0010\u001d\u001a\u00020\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001fR\u0012\u0010 \u001a\u00020\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\b \u0010\u001fR\u0014\u0010!\u001a\u0004\u0018\u00010\"X¦\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$¨\u0006C"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "Lorg/jetbrains/kotlin/fir/FirPureAbstractElement;", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;", "Lorg/jetbrains/kotlin/fir/expressions/FirCall;", "Lorg/jetbrains/kotlin/fir/expressions/FirContextReceiverArgumentListOwner;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "argumentList", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "getArgumentList", "()Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirReference;", "constructedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getConstructedTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "contextReceiverArguments", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getContextReceiverArguments", "dispatchReceiver", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "isSuper", "", "()Z", "isThis", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "replaceArgumentList", "newArgumentList", "replaceCalleeReference", "newCalleeReference", "replaceConstructedTypeRef", "newConstructedTypeRef", "replaceContextReceiverArguments", "newContextReceiverArguments", "replaceDispatchReceiver", "newDispatchReceiver", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "transformCalleeReference", "transformDispatchReceiver", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirDelegatedConstructorCall extends FirPureAbstractElement implements FirCall, FirContextReceiverArgumentListOwner, FirResolvable {
    @Override // org.jetbrains.kotlin.fir.expressions.FirCall, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    @Override // org.jetbrains.kotlin.fir.expressions.FirCall
    public abstract FirArgumentList getArgumentList();

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvable
    public abstract FirReference getCalleeReference();

    public abstract FirTypeRef getConstructedTypeRef();

    @Override // org.jetbrains.kotlin.fir.expressions.FirContextReceiverArgumentListOwner
    public abstract List<FirExpression> getContextReceiverArguments();

    public abstract FirExpression getDispatchReceiver();

    @Override // org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    public abstract boolean isSuper();

    public abstract boolean isThis();

    @Override // org.jetbrains.kotlin.fir.expressions.FirCall, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    @Override // org.jetbrains.kotlin.fir.expressions.FirCall
    public abstract void replaceArgumentList(FirArgumentList newArgumentList);

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvable
    public abstract void replaceCalleeReference(FirReference newCalleeReference);

    public abstract void replaceConstructedTypeRef(FirTypeRef newConstructedTypeRef);

    @Override // org.jetbrains.kotlin.fir.expressions.FirContextReceiverArgumentListOwner
    public abstract void replaceContextReceiverArguments(List<? extends FirExpression> newContextReceiverArguments);

    public abstract void replaceDispatchReceiver(FirExpression newDispatchReceiver);

    @Override // org.jetbrains.kotlin.fir.expressions.FirCall, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirDelegatedConstructorCall transformAnnotations(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvable
    public abstract <D> FirDelegatedConstructorCall transformCalleeReference(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirDelegatedConstructorCall transformDispatchReceiver(FirTransformer<? super D> transformer, D data);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitDelegatedConstructorCall(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformDelegatedConstructorCall = transformer.transformDelegatedConstructorCall(this, data);
        Intrinsics.checkNotNull(firStatementTransformDelegatedConstructorCall, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall.transform");
        return firStatementTransformDelegatedConstructorCall;
    }
}
