package org.jetbrains.kotlin.fir.expressions.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirLabel;
import org.jetbrains.kotlin.fir.MutableOrEmptyList;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeStubDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirErrorLoop;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirLoop;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirErrorLoopImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001B/\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ5\u0010%\u001a\u00020&\"\u0004\b\u0000\u0010'\"\u0004\b\u0001\u0010(2\u0012\u0010)\u001a\u000e\u0012\u0004\u0012\u0002H'\u0012\u0004\u0012\u0002H(0*2\u0006\u0010+\u001a\u0002H(H\u0016¢\u0006\u0002\u0010,J\u0016\u0010-\u001a\u00020&2\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00060/H\u0016J)\u00100\u001a\u00020\u0000\"\u0004\b\u0000\u0010(2\f\u00101\u001a\b\u0012\u0004\u0012\u0002H(022\u0006\u0010+\u001a\u0002H(H\u0016¢\u0006\u0002\u00103J)\u00104\u001a\u00020\u0000\"\u0004\b\u0000\u0010(2\f\u00101\u001a\b\u0012\u0004\u0012\u0002H(022\u0006\u0010+\u001a\u0002H(H\u0016¢\u0006\u0002\u00103J)\u00105\u001a\u00020\u0000\"\u0004\b\u0000\u0010(2\f\u00101\u001a\b\u0012\u0004\u0012\u0002H(022\u0006\u0010+\u001a\u0002H(H\u0016¢\u0006\u0002\u00103J)\u00106\u001a\u00020\u0000\"\u0004\b\u0000\u0010(2\f\u00101\u001a\b\u0012\u0004\u0012\u0002H(022\u0006\u0010+\u001a\u0002H(H\u0016¢\u0006\u0002\u00103J)\u00107\u001a\u00020\u0000\"\u0004\b\u0000\u0010(2\f\u00101\u001a\b\u0012\u0004\u0012\u0002H(022\u0006\u0010+\u001a\u0002H(H\u0016¢\u0006\u0002\u00103R(\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0010\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0011\u001a\u00020\u0012X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0018X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/impl/FirErrorLoopImpl;", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorLoop;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "annotations", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/kotlin/fir/FirLabel;", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "(Lorg/jetbrains/kotlin/KtSourceElement;Ljava/util/List;Lorg/jetbrains/kotlin/fir/FirLabel;Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "setAnnotations-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "block", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "getBlock", "()Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "setBlock", "(Lorg/jetbrains/kotlin/fir/expressions/FirBlock;)V", "condition", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getCondition", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setCondition", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "getDiagnostic", "()Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "getLabel", "()Lorg/jetbrains/kotlin/fir/FirLabel;", "setLabel", "(Lorg/jetbrains/kotlin/fir/FirLabel;)V", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/impl/FirErrorLoopImpl;", "transformBlock", "transformChildren", "transformCondition", "transformOtherChildren", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirErrorLoopImpl extends FirErrorLoop {
    private List<T> annotations;
    private FirBlock block;
    private FirExpression condition;
    private final ConeDiagnostic diagnostic;
    private FirLabel label;
    private final KtSourceElement source;

    public /* synthetic */ FirErrorLoopImpl(KtSourceElement ktSourceElement, List list, FirLabel firLabel, ConeDiagnostic coneDiagnostic, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, list, firLabel, coneDiagnostic);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorLoop, org.jetbrains.kotlin.fir.expressions.FirLoop, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m6952getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorLoop, org.jetbrains.kotlin.fir.expressions.FirLoop, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirErrorLoop transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirLoop, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirLoop transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirStatement transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorLoop, org.jetbrains.kotlin.fir.expressions.FirLoop
    public /* bridge */ /* synthetic */ FirErrorLoop transformBlock(FirTransformer firTransformer, Object obj) {
        return transformBlock((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirLoop
    public /* bridge */ /* synthetic */ FirLoop transformBlock(FirTransformer firTransformer, Object obj) {
        return transformBlock((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorLoop, org.jetbrains.kotlin.fir.expressions.FirLoop
    public /* bridge */ /* synthetic */ FirErrorLoop transformCondition(FirTransformer firTransformer, Object obj) {
        return transformCondition((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirLoop
    public /* bridge */ /* synthetic */ FirLoop transformCondition(FirTransformer firTransformer, Object obj) {
        return transformCondition((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorLoop, org.jetbrains.kotlin.fir.expressions.FirLoop
    public /* bridge */ /* synthetic */ FirErrorLoop transformOtherChildren(FirTransformer firTransformer, Object obj) {
        return transformOtherChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirLoop
    public /* bridge */ /* synthetic */ FirLoop transformOtherChildren(FirTransformer firTransformer, Object obj) {
        return transformOtherChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorLoop, org.jetbrains.kotlin.fir.expressions.FirLoop, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m6952getAnnotations5e3fPpI() {
        return this.annotations;
    }

    /* JADX INFO: renamed from: setAnnotations-GqUYU-s, reason: not valid java name */
    public void m6953setAnnotationsGqUYUs(List<T> list) {
        this.annotations = list;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorLoop, org.jetbrains.kotlin.fir.expressions.FirLoop
    public FirLabel getLabel() {
        return this.label;
    }

    public void setLabel(FirLabel firLabel) {
        this.label = firLabel;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorLoop, org.jetbrains.kotlin.fir.diagnostics.FirDiagnosticHolder
    public ConeDiagnostic getDiagnostic() {
        return this.diagnostic;
    }

    private FirErrorLoopImpl(KtSourceElement ktSourceElement, List<T> list, FirLabel firLabel, ConeDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        this.source = ktSourceElement;
        this.annotations = list;
        this.label = firLabel;
        this.diagnostic = diagnostic;
        this.block = new FirEmptyExpressionBlock();
        this.condition = new FirErrorExpressionImpl(getSource(), MutableOrEmptyList.INSTANCE.m6775empty5e3fPpI(), new ConeStubDiagnostic(getDiagnostic()), null, null, null);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorLoop, org.jetbrains.kotlin.fir.expressions.FirLoop
    public FirBlock getBlock() {
        return this.block;
    }

    public void setBlock(FirBlock firBlock) {
        Intrinsics.checkNotNullParameter(firBlock, "<set-?>");
        this.block = firBlock;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorLoop, org.jetbrains.kotlin.fir.expressions.FirLoop
    public FirExpression getCondition() {
        return this.condition;
    }

    public void setCondition(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.condition = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<T> it = MutableOrEmptyList.m6756boximpl(m6952getAnnotations5e3fPpI()).iterator();
        while (it.hasNext()) {
            ((FirAnnotation) it.next()).accept(visitor, data);
        }
        getBlock().accept(visitor, data);
        getCondition().accept(visitor, data);
        FirLabel label = getLabel();
        if (label != null) {
            label.accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirErrorLoopImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformBlock((FirTransformer) transformer, (Object) data);
        transformCondition((FirTransformer) transformer, (Object) data);
        transformOtherChildren((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorLoop, org.jetbrains.kotlin.fir.expressions.FirLoop, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirErrorLoopImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6952getAnnotations5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorLoop, org.jetbrains.kotlin.fir.expressions.FirLoop
    public <D> FirErrorLoopImpl transformBlock(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setBlock((FirBlock) getBlock().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorLoop, org.jetbrains.kotlin.fir.expressions.FirLoop
    public <D> FirErrorLoopImpl transformCondition(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setCondition((FirExpression) getCondition().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorLoop, org.jetbrains.kotlin.fir.expressions.FirLoop
    public <D> FirErrorLoopImpl transformOtherChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformAnnotations((FirTransformer) transformer, (Object) data);
        FirLabel label = getLabel();
        setLabel(label != null ? (FirLabel) label.transform(transformer, data) : null);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirErrorLoop, org.jetbrains.kotlin.fir.expressions.FirLoop, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        m6953setAnnotationsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newAnnotations));
    }
}
