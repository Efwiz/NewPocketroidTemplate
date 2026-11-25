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
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirComponentCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCallOrigin;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.impl.FirSimpleNamedReference;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitTypeRefImplWithoutSource;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirComponentCallImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001Ba\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003\u0012\u0006\u0010\t\u001a\u00020\u0006\u0012\u0006\u0010\n\u001a\u00020\u0006\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0006\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J5\u0010>\u001a\u00020?\"\u0004\b\u0000\u0010@\"\u0004\b\u0001\u0010A2\u0012\u0010B\u001a\u000e\u0012\u0004\u0012\u0002H@\u0012\u0004\u0012\u0002HA0C2\u0006\u0010D\u001a\u0002HAH\u0016¢\u0006\u0002\u0010EJ\u0016\u0010F\u001a\u00020?2\f\u0010G\u001a\b\u0012\u0004\u0012\u00020\u00040HH\u0016J\u0010\u0010I\u001a\u00020?2\u0006\u0010J\u001a\u00020\u000eH\u0016J\u0010\u0010K\u001a\u00020?2\u0006\u0010L\u001a\u00020\u001dH\u0016J\u0010\u0010K\u001a\u00020?2\u0006\u0010L\u001a\u00020MH\u0016J\u0016\u0010N\u001a\u00020?2\f\u0010O\u001a\b\u0012\u0004\u0012\u00020\u00060HH\u0016J\u0010\u0010P\u001a\u00020?2\u0006\u0010Q\u001a\u00020\u0006H\u0016J\u0012\u0010R\u001a\u00020?2\b\u0010S\u001a\u0004\u0018\u00010\u0006H\u0016J\u0010\u0010T\u001a\u00020?2\u0006\u0010U\u001a\u00020\u0006H\u0016J\u0012\u0010V\u001a\u00020?2\b\u0010W\u001a\u0004\u0018\u00010\fH\u0017J\u0016\u0010X\u001a\u00020?2\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020\b0HH\u0016J\u0010\u0010Z\u001a\u00020?2\u0006\u0010[\u001a\u000209H\u0016J)\u0010\\\u001a\u00020\u0000\"\u0004\b\u0000\u0010A2\f\u0010]\u001a\b\u0012\u0004\u0012\u0002HA0^2\u0006\u0010D\u001a\u0002HAH\u0016¢\u0006\u0002\u0010_J)\u0010`\u001a\u00020\u0000\"\u0004\b\u0000\u0010A2\f\u0010]\u001a\b\u0012\u0004\u0012\u0002HA0^2\u0006\u0010D\u001a\u0002HAH\u0016¢\u0006\u0002\u0010_J)\u0010a\u001a\u00020\u0000\"\u0004\b\u0000\u0010A2\f\u0010]\u001a\b\u0012\u0004\u0012\u0002HA0^2\u0006\u0010D\u001a\u0002HAH\u0016¢\u0006\u0002\u0010_J)\u0010b\u001a\u00020\u0000\"\u0004\b\u0000\u0010A2\f\u0010]\u001a\b\u0012\u0004\u0012\u0002HA0^2\u0006\u0010D\u001a\u0002HAH\u0016¢\u0006\u0002\u0010_J)\u0010c\u001a\u00020\u0000\"\u0004\b\u0000\u0010A2\f\u0010]\u001a\b\u0012\u0004\u0012\u0002HA0^2\u0006\u0010D\u001a\u0002HAH\u0016¢\u0006\u0002\u0010_R(\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0017\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\r\u001a\u00020\u000eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u00020\u001dX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u0014\u0010\u0010\u001a\u00020\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R(\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0017\u001a\u0004\b$\u0010\u0014\"\u0004\b%\u0010\u0016R\u001a\u0010\t\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u001a\u0010\u000f\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010'\"\u0004\b+\u0010)R\u001a\u0010\n\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010'\"\u0004\b-\u0010)R\u0014\u0010.\u001a\u00020/X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u00103\"\u0004\b4\u00105R(\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0017\u001a\u0004\b6\u0010\u0014\"\u0004\b7\u0010\u0016R\u001a\u00108\u001a\u000209X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006d"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/impl/FirComponentCallImpl;", "Lorg/jetbrains/kotlin/fir/expressions/FirComponentCall;", "annotations", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "contextReceiverArguments", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "dispatchReceiver", "extensionReceiver", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "argumentList", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "explicitReceiver", "componentIndex", "", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;ILkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "setAnnotations-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "getArgumentList", "()Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "setArgumentList", "(Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;)V", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "setCalleeReference", "(Lorg/jetbrains/kotlin/fir/references/FirNamedReference;)V", "getComponentIndex", "()I", "getContextReceiverArguments-5e3fPpI", "setContextReceiverArguments-GqUYU-s", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setDispatchReceiver", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "getExplicitReceiver", "setExplicitReceiver", "getExtensionReceiver", "setExtensionReceiver", "origin", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCallOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCallOrigin;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "getTypeArguments-5e3fPpI", "setTypeArguments-GqUYU-s", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "", "replaceArgumentList", "newArgumentList", "replaceCalleeReference", "newCalleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "replaceContextReceiverArguments", "newContextReceiverArguments", "replaceDispatchReceiver", "newDispatchReceiver", "replaceExplicitReceiver", "newExplicitReceiver", "replaceExtensionReceiver", "newExtensionReceiver", "replaceSource", "newSource", "replaceTypeArguments", "newTypeArguments", "replaceTypeRef", "newTypeRef", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/impl/FirComponentCallImpl;", "transformCalleeReference", "transformChildren", "transformExplicitReceiver", "transformTypeArguments", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirComponentCallImpl extends FirComponentCall {
    private List<T> annotations;
    private FirArgumentList argumentList;
    private FirNamedReference calleeReference;
    private final int componentIndex;
    private List<T> contextReceiverArguments;
    private FirExpression dispatchReceiver;
    private FirExpression explicitReceiver;
    private FirExpression extensionReceiver;
    private final FirFunctionCallOrigin origin;
    private KtSourceElement source;
    private List<T> typeArguments;
    private FirTypeRef typeRef;

    public /* synthetic */ FirComponentCallImpl(List list, List list2, List list3, FirExpression firExpression, FirExpression firExpression2, KtSourceElement ktSourceElement, FirArgumentList firArgumentList, FirExpression firExpression3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, list2, list3, firExpression, firExpression2, ktSourceElement, firArgumentList, firExpression3, i);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m6920getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirContextReceiverArgumentListOwner
    public /* bridge */ /* synthetic */ List getContextReceiverArguments() {
        return MutableOrEmptyList.m6756boximpl(m6921getContextReceiverArguments5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public /* bridge */ /* synthetic */ List getTypeArguments() {
        return MutableOrEmptyList.m6756boximpl(m6922getTypeArguments5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirCall transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirComponentCall transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirExpression transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirFunctionCall transformAnnotations(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public /* bridge */ /* synthetic */ FirComponentCall transformCalleeReference(FirTransformer firTransformer, Object obj) {
        return transformCalleeReference((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public /* bridge */ /* synthetic */ FirFunctionCall transformCalleeReference(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public /* bridge */ /* synthetic */ FirComponentCall transformExplicitReceiver(FirTransformer firTransformer, Object obj) {
        return transformExplicitReceiver((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public /* bridge */ /* synthetic */ FirFunctionCall transformExplicitReceiver(FirTransformer firTransformer, Object obj) {
        return transformExplicitReceiver((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public /* bridge */ /* synthetic */ FirQualifiedAccessExpression transformExplicitReceiver(FirTransformer firTransformer, Object obj) {
        return transformExplicitReceiver((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public /* bridge */ /* synthetic */ FirComponentCall transformTypeArguments(FirTransformer firTransformer, Object obj) {
        return transformTypeArguments((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public /* bridge */ /* synthetic */ FirFunctionCall transformTypeArguments(FirTransformer firTransformer, Object obj) {
        return transformTypeArguments((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public /* bridge */ /* synthetic */ FirQualifiedAccessExpression transformTypeArguments(FirTransformer firTransformer, Object obj) {
        return transformTypeArguments((FirTransformer<? super Object>) firTransformer, obj);
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m6920getAnnotations5e3fPpI() {
        return this.annotations;
    }

    /* JADX INFO: renamed from: setAnnotations-GqUYU-s, reason: not valid java name */
    public void m6923setAnnotationsGqUYUs(List<T> list) {
        this.annotations = list;
    }

    /* JADX INFO: renamed from: getContextReceiverArguments-5e3fPpI, reason: not valid java name */
    public List<T> m6921getContextReceiverArguments5e3fPpI() {
        return this.contextReceiverArguments;
    }

    /* JADX INFO: renamed from: setContextReceiverArguments-GqUYU-s, reason: not valid java name */
    public void m6924setContextReceiverArgumentsGqUYUs(List<T> list) {
        this.contextReceiverArguments = list;
    }

    /* JADX INFO: renamed from: getTypeArguments-5e3fPpI, reason: not valid java name */
    public List<T> m6922getTypeArguments5e3fPpI() {
        return this.typeArguments;
    }

    /* JADX INFO: renamed from: setTypeArguments-GqUYU-s, reason: not valid java name */
    public void m6925setTypeArgumentsGqUYUs(List<T> list) {
        this.typeArguments = list;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public FirExpression getDispatchReceiver() {
        return this.dispatchReceiver;
    }

    public void setDispatchReceiver(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.dispatchReceiver = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public FirExpression getExtensionReceiver() {
        return this.extensionReceiver;
    }

    public void setExtensionReceiver(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.extensionReceiver = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    public void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirCall
    public FirArgumentList getArgumentList() {
        return this.argumentList;
    }

    public void setArgumentList(FirArgumentList firArgumentList) {
        Intrinsics.checkNotNullParameter(firArgumentList, "<set-?>");
        this.argumentList = firArgumentList;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public FirExpression getExplicitReceiver() {
        return this.explicitReceiver;
    }

    public void setExplicitReceiver(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.explicitReceiver = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall
    public int getComponentIndex() {
        return this.componentIndex;
    }

    private FirComponentCallImpl(List<T> list, List<T> list2, List<T> list3, FirExpression dispatchReceiver, FirExpression extensionReceiver, KtSourceElement ktSourceElement, FirArgumentList argumentList, FirExpression explicitReceiver, int i) {
        Intrinsics.checkNotNullParameter(dispatchReceiver, "dispatchReceiver");
        Intrinsics.checkNotNullParameter(extensionReceiver, "extensionReceiver");
        Intrinsics.checkNotNullParameter(argumentList, "argumentList");
        Intrinsics.checkNotNullParameter(explicitReceiver, "explicitReceiver");
        this.annotations = list;
        this.contextReceiverArguments = list2;
        this.typeArguments = list3;
        this.dispatchReceiver = dispatchReceiver;
        this.extensionReceiver = extensionReceiver;
        this.source = ktSourceElement;
        this.argumentList = argumentList;
        this.explicitReceiver = explicitReceiver;
        this.componentIndex = i;
        this.typeRef = FirImplicitTypeRefImplWithoutSource.INSTANCE;
        KtSourceElement source = getSource();
        Name nameIdentifier = Name.identifier("component" + getComponentIndex());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        this.calleeReference = new FirSimpleNamedReference(source, nameIdentifier);
        this.origin = FirFunctionCallOrigin.Operator;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression
    public FirTypeRef getTypeRef() {
        return this.typeRef;
    }

    public void setTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.typeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public FirNamedReference getCalleeReference() {
        return this.calleeReference;
    }

    public void setCalleeReference(FirNamedReference firNamedReference) {
        Intrinsics.checkNotNullParameter(firNamedReference, "<set-?>");
        this.calleeReference = firNamedReference;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall
    public FirFunctionCallOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getTypeRef().accept(visitor, data);
        Iterator<T> it = MutableOrEmptyList.m6756boximpl(m6920getAnnotations5e3fPpI()).iterator();
        while (it.hasNext()) {
            ((FirAnnotation) it.next()).accept(visitor, data);
        }
        Iterator<T> it2 = MutableOrEmptyList.m6756boximpl(m6921getContextReceiverArguments5e3fPpI()).iterator();
        while (it2.hasNext()) {
            ((FirExpression) it2.next()).accept(visitor, data);
        }
        Iterator<T> it3 = MutableOrEmptyList.m6756boximpl(m6922getTypeArguments5e3fPpI()).iterator();
        while (it3.hasNext()) {
            ((FirTypeProjection) it3.next()).accept(visitor, data);
        }
        getArgumentList().accept(visitor, data);
        getCalleeReference().accept(visitor, data);
        getExplicitReceiver().accept(visitor, data);
        if (getDispatchReceiver() != getExplicitReceiver()) {
            getDispatchReceiver().accept(visitor, data);
        }
        if (getExtensionReceiver() == getExplicitReceiver() || getExtensionReceiver() == getDispatchReceiver()) {
            return;
        }
        getExtensionReceiver().accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirComponentCallImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setTypeRef((FirTypeRef) getTypeRef().transform(transformer, data));
        transformAnnotations((FirTransformer) transformer, (Object) data);
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6921getContextReceiverArguments5e3fPpI(), transformer, data);
        transformTypeArguments((FirTransformer) transformer, (Object) data);
        setArgumentList((FirArgumentList) getArgumentList().transform(transformer, data));
        transformCalleeReference((FirTransformer) transformer, (Object) data);
        setExplicitReceiver((FirExpression) getExplicitReceiver().transform(transformer, data));
        if (getDispatchReceiver() != getExplicitReceiver()) {
            setDispatchReceiver((FirExpression) getDispatchReceiver().transform(transformer, data));
        }
        if (getExtensionReceiver() != getExplicitReceiver() && getExtensionReceiver() != getDispatchReceiver()) {
            setExtensionReceiver((FirExpression) getExtensionReceiver().transform(transformer, data));
        }
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirComponentCallImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6920getAnnotations5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public <D> FirComponentCallImpl transformTypeArguments(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m6922getTypeArguments5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public <D> FirComponentCallImpl transformCalleeReference(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setCalleeReference((FirNamedReference) getCalleeReference().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public <D> FirComponentCallImpl transformExplicitReceiver(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setExplicitReceiver((FirExpression) getExplicitReceiver().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression
    public void replaceTypeRef(FirTypeRef newTypeRef) {
        Intrinsics.checkNotNullParameter(newTypeRef, "newTypeRef");
        setTypeRef(newTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        m6923setAnnotationsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newAnnotations));
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirContextReceiverArgumentListOwner
    public void replaceContextReceiverArguments(List<? extends FirExpression> newContextReceiverArguments) {
        Intrinsics.checkNotNullParameter(newContextReceiverArguments, "newContextReceiverArguments");
        m6924setContextReceiverArgumentsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newContextReceiverArguments));
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public void replaceTypeArguments(List<? extends FirTypeProjection> newTypeArguments) {
        Intrinsics.checkNotNullParameter(newTypeArguments, "newTypeArguments");
        m6925setTypeArgumentsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newTypeArguments));
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public void replaceDispatchReceiver(FirExpression newDispatchReceiver) {
        Intrinsics.checkNotNullParameter(newDispatchReceiver, "newDispatchReceiver");
        setDispatchReceiver(newDispatchReceiver);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public void replaceExtensionReceiver(FirExpression newExtensionReceiver) {
        Intrinsics.checkNotNullParameter(newExtensionReceiver, "newExtensionReceiver");
        setExtensionReceiver(newExtensionReceiver);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    @FirImplementationDetail
    public void replaceSource(KtSourceElement newSource) {
        setSource(newSource);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirCall
    public void replaceArgumentList(FirArgumentList newArgumentList) {
        Intrinsics.checkNotNullParameter(newArgumentList, "newArgumentList");
        setArgumentList(newArgumentList);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall
    public void replaceCalleeReference(FirNamedReference newCalleeReference) {
        Intrinsics.checkNotNullParameter(newCalleeReference, "newCalleeReference");
        setCalleeReference(newCalleeReference);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public void replaceCalleeReference(FirReference newCalleeReference) {
        Intrinsics.checkNotNullParameter(newCalleeReference, "newCalleeReference");
        if (!(newCalleeReference instanceof FirNamedReference)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        replaceCalleeReference((FirNamedReference) newCalleeReference);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirComponentCall, org.jetbrains.kotlin.fir.expressions.FirFunctionCall, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public void replaceExplicitReceiver(FirExpression newExplicitReceiver) {
        if (newExplicitReceiver == null) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        setExplicitReceiver(newExplicitReceiver);
    }
}
