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
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirVariableAssignmentImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B-\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b¢\u0006\u0002\u0010\nJ5\u0010\u0018\u001a\u00020\u0019\"\u0004\b\u0000\u0010\u001a\"\u0004\b\u0001\u0010\u001b2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u0002H\u001a\u0012\u0004\u0012\u0002H\u001b0\u001d2\u0006\u0010\u001e\u001a\u0002H\u001bH\u0016¢\u0006\u0002\u0010\u001fJ\u0016\u0010 \u001a\u00020\u00192\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00060\"H\u0016J\u0010\u0010#\u001a\u00020\u00192\u0006\u0010$\u001a\u00020\bH\u0016J)\u0010%\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001b2\f\u0010&\u001a\b\u0012\u0004\u0012\u0002H\u001b0'2\u0006\u0010\u001e\u001a\u0002H\u001bH\u0016¢\u0006\u0002\u0010(J)\u0010)\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001b2\f\u0010&\u001a\b\u0012\u0004\u0012\u0002H\u001b0'2\u0006\u0010\u001e\u001a\u0002H\u001bH\u0016¢\u0006\u0002\u0010(J)\u0010*\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001b2\f\u0010&\u001a\b\u0012\u0004\u0012\u0002H\u001b0'2\u0006\u0010\u001e\u001a\u0002H\u001bH\u0016¢\u0006\u0002\u0010(J)\u0010+\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001b2\f\u0010&\u001a\b\u0012\u0004\u0012\u0002H\u001b0'2\u0006\u0010\u001e\u001a\u0002H\u001bH\u0016¢\u0006\u0002\u0010(R(\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u000f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\t\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0011\"\u0004\b\u0015\u0010\u0013R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/impl/FirVariableAssignmentImpl;", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "annotations", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "lValue", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "rValue", "(Lorg/jetbrains/kotlin/KtSourceElement;Ljava/util/List;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "setAnnotations-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "getLValue", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setLValue", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "getRValue", "setRValue", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "", "replaceLValue", "newLValue", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/impl/FirVariableAssignmentImpl;", "transformChildren", "transformLValue", "transformRValue", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirVariableAssignmentImpl extends FirVariableAssignment {
    private List<T> annotations;
    private FirExpression lValue;
    private FirExpression rValue;
    private final KtSourceElement source;

    public /* synthetic */ FirVariableAssignmentImpl(KtSourceElement ktSourceElement, List list, FirExpression firExpression, FirExpression firExpression2, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, list, firExpression, firExpression2);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirVariableAssignment, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m7036getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirStatement transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirVariableAssignment, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirVariableAssignment transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirVariableAssignment
    public /* bridge */ /* synthetic */ FirVariableAssignment transformLValue(FirTransformer firTransformer, Object obj) {
        return transformLValue((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirVariableAssignment
    public /* bridge */ /* synthetic */ FirVariableAssignment transformRValue(FirTransformer firTransformer, Object obj) {
        return transformRValue((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirVariableAssignment, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m7036getAnnotations5e3fPpI() {
        return this.annotations;
    }

    /* JADX INFO: renamed from: setAnnotations-GqUYU-s, reason: not valid java name */
    public void m7037setAnnotationsGqUYUs(List<T> list) {
        this.annotations = list;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirVariableAssignment
    public FirExpression getLValue() {
        return this.lValue;
    }

    public void setLValue(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.lValue = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirVariableAssignment
    public FirExpression getRValue() {
        return this.rValue;
    }

    public void setRValue(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.rValue = firExpression;
    }

    private FirVariableAssignmentImpl(KtSourceElement ktSourceElement, List<T> list, FirExpression lValue, FirExpression rValue) {
        Intrinsics.checkNotNullParameter(lValue, "lValue");
        Intrinsics.checkNotNullParameter(rValue, "rValue");
        this.source = ktSourceElement;
        this.annotations = list;
        this.lValue = lValue;
        this.rValue = rValue;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<T> it = MutableOrEmptyList.m6756boximpl(m7036getAnnotations5e3fPpI()).iterator();
        while (it.hasNext()) {
            ((FirAnnotation) it.next()).accept(visitor, data);
        }
        getLValue().accept(visitor, data);
        getRValue().accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirVariableAssignmentImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformAnnotations((FirTransformer) transformer, (Object) data);
        transformLValue((FirTransformer) transformer, (Object) data);
        transformRValue((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirVariableAssignment, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirVariableAssignmentImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m7036getAnnotations5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirVariableAssignment
    public <D> FirVariableAssignmentImpl transformLValue(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setLValue((FirExpression) getLValue().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirVariableAssignment
    public <D> FirVariableAssignmentImpl transformRValue(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setRValue((FirExpression) getRValue().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirVariableAssignment, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        m7037setAnnotationsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newAnnotations));
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirVariableAssignment
    public void replaceLValue(FirExpression newLValue) {
        Intrinsics.checkNotNullParameter(newLValue, "newLValue");
        setLValue(newLValue);
    }
}
