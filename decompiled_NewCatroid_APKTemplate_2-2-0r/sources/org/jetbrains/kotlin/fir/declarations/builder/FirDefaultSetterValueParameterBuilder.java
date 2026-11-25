package org.jetbrains.kotlin.fir.declarations.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.declarations.DeprecationsProvider;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationAttributes;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.UnresolvedDeprecationProvider;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultSetterValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirDefaultSetterValueParameterBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000¦\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010{\u001a\u00020|H\u0016R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001e\u0010\u001a\u001a\u0006\u0012\u0002\b\u00030\u001bX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0007R\u001c\u0010#\u001a\u0004\u0018\u00010$X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u001c\u0010)\u001a\u0004\u0018\u00010$X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010&\"\u0004\b+\u0010(R\u001a\u0010,\u001a\u00020-X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010/\"\u0004\b0\u00101R\u001c\u00102\u001a\u0004\u0018\u000103X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u00105\"\u0004\b6\u00107R\u001c\u00108\u001a\u0004\u0018\u000109X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=R\u001c\u0010>\u001a\u0004\u0018\u00010$X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010&\"\u0004\b@\u0010(R\u001a\u0010A\u001a\u00020BX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bA\u0010C\"\u0004\bD\u0010ER\u001a\u0010F\u001a\u00020BX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bF\u0010C\"\u0004\bG\u0010ER\u001a\u0010H\u001a\u00020BX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010C\"\u0004\bI\u0010ER\u001a\u0010J\u001a\u00020BX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010C\"\u0004\bK\u0010ER\u001a\u0010L\u001a\u00020BX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bL\u0010C\"\u0004\bM\u0010ER\u001a\u0010N\u001a\u00020OX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bP\u0010Q\"\u0004\bR\u0010SR\u001a\u0010T\u001a\u00020UX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bV\u0010W\"\u0004\bX\u0010YR\u001c\u0010Z\u001a\u0004\u0018\u00010[X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\\\u0010]\"\u0004\b^\u0010_R\u001a\u0010`\u001a\u00020aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bb\u0010c\"\u0004\bd\u0010eR\u001a\u0010f\u001a\u00020gX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bh\u0010i\"\u0004\bj\u0010kR\u001c\u0010l\u001a\u0004\u0018\u000109X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bm\u0010;\"\u0004\bn\u0010=R\u001c\u0010o\u001a\u0004\u0018\u00010pX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bq\u0010r\"\u0004\bs\u0010tR\u001a\u0010u\u001a\u00020vX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bw\u0010x\"\u0004\by\u0010z¨\u0006}"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/builder/FirDefaultSetterValueParameterBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "setAttributes", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;)V", "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "getBackingField", "()Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "setBackingField", "(Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;)V", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "setContainerSource", "(Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;)V", "containingFunctionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "getContainingFunctionSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "setContainingFunctionSymbol", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;)V", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getContextReceivers", "defaultValue", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getDefaultValue", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setDefaultValue", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "getDelegate", "setDelegate", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "setDeprecationsProvider", "(Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;)V", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "setDispatchReceiverType", "(Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;)V", "getter", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "getGetter", "()Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "setGetter", "(Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;)V", "initializer", "getInitializer", "setInitializer", "isCrossinline", "", "()Z", "setCrossinline", "(Z)V", "isNoinline", "setNoinline", "isVal", "setVal", "isVar", "setVar", "isVararg", "setVararg", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "setModuleData", "(Lorg/jetbrains/kotlin/fir/FirModuleData;)V", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;)V", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "getReceiverParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "setReceiverParameter", "(Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;)V", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "getResolvePhase", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "setResolvePhase", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setReturnTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", "setter", "getSetter", "setSetter", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDefaultSetterValueParameterBuilder implements FirAnnotationContainerBuilder {
    private FirBackingField backingField;
    private DeserializedContainerSource containerSource;
    public FirFunctionSymbol<?> containingFunctionSymbol;
    private FirExpression defaultValue;
    private FirExpression delegate;
    private ConeSimpleKotlinType dispatchReceiverType;
    private FirPropertyAccessor getter;
    private FirExpression initializer;
    private boolean isCrossinline;
    private boolean isNoinline;
    private boolean isVar;
    private boolean isVararg;
    public FirModuleData moduleData;
    public FirDeclarationOrigin origin;
    private FirReceiverParameter receiverParameter;
    public FirTypeRef returnTypeRef;
    private FirPropertyAccessor setter;
    private KtSourceElement source;
    public FirValueParameterSymbol symbol;
    private FirResolvePhase resolvePhase = FirResolvePhase.RAW_FIR;
    private FirDeclarationAttributes attributes = new FirDeclarationAttributes();
    private DeprecationsProvider deprecationsProvider = UnresolvedDeprecationProvider.INSTANCE;
    private final List<FirContextReceiver> contextReceivers = new ArrayList();
    private boolean isVal = true;
    private final List<FirAnnotation> annotations = new ArrayList();

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    public final FirResolvePhase getResolvePhase() {
        return this.resolvePhase;
    }

    public final void setResolvePhase(FirResolvePhase firResolvePhase) {
        Intrinsics.checkNotNullParameter(firResolvePhase, "<set-?>");
        this.resolvePhase = firResolvePhase;
    }

    public final FirModuleData getModuleData() {
        FirModuleData firModuleData = this.moduleData;
        if (firModuleData != null) {
            return firModuleData;
        }
        Intrinsics.throwUninitializedPropertyAccessException("moduleData");
        return null;
    }

    public final void setModuleData(FirModuleData firModuleData) {
        Intrinsics.checkNotNullParameter(firModuleData, "<set-?>");
        this.moduleData = firModuleData;
    }

    public final FirDeclarationOrigin getOrigin() {
        FirDeclarationOrigin firDeclarationOrigin = this.origin;
        if (firDeclarationOrigin != null) {
            return firDeclarationOrigin;
        }
        Intrinsics.throwUninitializedPropertyAccessException("origin");
        return null;
    }

    public final void setOrigin(FirDeclarationOrigin firDeclarationOrigin) {
        Intrinsics.checkNotNullParameter(firDeclarationOrigin, "<set-?>");
        this.origin = firDeclarationOrigin;
    }

    public final FirDeclarationAttributes getAttributes() {
        return this.attributes;
    }

    public final void setAttributes(FirDeclarationAttributes firDeclarationAttributes) {
        Intrinsics.checkNotNullParameter(firDeclarationAttributes, "<set-?>");
        this.attributes = firDeclarationAttributes;
    }

    public final FirTypeRef getReturnTypeRef() {
        FirTypeRef firTypeRef = this.returnTypeRef;
        if (firTypeRef != null) {
            return firTypeRef;
        }
        Intrinsics.throwUninitializedPropertyAccessException("returnTypeRef");
        return null;
    }

    public final void setReturnTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.returnTypeRef = firTypeRef;
    }

    public final FirReceiverParameter getReceiverParameter() {
        return this.receiverParameter;
    }

    public final void setReceiverParameter(FirReceiverParameter firReceiverParameter) {
        this.receiverParameter = firReceiverParameter;
    }

    public final DeprecationsProvider getDeprecationsProvider() {
        return this.deprecationsProvider;
    }

    public final void setDeprecationsProvider(DeprecationsProvider deprecationsProvider) {
        Intrinsics.checkNotNullParameter(deprecationsProvider, "<set-?>");
        this.deprecationsProvider = deprecationsProvider;
    }

    public final DeserializedContainerSource getContainerSource() {
        return this.containerSource;
    }

    public final void setContainerSource(DeserializedContainerSource deserializedContainerSource) {
        this.containerSource = deserializedContainerSource;
    }

    public final ConeSimpleKotlinType getDispatchReceiverType() {
        return this.dispatchReceiverType;
    }

    public final void setDispatchReceiverType(ConeSimpleKotlinType coneSimpleKotlinType) {
        this.dispatchReceiverType = coneSimpleKotlinType;
    }

    public final List<FirContextReceiver> getContextReceivers() {
        return this.contextReceivers;
    }

    public final FirExpression getInitializer() {
        return this.initializer;
    }

    public final void setInitializer(FirExpression firExpression) {
        this.initializer = firExpression;
    }

    public final FirExpression getDelegate() {
        return this.delegate;
    }

    public final void setDelegate(FirExpression firExpression) {
        this.delegate = firExpression;
    }

    /* JADX INFO: renamed from: isVar, reason: from getter */
    public final boolean getIsVar() {
        return this.isVar;
    }

    public final void setVar(boolean z) {
        this.isVar = z;
    }

    /* JADX INFO: renamed from: isVal, reason: from getter */
    public final boolean getIsVal() {
        return this.isVal;
    }

    public final void setVal(boolean z) {
        this.isVal = z;
    }

    public final FirPropertyAccessor getGetter() {
        return this.getter;
    }

    public final void setGetter(FirPropertyAccessor firPropertyAccessor) {
        this.getter = firPropertyAccessor;
    }

    public final FirPropertyAccessor getSetter() {
        return this.setter;
    }

    public final void setSetter(FirPropertyAccessor firPropertyAccessor) {
        this.setter = firPropertyAccessor;
    }

    public final FirBackingField getBackingField() {
        return this.backingField;
    }

    public final void setBackingField(FirBackingField firBackingField) {
        this.backingField = firBackingField;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public List<FirAnnotation> getAnnotations() {
        return this.annotations;
    }

    public final FirValueParameterSymbol getSymbol() {
        FirValueParameterSymbol firValueParameterSymbol = this.symbol;
        if (firValueParameterSymbol != null) {
            return firValueParameterSymbol;
        }
        Intrinsics.throwUninitializedPropertyAccessException(NativeSymbol.TYPE_NAME);
        return null;
    }

    public final void setSymbol(FirValueParameterSymbol firValueParameterSymbol) {
        Intrinsics.checkNotNullParameter(firValueParameterSymbol, "<set-?>");
        this.symbol = firValueParameterSymbol;
    }

    public final FirExpression getDefaultValue() {
        return this.defaultValue;
    }

    public final void setDefaultValue(FirExpression firExpression) {
        this.defaultValue = firExpression;
    }

    public final FirFunctionSymbol<?> getContainingFunctionSymbol() {
        FirFunctionSymbol<?> firFunctionSymbol = this.containingFunctionSymbol;
        if (firFunctionSymbol != null) {
            return firFunctionSymbol;
        }
        Intrinsics.throwUninitializedPropertyAccessException("containingFunctionSymbol");
        return null;
    }

    public final void setContainingFunctionSymbol(FirFunctionSymbol<?> firFunctionSymbol) {
        Intrinsics.checkNotNullParameter(firFunctionSymbol, "<set-?>");
        this.containingFunctionSymbol = firFunctionSymbol;
    }

    /* JADX INFO: renamed from: isCrossinline, reason: from getter */
    public final boolean getIsCrossinline() {
        return this.isCrossinline;
    }

    public final void setCrossinline(boolean z) {
        this.isCrossinline = z;
    }

    /* JADX INFO: renamed from: isNoinline, reason: from getter */
    public final boolean getIsNoinline() {
        return this.isNoinline;
    }

    public final void setNoinline(boolean z) {
        this.isNoinline = z;
    }

    /* JADX INFO: renamed from: isVararg, reason: from getter */
    public final boolean getIsVararg() {
        return this.isVararg;
    }

    public final void setVararg(boolean z) {
        this.isVararg = z;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public FirValueParameter build() {
        return new FirDefaultSetterValueParameter(getSource(), this.resolvePhase, getModuleData(), getOrigin(), this.attributes, getReturnTypeRef(), this.receiverParameter, this.deprecationsProvider, this.containerSource, this.dispatchReceiverType, FirBuilderDslKt.toMutableOrEmpty(this.contextReceivers), this.initializer, this.delegate, this.isVar, this.isVal, this.getter, this.setter, this.backingField, FirBuilderDslKt.toMutableOrEmpty(getAnnotations()), getSymbol(), this.defaultValue, getContainingFunctionSymbol(), this.isCrossinline, this.isNoinline, this.isVararg, null);
    }
}
