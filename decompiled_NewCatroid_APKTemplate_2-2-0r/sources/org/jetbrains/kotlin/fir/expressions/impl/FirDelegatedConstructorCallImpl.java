package org.jetbrains.kotlin.fir.expressions.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.MutableOrEmptyList;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
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
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirDelegatedConstructorCallImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u001d\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001BS\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\n\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J5\u0010.\u001a\u00020/\"\u0004\b\u0000\u00100\"\u0004\b\u0001\u001012\u0012\u00102\u001a\u000e\u0012\u0004\u0012\u0002H0\u0012\u0004\u0012\u0002H1032\u0006\u00104\u001a\u0002H1H\u0016¢\u0006\u0002\u00105J\u0016\u00106\u001a\u00020/2\f\u00107\u001a\b\u0012\u0004\u0012\u00020\u000608H\u0016J\u0010\u00109\u001a\u00020/2\u0006\u0010:\u001a\u00020\bH\u0016J\u0010\u0010;\u001a\u00020/2\u0006\u0010<\u001a\u00020\u000fH\u0016J\u0010\u0010=\u001a\u00020/2\u0006\u0010>\u001a\u00020\fH\u0016J\u0016\u0010?\u001a\u00020/2\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\n08H\u0016J\u0010\u0010A\u001a\u00020/2\u0006\u0010B\u001a\u00020\nH\u0016J)\u0010C\u001a\u00020\u0000\"\u0004\b\u0000\u001012\f\u0010D\u001a\b\u0012\u0004\u0012\u0002H10E2\u0006\u00104\u001a\u0002H1H\u0016¢\u0006\u0002\u0010FJ)\u0010G\u001a\u00020\u0000\"\u0004\b\u0000\u001012\f\u0010D\u001a\b\u0012\u0004\u0012\u0002H10E2\u0006\u00104\u001a\u0002H1H\u0016¢\u0006\u0002\u0010FJ)\u0010H\u001a\u00020\u0000\"\u0004\b\u0000\u001012\f\u0010D\u001a\b\u0012\u0004\u0012\u0002H10E2\u0006\u00104\u001a\u0002H1H\u0016¢\u0006\u0002\u0010FJ)\u0010I\u001a\u00020\u0000\"\u0004\b\u0000\u001012\f\u0010D\u001a\b\u0012\u0004\u0012\u0002H10E2\u0006\u00104\u001a\u0002H1H\u0016¢\u0006\u0002\u0010FR(\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0017\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u000e\u001a\u00020\u000fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010\u000b\u001a\u00020\fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R(\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0017\u001a\u0004\b$\u0010\u0014\"\u0004\b%\u0010\u0016R\u001a\u0010\r\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u0014\u0010*\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b*\u0010+R\u0014\u0010\u0010\u001a\u00020\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010+R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006J"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/impl/FirDelegatedConstructorCallImpl;", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "annotations", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "argumentList", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "contextReceiverArguments", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "constructedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "dispatchReceiver", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "isThis", "", "(Lorg/jetbrains/kotlin/KtSourceElement;Ljava/util/List;Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;Ljava/util/List;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/references/FirReference;ZLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "setAnnotations-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "getArgumentList", "()Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "setArgumentList", "(Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;)V", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirReference;", "setCalleeReference", "(Lorg/jetbrains/kotlin/fir/references/FirReference;)V", "getConstructedTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setConstructedTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "getContextReceiverArguments-5e3fPpI", "setContextReceiverArguments-GqUYU-s", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setDispatchReceiver", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "isSuper", "()Z", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "", "replaceArgumentList", "newArgumentList", "replaceCalleeReference", "newCalleeReference", "replaceConstructedTypeRef", "newConstructedTypeRef", "replaceContextReceiverArguments", "newContextReceiverArguments", "replaceDispatchReceiver", "newDispatchReceiver", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/impl/FirDelegatedConstructorCallImpl;", "transformCalleeReference", "transformChildren", "transformDispatchReceiver", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDelegatedConstructorCallImpl extends FirDelegatedConstructorCall {
    private List<T> annotations;
    private FirArgumentList argumentList;
    private FirReference calleeReference;
    private FirTypeRef constructedTypeRef;
    private List<T> contextReceiverArguments;
    private FirExpression dispatchReceiver;
    private final boolean isThis;
    private final KtSourceElement source;

    public /* synthetic */ FirDelegatedConstructorCallImpl(KtSourceElement ktSourceElement, List list, FirArgumentList firArgumentList, List list2, FirTypeRef firTypeRef, FirExpression firExpression, FirReference firReference, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, list, firArgumentList, list2, firTypeRef, firExpression, firReference, z);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirCall, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m6932getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirContextReceiverArgumentListOwner
    public /* bridge */ /* synthetic */ List getContextReceiverArguments() {
        return MutableOrEmptyList.m6756boximpl(m6933getContextReceiverArguments5e3fPpI());
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

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m6932getAnnotations5e3fPpI() {
        return this.annotations;
    }

    /* JADX INFO: renamed from: setAnnotations-GqUYU-s, reason: not valid java name */
    public void m6934setAnnotationsGqUYUs(List<T> list) {
        this.annotations = list;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirCall
    public FirArgumentList getArgumentList() {
        return this.argumentList;
    }

    public void setArgumentList(FirArgumentList firArgumentList) {
        Intrinsics.checkNotNullParameter(firArgumentList, "<set-?>");
        this.argumentList = firArgumentList;
    }

    /* JADX INFO: renamed from: getContextReceiverArguments-5e3fPpI, reason: not valid java name */
    public List<T> m6933getContextReceiverArguments5e3fPpI() {
        return this.contextReceiverArguments;
    }

    /* JADX INFO: renamed from: setContextReceiverArguments-GqUYU-s, reason: not valid java name */
    public void m6935setContextReceiverArgumentsGqUYUs(List<T> list) {
        this.contextReceiverArguments = list;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public FirTypeRef getConstructedTypeRef() {
        return this.constructedTypeRef;
    }

    public void setConstructedTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.constructedTypeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public FirExpression getDispatchReceiver() {
        return this.dispatchReceiver;
    }

    public void setDispatchReceiver(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.dispatchReceiver = firExpression;
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

    private FirDelegatedConstructorCallImpl(KtSourceElement ktSourceElement, List<T> list, FirArgumentList argumentList, List<T> list2, FirTypeRef constructedTypeRef, FirExpression dispatchReceiver, FirReference calleeReference, boolean z) {
        Intrinsics.checkNotNullParameter(argumentList, "argumentList");
        Intrinsics.checkNotNullParameter(constructedTypeRef, "constructedTypeRef");
        Intrinsics.checkNotNullParameter(dispatchReceiver, "dispatchReceiver");
        Intrinsics.checkNotNullParameter(calleeReference, "calleeReference");
        this.source = ktSourceElement;
        this.annotations = list;
        this.argumentList = argumentList;
        this.contextReceiverArguments = list2;
        this.constructedTypeRef = constructedTypeRef;
        this.dispatchReceiver = dispatchReceiver;
        this.calleeReference = calleeReference;
        this.isThis = z;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public boolean isSuper() {
        return !getIsThis();
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<T> it = MutableOrEmptyList.m6756boximpl(m6932getAnnotations5e3fPpI()).iterator();
        while (it.hasNext()) {
            ((FirAnnotation) it.next()).accept(visitor, data);
        }
        getArgumentList().accept(visitor, data);
        Iterator<T> it2 = MutableOrEmptyList.m6756boximpl(m6933getContextReceiverArguments5e3fPpI()).iterator();
        while (it2.hasNext()) {
            ((FirExpression) it2.next()).accept(visitor, data);
        }
        getConstructedTypeRef().accept(visitor, data);
        getCalleeReference().accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirDelegatedConstructorCallImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformAnnotations((FirTransformer) transformer, (Object) data);
        setArgumentList((FirArgumentList) getArgumentList().transform(transformer, data));
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6933getContextReceiverArguments5e3fPpI(), transformer, data);
        setConstructedTypeRef((FirTypeRef) getConstructedTypeRef().transform(transformer, data));
        transformCalleeReference((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirCall, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirDelegatedConstructorCallImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6932getAnnotations5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public <D> FirDelegatedConstructorCallImpl transformDispatchReceiver(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setDispatchReceiver((FirExpression) getDispatchReceiver().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public <D> FirDelegatedConstructorCallImpl transformCalleeReference(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setCalleeReference((FirReference) getCalleeReference().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirCall, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        m6934setAnnotationsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newAnnotations));
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirCall
    public void replaceArgumentList(FirArgumentList newArgumentList) {
        Intrinsics.checkNotNullParameter(newArgumentList, "newArgumentList");
        setArgumentList(newArgumentList);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirContextReceiverArgumentListOwner
    public void replaceContextReceiverArguments(List<? extends FirExpression> newContextReceiverArguments) {
        Intrinsics.checkNotNullParameter(newContextReceiverArguments, "newContextReceiverArguments");
        m6935setContextReceiverArgumentsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newContextReceiverArguments));
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public void replaceConstructedTypeRef(FirTypeRef newConstructedTypeRef) {
        Intrinsics.checkNotNullParameter(newConstructedTypeRef, "newConstructedTypeRef");
        setConstructedTypeRef(newConstructedTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
    public void replaceDispatchReceiver(FirExpression newDispatchReceiver) {
        Intrinsics.checkNotNullParameter(newDispatchReceiver, "newDispatchReceiver");
        setDispatchReceiver(newDispatchReceiver);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public void replaceCalleeReference(FirReference newCalleeReference) {
        Intrinsics.checkNotNullParameter(newCalleeReference, "newCalleeReference");
        setCalleeReference(newCalleeReference);
    }
}
