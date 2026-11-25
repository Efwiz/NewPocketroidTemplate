package org.jetbrains.kotlin.fir.expressions.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.MutableOrEmptyList;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationArgumentMapping;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirAnnotationImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u0013\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B7\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\u0002\u0010\rJ5\u0010&\u001a\u00020'\"\u0004\b\u0000\u0010(\"\u0004\b\u0001\u0010)2\u0012\u0010*\u001a\u000e\u0012\u0004\u0012\u0002H(\u0012\u0004\u0012\u0002H)0+2\u0006\u0010,\u001a\u0002H)H\u0016¢\u0006\u0002\u0010-J\u0010\u0010.\u001a\u00020'2\u0006\u0010/\u001a\u00020\u0007H\u0016J\u0016\u00100\u001a\u00020'2\f\u00101\u001a\b\u0012\u0004\u0012\u00020\u00010\u0013H\u0016J\u0010\u00102\u001a\u00020'2\u0006\u00103\u001a\u00020\tH\u0016J\u0016\u00104\u001a\u00020'2\f\u00105\u001a\b\u0012\u0004\u0012\u00020\f0\u0013H\u0016J\u0010\u00106\u001a\u00020'2\u0006\u00107\u001a\u00020\u0007H\u0016J\u0012\u00108\u001a\u00020'2\b\u00109\u001a\u0004\u0018\u00010\u0005H\u0016J)\u0010:\u001a\u00020\u0000\"\u0004\b\u0000\u0010)2\f\u0010;\u001a\b\u0012\u0004\u0012\u0002H)0<2\u0006\u0010,\u001a\u0002H)H\u0016¢\u0006\u0002\u0010=J)\u0010>\u001a\u00020\u0000\"\u0004\b\u0000\u0010)2\f\u0010;\u001a\b\u0012\u0004\u0012\u0002H)0<2\u0006\u0010,\u001a\u0002H)H\u0016¢\u0006\u0002\u0010=J)\u0010?\u001a\u00020\u0000\"\u0004\b\u0000\u0010)2\f\u0010;\u001a\b\u0012\u0004\u0012\u0002H)0<2\u0006\u0010,\u001a\u0002H)H\u0016¢\u0006\u0002\u0010=J)\u0010@\u001a\u00020\u0000\"\u0004\b\u0000\u0010)2\f\u0010;\u001a\b\u0012\u0004\u0012\u0002H)0<2\u0006\u0010,\u001a\u0002H)H\u0016¢\u0006\u0002\u0010=R\u001a\u0010\u0006\u001a\u00020\u0007X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\b\u001a\u00020\tX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR(\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u001f\u001a\u0004\b\u001c\u0010\u0015\"\u0004\b\u001d\u0010\u001eR\u0014\u0010 \u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\u000fR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006A"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/impl/FirAnnotationImpl;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "useSiteTarget", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "annotationTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "argumentMapping", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;", "typeArguments", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;Ljava/util/List;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotationTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setAnnotationTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "annotations", "", "getAnnotations", "()Ljava/util/List;", "getArgumentMapping", "()Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;", "setArgumentMapping", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;)V", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getTypeArguments-5e3fPpI", "setTypeArguments-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "typeRef", "getTypeRef", "getUseSiteTarget", "()Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "setUseSiteTarget", "(Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;)V", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotationTypeRef", "newAnnotationTypeRef", "replaceAnnotations", "newAnnotations", "replaceArgumentMapping", "newArgumentMapping", "replaceTypeArguments", "newTypeArguments", "replaceTypeRef", "newTypeRef", "replaceUseSiteTarget", "newUseSiteTarget", "transformAnnotationTypeRef", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/impl/FirAnnotationImpl;", "transformAnnotations", "transformChildren", "transformTypeArguments", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirAnnotationImpl extends FirAnnotation {
    private FirTypeRef annotationTypeRef;
    private FirAnnotationArgumentMapping argumentMapping;
    private final KtSourceElement source;
    private List<T> typeArguments;
    private AnnotationUseSiteTarget useSiteTarget;

    public /* synthetic */ FirAnnotationImpl(KtSourceElement ktSourceElement, AnnotationUseSiteTarget annotationUseSiteTarget, FirTypeRef firTypeRef, FirAnnotationArgumentMapping firAnnotationArgumentMapping, List list, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, annotationUseSiteTarget, firTypeRef, firAnnotationArgumentMapping, list);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression
    public void replaceTypeRef(FirTypeRef newTypeRef) {
        Intrinsics.checkNotNullParameter(newTypeRef, "newTypeRef");
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirAnnotationImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public /* bridge */ /* synthetic */ List getTypeArguments() {
        return MutableOrEmptyList.m6756boximpl(m6892getTypeArguments5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public /* bridge */ /* synthetic */ FirAnnotation transformAnnotationTypeRef(FirTransformer firTransformer, Object obj) {
        return transformAnnotationTypeRef((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotation transformAnnotations(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public /* bridge */ /* synthetic */ FirAnnotation transformTypeArguments(FirTransformer firTransformer, Object obj) {
        return transformTypeArguments((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public AnnotationUseSiteTarget getUseSiteTarget() {
        return this.useSiteTarget;
    }

    public void setUseSiteTarget(AnnotationUseSiteTarget annotationUseSiteTarget) {
        this.useSiteTarget = annotationUseSiteTarget;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public FirTypeRef getAnnotationTypeRef() {
        return this.annotationTypeRef;
    }

    public void setAnnotationTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.annotationTypeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public FirAnnotationArgumentMapping getArgumentMapping() {
        return this.argumentMapping;
    }

    public void setArgumentMapping(FirAnnotationArgumentMapping firAnnotationArgumentMapping) {
        Intrinsics.checkNotNullParameter(firAnnotationArgumentMapping, "<set-?>");
        this.argumentMapping = firAnnotationArgumentMapping;
    }

    /* JADX INFO: renamed from: getTypeArguments-5e3fPpI, reason: not valid java name */
    public List<T> m6892getTypeArguments5e3fPpI() {
        return this.typeArguments;
    }

    /* JADX INFO: renamed from: setTypeArguments-GqUYU-s, reason: not valid java name */
    public void m6893setTypeArgumentsGqUYUs(List<T> list) {
        this.typeArguments = list;
    }

    private FirAnnotationImpl(KtSourceElement ktSourceElement, AnnotationUseSiteTarget annotationUseSiteTarget, FirTypeRef annotationTypeRef, FirAnnotationArgumentMapping argumentMapping, List<T> list) {
        Intrinsics.checkNotNullParameter(annotationTypeRef, "annotationTypeRef");
        Intrinsics.checkNotNullParameter(argumentMapping, "argumentMapping");
        this.source = ktSourceElement;
        this.useSiteTarget = annotationUseSiteTarget;
        this.annotationTypeRef = annotationTypeRef;
        this.argumentMapping = argumentMapping;
        this.typeArguments = list;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression
    public FirTypeRef getTypeRef() {
        return getAnnotationTypeRef();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public List<FirAnnotation> getAnnotations() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getAnnotationTypeRef().accept(visitor, data);
        getArgumentMapping().accept(visitor, data);
        Iterator<T> it = MutableOrEmptyList.m6756boximpl(m6892getTypeArguments5e3fPpI()).iterator();
        while (it.hasNext()) {
            ((FirTypeProjection) it.next()).accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirAnnotationImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformAnnotationTypeRef((FirTransformer) transformer, (Object) data);
        setArgumentMapping((FirAnnotationArgumentMapping) getArgumentMapping().transform(transformer, data));
        transformTypeArguments((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public <D> FirAnnotationImpl transformAnnotationTypeRef(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setAnnotationTypeRef((FirTypeRef) getAnnotationTypeRef().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public <D> FirAnnotationImpl transformTypeArguments(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6892getTypeArguments5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public void replaceUseSiteTarget(AnnotationUseSiteTarget newUseSiteTarget) {
        setUseSiteTarget(newUseSiteTarget);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public void replaceAnnotationTypeRef(FirTypeRef newAnnotationTypeRef) {
        Intrinsics.checkNotNullParameter(newAnnotationTypeRef, "newAnnotationTypeRef");
        setAnnotationTypeRef(newAnnotationTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public void replaceArgumentMapping(FirAnnotationArgumentMapping newArgumentMapping) {
        Intrinsics.checkNotNullParameter(newArgumentMapping, "newArgumentMapping");
        setArgumentMapping(newArgumentMapping);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public void replaceTypeArguments(List<? extends FirTypeProjection> newTypeArguments) {
        Intrinsics.checkNotNullParameter(newTypeArguments, "newTypeArguments");
        m6893setTypeArgumentsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newTypeArguments));
    }
}
