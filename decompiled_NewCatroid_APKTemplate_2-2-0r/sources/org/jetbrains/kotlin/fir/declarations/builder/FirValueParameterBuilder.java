package org.jetbrains.kotlin.fir.declarations.builder;

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
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.declarations.DeprecationsProvider;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationAttributes;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.UnresolvedDeprecationProvider;
import org.jetbrains.kotlin.fir.declarations.impl.FirValueParameterImpl;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirValueParameterBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010m\u001a\u00020nH\u0016R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\tX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001e\u0010\u001a\u001a\u0006\u0012\u0002\b\u00030\u001bX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0007R\u001c\u0010#\u001a\u0004\u0018\u00010$X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u001a\u0010)\u001a\u00020*X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u001c\u0010/\u001a\u0004\u0018\u000100X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u00102\"\u0004\b3\u00104R+\u00107\u001a\u0002062\u0006\u00105\u001a\u0002068V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b;\u0010<\u001a\u0004\b7\u00108\"\u0004\b9\u0010:R+\u0010=\u001a\u0002062\u0006\u00105\u001a\u0002068V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b?\u0010<\u001a\u0004\b=\u00108\"\u0004\b>\u0010:R+\u0010@\u001a\u0002062\u0006\u00105\u001a\u0002068V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bB\u0010<\u001a\u0004\b@\u00108\"\u0004\bA\u0010:R\u001a\u0010C\u001a\u00020DX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bE\u0010F\"\u0004\bG\u0010HR\u001a\u0010I\u001a\u00020JX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bK\u0010L\"\u0004\bM\u0010NR\u001a\u0010O\u001a\u00020PX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010R\"\u0004\bS\u0010TR\u001a\u0010U\u001a\u00020VX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bW\u0010X\"\u0004\bY\u0010ZR\u001a\u0010[\u001a\u00020\\X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b]\u0010^\"\u0004\b_\u0010`R\u001c\u0010a\u001a\u0004\u0018\u00010bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bc\u0010d\"\u0004\be\u0010fR\u001a\u0010g\u001a\u00020hX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bi\u0010j\"\u0004\bk\u0010l¨\u0006o"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/builder/FirValueParameterBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "setAttributes", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;)V", "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "getBackingField", "()Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "setBackingField", "(Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;)V", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "setContainerSource", "(Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;)V", "containingFunctionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "getContainingFunctionSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "setContainingFunctionSymbol", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;)V", "contextReceivers", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getContextReceivers", "defaultValue", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getDefaultValue", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setDefaultValue", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "deprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "getDeprecationsProvider", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "setDeprecationsProvider", "(Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;)V", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "setDispatchReceiverType", "(Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;)V", "<set-?>", "", "isCrossinline", "()Z", "setCrossinline", "(Z)V", "isCrossinline$delegate", "Lkotlin/properties/ReadWriteProperty;", "isNoinline", "setNoinline", "isNoinline$delegate", "isVararg", "setVararg", "isVararg$delegate", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "setModuleData", "(Lorg/jetbrains/kotlin/fir/FirModuleData;)V", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;)V", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "getResolvePhase", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "setResolvePhase", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", "returnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getReturnTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "setReturnTypeRef", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirValueParameterBuilder implements FirAnnotationContainerBuilder {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirValueParameterBuilder.class), "isCrossinline", "isCrossinline()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirValueParameterBuilder.class), "isNoinline", "isNoinline()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirValueParameterBuilder.class), "isVararg", "isVararg()Z"))};
    private FirBackingField backingField;
    private DeserializedContainerSource containerSource;
    public FirFunctionSymbol<?> containingFunctionSymbol;
    private FirExpression defaultValue;
    private ConeSimpleKotlinType dispatchReceiverType;
    public FirModuleData moduleData;
    public Name name;
    public FirDeclarationOrigin origin;
    public FirTypeRef returnTypeRef;
    private KtSourceElement source;
    public FirValueParameterSymbol symbol;
    private FirResolvePhase resolvePhase = FirResolvePhase.RAW_FIR;
    private FirDeclarationAttributes attributes = new FirDeclarationAttributes();
    private DeprecationsProvider deprecationsProvider = UnresolvedDeprecationProvider.INSTANCE;
    private final List<FirContextReceiver> contextReceivers = new ArrayList();
    private final List<FirAnnotation> annotations = new ArrayList();

    /* JADX INFO: renamed from: isCrossinline$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty isCrossinline = Delegates.INSTANCE.notNull();

    /* JADX INFO: renamed from: isNoinline$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty isNoinline = Delegates.INSTANCE.notNull();

    /* JADX INFO: renamed from: isVararg$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty isVararg = Delegates.INSTANCE.notNull();

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    public FirResolvePhase getResolvePhase() {
        return this.resolvePhase;
    }

    public void setResolvePhase(FirResolvePhase firResolvePhase) {
        Intrinsics.checkNotNullParameter(firResolvePhase, "<set-?>");
        this.resolvePhase = firResolvePhase;
    }

    public FirModuleData getModuleData() {
        FirModuleData firModuleData = this.moduleData;
        if (firModuleData != null) {
            return firModuleData;
        }
        Intrinsics.throwUninitializedPropertyAccessException("moduleData");
        return null;
    }

    public void setModuleData(FirModuleData firModuleData) {
        Intrinsics.checkNotNullParameter(firModuleData, "<set-?>");
        this.moduleData = firModuleData;
    }

    public FirDeclarationOrigin getOrigin() {
        FirDeclarationOrigin firDeclarationOrigin = this.origin;
        if (firDeclarationOrigin != null) {
            return firDeclarationOrigin;
        }
        Intrinsics.throwUninitializedPropertyAccessException("origin");
        return null;
    }

    public void setOrigin(FirDeclarationOrigin firDeclarationOrigin) {
        Intrinsics.checkNotNullParameter(firDeclarationOrigin, "<set-?>");
        this.origin = firDeclarationOrigin;
    }

    public FirDeclarationAttributes getAttributes() {
        return this.attributes;
    }

    public void setAttributes(FirDeclarationAttributes firDeclarationAttributes) {
        Intrinsics.checkNotNullParameter(firDeclarationAttributes, "<set-?>");
        this.attributes = firDeclarationAttributes;
    }

    public FirTypeRef getReturnTypeRef() {
        FirTypeRef firTypeRef = this.returnTypeRef;
        if (firTypeRef != null) {
            return firTypeRef;
        }
        Intrinsics.throwUninitializedPropertyAccessException("returnTypeRef");
        return null;
    }

    public void setReturnTypeRef(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<set-?>");
        this.returnTypeRef = firTypeRef;
    }

    public DeprecationsProvider getDeprecationsProvider() {
        return this.deprecationsProvider;
    }

    public void setDeprecationsProvider(DeprecationsProvider deprecationsProvider) {
        Intrinsics.checkNotNullParameter(deprecationsProvider, "<set-?>");
        this.deprecationsProvider = deprecationsProvider;
    }

    public DeserializedContainerSource getContainerSource() {
        return this.containerSource;
    }

    public void setContainerSource(DeserializedContainerSource deserializedContainerSource) {
        this.containerSource = deserializedContainerSource;
    }

    public ConeSimpleKotlinType getDispatchReceiverType() {
        return this.dispatchReceiverType;
    }

    public void setDispatchReceiverType(ConeSimpleKotlinType coneSimpleKotlinType) {
        this.dispatchReceiverType = coneSimpleKotlinType;
    }

    public List<FirContextReceiver> getContextReceivers() {
        return this.contextReceivers;
    }

    public Name getName() {
        Name name2 = this.name;
        if (name2 != null) {
            return name2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("name");
        return null;
    }

    public void setName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "<set-?>");
        this.name = name2;
    }

    public FirBackingField getBackingField() {
        return this.backingField;
    }

    public void setBackingField(FirBackingField firBackingField) {
        this.backingField = firBackingField;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public List<FirAnnotation> getAnnotations() {
        return this.annotations;
    }

    public FirValueParameterSymbol getSymbol() {
        FirValueParameterSymbol firValueParameterSymbol = this.symbol;
        if (firValueParameterSymbol != null) {
            return firValueParameterSymbol;
        }
        Intrinsics.throwUninitializedPropertyAccessException(NativeSymbol.TYPE_NAME);
        return null;
    }

    public void setSymbol(FirValueParameterSymbol firValueParameterSymbol) {
        Intrinsics.checkNotNullParameter(firValueParameterSymbol, "<set-?>");
        this.symbol = firValueParameterSymbol;
    }

    public FirExpression getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(FirExpression firExpression) {
        this.defaultValue = firExpression;
    }

    public FirFunctionSymbol<?> getContainingFunctionSymbol() {
        FirFunctionSymbol<?> firFunctionSymbol = this.containingFunctionSymbol;
        if (firFunctionSymbol != null) {
            return firFunctionSymbol;
        }
        Intrinsics.throwUninitializedPropertyAccessException("containingFunctionSymbol");
        return null;
    }

    public void setContainingFunctionSymbol(FirFunctionSymbol<?> firFunctionSymbol) {
        Intrinsics.checkNotNullParameter(firFunctionSymbol, "<set-?>");
        this.containingFunctionSymbol = firFunctionSymbol;
    }

    public boolean isCrossinline() {
        return ((Boolean) this.isCrossinline.getValue(this, $$delegatedProperties[0])).booleanValue();
    }

    public void setCrossinline(boolean z) {
        this.isCrossinline.setValue(this, $$delegatedProperties[0], Boolean.valueOf(z));
    }

    public boolean isNoinline() {
        return ((Boolean) this.isNoinline.getValue(this, $$delegatedProperties[1])).booleanValue();
    }

    public void setNoinline(boolean z) {
        this.isNoinline.setValue(this, $$delegatedProperties[1], Boolean.valueOf(z));
    }

    public boolean isVararg() {
        return ((Boolean) this.isVararg.getValue(this, $$delegatedProperties[2])).booleanValue();
    }

    public void setVararg(boolean z) {
        this.isVararg.setValue(this, $$delegatedProperties[2], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    /* JADX INFO: renamed from: build */
    public FirValueParameter mo6809build() {
        return new FirValueParameterImpl(getSource(), getResolvePhase(), getModuleData(), getOrigin(), getAttributes(), getReturnTypeRef(), getDeprecationsProvider(), getContainerSource(), getDispatchReceiverType(), FirBuilderDslKt.toMutableOrEmpty(getContextReceivers()), getName(), getBackingField(), FirBuilderDslKt.toMutableOrEmpty(getAnnotations()), getSymbol(), getDefaultValue(), getContainingFunctionSymbol(), isCrossinline(), isNoinline(), isVararg(), null);
    }
}
