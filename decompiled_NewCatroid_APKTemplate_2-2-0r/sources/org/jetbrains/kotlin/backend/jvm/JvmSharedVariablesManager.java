package org.jetbrains.kotlin.backend.jvm;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.backend.common.ir.SharedVariablesManager;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrClassBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrTypeParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.declarations.impl.IrExternalPackageFragmentImpl;
import org.jetbrains.kotlin.ir.declarations.impl.IrVariableImpl;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrVariableSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: JvmSharedVariablesManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u00010B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0016J\u0018\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u0019H\u0016J\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001fJ\u000e\u0010!\u001a\u00020\u00102\u0006\u0010\"\u001a\u00020\u001fJ\u0018\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H\u0016J\u0018\u0010)\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010*\u001a\u00020+H\u0016J \u0010,\u001a\u00020$2\u0006\u0010-\u001a\u00020$2\u0006\u0010.\u001a\u00020\u001f2\u0006\u0010/\u001a\u00020\u001fH\u0002R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00100\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmSharedVariablesManager;", "Lorg/jetbrains/kotlin/backend/common/ir/SharedVariablesManager;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "symbols", "Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols;Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/ir/declarations/IrFactory;)V", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "jvmInternalPackage", "Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "objectRefProvider", "Lorg/jetbrains/kotlin/backend/jvm/JvmSharedVariablesManager$RefProvider;", "primitiveRefProviders", "", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "refNamespaceClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getSymbols", "()Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols;", "declareSharedVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "originalDeclaration", "defineSharedValue", "Lorg/jetbrains/kotlin/ir/IrStatement;", "sharedVariableDeclaration", "getIrType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "originalType", "getProvider", "valueType", "getSharedValue", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "sharedVariableSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "originalGet", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "setSharedValue", "originalSet", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "unsafeCoerce", "value", TypedValues.TransitionType.S_FROM, "to", "RefProvider", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmSharedVariablesManager implements SharedVariablesManager {
    private final IrBuiltIns irBuiltIns;
    private final IrExternalPackageFragment jvmInternalPackage;
    private final RefProvider objectRefProvider;
    private final Map<IrClassifierSymbol, RefProvider> primitiveRefProviders;
    private final IrClass refNamespaceClass;
    private final JvmSymbols symbols;

    public JvmSharedVariablesManager(ModuleDescriptor module, JvmSymbols symbols, IrBuiltIns irBuiltIns, IrFactory irFactory) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(symbols, "symbols");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        Intrinsics.checkNotNullParameter(irFactory, "irFactory");
        this.symbols = symbols;
        this.irBuiltIns = irBuiltIns;
        IrExternalPackageFragment irExternalPackageFragmentCreateEmptyExternalPackageFragment = IrExternalPackageFragmentImpl.INSTANCE.createEmptyExternalPackageFragment(module, new FqName("kotlin.jvm.internal"));
        this.jvmInternalPackage = irExternalPackageFragmentCreateEmptyExternalPackageFragment;
        IrExternalPackageFragment irExternalPackageFragment = irExternalPackageFragmentCreateEmptyExternalPackageFragment;
        IrClassBuilder irClassBuilder = new IrClassBuilder();
        Name nameIdentifier = Name.identifier("Ref");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irClassBuilder.setName(nameIdentifier);
        IrClass irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
        irClassBuildClass.setParent(irExternalPackageFragment);
        irExternalPackageFragment.getDeclarations().add(irClassBuildClass);
        this.refNamespaceClass = irClassBuildClass;
        List<IrType> primitiveIrTypes = irBuiltIns.getPrimitiveIrTypes();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(primitiveIrTypes, 10)), 16));
        for (IrType irType : primitiveIrTypes) {
            IrClass irClass = this.refNamespaceClass;
            IrClassBuilder irClassBuilder2 = new IrClassBuilder();
            irClassBuilder2.setOrigin(IrDeclarationOrigin.IR_BUILTINS_STUB.INSTANCE);
            StringBuilder sb = new StringBuilder();
            IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irType);
            Intrinsics.checkNotNull(classOrNull);
            Name nameIdentifier2 = Name.identifier(sb.append(classOrNull.getOwner().getName().asString()).append("Ref").toString());
            Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
            irClassBuilder2.setName(nameIdentifier2);
            IrClass irClassBuildClass2 = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder2);
            irClassBuildClass2.setParent(irClass);
            irClass.getDeclarations().add(irClassBuildClass2);
            IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass2);
            Pair pair = TuplesKt.to(IrTypesKt.getClassifierOrFail(irType), new RefProvider(irClassBuildClass2, irType));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        this.primitiveRefProviders = linkedHashMap;
        IrClass irClass2 = this.refNamespaceClass;
        IrClassBuilder irClassBuilder3 = new IrClassBuilder();
        irClassBuilder3.setOrigin(IrDeclarationOrigin.IR_BUILTINS_STUB.INSTANCE);
        Name nameIdentifier3 = Name.identifier("ObjectRef");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
        irClassBuilder3.setName(nameIdentifier3);
        IrClass irClassBuildClass3 = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder3);
        irClassBuildClass3.setParent(irClass2);
        irClass2.getDeclarations().add(irClassBuildClass3);
        IrClass irClass3 = irClassBuildClass3;
        IrTypeParameterBuilder irTypeParameterBuilder = new IrTypeParameterBuilder();
        Name nameIdentifier4 = Name.identifier(ExifInterface.GPS_DIRECTION_TRUE);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier4, "identifier(...)");
        irTypeParameterBuilder.setName(nameIdentifier4);
        irTypeParameterBuilder.getSuperTypes().add(this.irBuiltIns.getAnyNType());
        if (irTypeParameterBuilder.getIndex() == -1) {
            irTypeParameterBuilder.setIndex(irClass3.getTypeParameters().size());
        }
        irClass3.setTypeParameters(CollectionsKt.plus((Collection<? extends IrTypeParameter>) irClass3.getTypeParameters(), DeclarationBuildersKt.buildTypeParameter(irClass3.getFactory(), irTypeParameterBuilder, irClass3)));
        IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass3);
        this.objectRefProvider = new RefProvider(irClassBuildClass3, IrTypesKt.getDefaultType(irClassBuildClass3.getTypeParameters().get(0)));
    }

    public final JvmSymbols getSymbols() {
        return this.symbols;
    }

    public final IrBuiltIns getIrBuiltIns() {
        return this.irBuiltIns;
    }

    /* JADX INFO: compiled from: JvmSharedVariablesManager.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmSharedVariablesManager$RefProvider;", "", "refClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "elementType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/types/IrType;)V", "elementField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getElementField", "()Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getRefClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "refConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getRefConstructor", "()Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class RefProvider {
        private final IrField elementField;
        private final IrClass refClass;
        private final IrConstructor refConstructor;

        public RefProvider(IrClass refClass, IrType elementType) {
            Intrinsics.checkNotNullParameter(refClass, "refClass");
            Intrinsics.checkNotNullParameter(elementType, "elementType");
            this.refClass = refClass;
            IrFactory factory = refClass.getFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            irFunctionBuilder.setOrigin(IrDeclarationOrigin.IR_BUILTINS_STUB.INSTANCE);
            irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(refClass));
            IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
            refClass.getDeclarations().add(irConstructorBuildConstructor);
            irConstructorBuildConstructor.setParent(refClass);
            this.refConstructor = irConstructorBuildConstructor;
            IrFactory factory2 = refClass.getFactory();
            IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
            irFieldBuilder.setOrigin(IrDeclarationOrigin.IR_BUILTINS_STUB.INSTANCE);
            Name nameIdentifier = Name.identifier(CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            irFieldBuilder.setName(nameIdentifier);
            irFieldBuilder.setType(elementType);
            IrField irFieldBuildField = DeclarationBuildersKt.buildField(factory2, irFieldBuilder);
            irFieldBuildField.setParent(refClass);
            refClass.getDeclarations().add(irFieldBuildField);
            this.elementField = irFieldBuildField;
        }

        public final IrClass getRefClass() {
            return this.refClass;
        }

        public final IrConstructor getRefConstructor() {
            return this.refConstructor;
        }

        public final IrField getElementField() {
            return this.elementField;
        }
    }

    public final RefProvider getProvider(IrType valueType) {
        Intrinsics.checkNotNullParameter(valueType, "valueType");
        if (IrTypePredicatesKt.isPrimitiveType$default(valueType, false, 1, null)) {
            return (RefProvider) MapsKt.getValue(this.primitiveRefProviders, IrTypesKt.getClassifierOrFail(valueType));
        }
        return this.objectRefProvider;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.SharedVariablesManager
    public IrVariable declareSharedVariable(IrVariable originalDeclaration) {
        Intrinsics.checkNotNullParameter(originalDeclaration, "originalDeclaration");
        IrType type = originalDeclaration.getType();
        IrType irTypeUnboxType = InlineClassAbi.INSTANCE.unboxType(type);
        if (irTypeUnboxType == null) {
            irTypeUnboxType = type;
        }
        RefProvider provider = getProvider(irTypeUnboxType);
        List<IrTypeParameter> typeParameters = provider.getRefClass().getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        for (IrTypeParameter irTypeParameter : typeParameters) {
            arrayList.add(type);
        }
        ArrayList arrayList2 = arrayList;
        IrSimpleType irSimpleTypeTypeWith = IrTypesKt.typeWith(provider.getRefClass(), arrayList2);
        IrConstructorCallImpl irConstructorCallImplFromSymbolOwner$default = IrConstructorCallImpl.Companion.fromSymbolOwner$default(IrConstructorCallImpl.INSTANCE, originalDeclaration.getStartOffset(), originalDeclaration.getStartOffset(), irSimpleTypeTypeWith, provider.getRefConstructor().getSymbol(), null, 16, null);
        int i = 0;
        for (Object obj : arrayList2) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            irConstructorCallImplFromSymbolOwner$default.putTypeArgument(i, (IrType) obj);
            i = i2;
        }
        IrVariableImpl irVariableImpl = new IrVariableImpl(originalDeclaration.getStartOffset(), originalDeclaration.getEndOffset(), originalDeclaration.getOrigin(), new IrVariableSymbolImpl(null, 1, null), originalDeclaration.getName(), irSimpleTypeTypeWith, false, false, false);
        irVariableImpl.setInitializer(irConstructorCallImplFromSymbolOwner$default);
        return irVariableImpl;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.SharedVariablesManager
    public IrStatement defineSharedValue(IrVariable originalDeclaration, IrVariable sharedVariableDeclaration) {
        Intrinsics.checkNotNullParameter(originalDeclaration, "originalDeclaration");
        Intrinsics.checkNotNullParameter(sharedVariableDeclaration, "sharedVariableDeclaration");
        IrExpression initializer = originalDeclaration.getInitializer();
        if (initializer == null) {
            return sharedVariableDeclaration;
        }
        IrConstImpl<?> irConstImplDefaultValueForType = IrConstImpl.INSTANCE.defaultValueForType(initializer.getStartOffset(), initializer.getEndOffset(), originalDeclaration.getType());
        if ((initializer instanceof IrConst) && Intrinsics.areEqual(((IrConst) initializer).getValue(), irConstImplDefaultValueForType.getValue())) {
            return sharedVariableDeclaration;
        }
        return new IrCompositeImpl(originalDeclaration.getStartOffset(), originalDeclaration.getEndOffset(), this.irBuiltIns.getUnitType(), null, CollectionsKt.listOf((Object[]) new IrElement[]{sharedVariableDeclaration, setSharedValue(sharedVariableDeclaration.getSymbol(), new IrSetValueImpl(originalDeclaration.getStartOffset(), originalDeclaration.getEndOffset(), this.irBuiltIns.getUnitType(), originalDeclaration.getSymbol(), initializer, null))}));
    }

    private final IrExpression unsafeCoerce(IrExpression value, IrType from, IrType to) {
        IrCallImpl.Companion companion = IrCallImpl.INSTANCE;
        int startOffset = value.getStartOffset();
        int endOffset = value.getEndOffset();
        IrSimpleFunctionSymbol unsafeCoerceIntrinsic = this.symbols.getUnsafeCoerceIntrinsic();
        IrCallImpl irCallImplFromSymbolOwner = companion.fromSymbolOwner(startOffset, endOffset, to, unsafeCoerceIntrinsic, (240 & 16) != 0 ? unsafeCoerceIntrinsic.getOwner().getTypeParameters().size() : 0, (240 & 32) != 0 ? unsafeCoerceIntrinsic.getOwner().getValueParameters().size() : 0, (240 & 64) != 0 ? null : null, (240 & 128) != 0 ? null : null);
        irCallImplFromSymbolOwner.putTypeArgument(0, from);
        irCallImplFromSymbolOwner.putTypeArgument(1, to);
        irCallImplFromSymbolOwner.putValueArgument(0, value);
        return irCallImplFromSymbolOwner;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.SharedVariablesManager
    public IrExpression getSharedValue(IrValueSymbol sharedVariableSymbol, IrGetValue originalGet) {
        IrExpression irExpressionUnsafeCoerce;
        Intrinsics.checkNotNullParameter(sharedVariableSymbol, "sharedVariableSymbol");
        Intrinsics.checkNotNullParameter(originalGet, "originalGet");
        IrType irTypeUnboxType = InlineClassAbi.INSTANCE.unboxType(originalGet.getSymbol().getOwner().getType());
        RefProvider provider = getProvider(irTypeUnboxType == null ? originalGet.getSymbol().getOwner().getType() : irTypeUnboxType);
        IrGetFieldImpl irGetFieldImpl = new IrGetFieldImpl(originalGet.getStartOffset(), originalGet.getEndOffset(), provider.getElementField().getSymbol(), irTypeUnboxType == null ? originalGet.getType() : irTypeUnboxType, new IrGetValueImpl(originalGet.getStartOffset(), originalGet.getEndOffset(), sharedVariableSymbol, null, 8, null), originalGet.getOrigin(), null, 64, null);
        return (irTypeUnboxType == null || (irExpressionUnsafeCoerce = unsafeCoerce(irGetFieldImpl, irTypeUnboxType, originalGet.getSymbol().getOwner().getType())) == null) ? irGetFieldImpl : irExpressionUnsafeCoerce;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.SharedVariablesManager
    public IrExpression setSharedValue(IrValueSymbol sharedVariableSymbol, IrSetValue originalSet) {
        IrExpression value;
        Intrinsics.checkNotNullParameter(sharedVariableSymbol, "sharedVariableSymbol");
        Intrinsics.checkNotNullParameter(originalSet, "originalSet");
        IrType irTypeUnboxType = InlineClassAbi.INSTANCE.unboxType(originalSet.getSymbol().getOwner().getType());
        if (irTypeUnboxType == null || (value = unsafeCoerce(originalSet.getValue(), originalSet.getSymbol().getOwner().getType(), irTypeUnboxType)) == null) {
            value = originalSet.getValue();
        }
        IrExpression irExpression = value;
        if (irTypeUnboxType == null) {
            irTypeUnboxType = originalSet.getSymbol().getOwner().getType();
        }
        return new IrSetFieldImpl(originalSet.getStartOffset(), originalSet.getEndOffset(), getProvider(irTypeUnboxType).getElementField().getSymbol(), new IrGetValueImpl(originalSet.getStartOffset(), originalSet.getEndOffset(), sharedVariableSymbol, null, 8, null), irExpression, originalSet.getType(), originalSet.getOrigin(), null, 128, null);
    }

    public final IrType getIrType(IrType originalType) {
        Intrinsics.checkNotNullParameter(originalType, "originalType");
        IrType irTypeUnboxType = InlineClassAbi.INSTANCE.unboxType(originalType);
        if (irTypeUnboxType == null) {
            irTypeUnboxType = originalType;
        }
        RefProvider provider = getProvider(irTypeUnboxType);
        List<IrTypeParameter> typeParameters = provider.getRefClass().getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        for (IrTypeParameter irTypeParameter : typeParameters) {
            arrayList.add(originalType);
        }
        return IrTypesKt.typeWith(provider.getRefClass(), arrayList);
    }
}
