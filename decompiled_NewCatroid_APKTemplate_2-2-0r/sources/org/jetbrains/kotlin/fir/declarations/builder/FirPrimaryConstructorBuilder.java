package org.jetbrains.kotlin.fir.declarations.builder;

import androidx.core.app.NotificationCompat;
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
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.contracts.FirContractDescription;
import org.jetbrains.kotlin.fir.contracts.impl.FirEmptyContractDescription;
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
import org.jetbrains.kotlin.fir.declarations.UnresolvedDeprecationProvider;
import org.jetbrains.kotlin.fir.declarations.impl.FirPrimaryConstructor;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirPrimaryConstructorBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000º\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010t\u001a\u00020uH\u0016R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\bR\u001a\u0010\u001e\u001a\u00020\u001fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R.\u0010&\u001a\u0004\u0018\u00010%2\b\u0010$\u001a\u0004\u0018\u00010%8V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b'\u0010\u0003\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u001c\u0010,\u001a\u0004\u0018\u00010-X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010/\"\u0004\b0\u00101R\u001a\u00102\u001a\u000203X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u00105\"\u0004\b6\u00107R\u001c\u00108\u001a\u0004\u0018\u000109X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=R\u001a\u0010>\u001a\u00020?X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b@\u0010A\"\u0004\bB\u0010CR\u001a\u0010D\u001a\u00020EX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bF\u0010G\"\u0004\bH\u0010IR\u001c\u0010J\u001a\u0004\u0018\u00010KX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bL\u0010M\"\u0004\bN\u0010OR\u001a\u0010P\u001a\u00020QX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bR\u0010S\"\u0004\bT\u0010UR\u001a\u0010V\u001a\u00020WX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bX\u0010Y\"\u0004\bZ\u0010[R\u001c\u0010\\\u001a\u0004\u0018\u00010]X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b^\u0010_\"\u0004\b`\u0010aR\u001a\u0010b\u001a\u00020cX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bd\u0010e\"\u0004\bf\u0010gR\u001a\u0010h\u001a\u00020iX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bj\u0010k\"\u0004\bl\u0010mR\u001a\u0010n\u001a\b\u0012\u0004\u0012\u00020o0\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bp\u0010\bR\u001a\u0010q\u001a\b\u0012\u0004\u0012\u00020r0\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bs\u0010\b¨\u0006v"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/builder/FirPrimaryConstructorBuilder;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirAbstractConstructorBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "setAttributes", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;)V", "body", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "getBody", "()Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "setBody", "(Lorg/jetbrains/kotlin/fir/expressions/FirBlock;)V", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "setContainerSource", "(Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;)V", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getContextReceivers", "contractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "getContractDescription", "()Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "setContractDescription", "(Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;)V", "<anonymous parameter 0>", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "controlFlowGraphReference", "getControlFlowGraphReference$annotations", "getControlFlowGraphReference", "()Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "setControlFlowGraphReference", "(Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;)V", "delegatedConstructor", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "getDelegatedConstructor", "()Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "setDelegatedConstructor", "(Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;)V", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "setDeprecationsProvider", "(Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;)V", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "setDispatchReceiverType", "(Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;)V", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "setModuleData", "(Lorg/jetbrains/kotlin/fir/FirModuleData;)V", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;)V", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "getReceiverParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "setReceiverParameter", "(Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;)V", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "getResolvePhase", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "setResolvePhase", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setReturnTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "setStatus", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;)V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;)V", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "getTypeParameters", "valueParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "getValueParameters", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirPrimaryConstructorBuilder implements FirAnnotationContainerBuilder, FirAbstractConstructorBuilder {
    private FirBlock body;
    private DeserializedContainerSource containerSource;
    private FirDelegatedConstructorCall delegatedConstructor;
    private ConeSimpleKotlinType dispatchReceiverType;
    public FirModuleData moduleData;
    public FirDeclarationOrigin origin;
    private FirReceiverParameter receiverParameter;
    public FirTypeRef returnTypeRef;
    private KtSourceElement source;
    public FirDeclarationStatus status;
    public FirConstructorSymbol symbol;
    private FirResolvePhase resolvePhase = FirResolvePhase.RAW_FIR;
    private FirDeclarationAttributes attributes = new FirDeclarationAttributes();
    private final List<FirTypeParameterRef> typeParameters = new ArrayList();
    private DeprecationsProvider deprecationsProvider = UnresolvedDeprecationProvider.INSTANCE;
    private final List<FirContextReceiver> contextReceivers = new ArrayList();
    private final List<FirValueParameter> valueParameters = new ArrayList();
    private FirContractDescription contractDescription = FirEmptyContractDescription.INSTANCE;
    private final List<FirAnnotation> annotations = new ArrayList();

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'controlFlowGraphReference' has no impact for FirPrimaryConstructorBuilder")
    public static /* synthetic */ void getControlFlowGraphReference$annotations() {
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public FirResolvePhase getResolvePhase() {
        return this.resolvePhase;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public void setResolvePhase(FirResolvePhase firResolvePhase) {
        Intrinsics.checkNotNullParameter(firResolvePhase, "<set-?>");
        this.resolvePhase = firResolvePhase;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public FirModuleData getModuleData() {
        FirModuleData firModuleData = this.moduleData;
        if (firModuleData != null) {
            return firModuleData;
        }
        Intrinsics.throwUninitializedPropertyAccessException("moduleData");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public void setModuleData(FirModuleData firModuleData) {
        Intrinsics.checkNotNullParameter(firModuleData, "<set-?>");
        this.moduleData = firModuleData;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public FirDeclarationOrigin getOrigin() {
        FirDeclarationOrigin firDeclarationOrigin = this.origin;
        if (firDeclarationOrigin != null) {
            return firDeclarationOrigin;
        }
        Intrinsics.throwUninitializedPropertyAccessException("origin");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public void setOrigin(FirDeclarationOrigin firDeclarationOrigin) {
        Intrinsics.checkNotNullParameter(firDeclarationOrigin, "<set-?>");
        this.origin = firDeclarationOrigin;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public FirDeclarationAttributes getAttributes() {
        return this.attributes;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public void setAttributes(FirDeclarationAttributes firDeclarationAttributes) {
        Intrinsics.checkNotNullParameter(firDeclarationAttributes, "<set-?>");
        this.attributes = firDeclarationAttributes;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder
    public List<FirTypeParameterRef> getTypeParameters() {
        return this.typeParameters;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public FirDeclarationStatus getStatus() {
        FirDeclarationStatus firDeclarationStatus = this.status;
        if (firDeclarationStatus != null) {
            return firDeclarationStatus;
        }
        Intrinsics.throwUninitializedPropertyAccessException(NotificationCompat.CATEGORY_STATUS);
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public void setStatus(FirDeclarationStatus firDeclarationStatus) {
        Intrinsics.checkNotNullParameter(firDeclarationStatus, "<set-?>");
        this.status = firDeclarationStatus;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public FirTypeRef getReturnTypeRef() {
        FirTypeRef firTypeRef = this.returnTypeRef;
        if (firTypeRef != null) {
            return firTypeRef;
        }
        Intrinsics.throwUninitializedPropertyAccessException("returnTypeRef");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public void setReturnTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.returnTypeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder
    public FirReceiverParameter getReceiverParameter() {
        return this.receiverParameter;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder
    public void setReceiverParameter(FirReceiverParameter firReceiverParameter) {
        this.receiverParameter = firReceiverParameter;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public DeprecationsProvider getDeprecationsProvider() {
        return this.deprecationsProvider;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public void setDeprecationsProvider(DeprecationsProvider deprecationsProvider) {
        Intrinsics.checkNotNullParameter(deprecationsProvider, "<set-?>");
        this.deprecationsProvider = deprecationsProvider;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public DeserializedContainerSource getContainerSource() {
        return this.containerSource;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public void setContainerSource(DeserializedContainerSource deserializedContainerSource) {
        this.containerSource = deserializedContainerSource;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public ConeSimpleKotlinType getDispatchReceiverType() {
        return this.dispatchReceiverType;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public void setDispatchReceiverType(ConeSimpleKotlinType coneSimpleKotlinType) {
        this.dispatchReceiverType = coneSimpleKotlinType;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public List<FirContextReceiver> getContextReceivers() {
        return this.contextReceivers;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public List<FirValueParameter> getValueParameters() {
        return this.valueParameters;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder
    public FirContractDescription getContractDescription() {
        return this.contractDescription;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder
    public void setContractDescription(FirContractDescription firContractDescription) {
        Intrinsics.checkNotNullParameter(firContractDescription, "<set-?>");
        this.contractDescription = firContractDescription;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public List<FirAnnotation> getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder
    public FirConstructorSymbol getSymbol() {
        FirConstructorSymbol firConstructorSymbol = this.symbol;
        if (firConstructorSymbol != null) {
            return firConstructorSymbol;
        }
        Intrinsics.throwUninitializedPropertyAccessException(NativeSymbol.TYPE_NAME);
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder
    public void setSymbol(FirConstructorSymbol firConstructorSymbol) {
        Intrinsics.checkNotNullParameter(firConstructorSymbol, "<set-?>");
        this.symbol = firConstructorSymbol;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder
    public FirDelegatedConstructorCall getDelegatedConstructor() {
        return this.delegatedConstructor;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder
    public void setDelegatedConstructor(FirDelegatedConstructorCall firDelegatedConstructorCall) {
        this.delegatedConstructor = firDelegatedConstructorCall;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public FirBlock getBody() {
        return this.body;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public void setBody(FirBlock firBlock) {
        this.body = firBlock;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public FirConstructor build() {
        return new FirPrimaryConstructor(getSource(), getResolvePhase(), getModuleData(), getOrigin(), getAttributes(), getTypeParameters(), getStatus(), getReturnTypeRef(), getReceiverParameter(), getDeprecationsProvider(), getContainerSource(), getDispatchReceiverType(), FirBuilderDslKt.toMutableOrEmpty(getContextReceivers()), getValueParameters(), getContractDescription(), FirBuilderDslKt.toMutableOrEmpty(getAnnotations()), getSymbol(), getDelegatedConstructor(), getBody(), null);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder
    public /* synthetic */ FirControlFlowGraphReference getControlFlowGraphReference() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder
    public /* synthetic */ void setControlFlowGraphReference(FirControlFlowGraphReference firControlFlowGraphReference) {
        throw new IllegalStateException();
    }
}
