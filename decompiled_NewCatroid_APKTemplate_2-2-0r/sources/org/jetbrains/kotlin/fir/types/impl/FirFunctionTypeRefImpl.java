package org.jetbrains.kotlin.fir.types.impl;

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
import org.jetbrains.kotlin.fir.FirFunctionTypeParameter;
import org.jetbrains.kotlin.fir.MutableOrEmptyList;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.types.FirFunctionTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirFunctionTypeRefImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B[\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u0012\u0006\u0010\u000e\u001a\u00020\n\u0012\u0006\u0010\u000f\u001a\u00020\b\u0012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\n0\f¢\u0006\u0002\u0010\u0011J5\u0010\"\u001a\u00020#\"\u0004\b\u0000\u0010$\"\u0004\b\u0001\u0010%2\u0012\u0010&\u001a\u000e\u0012\u0004\u0012\u0002H$\u0012\u0004\u0012\u0002H%0'2\u0006\u0010(\u001a\u0002H%H\u0016¢\u0006\u0002\u0010)J\u0016\u0010*\u001a\u00020#2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00060,H\u0016J)\u0010-\u001a\u00020\u0000\"\u0004\b\u0000\u0010%2\f\u0010.\u001a\b\u0012\u0004\u0012\u0002H%0/2\u0006\u0010(\u001a\u0002H%H\u0016¢\u0006\u0002\u00100J)\u00101\u001a\u00020\u0000\"\u0004\b\u0000\u0010%2\f\u0010.\u001a\b\u0012\u0004\u0012\u0002H%0/2\u0006\u0010(\u001a\u0002H%H\u0016¢\u0006\u0002\u00100R(\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0016\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\n0\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0013R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0018R\u0014\u0010\u000f\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0018R\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0013R\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001a\u0010\u000e\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001b\"\u0004\b\u001f\u0010\u001dR\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/impl/FirFunctionTypeRefImpl;", "Lorg/jetbrains/kotlin/fir/types/FirFunctionTypeRef;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "annotations", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "isMarkedNullable", "", "receiverTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "parameters", "", "Lorg/jetbrains/kotlin/fir/FirFunctionTypeParameter;", "returnTypeRef", "isSuspend", "contextReceiverTypeRefs", "(Lorg/jetbrains/kotlin/KtSourceElement;Ljava/util/List;ZLorg/jetbrains/kotlin/fir/types/FirTypeRef;Ljava/util/List;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;ZLjava/util/List;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "setAnnotations-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "getContextReceiverTypeRefs", "()Z", "getParameters", "getReceiverTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setReceiverTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "getReturnTypeRef", "setReturnTypeRef", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/impl/FirFunctionTypeRefImpl;", "transformChildren", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirFunctionTypeRefImpl extends FirFunctionTypeRef {
    private List<T> annotations;
    private final List<FirTypeRef> contextReceiverTypeRefs;
    private final boolean isMarkedNullable;
    private final boolean isSuspend;
    private final List<FirFunctionTypeParameter> parameters;
    private FirTypeRef receiverTypeRef;
    private FirTypeRef returnTypeRef;
    private final KtSourceElement source;

    public /* synthetic */ FirFunctionTypeRefImpl(KtSourceElement ktSourceElement, List list, boolean z, FirTypeRef firTypeRef, List list2, FirTypeRef firTypeRef2, boolean z2, List list3, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktSourceElement, list, z, firTypeRef, list2, firTypeRef2, z2, list3);
    }

    @Override // org.jetbrains.kotlin.fir.types.FirFunctionTypeRef, org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m7074getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.types.FirFunctionTypeRef, org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirFunctionTypeRef transformAnnotations(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.types.FirFunctionTypeRef, org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m7074getAnnotations5e3fPpI() {
        return this.annotations;
    }

    /* JADX INFO: renamed from: setAnnotations-GqUYU-s, reason: not valid java name */
    public void m7075setAnnotationsGqUYUs(List<T> list) {
        this.annotations = list;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirFunctionTypeRef, org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability
    /* JADX INFO: renamed from: isMarkedNullable, reason: from getter */
    public boolean getIsMarkedNullable() {
        return this.isMarkedNullable;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirFunctionTypeRef
    public FirTypeRef getReceiverTypeRef() {
        return this.receiverTypeRef;
    }

    public void setReceiverTypeRef(FirTypeRef firTypeRef) {
        this.receiverTypeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirFunctionTypeRef
    public List<FirFunctionTypeParameter> getParameters() {
        return this.parameters;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirFunctionTypeRef
    public FirTypeRef getReturnTypeRef() {
        return this.returnTypeRef;
    }

    public void setReturnTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.returnTypeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirFunctionTypeRef
    /* JADX INFO: renamed from: isSuspend, reason: from getter */
    public boolean getIsSuspend() {
        return this.isSuspend;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirFunctionTypeRef
    public List<FirTypeRef> getContextReceiverTypeRefs() {
        return this.contextReceiverTypeRefs;
    }

    private FirFunctionTypeRefImpl(KtSourceElement ktSourceElement, List<T> list, boolean z, FirTypeRef firTypeRef, List<FirFunctionTypeParameter> parameters, FirTypeRef returnTypeRef, boolean z2, List<FirTypeRef> contextReceiverTypeRefs) {
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(returnTypeRef, "returnTypeRef");
        Intrinsics.checkNotNullParameter(contextReceiverTypeRefs, "contextReceiverTypeRefs");
        this.source = ktSourceElement;
        this.annotations = list;
        this.isMarkedNullable = z;
        this.receiverTypeRef = firTypeRef;
        this.parameters = parameters;
        this.returnTypeRef = returnTypeRef;
        this.isSuspend = z2;
        this.contextReceiverTypeRefs = contextReceiverTypeRefs;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<T> it = MutableOrEmptyList.m6756boximpl(m7074getAnnotations5e3fPpI()).iterator();
        while (it.hasNext()) {
            ((FirAnnotation) it.next()).accept(visitor, data);
        }
        FirTypeRef receiverTypeRef = getReceiverTypeRef();
        if (receiverTypeRef != null) {
            receiverTypeRef.accept(visitor, data);
        }
        Iterator<T> it2 = getParameters().iterator();
        while (it2.hasNext()) {
            ((FirFunctionTypeParameter) it2.next()).accept(visitor, data);
        }
        getReturnTypeRef().accept(visitor, data);
        Iterator<T> it3 = getContextReceiverTypeRefs().iterator();
        while (it3.hasNext()) {
            ((FirTypeRef) it3.next()).accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirFunctionTypeRefImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformAnnotations((FirTransformer) transformer, (Object) data);
        FirTypeRef receiverTypeRef = getReceiverTypeRef();
        setReceiverTypeRef(receiverTypeRef != null ? (FirTypeRef) receiverTypeRef.transform(transformer, data) : null);
        FirTransformerUtilKt.transformInplace(getParameters(), transformer, data);
        setReturnTypeRef((FirTypeRef) getReturnTypeRef().transform(transformer, data));
        FirTransformerUtilKt.transformInplace(getContextReceiverTypeRefs(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirFunctionTypeRef, org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirFunctionTypeRefImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m7074getAnnotations5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.types.FirFunctionTypeRef, org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability, org.jetbrains.kotlin.fir.types.FirTypeRef, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        m7075setAnnotationsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newAnnotations));
    }
}
