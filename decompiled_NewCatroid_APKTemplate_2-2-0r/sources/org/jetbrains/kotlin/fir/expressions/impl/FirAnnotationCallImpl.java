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
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationResolvePhase;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirAnnotationCallImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001BO\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012¢\u0006\u0002\u0010\u0013J5\u00109\u001a\u00020:\"\u0004\b\u0000\u0010;\"\u0004\b\u0001\u0010<2\u0012\u0010=\u001a\u000e\u0012\u0004\u0012\u0002H;\u0012\u0004\u0012\u0002H<0>2\u0006\u0010?\u001a\u0002H<H\u0016¢\u0006\u0002\u0010@J\u0010\u0010A\u001a\u00020:2\u0006\u0010B\u001a\u00020\u0012H\u0016J\u0010\u0010C\u001a\u00020:2\u0006\u0010D\u001a\u00020\u0007H\u0016J\u0016\u0010E\u001a\u00020:2\f\u0010F\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH\u0016J\u0010\u0010G\u001a\u00020:2\u0006\u0010H\u001a\u00020\fH\u0016J\u0010\u0010I\u001a\u00020:2\u0006\u0010J\u001a\u00020\u0010H\u0016J\u0010\u0010K\u001a\u00020:2\u0006\u0010L\u001a\u00020\u000eH\u0016J\u0016\u0010M\u001a\u00020:2\f\u0010N\u001a\b\u0012\u0004\u0012\u00020\n0\u001dH\u0016J\u0010\u0010O\u001a\u00020:2\u0006\u0010P\u001a\u00020\u0007H\u0016J\u0012\u0010Q\u001a\u00020:2\b\u0010R\u001a\u0004\u0018\u00010\u0005H\u0016J)\u0010S\u001a\u00020\u0000\"\u0004\b\u0000\u0010<2\f\u0010T\u001a\b\u0012\u0004\u0012\u0002H<0U2\u0006\u0010?\u001a\u0002H<H\u0016¢\u0006\u0002\u0010VJ)\u0010W\u001a\u00020\u0000\"\u0004\b\u0000\u0010<2\f\u0010T\u001a\b\u0012\u0004\u0012\u0002H<0U2\u0006\u0010?\u001a\u0002H<H\u0016¢\u0006\u0002\u0010VJ)\u0010X\u001a\u00020\u0000\"\u0004\b\u0000\u0010<2\f\u0010T\u001a\b\u0012\u0004\u0012\u0002H<0U2\u0006\u0010?\u001a\u0002H<H\u0016¢\u0006\u0002\u0010VJ)\u0010Y\u001a\u00020\u0000\"\u0004\b\u0000\u0010<2\f\u0010T\u001a\b\u0012\u0004\u0012\u0002H<0U2\u0006\u0010?\u001a\u0002H<H\u0016¢\u0006\u0002\u0010VJ)\u0010Z\u001a\u00020\u0000\"\u0004\b\u0000\u0010<2\f\u0010T\u001a\b\u0012\u0004\u0012\u0002H<0U2\u0006\u0010?\u001a\u0002H<H\u0016¢\u0006\u0002\u0010VR\u001a\u0010\u0011\u001a\u00020\u0012X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0006\u001a\u00020\u0007X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u001a\u0010\u000b\u001a\u00020\fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001a\u0010\u000f\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u001a\u0010\r\u001a\u00020\u000eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R(\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u00102\u001a\u0004\b/\u0010 \"\u0004\b0\u00101R\u0014\u00103\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b4\u0010\u0019R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u00106\"\u0004\b7\u00108\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006["}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/impl/FirAnnotationCallImpl;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "useSiteTarget", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "annotationTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "typeArguments", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "argumentList", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "argumentMapping", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;", "annotationResolvePhase", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationResolvePhase;", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Ljava/util/List;Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;Lorg/jetbrains/kotlin/fir/references/FirReference;Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationResolvePhase;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotationResolvePhase", "()Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationResolvePhase;", "setAnnotationResolvePhase", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationResolvePhase;)V", "getAnnotationTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setAnnotationTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "getArgumentList", "()Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "setArgumentList", "(Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;)V", "getArgumentMapping", "()Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;", "setArgumentMapping", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;)V", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirReference;", "setCalleeReference", "(Lorg/jetbrains/kotlin/fir/references/FirReference;)V", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getTypeArguments-5e3fPpI", "setTypeArguments-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "typeRef", "getTypeRef", "getUseSiteTarget", "()Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "setUseSiteTarget", "(Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;)V", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotationResolvePhase", "newAnnotationResolvePhase", "replaceAnnotationTypeRef", "newAnnotationTypeRef", "replaceAnnotations", "newAnnotations", "replaceArgumentList", "newArgumentList", "replaceArgumentMapping", "newArgumentMapping", "replaceCalleeReference", "newCalleeReference", "replaceTypeArguments", "newTypeArguments", "replaceTypeRef", "newTypeRef", "replaceUseSiteTarget", "newUseSiteTarget", "transformAnnotationTypeRef", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/impl/FirAnnotationCallImpl;", "transformAnnotations", "transformCalleeReference", "transformChildren", "transformTypeArguments", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirAnnotationCallImpl extends FirAnnotationCall {
    private FirAnnotationResolvePhase annotationResolvePhase;
    private FirTypeRef annotationTypeRef;
    private FirArgumentList argumentList;
    private FirAnnotationArgumentMapping argumentMapping;
    private FirReference calleeReference;
    private final KtSourceElement source;
    private List<T> typeArguments;
    private AnnotationUseSiteTarget useSiteTarget;

    public /* synthetic */ FirAnnotationCallImpl(KtSourceElement ktSourceElement, AnnotationUseSiteTarget annotationUseSiteTarget, FirTypeRef firTypeRef, List list, FirArgumentList firArgumentList, FirReference firReference, FirAnnotationArgumentMapping firAnnotationArgumentMapping, FirAnnotationResolvePhase firAnnotationResolvePhase, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, annotationUseSiteTarget, firTypeRef, list, firArgumentList, firReference, firAnnotationArgumentMapping, firAnnotationResolvePhase);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression
    public void replaceTypeRef(FirTypeRef newTypeRef) {
        Intrinsics.checkNotNullParameter(newTypeRef, "newTypeRef");
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirAnnotationCallImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public /* bridge */ /* synthetic */ List getTypeArguments() {
        return MutableOrEmptyList.m6756boximpl(m6890getTypeArguments5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public /* bridge */ /* synthetic */ FirAnnotation transformAnnotationTypeRef(FirTransformer firTransformer, Object obj) {
        return transformAnnotationTypeRef((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public /* bridge */ /* synthetic */ FirAnnotationCall transformAnnotationTypeRef(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationCall transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirCall transformAnnotations(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public /* bridge */ /* synthetic */ FirAnnotationCall transformCalleeReference(FirTransformer firTransformer, Object obj) {
        return transformCalleeReference((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvable
    public /* bridge */ /* synthetic */ FirResolvable transformCalleeReference(FirTransformer firTransformer, Object obj) {
        return transformCalleeReference((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public /* bridge */ /* synthetic */ FirAnnotation transformTypeArguments(FirTransformer firTransformer, Object obj) {
        return transformTypeArguments((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public /* bridge */ /* synthetic */ FirAnnotationCall transformTypeArguments(FirTransformer firTransformer, Object obj) {
        return transformTypeArguments((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public AnnotationUseSiteTarget getUseSiteTarget() {
        return this.useSiteTarget;
    }

    public void setUseSiteTarget(AnnotationUseSiteTarget annotationUseSiteTarget) {
        this.useSiteTarget = annotationUseSiteTarget;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public FirTypeRef getAnnotationTypeRef() {
        return this.annotationTypeRef;
    }

    public void setAnnotationTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.annotationTypeRef = firTypeRef;
    }

    /* JADX INFO: renamed from: getTypeArguments-5e3fPpI, reason: not valid java name */
    public List<T> m6890getTypeArguments5e3fPpI() {
        return this.typeArguments;
    }

    /* JADX INFO: renamed from: setTypeArguments-GqUYU-s, reason: not valid java name */
    public void m6891setTypeArgumentsGqUYUs(List<T> list) {
        this.typeArguments = list;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirCall
    public FirArgumentList getArgumentList() {
        return this.argumentList;
    }

    public void setArgumentList(FirArgumentList firArgumentList) {
        Intrinsics.checkNotNullParameter(firArgumentList, "<set-?>");
        this.argumentList = firArgumentList;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public FirReference getCalleeReference() {
        return this.calleeReference;
    }

    public void setCalleeReference(FirReference firReference) {
        Intrinsics.checkNotNullParameter(firReference, "<set-?>");
        this.calleeReference = firReference;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public FirAnnotationArgumentMapping getArgumentMapping() {
        return this.argumentMapping;
    }

    public void setArgumentMapping(FirAnnotationArgumentMapping firAnnotationArgumentMapping) {
        Intrinsics.checkNotNullParameter(firAnnotationArgumentMapping, "<set-?>");
        this.argumentMapping = firAnnotationArgumentMapping;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall
    public FirAnnotationResolvePhase getAnnotationResolvePhase() {
        return this.annotationResolvePhase;
    }

    public void setAnnotationResolvePhase(FirAnnotationResolvePhase firAnnotationResolvePhase) {
        Intrinsics.checkNotNullParameter(firAnnotationResolvePhase, "<set-?>");
        this.annotationResolvePhase = firAnnotationResolvePhase;
    }

    private FirAnnotationCallImpl(KtSourceElement ktSourceElement, AnnotationUseSiteTarget annotationUseSiteTarget, FirTypeRef annotationTypeRef, List<T> list, FirArgumentList argumentList, FirReference calleeReference, FirAnnotationArgumentMapping argumentMapping, FirAnnotationResolvePhase annotationResolvePhase) {
        Intrinsics.checkNotNullParameter(annotationTypeRef, "annotationTypeRef");
        Intrinsics.checkNotNullParameter(argumentList, "argumentList");
        Intrinsics.checkNotNullParameter(calleeReference, "calleeReference");
        Intrinsics.checkNotNullParameter(argumentMapping, "argumentMapping");
        Intrinsics.checkNotNullParameter(annotationResolvePhase, "annotationResolvePhase");
        this.source = ktSourceElement;
        this.useSiteTarget = annotationUseSiteTarget;
        this.annotationTypeRef = annotationTypeRef;
        this.typeArguments = list;
        this.argumentList = argumentList;
        this.calleeReference = calleeReference;
        this.argumentMapping = argumentMapping;
        this.annotationResolvePhase = annotationResolvePhase;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression
    public FirTypeRef getTypeRef() {
        return getAnnotationTypeRef();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public List<FirAnnotation> getAnnotations() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getAnnotationTypeRef().accept(visitor, data);
        Iterator<T> it = MutableOrEmptyList.m6756boximpl(m6890getTypeArguments5e3fPpI()).iterator();
        while (it.hasNext()) {
            ((FirTypeProjection) it.next()).accept(visitor, data);
        }
        getArgumentList().accept(visitor, data);
        getCalleeReference().accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirAnnotationCallImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformAnnotationTypeRef((FirTransformer) transformer, (Object) data);
        transformTypeArguments((FirTransformer) transformer, (Object) data);
        setArgumentList((FirArgumentList) getArgumentList().transform(transformer, data));
        transformCalleeReference((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public <D> FirAnnotationCallImpl transformAnnotationTypeRef(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setAnnotationTypeRef((FirTypeRef) getAnnotationTypeRef().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public <D> FirAnnotationCallImpl transformTypeArguments(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6890getTypeArguments5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public <D> FirAnnotationCallImpl transformCalleeReference(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setCalleeReference((FirReference) getCalleeReference().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public void replaceUseSiteTarget(AnnotationUseSiteTarget newUseSiteTarget) {
        setUseSiteTarget(newUseSiteTarget);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public void replaceAnnotationTypeRef(FirTypeRef newAnnotationTypeRef) {
        Intrinsics.checkNotNullParameter(newAnnotationTypeRef, "newAnnotationTypeRef");
        setAnnotationTypeRef(newAnnotationTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public void replaceTypeArguments(List<? extends FirTypeProjection> newTypeArguments) {
        Intrinsics.checkNotNullParameter(newTypeArguments, "newTypeArguments");
        m6891setTypeArgumentsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newTypeArguments));
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirCall
    public void replaceArgumentList(FirArgumentList newArgumentList) {
        Intrinsics.checkNotNullParameter(newArgumentList, "newArgumentList");
        setArgumentList(newArgumentList);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public void replaceCalleeReference(FirReference newCalleeReference) {
        Intrinsics.checkNotNullParameter(newCalleeReference, "newCalleeReference");
        setCalleeReference(newCalleeReference);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall, org.jetbrains.kotlin.fir.expressions.FirAnnotation
    public void replaceArgumentMapping(FirAnnotationArgumentMapping newArgumentMapping) {
        Intrinsics.checkNotNullParameter(newArgumentMapping, "newArgumentMapping");
        setArgumentMapping(newArgumentMapping);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirAnnotationCall
    public void replaceAnnotationResolvePhase(FirAnnotationResolvePhase newAnnotationResolvePhase) {
        Intrinsics.checkNotNullParameter(newAnnotationResolvePhase, "newAnnotationResolvePhase");
        setAnnotationResolvePhase(newAnnotationResolvePhase);
    }
}
