package org.jetbrains.kotlin.fir.expressions.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCallOrigin;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: FirAbstractFunctionCallBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u00012\u00020\u0002J\b\u00107\u001a\u000208H&R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\tX¦\u000e¢\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\u00020\u000fX¦\u000e¢\u0006\f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0018\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0007R\u0018\u0010\u0017\u001a\u00020\u0015X¦\u000e¢\u0006\f\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u0004\u0018\u00010\u0015X¦\u000e¢\u0006\f\u001a\u0004\b\u001d\u0010\u0019\"\u0004\b\u001e\u0010\u001bR\u0018\u0010\u001f\u001a\u00020\u0015X¦\u000e¢\u0006\f\u001a\u0004\b \u0010\u0019\"\u0004\b!\u0010\u001bR\u0018\u0010\"\u001a\u00020#X¦\u000e¢\u0006\f\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u001a\u0010(\u001a\u0004\u0018\u00010)X¦\u000e¢\u0006\f\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u0018\u0010.\u001a\b\u0012\u0004\u0012\u00020/0\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b0\u0010\u0007R\u0018\u00101\u001a\u000202X¦\u000e¢\u0006\f\u001a\u0004\b3\u00104\"\u0004\b5\u00106ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u00069À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/builder/FirAbstractFunctionCallBuilder;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirQualifiedAccessExpressionBuilder;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirCallBuilder;", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "argumentList", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "getArgumentList", "()Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "setArgumentList", "(Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;)V", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "getCalleeReference", "()Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "setCalleeReference", "(Lorg/jetbrains/kotlin/fir/references/FirNamedReference;)V", "contextReceiverArguments", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getContextReceiverArguments", "dispatchReceiver", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setDispatchReceiver", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "explicitReceiver", "getExplicitReceiver", "setExplicitReceiver", "extensionReceiver", "getExtensionReceiver", "setExtensionReceiver", "origin", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCallOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCallOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCallOrigin;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "getTypeArguments", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface FirAbstractFunctionCallBuilder extends FirCallBuilder, FirQualifiedAccessExpressionBuilder {
    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirCallBuilder, org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    FirFunctionCall build();

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirCallBuilder, org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    List<FirAnnotation> getAnnotations();

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirCallBuilder
    FirArgumentList getArgumentList();

    FirNamedReference getCalleeReference();

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    List<FirExpression> getContextReceiverArguments();

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    FirExpression getDispatchReceiver();

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    FirExpression getExplicitReceiver();

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    FirExpression getExtensionReceiver();

    FirFunctionCallOrigin getOrigin();

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirCallBuilder, org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    List<FirTypeProjection> getTypeArguments();

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    FirTypeRef getTypeRef();

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirCallBuilder
    void setArgumentList(FirArgumentList firArgumentList);

    void setCalleeReference(FirNamedReference firNamedReference);

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    void setDispatchReceiver(FirExpression firExpression);

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    void setExplicitReceiver(FirExpression firExpression);

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    void setExtensionReceiver(FirExpression firExpression);

    void setOrigin(FirFunctionCallOrigin firFunctionCallOrigin);

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirCallBuilder, org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    void setSource(KtSourceElement ktSourceElement);

    @Override // org.jetbrains.kotlin.fir.expressions.builder.FirQualifiedAccessExpressionBuilder
    void setTypeRef(FirTypeRef firTypeRef);
}
