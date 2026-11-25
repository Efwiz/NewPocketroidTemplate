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
import org.jetbrains.kotlin.fir.FirImplementationDetail;
import org.jetbrains.kotlin.fir.MutableOrEmptyList;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirCallableReferenceAccessImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b#\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001Bk\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\f\u001a\u00020\b\u0012\u0006\u0010\r\u001a\u00020\b\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013¢\u0006\u0002\u0010\u0014J5\u00106\u001a\u000207\"\u0004\b\u0000\u00108\"\u0004\b\u0001\u001092\u0012\u0010:\u001a\u000e\u0012\u0004\u0012\u0002H8\u0012\u0004\u0012\u0002H90;2\u0006\u0010<\u001a\u0002H9H\u0016¢\u0006\u0002\u0010=J\u0016\u0010>\u001a\u0002072\f\u0010?\u001a\b\u0012\u0004\u0012\u00020\u00060@H\u0016J\u0010\u0010A\u001a\u0002072\u0006\u0010B\u001a\u00020\u0011H\u0016J\u0010\u0010A\u001a\u0002072\u0006\u0010B\u001a\u00020CH\u0016J\u0016\u0010D\u001a\u0002072\f\u0010E\u001a\b\u0012\u0004\u0012\u00020\b0@H\u0016J\u0010\u0010F\u001a\u0002072\u0006\u0010G\u001a\u00020\bH\u0016J\u0012\u0010H\u001a\u0002072\b\u0010I\u001a\u0004\u0018\u00010\bH\u0016J\u0010\u0010J\u001a\u0002072\u0006\u0010K\u001a\u00020\bH\u0016J\u0010\u0010L\u001a\u0002072\u0006\u0010M\u001a\u00020\u0013H\u0016J\u0012\u0010N\u001a\u0002072\b\u0010O\u001a\u0004\u0018\u00010\u000fH\u0017J\u0016\u0010P\u001a\u0002072\f\u0010Q\u001a\b\u0012\u0004\u0012\u00020\n0@H\u0016J\u0010\u0010R\u001a\u0002072\u0006\u0010S\u001a\u00020\u0003H\u0016J)\u0010T\u001a\u00020\u0000\"\u0004\b\u0000\u001092\f\u0010U\u001a\b\u0012\u0004\u0012\u0002H90V2\u0006\u0010<\u001a\u0002H9H\u0016¢\u0006\u0002\u0010WJ)\u0010X\u001a\u00020\u0000\"\u0004\b\u0000\u001092\f\u0010U\u001a\b\u0012\u0004\u0012\u0002H90V2\u0006\u0010<\u001a\u0002H9H\u0016¢\u0006\u0002\u0010WJ)\u0010Y\u001a\u00020\u0000\"\u0004\b\u0000\u001092\f\u0010U\u001a\b\u0012\u0004\u0012\u0002H90V2\u0006\u0010<\u001a\u0002H9H\u0016¢\u0006\u0002\u0010WJ)\u0010Z\u001a\u00020\u0000\"\u0004\b\u0000\u001092\f\u0010U\u001a\b\u0012\u0004\u0012\u0002H90V2\u0006\u0010<\u001a\u0002H9H\u0016¢\u0006\u0002\u0010WJ)\u0010[\u001a\u00020\u0000\"\u0004\b\u0000\u001092\f\u0010U\u001a\b\u0012\u0004\u0012\u0002H90V2\u0006\u0010<\u001a\u0002H9H\u0016¢\u0006\u0002\u0010WR(\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0019\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0010\u001a\u00020\u0011X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR(\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0019\u001a\u0004\b\u001e\u0010\u0016\"\u0004\b\u001f\u0010\u0018R\u001a\u0010\f\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010!\"\u0004\b%\u0010#R\u001a\u0010\r\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010!\"\u0004\b'\u0010#R\u001a\u0010\u0012\u001a\u00020\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R(\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0019\u001a\u0004\b0\u0010\u0016\"\u0004\b1\u0010\u0018R\u001a\u0010\u0002\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u00103\"\u0004\b4\u00105\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\\"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/impl/FirCallableReferenceAccessImpl;", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "annotations", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "contextReceiverArguments", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "explicitReceiver", "dispatchReceiver", "extensionReceiver", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "hasQuestionMarkAtLHS", "", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/references/FirNamedReference;ZLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "setAnnotations-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "setCalleeReference", "(Lorg/jetbrains/kotlin/fir/references/FirNamedReference;)V", "getContextReceiverArguments-5e3fPpI", "setContextReceiverArguments-GqUYU-s", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setDispatchReceiver", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "getExplicitReceiver", "setExplicitReceiver", "getExtensionReceiver", "setExtensionReceiver", "getHasQuestionMarkAtLHS", "()Z", "setHasQuestionMarkAtLHS", "(Z)V", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "getTypeArguments-5e3fPpI", "setTypeArguments-GqUYU-s", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "", "replaceCalleeReference", "newCalleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "replaceContextReceiverArguments", "newContextReceiverArguments", "replaceDispatchReceiver", "newDispatchReceiver", "replaceExplicitReceiver", "newExplicitReceiver", "replaceExtensionReceiver", "newExtensionReceiver", "replaceHasQuestionMarkAtLHS", "newHasQuestionMarkAtLHS", "replaceSource", "newSource", "replaceTypeArguments", "newTypeArguments", "replaceTypeRef", "newTypeRef", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/impl/FirCallableReferenceAccessImpl;", "transformCalleeReference", "transformChildren", "transformExplicitReceiver", "transformTypeArguments", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirCallableReferenceAccessImpl extends FirCallableReferenceAccess {
    private List<T> annotations;
    private FirNamedReference calleeReference;
    private List<T> contextReceiverArguments;
    private FirExpression dispatchReceiver;
    private FirExpression explicitReceiver;
    private FirExpression extensionReceiver;
    private boolean hasQuestionMarkAtLHS;
    private KtSourceElement source;
    private List<T> typeArguments;
    private FirTypeRef typeRef;

    public /* synthetic */ FirCallableReferenceAccessImpl(FirTypeRef firTypeRef, List list, List list2, List list3, FirExpression firExpression, FirExpression firExpression2, FirExpression firExpression3, KtSourceElement ktSourceElement, FirNamedReference firNamedReference, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(firTypeRef, list, list2, list3, firExpression, firExpression2, firExpression3, ktSourceElement, firNamedReference, z);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m6906getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirContextReceiverArgumentListOwner
    public /* bridge */ /* synthetic */ List getContextReceiverArguments() {
        return MutableOrEmptyList.m6756boximpl(m6907getContextReceiverArguments5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public /* bridge */ /* synthetic */ List getTypeArguments() {
        return MutableOrEmptyList.m6756boximpl(m6908getTypeArguments5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirCallableReferenceAccess transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirExpression transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirQualifiedAccessExpression transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirStatement transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public /* bridge */ /* synthetic */ FirCallableReferenceAccess transformCalleeReference(FirTransformer firTransformer, Object obj) {
        return transformCalleeReference((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public /* bridge */ /* synthetic */ FirQualifiedAccessExpression transformCalleeReference(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public /* bridge */ /* synthetic */ FirCallableReferenceAccess transformExplicitReceiver(FirTransformer firTransformer, Object obj) {
        return transformExplicitReceiver((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public /* bridge */ /* synthetic */ FirQualifiedAccessExpression transformExplicitReceiver(FirTransformer firTransformer, Object obj) {
        return transformExplicitReceiver((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public /* bridge */ /* synthetic */ FirCallableReferenceAccess transformTypeArguments(FirTransformer firTransformer, Object obj) {
        return transformTypeArguments((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public /* bridge */ /* synthetic */ FirQualifiedAccessExpression transformTypeArguments(FirTransformer firTransformer, Object obj) {
        return transformTypeArguments((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression
    public FirTypeRef getTypeRef() {
        return this.typeRef;
    }

    public void setTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.typeRef = firTypeRef;
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m6906getAnnotations5e3fPpI() {
        return this.annotations;
    }

    /* JADX INFO: renamed from: setAnnotations-GqUYU-s, reason: not valid java name */
    public void m6909setAnnotationsGqUYUs(List<T> list) {
        this.annotations = list;
    }

    /* JADX INFO: renamed from: getContextReceiverArguments-5e3fPpI, reason: not valid java name */
    public List<T> m6907getContextReceiverArguments5e3fPpI() {
        return this.contextReceiverArguments;
    }

    /* JADX INFO: renamed from: setContextReceiverArguments-GqUYU-s, reason: not valid java name */
    public void m6910setContextReceiverArgumentsGqUYUs(List<T> list) {
        this.contextReceiverArguments = list;
    }

    /* JADX INFO: renamed from: getTypeArguments-5e3fPpI, reason: not valid java name */
    public List<T> m6908getTypeArguments5e3fPpI() {
        return this.typeArguments;
    }

    /* JADX INFO: renamed from: setTypeArguments-GqUYU-s, reason: not valid java name */
    public void m6911setTypeArgumentsGqUYUs(List<T> list) {
        this.typeArguments = list;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public FirExpression getExplicitReceiver() {
        return this.explicitReceiver;
    }

    public void setExplicitReceiver(FirExpression firExpression) {
        this.explicitReceiver = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public FirExpression getDispatchReceiver() {
        return this.dispatchReceiver;
    }

    public void setDispatchReceiver(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.dispatchReceiver = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public FirExpression getExtensionReceiver() {
        return this.extensionReceiver;
    }

    public void setExtensionReceiver(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.extensionReceiver = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    public void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public FirNamedReference getCalleeReference() {
        return this.calleeReference;
    }

    public void setCalleeReference(FirNamedReference firNamedReference) {
        Intrinsics.checkNotNullParameter(firNamedReference, "<set-?>");
        this.calleeReference = firNamedReference;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess
    public boolean getHasQuestionMarkAtLHS() {
        return this.hasQuestionMarkAtLHS;
    }

    public void setHasQuestionMarkAtLHS(boolean z) {
        this.hasQuestionMarkAtLHS = z;
    }

    private FirCallableReferenceAccessImpl(FirTypeRef typeRef, List<T> list, List<T> list2, List<T> list3, FirExpression firExpression, FirExpression dispatchReceiver, FirExpression extensionReceiver, KtSourceElement ktSourceElement, FirNamedReference calleeReference, boolean z) {
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        Intrinsics.checkNotNullParameter(dispatchReceiver, "dispatchReceiver");
        Intrinsics.checkNotNullParameter(extensionReceiver, "extensionReceiver");
        Intrinsics.checkNotNullParameter(calleeReference, "calleeReference");
        this.typeRef = typeRef;
        this.annotations = list;
        this.contextReceiverArguments = list2;
        this.typeArguments = list3;
        this.explicitReceiver = firExpression;
        this.dispatchReceiver = dispatchReceiver;
        this.extensionReceiver = extensionReceiver;
        this.source = ktSourceElement;
        this.calleeReference = calleeReference;
        this.hasQuestionMarkAtLHS = z;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getTypeRef().accept(visitor, data);
        Iterator<T> it = MutableOrEmptyList.m6756boximpl(m6906getAnnotations5e3fPpI()).iterator();
        while (it.hasNext()) {
            ((FirAnnotation) it.next()).accept(visitor, data);
        }
        Iterator<T> it2 = MutableOrEmptyList.m6756boximpl(m6907getContextReceiverArguments5e3fPpI()).iterator();
        while (it2.hasNext()) {
            ((FirExpression) it2.next()).accept(visitor, data);
        }
        Iterator<T> it3 = MutableOrEmptyList.m6756boximpl(m6908getTypeArguments5e3fPpI()).iterator();
        while (it3.hasNext()) {
            ((FirTypeProjection) it3.next()).accept(visitor, data);
        }
        FirExpression explicitReceiver = getExplicitReceiver();
        if (explicitReceiver != null) {
            explicitReceiver.accept(visitor, data);
        }
        if (getDispatchReceiver() != getExplicitReceiver()) {
            getDispatchReceiver().accept(visitor, data);
        }
        if (getExtensionReceiver() != getExplicitReceiver() && getExtensionReceiver() != getDispatchReceiver()) {
            getExtensionReceiver().accept(visitor, data);
        }
        getCalleeReference().accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirCallableReferenceAccessImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setTypeRef((FirTypeRef) getTypeRef().transform(transformer, data));
        transformAnnotations((FirTransformer) transformer, (Object) data);
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6907getContextReceiverArguments5e3fPpI(), transformer, data);
        transformTypeArguments((FirTransformer) transformer, (Object) data);
        FirExpression explicitReceiver = getExplicitReceiver();
        setExplicitReceiver(explicitReceiver != null ? (FirExpression) explicitReceiver.transform(transformer, data) : null);
        if (getDispatchReceiver() != getExplicitReceiver()) {
            setDispatchReceiver((FirExpression) getDispatchReceiver().transform(transformer, data));
        }
        if (getExtensionReceiver() != getExplicitReceiver() && getExtensionReceiver() != getDispatchReceiver()) {
            setExtensionReceiver((FirExpression) getExtensionReceiver().transform(transformer, data));
        }
        transformCalleeReference((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirCallableReferenceAccessImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6906getAnnotations5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public <D> FirCallableReferenceAccessImpl transformTypeArguments(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6908getTypeArguments5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public <D> FirCallableReferenceAccessImpl transformExplicitReceiver(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirExpression explicitReceiver = getExplicitReceiver();
        setExplicitReceiver(explicitReceiver != null ? (FirExpression) explicitReceiver.transform(transformer, data) : null);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public <D> FirCallableReferenceAccessImpl transformCalleeReference(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setCalleeReference((FirNamedReference) getCalleeReference().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression
    public void replaceTypeRef(FirTypeRef newTypeRef) {
        Intrinsics.checkNotNullParameter(newTypeRef, "newTypeRef");
        setTypeRef(newTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        m6909setAnnotationsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newAnnotations));
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirContextReceiverArgumentListOwner
    public void replaceContextReceiverArguments(List<? extends FirExpression> newContextReceiverArguments) {
        Intrinsics.checkNotNullParameter(newContextReceiverArguments, "newContextReceiverArguments");
        m6910setContextReceiverArgumentsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newContextReceiverArguments));
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public void replaceTypeArguments(List<? extends FirTypeProjection> newTypeArguments) {
        Intrinsics.checkNotNullParameter(newTypeArguments, "newTypeArguments");
        m6911setTypeArgumentsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newTypeArguments));
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public void replaceExplicitReceiver(FirExpression newExplicitReceiver) {
        setExplicitReceiver(newExplicitReceiver);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public void replaceDispatchReceiver(FirExpression newDispatchReceiver) {
        Intrinsics.checkNotNullParameter(newDispatchReceiver, "newDispatchReceiver");
        setDispatchReceiver(newDispatchReceiver);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public void replaceExtensionReceiver(FirExpression newExtensionReceiver) {
        Intrinsics.checkNotNullParameter(newExtensionReceiver, "newExtensionReceiver");
        setExtensionReceiver(newExtensionReceiver);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    @FirImplementationDetail
    public void replaceSource(KtSourceElement newSource) {
        setSource(newSource);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess
    public void replaceCalleeReference(FirNamedReference newCalleeReference) {
        Intrinsics.checkNotNullParameter(newCalleeReference, "newCalleeReference");
        setCalleeReference(newCalleeReference);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public void replaceCalleeReference(FirReference newCalleeReference) {
        Intrinsics.checkNotNullParameter(newCalleeReference, "newCalleeReference");
        if (!(newCalleeReference instanceof FirNamedReference)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        replaceCalleeReference((FirNamedReference) newCalleeReference);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess
    public void replaceHasQuestionMarkAtLHS(boolean newHasQuestionMarkAtLHS) {
        setHasQuestionMarkAtLHS(newHasQuestionMarkAtLHS);
    }
}
