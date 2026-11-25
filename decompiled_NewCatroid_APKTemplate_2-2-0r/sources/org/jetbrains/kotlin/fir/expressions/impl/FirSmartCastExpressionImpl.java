package org.jetbrains.kotlin.fir.expressions.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.types.SmartcastStability;

/* JADX INFO: compiled from: FirSmartCastExpressionImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001BK\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u0006\u0010\f\u001a\u00020\u0006\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010J5\u0010-\u001a\u00020.\"\u0004\b\u0000\u0010/\"\u0004\b\u0001\u001002\u0012\u00101\u001a\u000e\u0012\u0004\u0012\u0002H/\u0012\u0004\u0012\u0002H0022\u0006\u00103\u001a\u0002H0H\u0016¢\u0006\u0002\u00104J\u0016\u00105\u001a\u00020.2\f\u00106\u001a\b\u0012\u0004\u0012\u00020\u000407H\u0016J\u0010\u00108\u001a\u00020.2\u0006\u00109\u001a\u00020\bH\u0016J\u0010\u0010:\u001a\u00020.2\u0006\u0010;\u001a\u00020\u0006H\u0016J)\u0010<\u001a\u00020\u0000\"\u0004\b\u0000\u001002\f\u0010=\u001a\b\u0012\u0004\u0012\u0002H00>2\u0006\u00103\u001a\u0002H0H\u0016¢\u0006\u0002\u0010?J)\u0010@\u001a\u00020\u0000\"\u0004\b\u0000\u001002\f\u0010=\u001a\b\u0012\u0004\u0012\u0002H00>2\u0006\u00103\u001a\u0002H0H\u0016¢\u0006\u0002\u0010?J)\u0010A\u001a\u00020\u0000\"\u0004\b\u0000\u001002\f\u0010=\u001a\b\u0012\u0004\u0012\u0002H00>2\u0006\u00103\u001a\u0002H0H\u0016¢\u0006\u0002\u0010?R(\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0096\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u0015\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0016\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0018R\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0014\u0010\u000e\u001a\u00020\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u001a\u0010\f\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001c\u0010\r\u001a\u0004\u0018\u00010\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010 \"\u0004\b$\u0010\"R\u0016\u0010%\u001a\u0004\u0018\u00010&X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010 \"\u0004\b*\u0010\"R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006B"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/impl/FirSmartCastExpressionImpl;", "Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", "annotations", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "originalExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "typesFromSmartCast", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "smartcastType", "smartcastTypeWithoutNullableNothing", "smartcastStability", "Lorg/jetbrains/kotlin/types/SmartcastStability;", "(Ljava/util/List;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Ljava/util/Collection;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Lorg/jetbrains/kotlin/types/SmartcastStability;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAnnotations-5e3fPpI", "()Ljava/util/List;", "setAnnotations-GqUYU-s", "(Ljava/util/List;)V", "Ljava/util/List;", "isStable", "", "()Z", "getOriginalExpression", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setOriginalExpression", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "getSmartcastStability", "()Lorg/jetbrains/kotlin/types/SmartcastStability;", "getSmartcastType", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setSmartcastType", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "getSmartcastTypeWithoutNullableNothing", "setSmartcastTypeWithoutNullableNothing", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getTypeRef", "setTypeRef", "getTypesFromSmartCast", "()Ljava/util/Collection;", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "", "replaceOriginalExpression", "newOriginalExpression", "replaceTypeRef", "newTypeRef", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/impl/FirSmartCastExpressionImpl;", "transformChildren", "transformOriginalExpression", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSmartCastExpressionImpl extends FirSmartCastExpression {
    private List<T> annotations;
    private FirExpression originalExpression;
    private final SmartcastStability smartcastStability;
    private FirTypeRef smartcastType;
    private FirTypeRef smartcastTypeWithoutNullableNothing;
    private final KtSourceElement source;
    private FirTypeRef typeRef;
    private final Collection<ConeKotlinType> typesFromSmartCast;

    public /* synthetic */ FirSmartCastExpressionImpl(List list, FirTypeRef firTypeRef, FirExpression firExpression, Collection collection, FirTypeRef firTypeRef2, FirTypeRef firTypeRef3, SmartcastStability smartcastStability, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, firTypeRef, firExpression, collection, firTypeRef2, firTypeRef3, smartcastStability);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ List getAnnotations() {
        return MutableOrEmptyList.m6756boximpl(m7014getAnnotations5e3fPpI());
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirExpression transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirSmartCastExpression transformAnnotations(FirTransformer firTransformer, Object obj) {
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

    @Override // org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression
    public /* bridge */ /* synthetic */ FirSmartCastExpression transformOriginalExpression(FirTransformer firTransformer, Object obj) {
        return transformOriginalExpression((FirTransformer<? super Object>) firTransformer, obj);
    }

    /* JADX INFO: renamed from: getAnnotations-5e3fPpI, reason: not valid java name */
    public List<T> m7014getAnnotations5e3fPpI() {
        return this.annotations;
    }

    /* JADX INFO: renamed from: setAnnotations-GqUYU-s, reason: not valid java name */
    public void m7015setAnnotationsGqUYUs(List<T> list) {
        this.annotations = list;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression, org.jetbrains.kotlin.fir.expressions.FirExpression
    public FirTypeRef getTypeRef() {
        return this.typeRef;
    }

    public void setTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.typeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression
    public FirExpression getOriginalExpression() {
        return this.originalExpression;
    }

    public void setOriginalExpression(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.originalExpression = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression
    public Collection<ConeKotlinType> getTypesFromSmartCast() {
        return this.typesFromSmartCast;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression
    public FirTypeRef getSmartcastType() {
        return this.smartcastType;
    }

    public void setSmartcastType(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.smartcastType = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression
    public FirTypeRef getSmartcastTypeWithoutNullableNothing() {
        return this.smartcastTypeWithoutNullableNothing;
    }

    public void setSmartcastTypeWithoutNullableNothing(FirTypeRef firTypeRef) {
        this.smartcastTypeWithoutNullableNothing = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression
    public SmartcastStability getSmartcastStability() {
        return this.smartcastStability;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private FirSmartCastExpressionImpl(List<T> list, FirTypeRef typeRef, FirExpression originalExpression, Collection<? extends ConeKotlinType> typesFromSmartCast, FirTypeRef smartcastType, FirTypeRef firTypeRef, SmartcastStability smartcastStability) {
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        Intrinsics.checkNotNullParameter(originalExpression, "originalExpression");
        Intrinsics.checkNotNullParameter(typesFromSmartCast, "typesFromSmartCast");
        Intrinsics.checkNotNullParameter(smartcastType, "smartcastType");
        Intrinsics.checkNotNullParameter(smartcastStability, "smartcastStability");
        this.annotations = list;
        this.typeRef = typeRef;
        this.originalExpression = originalExpression;
        this.typesFromSmartCast = typesFromSmartCast;
        this.smartcastType = smartcastType;
        this.smartcastTypeWithoutNullableNothing = firTypeRef;
        this.smartcastStability = smartcastStability;
        KtSourceElement source = getOriginalExpression().getSource();
        this.source = source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.SmartCastExpression.INSTANCE, 0, 0, 6, null) : null;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression
    public boolean isStable() {
        return getSmartcastStability() == SmartcastStability.STABLE_VALUE;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<T> it = MutableOrEmptyList.m6756boximpl(m7014getAnnotations5e3fPpI()).iterator();
        while (it.hasNext()) {
            ((FirAnnotation) it.next()).accept(visitor, data);
        }
        getTypeRef().accept(visitor, data);
        getOriginalExpression().accept(visitor, data);
        getSmartcastType().accept(visitor, data);
        FirTypeRef smartcastTypeWithoutNullableNothing = getSmartcastTypeWithoutNullableNothing();
        if (smartcastTypeWithoutNullableNothing != null) {
            smartcastTypeWithoutNullableNothing.accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirSmartCastExpressionImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformAnnotations((FirTransformer) transformer, (Object) data);
        setTypeRef((FirTypeRef) getTypeRef().transform(transformer, data));
        transformOriginalExpression((FirTransformer) transformer, (Object) data);
        setSmartcastType((FirTypeRef) getSmartcastType().transform(transformer, data));
        FirTypeRef smartcastTypeWithoutNullableNothing = getSmartcastTypeWithoutNullableNothing();
        setSmartcastTypeWithoutNullableNothing(smartcastTypeWithoutNullableNothing != null ? (FirTypeRef) smartcastTypeWithoutNullableNothing.transform(transformer, data) : null);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirSmartCastExpressionImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.m7082transformInplaceaLnlfrU(m7014getAnnotations5e3fPpI(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression
    public <D> FirSmartCastExpressionImpl transformOriginalExpression(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setOriginalExpression((FirExpression) getOriginalExpression().transform(transformer, data));
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression, org.jetbrains.kotlin.fir.expressions.FirExpression, org.jetbrains.kotlin.fir.expressions.FirStatement, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        m7015setAnnotationsGqUYUs(FirBuilderDslKt.toMutableOrEmptyForImmutable(newAnnotations));
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression, org.jetbrains.kotlin.fir.expressions.FirExpression
    public void replaceTypeRef(FirTypeRef newTypeRef) {
        Intrinsics.checkNotNullParameter(newTypeRef, "newTypeRef");
        setTypeRef(newTypeRef);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression
    public void replaceOriginalExpression(FirExpression newOriginalExpression) {
        Intrinsics.checkNotNullParameter(newOriginalExpression, "newOriginalExpression");
        setOriginalExpression(newOriginalExpression);
    }
}
