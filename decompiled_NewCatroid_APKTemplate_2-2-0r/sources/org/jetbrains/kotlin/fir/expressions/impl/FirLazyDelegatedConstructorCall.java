package org.jetbrains.kotlin.fir.expressions.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirImplementationDetail;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirLazyDelegatedConstructorCall.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ5\u0010&\u001a\u00020'\"\u0004\b\u0000\u0010(\"\u0004\b\u0001\u0010)2\u0012\u0010*\u001a\u000e\u0012\u0004\u0012\u0002H(\u0012\u0004\u0012\u0002H)0+2\u0006\u0010,\u001a\u0002H)H\u0016¢\u0006\u0002\u0010-J\u0016\u0010.\u001a\u00020'2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016J\u0010\u00100\u001a\u00020'2\u0006\u00101\u001a\u00020\u000fH\u0016J\u0010\u00102\u001a\u00020'2\u0006\u00103\u001a\u00020\u0005H\u0016J\u0010\u00104\u001a\u00020'2\u0006\u00105\u001a\u00020\u0003H\u0016J\u0016\u00106\u001a\u00020'2\f\u00107\u001a\b\u0012\u0004\u0012\u00020\u001b0\nH\u0016J\u0010\u00108\u001a\u00020'2\u0006\u00109\u001a\u00020\u001bH\u0016J)\u0010:\u001a\u00020\u0000\"\u0004\b\u0000\u0010)2\f\u0010;\u001a\b\u0012\u0004\u0012\u0002H)0<2\u0006\u0010,\u001a\u0002H)H\u0016¢\u0006\u0002\u0010=J)\u0010>\u001a\u00020\u0000\"\u0004\b\u0000\u0010)2\f\u0010;\u001a\b\u0012\u0004\u0012\u0002H)0<2\u0006\u0010,\u001a\u0002H)H\u0016¢\u0006\u0002\u0010=J)\u0010?\u001a\u00020\u0000\"\u0004\b\u0000\u0010)2\f\u0010;\u001a\b\u0012\u0004\u0012\u0002H)0<2\u0006\u0010,\u001a\u0002H)H\u0016¢\u0006\u0002\u0010=J)\u0010@\u001a\u00020\u0000\"\u0004\b\u0000\u0010)2\f\u0010;\u001a\b\u0012\u0004\u0012\u0002H)0<2\u0006\u0010,\u001a\u0002H)H\u0016¢\u0006\u0002\u0010=R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0002\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\rR\u0014\u0010\u001d\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010 \u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010!R\u0016\u0010\"\u001a\u0004\u0018\u00010#8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%¨\u0006A"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/impl/FirLazyDelegatedConstructorCall;", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "constructedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "isThis", "", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Lorg/jetbrains/kotlin/fir/references/FirReference;Z)V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "argumentList", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "getArgumentList", "()Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirReference;", "setCalleeReference", "(Lorg/jetbrains/kotlin/fir/references/FirReference;)V", "getConstructedTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setConstructedTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "contextReceiverArguments", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getContextReceiverArguments", "dispatchReceiver", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "isSuper", "()Z", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "replaceArgumentList", "newArgumentList", "replaceCalleeReference", "newCalleeReference", "replaceConstructedTypeRef", "newConstructedTypeRef", "replaceContextReceiverArguments", "newContextReceiverArguments", "replaceDispatchReceiver", "newDispatchReceiver", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/impl/FirLazyDelegatedConstructorCall;", "transformCalleeReference", "transformChildren", "transformDispatchReceiver", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirLazyDelegatedConstructorCall extends FirDelegatedConstructorCall {
    private FirReference calleeReference;
    private FirTypeRef constructedTypeRef;
    private final boolean isThis;

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirCall, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirCall
    public void replaceArgumentList(FirArgumentList newArgumentList) {
        Intrinsics.checkNotNullParameter(newArgumentList, "newArgumentList");
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirContextReceiverArgumentListOwner
    public void replaceContextReceiverArguments(List<? extends FirExpression> newContextReceiverArguments) {
        Intrinsics.checkNotNullParameter(newContextReceiverArguments, "newContextReceiverArguments");
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public void replaceDispatchReceiver(FirExpression newDispatchReceiver) {
        Intrinsics.checkNotNullParameter(newDispatchReceiver, "newDispatchReceiver");
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirCall, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirLazyDelegatedConstructorCall transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public <D> FirLazyDelegatedConstructorCall transformDispatchReceiver(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCall, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirCall transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirCall, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirDelegatedConstructorCall transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirStatement transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public /* bridge */ /* synthetic */ FirDelegatedConstructorCall transformCalleeReference(FirTransformer firTransformer, Object obj) {
        return transformCalleeReference((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvable
    public /* bridge */ /* synthetic */ FirResolvable transformCalleeReference(FirTransformer firTransformer, Object obj) {
        return transformCalleeReference((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public /* bridge */ /* synthetic */ FirDelegatedConstructorCall transformDispatchReceiver(FirTransformer firTransformer, Object obj) {
        return transformDispatchReceiver((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public FirTypeRef getConstructedTypeRef() {
        return this.constructedTypeRef;
    }

    public void setConstructedTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.constructedTypeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public FirReference getCalleeReference() {
        return this.calleeReference;
    }

    public void setCalleeReference(FirReference firReference) {
        Intrinsics.checkNotNullParameter(firReference, "<set-?>");
        this.calleeReference = firReference;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    /* JADX INFO: renamed from: isThis, reason: from getter */
    public boolean getIsThis() {
        return this.isThis;
    }

    @FirImplementationDetail
    public FirLazyDelegatedConstructorCall(FirTypeRef constructedTypeRef, FirReference calleeReference, boolean z) {
        Intrinsics.checkNotNullParameter(constructedTypeRef, "constructedTypeRef");
        Intrinsics.checkNotNullParameter(calleeReference, "calleeReference");
        this.constructedTypeRef = constructedTypeRef;
        this.calleeReference = calleeReference;
        this.isThis = z;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        throw new IllegalStateException("FirLazyDelegatedConstructorCall should be calculated before accessing".toString());
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirCall, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public List<FirAnnotation> getAnnotations() {
        throw new IllegalStateException("FirLazyDelegatedConstructorCall should be calculated before accessing".toString());
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirCall
    public FirArgumentList getArgumentList() {
        throw new IllegalStateException("FirLazyDelegatedConstructorCall should be calculated before accessing".toString());
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirContextReceiverArgumentListOwner
    public List<FirExpression> getContextReceiverArguments() {
        throw new IllegalStateException("FirLazyDelegatedConstructorCall should be calculated before accessing".toString());
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public FirExpression getDispatchReceiver() {
        throw new IllegalStateException("FirLazyDelegatedConstructorCall should be calculated before accessing".toString());
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public boolean isSuper() {
        return !getIsThis();
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getConstructedTypeRef().accept(visitor, data);
        getCalleeReference().accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirLazyDelegatedConstructorCall transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setConstructedTypeRef((FirTypeRef) getConstructedTypeRef().transform(transformer, data));
        transformCalleeReference((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public <D> FirLazyDelegatedConstructorCall transformCalleeReference(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setCalleeReference((FirReference) getCalleeReference().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public void replaceConstructedTypeRef(FirTypeRef newConstructedTypeRef) {
        Intrinsics.checkNotNullParameter(newConstructedTypeRef, "newConstructedTypeRef");
        setConstructedTypeRef(newConstructedTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public void replaceCalleeReference(FirReference newCalleeReference) {
        Intrinsics.checkNotNullParameter(newCalleeReference, "newCalleeReference");
        setCalleeReference(newCalleeReference);
    }
}
