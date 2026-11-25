package org.jetbrains.kotlin.fir.expressions.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.MutableOrEmptyList;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitTypeRefImplWithoutSource;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirSingleExpressionBlock.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J5\u0010\u001d\u001a\u00020\u001e\"\u0004\b\u0000\u0010\u001f\"\u0004\b\u0001\u0010 2\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u0002H\u001f\u0012\u0004\u0012\u0002H 0\"2\u0006\u0010#\u001a\u0002H H\u0016¢\u0006\u0002\u0010$J\u0016\u0010%\u001a\u00020\u001e2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00070\u0015H\u0016J\u0010\u0010'\u001a\u00020\u001e2\u0006\u0010(\u001a\u00020\u0018H\u0016J)\u0010)\u001a\u00020\u0001\"\u0004\b\u0000\u0010 2\f\u0010*\u001a\b\u0012\u0004\u0012\u0002H 0+2\u0006\u0010#\u001a\u0002H H\u0016¢\u0006\u0002\u0010,J)\u0010-\u001a\u00020\u0000\"\u0004\b\u0000\u0010 2\f\u0010*\u001a\b\u0012\u0004\u0012\u0002H 0+2\u0006\u0010#\u001a\u0002H H\u0016¢\u0006\u0002\u0010.J)\u0010/\u001a\u00020\u0001\"\u0004\b\u0000\u0010 2\f\u0010*\u001a\b\u0012\u0004\u0012\u0002H 0+2\u0006\u0010#\u001a\u0002H H\u0016¢\u0006\u0002\u0010,J)\u00100\u001a\u00020\u0001\"\u0004\b\u0000\u0010 2\f\u0010*\u001a\b\u0012\u0004\u0012\u0002H 0+2\u0006\u0010#\u001a\u0002H H\u0016¢\u0006\u0002\u0010,R(\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0016\u0010\r\u001a\u0004\u0018\u00010\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0004R\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00030\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\tR\u001a\u0010\u0017\u001a\u00020\u0018X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001c\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/impl/FirSingleExpressionBlock;", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "statement", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "(Lorg/jetbrains/kotlin/fir/expressions/FirStatement;)V", "annotations", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "setAnnotations-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getStatement", "()Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "setStatement", "statements", "", "getStatements", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "replaceTypeRef", "newTypeRef", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "transformChildren", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/impl/FirSingleExpressionBlock;", "transformOtherChildren", "transformStatements", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSingleExpressionBlock extends FirBlock {
    private List<T> annotations;
    private FirStatement statement;
    private FirTypeRef typeRef;

    @Override // org.jetbrains.kotlin.fir.expressions.FirBlock, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m7012getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
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

    public final FirStatement getStatement() {
        return this.statement;
    }

    public final void setStatement(FirStatement firStatement) {
        Intrinsics.checkNotNullParameter(firStatement, "<set-?>");
        this.statement = firStatement;
    }

    public FirSingleExpressionBlock(FirStatement statement) {
        Intrinsics.checkNotNullParameter(statement, "statement");
        this.statement = statement;
        this.annotations = MutableOrEmptyList.INSTANCE.m6775empty5e3fPpI();
        this.typeRef = FirImplicitTypeRefImplWithoutSource.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirBlock, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        KtSourceElement source = this.statement.getSource();
        if (source != null) {
            return KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.SingleExpressionBlock.INSTANCE, 0, 0, 6, null);
        }
        return null;
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m7012getAnnotations5e3fPpI() {
        return this.annotations;
    }

    /* JADX INFO: renamed from: setAnnotations-GqUYU-s, reason: not valid java name */
    public void m7013setAnnotationsGqUYUs(List<T> list) {
        this.annotations = list;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirBlock
    public List<FirStatement> getStatements() {
        return CollectionsKt.listOf(this.statement);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirBlock, org.jetbrains.kotlin.fir.expressions.FirExpression
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
        Iterator<T> it = MutableOrEmptyList.m6756boximpl(m7012getAnnotations5e3fPpI()).iterator();
        while (it.hasNext()) {
            ((FirAnnotation) it.next()).accept(visitor, data);
        }
        this.statement.accept(visitor, data);
        getTypeRef().accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirSingleExpressionBlock transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformStatements(transformer, data);
        transformOtherChildren(transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirBlock, org.jetbrains.kotlin.fir.expressions.FirExpression
    public void replaceTypeRef(FirTypeRef newTypeRef) {
        Intrinsics.checkNotNullParameter(newTypeRef, "newTypeRef");
        setTypeRef(newTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirBlock
    public <D> FirBlock transformStatements(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        this.statement = (FirStatement) FirTransformerUtilKt.transformSingle(this.statement, transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirBlock
    public <D> FirBlock transformOtherChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformAnnotations((FirTransformer) transformer, (Object) data);
        setTypeRef((FirTypeRef) FirTransformerUtilKt.transformSingle(getTypeRef(), transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirBlock, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        m7013setAnnotationsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newAnnotations));
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirBlock, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirBlock transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m7012getAnnotations5e3fPpI(), transformer, data);
        return this;
    }
}
