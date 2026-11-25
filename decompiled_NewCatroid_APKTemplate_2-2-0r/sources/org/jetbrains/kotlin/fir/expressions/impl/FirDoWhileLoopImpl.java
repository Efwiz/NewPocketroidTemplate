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
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirLoop;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirDoWhileLoopImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001B7\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010\rJ5\u0010!\u001a\u00020\"\"\u0004\b\u0000\u0010#\"\u0004\b\u0001\u0010$2\u0012\u0010%\u001a\u000e\u0012\u0004\u0012\u0002H#\u0012\u0004\u0012\u0002H$0&2\u0006\u0010'\u001a\u0002H$H\u0016¢\u0006\u0002\u0010(J\u0016\u0010)\u001a\u00020\"2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00060+H\u0016J)\u0010,\u001a\u00020\u0000\"\u0004\b\u0000\u0010$2\f\u0010-\u001a\b\u0012\u0004\u0012\u0002H$0.2\u0006\u0010'\u001a\u0002H$H\u0016¢\u0006\u0002\u0010/J)\u00100\u001a\u00020\u0000\"\u0004\b\u0000\u0010$2\f\u0010-\u001a\b\u0012\u0004\u0012\u0002H$0.2\u0006\u0010'\u001a\u0002H$H\u0016¢\u0006\u0002\u0010/J)\u00101\u001a\u00020\u0000\"\u0004\b\u0000\u0010$2\f\u0010-\u001a\b\u0012\u0004\u0012\u0002H$0.2\u0006\u0010'\u001a\u0002H$H\u0016¢\u0006\u0002\u0010/J)\u00102\u001a\u00020\u0000\"\u0004\b\u0000\u0010$2\f\u0010-\u001a\b\u0012\u0004\u0012\u0002H$0.2\u0006\u0010'\u001a\u0002H$H\u0016¢\u0006\u0002\u0010/J)\u00103\u001a\u00020\u0000\"\u0004\b\u0000\u0010$2\f\u0010-\u001a\b\u0012\u0004\u0012\u0002H$0.2\u0006\u0010'\u001a\u0002H$H\u0016¢\u0006\u0002\u0010/R(\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0012\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 \u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/impl/FirDoWhileLoopImpl;", "Lorg/jetbrains/kotlin/fir/expressions/FirDoWhileLoop;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "annotations", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "block", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "condition", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/kotlin/fir/FirLabel;", "(Lorg/jetbrains/kotlin/KtSourceElement;Ljava/util/List;Lorg/jetbrains/kotlin/fir/expressions/FirBlock;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/FirLabel;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "setAnnotations-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "getBlock", "()Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "setBlock", "(Lorg/jetbrains/kotlin/fir/expressions/FirBlock;)V", "getCondition", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setCondition", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "getLabel", "()Lorg/jetbrains/kotlin/fir/FirLabel;", "setLabel", "(Lorg/jetbrains/kotlin/fir/FirLabel;)V", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/impl/FirDoWhileLoopImpl;", "transformBlock", "transformChildren", "transformCondition", "transformOtherChildren", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDoWhileLoopImpl extends FirDoWhileLoop {
    private List<T> annotations;
    private FirBlock block;
    private FirExpression condition;
    private FirLabel label;
    private final KtSourceElement source;

    public /* synthetic */ FirDoWhileLoopImpl(KtSourceElement ktSourceElement, List list, FirBlock firBlock, FirExpression firExpression, FirLabel firLabel, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, list, firBlock, firExpression, firLabel);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop, org.jetbrains.kotlin.fir.expressions.FirLoop, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m6938getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop, org.jetbrains.kotlin.fir.expressions.FirLoop, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirDoWhileLoop transformAnnotations(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop, org.jetbrains.kotlin.fir.expressions.FirLoop
    public /* bridge */ /* synthetic */ FirDoWhileLoop transformBlock(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop, org.jetbrains.kotlin.fir.expressions.FirLoop
    public /* bridge */ /* synthetic */ FirDoWhileLoop transformCondition(FirTransformer firTransformer, Object obj) {
        return transformCondition((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirLoop
    public /* bridge */ /* synthetic */ FirLoop transformCondition(FirTransformer firTransformer, Object obj) {
        return transformCondition((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop, org.jetbrains.kotlin.fir.expressions.FirLoop
    public /* bridge */ /* synthetic */ FirDoWhileLoop transformOtherChildren(FirTransformer firTransformer, Object obj) {
        return transformOtherChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirLoop
    public /* bridge */ /* synthetic */ FirLoop transformOtherChildren(FirTransformer firTransformer, Object obj) {
        return transformOtherChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop, org.jetbrains.kotlin.fir.expressions.FirLoop, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m6938getAnnotations5e3fPpI() {
        return this.annotations;
    }

    /* JADX INFO: renamed from: setAnnotations-GqUYU-s, reason: not valid java name */
    public void m6939setAnnotationsGqUYUs(List<T> list) {
        this.annotations = list;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop, org.jetbrains.kotlin.fir.expressions.FirLoop
    public FirBlock getBlock() {
        return this.block;
    }

    public void setBlock(FirBlock firBlock) {
        Intrinsics.checkNotNullParameter(firBlock, "<set-?>");
        this.block = firBlock;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop, org.jetbrains.kotlin.fir.expressions.FirLoop
    public FirExpression getCondition() {
        return this.condition;
    }

    public void setCondition(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.condition = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop, org.jetbrains.kotlin.fir.expressions.FirLoop
    public FirLabel getLabel() {
        return this.label;
    }

    public void setLabel(FirLabel firLabel) {
        this.label = firLabel;
    }

    private FirDoWhileLoopImpl(KtSourceElement ktSourceElement, List<T> list, FirBlock block, FirExpression condition, FirLabel firLabel) {
        Intrinsics.checkNotNullParameter(block, "block");
        Intrinsics.checkNotNullParameter(condition, "condition");
        this.source = ktSourceElement;
        this.annotations = list;
        this.block = block;
        this.condition = condition;
        this.label = firLabel;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<T> it = MutableOrEmptyList.m6756boximpl(m6938getAnnotations5e3fPpI()).iterator();
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
    public <D> FirDoWhileLoopImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformBlock((FirTransformer) transformer, (Object) data);
        transformCondition((FirTransformer) transformer, (Object) data);
        transformOtherChildren((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop, org.jetbrains.kotlin.fir.expressions.FirLoop, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirDoWhileLoopImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6938getAnnotations5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop, org.jetbrains.kotlin.fir.expressions.FirLoop
    public <D> FirDoWhileLoopImpl transformBlock(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setBlock((FirBlock) getBlock().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop, org.jetbrains.kotlin.fir.expressions.FirLoop
    public <D> FirDoWhileLoopImpl transformCondition(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setCondition((FirExpression) getCondition().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop, org.jetbrains.kotlin.fir.expressions.FirLoop
    public <D> FirDoWhileLoopImpl transformOtherChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformAnnotations((FirTransformer) transformer, (Object) data);
        FirLabel label = getLabel();
        setLabel(label != null ? (FirLabel) label.transform(transformer, data) : null);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirDoWhileLoop, org.jetbrains.kotlin.fir.expressions.FirLoop, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        m6939setAnnotationsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newAnnotations));
    }
}
