package org.jetbrains.kotlin.fir.expressions.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirExpressionStub;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitTypeRefImplWithoutSource;

/* JADX INFO: compiled from: FirExpressionStubBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0016R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/builder/FirExpressionStubBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirExpressionStubBuilder implements FirAnnotationContainerBuilder {
    private KtSourceElement source;
    private FirTypeRef typeRef = FirImplicitTypeRefImplWithoutSource.INSTANCE;
    private final List<FirAnnotation> annotations = new ArrayList();

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    public final FirTypeRef getTypeRef() {
        return this.typeRef;
    }

    public final void setTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.typeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public List<FirAnnotation> getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public FirExpression build() {
        return new FirExpressionStub(getSource(), this.typeRef, FirBuilderDslKt.toMutableOrEmpty(getAnnotations()), null);
    }
}
