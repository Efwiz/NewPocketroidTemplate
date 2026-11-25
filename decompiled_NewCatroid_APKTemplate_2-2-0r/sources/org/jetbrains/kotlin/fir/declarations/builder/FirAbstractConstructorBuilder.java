package org.jetbrains.kotlin.fir.declarations.builder;

import androidx.core.app.NotificationCompat;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.contracts.FirContractDescription;
import org.jetbrains.kotlin.fir.declarations.DeprecationsProvider;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationAttributes;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirAbstractConstructorBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000´\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\b\u0010p\u001a\u00020qH&R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\u00020\bX¦\u000e¢\u0006\f\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u0004\u0018\u00010\u000eX¦\u000e¢\u0006\f\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u0004\u0018\u00010\u0014X¦\u000e¢\u0006\f\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0018\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0006R\u0018\u0010\u001c\u001a\u00020\u001dX¦\u000e¢\u0006\f\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001a\u0010\"\u001a\u0004\u0018\u00010#X¦\u000e¢\u0006\f\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u001a\u0010(\u001a\u0004\u0018\u00010)X¦\u000e¢\u0006\f\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u0018\u0010.\u001a\u00020/X¦\u000e¢\u0006\f\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u001a\u00104\u001a\u0004\u0018\u000105X¦\u000e¢\u0006\f\u001a\u0004\b6\u00107\"\u0004\b8\u00109R\u0018\u0010:\u001a\u00020;X¦\u000e¢\u0006\f\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R\u0018\u0010@\u001a\u00020AX¦\u000e¢\u0006\f\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\u001a\u0010F\u001a\u0004\u0018\u00010GX¦\u000e¢\u0006\f\u001a\u0004\bH\u0010I\"\u0004\bJ\u0010KR\u0018\u0010L\u001a\u00020MX¦\u000e¢\u0006\f\u001a\u0004\bN\u0010O\"\u0004\bP\u0010QR\u0018\u0010R\u001a\u00020SX¦\u000e¢\u0006\f\u001a\u0004\bT\u0010U\"\u0004\bV\u0010WR\u001a\u0010X\u001a\u0004\u0018\u00010YX¦\u000e¢\u0006\f\u001a\u0004\bZ\u0010[\"\u0004\b\\\u0010]R\u0018\u0010^\u001a\u00020_X¦\u000e¢\u0006\f\u001a\u0004\b`\u0010a\"\u0004\bb\u0010cR\u0018\u0010d\u001a\u00020eX¦\u000e¢\u0006\f\u001a\u0004\bf\u0010g\"\u0004\bh\u0010iR\u0018\u0010j\u001a\b\u0012\u0004\u0012\u00020k0\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\bl\u0010\u0006R\u0018\u0010m\u001a\b\u0012\u0004\u0012\u00020n0\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\bo\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/builder/FirAbstractConstructorBuilder;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirFunctionBuilder;", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "setAttributes", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;)V", "body", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "getBody", "()Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "setBody", "(Lorg/jetbrains/kotlin/fir/expressions/FirBlock;)V", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "setContainerSource", "(Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;)V", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getContextReceivers", "contractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "getContractDescription", "()Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "setContractDescription", "(Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;)V", "controlFlowGraphReference", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "getControlFlowGraphReference", "()Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "setControlFlowGraphReference", "(Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;)V", "delegatedConstructor", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "getDelegatedConstructor", "()Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "setDelegatedConstructor", "(Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;)V", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "setDeprecationsProvider", "(Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;)V", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "setDispatchReceiverType", "(Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;)V", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "setModuleData", "(Lorg/jetbrains/kotlin/fir/FirModuleData;)V", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;)V", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "getReceiverParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "setReceiverParameter", "(Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;)V", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "getResolvePhase", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "setResolvePhase", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setReturnTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "setStatus", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;)V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;)V", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "getTypeParameters", "valueParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "getValueParameters", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface FirAbstractConstructorBuilder extends FirFunctionBuilder {
    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    /* JADX INFO: renamed from: build */
    FirConstructor mo6809build();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    List<FirAnnotation> getAnnotations();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    FirDeclarationAttributes getAttributes();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    FirBlock getBody();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    DeserializedContainerSource getContainerSource();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    List<FirContextReceiver> getContextReceivers();

    FirContractDescription getContractDescription();

    FirControlFlowGraphReference getControlFlowGraphReference();

    FirDelegatedConstructorCall getDelegatedConstructor();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    DeprecationsProvider getDeprecationsProvider();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    ConeSimpleKotlinType getDispatchReceiverType();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    FirModuleData getModuleData();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    FirDeclarationOrigin getOrigin();

    FirReceiverParameter getReceiverParameter();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    FirResolvePhase getResolvePhase();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    FirTypeRef getReturnTypeRef();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    KtSourceElement getSource();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    FirDeclarationStatus getStatus();

    FirConstructorSymbol getSymbol();

    List<FirTypeParameterRef> getTypeParameters();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    List<FirValueParameter> getValueParameters();

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    void setAttributes(FirDeclarationAttributes firDeclarationAttributes);

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    void setBody(FirBlock firBlock);

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    void setContainerSource(DeserializedContainerSource deserializedContainerSource);

    void setContractDescription(FirContractDescription firContractDescription);

    void setControlFlowGraphReference(FirControlFlowGraphReference firControlFlowGraphReference);

    void setDelegatedConstructor(FirDelegatedConstructorCall firDelegatedConstructorCall);

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    void setDeprecationsProvider(DeprecationsProvider deprecationsProvider);

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    void setDispatchReceiverType(ConeSimpleKotlinType coneSimpleKotlinType);

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    void setModuleData(FirModuleData firModuleData);

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    void setOrigin(FirDeclarationOrigin firDeclarationOrigin);

    void setReceiverParameter(FirReceiverParameter firReceiverParameter);

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    void setResolvePhase(FirResolvePhase firResolvePhase);

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    void setReturnTypeRef(FirTypeRef firTypeRef);

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    void setSource(KtSourceElement ktSourceElement);

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    void setStatus(FirDeclarationStatus firDeclarationStatus);

    void setSymbol(FirConstructorSymbol firConstructorSymbol);
}
