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
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirPropertyAccessExpressionImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b!\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001Bs\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0005\u0012\b\u0010\r\u001a\u0004\u0018\u00010\n\u0012\u0006\u0010\u000e\u001a\u00020\n\u0012\u0006\u0010\u000f\u001a\u00020\n\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u0012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0005¢\u0006\u0002\u0010\u0014J5\u00104\u001a\u000205\"\u0004\b\u0000\u00106\"\u0004\b\u0001\u001072\u0012\u00108\u001a\u000e\u0012\u0004\u0012\u0002H6\u0012\u0004\u0012\u0002H7092\u0006\u0010:\u001a\u0002H7H\u0016¢\u0006\u0002\u0010;J\u0016\u0010<\u001a\u0002052\f\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00060>H\u0016J\u0010\u0010?\u001a\u0002052\u0006\u0010@\u001a\u00020\bH\u0016J\u0016\u0010A\u001a\u0002052\f\u0010B\u001a\b\u0012\u0004\u0012\u00020\n0>H\u0016J\u0010\u0010C\u001a\u0002052\u0006\u0010D\u001a\u00020\nH\u0016J\u0012\u0010E\u001a\u0002052\b\u0010F\u001a\u0004\u0018\u00010\nH\u0016J\u0010\u0010G\u001a\u0002052\u0006\u0010H\u001a\u00020\nH\u0016J\u0016\u0010I\u001a\u0002052\f\u0010J\u001a\b\u0012\u0004\u0012\u00020\u00130>H\u0016J\u0012\u0010K\u001a\u0002052\b\u0010L\u001a\u0004\u0018\u00010\u0011H\u0017J\u0016\u0010M\u001a\u0002052\f\u0010N\u001a\b\u0012\u0004\u0012\u00020\f0>H\u0016J\u0010\u0010O\u001a\u0002052\u0006\u0010P\u001a\u00020\u0003H\u0016J)\u0010Q\u001a\u00020\u0000\"\u0004\b\u0000\u001072\f\u0010R\u001a\b\u0012\u0004\u0012\u0002H70S2\u0006\u0010:\u001a\u0002H7H\u0016¢\u0006\u0002\u0010TJ)\u0010U\u001a\u00020\u0000\"\u0004\b\u0000\u001072\f\u0010R\u001a\b\u0012\u0004\u0012\u0002H70S2\u0006\u0010:\u001a\u0002H7H\u0016¢\u0006\u0002\u0010TJ)\u0010V\u001a\u00020\u0000\"\u0004\b\u0000\u001072\f\u0010R\u001a\b\u0012\u0004\u0012\u0002H70S2\u0006\u0010:\u001a\u0002H7H\u0016¢\u0006\u0002\u0010TJ)\u0010W\u001a\u00020\u0000\"\u0004\b\u0000\u001072\f\u0010R\u001a\b\u0012\u0004\u0012\u0002H70S2\u0006\u0010:\u001a\u0002H7H\u0016¢\u0006\u0002\u0010TJ)\u0010X\u001a\u00020\u0000\"\u0004\b\u0000\u001072\f\u0010R\u001a\b\u0012\u0004\u0012\u0002H70S2\u0006\u0010:\u001a\u0002H7H\u0016¢\u0006\u0002\u0010TR(\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0019\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR(\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0019\u001a\u0004\b\u001e\u0010\u0016\"\u0004\b\u001f\u0010\u0018R\u001a\u0010\u000e\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001c\u0010\r\u001a\u0004\u0018\u00010\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010!\"\u0004\b%\u0010#R\u001a\u0010\u000f\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010!\"\u0004\b'\u0010#R(\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0005X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0019\u001a\u0004\b(\u0010\u0016\"\u0004\b)\u0010\u0018R\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R(\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0005X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0019\u001a\u0004\b.\u0010\u0016\"\u0004\b/\u0010\u0018R\u001a\u0010\u0002\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u00101\"\u0004\b2\u00103\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006Y"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/impl/FirPropertyAccessExpressionImpl;", "Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "annotations", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "contextReceiverArguments", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "explicitReceiver", "dispatchReceiver", "extensionReceiver", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "nonFatalDiagnostics", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Ljava/util/List;Lorg/jetbrains/kotlin/fir/references/FirReference;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/KtSourceElement;Ljava/util/List;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "setAnnotations-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirReference;", "setCalleeReference", "(Lorg/jetbrains/kotlin/fir/references/FirReference;)V", "getContextReceiverArguments-5e3fPpI", "setContextReceiverArguments-GqUYU-s", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setDispatchReceiver", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "getExplicitReceiver", "setExplicitReceiver", "getExtensionReceiver", "setExtensionReceiver", "getNonFatalDiagnostics-5e3fPpI", "setNonFatalDiagnostics-GqUYU-s", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "getTypeArguments-5e3fPpI", "setTypeArguments-GqUYU-s", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "", "replaceCalleeReference", "newCalleeReference", "replaceContextReceiverArguments", "newContextReceiverArguments", "replaceDispatchReceiver", "newDispatchReceiver", "replaceExplicitReceiver", "newExplicitReceiver", "replaceExtensionReceiver", "newExtensionReceiver", "replaceNonFatalDiagnostics", "newNonFatalDiagnostics", "replaceSource", "newSource", "replaceTypeArguments", "newTypeArguments", "replaceTypeRef", "newTypeRef", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/impl/FirPropertyAccessExpressionImpl;", "transformCalleeReference", "transformChildren", "transformExplicitReceiver", "transformTypeArguments", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirPropertyAccessExpressionImpl extends FirPropertyAccessExpression {
    private List<T> annotations;
    private FirReference calleeReference;
    private List<T> contextReceiverArguments;
    private FirExpression dispatchReceiver;
    private FirExpression explicitReceiver;
    private FirExpression extensionReceiver;
    private List<T> nonFatalDiagnostics;
    private KtSourceElement source;
    private List<T> typeArguments;
    private FirTypeRef typeRef;

    @FirImplementationDetail
    public /* synthetic */ FirPropertyAccessExpressionImpl(FirTypeRef firTypeRef, List list, FirReference firReference, List list2, List list3, FirExpression firExpression, FirExpression firExpression2, FirExpression firExpression3, KtSourceElement ktSourceElement, List list4, DefaultConstructorMarker defaultConstructorMarker) {
        this(firTypeRef, list, firReference, list2, list3, firExpression, firExpression2, firExpression3, ktSourceElement, list4);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m6990getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirContextReceiverArgumentListOwner
    public /* bridge */ /* synthetic */ List getContextReceiverArguments() {
        return MutableOrEmptyList.m6756boximpl(m6991getContextReceiverArguments5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression
    public /* bridge */ /* synthetic */ List getNonFatalDiagnostics() {
        return MutableOrEmptyList.m6756boximpl(m6992getNonFatalDiagnostics5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public /* bridge */ /* synthetic */ List getTypeArguments() {
        return MutableOrEmptyList.m6756boximpl(m6993getTypeArguments5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirExpression transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirPropertyAccessExpression transformAnnotations(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public /* bridge */ /* synthetic */ FirPropertyAccessExpression transformCalleeReference(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public /* bridge */ /* synthetic */ FirPropertyAccessExpression transformExplicitReceiver(FirTransformer firTransformer, Object obj) {
        return transformExplicitReceiver((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public /* bridge */ /* synthetic */ FirQualifiedAccessExpression transformExplicitReceiver(FirTransformer firTransformer, Object obj) {
        return transformExplicitReceiver((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public /* bridge */ /* synthetic */ FirPropertyAccessExpression transformTypeArguments(FirTransformer firTransformer, Object obj) {
        return transformTypeArguments((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public /* bridge */ /* synthetic */ FirQualifiedAccessExpression transformTypeArguments(FirTransformer firTransformer, Object obj) {
        return transformTypeArguments((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression
    public FirTypeRef getTypeRef() {
        return this.typeRef;
    }

    public void setTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.typeRef = firTypeRef;
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m6990getAnnotations5e3fPpI() {
        return this.annotations;
    }

    /* JADX INFO: renamed from: setAnnotations-GqUYU-s, reason: not valid java name */
    public void m6994setAnnotationsGqUYUs(List<T> list) {
        this.annotations = list;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public FirReference getCalleeReference() {
        return this.calleeReference;
    }

    public void setCalleeReference(FirReference firReference) {
        Intrinsics.checkNotNullParameter(firReference, "<set-?>");
        this.calleeReference = firReference;
    }

    /* JADX INFO: renamed from: getContextReceiverArguments-5e3fPpI, reason: not valid java name */
    public List<T> m6991getContextReceiverArguments5e3fPpI() {
        return this.contextReceiverArguments;
    }

    /* JADX INFO: renamed from: setContextReceiverArguments-GqUYU-s, reason: not valid java name */
    public void m6995setContextReceiverArgumentsGqUYUs(List<T> list) {
        this.contextReceiverArguments = list;
    }

    /* JADX INFO: renamed from: getTypeArguments-5e3fPpI, reason: not valid java name */
    public List<T> m6993getTypeArguments5e3fPpI() {
        return this.typeArguments;
    }

    /* JADX INFO: renamed from: setTypeArguments-GqUYU-s, reason: not valid java name */
    public void m6997setTypeArgumentsGqUYUs(List<T> list) {
        this.typeArguments = list;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public FirExpression getExplicitReceiver() {
        return this.explicitReceiver;
    }

    public void setExplicitReceiver(FirExpression firExpression) {
        this.explicitReceiver = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public FirExpression getDispatchReceiver() {
        return this.dispatchReceiver;
    }

    public void setDispatchReceiver(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.dispatchReceiver = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public FirExpression getExtensionReceiver() {
        return this.extensionReceiver;
    }

    public void setExtensionReceiver(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.extensionReceiver = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    public void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    /* JADX INFO: renamed from: getNonFatalDiagnostics-5e3fPpI, reason: not valid java name */
    public List<T> m6992getNonFatalDiagnostics5e3fPpI() {
        return this.nonFatalDiagnostics;
    }

    /* JADX INFO: renamed from: setNonFatalDiagnostics-GqUYU-s, reason: not valid java name */
    public void m6996setNonFatalDiagnosticsGqUYUs(List<T> list) {
        this.nonFatalDiagnostics = list;
    }

    private FirPropertyAccessExpressionImpl(FirTypeRef typeRef, List<T> list, FirReference calleeReference, List<T> list2, List<T> list3, FirExpression firExpression, FirExpression dispatchReceiver, FirExpression extensionReceiver, KtSourceElement ktSourceElement, List<T> list4) {
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        Intrinsics.checkNotNullParameter(calleeReference, "calleeReference");
        Intrinsics.checkNotNullParameter(dispatchReceiver, "dispatchReceiver");
        Intrinsics.checkNotNullParameter(extensionReceiver, "extensionReceiver");
        this.typeRef = typeRef;
        this.annotations = list;
        this.calleeReference = calleeReference;
        this.contextReceiverArguments = list2;
        this.typeArguments = list3;
        this.explicitReceiver = firExpression;
        this.dispatchReceiver = dispatchReceiver;
        this.extensionReceiver = extensionReceiver;
        this.source = ktSourceElement;
        this.nonFatalDiagnostics = list4;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getTypeRef().accept(visitor, data);
        Iterator<T> it = MutableOrEmptyList.m6756boximpl(m6990getAnnotations5e3fPpI()).iterator();
        while (it.hasNext()) {
            ((FirAnnotation) it.next()).accept(visitor, data);
        }
        getCalleeReference().accept(visitor, data);
        Iterator<T> it2 = MutableOrEmptyList.m6756boximpl(m6991getContextReceiverArguments5e3fPpI()).iterator();
        while (it2.hasNext()) {
            ((FirExpression) it2.next()).accept(visitor, data);
        }
        Iterator<T> it3 = MutableOrEmptyList.m6756boximpl(m6993getTypeArguments5e3fPpI()).iterator();
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
        if (getExtensionReceiver() == getExplicitReceiver() || getExtensionReceiver() == getDispatchReceiver()) {
            return;
        }
        getExtensionReceiver().accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirPropertyAccessExpressionImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setTypeRef((FirTypeRef) getTypeRef().transform(transformer, data));
        transformAnnotations((FirTransformer) transformer, (Object) data);
        transformCalleeReference((FirTransformer) transformer, (Object) data);
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6991getContextReceiverArguments5e3fPpI(), transformer, data);
        transformTypeArguments((FirTransformer) transformer, (Object) data);
        FirExpression explicitReceiver = getExplicitReceiver();
        setExplicitReceiver(explicitReceiver != null ? (FirExpression) explicitReceiver.transform(transformer, data) : null);
        if (getDispatchReceiver() != getExplicitReceiver()) {
            setDispatchReceiver((FirExpression) getDispatchReceiver().transform(transformer, data));
        }
        if (getExtensionReceiver() != getExplicitReceiver() && getExtensionReceiver() != getDispatchReceiver()) {
            setExtensionReceiver((FirExpression) getExtensionReceiver().transform(transformer, data));
        }
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirPropertyAccessExpressionImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6990getAnnotations5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public <D> FirPropertyAccessExpressionImpl transformCalleeReference(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setCalleeReference((FirReference) getCalleeReference().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public <D> FirPropertyAccessExpressionImpl transformTypeArguments(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6993getTypeArguments5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public <D> FirPropertyAccessExpressionImpl transformExplicitReceiver(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirExpression explicitReceiver = getExplicitReceiver();
        setExplicitReceiver(explicitReceiver != null ? (FirExpression) explicitReceiver.transform(transformer, data) : null);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression
    public void replaceTypeRef(FirTypeRef newTypeRef) {
        Intrinsics.checkNotNullParameter(newTypeRef, "newTypeRef");
        setTypeRef(newTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        m6994setAnnotationsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newAnnotations));
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public void replaceCalleeReference(FirReference newCalleeReference) {
        Intrinsics.checkNotNullParameter(newCalleeReference, "newCalleeReference");
        setCalleeReference(newCalleeReference);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirContextReceiverArgumentListOwner
    public void replaceContextReceiverArguments(List<? extends FirExpression> newContextReceiverArguments) {
        Intrinsics.checkNotNullParameter(newContextReceiverArguments, "newContextReceiverArguments");
        m6995setContextReceiverArgumentsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newContextReceiverArguments));
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public void replaceTypeArguments(List<? extends FirTypeProjection> newTypeArguments) {
        Intrinsics.checkNotNullParameter(newTypeArguments, "newTypeArguments");
        m6997setTypeArgumentsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newTypeArguments));
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public void replaceExplicitReceiver(FirExpression newExplicitReceiver) {
        setExplicitReceiver(newExplicitReceiver);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public void replaceDispatchReceiver(FirExpression newDispatchReceiver) {
        Intrinsics.checkNotNullParameter(newDispatchReceiver, "newDispatchReceiver");
        setDispatchReceiver(newDispatchReceiver);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public void replaceExtensionReceiver(FirExpression newExtensionReceiver) {
        Intrinsics.checkNotNullParameter(newExtensionReceiver, "newExtensionReceiver");
        setExtensionReceiver(newExtensionReceiver);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    @FirImplementationDetail
    public void replaceSource(KtSourceElement newSource) {
        setSource(newSource);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression
    public void replaceNonFatalDiagnostics(List<? extends ConeDiagnostic> newNonFatalDiagnostics) {
        Intrinsics.checkNotNullParameter(newNonFatalDiagnostics, "newNonFatalDiagnostics");
        m6996setNonFatalDiagnosticsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newNonFatalDiagnostics));
    }
}
