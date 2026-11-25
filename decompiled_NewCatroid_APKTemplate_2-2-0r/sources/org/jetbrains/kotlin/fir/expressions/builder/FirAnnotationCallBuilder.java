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
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationResolvePhase;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirEmptyArgumentList;
import org.jetbrains.kotlin.fir.expressions.impl.FirAnnotationCallImpl;
import org.jetbrains.kotlin.fir.expressions.impl.FirEmptyAnnotationArgumentMapping;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitTypeRefImplWithoutSource;

/* JADX INFO: compiled from: FirAnnotationCallBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010=\u001a\u00020>H\u0016R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\"\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00128\u0016X\u0097\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0014\u0010\u0004\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0018X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\u00020\u001eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010#\u001a\u00020$X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u001c\u0010)\u001a\u0004\u0018\u00010*X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u0017\u0010/\u001a\b\u0012\u0004\u0012\u0002000\u0012¢\u0006\b\n\u0000\u001a\u0004\b1\u0010\u0016R*\u00103\u001a\u00020\f2\u0006\u00102\u001a\u00020\f8V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b4\u0010\u0004\u001a\u0004\b5\u0010\u000e\"\u0004\b6\u0010\u0010R\u001c\u00107\u001a\u0004\u0018\u000108X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<¨\u0006?"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/builder/FirAnnotationCallBuilder;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirCallBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirExpressionBuilder;", "()V", "annotationResolvePhase", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationResolvePhase;", "getAnnotationResolvePhase", "()Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationResolvePhase;", "setAnnotationResolvePhase", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationResolvePhase;)V", "annotationTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getAnnotationTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setAnnotationTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations$annotations", "getAnnotations", "()Ljava/util/List;", "argumentList", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "getArgumentList", "()Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "setArgumentList", "(Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;)V", "argumentMapping", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;", "getArgumentMapping", "()Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;", "setArgumentMapping", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationArgumentMapping;)V", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirReference;", "setCalleeReference", "(Lorg/jetbrains/kotlin/fir/references/FirReference;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "getTypeArguments", "<anonymous parameter 0>", "typeRef", "getTypeRef$annotations", "getTypeRef", "setTypeRef", "useSiteTarget", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "getUseSiteTarget", "()Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "setUseSiteTarget", "(Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirAnnotationCallBuilder implements FirAnnotationContainerBuilder, FirCallBuilder, FirExpressionBuilder {
    public FirReference calleeReference;
    private KtSourceElement source;
    private AnnotationUseSiteTarget useSiteTarget;
    private FirTypeRef annotationTypeRef = FirImplicitTypeRefImplWithoutSource.INSTANCE;
    private final List<FirTypeProjection> typeArguments = new ArrayList();
    private FirArgumentList argumentList = FirEmptyArgumentList.INSTANCE;
    private FirAnnotationArgumentMapping argumentMapping = FirEmptyAnnotationArgumentMapping.INSTANCE;
    private FirAnnotationResolvePhase annotationResolvePhase = FirAnnotationResolvePhase.Unresolved;
    private final List<FirAnnotation> annotations = new ArrayList();

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'annotations' has no impact for FirAnnotationCallBuilder")
    public static /* synthetic */ void getAnnotations$annotations() {
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'typeRef' has no impact for FirAnnotationCallBuilder")
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
        return this.annotationTypeRef;
    }

    public final void setAnnotationTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.annotationTypeRef = firTypeRef;
    }

    public final List<FirTypeProjection> getTypeArguments() {
        return this.typeArguments;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirCallBuilder
    public FirArgumentList getArgumentList() {
        return this.argumentList;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirCallBuilder
    public void setArgumentList(FirArgumentList firArgumentList) {
        Intrinsics.checkNotNullParameter(firArgumentList, "<set-?>");
        this.argumentList = firArgumentList;
    }

    public final FirReference getCalleeReference() {
        FirReference firReference = this.calleeReference;
        if (firReference != null) {
            return firReference;
        }
        Intrinsics.throwUninitializedPropertyAccessException("calleeReference");
        return null;
    }

    public final void setCalleeReference(FirReference firReference) {
        Intrinsics.checkNotNullParameter(firReference, "<set-?>");
        this.calleeReference = firReference;
    }

    public final FirAnnotationArgumentMapping getArgumentMapping() {
        return this.argumentMapping;
    }

    public final void setArgumentMapping(FirAnnotationArgumentMapping firAnnotationArgumentMapping) {
        Intrinsics.checkNotNullParameter(firAnnotationArgumentMapping, "<set-?>");
        this.argumentMapping = firAnnotationArgumentMapping;
    }

    public final FirAnnotationResolvePhase getAnnotationResolvePhase() {
        return this.annotationResolvePhase;
    }

    public final void setAnnotationResolvePhase(FirAnnotationResolvePhase firAnnotationResolvePhase) {
        Intrinsics.checkNotNullParameter(firAnnotationResolvePhase, "<set-?>");
        this.annotationResolvePhase = firAnnotationResolvePhase;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    /* JADX INFO: renamed from: build */
    public FirAnnotationCall mo6810build() {
        return new FirAnnotationCallImpl(getSource(), this.useSiteTarget, this.annotationTypeRef, FirBuilderDslKt.toMutableOrEmpty(this.typeArguments), getArgumentList(), getCalleeReference(), this.argumentMapping, this.annotationResolvePhase, null);
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
