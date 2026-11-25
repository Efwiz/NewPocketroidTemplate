package org.jetbrains.kotlin.fir.expressions.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirAnonymousObjectExpressionImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ5\u0010\u0018\u001a\u00020\u0019\"\u0004\b\u0000\u0010\u001a\"\u0004\b\u0001\u0010\u001b2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u0002H\u001a\u0012\u0004\u0012\u0002H\u001b0\u001d2\u0006\u0010\u001e\u001a\u0002H\u001bH\u0016¢\u0006\u0002\u0010\u001fJ\u0016\u0010 \u001a\u00020\u00192\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016J\u0010\u0010\"\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\u0005H\u0016J)\u0010$\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001b2\f\u0010%\u001a\b\u0012\u0004\u0012\u0002H\u001b0&2\u0006\u0010\u001e\u001a\u0002H\u001bH\u0016¢\u0006\u0002\u0010'J)\u0010(\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001b2\f\u0010%\u001a\b\u0012\u0004\u0012\u0002H\u001b0&2\u0006\u0010\u001e\u001a\u0002H\u001bH\u0016¢\u0006\u0002\u0010'J)\u0010)\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001b2\f\u0010%\u001a\b\u0012\u0004\u0012\u0002H\u001b0&2\u0006\u0010\u001e\u001a\u0002H\u001bH\u0016¢\u0006\u0002\u0010'R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001a\u0010\u0006\u001a\u00020\u0007X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/impl/FirAnonymousObjectExpressionImpl;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousObjectExpression;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "anonymousObject", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;)V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "getAnonymousObject", "()Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "setAnonymousObject", "(Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;)V", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "replaceTypeRef", "newTypeRef", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/impl/FirAnonymousObjectExpressionImpl;", "transformAnonymousObject", "transformChildren", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirAnonymousObjectExpressionImpl extends FirAnonymousObjectExpression {
    private FirAnonymousObject anonymousObject;
    private final KtSourceElement source;
    private FirTypeRef typeRef;

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirAnonymousObjectExpressionImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnonymousObjectExpression transformAnnotations(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression
    public /* bridge */ /* synthetic */ FirAnonymousObjectExpression transformAnonymousObject(FirTransformer firTransformer, Object obj) {
        return transformAnonymousObject((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression, org.jetbrains.kotlin.fir.expressions.FirExpression
    public FirTypeRef getTypeRef() {
        return this.typeRef;
    }

    public void setTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.typeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression
    public FirAnonymousObject getAnonymousObject() {
        return this.anonymousObject;
    }

    public void setAnonymousObject(FirAnonymousObject firAnonymousObject) {
        Intrinsics.checkNotNullParameter(firAnonymousObject, "<set-?>");
        this.anonymousObject = firAnonymousObject;
    }

    public FirAnonymousObjectExpressionImpl(KtSourceElement ktSourceElement, FirTypeRef typeRef, FirAnonymousObject anonymousObject) {
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
        this.source = ktSourceElement;
        this.typeRef = typeRef;
        this.anonymousObject = anonymousObject;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public List<FirAnnotation> getAnnotations() {
        return getAnonymousObject().getAnnotations();
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getTypeRef().accept(visitor, data);
        getAnonymousObject().accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirAnonymousObjectExpressionImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setTypeRef((FirTypeRef) getTypeRef().transform(transformer, data));
        transformAnonymousObject((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        getAnonymousObject().replaceAnnotations(newAnnotations);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression
    public <D> FirAnonymousObjectExpressionImpl transformAnonymousObject(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setAnonymousObject((FirAnonymousObject) getAnonymousObject().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression, org.jetbrains.kotlin.fir.expressions.FirExpression
    public void replaceTypeRef(FirTypeRef newTypeRef) {
        Intrinsics.checkNotNullParameter(newTypeRef, "newTypeRef");
        setTypeRef(newTypeRef);
    }
}
