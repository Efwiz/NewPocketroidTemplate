package org.jetbrains.kotlin.fir.expressions;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirMultiDelegatedConstructorCall.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010$\u001a\u0002H%\"\u0004\b\u0000\u0010%\"\u0004\b\u0001\u0010&2\u0012\u0010'\u001a\u000e\u0012\u0004\u0012\u0002H%\u0012\u0004\u0012\u0002H&0(2\u0006\u0010)\u001a\u0002H&H\u0016¢\u0006\u0002\u0010*J\u0016\u0010+\u001a\u00020,2\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H&J\u0010\u0010.\u001a\u00020,2\u0006\u0010/\u001a\u00020\tH&J\u0010\u00100\u001a\u00020,2\u0006\u00101\u001a\u00020\rH&J\u0010\u00102\u001a\u00020,2\u0006\u00103\u001a\u00020\u0011H&J\u0016\u00104\u001a\u00020,2\f\u00105\u001a\b\u0012\u0004\u0012\u00020\u00150\u0004H&J\u0016\u00106\u001a\u00020,2\f\u00107\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004H&J\u0010\u00108\u001a\u00020,2\u0006\u00109\u001a\u00020\u0015H&J3\u0010:\u001a\u0002H;\"\b\b\u0000\u0010;*\u00020<\"\u0004\b\u0001\u0010&2\f\u0010=\u001a\b\u0012\u0004\u0012\u0002H&0>2\u0006\u0010)\u001a\u0002H&H\u0016¢\u0006\u0002\u0010?J)\u0010@\u001a\u00020\u0000\"\u0004\b\u0000\u0010&2\f\u0010=\u001a\b\u0012\u0004\u0012\u0002H&0>2\u0006\u0010)\u001a\u0002H&H&¢\u0006\u0002\u0010AJ)\u0010B\u001a\u00020\u0000\"\u0004\b\u0000\u0010&2\f\u0010=\u001a\b\u0012\u0004\u0012\u0002H&0>2\u0006\u0010)\u001a\u0002H&H&¢\u0006\u0002\u0010AJ)\u0010C\u001a\u00020\u0000\"\u0004\b\u0000\u0010&2\f\u0010=\u001a\b\u0012\u0004\u0012\u0002H&0>2\u0006\u0010)\u001a\u0002H&H&¢\u0006\u0002\u0010AJ)\u0010D\u001a\u00020\u0000\"\u0004\b\u0000\u0010&2\f\u0010=\u001a\b\u0012\u0004\u0012\u0002H&0>2\u0006\u0010)\u001a\u0002H&H&¢\u0006\u0002\u0010AR\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0012\u0010\u0010\u001a\u00020\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0018\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0007R\u0018\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0007R\u0012\u0010\u0019\u001a\u00020\u0015X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0012\u0010\u001c\u001a\u00020\u001dX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001eR\u0012\u0010\u001f\u001a\u00020\u001dX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u001eR\u0014\u0010 \u001a\u0004\u0018\u00010!X¦\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#¨\u0006E"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/FirMultiDelegatedConstructorCall;", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "argumentList", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "getArgumentList", "()Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirReference;", "constructedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getConstructedTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "contextReceiverArguments", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getContextReceiverArguments", "delegatedConstructorCalls", "getDelegatedConstructorCalls", "dispatchReceiver", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "isSuper", "", "()Z", "isThis", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "replaceArgumentList", "newArgumentList", "replaceCalleeReference", "newCalleeReference", "replaceConstructedTypeRef", "newConstructedTypeRef", "replaceContextReceiverArguments", "newContextReceiverArguments", "replaceDelegatedConstructorCalls", "newDelegatedConstructorCalls", "replaceDispatchReceiver", "newDispatchReceiver", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirMultiDelegatedConstructorCall;", "transformCalleeReference", "transformDelegatedConstructorCalls", "transformDispatchReceiver", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirMultiDelegatedConstructorCall extends FirDelegatedConstructorCall {
    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirCall, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirCall
    public abstract FirArgumentList getArgumentList();

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public abstract FirReference getCalleeReference();

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public abstract FirTypeRef getConstructedTypeRef();

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirContextReceiverArgumentListOwner
    public abstract List<FirExpression> getContextReceiverArguments();

    public abstract List<FirDelegatedConstructorCall> getDelegatedConstructorCalls();

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public abstract FirExpression getDispatchReceiver();

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public abstract boolean isSuper();

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public abstract boolean isThis();

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirCall, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirCall
    public abstract void replaceArgumentList(FirArgumentList newArgumentList);

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public abstract void replaceCalleeReference(FirReference newCalleeReference);

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public abstract void replaceConstructedTypeRef(FirTypeRef newConstructedTypeRef);

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirContextReceiverArgumentListOwner
    public abstract void replaceContextReceiverArguments(List<? extends FirExpression> newContextReceiverArguments);

    public abstract void replaceDelegatedConstructorCalls(List<? extends FirDelegatedConstructorCall> newDelegatedConstructorCalls);

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public abstract void replaceDispatchReceiver(FirExpression newDispatchReceiver);

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirCall, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirMultiDelegatedConstructorCall transformAnnotations(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public abstract <D> FirMultiDelegatedConstructorCall transformCalleeReference(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirMultiDelegatedConstructorCall transformDelegatedConstructorCalls(FirTransformer<? super D> transformer, D data);

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public abstract <D> FirMultiDelegatedConstructorCall transformDispatchReceiver(FirTransformer<? super D> transformer, D data);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitMultiDelegatedConstructorCall(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirStatement firStatementTransformMultiDelegatedConstructorCall = transformer.transformMultiDelegatedConstructorCall(this, data);
        Intrinsics.checkNotNull(firStatementTransformMultiDelegatedConstructorCall, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall.transform");
        return firStatementTransformMultiDelegatedConstructorCall;
    }
}
