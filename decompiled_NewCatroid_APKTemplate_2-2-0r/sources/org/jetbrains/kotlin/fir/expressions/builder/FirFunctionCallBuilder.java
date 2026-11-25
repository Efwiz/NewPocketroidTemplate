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
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirEmptyArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCallOrigin;
import org.jetbrains.kotlin.fir.expressions.impl.FirFunctionCallImpl;
import org.jetbrains.kotlin.fir.expressions.impl.FirNoReceiverExpression;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitTypeRefImplWithoutSource;

/* JADX INFO: compiled from: FirFunctionCallBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0017\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\b\u00109\u001a\u00020:H\u0016R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\tR\u001a\u0010\u0019\u001a\u00020\u0017X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0017X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u001b\"\u0004\b \u0010\u001dR\u001a\u0010!\u001a\u00020\u0017X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u001b\"\u0004\b#\u0010\u001dR\u001a\u0010$\u001a\u00020%X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u001c\u0010*\u001a\u0004\u0018\u00010+X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\u001a\u00100\u001a\b\u0012\u0004\u0012\u0002010\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b2\u0010\tR\u001a\u00103\u001a\u000204X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u00106\"\u0004\b7\u00108¨\u0006;"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/builder/FirFunctionCallBuilder;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirAbstractFunctionCallBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirExpressionBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "argumentList", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "getArgumentList", "()Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "setArgumentList", "(Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;)V", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "setCalleeReference", "(Lorg/jetbrains/kotlin/fir/references/FirNamedReference;)V", "contextReceiverArguments", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getContextReceiverArguments", "dispatchReceiver", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setDispatchReceiver", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "explicitReceiver", "getExplicitReceiver", "setExplicitReceiver", "extensionReceiver", "getExtensionReceiver", "setExtensionReceiver", "origin", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCallOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCallOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCallOrigin;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "getTypeArguments", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirFunctionCallBuilder implements FirAnnotationContainerBuilder, FirAbstractFunctionCallBuilder, FirExpressionBuilder {
    public FirNamedReference calleeReference;
    private FirExpression explicitReceiver;
    private KtSourceElement source;
    private FirTypeRef typeRef = FirImplicitTypeRefImplWithoutSource.INSTANCE;
    private final List<FirAnnotation> annotations = new ArrayList();
    private final List<FirExpression> contextReceiverArguments = new ArrayList();
    private final List<FirTypeProjection> typeArguments = new ArrayList();
    private FirExpression dispatchReceiver = FirNoReceiverExpression.INSTANCE;
    private FirExpression extensionReceiver = FirNoReceiverExpression.INSTANCE;
    private FirArgumentList argumentList = FirEmptyArgumentList.INSTANCE;
    private FirFunctionCallOrigin origin = FirFunctionCallOrigin.Regular;

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractFunctionCallBuilder, org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    public FirTypeRef getTypeRef() {
        return this.typeRef;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractFunctionCallBuilder, org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    public void setTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.typeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public List<FirAnnotation> getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractFunctionCallBuilder, org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    public List<FirExpression> getContextReceiverArguments() {
        return this.contextReceiverArguments;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractFunctionCallBuilder, org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    public List<FirTypeProjection> getTypeArguments() {
        return this.typeArguments;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractFunctionCallBuilder, org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    public FirExpression getExplicitReceiver() {
        return this.explicitReceiver;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractFunctionCallBuilder, org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    public void setExplicitReceiver(FirExpression firExpression) {
        this.explicitReceiver = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractFunctionCallBuilder, org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    public FirExpression getDispatchReceiver() {
        return this.dispatchReceiver;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractFunctionCallBuilder, org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    public void setDispatchReceiver(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.dispatchReceiver = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractFunctionCallBuilder, org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    public FirExpression getExtensionReceiver() {
        return this.extensionReceiver;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractFunctionCallBuilder, org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    public void setExtensionReceiver(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<set-?>");
        this.extensionReceiver = firExpression;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractFunctionCallBuilder, org.jetbrains.kotlin.fir.expressions.builder.FirCallBuilder
    public FirArgumentList getArgumentList() {
        return this.argumentList;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractFunctionCallBuilder, org.jetbrains.kotlin.fir.expressions.builder.FirCallBuilder
    public void setArgumentList(FirArgumentList firArgumentList) {
        Intrinsics.checkNotNullParameter(firArgumentList, "<set-?>");
        this.argumentList = firArgumentList;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractFunctionCallBuilder
    public FirNamedReference getCalleeReference() {
        FirNamedReference firNamedReference = this.calleeReference;
        if (firNamedReference != null) {
            return firNamedReference;
        }
        Intrinsics.throwUninitializedPropertyAccessException("calleeReference");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractFunctionCallBuilder
    public void setCalleeReference(FirNamedReference firNamedReference) {
        Intrinsics.checkNotNullParameter(firNamedReference, "<set-?>");
        this.calleeReference = firNamedReference;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractFunctionCallBuilder
    public FirFunctionCallOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractFunctionCallBuilder
    public void setOrigin(FirFunctionCallOrigin firFunctionCallOrigin) {
        Intrinsics.checkNotNullParameter(firFunctionCallOrigin, "<set-?>");
        this.origin = firFunctionCallOrigin;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public FirFunctionCall build() {
        return new FirFunctionCallImpl(getTypeRef(), FirBuilderDslKt.toMutableOrEmpty(getAnnotations()), FirBuilderDslKt.toMutableOrEmpty(getContextReceiverArguments()), FirBuilderDslKt.toMutableOrEmpty(getTypeArguments()), getExplicitReceiver(), getDispatchReceiver(), getExtensionReceiver(), getSource(), getArgumentList(), getCalleeReference(), getOrigin(), null);
    }
}
