package org.jetbrains.kotlin.fir.expressions.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirSmartCastExpressionImpl;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.types.SmartcastStability;

/* JADX INFO: compiled from: FirSmartCastExpressionBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u00100\u001a\u000201H\u0016R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0018\"\u0004\b\u001d\u0010\u001aR.\u0010 \u001a\u0004\u0018\u00010\u001f2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f8V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b!\u0010\u0003\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001a\u0010&\u001a\u00020\u0016X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\u0018\"\u0004\b(\u0010\u001aR \u0010)\u001a\b\u0012\u0004\u0012\u00020+0*X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/builder/FirSmartCastExpressionBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirExpressionBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "originalExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getOriginalExpression", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setOriginalExpression", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "smartcastStability", "Lorg/jetbrains/kotlin/types/SmartcastStability;", "getSmartcastStability", "()Lorg/jetbrains/kotlin/types/SmartcastStability;", "setSmartcastStability", "(Lorg/jetbrains/kotlin/types/SmartcastStability;)V", "smartcastType", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getSmartcastType", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setSmartcastType", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "smartcastTypeWithoutNullableNothing", "getSmartcastTypeWithoutNullableNothing", "setSmartcastTypeWithoutNullableNothing", "<anonymous parameter 0>", "Lorg/jetbrains/kotlin/KtSourceElement;", FirebaseAnalytics.Param.SOURCE, "getSource$annotations", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "typeRef", "getTypeRef", "setTypeRef", "typesFromSmartCast", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getTypesFromSmartCast", "()Ljava/util/Collection;", "setTypesFromSmartCast", "(Ljava/util/Collection;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSmartCastExpressionBuilder implements FirAnnotationContainerBuilder, FirExpressionBuilder {
    private final List<FirAnnotation> annotations = new ArrayList();
    public FirExpression originalExpression;
    public SmartcastStability smartcastStability;
    public FirTypeRef smartcastType;
    private FirTypeRef smartcastTypeWithoutNullableNothing;
    public FirTypeRef typeRef;
    public Collection<? extends ConeKotlinType> typesFromSmartCast;

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'source' has no impact for FirSmartCastExpressionBuilder")
    public static /* synthetic */ void getSource$annotations() {
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public List<FirAnnotation> getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirExpressionBuilder
    public FirTypeRef getTypeRef() {
        FirTypeRef firTypeRef = this.typeRef;
        if (firTypeRef != null) {
            return firTypeRef;
        }
        Intrinsics.throwUninitializedPropertyAccessException("typeRef");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirExpressionBuilder
    public void setTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.typeRef = firTypeRef;
    }

    public final FirExpression getOriginalExpression() {
        FirExpression firExpression = this.originalExpression;
        if (firExpression != null) {
            return firExpression;
        }
        Intrinsics.throwUninitializedPropertyAccessException("originalExpression");
        return null;
    }

    public final void setOriginalExpression(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.originalExpression = firExpression;
    }

    public final Collection<ConeKotlinType> getTypesFromSmartCast() {
        Collection collection = this.typesFromSmartCast;
        if (collection != null) {
            return collection;
        }
        Intrinsics.throwUninitializedPropertyAccessException("typesFromSmartCast");
        return null;
    }

    public final void setTypesFromSmartCast(Collection<? extends ConeKotlinType> collection) {
        Intrinsics.checkNotNullParameter(collection, "<set-?>");
        this.typesFromSmartCast = collection;
    }

    public final FirTypeRef getSmartcastType() {
        FirTypeRef firTypeRef = this.smartcastType;
        if (firTypeRef != null) {
            return firTypeRef;
        }
        Intrinsics.throwUninitializedPropertyAccessException("smartcastType");
        return null;
    }

    public final void setSmartcastType(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.smartcastType = firTypeRef;
    }

    public final FirTypeRef getSmartcastTypeWithoutNullableNothing() {
        return this.smartcastTypeWithoutNullableNothing;
    }

    public final void setSmartcastTypeWithoutNullableNothing(FirTypeRef firTypeRef) {
        this.smartcastTypeWithoutNullableNothing = firTypeRef;
    }

    public final SmartcastStability getSmartcastStability() {
        SmartcastStability smartcastStability = this.smartcastStability;
        if (smartcastStability != null) {
            return smartcastStability;
        }
        Intrinsics.throwUninitializedPropertyAccessException("smartcastStability");
        return null;
    }

    public final void setSmartcastStability(SmartcastStability smartcastStability) {
        Intrinsics.checkNotNullParameter(smartcastStability, "<set-?>");
        this.smartcastStability = smartcastStability;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public FirSmartCastExpression build() {
        return new FirSmartCastExpressionImpl(FirBuilderDslKt.toMutableOrEmpty(getAnnotations()), getTypeRef(), getOriginalExpression(), getTypesFromSmartCast(), getSmartcastType(), this.smartcastTypeWithoutNullableNothing, getSmartcastStability(), null);
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public /* synthetic */ KtSourceElement getSource() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public /* synthetic */ void setSource(KtSourceElement ktSourceElement) {
        throw new IllegalStateException();
    }
}
