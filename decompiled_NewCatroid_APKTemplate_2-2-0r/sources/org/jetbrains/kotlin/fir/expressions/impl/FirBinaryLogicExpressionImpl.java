package org.jetbrains.kotlin.fir.expressions.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.contracts.description.LogicOperationKind;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.MutableOrEmptyList;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitTypeRefImplWithoutSource;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: FirBinaryLogicExpressionImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001B5\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ5\u0010\"\u001a\u00020#\"\u0004\b\u0000\u0010$\"\u0004\b\u0001\u0010%2\u0012\u0010&\u001a\u000e\u0012\u0004\u0012\u0002H$\u0012\u0004\u0012\u0002H%0'2\u0006\u0010(\u001a\u0002H%H\u0016¢\u0006\u0002\u0010)J\u0016\u0010*\u001a\u00020#2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00060,H\u0016J\u0010\u0010-\u001a\u00020#2\u0006\u0010.\u001a\u00020\u001dH\u0016J)\u0010/\u001a\u00020\u0000\"\u0004\b\u0000\u0010%2\f\u00100\u001a\b\u0012\u0004\u0012\u0002H%012\u0006\u0010(\u001a\u0002H%H\u0016¢\u0006\u0002\u00102J)\u00103\u001a\u00020\u0000\"\u0004\b\u0000\u0010%2\f\u00100\u001a\b\u0012\u0004\u0012\u0002H%012\u0006\u0010(\u001a\u0002H%H\u0016¢\u0006\u0002\u00102J)\u00104\u001a\u00020\u0000\"\u0004\b\u0000\u0010%2\f\u00100\u001a\b\u0012\u0004\u0012\u0002H%012\u0006\u0010(\u001a\u0002H%H\u0016¢\u0006\u0002\u00102J)\u00105\u001a\u00020\u0000\"\u0004\b\u0000\u0010%2\f\u00100\u001a\b\u0012\u0004\u0012\u0002H%012\u0006\u0010(\u001a\u0002H%H\u0016¢\u0006\u0002\u00102J)\u00106\u001a\u00020\u0000\"\u0004\b\u0000\u0010%2\f\u00100\u001a\b\u0012\u0004\u0012\u0002H%012\u0006\u0010(\u001a\u0002H%H\u0016¢\u0006\u0002\u00102R(\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0011\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\t\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0015\"\u0004\b\u0019\u0010\u0017R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u00020\u001dX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u00067"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/impl/FirBinaryLogicExpressionImpl;", "Lorg/jetbrains/kotlin/fir/expressions/FirBinaryLogicExpression;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "annotations", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "leftOperand", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "rightOperand", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/contracts/description/LogicOperationKind;", "(Lorg/jetbrains/kotlin/KtSourceElement;Ljava/util/List;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/contracts/description/LogicOperationKind;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "setAnnotations-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "getKind", "()Lorg/jetbrains/kotlin/contracts/description/LogicOperationKind;", "getLeftOperand", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setLeftOperand", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "getRightOperand", "setRightOperand", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "", "replaceTypeRef", "newTypeRef", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/impl/FirBinaryLogicExpressionImpl;", "transformChildren", "transformLeftOperand", "transformOtherChildren", "transformRightOperand", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirBinaryLogicExpressionImpl extends FirBinaryLogicExpression {
    private List<T> annotations;
    private final LogicOperationKind kind;
    private FirExpression leftOperand;
    private FirExpression rightOperand;
    private final KtSourceElement source;
    private FirTypeRef typeRef;

    public /* synthetic */ FirBinaryLogicExpressionImpl(KtSourceElement ktSourceElement, List list, FirExpression firExpression, FirExpression firExpression2, LogicOperationKind logicOperationKind, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, list, firExpression, firExpression2, logicOperationKind);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m6900getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirBinaryLogicExpression transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirExpression transformAnnotations(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression
    public /* bridge */ /* synthetic */ FirBinaryLogicExpression transformLeftOperand(FirTransformer firTransformer, Object obj) {
        return transformLeftOperand((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression
    public /* bridge */ /* synthetic */ FirBinaryLogicExpression transformOtherChildren(FirTransformer firTransformer, Object obj) {
        return transformOtherChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression
    public /* bridge */ /* synthetic */ FirBinaryLogicExpression transformRightOperand(FirTransformer firTransformer, Object obj) {
        return transformRightOperand((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m6900getAnnotations5e3fPpI() {
        return this.annotations;
    }

    /* JADX INFO: renamed from: setAnnotations-GqUYU-s, reason: not valid java name */
    public void m6901setAnnotationsGqUYUs(List<T> list) {
        this.annotations = list;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression
    public FirExpression getLeftOperand() {
        return this.leftOperand;
    }

    public void setLeftOperand(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.leftOperand = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression
    public FirExpression getRightOperand() {
        return this.rightOperand;
    }

    public void setRightOperand(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.rightOperand = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression
    public LogicOperationKind getKind() {
        return this.kind;
    }

    private FirBinaryLogicExpressionImpl(KtSourceElement ktSourceElement, List<T> list, FirExpression leftOperand, FirExpression rightOperand, LogicOperationKind kind) {
        Intrinsics.checkNotNullParameter(leftOperand, "leftOperand");
        Intrinsics.checkNotNullParameter(rightOperand, "rightOperand");
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.source = ktSourceElement;
        this.annotations = list;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.kind = kind;
        this.typeRef = FirImplicitTypeRefImplWithoutSource.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression, org.jetbrains.kotlin.fir.expressions.FirExpression
    public FirTypeRef getTypeRef() {
        return this.typeRef;
    }

    public void setTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.typeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getTypeRef().accept(visitor, data);
        Iterator<T> it = MutableOrEmptyList.m6756boximpl(m6900getAnnotations5e3fPpI()).iterator();
        while (it.hasNext()) {
            ((FirAnnotation) it.next()).accept(visitor, data);
        }
        getLeftOperand().accept(visitor, data);
        getRightOperand().accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirBinaryLogicExpressionImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformLeftOperand((FirTransformer) transformer, (Object) data);
        transformRightOperand((FirTransformer) transformer, (Object) data);
        transformOtherChildren((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirBinaryLogicExpressionImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6900getAnnotations5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression
    public <D> FirBinaryLogicExpressionImpl transformLeftOperand(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setLeftOperand((FirExpression) getLeftOperand().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression
    public <D> FirBinaryLogicExpressionImpl transformRightOperand(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setRightOperand((FirExpression) getRightOperand().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression
    public <D> FirBinaryLogicExpressionImpl transformOtherChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setTypeRef((FirTypeRef) getTypeRef().transform(transformer, data));
        transformAnnotations((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression, org.jetbrains.kotlin.fir.expressions.FirExpression
    public void replaceTypeRef(FirTypeRef newTypeRef) {
        Intrinsics.checkNotNullParameter(newTypeRef, "newTypeRef");
        setTypeRef(newTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        m6901setAnnotationsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newAnnotations));
    }
}
