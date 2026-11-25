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
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirThisReceiverExpressionImpl;
import org.jetbrains.kotlin.fir.references.FirThisReference;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitTypeRefImplWithoutSource;

/* JADX INFO: compiled from: FirThisReceiverExpressionBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\b\u00106\u001a\u000207H\u0016R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\tR*\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00118V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b\u0015\u0010\u0004\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R.\u0010\u001a\u001a\u0004\u0018\u00010\u00112\b\u0010\u0013\u001a\u0004\u0018\u00010\u00118V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b\u001b\u0010\u0004\u001a\u0004\b\u001c\u0010\u0017\"\u0004\b\u001d\u0010\u0019R*\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00118V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b\u001f\u0010\u0004\u001a\u0004\b \u0010\u0017\"\u0004\b!\u0010\u0019R\u001a\u0010\"\u001a\u00020#X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010$\"\u0004\b%\u0010&R\u001c\u0010'\u001a\u0004\u0018\u00010(X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u001a\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b/\u0010\tR\u001a\u00100\u001a\u000201X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u00103\"\u0004\b4\u00105¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/builder/FirThisReceiverExpressionBuilder;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirQualifiedAccessExpressionBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirExpressionBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirThisReference;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirThisReference;", "setCalleeReference", "(Lorg/jetbrains/kotlin/fir/references/FirThisReference;)V", "contextReceiverArguments", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getContextReceiverArguments", "<anonymous parameter 0>", "dispatchReceiver", "getDispatchReceiver$annotations", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setDispatchReceiver", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "explicitReceiver", "getExplicitReceiver$annotations", "getExplicitReceiver", "setExplicitReceiver", "extensionReceiver", "getExtensionReceiver$annotations", "getExtensionReceiver", "setExtensionReceiver", "isImplicit", "", "()Z", "setImplicit", "(Z)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "getTypeArguments", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirThisReceiverExpressionBuilder implements FirAnnotationContainerBuilder, FirExpressionBuilder, FirQualifiedAccessExpressionBuilder {
    public FirThisReference calleeReference;
    private boolean isImplicit;
    private KtSourceElement source;
    private FirTypeRef typeRef = FirImplicitTypeRefImplWithoutSource.INSTANCE;
    private final List<FirAnnotation> annotations = new ArrayList();
    private final List<FirExpression> contextReceiverArguments = new ArrayList();
    private final List<FirTypeProjection> typeArguments = new ArrayList();

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'dispatchReceiver' has no impact for FirThisReceiverExpressionBuilder")
    public static /* synthetic */ void getDispatchReceiver$annotations() {
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'explicitReceiver' has no impact for FirThisReceiverExpressionBuilder")
    public static /* synthetic */ void getExplicitReceiver$annotations() {
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'extensionReceiver' has no impact for FirThisReceiverExpressionBuilder")
    public static /* synthetic */ void getExtensionReceiver$annotations() {
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirExpressionBuilder
    public FirTypeRef getTypeRef() {
        return this.typeRef;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirExpressionBuilder
    public void setTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.typeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public List<FirAnnotation> getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    public List<FirExpression> getContextReceiverArguments() {
        return this.contextReceiverArguments;
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    public List<FirTypeProjection> getTypeArguments() {
        return this.typeArguments;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    public final FirThisReference getCalleeReference() {
        FirThisReference firThisReference = this.calleeReference;
        if (firThisReference != null) {
            return firThisReference;
        }
        Intrinsics.throwUninitializedPropertyAccessException("calleeReference");
        return null;
    }

    public final void setCalleeReference(FirThisReference firThisReference) {
        Intrinsics.checkNotNullParameter(firThisReference, "<set-?>");
        this.calleeReference = firThisReference;
    }

    /* JADX INFO: renamed from: isImplicit, reason: from getter */
    public final boolean getIsImplicit() {
        return this.isImplicit;
    }

    public final void setImplicit(boolean z) {
        this.isImplicit = z;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    /* JADX INFO: renamed from: build */
    public FirThisReceiverExpression mo6810build() {
        return new FirThisReceiverExpressionImpl(getTypeRef(), FirBuilderDslKt.toMutableOrEmpty(getAnnotations()), FirBuilderDslKt.toMutableOrEmpty(getContextReceiverArguments()), FirBuilderDslKt.toMutableOrEmpty(getTypeArguments()), getSource(), getCalleeReference(), this.isImplicit, null);
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    public /* synthetic */ FirExpression getExplicitReceiver() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    public /* synthetic */ void setExplicitReceiver(FirExpression firExpression) {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    public /* synthetic */ FirExpression getDispatchReceiver() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    public /* synthetic */ void setDispatchReceiver(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<anonymous parameter 0>");
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    public /* synthetic */ FirExpression getExtensionReceiver() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    public /* synthetic */ void setExtensionReceiver(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<anonymous parameter 0>");
        throw new IllegalStateException();
    }
}
