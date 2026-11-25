package org.jetbrains.kotlin.fir.types.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.MutableOrEmptyList;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.types.FirQualifierPart;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability;
import org.jetbrains.kotlin.fir.types.FirUserTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirUserTypeRefImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B3\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\u0002\u0010\fJ5\u0010\u001a\u001a\u00020\u001b\"\u0004\b\u0000\u0010\u001c\"\u0004\b\u0001\u0010\u001d2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u0002H\u001c\u0012\u0004\u0012\u0002H\u001d0\u001f2\u0006\u0010 \u001a\u0002H\u001dH\u0016¢\u0006\u0002\u0010!J\u0016\u0010\"\u001a\u00020\u001b2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u000b0$H\u0016J)\u0010%\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u001d2\f\u0010&\u001a\b\u0012\u0004\u0012\u0002H\u001d0'2\u0006\u0010 \u001a\u0002H\u001dH\u0016¢\u0006\u0002\u0010(J)\u0010)\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001d2\f\u0010&\u001a\b\u0012\u0004\u0012\u0002H\u001d0'2\u0006\u0010 \u001a\u0002H\u001dH\u0016¢\u0006\u0002\u0010*R(\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0011\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0012\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0014R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000eR\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/impl/FirUserTypeRefImpl;", "Lorg/jetbrains/kotlin/fir/types/FirUserTypeRef;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "isMarkedNullable", "", "qualifier", "", "Lorg/jetbrains/kotlin/fir/types/FirQualifierPart;", "annotations", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "(Lorg/jetbrains/kotlin/KtSourceElement;ZLjava/util/List;Ljava/util/List;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "setAnnotations-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "customRenderer", "getCustomRenderer", "()Z", "getQualifier", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirUserTypeRef;", "transformChildren", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/impl/FirUserTypeRefImpl;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirUserTypeRefImpl extends FirUserTypeRef {
    private List<T> annotations;
    private final boolean isMarkedNullable;
    private final List<FirQualifierPart> qualifier;
    private KtSourceElement source;

    public /* synthetic */ FirUserTypeRefImpl(KtSourceElement ktSourceElement, boolean z, List list, List list2, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, z, list, list2);
    }

    @Override // org.jetbrains.kotlin.fir.types.FirUserTypeRef
    public boolean getCustomRenderer() {
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirUserTypeRef, org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m7080getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirTypeRef transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirTypeRefWithNullability transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.types.FirUserTypeRef, org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    public void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirUserTypeRef, org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability
    /* JADX INFO: renamed from: isMarkedNullable, reason: from getter */
    public boolean getIsMarkedNullable() {
        return this.isMarkedNullable;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirUserTypeRef
    public List<FirQualifierPart> getQualifier() {
        return this.qualifier;
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m7080getAnnotations5e3fPpI() {
        return this.annotations;
    }

    /* JADX INFO: renamed from: setAnnotations-GqUYU-s, reason: not valid java name */
    public void m7081setAnnotationsGqUYUs(List<T> list) {
        this.annotations = list;
    }

    private FirUserTypeRefImpl(KtSourceElement ktSourceElement, boolean z, List<FirQualifierPart> qualifier, List<T> list) {
        Intrinsics.checkNotNullParameter(qualifier, "qualifier");
        this.source = ktSourceElement;
        this.isMarkedNullable = z;
        this.qualifier = qualifier;
        this.annotations = list;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<FirQualifierPart> it = getQualifier().iterator();
        while (it.hasNext()) {
            Iterator<T> it2 = it.next().getTypeArgumentList().getTypeArguments().iterator();
            while (it2.hasNext()) {
                ((FirTypeProjection) it2.next()).accept(visitor, data);
            }
        }
        Iterator<T> it3 = MutableOrEmptyList.m6756boximpl(m7080getAnnotations5e3fPpI()).iterator();
        while (it3.hasNext()) {
            ((FirAnnotation) it3.next()).accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirUserTypeRefImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        Iterator<FirQualifierPart> it = getQualifier().iterator();
        while (it.hasNext()) {
            List<FirTypeProjection> typeArguments = it.next().getTypeArgumentList().getTypeArguments();
            Intrinsics.checkNotNull(typeArguments, "null cannot be cast to non-null type kotlin.collections.MutableList<org.jetbrains.kotlin.fir.types.FirTypeProjection>");
            FirTransformerUtilKt.transformInplace(TypeIntrinsics.asMutableList(typeArguments), transformer, data);
        }
        transformAnnotations((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirUserTypeRef, org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        m7081setAnnotationsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newAnnotations));
    }

    @Override // org.jetbrains.kotlin.fir.types.FirUserTypeRef, org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirUserTypeRef transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m7080getAnnotations5e3fPpI(), transformer, data);
        return this;
    }
}
