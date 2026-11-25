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
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirThisReference;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: FirThisReceiverExpressionImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b#\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001BQ\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010¢\u0006\u0002\u0010\u0011J5\u00103\u001a\u000204\"\u0004\b\u0000\u00105\"\u0004\b\u0001\u001062\u0012\u00107\u001a\u000e\u0012\u0004\u0012\u0002H5\u0012\u0004\u0012\u0002H6082\u0006\u00109\u001a\u0002H6H\u0016¢\u0006\u0002\u0010:J\u0016\u0010;\u001a\u0002042\f\u0010<\u001a\b\u0012\u0004\u0012\u00020\u00060=H\u0016J\u0010\u0010>\u001a\u0002042\u0006\u0010?\u001a\u00020@H\u0016J\u0010\u0010>\u001a\u0002042\u0006\u0010?\u001a\u00020\u000eH\u0016J\u0016\u0010A\u001a\u0002042\f\u0010B\u001a\b\u0012\u0004\u0012\u00020\b0=H\u0016J\u0010\u0010C\u001a\u0002042\u0006\u0010D\u001a\u00020\bH\u0016J\u0012\u0010E\u001a\u0002042\b\u0010F\u001a\u0004\u0018\u00010\bH\u0016J\u0010\u0010G\u001a\u0002042\u0006\u0010H\u001a\u00020\bH\u0016J\u0012\u0010I\u001a\u0002042\b\u0010J\u001a\u0004\u0018\u00010\fH\u0017J\u0016\u0010K\u001a\u0002042\f\u0010L\u001a\b\u0012\u0004\u0012\u00020\n0=H\u0016J\u0010\u0010M\u001a\u0002042\u0006\u0010N\u001a\u00020\u0003H\u0016J)\u0010O\u001a\u00020\u0000\"\u0004\b\u0000\u001062\f\u0010P\u001a\b\u0012\u0004\u0012\u0002H60Q2\u0006\u00109\u001a\u0002H6H\u0016¢\u0006\u0002\u0010RJ)\u0010S\u001a\u00020\u0000\"\u0004\b\u0000\u001062\f\u0010P\u001a\b\u0012\u0004\u0012\u0002H60Q2\u0006\u00109\u001a\u0002H6H\u0016¢\u0006\u0002\u0010RJ)\u0010T\u001a\u00020\u0000\"\u0004\b\u0000\u001062\f\u0010P\u001a\b\u0012\u0004\u0012\u0002H60Q2\u0006\u00109\u001a\u0002H6H\u0016¢\u0006\u0002\u0010RJ)\u0010U\u001a\u00020\u0000\"\u0004\b\u0000\u001062\f\u0010P\u001a\b\u0012\u0004\u0012\u0002H60Q2\u0006\u00109\u001a\u0002H6H\u0016¢\u0006\u0002\u0010RJ)\u0010V\u001a\u00020\u0000\"\u0004\b\u0000\u001062\f\u0010P\u001a\b\u0012\u0004\u0012\u0002H60Q2\u0006\u00109\u001a\u0002H6H\u0016¢\u0006\u0002\u0010RR(\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0016\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\r\u001a\u00020\u000eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR(\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0016\u001a\u0004\b\u001b\u0010\u0013\"\u0004\b\u001c\u0010\u0015R\u001a\u0010\u001d\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001c\u0010\"\u001a\u0004\u0018\u00010\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u001f\"\u0004\b$\u0010!R\u001a\u0010%\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u001f\"\u0004\b'\u0010!R\u0014\u0010\u000f\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010(R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R(\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0016\u001a\u0004\b-\u0010\u0013\"\u0004\b.\u0010\u0015R\u001a\u0010\u0002\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u00100\"\u0004\b1\u00102\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006W"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/impl/FirThisReceiverExpressionImpl;", "Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "annotations", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "contextReceiverArguments", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirThisReference;", "isImplicit", "", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/references/FirThisReference;ZLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "setAnnotations-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirThisReference;", "setCalleeReference", "(Lorg/jetbrains/kotlin/fir/references/FirThisReference;)V", "getContextReceiverArguments-5e3fPpI", "setContextReceiverArguments-GqUYU-s", "dispatchReceiver", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setDispatchReceiver", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "explicitReceiver", "getExplicitReceiver", "setExplicitReceiver", "extensionReceiver", "getExtensionReceiver", "setExtensionReceiver", "()Z", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "getTypeArguments-5e3fPpI", "setTypeArguments-GqUYU-s", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "", "replaceCalleeReference", "newCalleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "replaceContextReceiverArguments", "newContextReceiverArguments", "replaceDispatchReceiver", "newDispatchReceiver", "replaceExplicitReceiver", "newExplicitReceiver", "replaceExtensionReceiver", "newExtensionReceiver", "replaceSource", "newSource", "replaceTypeArguments", "newTypeArguments", "replaceTypeRef", "newTypeRef", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/impl/FirThisReceiverExpressionImpl;", "transformCalleeReference", "transformChildren", "transformExplicitReceiver", "transformTypeArguments", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirThisReceiverExpressionImpl extends FirThisReceiverExpression {
    private List<T> annotations;
    private FirThisReference calleeReference;
    private List<T> contextReceiverArguments;
    private FirExpression dispatchReceiver;
    private FirExpression explicitReceiver;
    private FirExpression extensionReceiver;
    private final boolean isImplicit;
    private KtSourceElement source;
    private List<T> typeArguments;
    private FirTypeRef typeRef;

    public /* synthetic */ FirThisReceiverExpressionImpl(FirTypeRef firTypeRef, List list, List list2, List list3, KtSourceElement ktSourceElement, FirThisReference firThisReference, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(firTypeRef, list, list2, list3, ktSourceElement, firThisReference, z);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m7020getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirContextReceiverArgumentListOwner
    public /* bridge */ /* synthetic */ List getContextReceiverArguments() {
        return MutableOrEmptyList.m6756boximpl(m7021getContextReceiverArguments5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public /* bridge */ /* synthetic */ List getTypeArguments() {
        return MutableOrEmptyList.m6756boximpl(m7022getTypeArguments5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirThisReceiverExpression transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public /* bridge */ /* synthetic */ FirQualifiedAccessExpression transformCalleeReference(FirTransformer firTransformer, Object obj) {
        return transformCalleeReference((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirResolvable
    public /* bridge */ /* synthetic */ FirResolvable transformCalleeReference(FirTransformer firTransformer, Object obj) {
        return transformCalleeReference((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public /* bridge */ /* synthetic */ FirThisReceiverExpression transformCalleeReference(FirTransformer firTransformer, Object obj) {
        return transformCalleeReference((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public /* bridge */ /* synthetic */ FirQualifiedAccessExpression transformExplicitReceiver(FirTransformer firTransformer, Object obj) {
        return transformExplicitReceiver((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public /* bridge */ /* synthetic */ FirThisReceiverExpression transformExplicitReceiver(FirTransformer firTransformer, Object obj) {
        return transformExplicitReceiver((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public /* bridge */ /* synthetic */ FirQualifiedAccessExpression transformTypeArguments(FirTransformer firTransformer, Object obj) {
        return transformTypeArguments((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public /* bridge */ /* synthetic */ FirThisReceiverExpression transformTypeArguments(FirTransformer firTransformer, Object obj) {
        return transformTypeArguments((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression
    public FirTypeRef getTypeRef() {
        return this.typeRef;
    }

    public void setTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.typeRef = firTypeRef;
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m7020getAnnotations5e3fPpI() {
        return this.annotations;
    }

    /* JADX INFO: renamed from: setAnnotations-GqUYU-s, reason: not valid java name */
    public void m7023setAnnotationsGqUYUs(List<T> list) {
        this.annotations = list;
    }

    /* JADX INFO: renamed from: getContextReceiverArguments-5e3fPpI, reason: not valid java name */
    public List<T> m7021getContextReceiverArguments5e3fPpI() {
        return this.contextReceiverArguments;
    }

    /* JADX INFO: renamed from: setContextReceiverArguments-GqUYU-s, reason: not valid java name */
    public void m7024setContextReceiverArgumentsGqUYUs(List<T> list) {
        this.contextReceiverArguments = list;
    }

    /* JADX INFO: renamed from: getTypeArguments-5e3fPpI, reason: not valid java name */
    public List<T> m7022getTypeArguments5e3fPpI() {
        return this.typeArguments;
    }

    /* JADX INFO: renamed from: setTypeArguments-GqUYU-s, reason: not valid java name */
    public void m7025setTypeArgumentsGqUYUs(List<T> list) {
        this.typeArguments = list;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    public void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public FirThisReference getCalleeReference() {
        return this.calleeReference;
    }

    public void setCalleeReference(FirThisReference firThisReference) {
        Intrinsics.checkNotNullParameter(firThisReference, "<set-?>");
        this.calleeReference = firThisReference;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression
    /* JADX INFO: renamed from: isImplicit, reason: from getter */
    public boolean getIsImplicit() {
        return this.isImplicit;
    }

    private FirThisReceiverExpressionImpl(FirTypeRef typeRef, List<T> list, List<T> list2, List<T> list3, KtSourceElement ktSourceElement, FirThisReference calleeReference, boolean z) {
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        Intrinsics.checkNotNullParameter(calleeReference, "calleeReference");
        this.typeRef = typeRef;
        this.annotations = list;
        this.contextReceiverArguments = list2;
        this.typeArguments = list3;
        this.source = ktSourceElement;
        this.calleeReference = calleeReference;
        this.isImplicit = z;
        this.dispatchReceiver = FirNoReceiverExpression.INSTANCE;
        this.extensionReceiver = FirNoReceiverExpression.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public FirExpression getExplicitReceiver() {
        return this.explicitReceiver;
    }

    public void setExplicitReceiver(FirExpression firExpression) {
        this.explicitReceiver = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public FirExpression getDispatchReceiver() {
        return this.dispatchReceiver;
    }

    public void setDispatchReceiver(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.dispatchReceiver = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public FirExpression getExtensionReceiver() {
        return this.extensionReceiver;
    }

    public void setExtensionReceiver(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.extensionReceiver = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getTypeRef().accept(visitor, data);
        Iterator<T> it = MutableOrEmptyList.m6756boximpl(m7020getAnnotations5e3fPpI()).iterator();
        while (it.hasNext()) {
            ((FirAnnotation) it.next()).accept(visitor, data);
        }
        Iterator<T> it2 = MutableOrEmptyList.m6756boximpl(m7021getContextReceiverArguments5e3fPpI()).iterator();
        while (it2.hasNext()) {
            ((FirExpression) it2.next()).accept(visitor, data);
        }
        Iterator<T> it3 = MutableOrEmptyList.m6756boximpl(m7022getTypeArguments5e3fPpI()).iterator();
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
    public <D> FirThisReceiverExpressionImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setTypeRef((FirTypeRef) getTypeRef().transform(transformer, data));
        transformAnnotations((FirTransformer) transformer, (Object) data);
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m7021getContextReceiverArguments5e3fPpI(), transformer, data);
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

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirThisReceiverExpressionImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m7020getAnnotations5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public <D> FirThisReceiverExpressionImpl transformTypeArguments(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m7022getTypeArguments5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public <D> FirThisReceiverExpressionImpl transformExplicitReceiver(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirExpression explicitReceiver = getExplicitReceiver();
        setExplicitReceiver(explicitReceiver != null ? (FirExpression) explicitReceiver.transform(transformer, data) : null);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public <D> FirThisReceiverExpressionImpl transformCalleeReference(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setCalleeReference((FirThisReference) getCalleeReference().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression
    public void replaceTypeRef(FirTypeRef newTypeRef) {
        Intrinsics.checkNotNullParameter(newTypeRef, "newTypeRef");
        setTypeRef(newTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        m7023setAnnotationsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newAnnotations));
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirContextReceiverArgumentListOwner
    public void replaceContextReceiverArguments(List<? extends FirExpression> newContextReceiverArguments) {
        Intrinsics.checkNotNullParameter(newContextReceiverArguments, "newContextReceiverArguments");
        m7024setContextReceiverArgumentsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newContextReceiverArguments));
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public void replaceTypeArguments(List<? extends FirTypeProjection> newTypeArguments) {
        Intrinsics.checkNotNullParameter(newTypeArguments, "newTypeArguments");
        m7025setTypeArgumentsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newTypeArguments));
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public void replaceExplicitReceiver(FirExpression newExplicitReceiver) {
        setExplicitReceiver(newExplicitReceiver);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public void replaceDispatchReceiver(FirExpression newDispatchReceiver) {
        Intrinsics.checkNotNullParameter(newDispatchReceiver, "newDispatchReceiver");
        setDispatchReceiver(newDispatchReceiver);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    public void replaceExtensionReceiver(FirExpression newExtensionReceiver) {
        Intrinsics.checkNotNullParameter(newExtensionReceiver, "newExtensionReceiver");
        setExtensionReceiver(newExtensionReceiver);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
    @FirImplementationDetail
    public void replaceSource(KtSourceElement newSource) {
        setSource(newSource);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression
    public void replaceCalleeReference(FirThisReference newCalleeReference) {
        Intrinsics.checkNotNullParameter(newCalleeReference, "newCalleeReference");
        setCalleeReference(newCalleeReference);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression, org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression, org.jetbrains.kotlin.fir.expressions.FirResolvable
    public void replaceCalleeReference(FirReference newCalleeReference) {
        Intrinsics.checkNotNullParameter(newCalleeReference, "newCalleeReference");
        if (!(newCalleeReference instanceof FirThisReference)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        replaceCalleeReference((FirThisReference) newCalleeReference);
    }
}
