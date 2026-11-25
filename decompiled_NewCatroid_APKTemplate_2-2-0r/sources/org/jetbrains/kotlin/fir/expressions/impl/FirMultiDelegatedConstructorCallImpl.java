package org.jetbrains.kotlin.fir.expressions.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
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
import org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirMultiDelegatedConstructorCallImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\b\u0007\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J5\u0010&\u001a\u00020'\"\u0004\b\u0000\u0010(\"\u0004\b\u0001\u0010)2\u0012\u0010*\u001a\u000e\u0012\u0004\u0012\u0002H(\u0012\u0004\u0012\u0002H)0+2\u0006\u0010,\u001a\u0002H)H\u0016¢\u0006\u0002\u0010-J\u0016\u0010.\u001a\u00020'2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0016J\u0010\u00100\u001a\u00020'2\u0006\u00101\u001a\u00020\fH\u0016J\u0010\u00102\u001a\u00020'2\u0006\u00103\u001a\u00020\u0010H\u0016J\u0010\u00104\u001a\u00020'2\u0006\u00105\u001a\u00020\u0014H\u0016J\u0016\u00106\u001a\u00020'2\f\u00107\u001a\b\u0012\u0004\u0012\u00020\u00180\u0007H\u0016J\u0016\u00108\u001a\u00020'2\f\u00109\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007H\u0016J\u0010\u0010:\u001a\u00020'2\u0006\u0010;\u001a\u00020\u0018H\u0016J)\u0010<\u001a\u00020\u0000\"\u0004\b\u0000\u0010)2\f\u0010=\u001a\b\u0012\u0004\u0012\u0002H)0>2\u0006\u0010,\u001a\u0002H)H\u0016¢\u0006\u0002\u0010?J)\u0010@\u001a\u00020\u0000\"\u0004\b\u0000\u0010)2\f\u0010=\u001a\b\u0012\u0004\u0012\u0002H)0>2\u0006\u0010,\u001a\u0002H)H\u0016¢\u0006\u0002\u0010?J)\u0010A\u001a\u00020\u0000\"\u0004\b\u0000\u0010)2\f\u0010=\u001a\b\u0012\u0004\u0012\u0002H)0>2\u0006\u0010,\u001a\u0002H)H\u0016¢\u0006\u0002\u0010?J)\u0010B\u001a\u00020\u0000\"\u0004\b\u0000\u0010)2\f\u0010=\u001a\b\u0012\u0004\u0012\u0002H)0>2\u0006\u0010,\u001a\u0002H)H\u0016¢\u0006\u0002\u0010?J)\u0010C\u001a\u00020\u0000\"\u0004\b\u0000\u0010)2\f\u0010=\u001a\b\u0012\u0004\u0012\u0002H)0>2\u0006\u0010,\u001a\u0002H)H\u0016¢\u0006\u0002\u0010?R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\nR\u001a\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\nR\u0014\u0010\u001b\u001a\u00020\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\u00020\u001f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010 R\u0014\u0010!\u001a\u00020\u001f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010 R\u0016\u0010\"\u001a\u0004\u0018\u00010#8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%¨\u0006D"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/impl/FirMultiDelegatedConstructorCallImpl;", "Lorg/jetbrains/kotlin/fir/expressions/FirMultiDelegatedConstructorCall;", "delegatedConstructorCalls", "", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "(Ljava/util/List;)V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "argumentList", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "getArgumentList", "()Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirReference;", "constructedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getConstructedTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "contextReceiverArguments", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getContextReceiverArguments", "getDelegatedConstructorCalls", "dispatchReceiver", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "isSuper", "", "()Z", "isThis", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "replaceArgumentList", "newArgumentList", "replaceCalleeReference", "newCalleeReference", "replaceConstructedTypeRef", "newConstructedTypeRef", "replaceContextReceiverArguments", "newContextReceiverArguments", "replaceDelegatedConstructorCalls", "newDelegatedConstructorCalls", "replaceDispatchReceiver", "newDispatchReceiver", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/impl/FirMultiDelegatedConstructorCallImpl;", "transformCalleeReference", "transformChildren", "transformDelegatedConstructorCalls", "transformDispatchReceiver", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirMultiDelegatedConstructorCallImpl extends FirMultiDelegatedConstructorCall {
    private final List<FirDelegatedConstructorCall> delegatedConstructorCalls;

    @Override // org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirCall, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirCall
    public void replaceArgumentList(FirArgumentList newArgumentList) {
        Intrinsics.checkNotNullParameter(newArgumentList, "newArgumentList");
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public void replaceCalleeReference(FirReference newCalleeReference) {
        Intrinsics.checkNotNullParameter(newCalleeReference, "newCalleeReference");
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public void replaceConstructedTypeRef(FirTypeRef newConstructedTypeRef) {
        Intrinsics.checkNotNullParameter(newConstructedTypeRef, "newConstructedTypeRef");
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirContextReceiverArgumentListOwner
    public void replaceContextReceiverArguments(List<? extends FirExpression> newContextReceiverArguments) {
        Intrinsics.checkNotNullParameter(newContextReceiverArguments, "newContextReceiverArguments");
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public void replaceDispatchReceiver(FirExpression newDispatchReceiver) {
        Intrinsics.checkNotNullParameter(newDispatchReceiver, "newDispatchReceiver");
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirCall, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirMultiDelegatedConstructorCallImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public <D> FirMultiDelegatedConstructorCallImpl transformCalleeReference(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public <D> FirMultiDelegatedConstructorCallImpl transformDispatchReceiver(FirTransformer<? super D> transformer, D data) {
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

    @Override // org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirCall, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirMultiDelegatedConstructorCall transformAnnotations(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public /* bridge */ /* synthetic */ FirMultiDelegatedConstructorCall transformCalleeReference(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall
    public /* bridge */ /* synthetic */ FirMultiDelegatedConstructorCall transformDelegatedConstructorCalls(FirTransformer firTransformer, Object obj) {
        return transformDelegatedConstructorCalls((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public /* bridge */ /* synthetic */ FirDelegatedConstructorCall transformDispatchReceiver(FirTransformer firTransformer, Object obj) {
        return transformDispatchReceiver((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public /* bridge */ /* synthetic */ FirMultiDelegatedConstructorCall transformDispatchReceiver(FirTransformer firTransformer, Object obj) {
        return transformDispatchReceiver((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall
    public List<FirDelegatedConstructorCall> getDelegatedConstructorCalls() {
        return this.delegatedConstructorCalls;
    }

    @FirImplementationDetail
    public FirMultiDelegatedConstructorCallImpl(List<FirDelegatedConstructorCall> delegatedConstructorCalls) {
        Intrinsics.checkNotNullParameter(delegatedConstructorCalls, "delegatedConstructorCalls");
        this.delegatedConstructorCalls = delegatedConstructorCalls;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return ((FirDelegatedConstructorCall) CollectionsKt.last((List) getDelegatedConstructorCalls())).getSource();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirCall, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public List<FirAnnotation> getAnnotations() {
        return ((FirDelegatedConstructorCall) CollectionsKt.last((List) getDelegatedConstructorCalls())).getAnnotations();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirCall
    public FirArgumentList getArgumentList() {
        return ((FirDelegatedConstructorCall) CollectionsKt.last((List) getDelegatedConstructorCalls())).getArgumentList();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirContextReceiverArgumentListOwner
    public List<FirExpression> getContextReceiverArguments() {
        return ((FirDelegatedConstructorCall) CollectionsKt.last((List) getDelegatedConstructorCalls())).getContextReceiverArguments();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public FirTypeRef getConstructedTypeRef() {
        return ((FirDelegatedConstructorCall) CollectionsKt.last((List) getDelegatedConstructorCalls())).getConstructedTypeRef();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public FirExpression getDispatchReceiver() {
        return ((FirDelegatedConstructorCall) CollectionsKt.last((List) getDelegatedConstructorCalls())).getDispatchReceiver();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public FirReference getCalleeReference() {
        return ((FirDelegatedConstructorCall) CollectionsKt.last((List) getDelegatedConstructorCalls())).getCalleeReference();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    /* JADX INFO: renamed from: isThis */
    public boolean getIsThis() {
        return ((FirDelegatedConstructorCall) CollectionsKt.last((List) getDelegatedConstructorCalls())).getIsThis();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public boolean isSuper() {
        return !getIsThis();
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<T> it = getDelegatedConstructorCalls().iterator();
        while (it.hasNext()) {
            ((FirDelegatedConstructorCall) it.next()).accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirMultiDelegatedConstructorCallImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformDelegatedConstructorCalls((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall
    public <D> FirMultiDelegatedConstructorCallImpl transformDelegatedConstructorCalls(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.transformInplace(getDelegatedConstructorCalls(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall
    public void replaceDelegatedConstructorCalls(List<? extends FirDelegatedConstructorCall> newDelegatedConstructorCalls) {
        Intrinsics.checkNotNullParameter(newDelegatedConstructorCalls, "newDelegatedConstructorCalls");
        getDelegatedConstructorCalls().clear();
        getDelegatedConstructorCalls().addAll(newDelegatedConstructorCalls);
    }
}
