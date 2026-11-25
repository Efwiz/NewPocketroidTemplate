package org.jetbrains.kotlin.fir.declarations.builder;

import androidx.core.app.NotificationCompat;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.fir.FirLabel;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.contracts.FirContractDescription;
import org.jetbrains.kotlin.fir.contracts.impl.FirEmptyContractDescription;
import org.jetbrains.kotlin.fir.declarations.DeprecationsProvider;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationAttributes;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.InlineStatus;
import org.jetbrains.kotlin.fir.declarations.UnresolvedDeprecationProvider;
import org.jetbrains.kotlin.fir.declarations.impl.FirAnonymousFunctionImpl;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousFunctionSymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitTypeRefImplWithoutSource;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirAnonymousFunctionBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000Ò\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\n\u0010\u008d\u0001\u001a\u00030\u008e\u0001H\u0016R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\bR\u001a\u0010\u001e\u001a\u00020\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001c\u0010$\u001a\u0004\u0018\u00010%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u001a\u0010*\u001a\u00020+X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\u001c\u00100\u001a\u0004\u0018\u000101X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u00103\"\u0004\b4\u00105R+\u00108\u001a\u0002072\u0006\u00106\u001a\u0002078F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b=\u0010>\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R\u001a\u0010?\u001a\u00020@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bA\u0010B\"\u0004\bC\u0010DR\u001c\u0010E\u001a\u0004\u0018\u00010FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bG\u0010H\"\u0004\bI\u0010JR+\u0010K\u001a\u0002072\u0006\u00106\u001a\u0002078F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\bM\u0010>\u001a\u0004\bK\u0010:\"\u0004\bL\u0010<R\u001c\u0010N\u001a\u0004\u0018\u00010OX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bP\u0010Q\"\u0004\bR\u0010SR\u001a\u0010T\u001a\u00020UX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bV\u0010W\"\u0004\bX\u0010YR\u001a\u0010Z\u001a\u00020[X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\\\u0010]\"\u0004\b^\u0010_R\u001c\u0010`\u001a\u0004\u0018\u00010aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bb\u0010c\"\u0004\bd\u0010eR\u001a\u0010f\u001a\u00020gX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bh\u0010i\"\u0004\bj\u0010kR\u001a\u0010l\u001a\u00020mX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bn\u0010o\"\u0004\bp\u0010qR\u001c\u0010r\u001a\u0004\u0018\u00010sX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bt\u0010u\"\u0004\bv\u0010wR\u001a\u0010x\u001a\u00020yX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bz\u0010{\"\u0004\b|\u0010}R\u001e\u0010~\u001a\u00020\u007fX\u0086.¢\u0006\u0012\n\u0000\u001a\u0006\b\u0080\u0001\u0010\u0081\u0001\"\u0006\b\u0082\u0001\u0010\u0083\u0001R\u001a\u0010\u0084\u0001\u001a\t\u0012\u0005\u0012\u00030\u0085\u00010\u0005¢\u0006\t\n\u0000\u001a\u0005\b\u0086\u0001\u0010\bR\u001d\u0010\u0087\u0001\u001a\u00020mX\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0088\u0001\u0010o\"\u0005\b\u0089\u0001\u0010qR\u001d\u0010\u008a\u0001\u001a\t\u0012\u0005\u0012\u00030\u008b\u00010\u0005X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008c\u0001\u0010\b¨\u0006\u008f\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/builder/FirAnonymousFunctionBuilder;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirFunctionBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "setAttributes", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;)V", "body", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "getBody", "()Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "setBody", "(Lorg/jetbrains/kotlin/fir/expressions/FirBlock;)V", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "setContainerSource", "(Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;)V", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getContextReceivers", "contractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "getContractDescription", "()Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "setContractDescription", "(Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;)V", "controlFlowGraphReference", "Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "getControlFlowGraphReference", "()Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;", "setControlFlowGraphReference", "(Lorg/jetbrains/kotlin/fir/references/FirControlFlowGraphReference;)V", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "setDeprecationsProvider", "(Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;)V", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "setDispatchReceiverType", "(Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;)V", "<set-?>", "", "hasExplicitParameterList", "getHasExplicitParameterList", "()Z", "setHasExplicitParameterList", "(Z)V", "hasExplicitParameterList$delegate", "Lkotlin/properties/ReadWriteProperty;", "inlineStatus", "Lorg/jetbrains/kotlin/fir/declarations/InlineStatus;", "getInlineStatus", "()Lorg/jetbrains/kotlin/fir/declarations/InlineStatus;", "setInlineStatus", "(Lorg/jetbrains/kotlin/fir/declarations/InlineStatus;)V", "invocationKind", "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "getInvocationKind", "()Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "setInvocationKind", "(Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;)V", "isLambda", "setLambda", "isLambda$delegate", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/kotlin/fir/FirLabel;", "getLabel", "()Lorg/jetbrains/kotlin/fir/FirLabel;", "setLabel", "(Lorg/jetbrains/kotlin/fir/FirLabel;)V", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "setModuleData", "(Lorg/jetbrains/kotlin/fir/FirModuleData;)V", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;)V", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "getReceiverParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "setReceiverParameter", "(Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;)V", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "getResolvePhase", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "setResolvePhase", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setReturnTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "setStatus", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;)V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirAnonymousFunctionSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirAnonymousFunctionSymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirAnonymousFunctionSymbol;)V", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "getTypeParameters", "typeRef", "getTypeRef", "setTypeRef", "valueParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "getValueParameters", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirAnonymousFunctionBuilder implements FirAnnotationContainerBuilder, FirFunctionBuilder {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirAnonymousFunctionBuilder.class), "isLambda", "isLambda()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirAnonymousFunctionBuilder.class), "hasExplicitParameterList", "getHasExplicitParameterList()Z"))};
    private FirBlock body;
    private DeserializedContainerSource containerSource;
    private FirControlFlowGraphReference controlFlowGraphReference;
    private ConeSimpleKotlinType dispatchReceiverType;
    private EventOccurrencesRange invocationKind;
    private FirLabel label;
    public FirModuleData moduleData;
    public FirDeclarationOrigin origin;
    private FirReceiverParameter receiverParameter;
    public FirTypeRef returnTypeRef;
    private KtSourceElement source;
    public FirAnonymousFunctionSymbol symbol;
    private FirResolvePhase resolvePhase = FirResolvePhase.RAW_FIR;
    private final List<FirAnnotation> annotations = new ArrayList();
    private FirDeclarationAttributes attributes = new FirDeclarationAttributes();
    private FirDeclarationStatus status = FirResolvedDeclarationStatusImpl.INSTANCE.getDEFAULT_STATUS_FOR_STATUSLESS_DECLARATIONS();
    private DeprecationsProvider deprecationsProvider = UnresolvedDeprecationProvider.INSTANCE;
    private final List<FirContextReceiver> contextReceivers = new ArrayList();
    private final List<FirValueParameter> valueParameters = new ArrayList();
    private FirContractDescription contractDescription = FirEmptyContractDescription.INSTANCE;
    private InlineStatus inlineStatus = InlineStatus.Unknown;

    /* JADX INFO: renamed from: isLambda$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty isLambda = Delegates.INSTANCE.notNull();

    /* JADX INFO: renamed from: hasExplicitParameterList$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty hasExplicitParameterList = Delegates.INSTANCE.notNull();
    private final List<FirTypeParameter> typeParameters = new ArrayList();
    private FirTypeRef typeRef = FirImplicitTypeRefImplWithoutSource.INSTANCE;

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public FirResolvePhase getResolvePhase() {
        return this.resolvePhase;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public void setResolvePhase(FirResolvePhase firResolvePhase) {
        Intrinsics.checkNotNullParameter(firResolvePhase, "<set-?>");
        this.resolvePhase = firResolvePhase;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public List<FirAnnotation> getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public FirModuleData getModuleData() {
        FirModuleData firModuleData = this.moduleData;
        if (firModuleData != null) {
            return firModuleData;
        }
        Intrinsics.throwUninitializedPropertyAccessException("moduleData");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public void setModuleData(FirModuleData firModuleData) {
        Intrinsics.checkNotNullParameter(firModuleData, "<set-?>");
        this.moduleData = firModuleData;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public FirDeclarationOrigin getOrigin() {
        FirDeclarationOrigin firDeclarationOrigin = this.origin;
        if (firDeclarationOrigin != null) {
            return firDeclarationOrigin;
        }
        Intrinsics.throwUninitializedPropertyAccessException("origin");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public void setOrigin(FirDeclarationOrigin firDeclarationOrigin) {
        Intrinsics.checkNotNullParameter(firDeclarationOrigin, "<set-?>");
        this.origin = firDeclarationOrigin;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public FirDeclarationAttributes getAttributes() {
        return this.attributes;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public void setAttributes(FirDeclarationAttributes firDeclarationAttributes) {
        Intrinsics.checkNotNullParameter(firDeclarationAttributes, "<set-?>");
        this.attributes = firDeclarationAttributes;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public FirDeclarationStatus getStatus() {
        return this.status;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public void setStatus(FirDeclarationStatus firDeclarationStatus) {
        Intrinsics.checkNotNullParameter(firDeclarationStatus, "<set-?>");
        this.status = firDeclarationStatus;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public FirTypeRef getReturnTypeRef() {
        FirTypeRef firTypeRef = this.returnTypeRef;
        if (firTypeRef != null) {
            return firTypeRef;
        }
        Intrinsics.throwUninitializedPropertyAccessException("returnTypeRef");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public void setReturnTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.returnTypeRef = firTypeRef;
    }

    public final FirReceiverParameter getReceiverParameter() {
        return this.receiverParameter;
    }

    public final void setReceiverParameter(FirReceiverParameter firReceiverParameter) {
        this.receiverParameter = firReceiverParameter;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public DeprecationsProvider getDeprecationsProvider() {
        return this.deprecationsProvider;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public void setDeprecationsProvider(DeprecationsProvider deprecationsProvider) {
        Intrinsics.checkNotNullParameter(deprecationsProvider, "<set-?>");
        this.deprecationsProvider = deprecationsProvider;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public DeserializedContainerSource getContainerSource() {
        return this.containerSource;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public void setContainerSource(DeserializedContainerSource deserializedContainerSource) {
        this.containerSource = deserializedContainerSource;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public ConeSimpleKotlinType getDispatchReceiverType() {
        return this.dispatchReceiverType;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public void setDispatchReceiverType(ConeSimpleKotlinType coneSimpleKotlinType) {
        this.dispatchReceiverType = coneSimpleKotlinType;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public List<FirContextReceiver> getContextReceivers() {
        return this.contextReceivers;
    }

    public final FirControlFlowGraphReference getControlFlowGraphReference() {
        return this.controlFlowGraphReference;
    }

    public final void setControlFlowGraphReference(FirControlFlowGraphReference firControlFlowGraphReference) {
        this.controlFlowGraphReference = firControlFlowGraphReference;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public List<FirValueParameter> getValueParameters() {
        return this.valueParameters;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public FirBlock getBody() {
        return this.body;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirFunctionBuilder
    public void setBody(FirBlock firBlock) {
        this.body = firBlock;
    }

    public final FirContractDescription getContractDescription() {
        return this.contractDescription;
    }

    public final void setContractDescription(FirContractDescription firContractDescription) {
        Intrinsics.checkNotNullParameter(firContractDescription, "<set-?>");
        this.contractDescription = firContractDescription;
    }

    public final FirAnonymousFunctionSymbol getSymbol() {
        FirAnonymousFunctionSymbol firAnonymousFunctionSymbol = this.symbol;
        if (firAnonymousFunctionSymbol != null) {
            return firAnonymousFunctionSymbol;
        }
        Intrinsics.throwUninitializedPropertyAccessException(NativeSymbol.TYPE_NAME);
        return null;
    }

    public final void setSymbol(FirAnonymousFunctionSymbol firAnonymousFunctionSymbol) {
        Intrinsics.checkNotNullParameter(firAnonymousFunctionSymbol, "<set-?>");
        this.symbol = firAnonymousFunctionSymbol;
    }

    public final FirLabel getLabel() {
        return this.label;
    }

    public final void setLabel(FirLabel firLabel) {
        this.label = firLabel;
    }

    public final EventOccurrencesRange getInvocationKind() {
        return this.invocationKind;
    }

    public final void setInvocationKind(EventOccurrencesRange eventOccurrencesRange) {
        this.invocationKind = eventOccurrencesRange;
    }

    public final InlineStatus getInlineStatus() {
        return this.inlineStatus;
    }

    public final void setInlineStatus(InlineStatus inlineStatus) {
        Intrinsics.checkNotNullParameter(inlineStatus, "<set-?>");
        this.inlineStatus = inlineStatus;
    }

    public final boolean isLambda() {
        return ((Boolean) this.isLambda.getValue(this, $$delegatedProperties[0])).booleanValue();
    }

    public final void setLambda(boolean z) {
        this.isLambda.setValue(this, $$delegatedProperties[0], Boolean.valueOf(z));
    }

    public final boolean getHasExplicitParameterList() {
        return ((Boolean) this.hasExplicitParameterList.getValue(this, $$delegatedProperties[1])).booleanValue();
    }

    public final void setHasExplicitParameterList(boolean z) {
        this.hasExplicitParameterList.setValue(this, $$delegatedProperties[1], Boolean.valueOf(z));
    }

    public final List<FirTypeParameter> getTypeParameters() {
        return this.typeParameters;
    }

    public final FirTypeRef getTypeRef() {
        return this.typeRef;
    }

    public final void setTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.typeRef = firTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    /* JADX INFO: renamed from: build */
    public FirAnonymousFunction mo6809build() {
        return new FirAnonymousFunctionImpl(getSource(), getResolvePhase(), FirBuilderDslKt.toMutableOrEmpty(getAnnotations()), getModuleData(), getOrigin(), getAttributes(), getStatus(), getReturnTypeRef(), this.receiverParameter, getDeprecationsProvider(), getContainerSource(), getDispatchReceiverType(), FirBuilderDslKt.toMutableOrEmpty(getContextReceivers()), this.controlFlowGraphReference, getValueParameters(), getBody(), this.contractDescription, getSymbol(), this.label, this.invocationKind, this.inlineStatus, isLambda(), getHasExplicitParameterList(), this.typeParameters, this.typeRef, null);
    }
}
