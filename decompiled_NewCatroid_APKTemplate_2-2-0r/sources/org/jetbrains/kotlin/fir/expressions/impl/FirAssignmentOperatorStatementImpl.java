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
import org.jetbrains.kotlin.fir.expressions.FirAssignmentOperatorStatement;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirAssignmentOperatorStatementImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B5\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n¢\u0006\u0002\u0010\fJ5\u0010\u001c\u001a\u00020\u001d\"\u0004\b\u0000\u0010\u001e\"\u0004\b\u0001\u0010\u001f2\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u0002H\u001e\u0012\u0004\u0012\u0002H\u001f0!2\u0006\u0010\"\u001a\u0002H\u001fH\u0016¢\u0006\u0002\u0010#J\u0016\u0010$\u001a\u00020\u001d2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00060&H\u0016J)\u0010'\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001f2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H\u001f0)2\u0006\u0010\"\u001a\u0002H\u001fH\u0016¢\u0006\u0002\u0010*J)\u0010+\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001f2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H\u001f0)2\u0006\u0010\"\u001a\u0002H\u001fH\u0016¢\u0006\u0002\u0010*J)\u0010,\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001f2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H\u001f0)2\u0006\u0010\"\u001a\u0002H\u001fH\u0016¢\u0006\u0002\u0010*J)\u0010-\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001f2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H\u001f0)2\u0006\u0010\"\u001a\u0002H\u001fH\u0016¢\u0006\u0002\u0010*R(\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0011\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u000b\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0013\"\u0004\b\u0019\u0010\u0015R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/impl/FirAssignmentOperatorStatementImpl;", "Lorg/jetbrains/kotlin/fir/expressions/FirAssignmentOperatorStatement;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "annotations", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "operation", "Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "leftArgument", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "rightArgument", "(Lorg/jetbrains/kotlin/KtSourceElement;Ljava/util/List;Lorg/jetbrains/kotlin/fir/expressions/FirOperation;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "setAnnotations-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "getLeftArgument", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setLeftArgument", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "getOperation", "()Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "getRightArgument", "setRightArgument", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/impl/FirAssignmentOperatorStatementImpl;", "transformChildren", "transformLeftArgument", "transformRightArgument", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirAssignmentOperatorStatementImpl extends FirAssignmentOperatorStatement {
    private List<T> annotations;
    private FirExpression leftArgument;
    private final FirOperation operation;
    private FirExpression rightArgument;
    private final KtSourceElement source;

    public /* synthetic */ FirAssignmentOperatorStatementImpl(KtSourceElement ktSourceElement, List list, FirOperation firOperation, FirExpression firExpression, FirExpression firExpression2, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, list, firOperation, firExpression, firExpression2);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAssignmentOperatorStatement, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m6896getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAssignmentOperatorStatement, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAssignmentOperatorStatement transformAnnotations(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.expressions.FirAssignmentOperatorStatement
    public /* bridge */ /* synthetic */ FirAssignmentOperatorStatement transformLeftArgument(FirTransformer firTransformer, Object obj) {
        return transformLeftArgument((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAssignmentOperatorStatement
    public /* bridge */ /* synthetic */ FirAssignmentOperatorStatement transformRightArgument(FirTransformer firTransformer, Object obj) {
        return transformRightArgument((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAssignmentOperatorStatement, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m6896getAnnotations5e3fPpI() {
        return this.annotations;
    }

    /* JADX INFO: renamed from: setAnnotations-GqUYU-s, reason: not valid java name */
    public void m6897setAnnotationsGqUYUs(List<T> list) {
        this.annotations = list;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAssignmentOperatorStatement
    public FirOperation getOperation() {
        return this.operation;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAssignmentOperatorStatement
    public FirExpression getLeftArgument() {
        return this.leftArgument;
    }

    public void setLeftArgument(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.leftArgument = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAssignmentOperatorStatement
    public FirExpression getRightArgument() {
        return this.rightArgument;
    }

    public void setRightArgument(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.rightArgument = firExpression;
    }

    private FirAssignmentOperatorStatementImpl(KtSourceElement ktSourceElement, List<T> list, FirOperation operation, FirExpression leftArgument, FirExpression rightArgument) {
        Intrinsics.checkNotNullParameter(operation, "operation");
        Intrinsics.checkNotNullParameter(leftArgument, "leftArgument");
        Intrinsics.checkNotNullParameter(rightArgument, "rightArgument");
        this.source = ktSourceElement;
        this.annotations = list;
        this.operation = operation;
        this.leftArgument = leftArgument;
        this.rightArgument = rightArgument;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<T> it = MutableOrEmptyList.m6756boximpl(m6896getAnnotations5e3fPpI()).iterator();
        while (it.hasNext()) {
            ((FirAnnotation) it.next()).accept(visitor, data);
        }
        getLeftArgument().accept(visitor, data);
        getRightArgument().accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirAssignmentOperatorStatementImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformAnnotations((FirTransformer) transformer, (Object) data);
        transformLeftArgument((FirTransformer) transformer, (Object) data);
        transformRightArgument((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAssignmentOperatorStatement, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirAssignmentOperatorStatementImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6896getAnnotations5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAssignmentOperatorStatement
    public <D> FirAssignmentOperatorStatementImpl transformLeftArgument(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setLeftArgument((FirExpression) getLeftArgument().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAssignmentOperatorStatement
    public <D> FirAssignmentOperatorStatementImpl transformRightArgument(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setRightArgument((FirExpression) getRightArgument().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAssignmentOperatorStatement, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        m6897setAnnotationsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newAnnotations));
    }
}
