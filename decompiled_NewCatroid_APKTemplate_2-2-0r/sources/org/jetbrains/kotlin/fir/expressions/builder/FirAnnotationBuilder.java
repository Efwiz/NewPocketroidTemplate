package org.jetbrains.kotlin.fir.expressions.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationArgumentMapping;
import org.jetbrains.kotlin.fir.expressions.impl.FirAnnotationImpl;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: FirAnnotationBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010*\u001a\u00020\fH\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\"\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8\u0016X\u0097\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\r\u0010\u0003\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u000fR*\u0010 \u001a\u00020\u00052\u0006\u0010\u001f\u001a\u00020\u00058V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b!\u0010\u0003\u001a\u0004\b\"\u0010\u0007\"\u0004\b#\u0010\tR\u001c\u0010$\u001a\u0004\u0018\u00010%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/builder/FirAnnotationBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirExpressionBuilder;", "()V", "annotationTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getAnnotationTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setAnnotationTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations$annotations", "getAnnotations", "()Ljava/util/List;", "argumentMapping", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;", "getArgumentMapping", "()Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;", "setArgumentMapping", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "getTypeArguments", "<anonymous parameter 0>", "typeRef", "getTypeRef$annotations", "getTypeRef", "setTypeRef", "useSiteTarget", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "getUseSiteTarget", "()Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "setUseSiteTarget", "(Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirAnnotationBuilder implements FirAnnotationContainerBuilder, FirExpressionBuilder {
    public FirTypeRef annotationTypeRef;
    public FirAnnotationArgumentMapping argumentMapping;
    private KtSourceElement source;
    private AnnotationUseSiteTarget useSiteTarget;
    private final List<FirTypeProjection> typeArguments = new ArrayList();
    private final List<FirAnnotation> annotations = new ArrayList();

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'annotations' has no impact for FirAnnotationBuilder")
    public static /* synthetic */ void getAnnotations$annotations() {
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'typeRef' has no impact for FirAnnotationBuilder")
    public static /* synthetic */ void getTypeRef$annotations() {
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    public final AnnotationUseSiteTarget getUseSiteTarget() {
        return this.useSiteTarget;
    }

    public final void setUseSiteTarget(AnnotationUseSiteTarget annotationUseSiteTarget) {
        this.useSiteTarget = annotationUseSiteTarget;
    }

    public final FirTypeRef getAnnotationTypeRef() {
        FirTypeRef firTypeRef = this.annotationTypeRef;
        if (firTypeRef != null) {
            return firTypeRef;
        }
        Intrinsics.throwUninitializedPropertyAccessException("annotationTypeRef");
        return null;
    }

    public final void setAnnotationTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.annotationTypeRef = firTypeRef;
    }

    public final FirAnnotationArgumentMapping getArgumentMapping() {
        FirAnnotationArgumentMapping firAnnotationArgumentMapping = this.argumentMapping;
        if (firAnnotationArgumentMapping != null) {
            return firAnnotationArgumentMapping;
        }
        Intrinsics.throwUninitializedPropertyAccessException("argumentMapping");
        return null;
    }

    public final void setArgumentMapping(FirAnnotationArgumentMapping firAnnotationArgumentMapping) {
        Intrinsics.checkNotNullParameter(firAnnotationArgumentMapping, "<set-?>");
        this.argumentMapping = firAnnotationArgumentMapping;
    }

    public final List<FirTypeProjection> getTypeArguments() {
        return this.typeArguments;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public FirAnnotation build() {
        return new FirAnnotationImpl(getSource(), this.useSiteTarget, getAnnotationTypeRef(), getArgumentMapping(), FirBuilderDslKt.toMutableOrEmpty(this.typeArguments), null);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirExpressionBuilder
    public /* synthetic */ FirTypeRef getTypeRef() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirExpressionBuilder
    public /* synthetic */ void setTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<anonymous parameter 0>");
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public /* synthetic */ List getAnnotations() {
        return this.annotations;
    }
}
