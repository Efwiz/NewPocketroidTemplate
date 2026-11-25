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
import org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirEmptyArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCallOrigin;
import org.jetbrains.kotlin.fir.expressions.FirImplicitInvokeCall;
import org.jetbrains.kotlin.fir.expressions.impl.FirImplicitInvokeCallImpl;
import org.jetbrains.kotlin.fir.expressions.impl.FirNoReceiverExpression;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: FirImplicitInvokeCallBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\b\u0017\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010<\u001a\u00020=H\u0016R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\tR\u001a\u0010\u0019\u001a\u00020\u0017X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0017X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u001b\"\u0004\b \u0010\u001dR\u001a\u0010!\u001a\u00020\u0017X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u001b\"\u0004\b#\u0010\u001dR*\u0010&\u001a\u00020%2\u0006\u0010$\u001a\u00020%8V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b'\u0010\u0004\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u001c\u0010,\u001a\u0004\u0018\u00010-X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010/\"\u0004\b0\u00101R\u001a\u00102\u001a\b\u0012\u0004\u0012\u0002030\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b4\u0010\tR*\u00106\u001a\u0002052\u0006\u0010$\u001a\u0002058V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b7\u0010\u0004\u001a\u0004\b8\u00109\"\u0004\b:\u0010;¨\u0006>"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/builder/FirImplicitInvokeCallBuilder;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirAbstractFunctionCallBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirExpressionBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "argumentList", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "getArgumentList", "()Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "setArgumentList", "(Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;)V", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "setCalleeReference", "(Lorg/jetbrains/kotlin/fir/references/FirNamedReference;)V", "contextReceiverArguments", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getContextReceiverArguments", "dispatchReceiver", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setDispatchReceiver", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "explicitReceiver", "getExplicitReceiver", "setExplicitReceiver", "extensionReceiver", "getExtensionReceiver", "setExtensionReceiver", "<anonymous parameter 0>", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCallOrigin;", "origin", "getOrigin$annotations", "getOrigin", "()Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCallOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCallOrigin;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "getTypeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "typeRef", "getTypeRef$annotations", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/expressions/FirImplicitInvokeCall;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirImplicitInvokeCallBuilder implements FirAnnotationContainerBuilder, FirAbstractFunctionCallBuilder, FirExpressionBuilder {
    public FirNamedReference calleeReference;
    private FirExpression explicitReceiver;
    private KtSourceElement source;
    private final List<FirAnnotation> annotations = new ArrayList();
    private final List<FirExpression> contextReceiverArguments = new ArrayList();
    private final List<FirTypeProjection> typeArguments = new ArrayList();
    private FirExpression dispatchReceiver = FirNoReceiverExpression.INSTANCE;
    private FirExpression extensionReceiver = FirNoReceiverExpression.INSTANCE;
    private FirArgumentList argumentList = FirEmptyArgumentList.INSTANCE;

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'origin' has no impact for FirImplicitInvokeCallBuilder")
    public static /* synthetic */ void getOrigin$annotations() {
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'typeRef' has no impact for FirImplicitInvokeCallBuilder")
    public static /* synthetic */ void getTypeRef$annotations() {
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

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public FirImplicitInvokeCall build() {
        return new FirImplicitInvokeCallImpl(FirBuilderDslKt.toMutableOrEmpty(getAnnotations()), FirBuilderDslKt.toMutableOrEmpty(getContextReceiverArguments()), FirBuilderDslKt.toMutableOrEmpty(getTypeArguments()), getExplicitReceiver(), getDispatchReceiver(), getExtensionReceiver(), getSource(), getArgumentList(), getCalleeReference(), null);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractFunctionCallBuilder, org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    public /* synthetic */ FirTypeRef getTypeRef() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractFunctionCallBuilder, org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    public /* synthetic */ void setTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<anonymous parameter 0>");
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractFunctionCallBuilder
    public /* synthetic */ FirFunctionCallOrigin getOrigin() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirAbstractFunctionCallBuilder
    public /* synthetic */ void setOrigin(FirFunctionCallOrigin firFunctionCallOrigin) {
        Intrinsics.checkNotNullParameter(firFunctionCallOrigin, "<anonymous parameter 0>");
        throw new IllegalStateException();
    }
}
