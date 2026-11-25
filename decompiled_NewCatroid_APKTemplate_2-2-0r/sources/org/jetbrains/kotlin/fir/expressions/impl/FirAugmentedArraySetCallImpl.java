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
import org.jetbrains.kotlin.fir.expressions.FirAugmentedArraySetCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirAugmentedArraySetCallImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001BG\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0010J5\u0010'\u001a\u00020(\"\u0004\b\u0000\u0010)\"\u0004\b\u0001\u0010*2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u0002H)\u0012\u0004\u0012\u0002H*0,2\u0006\u0010-\u001a\u0002H*H\u0016¢\u0006\u0002\u0010.J\u0016\u0010/\u001a\u00020(2\f\u00100\u001a\b\u0012\u0004\u0012\u00020\u000601H\u0016J\u0010\u00102\u001a\u00020(2\u0006\u00103\u001a\u00020\u000eH\u0016J)\u00104\u001a\u00020\u0000\"\u0004\b\u0000\u0010*2\f\u00105\u001a\b\u0012\u0004\u0012\u0002H*062\u0006\u0010-\u001a\u0002H*H\u0016¢\u0006\u0002\u00107J)\u00108\u001a\u00020\u0000\"\u0004\b\u0000\u0010*2\f\u00105\u001a\b\u0012\u0004\u0012\u0002H*062\u0006\u0010-\u001a\u0002H*H\u0016¢\u0006\u0002\u00107R(\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0015\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\r\u001a\u00020\u000eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u001a\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0017\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u00069"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/impl/FirAugmentedArraySetCallImpl;", "Lorg/jetbrains/kotlin/fir/expressions/FirAugmentedArraySetCall;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "annotations", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "lhsGetCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "rhs", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "operation", "Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "arrayAccessSource", "(Lorg/jetbrains/kotlin/KtSourceElement;Ljava/util/List;Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/expressions/FirOperation;Lorg/jetbrains/kotlin/fir/references/FirReference;Lorg/jetbrains/kotlin/KtSourceElement;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "setAnnotations-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "getArrayAccessSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirReference;", "setCalleeReference", "(Lorg/jetbrains/kotlin/fir/references/FirReference;)V", "getLhsGetCall", "()Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "setLhsGetCall", "(Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;)V", "getOperation", "()Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "getRhs", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setRhs", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "getSource", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "", "replaceCalleeReference", "newCalleeReference", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/impl/FirAugmentedArraySetCallImpl;", "transformChildren", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirAugmentedArraySetCallImpl extends FirAugmentedArraySetCall {
    private List<T> annotations;
    private final KtSourceElement arrayAccessSource;
    private FirReference calleeReference;
    private FirFunctionCall lhsGetCall;
    private final FirOperation operation;
    private FirExpression rhs;
    private final KtSourceElement source;

    public /* synthetic */ FirAugmentedArraySetCallImpl(KtSourceElement ktSourceElement, List list, FirFunctionCall firFunctionCall, FirExpression firExpression, FirOperation firOperation, FirReference firReference, KtSourceElement ktSourceElement2, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, list, firFunctionCall, firExpression, firOperation, firReference, ktSourceElement2);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAugmentedArraySetCall, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m6898getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAugmentedArraySetCall, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAugmentedArraySetCall transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirStatement transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAugmentedArraySetCall, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m6898getAnnotations5e3fPpI() {
        return this.annotations;
    }

    /* JADX INFO: renamed from: setAnnotations-GqUYU-s, reason: not valid java name */
    public void m6899setAnnotationsGqUYUs(List<T> list) {
        this.annotations = list;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAugmentedArraySetCall
    public FirFunctionCall getLhsGetCall() {
        return this.lhsGetCall;
    }

    public void setLhsGetCall(FirFunctionCall firFunctionCall) {
        Intrinsics.checkNotNullParameter(firFunctionCall, "<set-?>");
        this.lhsGetCall = firFunctionCall;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAugmentedArraySetCall
    public FirExpression getRhs() {
        return this.rhs;
    }

    public void setRhs(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.rhs = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAugmentedArraySetCall
    public FirOperation getOperation() {
        return this.operation;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAugmentedArraySetCall
    public FirReference getCalleeReference() {
        return this.calleeReference;
    }

    public void setCalleeReference(FirReference firReference) {
        Intrinsics.checkNotNullParameter(firReference, "<set-?>");
        this.calleeReference = firReference;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAugmentedArraySetCall
    public KtSourceElement getArrayAccessSource() {
        return this.arrayAccessSource;
    }

    private FirAugmentedArraySetCallImpl(KtSourceElement ktSourceElement, List<T> list, FirFunctionCall lhsGetCall, FirExpression rhs, FirOperation operation, FirReference calleeReference, KtSourceElement ktSourceElement2) {
        Intrinsics.checkNotNullParameter(lhsGetCall, "lhsGetCall");
        Intrinsics.checkNotNullParameter(rhs, "rhs");
        Intrinsics.checkNotNullParameter(operation, "operation");
        Intrinsics.checkNotNullParameter(calleeReference, "calleeReference");
        this.source = ktSourceElement;
        this.annotations = list;
        this.lhsGetCall = lhsGetCall;
        this.rhs = rhs;
        this.operation = operation;
        this.calleeReference = calleeReference;
        this.arrayAccessSource = ktSourceElement2;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<T> it = MutableOrEmptyList.m6756boximpl(m6898getAnnotations5e3fPpI()).iterator();
        while (it.hasNext()) {
            ((FirAnnotation) it.next()).accept(visitor, data);
        }
        getLhsGetCall().accept(visitor, data);
        getRhs().accept(visitor, data);
        getCalleeReference().accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirAugmentedArraySetCallImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformAnnotations((FirTransformer) transformer, (Object) data);
        setLhsGetCall((FirFunctionCall) getLhsGetCall().transform(transformer, data));
        setRhs((FirExpression) getRhs().transform(transformer, data));
        setCalleeReference((FirReference) getCalleeReference().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAugmentedArraySetCall, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirAugmentedArraySetCallImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6898getAnnotations5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAugmentedArraySetCall, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        m6899setAnnotationsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newAnnotations));
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAugmentedArraySetCall
    public void replaceCalleeReference(FirReference newCalleeReference) {
        Intrinsics.checkNotNullParameter(newCalleeReference, "newCalleeReference");
        setCalleeReference(newCalleeReference);
    }
}
