package org.jetbrains.kotlin.fir.lazy;

import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.fir.backend.ConversionTypeOrigin;
import org.jetbrains.kotlin.fir.backend.ConversionUtilsKt;
import org.jetbrains.kotlin.fir.backend.Fir2IrComponents;
import org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage;
import org.jetbrains.kotlin.fir.backend.Fir2IrTypeConverterKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticPropertyAccessor;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.lazy.LazyUtilKt;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: Fir2IrLazyPropertyAccessor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001BY\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\r¢\u0006\u0002\u0010\u0015R7\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00178V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u0016\u0010 \u001a\u0004\u0018\u00010!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u001a\u0010$\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u000e\u0010)\u001a\u00020*X\u0082\u0004¢\u0006\u0002\n\u0000R/\u0010,\u001a\u0004\u0018\u00010+2\b\u0010\u0016\u001a\u0004\u0018\u00010+8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b1\u0010\u001f\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R/\u00102\u001a\u0004\u0018\u00010+2\b\u0010\u0016\u001a\u0004\u0018\u00010+8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b5\u0010\u001f\u001a\u0004\b3\u0010.\"\u0004\b4\u00100R\u0014\u00106\u001a\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b7\u00108R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u00109\u001a\u0004\u0018\u00010:8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b=\u0010>\u001a\u0004\b;\u0010<R$\u0010@\u001a\u00020\r2\u0006\u0010?\u001a\u00020\r8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b@\u0010A\"\u0004\bB\u0010CR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010E\u001a\u00020D2\u0006\u0010?\u001a\u00020D8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bF\u0010G\"\u0004\bH\u0010IR7\u0010J\u001a\b\u0012\u0004\u0012\u00020\u00130\u00172\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00130\u00178V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bM\u0010\u001f\u001a\u0004\bK\u0010\u001b\"\u0004\bL\u0010\u001dR+\u0010O\u001a\u00020N2\u0006\u0010\u0016\u001a\u00020N8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bT\u0010\u001f\u001a\u0004\bP\u0010Q\"\u0004\bR\u0010SR7\u0010U\u001a\b\u0012\u0004\u0012\u00020+0\u00172\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020+0\u00178V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bX\u0010\u001f\u001a\u0004\bV\u0010\u001b\"\u0004\bW\u0010\u001d¨\u0006Y"}, d2 = {"Lorg/jetbrains/kotlin/fir/lazy/Fir2IrLazyPropertyAccessor;", "Lorg/jetbrains/kotlin/fir/lazy/AbstractFir2IrLazyFunction;", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "components", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "firAccessor", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "isSetter", "", "firParentProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "firParentClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "isFakeOverride", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;ZLorg/jetbrains/kotlin/fir/declarations/FirProperty;Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;Z)V", "<set-?>", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "annotations", "getAnnotations", "()Ljava/util/List;", "setAnnotations", "(Ljava/util/List;)V", "annotations$delegate", "Lkotlin/properties/ReadWriteProperty;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "contextReceiverParametersCount", "getContextReceiverParametersCount", "()I", "setContextReceiverParametersCount", "(I)V", "conversionTypeContext", "Lorg/jetbrains/kotlin/fir/backend/ConversionTypeOrigin;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "dispatchReceiverParameter", "getDispatchReceiverParameter", "()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "setDispatchReceiverParameter", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;)V", "dispatchReceiverParameter$delegate", "extensionReceiverParameter", "getExtensionReceiverParameter", "setExtensionReceiverParameter", "extensionReceiverParameter$delegate", "fir", "getFir", "()Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "initialSignatureFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getInitialSignatureFunction", "()Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "initialSignatureFunction$delegate", "Lkotlin/Lazy;", "<anonymous parameter 0>", "isInline", "()Z", "setInline", "(Z)V", "Lorg/jetbrains/kotlin/name/Name;", "name", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "overriddenSymbols", "getOverriddenSymbols", "setOverriddenSymbols", "overriddenSymbols$delegate", "Lorg/jetbrains/kotlin/ir/types/IrType;", "returnType", "getReturnType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setReturnType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "returnType$delegate", "valueParameters", "getValueParameters", "setValueParameters", "valueParameters$delegate", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrLazyPropertyAccessor extends AbstractFir2IrLazyFunction<FirCallableDeclaration> {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazyPropertyAccessor.class), "annotations", "getAnnotations()Ljava/util/List;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazyPropertyAccessor.class), "returnType", "getReturnType()Lorg/jetbrains/kotlin/ir/types/IrType;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazyPropertyAccessor.class), "dispatchReceiverParameter", "getDispatchReceiverParameter()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazyPropertyAccessor.class), "extensionReceiverParameter", "getExtensionReceiverParameter()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazyPropertyAccessor.class), "valueParameters", "getValueParameters()Ljava/util/List;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazyPropertyAccessor.class), "overriddenSymbols", "getOverriddenSymbols()Ljava/util/List;"))};

    /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty annotations;
    private int contextReceiverParametersCount;
    private final ConversionTypeOrigin conversionTypeContext;

    /* JADX INFO: renamed from: dispatchReceiverParameter$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty dispatchReceiverParameter;

    /* JADX INFO: renamed from: extensionReceiverParameter$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty extensionReceiverParameter;
    private final FirPropertyAccessor firAccessor;
    private final FirProperty firParentProperty;

    /* JADX INFO: renamed from: initialSignatureFunction$delegate, reason: from kotlin metadata */
    private final Lazy initialSignatureFunction;
    private final boolean isSetter;

    /* JADX INFO: renamed from: overriddenSymbols$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty overriddenSymbols;

    /* JADX INFO: renamed from: returnType$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty returnType;

    /* JADX INFO: renamed from: valueParameters$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty valueParameters;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Fir2IrLazyPropertyAccessor(Fir2IrComponents components, final int i, final int i2, IrDeclarationOrigin origin, FirPropertyAccessor firPropertyAccessor, boolean z, FirProperty firParentProperty, final FirRegularClass firRegularClass, IrSimpleFunctionSymbol symbol, boolean z2) {
        super(components, i, i2, origin, symbol, z2);
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(firParentProperty, "firParentProperty");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.firAccessor = firPropertyAccessor;
        this.isSetter = z;
        this.firParentProperty = firParentProperty;
        symbol.bind(this);
        this.annotations = createLazyAnnotations();
        this.returnType = LazyUtilKt.lazyVar(getLock(), new Function0<IrType>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyPropertyAccessor$returnType$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrType invoke() {
                return this.this$0.isSetter ? this.this$0.getIrBuiltIns().getUnitType() : Fir2IrTypeConverterKt.toIrType(this.this$0.firParentProperty.getReturnTypeRef(), this.this$0.getTypeConverter(), this.this$0.conversionTypeContext);
            }
        });
        this.dispatchReceiverParameter = LazyUtilKt.lazyVar(getLock(), new Function0<IrValueParameter>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyPropertyAccessor$dispatchReceiverParameter$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrValueParameter invoke() {
                IrType type;
                IrDeclarationParent parent = this.this$0.getParent();
                IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
                if (irClass == null || IrUtilsKt.isFacadeClass(irClass)) {
                    irClass = null;
                }
                if (irClass == null || !this.this$0.shouldHaveDispatchReceiver(irClass)) {
                    return null;
                }
                Fir2IrLazyPropertyAccessor fir2IrLazyPropertyAccessor = this.this$0;
                IrValueParameter thisReceiver = irClass.getThisReceiver();
                if (thisReceiver == null || (type = thisReceiver.getType()) == null) {
                    throw new IllegalStateException("No this receiver for containing class".toString());
                }
                return AbstractFir2IrLazyFunction.createThisReceiverParameter$default(fir2IrLazyPropertyAccessor, type, null, 2, null);
            }
        });
        this.extensionReceiverParameter = LazyUtilKt.lazyVar(getLock(), new Function0<IrValueParameter>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyPropertyAccessor$extensionReceiverParameter$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrValueParameter invoke() {
                FirReceiverParameter receiverParameter = this.this$0.firParentProperty.getReceiverParameter();
                if (receiverParameter == null) {
                    return null;
                }
                Fir2IrLazyPropertyAccessor fir2IrLazyPropertyAccessor = this.this$0;
                return fir2IrLazyPropertyAccessor.createThisReceiverParameter(Fir2IrTypeConverterKt.toIrType(receiverParameter.getTypeRef(), fir2IrLazyPropertyAccessor.getTypeConverter(), fir2IrLazyPropertyAccessor.conversionTypeContext), receiverParameter);
            }
        });
        this.contextReceiverParametersCount = ConversionUtilsKt.contextReceiversForFunctionOrContainingProperty(getFir()).size();
        this.valueParameters = LazyUtilKt.lazyVar(getLock(), new Function0<List<? extends IrValueParameter>>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyPropertyAccessor$valueParameters$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IrValueParameter> invoke() {
                FirTypeRef returnTypeRef;
                List<FirValueParameter> valueParameters;
                if (!this.this$0.isSetter && this.this$0.getContextReceiverParametersCount() == 0) {
                    return CollectionsKt.emptyList();
                }
                this.this$0.getDeclarationStorage().enterScope(this.this$0);
                Fir2IrLazyPropertyAccessor fir2IrLazyPropertyAccessor = this.this$0;
                int i3 = i;
                int i4 = i2;
                List<IrValueParameter> listCreateListBuilder = CollectionsKt.createListBuilder();
                Fir2IrLazyPropertyAccessor fir2IrLazyPropertyAccessor2 = fir2IrLazyPropertyAccessor;
                fir2IrLazyPropertyAccessor.getDeclarationStorage().addContextReceiverParametersTo(ConversionUtilsKt.contextReceiversForFunctionOrContainingProperty(fir2IrLazyPropertyAccessor.getFir()), fir2IrLazyPropertyAccessor2, listCreateListBuilder);
                if (fir2IrLazyPropertyAccessor.isSetter) {
                    FirPropertyAccessor firPropertyAccessor2 = fir2IrLazyPropertyAccessor.firAccessor;
                    FirValueParameter firValueParameter = (firPropertyAccessor2 == null || (valueParameters = firPropertyAccessor2.getValueParameters()) == null) ? null : (FirValueParameter) CollectionsKt.firstOrNull((List) valueParameters);
                    Fir2IrDeclarationStorage declarationStorage = fir2IrLazyPropertyAccessor.getDeclarationStorage();
                    if (firValueParameter == null || (returnTypeRef = firValueParameter.getReturnTypeRef()) == null) {
                        returnTypeRef = fir2IrLazyPropertyAccessor.firParentProperty.getReturnTypeRef();
                    }
                    listCreateListBuilder.add(declarationStorage.createDefaultSetterParameter$fir2ir(i3, i4, Fir2IrTypeConverterKt.toIrType(returnTypeRef, fir2IrLazyPropertyAccessor.getTypeConverter(), fir2IrLazyPropertyAccessor.conversionTypeContext), fir2IrLazyPropertyAccessor2, firValueParameter, firValueParameter != null ? firValueParameter.getName() : null, firValueParameter != null && firValueParameter.getIsCrossinline(), firValueParameter != null && firValueParameter.getIsNoinline()));
                }
                List<? extends IrValueParameter> listBuild = CollectionsKt.build(listCreateListBuilder);
                Fir2IrLazyPropertyAccessor fir2IrLazyPropertyAccessor3 = this.this$0;
                fir2IrLazyPropertyAccessor3.getDeclarationStorage().leaveScope(fir2IrLazyPropertyAccessor3);
                return listBuild;
            }
        });
        this.overriddenSymbols = LazyUtilKt.lazyVar(getLock(), new Function0<List<? extends IrSimpleFunctionSymbol>>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyPropertyAccessor$overriddenSymbols$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IrSimpleFunctionSymbol> invoke() {
                if (firRegularClass == null) {
                    return CollectionsKt.emptyList();
                }
                Fir2IrLazyPropertyAccessor fir2IrLazyPropertyAccessor = this;
                return ConversionUtilsKt.generateOverriddenAccessorSymbols(fir2IrLazyPropertyAccessor, fir2IrLazyPropertyAccessor.firParentProperty, firRegularClass, !this.isSetter);
            }
        });
        this.initialSignatureFunction = LazyKt.lazy(new Function0<IrFunction>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazyPropertyAccessor$initialSignatureFunction$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrFunction invoke() {
                FirSimpleFunction delegate;
                FirCallableDeclaration fir = this.this$0.getFir();
                FirSyntheticPropertyAccessor firSyntheticPropertyAccessor = fir instanceof FirSyntheticPropertyAccessor ? (FirSyntheticPropertyAccessor) fir : null;
                if (firSyntheticPropertyAccessor == null || (delegate = firSyntheticPropertyAccessor.getDelegate()) == null) {
                    return null;
                }
                return Fir2IrDeclarationStorage.getIrFunctionSymbol$default(this.this$0.getDeclarationStorage(), delegate.getSymbol(), null, 2, null).getOwner();
            }
        });
        this.conversionTypeContext = z ? ConversionTypeOrigin.SETTER : ConversionTypeOrigin.DEFAULT;
    }

    @Override // org.jetbrains.kotlin.fir.lazy.AbstractFir2IrLazyDeclaration
    public FirCallableDeclaration getFir() {
        FirCallableDeclaration firCallableDeclaration = this.firAccessor;
        if (firCallableDeclaration == null) {
            firCallableDeclaration = this.firParentProperty;
        }
        return firCallableDeclaration;
    }

    @Override // org.jetbrains.kotlin.fir.lazy.AbstractFir2IrLazyFunction, org.jetbrains.kotlin.ir.declarations.IrFunction
    /* JADX INFO: renamed from: isInline */
    public boolean getIsInline() {
        FirPropertyAccessor firPropertyAccessor = this.firAccessor;
        return firPropertyAccessor != null && firPropertyAccessor.getStatus().isInline();
    }

    @Override // org.jetbrains.kotlin.fir.lazy.AbstractFir2IrLazyFunction, org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setInline(boolean z) {
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer, org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer
    public List<IrConstructorCall> getAnnotations() {
        return (List) this.annotations.getValue(this, $$delegatedProperties[0]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer
    public void setAnnotations(List<? extends IrConstructorCall> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.annotations.setValue(this, $$delegatedProperties[0], list);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName
    public Name getName() {
        Name nameSpecial = Name.special("<" + (this.isSetter ? FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX : "get") + '-' + this.firParentProperty.getName() + '>');
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        return nameSpecial;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName
    public void setName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "<anonymous parameter 0>");
        mutationNotSupported();
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public IrType getReturnType() {
        return (IrType) this.returnType.getValue(this, $$delegatedProperties[1]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setReturnType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<set-?>");
        this.returnType.setValue(this, $$delegatedProperties[1], irType);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public IrValueParameter getDispatchReceiverParameter() {
        return (IrValueParameter) this.dispatchReceiverParameter.getValue(this, $$delegatedProperties[2]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setDispatchReceiverParameter(IrValueParameter irValueParameter) {
        this.dispatchReceiverParameter.setValue(this, $$delegatedProperties[2], irValueParameter);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public IrValueParameter getExtensionReceiverParameter() {
        return (IrValueParameter) this.extensionReceiverParameter.getValue(this, $$delegatedProperties[3]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setExtensionReceiverParameter(IrValueParameter irValueParameter) {
        this.extensionReceiverParameter.setValue(this, $$delegatedProperties[3], irValueParameter);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public int getContextReceiverParametersCount() {
        return this.contextReceiverParametersCount;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setContextReceiverParametersCount(int i) {
        this.contextReceiverParametersCount = i;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public List<IrValueParameter> getValueParameters() {
        return (List) this.valueParameters.getValue(this, $$delegatedProperties[4]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrFunction
    public void setValueParameters(List<? extends IrValueParameter> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.valueParameters.setValue(this, $$delegatedProperties[4], list);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public List<IrSimpleFunctionSymbol> getOverriddenSymbols() {
        return (List) this.overriddenSymbols.getValue(this, $$delegatedProperties[5]);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public void setOverriddenSymbols(List<? extends IrSimpleFunctionSymbol> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.overriddenSymbols.setValue(this, $$delegatedProperties[5], list);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunctionBase
    public IrFunction getInitialSignatureFunction() {
        return (IrFunction) this.initialSignatureFunction.getValue();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMemberWithContainerSource
    public DeserializedContainerSource getContainerSource() {
        return this.firParentProperty.getContainerSource();
    }
}
