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
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.backend.ConversionUtilsKt;
import org.jetbrains.kotlin.fir.backend.Fir2IrComponents;
import org.jetbrains.kotlin.fir.backend.Fir2IrDeclarationStorage;
import org.jetbrains.kotlin.fir.backend.Fir2IrTypeConverterKt;
import org.jetbrains.kotlin.fir.backend.generators.FakeOverrideGenerator;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.lazy.LazyUtilKt;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: Fir2IrLazySimpleFunction.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001BG\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u0002\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010¢\u0006\u0002\u0010\u0011R7\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00138V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0016\u0010\u001c\u001a\u0004\u0018\u00010\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u001a\u0010 \u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R/\u0010&\u001a\u0004\u0018\u00010%2\b\u0010\u0012\u001a\u0004\u0018\u00010%8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b+\u0010\u001b\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R/\u0010,\u001a\u0004\u0018\u00010%2\b\u0010\u0012\u001a\u0004\u0018\u00010%8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b/\u0010\u001b\u001a\u0004\b-\u0010(\"\u0004\b.\u0010*R\u0014\u0010\n\u001a\u00020\u0002X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u001d\u00102\u001a\u0004\u0018\u0001038VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b6\u00107\u001a\u0004\b4\u00105R$\u0010:\u001a\u0002092\u0006\u00108\u001a\u0002098V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b;\u0010<\"\u0004\b=\u0010>R7\u0010?\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00132\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00138V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bB\u0010\u001b\u001a\u0004\b@\u0010\u0017\"\u0004\bA\u0010\u0019R+\u0010D\u001a\u00020C2\u0006\u0010\u0012\u001a\u00020C8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bI\u0010\u001b\u001a\u0004\bE\u0010F\"\u0004\bG\u0010HR7\u0010J\u001a\b\u0012\u0004\u0012\u00020%0\u00132\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020%0\u00138V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bM\u0010\u001b\u001a\u0004\bK\u0010\u0017\"\u0004\bL\u0010\u0019¨\u0006N"}, d2 = {"Lorg/jetbrains/kotlin/fir/lazy/Fir2IrLazySimpleFunction;", "Lorg/jetbrains/kotlin/fir/lazy/AbstractFir2IrLazyFunction;", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "components", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "fir", "firParent", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "isFakeOverride", "", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;IILorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;Z)V", "<set-?>", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "annotations", "getAnnotations", "()Ljava/util/List;", "setAnnotations", "(Ljava/util/List;)V", "annotations$delegate", "Lkotlin/properties/ReadWriteProperty;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "contextReceiverParametersCount", "getContextReceiverParametersCount", "()I", "setContextReceiverParametersCount", "(I)V", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "dispatchReceiverParameter", "getDispatchReceiverParameter", "()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "setDispatchReceiverParameter", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;)V", "dispatchReceiverParameter$delegate", "extensionReceiverParameter", "getExtensionReceiverParameter", "setExtensionReceiverParameter", "extensionReceiverParameter$delegate", "getFir", "()Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "initialSignatureFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getInitialSignatureFunction", "()Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "initialSignatureFunction$delegate", "Lkotlin/Lazy;", "<anonymous parameter 0>", "Lorg/jetbrains/kotlin/name/Name;", "name", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "overriddenSymbols", "getOverriddenSymbols", "setOverriddenSymbols", "overriddenSymbols$delegate", "Lorg/jetbrains/kotlin/ir/types/IrType;", "returnType", "getReturnType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setReturnType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "returnType$delegate", "valueParameters", "getValueParameters", "setValueParameters", "valueParameters$delegate", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrLazySimpleFunction extends AbstractFir2IrLazyFunction<FirSimpleFunction> {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazySimpleFunction.class), "annotations", "getAnnotations()Ljava/util/List;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazySimpleFunction.class), "returnType", "getReturnType()Lorg/jetbrains/kotlin/ir/types/IrType;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazySimpleFunction.class), "dispatchReceiverParameter", "getDispatchReceiverParameter()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazySimpleFunction.class), "extensionReceiverParameter", "getExtensionReceiverParameter()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazySimpleFunction.class), "valueParameters", "getValueParameters()Ljava/util/List;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Fir2IrLazySimpleFunction.class), "overriddenSymbols", "getOverriddenSymbols()Ljava/util/List;"))};

    /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty annotations;
    private int contextReceiverParametersCount;

    /* JADX INFO: renamed from: dispatchReceiverParameter$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty dispatchReceiverParameter;

    /* JADX INFO: renamed from: extensionReceiverParameter$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty extensionReceiverParameter;
    private final FirSimpleFunction fir;

    /* JADX INFO: renamed from: initialSignatureFunction$delegate, reason: from kotlin metadata */
    private final Lazy initialSignatureFunction;

    /* JADX INFO: renamed from: overriddenSymbols$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty overriddenSymbols;

    /* JADX INFO: renamed from: returnType$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty returnType;

    /* JADX INFO: renamed from: valueParameters$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty valueParameters;

    @Override // org.jetbrains.kotlin.fir.lazy.AbstractFir2IrLazyDeclaration
    public FirSimpleFunction getFir() {
        return this.fir;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Fir2IrLazySimpleFunction(Fir2IrComponents components, int i, int i2, IrDeclarationOrigin origin, FirSimpleFunction fir, final FirRegularClass firRegularClass, final IrSimpleFunctionSymbol symbol, final boolean z) {
        super(components, i, i2, origin, symbol, z);
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(fir, "fir");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.fir = fir;
        symbol.bind(this);
        getClassifierStorage().preCacheTypeParameters$fir2ir(getFir(), symbol);
        this.annotations = createLazyAnnotations();
        this.returnType = LazyUtilKt.lazyVar(getLock(), new Function0<IrType>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazySimpleFunction$returnType$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrType invoke() {
                return Fir2IrTypeConverterKt.toIrType$default(this.this$0.getFir().getReturnTypeRef(), this.this$0.getTypeConverter(), null, 2, null);
            }
        });
        this.dispatchReceiverParameter = LazyUtilKt.lazyVar(getLock(), new Function0<IrValueParameter>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazySimpleFunction$dispatchReceiverParameter$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrValueParameter invoke() {
                IrType type;
                IrDeclarationParent parent = this.this$0.getParent();
                IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
                if (irClass == null || !this.this$0.shouldHaveDispatchReceiver(irClass)) {
                    return null;
                }
                Fir2IrLazySimpleFunction fir2IrLazySimpleFunction = this.this$0;
                IrValueParameter thisReceiver = irClass.getThisReceiver();
                if (thisReceiver == null || (type = thisReceiver.getType()) == null) {
                    throw new IllegalStateException("No this receiver for containing class".toString());
                }
                return AbstractFir2IrLazyFunction.createThisReceiverParameter$default(fir2IrLazySimpleFunction, type, null, 2, null);
            }
        });
        this.extensionReceiverParameter = LazyUtilKt.lazyVar(getLock(), new Function0<IrValueParameter>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazySimpleFunction$extensionReceiverParameter$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrValueParameter invoke() {
                FirReceiverParameter receiverParameter = this.this$0.getFir().getReceiverParameter();
                if (receiverParameter == null) {
                    return null;
                }
                Fir2IrLazySimpleFunction fir2IrLazySimpleFunction = this.this$0;
                return fir2IrLazySimpleFunction.createThisReceiverParameter(Fir2IrTypeConverterKt.toIrType$default(receiverParameter.getTypeRef(), fir2IrLazySimpleFunction.getTypeConverter(), null, 2, null), receiverParameter);
            }
        });
        this.contextReceiverParametersCount = ConversionUtilsKt.contextReceiversForFunctionOrContainingProperty(getFir()).size();
        this.valueParameters = LazyUtilKt.lazyVar(getLock(), new Function0<List<? extends IrValueParameter>>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazySimpleFunction$valueParameters$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IrValueParameter> invoke() {
                this.this$0.getDeclarationStorage().enterScope(this.this$0);
                Fir2IrLazySimpleFunction fir2IrLazySimpleFunction = this.this$0;
                boolean z2 = z;
                List<IrValueParameter> listCreateListBuilder = CollectionsKt.createListBuilder();
                fir2IrLazySimpleFunction.getDeclarationStorage().addContextReceiverParametersTo(ConversionUtilsKt.contextReceiversForFunctionOrContainingProperty(fir2IrLazySimpleFunction.getFir()), fir2IrLazySimpleFunction, listCreateListBuilder);
                int i3 = 0;
                for (Object obj : fir2IrLazySimpleFunction.getFir().getValueParameters()) {
                    List<IrValueParameter> list = listCreateListBuilder;
                    int i4 = i3 + 1;
                    if (i3 < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    IrValueParameter irValueParameterCreateIrParameter$fir2ir$default = Fir2IrDeclarationStorage.createIrParameter$fir2ir$default(fir2IrLazySimpleFunction.getDeclarationStorage(), (FirValueParameter) obj, fir2IrLazySimpleFunction.getContextReceiverParametersCount() + i3, false, null, z2, false, 44, null);
                    irValueParameterCreateIrParameter$fir2ir$default.setParent(fir2IrLazySimpleFunction);
                    list.add(irValueParameterCreateIrParameter$fir2ir$default);
                    i3 = i4;
                }
                List<? extends IrValueParameter> listBuild = CollectionsKt.build(listCreateListBuilder);
                Fir2IrLazySimpleFunction fir2IrLazySimpleFunction2 = this.this$0;
                fir2IrLazySimpleFunction2.getDeclarationStorage().leaveScope(fir2IrLazySimpleFunction2);
                return listBuild;
            }
        });
        this.overriddenSymbols = LazyUtilKt.lazyVar(getLock(), new Function0<List<? extends IrSimpleFunctionSymbol>>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazySimpleFunction$overriddenSymbols$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IrSimpleFunctionSymbol> invoke() {
                if (firRegularClass == null) {
                    return CollectionsKt.emptyList();
                }
                IrDeclarationParent parent = this.getParent();
                if (z && (parent instanceof Fir2IrLazyClass)) {
                    FakeOverrideGenerator fakeOverrideGenerator = this.getFakeOverrideGenerator();
                    FirRegularClass firRegularClass2 = firRegularClass;
                    Fir2IrLazySimpleFunction fir2IrLazySimpleFunction = this;
                    fakeOverrideGenerator.calcBaseSymbolsForFakeOverrideFunction$fir2ir(firRegularClass2, fir2IrLazySimpleFunction, fir2IrLazySimpleFunction.getFir().getSymbol());
                    List<IrSimpleFunctionSymbol> overriddenSymbolsForFakeOverride$fir2ir = this.getFakeOverrideGenerator().getOverriddenSymbolsForFakeOverride$fir2ir(this);
                    if (overriddenSymbolsForFakeOverride$fir2ir != null) {
                        overriddenSymbolsForFakeOverride$fir2ir.contains(symbol);
                        return overriddenSymbolsForFakeOverride$fir2ir;
                    }
                }
                Fir2IrLazySimpleFunction fir2IrLazySimpleFunction2 = this;
                return ConversionUtilsKt.generateOverriddenFunctionSymbols(fir2IrLazySimpleFunction2, fir2IrLazySimpleFunction2.getFir(), firRegularClass);
            }
        });
        this.initialSignatureFunction = LazyKt.lazy(new Function0<IrFunction>() { // from class: org.jetbrains.kotlin.fir.lazy.Fir2IrLazySimpleFunction$initialSignatureFunction$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrFunction invoke() {
                FirFunctionSymbol<? extends FirFunction> symbol2;
                IrFunction owner;
                FirCallableDeclaration initialSignatureAttr = ClassMembersKt.getInitialSignatureAttr(this.this$0.getFir());
                FirFunction firFunction = initialSignatureAttr instanceof FirFunction ? (FirFunction) initialSignatureAttr : null;
                if (firFunction == null || (symbol2 = firFunction.getSymbol()) == null || (owner = Fir2IrDeclarationStorage.getIrFunctionSymbol$default(this.this$0.getDeclarationStorage(), symbol2, null, 2, null).getOwner()) == null || owner == this.this$0) {
                    return null;
                }
                return owner;
            }
        });
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
        return getFir().getName();
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
        return getFir().getContainerSource();
    }
}
